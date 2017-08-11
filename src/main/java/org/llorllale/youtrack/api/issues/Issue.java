/**
 * Copyright 2017 George Aristy
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

import java.time.Instant;
import java.util.Optional;

/**
 * A YouTrack issue.
 * @author George Aristy
 * @version $Id$
 * @since 1.0.0
 */
public interface Issue {
  /**
   * The issue's id.
   * @return The issue's id.
   * @since 1.0.0
   */
  public String id();
  /**
   * The date the issue was created.
   * @return The date the issue was created.
   * @since 1.0.0
   */
  public Instant creationDate();
  /**
   * The issue's type.
   * @return The issue's type.
   * @since 1.0.0
   */
  public String type();
  /**
   * The issue's state.
   * @return The issue's state.
   * @since 1.0.0
   */
  public String state();
  /**
   * The name of the user that the issue is assigned to.
   * @return The name of the user that the issue is assigned to.
   * @since 1.0.0
   */
  public String assignee();
  /**
   * The issue's priority.
   * @return The issue's priority.
   * @since 1.0.0
   */
  public String priority();
  /**
   * The name of the user that reported the issue.
   * @return The name of the user that reported the issue.
   * @since 1.0.0
   */
  public String reporterName();
  /**
   * The name of the user that last updated the issue.
   * @return The name of the user that last updated the issue.
   * @since 1.0.0
   */
  public Optional<String> updaterName();
  /**
   * The issue's summary.
   * @return The issue's summary.
   * @since 1.0.0
   */
  public String summary();
  /**
   * The issue's description.
   * @return The issue's description.
   * @since 1.0.0
   */
  public String description();
  /**
   * The 'short name' of the project that the issue was created in.
   * This property is used internally for other queries to the remote YouTrack
   * API.
   * @return The 'short name' of the project that the issue was created in.
   * @since 1.0.0
   */
  public String projectShortName();
}
