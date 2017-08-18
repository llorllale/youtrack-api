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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.llorllale.youtrack.api.response.HttpResponseAsResponse;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.NonCheckedUriBuilder;

import java.io.IOException;
import java.net.URI;

/**
 * Creates a new comment on an {@link Issue}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.2.0
 */
public class CreateComment {
  private static final String RESOURCE = "/issue/ISSUE_ID/execute";

  private final Session session;
  private final HttpClient httpClient;
  private String text;
  private String issueId;

  /**
   * Primary ctor.
   * @param session the user's session
   * @param httpClient the {@link HttpClient} to use
   * @since 0.2.0
   */
  public CreateComment(Session session, HttpClient httpClient) {
    this.session = session;
    this.httpClient = httpClient;
  }

  /**
   * Uses the {@link HttpClients#createDefault() default http client}.
   * @param session the user's session
   * @since 0.2.0
   * @see #CreateComment(org.llorllale.youtrack.api.session.Session, 
   *     org.apache.http.client.HttpClient) 
   */
  public CreateComment(Session session) {
    this(session, HttpClients.createDefault());
  }

  /**
   * Specifies the ID of the {@link Issue} on which the comment will be created.
   * @param issueId the {@link Issue#id() issue's ID}
   * @return this object
   * @since 0.2.0
   */
  public CreateComment forIssueId(String issueId) {
    this.issueId = issueId;
    return this;
  }

  /**
   * The text to be added as comment.
   * @param text the text to be added as comment
   * @return this object
   * @since 0.2.0
   */
  public CreateComment withText(String text) {
    this.text = text;
    return this;
  }

  /**
   * Creates the comment in the remote YouTrack API.
   * @throws IOException if YouTrack is unreachable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to access the
   *     resource
   * @since 0.2.0
   */
  public void create() throws IOException, UnauthorizedException {
    final URI uri = new NonCheckedUriBuilder(
        session.baseUrl()
            .toString()
            .concat(RESOURCE.replaceAll("ISSUE_ID", issueId))
    ).build();
    final HttpPost post = new HttpPost(uri);
    session.cookies().stream().forEach(post::addHeader);
    post.setEntity(
        new StringEntity(
            "comment=".concat(text), 
            ContentType.APPLICATION_FORM_URLENCODED
        )
    );
    new HttpResponseAsResponse(httpClient.execute(post)).payload();
  }
}
