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

import static java.util.stream.Collectors.toList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.HttpEntityAsJaxb;
import org.llorllale.youtrack.api.util.HttpRequestWithEntity;
import org.llorllale.youtrack.api.util.HttpUriRequestWithSession;
import org.llorllale.youtrack.api.util.NonCheckedUriBuilder;
import org.llorllale.youtrack.api.util.response.HttpResponseAsResponse;

import java.io.IOException;
import java.util.List;


/**
 * Default implementation of {@link TimeTracking}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
class DefaultTimeTracking implements TimeTracking {
  private final Session session;
  private final Issue issue;
  private final HttpClient httpClient;

  /**
   * Primary ctor.
   * @param session the user's {@link Session}
   * @param issue the {@link Issue} to which this {@link TimeTracking} is attached to
   * @param httpClient the {@link HttpClient} to use
   * @since 0.4.0
   */
  DefaultTimeTracking(Session session, Issue issue, HttpClient httpClient) {
    this.session = session;
    this.issue = issue;
    this.httpClient = httpClient;
  }

  /**
   * Uses the {@link HttpClients#createDefault() default} http client.
   * @param session the user's {@link Session}
   * @param issue the {@link Issue} to which this {@link TimeTracking} is attached to
   * @since 0.4.0
   * @see #DefaultTimeTracking(org.llorllale.youtrack.api.session.Session, 
   *     org.llorllale.youtrack.api.Issue, org.apache.http.client.HttpClient) 
   */
  DefaultTimeTracking(Session session, Issue issue) {
    this(session, issue, HttpClients.createDefault());
  }

  @Override
  public List<TimeTrackEntry> all() throws IOException, UnauthorizedException {
    return new HttpEntityAsJaxb<>(org.llorllale.youtrack.api.jaxb.WorkItems.class)
        .andThen(
            items -> items.getWorkItem()
                .stream()
                .map(i -> new XmlTimeTrackEntry(issue, i))
        ).apply(
            new HttpResponseAsResponse(
                httpClient.execute(
                    new HttpUriRequestWithSession(
                        session, 
                        new HttpGet(
                            new NonCheckedUriBuilder(
                                session.baseUrl()
                                    .toString()
                                    .concat("/issue/")
                                    .concat(issue.id())
                                    .concat("/timetracking/workitem")
                            ).build()
                        )
                    )
                )
            ).asHttpResponse().getEntity()
        ).collect(toList());
  }

  @Override
  public TimeTracking create(EntrySpec spec) throws IOException, UnauthorizedException {
    new HttpResponseAsResponse(
        httpClient.execute(
            new HttpUriRequestWithSession(
                session, 
                new HttpRequestWithEntity(
                    spec.asHttpEntity(),
                    new HttpPost(
                        new NonCheckedUriBuilder(
                            session.baseUrl()
                                .toString()
                                .concat("/issue/")
                                .concat(issue.id())
                                .concat("/timetracking/workitem")
                        ).build()
                    )
                )
            )
        )
    ).asHttpResponse();

    return this;
  }
}
