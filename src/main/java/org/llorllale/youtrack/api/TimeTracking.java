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
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public interface TimeTracking {
  public Issue issue();
  public TimeTrackEntry create(EntrySpec spec) throws IOException, UnauthorizedException;

  public Optional<TimeTrackEntry> get(String id) throws IOException, UnauthorizedException;

  public static final class EntrySpec {
    private final Issue issue;
    private final LocalDate date;
    private final Duration duration;
    private final Optional<String> description;
    private final Optional<String> type;

    /**
     * 
     * @param issue
     * @param date
     * @param duration
     * @param description
     * @param type 
     * @since 0.4.0
     */
    public EntrySpec(
        Issue issue, 
        LocalDate date, 
        Duration duration, 
        Optional<String> description, 
        Optional<String> type
    ) {
      this.issue = issue;
      this.date = date;
      this.duration = duration;
      this.description = description;
      this.type = type;
    }

    public EntrySpec(Issue issue, Duration duration) {
      this(
          issue, 
          LocalDate.now(), 
          duration, 
          Optional.empty(), 
          Optional.empty()
      );
    }

    public EntrySpec withDate(LocalDate date) {
      return new EntrySpec(
          this.issue, 
          date, 
          this.duration, 
          this.description, 
          this.type
      );
    }

    public EntrySpec withDescription(String description) {
      return new EntrySpec(
          this.issue, 
          this.date, 
          this.duration, 
          Optional.of(description), 
          this.type
      );
    }

    public EntrySpec withType(String type) {
      return new EntrySpec(
          this.issue, 
          this.date, 
          this.duration, 
          this.description, 
          Optional.of(type)
      );
    }

    public String asXmlString() {
      final XMLBuilder2 builder = XMLBuilder2.create("workItem")
          .elem("date").text(String.valueOf(date.atTime(0, 0))).up()
          .elem("duration").text(String.valueOf(duration.toMinutes())).up()
          .elem("description").text(description.orElse("")).up();

      type.ifPresent(type -> 
          builder
              .elem("worktype")
                .elem("name")
                  .text(type)
                  .up()
                .up()
      );

      return builder.asString();
    }
  }
}
