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
 * Access to all the {@link Field fields} of a {@link Project project} that are configured for
 * {@link Issue issues}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 */
public interface Fields {
  /**
   * Returns the {@link Project} to which this {@link Fields} belongs.
   * 
   * @return the {@link Project} to which this {@link Fields} belongs
   * @since 0.8.0
   */
  Project project();

  /**
   * Returns a stream of all configured {@link ProjectField fields} for this {@link Project}.
   * 
   * @return a stream of all configured {@link ProjectField fields} for this {@link Project}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform
   *     this operation
   * @since 0.8.0
   */
  Stream<ProjectField> stream() throws IOException, UnauthorizedException;
}
