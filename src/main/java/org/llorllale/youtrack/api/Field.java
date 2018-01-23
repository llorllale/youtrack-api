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

/**
 * A property of {@link Issue issues}.
 * 
 * <p>Fields are configured on a per-{@link Project project} basis.</p>
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 */
public interface Field {
  /**
   * Returns the parent {@link Project}.
   * 
   * @return the parent {@link Project}
   * @since 0.8.0
   */
  Project project();

  /**
   * Returns the name of this {@link Field}.
   * 
   * @return the name of this {@link Field}
   * @since 0.8.0
   */
  String name();

  /**
   * Whether this and {@code other} both represent the same field.
   * 
   * @param other other {@link Field field}
   * @return {@code true} if {@code this} and {@code other} both represent the same field, 
   *     {@code false} otherwise
   * @since 0.8.0
   */
  default boolean isSameField(Field other) {
    return this.name().equals(other.name()) && this.project().equals(other.project());
  }
}
