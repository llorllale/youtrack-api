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

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.llorllale.youtrack.api.session.Session;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Creates a new time-tracking entry for a given {@link Issue}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.3.0
 */
public class CreateWorkItem {
  private final Session session;
  private final HttpClient httpClient;

  private String issueId;
  private LocalDate date;
  private int durationInMins;
  private Optional<String> description;
  private Optional<String> type;

  /**
   * Primary constructor.
   * @param session the user's {@link Session}
   * @param httpClient the {@link HttpClient} to use
   * @since 0.3.0
   */
  public CreateWorkItem(Session session, HttpClient httpClient) {
    this.session = session;
    this.httpClient = httpClient;
  }

  /**
   * Uses the {@link HttpClients#createDefault() default} http client.
   * @param session the user's {@link Session}
   * @since 0.3.0
   */
  public CreateWorkItem(Session session) {
    this(session, HttpClients.createDefault());
  }

  /**
   * Specifies the {@link Issue#id() issue's id} on which to add the work item.
   * @param issueId the {@link Issue issue}'s id.
   * @return this object
   * @since 0.3.0
   */
  public CreateWorkItem onIssue(String issueId) {
    this.issueId = issueId;
    return this;
  }

  /**
   * The date on which the work item was worked.
   * @param date the date on which the work item was worked
   * @return this object
   * @since 0.3.0
   */
  public CreateWorkItem workedOn(LocalDate date) {
    this.date = date;
    return this;
  }

}
