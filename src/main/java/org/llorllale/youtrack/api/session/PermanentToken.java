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

import java.io.IOException;
import java.net.URL;

/**
 * A {@link Login} that makes use of YouTrack's <em>permanent tokens</em> feature to authorize 
 * 3rd party application integrations.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.3.0
 */
public final class PermanentToken implements Login {
  private final URL youtrackUrl;
  private final String token;

  /**
   * Ctor.
   * 
   * @param youtrackUrl the YouTrack API's url
   * @param token the YouTrack user's permanent token
   * @since 0.3.0
   */
  public PermanentToken(URL youtrackUrl, String token) {
    this.youtrackUrl = youtrackUrl;
    this.token = token;
  }

  @Override
  public Session session() throws AuthenticationException, IOException {
    return new DefaultSession(
        this.youtrackUrl, 
        new DefaultCookie(
            "Authorization", 
            "Bearer ".concat(this.token)
        )
    );
  }
}
