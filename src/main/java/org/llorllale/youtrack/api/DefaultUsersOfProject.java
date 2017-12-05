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

package org.llorllale.youtrack.api;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.HttpEntityAsJaxb;
import org.llorllale.youtrack.api.util.HttpRequestWithSession;
import org.llorllale.youtrack.api.util.MappedCollection;
import org.llorllale.youtrack.api.util.Mapping;
import org.llorllale.youtrack.api.util.StreamOf;
import org.llorllale.youtrack.api.util.response.HttpResponseAsResponse;

/**
 * Default impl of {@link UsersOfProject}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.9.0
 */
class DefaultUsersOfProject implements UsersOfProject {
  private final Project project;
  private final Session session;
  private final org.llorllale.youtrack.api.jaxb.Project jaxb;

  /**
   * Primary ctor.
   * 
   * @param project the {@link Project} in scope
   * @param session the users's {@link Session}
   * @param jaxb the jaxb object
   * @since 0.9.0
   */
  DefaultUsersOfProject(
      Project project, 
      Session session, 
      org.llorllale.youtrack.api.jaxb.Project jaxb
  ) {
    this.project = project;
    this.session = session;
    this.jaxb = jaxb;
  }

  @Override
  public Project project() {
    return this.project;
  }

  @Override
  public User user(String login) throws IOException, UnauthorizedException {
    return new XmlUser(
        new Mapping<>(
            () -> new HttpResponseAsResponse(
                HttpClients.createDefault().execute(
                    new HttpRequestWithSession(
                        this.session, 
                        new HttpGet(
                            this.session.baseUrl().toString().concat("/user/").concat(login)
                        )
                    )
                )
            ),
            resp -> new HttpEntityAsJaxb<>(org.llorllale.youtrack.api.jaxb.User.class)
                .apply(resp.httpResponse().getEntity())
        ).get()
    );
  }

  @Override
  public Stream<User> assignees() throws IOException, UnauthorizedException {
    return new StreamOf<>(
        new MappedCollection<>(
            this.jaxb.getAssigneesLogin().getSub().stream()
                .map(s -> s.getValue())
                .collect(Collectors.toList()),
            this::user
        )
    );
  }
}
