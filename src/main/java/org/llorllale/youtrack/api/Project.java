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

import java.util.Optional;

/**
 * A YouTrack project.
 * 
 * <p>Two projects are equal only if both their {@link #id() ids} are equal.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public interface Project {
  /**
   * The project's id.
   * 
   * @return the project's id
   * @since 0.2.0
   */
  String id();

  /**
   * The project's name.
   * 
   * @return the project's name
   * @since 0.2.0
   */
  String name();

  /**
   * The project's description.
   * 
   * @return the project's optional description
   * @since 0.2.0
   */
  Optional<String> description();

  /**
   * This {@link Project}'s {@link Issue issues}.
   * 
   * @return this {@link Project}'s {@link Issue issues}
   * @since 0.4.0
   */
  Issues issues();

  /**
   * Returns the parent {@link YouTrack} instance.
   * 
   * @return the parent {@link YouTrack} instance
   * @since 0.6.0
   */
  YouTrack youtrack();

  /**
   * Returns the {@link Fields} API.
   * 
   * @return the {@link Fields} API
   * @since 0.8.0
   */
  Fields fields();

  /**
   * Access the project's {@link ProjectTimeTracking timetracking} settings.
   * 
   * @return the project's {@link ProjectTimeTracking timetracking} settings
   * @since 0.8.0
   */
  ProjectTimeTracking timetracking();

  /**
   * Access the {@link UsersOfProject} API for this {@link Project}.
   * 
   * @return the {@link UsersOfProject} API for this {@link Project}
   * @since 0.9.0
   */
  UsersOfProject users();
}
