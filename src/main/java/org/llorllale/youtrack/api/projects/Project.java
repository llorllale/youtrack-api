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

package org.llorllale.youtrack.api.projects;

import java.util.Optional;
import java.util.Set;

/**
 * A YouTrack project.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.2.0
 */
public interface Project {
  /**
   * The project's ID.
   * @return the project's ID
   * @since 0.2.0
   */
  public String id();

  /**
   * The project's name.
   * @return the project's name
   * @since 0.2.0
   */
  public String name();

  /**
   * The project's description.
   * @return the project's optional description
   * @since 0.2.0
   */
  public Optional<String> description();

  /**
   * The full name of the persons assigned to this project.
   * @return the full name of the persons assigned to this project
   * @since 0.2.0
   */
  public Set<String> nameOfAssignees();
}
