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

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

/**
 * XmlOf impl of {@link TimeTrackEntry}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.3.0
 */
final class XmlTimeTrackEntry implements TimeTrackEntry {
  private final Issue issue;
  private final Xml xml;

  /**
   * Ctor.
   * 
   * @param issue the parent issue
   * @param xml the XML received from YouTrack
   * @since 0.3.0
   */
  XmlTimeTrackEntry(Issue issue, Xml xml) {
    this.issue = issue;
    this.xml = xml;
  }

  @Override
  public Issue issue() {
    return this.issue;
  }

  @Override
  public LocalDate date() {
    return Instant.ofEpochMilli(
        Long.parseLong(
            this.xml.textOf("date").get()
        )
    ).atZone(new YouTrackZoneId().toZoneId())
        .toLocalDate();
  }

  @Override
  public Duration duration() {
    return Duration.ofMinutes(Long.parseLong(this.xml.textOf("duration").get()));
  }

  @Override
  public String description() {
    return this.xml.textOf("description").get();
  }

  @Override
  public Optional<TimeTrackEntryType> type() {
    return this.xml.child("worktype").map(XmlTimeTrackEntryType::new);
  }
}
