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

package org.llorllale.youtrack.api.issues;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;

/**
 * An item of <em>work</em> that is time-tracked as part of the effort to resolve an {@link Issue}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.3.0
 */
public interface WorkItem {
  /**
   * The date when the item was entered into the {@link Issue issue}'s time-tracking.
   * @return the date when the item was entered into the {@link Issue issue}'s time-tracking
   * @since 0.3.0
   */
  public LocalDate date();

  /**
   * The duration during which the work-item was worked on.
   * @return the duration during which the work-item was worked on
   * @since 0.3.0
   */
  public Duration duration();

  /**
   * The item's description.
   * @return the item's description (if any was entered)
   * @since 0.3.0
   */
  public Optional<String> description();

  /**
   * The login name of the user that registered the work item.
   * @return the login name of the user that registered the work item
   * @since 0.3.0
   */
  public String authorLoginName();
}
