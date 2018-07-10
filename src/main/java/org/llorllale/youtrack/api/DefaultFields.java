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
import org.llorllale.youtrack.api.session.Login;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Default impl of {@link Fields}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 */
final class DefaultFields implements Fields {
  private final Login login;
  private final Project project;
  private final HttpClient httpClient;

  /**
   * Ctor.
   * @param session the user's {@link Login}
   * @param project the parent {@link Project}
   * @param httpClient the {@link HttpClient} to use
   */
  DefaultFields(Login session, Project project, HttpClient httpClient) {
    this.login = session;
    this.project = project;
    this.httpClient = httpClient;
  }

  @Override
  public Project project() {
    return this.project;
  }

  @Override
  public Stream<ProjectField> stream() throws IOException, UnauthorizedException {
    return new StreamOf<>(
      new MappedCollection<>(
        new UncheckedIoFunction<>(
          x -> new XmlProjectField(x, this.project(), this.login, this.httpClient)
        ),
        new XmlsOf(
          "/projectCustomFieldRefs/projectCustomField",
          new HttpResponseAsResponse(
            this.httpClient.execute(
              new HttpRequestWithSession(
                this.login.session(),
                new HttpGet(
                  this.login.session().baseUrl().toString()
                    .concat("/admin/project/")
                    .concat(this.project().id())
                    .concat("/customfield")
                )
              )
            )
          )
        )
      )
    );
  }
}
