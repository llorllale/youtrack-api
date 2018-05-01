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

import org.llorllale.youtrack.api.session.Login;

/**
 * Default implementation of {@link YouTrack}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 * @todo #166 Continue making all implementations accept a Login instead of a Session.
 *  Wrap the given login in the new CachedLogin implementation.
 */
public final class DefaultYouTrack implements YouTrack {
  private final Login login;

  /**
   * Primary ctor.
   * @param login the user's {@link Login}
   * @since 0.4.0
   */
  public DefaultYouTrack(Login login) {
    this.login = new CachedLogin(login);
  }

  @Override
  public Projects projects() {
    return new DefaultProjects(this, this.login);
  }
}
