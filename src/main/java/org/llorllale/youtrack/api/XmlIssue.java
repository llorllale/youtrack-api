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

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.llorllale.youtrack.api.jaxb.Field;
import org.llorllale.youtrack.api.jaxb.Value;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.HttpRequestWithEntity;
import org.llorllale.youtrack.api.util.HttpRequestWithSession;
import org.llorllale.youtrack.api.util.response.HttpResponseAsResponse;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * JAXB implementation of {@link Issue}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
class XmlIssue implements Issue<org.llorllale.youtrack.api.jaxb.Issue> {
  private final Project project;
  private final Session session;
  private final org.llorllale.youtrack.api.jaxb.Issue jaxbIssue;
  private final HttpClient httpClient;

  /**
   * Primary ctor.
   * @param project this {@link Issue issue's} {@link Project}
   * @param jaxbIssue the JAXB issue to be adapted
   * @param httpClient the {@link HttpClient} to use
   * @since 0.1.0
   */
  XmlIssue(
      Project project, 
      Session session, 
      org.llorllale.youtrack.api.jaxb.Issue jaxbIssue,
      HttpClient httpClient
  ) {
    this.project = project;
    this.session = session;
    this.jaxbIssue = jaxbIssue;
    this.httpClient = httpClient;
  }

  /**
   * Uses the {@link HttpClients#createDefault() default} http client.
   * @param project this {@link Issue issue's} {@link Project}
   * @param jaxbIssue the JAXB issue to be adapted
   * @since 0.1.0
   */
  XmlIssue(
      Project project, 
      Session session, 
      org.llorllale.youtrack.api.jaxb.Issue jaxbIssue
  ) {
    this(project, session, jaxbIssue, HttpClients.createDefault());
  }

  @Override
  public String id() {
    return jaxbIssue.getId();
  }

  @Override
  public Instant creationDate() {
    return Instant.ofEpochMilli(jaxbIssue.getField()
            .stream()
            .filter(f -> "created".equals(f.getName()))
            .map(Field::getValue)
            .map(Value::getValue)
            .map(Long::valueOf)
            .findFirst()
            .get()
    );
  }

  @Override
  public String type() {
    return jaxbIssue.getField()
            .stream()
            .filter(f -> "Type".equals(f.getName()))
            .map(Field::getValue)
            .map(Value::getValue)
            .findFirst()
            .get();
  }

  @Override
  public AssignedState state() {
    return new XmlAssignedState(this);
  }

  @Override
  public AssignedPriority priority() {
    return new XmlAssignedPriority(this, session);
  }

  @Override
  public String summary() {
    return jaxbIssue.getField()
            .stream()
            .filter(f -> "summary".equals(f.getName()))
            .map(Field::getValue)
            .map(Value::getValue)
            .findFirst()
            .get();
  }

  @Override
  public String description() {
    return jaxbIssue.getField()
            .stream()
            .filter(f -> "description".equals(f.getName()))
            .map(Field::getValue)
            .map(Value::getValue)
            .findFirst()
            .get();
  }

  @Override
  public Project project() {
    return project;
  }

  @Override
  public Comments comments() {
    return new DefaultComments(session, this);
  }

  @Override
  public TimeTracking timetracking() {
    return new DefaultTimeTracking(session, this);
  }

  @Override
  public UsersOfIssue users() {
    return new DefaultUsersOfIssue(session, this);
  }

  @Override
  public org.llorllale.youtrack.api.jaxb.Issue asDto() {
    return jaxbIssue;
  }

  @Override
  public Issue refresh() 
      throws IOException, UnauthorizedException {
    return project().issues()
        .get(id())
        .get();
  }

  @Override
  public Issue update(Map<String, String> args) throws IOException, UnauthorizedException {
    new HttpResponseAsResponse(
        httpClient.execute(
            new HttpRequestWithSession(
                session, 
                new HttpRequestWithEntity(
                    new StringEntity(
                        "command=".concat(
                            args.entrySet().stream()
                                .map(e -> String.join(" ", e.getKey(), e.getValue()))
                                .collect(Collectors.joining(" "))
                        ), 
                        ContentType.APPLICATION_FORM_URLENCODED
                    ), 
                    new HttpPost(
                        session.baseUrl().toString()
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
}