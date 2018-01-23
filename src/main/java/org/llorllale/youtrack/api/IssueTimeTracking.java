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
import java.util.stream.Stream;

import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * <p>API for {@link Issue} timetracking.</p>
 * 
 * <p>Note: timetracking needs to be {@link ProjectTimeTracking#enabled() enabled} for the 
 * {@link Project} in YouTrack, otherwise these operations will throw {@link IOException}.</p>
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public interface IssueTimeTracking {
  /**
   * Returns a {@link Stream} with all available {@link TimeTrackEntry work entries} for the 
   * {@link Issue}.
   * 
   * @return a {@link Stream} with all available {@link TimeTrackEntry work entries} for the 
   *     {@link Issue}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 0.4.0
   */
  Stream<TimeTrackEntry> stream() throws IOException, UnauthorizedException;

  /**
   * Creates a new {@link TimeTrackEntry entry}.
   * 
   * @param date the date when the entry was worked
   * @param duration the work's duration
   * @param description description for the work entry
   * @param type the work type (eg. "Development")
   * @return {@link IssueTimeTracking}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 1.0.0
   */
  IssueTimeTracking create(
      LocalDate date, 
      Duration duration, 
      String description, 
      TimeTrackEntryType type
  ) throws IOException, UnauthorizedException;

  /**
   * Creates a new {@link TimeTrackEntry entry} with all other fields unspecified.
   * 
   * @param duration the work's duration
   * @return {@link IssueTimeTracking}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 1.0.0
   */
  IssueTimeTracking create(Duration duration) throws IOException, UnauthorizedException;

  /**
   * Creates a new {@link TimeTrackEntry entry} with a duration and description.
   * 
   * @param duration the work's duration
   * @param description descriptive text
   * @return {@link IssueTimeTracking}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 1.0.0
   */
  IssueTimeTracking create(Duration duration, String description) 
      throws IOException, UnauthorizedException;

  /**
   * Creates a new {@link TimeTrackEntry entry} with a duration and a date.
   * 
   * @param date the date when the entry was worked
   * @param duration the work's duration
   * @return {@link IssueTimeTracking}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 1.0.0
   */
  IssueTimeTracking create(LocalDate date, Duration duration) 
      throws IOException, UnauthorizedException;

  /**
   * Creates a new {@link TimeTrackEntry entry} with a duration and a type.
   * 
   * @param duration the work's duration
   * @param type the work type (eg. "Development")
   * @return {@link IssueTimeTracking}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 1.0.0
   */
  IssueTimeTracking create(Duration duration, TimeTrackEntryType type) 
      throws IOException, UnauthorizedException;

  /**
   * Creates a new {@link TimeTrackEntry entry} with a duration, description, and a type.
   * 
   * @param duration the work's duration
   * @param description descriptive text
   * @param type the work type (eg. "Development")
   * @return {@link IssueTimeTracking}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 1.0.0
   */
  IssueTimeTracking create(Duration duration, String description, TimeTrackEntryType type) 
      throws IOException, UnauthorizedException;

  /**
   * Creates a new {@link TimeTrackEntry entry} with a duration, date, and description.
   * 
   * @param date the date when the entry was worked
   * @param duration the work's duration
   * @param description descriptive text
   * @return {@link IssueTimeTracking}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 1.0.0
   */
  IssueTimeTracking create(LocalDate date, Duration duration, String description)
      throws IOException, UnauthorizedException;
}
