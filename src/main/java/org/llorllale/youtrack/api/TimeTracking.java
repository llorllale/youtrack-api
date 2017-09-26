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

import com.jamesmurty.utils.XMLBuilder2;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * <p>API for {@link Issue} timetracking.</p>
 * 
 * <p>Note: timetracking needs to be enabled for the {@link Project} in YouTrack.</p>
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public interface TimeTracking {
  /**
   * Returns a {@link Stream} with all available {@link TimeTrackEntry work entries} for the 
   * {@link Issue}.
   * @return a {@link Stream} with all available {@link TimeTrackEntry work entries} for the 
   *     {@link Issue}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 0.4.0
   */
  public Stream<TimeTrackEntry> stream() throws IOException, UnauthorizedException;

  /**
   * Creates a new {@link TimeTrackEntry entry}.
   * @param spec the entry's {@link EntrySpec spec}
   * @return the newly-created {@link TimeTrackEntry entry}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 0.4.0
   */
  public TimeTracking create(EntrySpec spec) throws IOException, UnauthorizedException;

  /**
   * <p>Specifications for creating a {@link TimeTrackEntry} on an {@link Issue}.</p>
   * 
   * <p>The new entry will always be created on the {@link Issue} attached to this 
   * {@link TimeTracking}.</p>
   * 
   * @since 0.4.0
   */
  public static final class EntrySpec {
    private final LocalDate date;
    private final Duration duration;
    private final Optional<String> description;
    private final Optional<String> type;

    /**
     * Primary ctor.
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
        Optional<String> type
    ) {
      this.date = date;
      this.duration = duration;
      this.description = description;
      this.type = type;
    }

    /**
     * Shorthand constructor that assumes the following:
     * <ul>
     *   <li>date is <em>now</em></li>
     *   <li>description is empty</li>
     *   <li>type is empty</li>
     * </ul>
     * @param duration the work's duration
     * @since 0.4.0
     * @see #EntrySpec(java.time.LocalDate, java.time.Duration, java.util.Optional, 
     *     java.util.Optional) 
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
     * @param date the entry's date
     * @return a new spec with {@code date} and using {@code this} as a prototype
     * @since 0.4.0
     */
    public EntrySpec withDate(LocalDate date) {
      return new EntrySpec(
          date, 
          this.duration, 
          this.description, 
          this.type
      );
    }

    /**
     * Returns a new spec with {@code description} and using {@code this} as a prototype.
     * @param description the entry's description
     * @return a new spec with {@code description} and using {@code this} as a prototype
     * @since 0.4.0
     */
    public EntrySpec withDescription(String description) {
      return new EntrySpec(
          this.date, 
          this.duration, 
          Optional.of(description), 
          this.type
      );
    }

    /**
     * Returns a new spec with {@code type} and using {@code this} as a prototype.
     * @param type the entry's type
     * @return a new spec with {@code type} and using {@code this} as a prototype
     * @since 0.4.0
     */
    public EntrySpec withType(String type) {
      return new EntrySpec(
          this.date, 
          this.duration, 
          this.description, 
          Optional.of(type)
      );
    }

    /**
     * Returns a {@link HttpEntity} representing this spec.
     * @return a {@link HttpEntity} representing this spec
     */
    public HttpEntity asHttpEntity() {
      final XMLBuilder2 builder = XMLBuilder2.create("workItem")
          .elem("date")
              .text(
                  String.valueOf(
                      date.atStartOfDay()
                          .atZone(ZoneId.systemDefault())
                          .toInstant()
                          .toEpochMilli()
                  )
              )
              .up()
          .elem("duration")
              .text(String.valueOf(duration.toMinutes()))
              .up()
          .elem("description")
              .text(description.orElse(""))
              .up();

      type.ifPresent(type -> 
          builder
              .elem("worktype")
                .elem("name")
                  .text(type)
                  .up()
                .up()
      );

      return new StringEntity(builder.asString(), ContentType.APPLICATION_XML);
    }
  }
}
