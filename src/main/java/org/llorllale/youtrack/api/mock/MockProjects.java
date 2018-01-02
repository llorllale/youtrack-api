/*
 * Copyright 2018 George Aristy.
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
import java.util.Optional;
import java.util.stream.Stream;
import org.llorllale.youtrack.api.Project;
import org.llorllale.youtrack.api.Projects;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Mock implementation of {@link Projects} suitable for tests.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public final class MockProjects implements Projects {
  private final Collection<Project> projects;

  /**
   * Ctor.
   * 
   * @param projects the projects
   * @since 1.0.0
   */
  public MockProjects(Collection<Project> projects) {
    this.projects = projects;
  }

  @Override
  public Stream<Project> stream() throws IOException, UnauthorizedException {
    return this.projects.stream();
  }

  @Override
  public Optional<Project> get(String id) throws IOException, UnauthorizedException {
    return this.stream()
        .filter(p -> id.equals(p.id()))
        .findAny();
  }
}
