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
import java.time.LocalDate;
import java.util.Optional;

/**
 * An entry in an {@link Issue issue's} timetracking.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public interface TimeTrackEntry {
  /**
   * The parent {@link Issue}.
   * 
   * @return the parent {@link Issue}
   * @since 0.8.0
   */
  Issue issue();

  /**
   * The date when the item was entered into the {@link Issue issue}'s time-tracking.
   * 
   * @return the date when the item was entered into the {@link Issue issue}'s time-tracking
   * @since 0.4.0
   */
  LocalDate date();

  /**
   * The duration during which the work-item was worked on.
   * 
   * @return the duration during which the work-item was worked on
   * @since 0.4.0
   */
  Duration duration();

  /**
   * The item's description.
   * 
   * @return the item's description
   * @since 0.4.0
   */
  String description();

  /**
   * The entry's {@link TimeTrackEntryType type}.
   * 
   * @return the entry's {@link TimeTrackEntryType type}
   * @since 0.8.0
   */
  Optional<TimeTrackEntryType> type();
}
