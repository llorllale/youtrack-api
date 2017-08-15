/* 
 * Copyright 2017 George Aristy (george.aristy@gmail.com).
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

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A {@link Session} obtained from a {@link Login} that requires user 
 * authentication (ie. not {@link AnonymousLogin}).
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
public class AuthenticatedSession implements Session {
  private final URL youtrackUrl;
  private final List<Header> cookies;

  /**
   * Base constructor.
   * @param youtrackUrl the remote API url
   * @param headers the session's state
   * @since 0.1.0
   */
  public AuthenticatedSession(URL youtrackUrl, List<Header> headers) {
    this.youtrackUrl = youtrackUrl;
    this.cookies = new ArrayList<>();
    headers.stream()
        .filter(h -> "Set-Cookie".equals(h.getName()))
        .map(h -> new BasicHeader("Cookie", h.getValue()))
        .map(h -> new BasicHeader("Cookie", h.getValue().split(";")[0]))
        .reduce((h1, h2) -> new BasicHeader("Cookie", h1.getValue()
            .concat(";")
            .concat(h2.getValue()))
        ).ifPresent(this.cookies::add);
  }

  @Override
  public URL baseUrl() {
    return youtrackUrl;
  }

  @Override
  public List<Header> cookies() {
    return Collections.unmodifiableList(cookies);
  }
}