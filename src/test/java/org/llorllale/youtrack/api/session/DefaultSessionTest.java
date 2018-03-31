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

// @checkstyle AvoidStaticImport (3 lines)
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * Unit tests for {@link DefaultSession}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 * @checkstyle MultipleStringLiterals (500 lines)
 */
public final class DefaultSessionTest {
  /**
   * Returns the base URL.
   * @throws Exception unexpected
   */
  @Test
  public void testBaseUrl() throws Exception {
    final URL url = new URL("http://some.url");

    assertThat(
      new DefaultSession(url).baseUrl(),
      is(url)
    );
  }

  /**
   * Returns the given cookies.
   * @throws Exception unexpected
   */
  @Test
  public void testCookies() throws Exception {
    final List<Cookie> cookies = Arrays.asList(
      new DefaultCookie("H1", "V1"),
      new DefaultCookie("H2", "V2")
    );

    assertThat(
      new DefaultSession(
        new URL("http://some.url"), 
        cookies
      ).cookies(),
      containsInAnyOrder(cookies.toArray(new Cookie[] {}))
    );
  }
}
