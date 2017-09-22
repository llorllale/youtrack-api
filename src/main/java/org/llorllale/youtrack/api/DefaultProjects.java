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

import static java.util.stream.Collectors.toList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.HttpEntityAsJaxb;
import org.llorllale.youtrack.api.util.HttpRequestWithSession;
import org.llorllale.youtrack.api.util.NonCheckedUriBuilder;
import org.llorllale.youtrack.api.util.response.HttpResponseAsResponse;

import java.io.IOException;
import java.util.List;

/**
 * Default implementation of {@link Projects}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
class DefaultProjects implements Projects {
  private final Session session;
  private final HttpClient httpClient;

  /**
   * Primary ctor.
   * @param session the user's {@link Session}
   * @param httpClient the {@link HttpClient} to use
   * @since 0.4.0
   */
  DefaultProjects(Session session, HttpClient httpClient) {
    this.session = session;
    this.httpClient = httpClient;
  }

  /**
   * Uses the {@link HttpClients#createDefault() default} http client.
   * @param session the user's {@link Session}
   * @since 0.4.0
   * @see #DefaultProjects(org.llorllale.youtrack.api.session.Session, 
   *     org.apache.http.client.HttpClient) 
   */
  DefaultProjects(Session session) {
    this(session, HttpClients.createDefault());
  }

  @Override
  public List<Project> all() throws IOException, UnauthorizedException {
    return new HttpEntityAsJaxb<>(org.llorllale.youtrack.api.jaxb.Projects.class)
        .andThen(
            projects -> projects.getProject()
                .stream()
                .map(p -> new XmlProject(session, p))
        ).apply(
            new HttpResponseAsResponse(
                httpClient.execute(
                    new HttpRequestWithSession(
                        session, 
                        new HttpGet(
                            new NonCheckedUriBuilder(
                                session.baseUrl()
                                    .toString()
                                    .concat("/project/all")
                            ).build()
                        )
                    )
                )
            ).asHttpResponse().getEntity()
        ).collect(toList());
  }
}
