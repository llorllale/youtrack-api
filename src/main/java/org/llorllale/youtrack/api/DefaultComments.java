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
import java.util.stream.Stream;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Default implementation of {@link Comments}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
final class DefaultComments implements Comments {
  private static final String BASE_PATH = "/issue/";

  private final Session session;
  private final Issue issue;
  private final HttpClient httpClient;

  /**
   * Primary ctor.
   * 
   * @param session the user's {@link Session}
   * @param issue the {@link Issue} on which the comments are attached to
   * @param httpClient the {@link HttpClient} to use
   * @since 0.4.0
   */
  DefaultComments(Session session, Issue issue, HttpClient httpClient) {
    this.session = session;
    this.issue = issue;
    this.httpClient = httpClient;
  }

  /**
   * Uses the {@link HttpClients#createDefault() default} http client.
   * 
   * @param session the user's {@link Session}
   * @param issue the {@link Issue} on which the comments are attached to
   * @see #DefaultComments(org.llorllale.youtrack.api.session.Session, 
   *     org.llorllale.youtrack.api.Issue, org.apache.http.client.HttpClient) 
   * @since 0.4.0
   */
  DefaultComments(Session session, Issue issue) {
    this(session, issue, HttpClients.createDefault());
  }

  @Override
  public Stream<Comment> stream() throws IOException, UnauthorizedException {
    return new StreamOf<>(
      new MappedCollection<>(
        xml -> new XmlComment(this.issue(), this.session, xml),
        new XmlsOf(
          "//comment",
          new HttpResponseAsResponse(
            this.httpClient.execute(
              new HttpRequestWithSession(
                this.session, 
                new HttpGet(
                  this.session.baseUrl().toString()
                    .concat(BASE_PATH)
                    .concat(this.issue().id())
                    .concat("/comment")
                )
              )
            )
          )
        )
      )
    );
  }

  @Override
  public Comments post(String text) throws IOException, UnauthorizedException {
    new HttpResponseAsResponse(
        this.httpClient.execute(
            new HttpRequestWithSession(
                this.session, 
                new HttpRequestWithEntity(
                    new StringEntity(
                        "comment=".concat(text), 
                        ContentType.APPLICATION_FORM_URLENCODED
                    ),
                    new HttpPost(
                        this.session.baseUrl().toString()
                            .concat(BASE_PATH)
                            .concat(this.issue().id())
                            .concat("/execute")
                    )
                )
            )
        )
    ).httpResponse();

    return new DefaultComments(this.session, this.issue());
  }

  @Override
  public Issue issue() {
    return this.issue;
  }
}
