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

/**
 * A {@link State} that has been assigned to an {@link Issue}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.7.0
 */
public interface AssignedState extends State {
  /**
   * The parent {@link Issue}.
   * @return the parent {@link Issue}
   * @since 0.7.0
   */
  public Issue issue();

  /**
   * Whether the parent {@link Issue} is resolved.
   * @return whether the parent {@link Issue} is resolved
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 0.7.0
   */
  public boolean resolved() throws IOException, UnauthorizedException;

  /**
   * Changes the parent {@link Issue issue's} state and returns a new {@link AssignedState} 
   * representing {@code other}.
   * @param other
   * @return  a new {@link AssignedState} representing {@code other}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 0.7.0
   */
  public AssignedState changeTo(State other) throws IOException, UnauthorizedException;
}
