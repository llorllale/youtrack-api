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
    private final String description;
    private final TimeTrackEntryType type;

    /**
     * Primary ctor.
     * 
     * @param date the date when the entry was worked
     * @param duration the duration for the work
     * @param description description for the work (may be {@code null})
     * @param type the work type (eg. "Development") (may be {@code null})
     * @since 0.4.0
     */
    public EntrySpec(
        LocalDate date, 
        Duration duration, 
        String description, 
        TimeTrackEntryType type
    ) {
      this.date = date;
      this.duration = duration;
      this.description = description;
      this.type = type;
    }

    /**
     * Assumes that the date is <em>now</em>, the description is empty, and the type is empty.
     * 
     * @param duration the work's duration
     * @since 0.4.0
     */
    public EntrySpec(Duration duration) {
      this(LocalDate.now(), duration, null, null);
    }

    /**
     * Assumes the date is <em>now</em> and {@code type} is empty.
     * 
     * @param duration the work's duration
     * @param description descriptive text
     * @since 1.0.0
     */
    public EntrySpec(Duration duration, String description) {
      this(LocalDate.now(), duration, description, null);
    }

    /**
     * Assumes an empty description and type.
     * 
     * @param date the date when the work took place
     * @param duration  the work's duration
     * @since 1.0.0
     */
    public EntrySpec(LocalDate date, Duration duration) {
      this(date, duration, null, null);
    }

    /**
     * Assumes the date is <em>now</em> and an empty description.
     * 
     * @param duration the work's duration
     * @param type the work type
     * @since 1.0.0
     */
    public EntrySpec(Duration duration, TimeTrackEntryType type) {
      this(LocalDate.now(), duration, null, type);
    }

    /**
     * Assumes the date is <em>now</em>.
     * 
     * @param duration the work's duration
     * @param description descriptive text
     * @param type the work type
     * @since 1.0.0
     */
    public EntrySpec(Duration duration, String description, TimeTrackEntryType type) {
      this(LocalDate.now(), duration, description, type);
    }

    /**
     * Assumes the {@code type} is {@code null}.
     * 
     * @param date the date when the work was performed
     * @param duration the work's duration
     * @param description descriptive text
     * @since 1.0.0
     */
    public EntrySpec(LocalDate date, Duration duration, String description) {
      this(date, duration, description, null);
    }

    /**
     * A view of this {@link EntrySpec} as an {@link Xml}.
     * 
     * @return a view of this spec as an xml
     * @since 1.0.0
     */
    public Xml asXml() {
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
          .append(String.valueOf(this.duration.toMinutes()))
          .append("</duration>")
          .append("<description>")
          .append(Optional.ofNullable(this.description).orElse(""))
          .append("</description>");

      Optional.ofNullable(this.type).ifPresent(t -> 
          xmlBuilder
              .append("<worktype>")
                  .append("<name>")
                      .append(t.asString())
                  .append("</name>")
              .append("</worktype>")
      );

      xmlBuilder.append("</workItem>");

      return new XmlOf(
          new StringAsDocument(
              xmlBuilder.toString()
          )
      );
    }

    @Override
    public int hashCode() {
      return this.duration.hashCode() 
          ^ this.date.hashCode()
          ^ Optional.ofNullable(this.description).hashCode()
          ^ Optional.ofNullable(this.type).hashCode();
    }

    @Override
    public boolean equals(Object object) {
      if (!(object instanceof EntrySpec)) {
        return false;
      }

      final EntrySpec other = (EntrySpec) object;
      return this.asXml().node().isEqualNode(other.asXml().node());
    }
  }
}
