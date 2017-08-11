/**
 * Copyright 2017 George Aristy
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
package org.llorllale.youtrack.api.session;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import org.apache.http.Header;

/**
 * <p>
 * A {@link Session} created from an {@link AnonymousLogin}.
 * </p>
 * 
 * No {@link #cookies() cookies} are stored by an anonymous session because none
 * are needed.
 * @author George Aristy
 * @since 1.0.0
 * @see AnonymousLogin
 */
public class AnonymousSession implements Session {
  private final URL youtrackURL;

  /**
   * Primary constructor.
   * @param youtrackURL The remote API url.
   * @since 1.0.0
   * @see IsOkResponse
   */
  public AnonymousSession(URL youtrackURL) {
    this.youtrackURL = youtrackURL;
  }
 
  @Override
  public URL baseURL() {
    return youtrackURL;
  }

  @Override
  public List<Header> cookies() {
    return Collections.emptyList();
  }
}