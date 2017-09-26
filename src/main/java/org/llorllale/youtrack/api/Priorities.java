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

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * API for accessing YouTrack {@link Priority priorities} at the global level.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.6.0
 */
public interface Priorities {
  /**
   * Returns a stream with all configured {@link Priority priorities} assignable to 
   * {@link Issue issues}.
   * 
   * @return stream with all configured {@link Priority priorities} assignable to 
   *     {@link Issue issues}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 0.6.0
   */
  public Stream<Priority> stream() throws IOException, UnauthorizedException;

  /**
   * Returns the lowest configured {@link Priority}
   * 
   * @return the lowest configured {@link Priority}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 0.6.0
   */
  public Priority lowest() throws IOException, UnauthorizedException;

  /**
   * Returns the highest configured {@link Priority}
   * 
   * @return the highest configured {@link Priority}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 0.6.0
   */
  public Priority highest() throws IOException, UnauthorizedException;

  /**
   * Returns the next lower {@link Priority}, if one exists.
   * 
   * @param start the priority to compare against
   * @return the next lower {@link Priority}, if one exists
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 0.6.0
   */
  public Optional<Priority> lower(Priority start) throws IOException, UnauthorizedException;

  /**
   * Returns the next higher {@link Priority}, if one exists.
   * 
   * @param start the priority to compare against
   * @return the next higher {@link Priority}, if one exists
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 0.6.0
   */
  public Optional<Priority> higher(Priority start) throws IOException, UnauthorizedException;
}
