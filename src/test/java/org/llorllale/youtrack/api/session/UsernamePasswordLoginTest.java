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

import java.net.URL;
import org.apache.http.message.BasicHeader;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.llorllale.youtrack.api.mock.http.MockHttpClient;
import org.llorllale.youtrack.api.mock.http.response.MockForbiddenResponse;
import org.llorllale.youtrack.api.mock.http.response.MockOkResponse;

/**
 * Unit tests for {@link UsernamePasswordLogin}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public class UsernamePasswordLoginTest {

  /**
   * Login should be successful if remote API response is 200
   *
   * @throws Exception
   * @since 0.1.0
   */
  @Test
  public void successfulLogin() throws Exception {
    assertNotNull(
        new UsernamePasswordLogin(
            new URL("http://some.url"),
            "test",
            "123".toCharArray(),
            new MockHttpClient(
                new MockOkResponse(
                    new BasicHeader("Set-Cookie", "123")
                )
            )
        ).login()
    );
  }

  /**
   * Authentication error if remote API response is different from 200
   *
   * @throws Exception
   * @since 0.1.0
   */
  @Test(expected = AuthenticationException.class)
  public void authenticationError() throws Exception {
    new UsernamePasswordLogin(
        new URL("http://some.url"),
        "test",
        "123".toCharArray(),
        new MockHttpClient(
            new MockForbiddenResponse()
        )
    ).login();
  }

  /**
   * Fix for issue 13: Incorrect handling of session cookies
   *
   * @since 0.1.0
   */
  @Test
  public void correctHandlingOfCookieNames() throws Exception {
    assertTrue(
        new UsernamePasswordLogin(
            new URL("http://some.url"),
            "test",
            "test123".toCharArray(),
            new MockHttpClient(
                new MockOkResponse(
                    new BasicHeader(
                        "Set-Cookie",
                        "YTSESSIONID=1pjvfsojr5pch12i3cx6509n61;Path=/;HttpOnly"
                    ),
                    new BasicHeader(
                        "Set-Cookie",
                        "jetbrains.charisma.main.security.PRINCIPAL=OTE1ZGZmMzRiMDEwY2MzMzhiNmZiMTM5Y2IwYzM1NTUzNzQ3MWRjMmJlNmNkM2QxNmViNmYzZTNkYmIwNDQ1NTpyb290;Path=/;Expires=Wed, 15-Aug-2018 18:54:08 GMT"
                    )
                )
            )
        ).login()
            .cookies()
            .stream()
            .allMatch(c -> "Cookie".equals(c.name()))
    );
  }

  /**
   * Fix for issue 13: Incorrect handling of session cookies
   *
   * @since 0.1.0
   */
  @Test
  public void correctHandlingOfCookieValues() throws Exception {
    assertTrue(
        new UsernamePasswordLogin(
            new URL("http://some.url"),
            "test",
            "test123".toCharArray(),
            new MockHttpClient(
                new MockOkResponse(
                    new BasicHeader(
                        "Set-Cookie",
                        "YTSESSIONID=1pjvfsojr5pch12i3cx6509n61;Path=/;HttpOnly"
                    ),
                    new BasicHeader(
                        "Set-Cookie",
                        "jetbrains.charisma.main.security.PRINCIPAL=OTE1ZGZmMzRiMDEwY2MzMzhiNmZiMTM5Y2IwYzM1NTUzNzQ3MWRjMmJlNmNkM2QxNmViNmYzZTNkYmIwNDQ1NTpyb290;Path=/;Expires=Wed, 15-Aug-2018 18:54:08 GMT"
                    )
                )
            )
        ).login()
            .cookies()
            .stream()
            .allMatch(
                c -> "YTSESSIONID=1pjvfsojr5pch12i3cx6509n61; jetbrains.charisma.main.security.PRINCIPAL=OTE1ZGZmMzRiMDEwY2MzMzhiNmZiMTM5Y2IwYzM1NTUzNzQ3MWRjMmJlNmNkM2QxNmViNmYzZTNkYmIwNDQ1NTpyb290".equals(c.value())
            )
    );
  }
}
