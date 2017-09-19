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

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.llorllale.youtrack.api.response.HttpResponseAsResponse;
import org.llorllale.youtrack.api.response.Response;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.NonCheckedUriBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

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
  public TimeTrackEntry create(EntrySpec spec) throws IOException, UnauthorizedException {
    final URI uri = new NonCheckedUriBuilder(
        session.baseUrl()
            .toString()
            .concat("/issue/")
            .concat(issue.id())
            .concat("/timetracking/workitem")
    ).build();
    final HttpPost post = new HttpPost(uri);
    session.cookies().forEach(post::addHeader);
    post.setEntity(spec.asHttpEntity());
    final Response response = new HttpResponseAsResponse(httpClient.execute(post));
    response.payload(); //TODO there must be a better way/design to trigger validation...
    final String url = response.rawResponse()
        .getHeaders("Location")[0]  //expected
        .getValue();
    return this.get(url.substring(url.lastIndexOf('/') + 1))
        .get();
  }

  @Override
  public Optional<TimeTrackEntry> get(String id) throws IOException, UnauthorizedException {
    throw new UnsupportedOperationException("Not supported yet."); //TODO implement
  }
}
