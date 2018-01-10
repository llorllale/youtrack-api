/*
 * Copyright 2018 George Aristy.
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

package org.llorllale.youtrack.api.mock;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;
import org.llorllale.youtrack.api.Issue;
import org.llorllale.youtrack.api.TimeTrackEntry;
import org.llorllale.youtrack.api.TimeTrackEntryType;

/**
 * Mock implementation of {@link TimeTrackEntry} suitable for tests.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public final class MockTimeTrackEntry implements TimeTrackEntry {
  private final Issue issue;
  private final LocalDate date;
  private final Duration duration;
  private final String description;
  private final TimeTrackEntryType type;

  /**
   * Primary ctor.
   * 
   * @param issue the associated issue
   * @param date the date this entry was created
   * @param duration the duration of this entry
   * @param description this entry's description
   * @param type this entry's type (may be {@code null})
   * @since 1.0.0
   */
  @SuppressWarnings("checkstyle:ParameterNumber")
  public MockTimeTrackEntry(
      Issue issue, 
      LocalDate date, 
      Duration duration, 
      String description, 
      TimeTrackEntryType type
  ) {
    this.issue = issue;
    this.date = date;
    this.duration = duration;
    this.description = description;
    this.type = type;
  }

  /**
   * Create a {@link MockTimeTrackEntry} with no description nor type.
   * 
   * @param issue the associated issue
   * @param date the date this entry was created
   * @param duration the duration of this entry
   * @since 1.0.0
   */
  public MockTimeTrackEntry(Issue issue, LocalDate date, Duration duration) {
    this(issue, date, duration, null, null);
  }

  /**
   * Create a {@link MockTimeTrackEntry} with no description nor type, created 
   * {@link LocalDate#now() now}.
   * 
   * @param issue the associated issue
   * @param duration the duration of this entry
   * @since 1.0.0
   */
  public MockTimeTrackEntry(Issue issue, Duration duration) {
    this(issue, LocalDate.now(), duration);
  }

  @Override
  public Issue issue() {
    return this.issue;
  }

  @Override
  public LocalDate date() {
    return this.date;
  }

  @Override
  public Duration duration() {
    return this.duration;
  }

  @Override
  public String description() {
    return this.description;
  }

  @Override
  public Optional<TimeTrackEntryType> type() {
    return Optional.ofNullable(this.type);
  }
}
