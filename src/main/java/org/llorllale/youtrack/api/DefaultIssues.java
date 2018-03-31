/*
 * Copyright 2017 George Aristy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.llorllale.youtrack.api;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClients;

import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Default implementation of {@link Issues}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
final class DefaultIssues implements Issues {
  private final Project project;
  private final Session session;
  private final HttpClient httpClient;

  /**
   * Primary ctor.
   * 
   * @param project the parent {@link Project}
   * @param session the user {@link Session}
   * @param httpClient the {@link HttpClient} to use
   * @since 0.4.0
   */
  DefaultIssues(Project project, Session session, HttpClient httpClient) {
    this.project = project;
    this.session = session;
    this.httpClient = httpClient;
  }

  /**
   * Uses the {@link HttpClients#createDefault() default} httpClient.
   * 
   * @param project the parent {@link Project}
   * @param session the user {@link Session}
   * @see #DefaultIssues(Project, Session, HttpClient) 
   * @since 0.4.0
   */
  DefaultIssues(Project project, Session session) {
    this(project, session, HttpClients.createDefault());
  }

  @Override
  public Project project() {
    return this.project;
  }

  @Override
  public Stream<Issue> stream() throws IOException, UnauthorizedException {
    final int pageSize = 10;
    return new StreamOf<>(
      new Pagination<>(
        pageSize,
        n -> new HttpRequestWithSession(
          this.session, 
          new HttpGet(
            new UncheckedUriBuilder(
              this.session.baseUrl().toString()
                .concat("/issue/byproject/")
                .concat(this.project().id())
            ).param("after", String.valueOf(n))
              .build()
          )
        ),
        resp -> 
          new MappedCollection<>(
            xml -> new XmlIssue(this.project(), this.session, xml),
            new XmlsOf("/issues/issue", resp)
          ),
        this.httpClient
      )
    );
  }

  @Override
  public Optional<Issue> get(String issueId) throws IOException, UnauthorizedException {
    //when the issueId does not exist, YouTrack returns a 404 response with an error XML
    //in the payload
    return Optional.of(
      new XmlOf(
        new HttpResponseAsResponse(
          this.httpClient.execute(
            new HttpRequestWithSession(
              this.session, 
              new HttpGet(
                this.session.baseUrl().toString().concat("/issue/").concat(issueId)
              )
            )
          )
        )
      )
    ).filter(x -> !x.child("//error").isPresent())
      .map(x -> new XmlIssue(this.project(), this.session, x));
  }

  @Override
  public Issue create(String summary, String description) 
      throws IOException, UnauthorizedException {
    return this.create(summary, description, Collections.emptyMap());
  }

  @Override
  public Issue create(String summary, String description, Map<Field, FieldValue> fields) 
      throws IOException, UnauthorizedException {
    return this.get(
      new SubstringAfterLast(
        new HttpResponseAsResponse(
          this.httpClient.execute(
            new HttpRequestWithSession(
              this.session, 
              new HttpPut(
                new UncheckedUriBuilder(
                  this.session.baseUrl().toString().concat("/issue")
                ).param("project", this.project().id())
                  .param("summary", summary)
                  .paramIfPresent("description", Optional.ofNullable(description))
                  .build()
              )
            )
          )
        ).httpResponse().getFirstHeader("Location").getValue(),
        "/"
      ).get()
    ).get().update().fields(fields);
  }
}
