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
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Default implementation of {@link Projects}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
final class DefaultProjects implements Projects {
  private final YouTrack youtrack;
  private final Session session;
  private final HttpClient httpClient;

  /**
   * Primary ctor.
   * 
   * @param youtrack the parent {@link YouTrack}
   * @param session the user's {@link Session}
   * @param httpClient the {@link HttpClient} to use
   * @since 0.4.0
   */
  DefaultProjects(YouTrack youtrack, Session session, HttpClient httpClient) {
    this.youtrack = youtrack;
    this.session = session;
    this.httpClient = httpClient;
  }

  /**
   * Uses the {@link HttpClients#createDefault() default} http client.
   * 
   * @param youtrack the parent {@link YouTrack}
   * @param session the user's {@link Session}
   * @see #DefaultProjects(org.llorllale.youtrack.api.session.Session, 
   *     org.apache.http.client.HttpClient) 
   * @since 0.4.0
   */
  DefaultProjects(YouTrack youtrack, Session session) {
    this(youtrack, session, HttpClients.createDefault());
  }

  @Override
  public Stream<Project> stream() throws IOException, UnauthorizedException {
    return new StreamOf<>(
      new MappedCollection<>(
        xml -> new XmlProject(this.youtrack, this.session, xml),
        new XmlsOf(
          "/projects/project",
          new HttpResponseAsResponse(
            this.httpClient.execute(
              new HttpRequestWithSession(
                this.session, 
                new HttpGet(
                  this.session.baseUrl().toString().concat("/project/all")
                )
              )
            )
          )
        )
      )
    );
  }

  @Override
  public Optional<Project> get(String id) throws IOException, UnauthorizedException {
    return new MappedCollection<Xml, Project>(
      xml -> new XmlProject(this.youtrack, this.session, xml),
      new XmlsOf(
        "/project",
        new HttpResponseAsResponse(
          this.httpClient.execute(
            new HttpRequestWithSession(
              this.session, 
                new HttpGet(
                  this.session.baseUrl().toString()
                    .concat("/admin/project/")
                    .concat(id)
                )
            )
          )
        )
      )
    ).stream().findAny();
  }
}
