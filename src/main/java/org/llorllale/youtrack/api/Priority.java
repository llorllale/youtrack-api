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

/**
 * A priority configured in the YouTrack system.
 * 
 * <p>Instances of {@link Priority} have not necessarily been assigned to an {@link Issue}.</p>
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.6.0
 * @see AssignedPriority
 */
public interface Priority {
  /**
   * Returns a string representation of this {@link Priority}.
   * @return a string representation of this {@link Priority}
   * @since 0.6.0
   */
  public String asString();
}
