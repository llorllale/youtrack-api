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

import org.apache.http.client.HttpClient;
import org.llorllale.youtrack.api.session.Login;

/**
 * Default implementation of {@link YouTrack}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public final class DefaultYouTrack implements YouTrack {
  private final Login login;
  private final HttpClient client;

  /**
   * Ctor. Uses the {@link DefaultClient} with a pool size of {@code 10}.
   * @param login the user's {@link Login}
   * @since 0.4.0
   */
  public DefaultYouTrack(Login login) {
    // @checkstyle MagicNumber (1 line)
    this(login, new DefaultClient(10));
  }

  /**
   * Ctor.
   * @param login the user's {@link Login}
   * @param httpClient the {@link HttpClient} to use
   * @since 1.1.0
   * @todo #228 Continue making all implementations accept an HttpClient in order to enable
   *  concurrency. Right now major parts of the API are hanging when multiple threads are
   *  accessing them.
   */
  public DefaultYouTrack(Login login, HttpClient httpClient) {
    this.login = login;
    this.client = httpClient;
  }

  @Override
  public Projects projects() {
    return new DefaultProjects(this, this.login, this.client);
  }
}
