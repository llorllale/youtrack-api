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

package org.llorllale.youtrack.api.mock;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.llorllale.youtrack.api.session.Session;

/**
 * A mock {@link Session} suitable for unit tests.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
public class MockAuthenticatedSession implements Session {
  private final URL baseUrl;
  private final List<Header> cookies;

  /**
   * 
   * @param baseUrl
   * @since 0.1.0
   */
  public MockAuthenticatedSession(URL baseUrl) {
    this.baseUrl = baseUrl;
    this.cookies = new ArrayList<>();
    this.cookies.add(new BasicHeader("Set-Cookie", "12345"));
    this.cookies.add(new BasicHeader("Set-Cookie", "98273"));
  }

  @Override
  public URL baseUrl() {
    return baseUrl;
  }

  @Override
  public List<Header> cookies() {
    return Collections.unmodifiableList(cookies);
  }
}
