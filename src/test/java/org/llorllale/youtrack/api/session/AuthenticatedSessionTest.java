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
import java.util.Arrays;
import java.util.Collections;
import org.apache.http.message.BasicHeader;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for {@link AuthenticatedSession}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
public class AuthenticatedSessionTest {
  /**
   * Test of baseUrl method, of class AuthenticatedSession.
   * @since 0.1.0
   */
  @Test
  public void baseURL() throws Exception {
    assertThat(
        new AuthenticatedSession(
            new URL("http://some.url"), 
            Collections.emptyList()
        ).baseUrl(),
        is(new URL("http://some.url"))
    );
  }

  /**
   * Fix for issue 13: Incorrect handling of session cookies
   * @since 0.1.0
   */
  @Test
  public void correctHandlingOfCookieNames() throws Exception {
    assertTrue(
        new AuthenticatedSession(
            new URL("http://some.url"), 
            Arrays.asList(
                new BasicHeader("Set-Cookie", "Set-Cookie: YTSESSIONID=1pjvfsojr5pch12i3cx6509n61;Path=/;HttpOnly"),
                new BasicHeader("Set-Cookie", "jetbrains.charisma.main.security.PRINCIPAL=OTE1ZGZmMzRiMDEwY2MzMzhiNmZiMTM5Y2IwYzM1NTUzNzQ3MWRjMmJlNmNkM2QxNmViNmYzZTNkYmIwNDQ1NTpyb290;Path=/;Expires=Wed, 15-Aug-2018 18:54:08 GMT")
            )
        ).cookies()
        .stream()
        .allMatch(h -> "Cookie".equals(h.getName()))
    );
  }

  /**
   * Fix for issue 13: Incorrect handling of session cookies
   * @since 0.1.0
   */
  @Test
  public void correctHandlingOfCookieValues() throws Exception {
    assertTrue(
        new AuthenticatedSession(
            new URL("http://some.url"), 
            Arrays.asList(
                new BasicHeader("Set-Cookie", "YTSESSIONID=1pjvfsojr5pch12i3cx6509n61;Path=/;HttpOnly"),
                new BasicHeader("Set-Cookie", "jetbrains.charisma.main.security.PRINCIPAL=OTE1ZGZmMzRiMDEwY2MzMzhiNmZiMTM5Y2IwYzM1NTUzNzQ3MWRjMmJlNmNkM2QxNmViNmYzZTNkYmIwNDQ1NTpyb290;Path=/;Expires=Wed, 15-Aug-2018 18:54:08 GMT")
            )
        ).cookies()
        .stream()
        .allMatch(h -> 
            "YTSESSIONID=1pjvfsojr5pch12i3cx6509n61; jetbrains.charisma.main.security.PRINCIPAL=OTE1ZGZmMzRiMDEwY2MzMzhiNmZiMTM5Y2IwYzM1NTUzNzQ3MWRjMmJlNmNkM2QxNmViNmYzZTNkYmIwNDQ1NTpyb290".equals(h.getValue())
        )
    );
  }
}