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

package org.llorllale.youtrack.api.mock.http;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import org.llorllale.youtrack.api.session.Cookie;
import org.llorllale.youtrack.api.session.Session;

/**
 * Mock implementation of {@link Session}, suitable for unit tests.
 * Sets {@link #baseUrl() baseUrl} to {@code http://some.url}, and no {@link #cookies() cookies} 
 * are present.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public final class MockSession implements Session {
  @Override
  public URL baseUrl() {
    try {
      return new URL("http://some.url");
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Cookie> cookies() {
    return Collections.emptyList();
  }
}
