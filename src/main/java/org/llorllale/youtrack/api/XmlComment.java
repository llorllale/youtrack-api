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

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * JAXB implementation of {@link Comment}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.2.0
 */
final class XmlComment implements Comment {
  private static final String PATH_TEMPLATE = "/issue/%s/comment/%s";
  private final String id;
  private final long creationDate;
  private final String text;
  private final Issue issue;
  private final Session session;

  /**
   * Primary ctor.
   * 
   * @param id the comment's id
   * @param creationDate the date when the comment was created (epoch time)
   * @param text the comment's text
   * @param issue the issue to which the comment is attached
   * @param session the user's session
   * @since 0.9.0
   */
  @SuppressWarnings("checkstyle:ParameterNumber")
  XmlComment(String id, long creationDate, String text, Issue issue, Session session) {
    this.id = id;
    this.creationDate = creationDate;
    this.text = text;
    this.issue = issue;
    this.session = session;
  }

  /**
   * Extracts {@code id}, {@code creationDate}, and {@code text} from {@code jaxbComment}.
   * 
   * @param issue the {@link Issue} to which this comment is attached
   * @param session the user's session
   * @param xml comment's XML object received from YouTrack
   * @throws UncheckedException from {@link XmlOf#textOf(java.lang.String)}
   * @since 1.0.0
   */
  XmlComment(Issue issue, Session session, Xml xml) throws UncheckedException {
    this(xml.textOf("//@id").get(), 
        Long.parseLong(xml.textOf("//@created").get()), 
        xml.textOf("//@text").get(), 
        issue, 
        session
    );
  }

  @Override
  public Issue issue() {
    return this.issue;
  }

  @Override
  public String id() {
    return this.id;
  }

  @Override
  public Instant creationDate() {
    return Instant.ofEpochMilli(this.creationDate);
  }

  @Override
  public String text() {
    return this.text;
  }

  @Override
  public Comment update(String txt) throws IOException, UnauthorizedException {
    new HttpResponseAsResponse(
        HttpClients.createDefault().execute(
            new HttpRequestWithSession(
                this.session, 
                new HttpRequestWithEntity(
                    new StringEntity(
                        String.format("{\"text\": \"%s\"}", txt), 
                        ContentType.APPLICATION_JSON
                    ),
                    new HttpPut(
                        new UncheckedUriBuilder(
                            this.session.baseUrl().toString()
                                .concat(
                                    String.format(PATH_TEMPLATE, this.issue().id(), this.id())
                                )
                        ).build()
                    )
                )
            )
        )
    ).httpResponse();

    return new XmlComment(this.id, this.creationDate, txt, this.issue(), this.session);
  }

  @Override
  public Issue delete() throws IOException, UnauthorizedException {
    new HttpResponseAsResponse(
        HttpClients.createDefault().execute(
            new HttpRequestWithSession(
                this.session, 
                new HttpDelete(
                    this.session.baseUrl().toString()
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
