/*
 * Copyright 2017 George Aristy.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.llorllale.youtrack.api;

import org.llorllale.youtrack.api.session.UnauthorizedException;

import java.io.IOException;
import java.util.List;

/**
 * Fetches {@link Project projects} from the YouTrack server.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public interface Projects {
  /**
   * All accessible {@link Project projects} by the user's {@link Session}.
   * @return all accessible {@link Project projects} by the user's {@link Session}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to access this
   *     resource
   * @since 0.4.0
   */
  public List<Project> all() throws IOException, UnauthorizedException;
}
