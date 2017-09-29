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
 * Access to all {@link State states} configured in for a {@link Project}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.7.0
 */
public interface States {
  /**
   * Returns the parent {@link Project}.
   * @return the parent {@link Project}
   * @since 0.7.0
   */
  public Project project();

  /**
   * Returns a stream of all {@link State states} configured for the parent 
   * {@link #project() project}.
   * 
   * @return a stream of all {@link State states} configured for the parent 
   *     {@link #project() project}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 0.7.0
   */
  public Stream<State> stream() throws IOException, UnauthorizedException;

  /**
   * Returns a stream consisting of a subset of {@link #stream() all} {@link State states} 
   * configured for the parent {@link #project() project} that mark an {@link Issue} as 
   * {@link AssignedState#resolved() resolved}.
   * 
   * @return a stream consisting of a subset of {@link #stream() all} {@link State states} 
   *     configured for the parent {@link #project() project} that mark an {@link Issue} as 
   *     {@link AssignedState#resolved() resolved}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 0.7.0
   * @see #stream() 
   * @see Issue#state() 
   * @see AssignedState#resolved() 
   */
  public Stream<State> resolving() throws IOException, UnauthorizedException;
}
