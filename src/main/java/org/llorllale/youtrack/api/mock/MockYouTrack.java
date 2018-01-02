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

import org.llorllale.youtrack.api.Projects;
import org.llorllale.youtrack.api.YouTrack;

/**
 * Mock implementation of {@link YouTrack} suitable for tests.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public final class MockYouTrack implements YouTrack {
  private final Projects projects;

  /**
   * Ctor.
   * 
   * @param projects implementation of {@link Projects}
   * @see MockProjects
   * @since 1.0.0
   */
  public MockYouTrack(Projects projects) {
    this.projects = projects;
  }

  @Override
  public Projects projects() {
    return this.projects;
  }
}
