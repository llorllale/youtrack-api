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
 * An extended {@link Field} that provides its possible {@link #values() values}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 */
public interface ProjectField extends Field {
  /**
   * Returns all possible {@link FieldValue values} for this {@link Field}.
   * 
   * @return all possible {@link FieldValue values} for this {@link Field}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform
   *     this operation
   * @since 0.8.0
   */
  Stream<FieldValue> values() throws IOException, UnauthorizedException;
}
