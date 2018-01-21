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

package org.llorllale.youtrack.api.session;

import java.net.URL;
import java.util.List;

/**
 * <p>
 * A {@code Session} object encapsulates all the state required for conducting
 * further transactions with the remote YouTrack API.
 * </p>
 * 
 * <p>
 * Instances of {@code Session} are obtained by {@link Login#login() login} in
 * to YouTrack.
 * </p>
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @see Login
 * @since 0.1.0
 */
public interface Session {
  /**
   * The base endpoint URL of the remote YouTrack API.
   * 
   * @return The base endpoint URL of the remote YouTrack API.
   * @since 0.1.0
   */
  URL baseUrl();

  /**
   * Session state.
   * 
   * @return The session's state.
   * @since 0.1.0
   */
  List<Cookie> cookies();
}
