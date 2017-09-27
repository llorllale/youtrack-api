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

import org.llorllale.youtrack.api.session.Session;

/**
 * Default implementation of {@link YouTrack}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public class DefaultYouTrack implements YouTrack {
  private final Session session;

  /**
   * Primary ctor.
   * @param session the user's {@link Session}
   * @since 0.4.0
   */
  public DefaultYouTrack(Session session) {
    this.session = session;
  }

  @Override
  public Projects projects() {
    return new DefaultProjects(session);
  }

  @Override
  public Priorities priorities() {
    return new DefaultPriorities(session);
  }
}
