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
 * Default impl of {@link Field}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 */
final class BasicField implements Field {
  private final String name;
  private final Project project;

  /**
   * Primary ctor.
   * 
   * @param name the field's name
   * @param project the parent {@link Project}
   * @since 0.8.0
   */
  BasicField(String name, Project project) {
    this.name = name;
    this.project = project;
  }

  @Override
  public Project project() {
    return this.project;
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public int hashCode() {
    return this.name().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Field)) {
      return false;
    }

    final Field other = (Field) obj;
    return this.isSameField(other);
  }
}
