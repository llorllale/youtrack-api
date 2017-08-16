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

package org.llorllale.youtrack.api.issues;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.llorllale.youtrack.api.response.HttpResponseAsResponse;
import org.llorllale.youtrack.api.response.Response;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.HttpEntityAsJaxb;
import org.llorllale.youtrack.api.util.NonCheckedUriBuilder;

import java.io.IOException;
import java.util.Optional;

/**
 * Queries the remote YouTrack API for an {@link Issue}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
public class IssueWithId {
  private static final String ISSUE_RESOURCE = "/issue/";

  private final String issueId;
  private final Session session;
  private final HttpClient httpClient;

  /**
   * Primary constructor.
   * @param issueId the issue's ID
   * @param session the login session
   * @param httpClient the {@link HttpClient} to use
   * @since 0.1.0
   */
  public IssueWithId(String issueId, Session session, HttpClient httpClient) {
    this.issueId = issueId;
    this.session = session;
    this.httpClient = httpClient;
  }

  /**
   * Assumes the {@link HttpClients#createDefault() default} httpclient.
   * @param issueId the issue's ID
   * @param session the login session
   * @since 0.1.0
   * @see #IssueWithId(String, Session, HttpClient) 
   */
  public IssueWithId(String issueId, Session session) {
    this(issueId, session, HttpClients.createDefault());
  }

  /**
   * Queries the remote YouTrack API and retrieves the {@link Issue}.
   * @return the YouTrack {@link Issue}
   * @throws IOException if the YouTrack API is unreachable
   * @throws UnauthorizedException if the user's {@link Session} is not allowed to access the 
   *     resource
   * @since 0.1.0
   */
  public Optional<Issue> query() throws IOException, UnauthorizedException {
    final NonCheckedUriBuilder ub = new NonCheckedUriBuilder(
        session.baseUrl()
            .toString()
            .concat(ISSUE_RESOURCE)
            .concat(issueId)
    );
    final HttpGet get = new HttpGet(ub.build());
    get.addHeader("Accept", "application/xml");
    session.cookies().forEach(get::addHeader);
    final Response response = new HttpResponseAsResponse(httpClient.execute(get));
    return response.payload()
        .map(new HttpEntityAsJaxb<>(org.llorllale.youtrack.api.issues.jaxb.Issue.class))
        .map(XmlIssue::new);
  }
}
