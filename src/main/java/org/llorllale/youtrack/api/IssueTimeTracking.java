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

package org.llorllale.youtrack.api;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
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
   * @param spec the entry's {@link EntrySpec spec}
   * @return the newly-created {@link TimeTrackEntry entry}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 0.4.0
   */
  IssueTimeTracking create(EntrySpec spec) throws IOException, UnauthorizedException;

  /**
   * <p>Specifications for creating a {@link TimeTrackEntry} on an {@link Issue}.</p>
   * 
   * <p>The new entry will always be created on the {@link Issue} attached to this 
   * {@link IssueTimeTracking}.</p>
   * 
   * @since 0.4.0
   */
  final class EntrySpec {
    private final LocalDate date;
    private final Duration duration;
    private final Optional<String> description;
    private final Optional<TimeTrackEntryType> type;

    /**
     * Primary ctor.
     * 
     * @param date the date when the entry was worked
     * @param duration the duration for the work
     * @param description description for the work
     * @param type the work type (eg. "Development")
     * @since 0.4.0
     */
    public EntrySpec(
        LocalDate date, 
        Duration duration, 
        Optional<String> description, 
        Optional<TimeTrackEntryType> type
    ) {
      this.date = date;
      this.duration = duration;
      this.description = description;
      this.type = type;
    }

    /**
     * Shorthand constructor. The following assumptions are made:
     * 
     * <ul>
     *   <li>date is <em>now</em></li>
     *   <li>description is empty</li>
     *   <li>type is empty</li>
     * </ul>
     * 
     * @param duration the work's duration
     * @see #EntrySpec(LocalDate, Duration, Optional, Optional) 
     * @since 0.4.0
     */
    public EntrySpec(Duration duration) {
      this(
          LocalDate.now(), 
          duration, 
          Optional.empty(), 
          Optional.empty()
      );
    }

    /**
     * Returns a new spec with {@code date} and using {@code this} as a prototype.
     * 
     * @param entryDate the entry's date
     * @return a new spec with {@code date} and using {@code this} as a prototype
     * @since 0.4.0
     */
    public EntrySpec withDate(LocalDate entryDate) {
      return new EntrySpec(
          entryDate, 
          this.duration, 
          this.description, 
          this.type
      );
    }

    /**
     * Returns a new spec with {@code description} and using {@code this} as a prototype.
     * 
     * @param desc the entry's description
     * @return a new spec with {@code description} and using {@code this} as a prototype
     * @since 0.4.0
     */
    public EntrySpec withDesc(String desc) {
      return new EntrySpec(
          this.date, 
          this.duration, 
          Optional.of(desc), 
          this.type
      );
    }

    /**
     * Returns a new spec with {@code type} and using {@code this} as a prototype.
     * 
     * @param entryType the entry's type
     * @return a new spec with {@code type} and using {@code this} as a prototype
     * @since 0.4.0
     */
    public EntrySpec withType(TimeTrackEntryType entryType) {
      return new EntrySpec(
          this.date, 
          this.duration, 
          this.description, 
          Optional.of(entryType)
      );
    }

    /**
     * Returns a {@link HttpEntity} representing this spec.
     * 
     * @return a {@link HttpEntity} representing this spec
     */
    HttpEntity asHttpEntity() {
      final StringBuilder xmlBuilder = new StringBuilder("<workItem>")
          .append("<date>")
          .append(String.valueOf(
                      this.date.atStartOfDay()
                          .atZone(ZoneId.systemDefault())
                          .toInstant()
                          .toEpochMilli()
                  )
          ).append("</date>")
          .append("<duration>")
          .append(String.valueOf(this.duration.toMinutes())
          ).append("</duration>")
          .append("<description>")
          .append(this.description.orElse(""))
          .append("</description>");

      this.type.ifPresent(t -> 
          xmlBuilder
              .append("<workType>")
                  .append("<name>")
                      .append(t.asString())
                  .append("</name>")
              .append("</worktype>")
      );

      return new StringEntity(
          xmlBuilder.append("</workItem>").toString(), 
          ContentType.APPLICATION_XML
      );
    }
  }
}
