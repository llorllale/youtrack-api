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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Issues API.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public interface Issues {
  /**
   * This {@link Issues}' {@link Project}.
   * 
   * @return this {@link Issues}' {@link Project}
   * @since 0.4.0
   */
  Project project();

  /**
   * A {@link Stream} with all {@link Issue issues} created for this {@link Project}.
   * 
   * @return a {@link Stream} with all {@link Issue issues} for this {@link #project() project}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to access this
   *     resource
   * @since 0.4.0
   */
  Stream<Issue> stream() throws IOException, UnauthorizedException;

  /**
   * The {@link Issue} with the given {@code id}, if it exists.
   * 
   * @param id the {@link Issue#id() issue's id}
   * @return The {@link Issue} with the given {@code id}, if it exists.
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is unauthorized to perform this
   *     operation
   * @since 0.4.0
   */
  Optional<Issue> get(String id) throws IOException, UnauthorizedException;

  /**
   * Creates an {@link Issue issue} with the given {@code summary} and {@code description}.
   * 
   * @param summary the issue's summary
   * @param description the issue's description
   * @return the newly-created {@link Issue}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is unauthorized to perform this
   *     operation
   * @since 1.0.0
   */
  Issue create(String summary, String description) throws IOException, UnauthorizedException;

  /**
   * Creates an {@link Issue issue} with the given {@code summary}, {@code description}, and 
   * {@code fields}.
   * 
   * @param summary the issue's summary
   * @param description the issue's description
   * @param fields the issue's fields
   * @return the newly-created {@link Issue}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is unauthorized to perform this
   *     operation
   * @since 1.0.0
   */
  Issue create(String summary, String description, Map<Field, FieldValue> fields) 
      throws IOException, UnauthorizedException;
}
