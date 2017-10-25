/* 
 * Copyright 2017 George Aristy (george.aristy@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.llorllale.youtrack.api;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.HttpRequestWithEntity;
import org.llorllale.youtrack.api.util.HttpRequestWithSession;
import org.llorllale.youtrack.api.util.response.HttpResponseAsResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * JAXB implementation of {@link Issue}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
class XmlIssue implements Issue {
  private final Project project;
  private final Session session;
  private final org.llorllale.youtrack.api.jaxb.Issue jaxbIssue;

  /**
   * Primary ctor.
   * 
   * @param project this {@link Issue issue's} {@link Project}
   * @param jaxbIssue the JAXB issue to be adapted
   * @param httpClient the {@link HttpClient} to use
   * @since 0.1.0
   */
  XmlIssue(
      Project project, 
      Session session, 
      org.llorllale.youtrack.api.jaxb.Issue jaxbIssue
  ) {
    this.project = project;
    this.session = session;
    this.jaxbIssue = jaxbIssue;
  }

  /**
   * For testing purposes.
   * 
   * @param prototype the prototype
   * @since 0.8.0
   */
  XmlIssue(XmlIssue prototype) {
    this(prototype.project(), prototype.session, prototype.jaxbIssue);
  }

  @Override
  public String id() {
    return this.jaxbIssue.getId();
  }

  @Override
  public Instant creationDate() {
    return Instant.ofEpochMilli(this.jaxbIssue.getField()
            .stream()
            .filter(f -> "created".equals(f.getName()))
            .map(f -> f.getValue().getValue())
            .map(Long::valueOf)
            .findFirst()
            .get()
    );
  }

  @Override
  public String summary() {
    return this.jaxbIssue.getField()
            .stream()
            .filter(f -> "summary".equals(f.getName()))
            .map(f -> f.getValue().getValue())
            .findFirst()
            .get();
  }

  @Override
  public String description() {
    return this.jaxbIssue.getField()
            .stream()
            .filter(f -> "description".equals(f.getName()))
            .map(f -> f.getValue().getValue())
            .findFirst()
            .get();
  }

  @Override
  public Project project() {
    return this.project;
  }

  @Override
  public Comments comments() {
    return new DefaultComments(this.session, this);
  }

  @Override
  public IssueTimeTracking timetracking() {
    return new DefaultIssueTimeTracking(this.session, this);
  }

  @Override
  public UsersOfIssue users() {
    return new DefaultUsersOfIssue(this, this.jaxbIssue);
  }

  @Override
  public Issue refresh() throws IOException, UnauthorizedException {
    return this.project().issues()
        .get(this.id())
        .get();
  }

  @Override
  public Issue update(Field field, FieldValue value) 
      throws IOException, UnauthorizedException {
    return this.update(String.join(" ", field.name(), value.asString()));
  }

  @Override
  public Issue update(Map<Field, FieldValue> fields) 
      throws IOException, UnauthorizedException {
    return this.update(
        fields.entrySet().stream()
            .map(e -> String.join(" ", e.getKey().name(), e.getValue().asString()))
            .collect(joining(" "))
    );
  }

  private Issue update(String commands) 
      throws IOException, UnauthorizedException {
    new HttpResponseAsResponse(
        HttpClients.createDefault().execute(
            new HttpRequestWithSession(
                this.session, 
                new HttpRequestWithEntity(
                    new UrlEncodedFormEntity(
                        Arrays.asList(
                            new BasicNameValuePair("command", commands)
                        ),
                        StandardCharsets.UTF_8
                    ),
                    new HttpPost(
                        this.session.baseUrl().toString()
                            .concat("/issue/")
                            .concat(this.id())
                            .concat("/execute")
                    )
                )
            )
        )
    ).asHttpResponse();

    return this.refresh();
  }

  @Override
  public List<AssignedField> fields() {
    return this.jaxbIssue.getField().stream()
        .filter(f -> nonNull(f.getValueId()))
        .map(f -> 
            new DefaultAssignedField(
                new BasicField(f.getName(), this.project),
                this, 
                f
            )
        ).collect(toList());
  }
}