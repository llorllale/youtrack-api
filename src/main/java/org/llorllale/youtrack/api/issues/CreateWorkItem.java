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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.llorllale.youtrack.api.response.HttpResponseAsResponse;
import org.llorllale.youtrack.api.response.Response;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.NonCheckedUriBuilder;


import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

/**
 * Creates a new time-tracking entry for a given {@link Issue}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.3.0
 */
public class CreateWorkItem {
  private static final String RESOURCE = "/issue/ISSUE_ID/timetracking/workitem";
  private static final String PAYLOAD = 
      "<workItem>\n"
      + "  <date>%DATE%</date>\n"
      + "  <duration>%DURATION%</duration>\n"
      + "  <description>%DESCRIPTION%</description>\n"
      + "  <worktype>\n"
      + "    <name>%TYPE%</name>\n"
      + "  </worktype>\n"
      + "</workItem>";

  private final Session session;
  private final HttpClient httpClient;

  private String issueId;
  private LocalDate date;
  private long durationInMins;
  private Optional<String> description;
  private Optional<String> type;

  /**
   * Primary constructor.
   * @param session the user's {@link Session}
   * @param httpClient the {@link HttpClient} to use
   * @since 0.3.0
   */
  CreateWorkItem(Session session, HttpClient httpClient) {
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

  /**
   * Sets the workitem's duration.
   * @param duration the workitem's duration
   * @return this object
   * @since 0.3.0
   */
  public CreateWorkItem forDuration(Duration duration) {
    this.durationInMins = duration.toMinutes();
    return this;
  }

  /**
   * Sets the workitem's description.
   * @param description the workitem's description
   * @return this object
   * @since 0.3.0
   */
  public CreateWorkItem withDescription(String description) {
    this.description = Optional.ofNullable(description);
    return this;
  }

  /**
   * Sets the workitem's type.
   * @param type the workitem's type
   * @return this object
   * @since 0.3.0
   */
  public CreateWorkItem withType(String type) {
    this.type = Optional.ofNullable(type);
    return this;
  }

  /**
   * Creates the {@link WorkItem} and returns its ID.
   * @return the newly-created {@link WorkItem workitem's} ID.
   * @throws IOException if YouTrack is unreachable
   * @throws UnauthorizedException if the user's {@link Session} does not have permission to create
   *     this resource
   * @since 0.3.0
   */
  public String create() throws IOException, UnauthorizedException {
    final URI uri = new NonCheckedUriBuilder(
        session.baseUrl()
            .toString()
            .concat(RESOURCE.replace("ISSUE_ID", issueId))
    ).build();
    final HttpPost post = new HttpPost(uri);
    session.cookies().forEach(post::addHeader);
    post.setEntity(
        new StringEntity(
            PAYLOAD.replace(
                "%DATE%", 
                String.valueOf(date.atTime(0, 0)
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli()
                )
            ).replace("%DURATION%", String.valueOf(durationInMins))
                .replace("%DESCRIPTION%", description.orElse(""))
                .replace("%TYPE%", type.orElse(""))
        )
    );
    final Response response = new HttpResponseAsResponse(httpClient.execute(post));
    response.payload(); //TODO there must be a better way/design to trigger validation...
    final String url = response.rawResponse()
        .getHeaders("Location")[0]  //expected
        .getValue();
    return url.substring(url.lastIndexOf('/') + 1);
  }
}
