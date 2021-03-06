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
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.llorllale.youtrack.api.session.Login;

import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Default implementation of {@link Projects}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
final class DefaultProjects implements Projects {
  private static final String PROJECT_PATH = "/admin/project/";
  private final YouTrack youtrack;
  private final Login login;
  private final Supplier<CloseableHttpClient> httpClient;

  /**
   * Primary ctor.
   * @param youtrack the parent {@link YouTrack}
   * @param login the user's {@link Login}
   * @param httpClient the {@link HttpClient} to use
   * @since 0.4.0
   */
  DefaultProjects(YouTrack youtrack, Login login, Supplier<CloseableHttpClient> httpClient) {
    this.youtrack = youtrack;
    this.login = login;
    this.httpClient = httpClient;
  }

  @Override
  public Stream<Project> stream() throws IOException, UnauthorizedException {
    return new StreamOf<>(
      new MappedCollection<>(
        xml -> new XmlProject(this.youtrack, this.login, xml, this.httpClient),
        new XmlsOf(
          "/projects/project",
          new HttpResponseAsResponse(
            this.httpClient.get().execute(
              new Authenticated(
                this.login.session(), 
                new HttpGet(
                  this.login.session().baseUrl().toString().concat("/project/all")
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
      xml -> new XmlProject(this.youtrack, this.login, xml, this.httpClient),
      new XmlsOf(
        "/project",
        new HttpResponseAsResponse(
          this.httpClient.get().execute(
            new Authenticated(
              this.login.session(), 
              new HttpGet(
                this.login.session().baseUrl().toString()
                  .concat(PROJECT_PATH)
                  .concat(id)
              )
            )
          )
        )
      )
    ).stream().findAny();
  }

  @Override
  public Project create(
    String id, String name, User leader
  ) throws IOException, UnauthorizedException {
    return this.get(
      new SubstringAfterLast(
        new HttpResponseAsResponse(
          this.httpClient.get().execute(
            new Authenticated(
              this.login.session(),
              new HttpPut(
                new UncheckedUriBuilder(
                  this.login.session().baseUrl().toString()
                    .concat(PROJECT_PATH).concat(id)
                ).param("projectName", name)
                  .param("startingNumber", "1")
                  .param("projectLeadLogin", leader.loginName())
                  .build()
              )
            )
          )
        ).httpResponse().getFirstHeader("Location").getValue(),
        "/"
      ).get()
    ).get();
  }
}
