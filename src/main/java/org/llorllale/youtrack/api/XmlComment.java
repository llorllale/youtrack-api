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
import java.time.Instant;
import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.llorllale.youtrack.api.session.Login;

import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * JAXB implementation of {@link Comment}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.2.0
 */
final class XmlComment implements Comment {
  private static final String PATH_TEMPLATE = "/issue/%s/comment/%s";
  private final Issue issue;
  private final Login login;
  private final Xml xml;
  private final HttpClient http;

  /**
   * Extracts {@code id}, {@code creationDate}, and {@code text} from {@code jaxbComment}.
   * @param issue the {@link Issue} to which this comment is attached
   * @param login the user's session
   * @param xml comment's XML object received from YouTrack
   * @param client the Http client to use
   * @throws UncheckedException from {@link XmlOf#textOf(String)}
   * @since 1.1.0
   */
  XmlComment(Issue issue, Login login, Xml xml, HttpClient client) throws UncheckedException {
    this.issue = issue;
    this.login = login;
    this.xml = xml;
    this.http = client;
  }

  @Override
  public Issue issue() {
    return this.issue;
  }

  @Override
  public String id() {
    return this.xml.textOf("//@id").get();
  }

  @Override
  public Instant creationDate() {
    return Instant.ofEpochMilli(
      Long.parseLong(this.xml.textOf("//@created").get())
    );
  }

  @Override
  public String text() {
    return this.xml.textOf("//@text").get();
  }

  @Override
  public Comment update(String txt) throws IOException, UnauthorizedException {
    new HttpResponseAsResponse(
      this.http.execute(
        new HttpRequestWithSession(
          this.login.session(),
          new HttpRequestWithEntity(
            new StringEntity(
              String.format("{\"text\": \"%s\"}", txt),
              ContentType.APPLICATION_JSON
            ),
            new HttpPut(
              new UncheckedUriBuilder(
                this.login.session().baseUrl().toString()
                  .concat(
                    String.format(PATH_TEMPLATE, this.issue().id(), this.id())
                  )
              ).build()
            )
          )
        )
      )
    ).httpResponse();
    return new XmlComment(this.issue, this.login, this.xml, this.http);
  }

  @Override
  public Issue delete() throws IOException, UnauthorizedException {
    new HttpResponseAsResponse(
      this.http.execute(
        new HttpRequestWithSession(
          this.login.session(),
          new HttpDelete(
            this.login.session().baseUrl().toString()
              .concat(
                String.format(PATH_TEMPLATE, this.issue().id(), this.id())
              )
          )
        )
      )
    ).httpResponse();
    return this.issue();
  }
}
