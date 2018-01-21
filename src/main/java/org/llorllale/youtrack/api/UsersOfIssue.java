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
import java.util.Optional;

import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Access the {@link User users} of an {@link Issue issue}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.5.0
 */
public interface UsersOfIssue {
  /**
   * The {@link Issue issue's} creator.
   * 
   * @return the {@link Issue issue's} creator
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 0.5.0
   */
  User creator() throws IOException, UnauthorizedException;

  /**
   * The {@link User user} that updated the {@link Issue issue}, if any.
   * 
   * @return the {@link User user} that updated the {@link Issue issue}, if any
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 0.5.0
   */
  Optional<User> updater() throws IOException, UnauthorizedException;

  /**
   * The {@link User user} assigned to the {@link Issue issue}, if any.
   * 
   * @return the {@link User user} assigned to the {@link Issue issue}, if any
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 0.5.0
   */
  Optional<User> assignee() throws IOException, UnauthorizedException;

  /**
   * Assigns the {@link Issue} to the given {@link User}.
   * 
   * @param user the {@link User} to assign the issue to
   * @return this object
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @see #assignee() 
   * @since 0.5.0
   */
  UsersOfIssue assignTo(User user) throws IOException, UnauthorizedException;

  /**
   * The enclosing {@link Issue}.
   * 
   * @return the enclosing {@link Issue}
   * @since 0.5.0
   */
  Issue issue();
}
