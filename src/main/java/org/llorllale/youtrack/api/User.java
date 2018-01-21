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
 * A registered user in YouTrack.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.5.0
 */
public interface User {
  /**
   * The user's full name.
   * 
   * @return the user's full name
   * @since 0.5.0
   */
  String name();

  /**
   * The user's email.
   * 
   * @return the user's email
   * @since 0.5.0
   */
  String email();

  /**
   * The user's login name.
   * 
   * @return the user's login name
   * @since 0.5.0
   */
  String loginName();
}
