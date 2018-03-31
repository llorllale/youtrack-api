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

// @checkstyle AvoidStaticImport (2 lines)
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import org.junit.Test;

/**
 * Unit tests for {@link PermanentToken}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 * @checkstyle MultipleStringLiterals (500 lines)
 */
public final class PermanentTokenTest {
  /**
   * Returns session upon login.
   * @throws Exception unexpected
   */
  @Test
  public void login() throws Exception {
    final String token = "abc123";
    assertNotNull(
      new PermanentToken(
        new URL("http://some.url"), 
        token
      ).login()
    );
  }

  /**
   * Session has Authorization header after login.
   * @throws Exception unexpected
   */
  @Test
  public void loginHeader() throws Exception {
    final String token = "abc123";
    assertTrue(
      new PermanentToken(
        new URL("http://some.url"), 
          token
      ).login()
        .cookies()
        .stream()
        .allMatch(
          c -> "Authorization".equals(c.name())
            && "Bearer ".concat(token).equals(c.value())
        )
    );
  }
}
