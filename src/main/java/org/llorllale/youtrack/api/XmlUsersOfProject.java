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
import org.llorllale.youtrack.api.session.Login;

import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Default impl of {@link UsersOfProject}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.9.0
 */
final class XmlUsersOfProject implements UsersOfProject {
  private final Project project;
  private final Login login;
  private final Xml xml;
  private final Supplier<HttpClient> httpClient;

  /**
   * Primary ctor.
   * 
   * @param project the {@link Project} in scope
   * @param login the users's {@link Login}
   * @param xml the xml object received from YouTrack for this {@link #project() project}
   * @param httpClient the {@link HttpClient} to use
   * @since 0.9.0
   */
  XmlUsersOfProject(Project project, Login login, Xml xml, Supplier<HttpClient> httpClient) {
    this.project = project;
    this.login = login;
    this.xml = xml;
    this.httpClient = httpClient;
  }

  @Override
  public Project project() {
    return this.project;
  }

  @Override
  public User user(String userLogin) throws IOException, UnauthorizedException {
    return new XmlUser(
      new XmlsOf(
        "/user",
        new HttpResponseAsResponse(
          this.httpClient.get().execute(
            new HttpRequestWithSession(
              this.login.session(),
              new HttpGet(
                this.login.session().baseUrl().toString().concat("/user/").concat(userLogin)
              )
            )
          )
        )
      ).stream().findAny().get()
    );
  }

  @Override
  public Stream<User> assignees() throws IOException, UnauthorizedException {
    return new StreamOf<>(
      new MappedCollection<>(
        () -> x -> this.user(x.textOf("@value").get()),
        this.xml.children("//assigneesLogin/sub")
      )
    );
  }
}
