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
 * API for {@link Comment comments} on an {@link Issue}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public interface Comments {
  /**
   * A {@link Stream} with all comments for this {@link Issue}.
   * 
   * @return A {@link Stream} with all comments for this {@link Issue}.
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is unauthorized to access this 
   *     resource
   * @since 0.4.0
   */
  Stream<Comment> stream() throws IOException, UnauthorizedException;

  /**
   * Creates a new {@link Comment} for this {@link Issue}.
   * 
   * @param text the comment's text
   * @return this {@link Comments}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is unauthorized to perform this 
   *     operation
   * @since 0.4.0
   */
  Comments post(String text) throws IOException, UnauthorizedException;

  /**
   * The parent {@link Issue}.
   * 
   * @return the parent {@link Issue}
   * @since 0.9.0
   */
  Issue issue();
}
