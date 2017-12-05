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

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClients;

import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.EmptyIfError;
import org.llorllale.youtrack.api.util.HttpEntityAsJaxb;
import org.llorllale.youtrack.api.util.HttpRequestWithSession;
import org.llorllale.youtrack.api.util.Mapping;
import org.llorllale.youtrack.api.util.OptionalMapping;
import org.llorllale.youtrack.api.util.PageUri;
import org.llorllale.youtrack.api.util.Pagination;
import org.llorllale.youtrack.api.util.StreamOf;
import org.llorllale.youtrack.api.util.UncheckedUriBuilder;
import org.llorllale.youtrack.api.util.XmlStringAsJaxb;
import org.llorllale.youtrack.api.util.response.HttpResponseAsResponse;

/**
 * Default implementation of {@link Issues}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
class DefaultIssues implements Issues {
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
   * @see #DefaultIssues(org.llorllale.youtrack.api.v2.Project, 
   *   org.llorllale.youtrack.api.session.Session, org.apache.http.client.HttpClient) 
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
            new PageUri(
                pageSize,
                n -> new HttpRequestWithSession(
                    this.session, 
                    new HttpGet(
                        new UncheckedUriBuilder(
                            this.session.baseUrl().toString()
                                .concat("/issue/byproject/")
                                .concat(this.project().id())
                        ).setParameter("after", String.valueOf(n))
                            .build()
                    )
                )
            ),
            new HttpEntityAsJaxb<>(org.llorllale.youtrack.api.jaxb.Issues.class).andThen(
                issues -> 
                    issues.getIssue().stream()
                        .map(issue -> new XmlIssue(this.project(), this.session, issue))
                        .collect(Collectors.toList())
            )
        )
    );
  }

  @Override
  public Optional<Issue> get(String issueId) throws IOException, UnauthorizedException {
    return new Mapping<Optional<org.llorllale.youtrack.api.jaxb.Issue>, Optional<Issue>>(
        () -> new OptionalMapping<>(
            () -> Optional.ofNullable(
                new HttpResponseAsResponse(
                    this.httpClient.execute(
                        new HttpRequestWithSession(
                            this.session, 
                            new HttpGet(
                                this.session.baseUrl() 
                                    .toString()
                                    .concat("/issue/")
                                    .concat(issueId)
                            )
                        )
                    )
                ).httpResponse().getEntity()
            ),
            entity -> new EmptyIfError<>(
                new XmlStringAsJaxb<>(org.llorllale.youtrack.api.jaxb.Issue.class)
            ).apply(entity)
        ).get().get(),
        optional -> optional.map(i -> new XmlIssue(this.project(), this.session, i))
    ).get();
  }

  @Override
  public Issue create(IssueSpec spec) throws IOException, UnauthorizedException {
    return this.get(
        StringUtils.substringAfterLast(
            new HttpResponseAsResponse(
                this.httpClient.execute(
                    new HttpRequestWithSession(
                        this.session, 
                        new HttpPut(
                            new UncheckedUriBuilder(
                                this.session.baseUrl()
                                    .toString()
                                    .concat("/issue")
                            ).setParameter("project", this.project().id())
                                .addParameters(spec.nameValuePairs())
                                .build()
                        )
                    )
                )
            ).httpResponse().getFirstHeader("Location").getValue(),
            "/"
        )
    ).get().update().fields(spec.fields());
  }
}
