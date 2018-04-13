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
import java.util.Collection;
import java.util.stream.Stream;
import org.llorllale.youtrack.api.Fields;
import org.llorllale.youtrack.api.Project;
import org.llorllale.youtrack.api.ProjectField;

/**
 * Mock implementation of {@link Fields} suitable for tests.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public final class MockFields implements Fields {
  private final Project project;
  private final Stream<ProjectField> stream;

  /**
   * Primary ctor.
   * @param project the associated project
   * @param fields the {@link ProjectField fields} to be produced by {@link #stream()}
   * @since 1.0.0
   */
  public MockFields(Project project, Collection<ProjectField> fields) {
    this.project = project;
    this.stream = fields.stream();
  }

  /**
   * Ctor.
   * 
   * <p>Same as calling {@code new MockFields(project, Arrays.asList(fields))}.
   * @param project the associated project
   * @param fields the {@link ProjectField fields} to be produced by {@link #stream()}
   * @since 1.0.0
   */
  public MockFields(Project project, ProjectField... fields) {
    this(project, Arrays.asList(fields));
  }

  @Override
  public Project project() {
    return this.project;
  }

  @Override
  public Stream<ProjectField> stream() {
    return this.stream;
  }
}
