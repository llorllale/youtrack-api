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
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.llorllale.youtrack.api.session.Login;

import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Default implementation of {@link IssueTimeTracking}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
final class DefaultIssueTimeTracking implements IssueTimeTracking {
  private static final String PATH_TEMPLATE = "/issue/%s/timetracking/workitem";
  private final Login login;
  private final Issue issue;
  private final Supplier<CloseableHttpClient> httpClient;

  /**
   * Ctor.
   * 
   * @param login the user's {@link Login}
   * @param issue the {@link Issue} to which this {@link IssueTimeTracking} is attached to
   * @param httpClient the {@link HttpClient} to use
   * @since 0.4.0
   */
  DefaultIssueTimeTracking(Login login, Issue issue, Supplier<CloseableHttpClient> httpClient) {
    this.login = login;
    this.issue = issue;
    this.httpClient = httpClient;
  }

  @Override
  public Stream<TimeTrackEntry> stream() throws IOException, UnauthorizedException {
    return new StreamOf<>(
      new MappedCollection<>(
        xml -> new XmlTimeTrackEntry(this.issue, xml),
        new XmlsOf(
          "/workItems/workItem",
          new HttpResponseAsResponse(
            this.httpClient.get().execute(
              new Authenticated(
                this.login.session(),
                new HttpGet(
                  this.login.session().baseUrl().toString()
                    .concat(String.format(PATH_TEMPLATE, this.issue.id()))
                )
              )
            )
          )
        )
      )
    );
  }

  @Override
  public IssueTimeTracking create(Duration duration) throws IOException, UnauthorizedException {
    return this.create(LocalDate.now(), duration, null, null);
  }

  @Override
  public IssueTimeTracking create(Duration duration, String description) 
      throws IOException, UnauthorizedException {
    return this.create(LocalDate.now(), duration, description, null);
  }

  @Override
  public IssueTimeTracking create(LocalDate date, Duration duration) 
      throws IOException, UnauthorizedException {
    return this.create(date, duration, null, null);
  }

  @Override
  public IssueTimeTracking create(Duration duration, TimeTrackEntryType type) 
      throws IOException, UnauthorizedException {
    return this.create(LocalDate.now(), duration, null, type);
  }

  @Override
  public IssueTimeTracking create(Duration duration, String description, TimeTrackEntryType type) 
      throws IOException, UnauthorizedException {
    return this.create(LocalDate.now(), duration, description, type);
  }

  @Override
  public IssueTimeTracking create(LocalDate date, Duration duration, String description) 
      throws IOException, UnauthorizedException {
    return this.create(date, duration, description, null);
  }

  @Override
  public IssueTimeTracking create(
      LocalDate date,
      Duration duration,
      String description,
      TimeTrackEntryType type
  ) throws IOException, UnauthorizedException {
    new HttpResponseAsResponse(
      this.httpClient.get().execute(
        new Authenticated(
          this.login.session(),
          new Loaded(
            new StringEntity(
              this.toXmlString(date, duration, description, type),
              ContentType.APPLICATION_XML
            ),
            new HttpPost(
              this.login.session().baseUrl().toString()
                .concat(String.format(PATH_TEMPLATE, this.issue.id()))
            )
          )
        )
      )
    ).httpResponse();
    return new DefaultIssueTimeTracking(this.login, this.issue, this.httpClient);
  }

  /**
   * The XML payload to be sent to YouTrack in order to create timetrack entries for the 
   * issue.
   * @param date the entry's date
   * @param duration the entry's duration
   * @param description the entry's description
   * @param type the entry's type
   * @return the XML payload to be set as the HTTP request's entity
   */
  private String toXmlString(
      LocalDate date, 
      Duration duration, 
      String description, 
      TimeTrackEntryType type
  ) {
    final StringBuilder xmlBuilder = new StringBuilder("<workItem>")
      .append("<date>")
      .append(
        String.valueOf(
          date.atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
        )
      ).append("</date>")
      .append("<duration>")
      .append(String.valueOf(duration.toMinutes()))
      .append("</duration>")
      .append("<description>")
      .append(Optional.ofNullable(description).orElse(""))
      .append("</description>");
    Optional.ofNullable(type).ifPresent(t -> 
      xmlBuilder
        .append("<worktype>")
          .append("<name>")
            .append(t.asString())
          .append("</name>")
        .append("</worktype>")
    );
    xmlBuilder.append("</workItem>");
    return xmlBuilder.toString();
  }
}
