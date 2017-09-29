/*
 * Copyright 2017 George Aristy.
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

import static org.apache.commons.lang3.StringUtils.substringAfterLast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClients;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.HttpEntityAsJaxb;
import org.llorllale.youtrack.api.util.HttpRequestWithSession;
import org.llorllale.youtrack.api.util.NonCheckedUriBuilder;
import org.llorllale.youtrack.api.util.StandardErrorCheck;
import org.llorllale.youtrack.api.util.XmlStringAsJaxb;
import org.llorllale.youtrack.api.util.response.HttpResponseAsResponse;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Default implementation of {@link Issues}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
class DefaultIssues implements Issues {
  private final Project project;
  private final Session session;
  private final HttpClient httpClient;

  /**
   * Primary ctor.
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
   * @param project the parent {@link Project}
   * @param session the user {@link Session}
   * @since 0.4.0
   * @see #DefaultIssues(org.llorllale.youtrack.api.v2.Project, 
   *     org.llorllale.youtrack.api.session.Session, org.apache.http.client.HttpClient) 
   */
  DefaultIssues(Project project, Session session) {
    this(project, session, HttpClients.createDefault());
  }

  @Override
  public Project project() {
    return project;
  }

  @Override
  public Stream<Issue> stream() throws IOException, UnauthorizedException {
    return new HttpEntityAsJaxb<>(org.llorllale.youtrack.api.jaxb.Issues.class).apply(
        new HttpResponseAsResponse(
            httpClient.execute(
                new HttpRequestWithSession(
                    session, 
                    new HttpGet(
                        session.baseUrl()
                            .toString()
                            .concat("/issue/byproject/")
                            .concat(project().id())
                    )
                )
            )
        ).asHttpResponse().getEntity()
    ).getIssue()
        .stream()
        .map(i -> new XmlIssue(project, session, i));
  }

  @Override
  public Optional<Issue> get(String id) throws IOException, UnauthorizedException {
    return new HttpResponseAsResponse(
        httpClient.execute(
            new HttpRequestWithSession(
                session, 
                new HttpGet(
                    session.baseUrl()
                        .toString()
                        .concat("/issue/")
                        .concat(id)
                )
            )
        )
    ).applyOnEntity(
        new XmlStringAsJaxb<>(org.llorllale.youtrack.api.jaxb.Issue.class), 
        new StandardErrorCheck()
    ).map(i -> new XmlIssue(project, session, i));
  }

  @Override
  public Issue create(IssueSpec spec) throws IOException, UnauthorizedException {
    return this.get(
        substringAfterLast(
            new HttpResponseAsResponse(
                httpClient.execute(
                    new HttpRequestWithSession(
                        session, 
                        new HttpPut(
                            new NonCheckedUriBuilder(
                                session.baseUrl()
                                    .toString()
                                    .concat("/issue")
                            ).setParameter("project", project().id())
                                .addParameters(spec.asQueryParams())
                                .build()
                        )
                    )
                )
            ).asHttpResponse()
                .getFirstHeader("Location")
                .getValue(),
            "/"
        )
    ).get();
  }
}
