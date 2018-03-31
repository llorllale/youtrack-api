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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.llorllale.youtrack.api.session.AuthenticationException;
import org.llorllale.youtrack.api.session.Login;
import org.llorllale.youtrack.api.session.Session;

/**
 * Reuses {@link Session sessions} if already authenticated.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
final class CachedLogin implements Login {
  private final Login origin;
  private final List<Session> cache = new ArrayList<>(0);

  /**
   * Ctor.
   * 
   * @param origin decorated login
   * @since 1.0.0
   */
  CachedLogin(Login origin) {
    this.origin = origin;
  }

  @Override
  public Session login() throws AuthenticationException, IOException {
    synchronized (this.cache) {
      if (this.cache.isEmpty()) {
        this.cache.add(this.origin.login());
      }
    }
    return this.cache.get(0);
  }
}
