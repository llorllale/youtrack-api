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

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClients;
import org.llorllale.youtrack.api.response.HttpResponseAsResponse;
import org.llorllale.youtrack.api.response.Response;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.NonCheckedUriBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

/**
 * Creates {@link Issue issues}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
public class CreateIssue {
  private static final String RESOURCE = "/issue";

  private final Session session;
  private final HttpClient httpClient;
  private String projectId;
  private String summary;
  private Optional<String> description = Optional.empty();

  /**
   * Primary constructor.
   * @param session the user session
   * @param httpClient the {@link HttpClient} to use
   * @since 0.1.0
   */
  public CreateIssue(Session session, HttpClient httpClient) {
    this.session = session;
    this.httpClient = httpClient;
  }

  /**
   * Assumes the {@link HttpClients#createDefault() default http client} as the {@link HttpClient}
   * to use.
   * @param session the user session
   * @since 0.1.0
   * @see #CreateIssue(org.llorllale.youtrack.api.session.Session, 
   *     org.apache.http.client.HttpClient) 
   */
  public CreateIssue(Session session) {
    this(session, HttpClients.createDefault());
  }

  /**
   * Specifies the ID of the project for which the issue will be created.
   * @param projectId the ID of the project for which the issue will be created
   * @return this {@link CreateIssue}
   * @since 0.1.0
   */
  public CreateIssue forProjectId(String projectId) {
    this.projectId = projectId;
    return this;
  }

  /**
   * Specifies the text for the issue's {@link Issue#summary() summary}.
   * @param summary the text for the issue's {@link Issue#summary() summary}
   * @return this {@link CreateIssue}
   * @since 0.1.0
   */
  public CreateIssue withSummary(String summary) {
    this.summary = summary;
    return this;
  }

  /**
   * Specifies the text for the issue's {@link Issue#description() description}.
   * @param description the text for the issue's {@link Issue#description() description}
   * @return this {@link CreateIssue}
   * @since 0.1.0
   */
  public CreateIssue withDescription(String description) {
    this.description = Optional.of(description);
    return this;
  }

  /**
   * Creates the {@link Issue issue} at the remote YouTrack service and return's the newly-created
   * issue's ID.
   * @return the newly-created issue's ID.
   * @throws IOException if the service is unreachable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to create 
   * {@link Issue issues}.
   * @since 0.1.0
   */
  public String create() throws IOException, UnauthorizedException {
    final URI uri = new NonCheckedUriBuilder(
        session.baseUrl()
            .toString()
            .concat(RESOURCE)
    ).setParameter("project", projectId)
        .setParameter("summary", summary)
        .setParameter("description", description.orElse(""))
        .build();
    final HttpPut put = new HttpPut(uri);
    session.cookies()
        .stream()
        .forEach(put::addHeader);
    final Response response = new HttpResponseAsResponse(httpClient.execute(put));
    response.payload(); //TODO how to better trigger validation logic?
    final Header location = response.rawResponse().getFirstHeader("Location");
    return location
        .getValue()
        .substring(
            response.rawResponse()
                .getFirstHeader("Location")
                .getValue()
                .lastIndexOf('/') + 1
        );
  }
}
