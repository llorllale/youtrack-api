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

package org.llorllale.youtrack.api.projects;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;

import java.io.IOException;
import java.util.Optional;

/**
 * Queries the remote YouTrack API for a {@link Project} with the given ID.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.2.0
 */
public class ProjectWithId {
  private final String projectId;
  private final Session session;
  private final HttpClient httpClient;

  /**
   * Primary constructor.
   * @param projectId the {@link Project#id() project's id}
   * @param session the user's {@link Session}
   * @param httpClient the {@link HttpClient} to use
   * @since 0.2.0
   */
  public ProjectWithId(String projectId, Session session, HttpClient httpClient) {
    this.projectId = projectId;
    this.session = session;
    this.httpClient = httpClient;
  }

  /**
   * Uses the {@link HttpClients#createDefault() default} http client.
   * @param projectId the {@link Project#id() project's id}
   * @param session the user's {@link Session}
   * @since 0.2.0
   */
  public ProjectWithId(String projectId, Session session) {
    this(projectId, session, HttpClients.createDefault());
  }

  /**
   * Produces the {@link Project} with the given {@code projectId} returned by the YouTrack API, if
   * it exists.
   * @return the {@link Project} with the given {@code projectId} returned by the YouTrack API, if
   *     it exists
   * @throws IOException if YouTrack is unreachable
   * @throws UnauthorizedException if the user's {@link Session} is not allowed to access this 
   *     resource
   * @since 0.2.0
   */
  public Optional<Project> query() throws IOException, UnauthorizedException {
    throw new UnsupportedOperationException();
  }
}
