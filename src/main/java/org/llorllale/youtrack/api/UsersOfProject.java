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
import java.util.stream.Stream;

import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Access the {@link User users} of a {@link Project project}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.9.0
 */
public interface UsersOfProject {
  /**
   * The {@link Project}.
   * 
   * @return the {@link Project}
   * @since 0.9.0
   */
  Project project();

  /**
   * The {@link User} with the given {@code login}.
   * 
   * <p><strong>Note:</strong><br>
   * Unfortunately, {@code YouTrack}'s response - {@code 403 Forbidden} - does not allow clients 
   * to differentiate between cases when {@code login} is not found vs. cases when the user's 
   * {@link Session} is not authorized to read the user's information.
   * 
   * @param login the user's {@link User#loginName() login}
   * @return the {@link User} with the given {@code login}
   * @throws IOException if the server is unreachable 
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform 
   *     this operation <strong>or if the given {@code login} does not exist</strong>
   * @since 0.9.0
   */
  User user(String login) throws IOException, UnauthorizedException;

  /**
   * A stream of all {@link User users} to which {@link Issue issues} can be assigned in this
   * {@link Project}.
   * 
   * @return a stream of all {@link User users} to which {@link Issue issues} can be assigned.
   * @throws IOException if the server is unreachable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform 
   *     this operation
   * @since 0.9.0
   */
  Stream<User> assignees() throws IOException, UnauthorizedException;
}
