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

import static java.util.stream.Collectors.toList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.llorllale.youtrack.api.response.HttpResponseAsResponse;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.HttpEntityAsJaxb;
import org.llorllale.youtrack.api.util.NonCheckedUriBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * Queries the remote YouTrack API for all {@link Comment comments} for a given {@link Issue issue}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.2.0
 */
public class CommentsForIssue {
  private static final String RESOURCE = "/issue/ISSUE_ID/comment";

  private final String issueId;
  private final Session session;
  private final HttpClient httpClient;

  /**
   * Primary constructor.
   * @param issueId the issue's {@link Issue#id() ID}
   * @param session the user's {@link Session session}
   * @param httpClient the {@link HttpClient} to use
   * @since 0.2.0
   */
  public CommentsForIssue(String issueId, Session session, HttpClient httpClient) {
    this.issueId = issueId;
    this.session = session;
    this.httpClient = httpClient;
  }

  /**
   * Uses the {@link HttpClients#createDefault() default} http client.
   * @param issueId the issue's {@link Issue#id() ID}
   * @param session the user's {@link Session session}
   * @since 0.2.0
   * @see #CommentsForIssue(java.lang.String, org.llorllale.youtrack.api.session.Session, 
   *     org.apache.http.client.HttpClient) 
   */
  public CommentsForIssue(String issueId, Session session) {
    this(issueId, session, HttpClients.createDefault());
  }

  /**
   * Returns all {@link Comment comments} created for the {@link Issue issue}.
   * @return all {@link Comment comments} created for the {@link Issue issue}
   * @throws IOException if YouTrack is unreachable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to access this
   *     resource
   * @since 0.2.0
   */
  public List<Comment> query() throws IOException, UnauthorizedException {
    final URI uri = new NonCheckedUriBuilder(
        session.baseUrl()
            .toString()
            .concat(RESOURCE.replace("ISSUE_ID", issueId))
    ).build();
    final HttpGet get = new HttpGet(uri);
    session.cookies().stream().forEach(get::addHeader);
    return new HttpResponseAsResponse(httpClient.execute(get)).payload()
        .map(new HttpEntityAsJaxb<>(org.llorllale.youtrack.api.issues.jaxb.Comments.class))
        .map(c -> c.getComment().stream())
        .get()
        .map(XmlComment::new)
        .collect(toList());
  }
}
