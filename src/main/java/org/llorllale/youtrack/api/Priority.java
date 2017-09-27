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

import org.llorllale.youtrack.api.session.UnauthorizedException;

import java.io.IOException;
import java.util.Optional;
import org.llorllale.youtrack.api.util.DataTransferObject;

/**
 * A priority configured in the YouTrack system.
 * 
 * <p>Instances of {@link Priority} have not necessarily been assigned to an {@link Issue}.</p>
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @param <T>
 * @since 0.6.0
 * @see AssignedPriority
 */
public interface Priority<T> extends Comparable<Priority<T>>, DataTransferObject<T> {
  /**
   * Returns a string representation of this {@link Priority}.
   * @return a string representation of this {@link Priority}
   * @since 0.6.0
   */
  public String asString();

  /**
   * Returns the result of passing this {@link Priority} to 
   * {@link Priorities#lower(org.llorllale.youtrack.api.Priority)}.
   * @return the result of passing this {@link Priority} to 
   *     {@link Priorities#lower(org.llorllale.youtrack.api.Priority)}
   * @throws IOException
   * @throws UnauthorizedException 
   * @since 0.6.0
   */
  public Priority<T> lower() throws IOException, UnauthorizedException;

  public Priority<T> higher() throws IOException, UnauthorizedException;
}
