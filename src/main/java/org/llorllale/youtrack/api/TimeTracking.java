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

import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * Access to a {@link Project project's} timetracking settings.
 *
 * <p><strong>Note:</strong> it is important that you first check whether timetracking is 
 * {@link #enabled() enabled} for the {@link Project}.</p>
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 * @see Issue#timetracking() 
 */
public interface TimeTracking {
  /**
   * The parent {@link Project}.
   * 
   * @return the parent {@link Project}
   * @since 0.8.0
   */
  public Project project();

  /**
   * Whether timetracking is enabled for the {@link Project} or not.
   * 
   * @return {@code true} if timetracking is enabled for the {@link Project}, {@code false} 
   *     otherwise
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 0.8.0
   */
  public boolean enabled() throws IOException, UnauthorizedException;

  /**
   * A stream of all {@link TimeTrackEntryType timetracking entry types}, if 
   * timetracking is {@link #enabled() enabled}.
   * 
   * @return a stream of all {@link TimeTrackEntryType timetracking entry types}
   * @throws IOException if the server is unavailable, <strong>or if timetracking is not 
   *     {@link #enabled() enabled} for this {@link Project}</strong>
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 0.8.0
   */
  public Stream<TimeTrackEntryType> types() throws IOException, UnauthorizedException;
}
