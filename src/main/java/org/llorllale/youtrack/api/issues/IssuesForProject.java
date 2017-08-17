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

package org.llorllale.youtrack.api.issues;

import static java.util.stream.Collectors.toList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.llorllale.youtrack.api.projects.Project;
import org.llorllale.youtrack.api.response.HttpResponseAsResponse;
import org.llorllale.youtrack.api.response.Response;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.HttpEntityAsJaxb;
import org.llorllale.youtrack.api.util.NonCheckedUriBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * Queries the remote YouTrack API for all {@link Issue issues} created for a given 
 * {@link Project project}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.2.0
 */
public class IssuesForProject {
  private static final String RESOURCE = "/issue/byproject/";

  private final String projectId;
  private final Session session;
  private final HttpClient httpClient;

  /**
   * Primary ctor.
   * @param projectId the project's ID
   * @param session the user's {@link Session}
   * @param httpClient the {@link HttpClient} to use
   * @since 0.2.0
   */
  public IssuesForProject(String projectId, Session session, HttpClient httpClient) {
    this.projectId = projectId;
    this.session = session;
    this.httpClient = httpClient;
  }

  /**
   * Uses the {@link HttpClients#createDefault() default http client}.
   * @param projectId the project's ID
   * @param session the user's {@link Session}
   * @since 0.2.0
   * @see #IssuesForProject(java.lang.String, org.llorllale.youtrack.api.session.Session, 
   *     org.apache.http.client.HttpClient) 
   */
  public IssuesForProject(String projectId, Session session) {
    this(projectId, session, HttpClients.createDefault());
  }

  /**
   * Returns the {@link Issue issues} created under the given project.
   * @return a list of {@link Issue issues} created under the given project
   * @throws IOException if the remote YouTrack API is unreachable
   * @throws UnauthorizedException if the user's {@link Session session} does not have access to
   *     the resource
   * @since 0.2.0
   */
  public List<Issue> query() throws IOException, UnauthorizedException {
    final URI uri = new NonCheckedUriBuilder(RESOURCE.concat(projectId)).build();
    final HttpGet get = new HttpGet(uri);
    session.cookies().stream().forEach(get::addHeader);
    final Response response = new HttpResponseAsResponse(httpClient.execute(get));
    return response.payload()
        .map(new HttpEntityAsJaxb<>(org.llorllale.youtrack.api.issues.jaxb.Issues.class))
        .get()
        .getIssue()
        .stream()
        .map(XmlIssue::new)
        .collect(toList());
  }
}
