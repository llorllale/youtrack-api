/**
 * Copyright 2017 George Aristy.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.llorllale.youtrack.api.projects;

import java.io.IOException;
import java.net.URI;
import java.util.Set;
import static java.util.stream.Collectors.toSet;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.llorllale.youtrack.api.response.HttpResponseAsResponse;
import org.llorllale.youtrack.api.response.Response;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.HttpEntityAsJaxb;
import org.llorllale.youtrack.api.util.NonCheckedUriBuilder;

/**
 * Queries the remote YouTrack API for all available {@link Project projects}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.2.0
 */
public class AllProjects {
  private static final String RESOURCE = "/project/all";
  private final Session session;
  private final HttpClient httpClient;

  /**
   * Primary constructor.
   * @param session the user session
   * @param httpClient the {@link HttpClient} to use
   * @since 0.2.0
   */
  public AllProjects(Session session, HttpClient httpClient) {
    this.session = session;
    this.httpClient = httpClient;
  }

  /**
   * Assumes the {@link HttpClients#createDefault() default http client} to use.
   * @param session the user's {@link Session session}
   * @since 0.2.0
   * @see #AllProjects(org.llorllale.youtrack.api.session.Session, org.apache.http.client.HttpClient) 
   */
  public AllProjects(Session session) {
    this(session, HttpClients.createDefault());
  }

  /**
   * Queries the remote YouTrack API for all {@link Project projects} available to the 
   * {@link Session user}.
   * @return all available {@link Project projects}
   * @throws IOException if the service is unreachable
   * @throws UnauthorizedException if the user's {@link Session session} is not authorized to 
   *     access the resource
   * @since 0.2.0
   */
  public Set<Project> query() throws IOException, UnauthorizedException {
    final URI uri = new NonCheckedUriBuilder(
        session.baseUrl()
            .toString()
            .concat(RESOURCE)
    ).build();
    final HttpGet get = new HttpGet(uri);
    session.cookies().stream().forEach(get::addHeader);
    final Response response = new HttpResponseAsResponse(httpClient.execute(get));
    return response.payload()
        .map(new HttpEntityAsJaxb<>(org.llorllale.youtrack.api.issues.jaxb.Projects.class))
        .map(p -> p.getProject())
        .get()
        .stream()
        .map(XmlProject::new)
        .collect(toSet());
  }
}
