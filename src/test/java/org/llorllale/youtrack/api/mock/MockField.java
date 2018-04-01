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

package org.llorllale.youtrack.api.mock;

import org.llorllale.youtrack.api.Field;
import org.llorllale.youtrack.api.Project;

/**
 * Mock implementation of {@link Field} suitable for tests.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public final class MockField implements Field {
  private final String name;
  private final Project project;

  /**
   * Ctor.
   *
   * @param name the field's name
   * @param project the project to which this field belongs to
   * @since 1.0.0
   */
  public MockField(String name, Project project) {
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
  public boolean equals(Object object) {
    if (!(object instanceof Field)) {
      return false;
    }

    final Field other = (Field) object;
    return this.isSameField(other);
  }

  @Override
  public int hashCode() {
    return this.name().hashCode();
  }
}
