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

import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;

import java.io.IOException;
import java.util.Optional;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
public class CreateIssue {
  private static final String RESOURCE = "/rest/issue";

  private final Session session;
  private final String projectId;
  private Optional<String> summary = Optional.empty();
  private Optional<String> description = Optional.empty();
  private final HttpClient httpClient;

  /**
   * Primary constructor.
   * @param session
   * @param projectId
   * @param httpClient 
   * @since 0.1.0
   */
  public CreateIssue(Session session, String projectId, HttpClient httpClient) {
    this.session = session;
    this.projectId = projectId;
    this.httpClient = httpClient;
  }

  /**
   * Assumes the {@link HttpClients#createDefault() default http client} as the {@link HttpClient}
   * to use.
   * @param session
   * @param projectId 
   * @since 0.1.0
   * @see #CreateIssue(Session, String, HttpClient) 
   */
  public CreateIssue(Session session, String projectId) {
    this(session, projectId, HttpClients.createDefault());
  }

  /**
   * 
   * @param summary
   * @return 
   * @since 0.1.0
   */
  public CreateIssue withSummary(String summary) {
    this.summary = Optional.of(summary);
    return this;
  }

  /**
   * 
   * @param description
   * @return 
   * @since 0.1.0
   */
  public CreateIssue withDescription(String description) {
    this.description = Optional.of(description);
    return this;
  }

  /**
   * 
   * @return 
   * @throws IOException 
   * @throws UnauthorizedException 
   * @since 0.1.0
   */
  public Issue create() throws IOException, UnauthorizedException {
    throw new UnsupportedOperationException();
  }
}
