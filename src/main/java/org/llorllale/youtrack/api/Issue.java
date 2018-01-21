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
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * A {@link YouTrack} issue.
 * 
 * <p>Two issues are equal if their {@link #project() projects} are both equal, and their 
 * {@link #id() ids} are also equal.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
public interface Issue {
  /**
   * The {@link Project} that the issue was created in.
   * 
   * @return the ID of the project that the issue was created in
   * @since 0.4.0
   */
  Project project();

  /**
   * The issue's id.
   * 
   * @return the issue's id
   * @since 0.1.0
   */
  String id();

  /**
   * The date the issue was created.
   * 
   * @return The date the issue was created.
   * @since 0.1.0
   */
  Instant creationDate();

  /**
   * The issue's summary.
   * 
   * @return The issue's summary.
   * @since 0.1.0
   */
  String summary();

  /**
   * The issue's description.
   * 
   * @return the issue's description
   * @since 0.1.0
   */
  Optional<String> description();

  /**
   * Access to the issue's {@link User users}.
   * 
   * @return access to the issue's {@link User users}
   * @since 0.5.0
   */
  UsersOfIssue users();

  /**
   * Access to the issue's {@link Comment comments}.
   * 
   * @return Access to the issue's {@link Comment comments}.
   * @since 0.4.0
   */
  Comments comments();

  /**
   * Access to the issue's {@link TimeTrackEntry timetracking}.
   * 
   * <p><strong>Note:</strong> it is important that you first check whether timetracking is 
   * {@link ProjectTimeTracking#enabled() enabled} for the {@link Project}.</p>
   * 
   * @return access to the issue's {@link TimeTrackEntry timetracking}.
   * @see ProjectTimeTracking#enabled() 
   * @since 0.4.0
   */
  IssueTimeTracking timetracking();

  /**
   * Returns the same {@link Issue} after refreshing its data from the server.
   * 
   * <p>
   * This is a convenient shortcut for {@code issue.project().issues().get(issue.id()).get()}.
   * </p>
   * 
   * @return the same {@link Issue} after refreshing its data from the server
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 0.5.0
   */
  Issue refresh() throws IOException, UnauthorizedException;

  /**
   * The {@link UpdateIssue} API for this {@link Issue}.
   * 
   * @return the {@link UpdateIssue} API for this {@link Issue}
   * @since 0.9.0
   */
  UpdateIssue update();

  /**
   * All {@link AssignedField fields} of this {@link Issue}.
   * 
   * @return all {@link AssignedField fields} of this {@link Issue}
   * @see UpdateIssue#fields(java.util.Map) 
   * @since 0.8.0
   */
  Collection<AssignedField> fields();
}
