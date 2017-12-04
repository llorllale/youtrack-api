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
import java.util.stream.Stream;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.llorllale.youtrack.api.jaxb.WorkItems;

import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.HttpRequestWithEntity;
import org.llorllale.youtrack.api.util.HttpRequestWithSession;
import org.llorllale.youtrack.api.util.OptionalMapping;
import org.llorllale.youtrack.api.util.ResponseAs;
import org.llorllale.youtrack.api.util.response.HttpResponseAsResponse;

/**
 * Default implementation of {@link IssueTimeTracking}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
class DefaultIssueTimeTracking implements IssueTimeTracking {
  private static final String PATH_TEMPLATE = "/issue/%s/timetracking/workitem";
  private final Session session;
  private final Issue issue;
  private final HttpClient httpClient;

  /**
   * Primary ctor.
   * 
   * @param session the user's {@link Session}
   * @param issue the {@link Issue} to which this {@link IssueTimeTracking} is attached to
   * @param httpClient the {@link HttpClient} to use
   * @since 0.4.0
   */
  DefaultIssueTimeTracking(Session session, Issue issue, HttpClient httpClient) {
    this.session = session;
    this.issue = issue;
    this.httpClient = httpClient;
  }

  /**
   * Uses the {@link HttpClients#createDefault() default} http client.
   * 
   * @param session the user's {@link Session}
   * @param issue the {@link Issue} to which this {@link IssueTimeTracking} is attached to
   * @see #DefaultTimeTracking(org.llorllale.youtrack.api.session.Session, 
   *     org.llorllale.youtrack.api.Issue, org.apache.http.client.HttpClient) 
   * @since 0.4.0
   */
  DefaultIssueTimeTracking(Session session, Issue issue) {
    this(session, issue, HttpClients.createDefault());
  }

  @Override
  public Stream<TimeTrackEntry> stream() throws IOException, UnauthorizedException {
    return new OptionalMapping<WorkItems, Stream<TimeTrackEntry>>(
        () -> new ResponseAs<>(
            WorkItems.class,
            new HttpResponseAsResponse(
                this.httpClient.execute(
                    new HttpRequestWithSession(
                        this.session, 
                        new HttpGet(
                            this.session.baseUrl().toString()
                                .concat(String.format(PATH_TEMPLATE, this.issue.id()))
                        )
                    )
                )
            )
        ).get(),
        item -> item.getWorkItem().stream().map(i -> new XmlTimeTrackEntry(this.issue, i))
    ).get().get();
  }

  @Override
  public IssueTimeTracking create(EntrySpec spec) throws IOException, UnauthorizedException {
    new HttpResponseAsResponse(
        this.httpClient.execute(
            new HttpRequestWithSession(
                this.session, 
                new HttpRequestWithEntity(
                    spec.asHttpEntity(),
                    new HttpPost(
                        this.session.baseUrl().toString()
                            .concat(String.format(PATH_TEMPLATE, this.issue.id()))
                    )
                )
            )
        )
    ).httpResponse();

    return new DefaultIssueTimeTracking(this.session, this.issue);
  }
}
