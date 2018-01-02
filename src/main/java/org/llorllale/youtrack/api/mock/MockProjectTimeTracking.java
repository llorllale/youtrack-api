/*
 * Copyright 2017 George Aristy.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.llorllale.youtrack.api.mock;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Stream;
import org.llorllale.youtrack.api.Project;
import org.llorllale.youtrack.api.ProjectTimeTracking;
import org.llorllale.youtrack.api.TimeTrackEntryType;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Mock implementation of {@link ProjectTimeTracking} suitable for tests.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public final class MockProjectTimeTracking implements ProjectTimeTracking {
  private final Project project;
  private final Collection<TimeTrackEntryType> types;

  /**
   * Primary ctor.
   * 
   * <p>{@link #enabled() Enable} or disable timetracking by providing a non-empty/empty 
   * {@code types}.
   * 
   * @param project the associated project
   * @param types the types to configure for the associated project
   * @since 1.0.0
   */
  public MockProjectTimeTracking(Project project, Collection<TimeTrackEntryType> types) {
    this.project = project;
    this.types = types;
  }

  @Override
  public Project project() {
    return this.project;
  }

  @Override
  public boolean enabled() throws IOException, UnauthorizedException {
    return !this.types.isEmpty();
  }

  @Override
  public Stream<TimeTrackEntryType> types() throws IOException, UnauthorizedException {
    if (this.enabled()) {
      return this.types.stream();
    }

    throw new IOException("Disabled because no types were added. Always check enabled() first.");
  }
}
