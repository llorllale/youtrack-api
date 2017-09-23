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

package org.llorllale.youtrack.api.session;

import java.net.URL;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Unit tests for {@link PermanentTokenLogin}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public class PermanentTokenLoginTest {
  @Test
  public void login() throws Exception {
    final String token = "abc123";
    assertNotNull(
        new PermanentTokenLogin(
            new URL("http://some.url"), 
            token
        ).login()
    );
  }

  @Test
  public void loginHeader() throws Exception {
    final String token = "abc123";

    assertTrue(
        new PermanentTokenLogin(
            new URL("http://some.url"), 
            token
        ).login()
            .cookies()
            .stream()
            .allMatch(
                h -> "Authorization".equals(h.getName()) && 
                     "Bearer ".concat(token).equals(h.getValue())
            )
    );
  }
}