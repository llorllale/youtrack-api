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

import java.util.Arrays;
import java.util.stream.Stream;
import org.llorllale.youtrack.api.Field;
import org.llorllale.youtrack.api.FieldValue;
import org.llorllale.youtrack.api.Project;
import org.llorllale.youtrack.api.ProjectField;

/**
 * Mock implementation of {@link ProjectField} suitable for tests.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public final class MockProjectField implements ProjectField {
  private final String name;
  private final Project project;
  private final Stream<FieldValue> values;

  /**
   * Primary ctor.
   * @param name this field's name
   * @param project the associated project
   * @param values this project field's set of possible values
   * @since 1.0.0
   */
  public MockProjectField(String name, Project project, FieldValue... values) {
    this.name = name;
    this.project = project;
    this.values = Arrays.asList(values).stream();
  }

  @Override
  public Stream<FieldValue> values() {
    return this.values;
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
    if (!(obj instanceof Field)) {
      return false;
    }

    final Field other = (Field) obj;
    return this.isSameField(other);
  }
}
