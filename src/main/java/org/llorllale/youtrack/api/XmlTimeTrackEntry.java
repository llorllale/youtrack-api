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

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

/**
 * <p>JAXB implementation of {@link WorkItem}.</p>
 * 
 * <p>This class adapts {@link org.llorllale.youtrack.api.issues.jaxb.WorkItem} into 
 * {@link WorkItem}.</p>
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.3.0
 */
class XmlTimeTrackEntry implements TimeTrackEntry {
  private final Issue issue;
  private final org.llorllale.youtrack.api.jaxb.WorkItem jaxbWorkItem;

  /**
   * Ctor.
   * 
   * @param jaxbWorkItem the JAXB class 
   * @since 0.3.0
   */
  XmlTimeTrackEntry(Issue issue, org.llorllale.youtrack.api.jaxb.WorkItem jaxbWorkItem) {
    this.issue = issue;
    this.jaxbWorkItem = jaxbWorkItem;
  }

  @Override
  public Issue issue() {
    return issue;
  }

  @Override
  public LocalDate date() {
    return Instant.ofEpochMilli(jaxbWorkItem.getDate())
        .atZone(ZoneId.systemDefault())
        .toLocalDate();
  }

  @Override
  public Duration duration() {
    return Duration.ofMinutes(jaxbWorkItem.getDuration());
  }

  @Override
  public Optional<String> description() {
    return Optional.ofNullable(jaxbWorkItem.getDescription());
  }

  @Override
  public Optional<TimeTrackEntryType> type() {
    return Optional.ofNullable(jaxbWorkItem.getWorkType())
        .map(XmlTimeTrackEntryType::new);
  }
}
