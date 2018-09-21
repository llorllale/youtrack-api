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
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.llorllale.youtrack.api.session.Login;

import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Default implementation of {@link Comments}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
final class DefaultComments implements Comments {
  private static final String BASE_PATH = "/issue/";

  private final Login login;
  private final Issue issue;
  private final Supplier<CloseableHttpClient> httpClient;

  /**
   * Ctor.
   * @param login the user's {@link Login}
   * @param issue the {@link Issue} on which the comments are attached to
   * @param httpClient the {@link HttpClient} to use
   * @since 0.4.0
   */
  DefaultComments(Login login, Issue issue, Supplier<CloseableHttpClient> httpClient) {
    this.login = login;
    this.issue = issue;
    this.httpClient = httpClient;
  }

  @Override
  public Stream<Comment> stream() throws IOException, UnauthorizedException {
    return new StreamOf<>(
      new MappedCollection<>(
        xml -> new XmlComment(this.issue(), this.login, xml, this.httpClient),
        new XmlsOf(
          "//comment",
          new HttpResponseAsResponse(
            this.httpClient.get().execute(
              new Authenticated(
                this.login.session(), 
                new HttpGet(
                  this.login.session().baseUrl().toString()
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
      this.httpClient.get().execute(
        new Authenticated(
          this.login.session(),
          new Loaded(
            new StringEntity(
              "comment=".concat(text), 
              ContentType.APPLICATION_FORM_URLENCODED
            ),
            new HttpPost(
              this.login.session().baseUrl().toString()
                .concat(BASE_PATH)
                .concat(this.issue().id())
                .concat("/execute")
            )
          )
        )
      )
    ).httpResponse();
    return new DefaultComments(this.login, this.issue(), this.httpClient);
  }

  @Override
  public Issue issue() {
    return this.issue;
  }
}
