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
import org.apache.http.impl.client.HttpClients;

import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Default impl of {@link ProjectTimeTracking}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 */
final class DefaultProjectTimeTracking implements ProjectTimeTracking {
  private static final String PATH_TEMPLATE = "/admin/project/%s/timetracking";
  private final Project project;
  private final Session session;
  private final HttpClient httpClient;

  /**
   * Primary ctor.
   * 
   * @param project the parent {@link Project}
   * @param session the user's {@link Session}
   * @param httpClient the {@link HttpClient} to use
   * @since 1.0.0
   */
  DefaultProjectTimeTracking(Project project, Session session, HttpClient httpClient) {
    this.project = project;
    this.session = session;
    this.httpClient = httpClient;
  }

  /**
   * Ctor.
   * 
   * @param project the parent {@link Project}
   * @param session the user's {@link Session}
   * @since 0.8.0
   */
  DefaultProjectTimeTracking(Project project, Session session) {
    this(project, session, HttpClients.createDefault());
  }
  
  @Override
  public Project project() {
    return this.project;
  }

  @Override
  public boolean enabled() throws IOException, UnauthorizedException {
    final Xml settings = new XmlsOf(
        "/settings",
        new HttpResponseAsResponse(
            this.httpClient.execute(
                new HttpRequestWithSession(
                    this.session, 
                    new HttpGet(
                        this.session.baseUrl().toString()
                            .concat(String.format(PATH_TEMPLATE, this.project().id()))
                    )
                )
            )
        )
    ).stream().findAny().get();

    return Boolean.parseBoolean(settings.textOf("@enabled").get())
        && settings.child("estimation").isPresent()
        && settings.child("spentTime").isPresent();
  }

  @Override
  public Stream<TimeTrackEntryType> types() throws IOException, UnauthorizedException {
    return new StreamOf<>(
        new MappedCollection<>(
            XmlTimeTrackEntryType::new,
            new XmlsOf(
                "/workItemTypes/workType",
                new HttpResponseAsResponse(
                    this.httpClient.execute(
                        new HttpRequestWithSession(
                            this.session, 
                            new HttpGet(
                                this.session.baseUrl().toString()
                                    .concat(String.format(PATH_TEMPLATE, this.project().id()))
                                    .concat("/worktype")
                            )
                        )
                    )
                )
            )
        )
    );
  }
}
