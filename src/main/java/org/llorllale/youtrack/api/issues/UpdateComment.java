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
import org.apache.http.client.methods.HttpPut;
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
 * Updates the text content of a {@link Comment}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.2.0
 */
public class UpdateComment {
  private static final String RESOURCE = "/issue/ISSUE_ID/comment/";
  private final String commentId;
  private final String issueId;
  private final String newText;
  private final Session session;
  private final HttpClient httpClient;

  /**
   * Primary constructor.
   * @param comment the {@link Comment} to update
   * @param newText the new {@link Comment#text() text}
   * @param session the user's {@link Session}
   * @param httpClient the {@link HttpClient http client} to use
   */
  public UpdateComment(Comment comment, String newText, Session session, HttpClient httpClient) {
    this.commentId = comment.id();
    this.issueId = comment.issueId();
    this.newText = newText;
    this.session = session;
    this.httpClient = httpClient;
  }

  /**
   * Uses the {@link HttpClients#createDefault() default http client}.
   * @param comment the {@link Comment} to update
   * @param newText the new {@link Comment#text() text}
   * @param session the user's {@link Session}
   * @since 0.2.0
   * @see #UpdateComment(org.llorllale.youtrack.api.issues.Comment, 
   *     java.lang.String, org.llorllale.youtrack.api.session.Session, 
   *     org.apache.http.client.HttpClient) 
   */
  public UpdateComment(Comment comment, String newText, Session session) {
    this(comment, newText, session, HttpClients.createDefault());
  }

  /**
   * Updates the {@link Comment comment's} text content.
   * @throws IOException if the remote YouTrack API is unreachable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to modify this
   *     resource
   */
  public void update() throws IOException, UnauthorizedException {
    final URI uri = new NonCheckedUriBuilder(
        session.baseUrl()
            .toString()
            .concat(RESOURCE.replace("ISSUE_ID", issueId))
            .concat(commentId)
    ).build();
    final HttpPut put = new HttpPut(uri);
    session.cookies().forEach(put::addHeader);
    put.setEntity(
        new StringEntity(
            String.format("{\"text\": \"%s\"}", newText), 
            ContentType.APPLICATION_FORM_URLENCODED
        )
    );
    new HttpResponseAsResponse(httpClient.execute(put)).payload();
  }
}
