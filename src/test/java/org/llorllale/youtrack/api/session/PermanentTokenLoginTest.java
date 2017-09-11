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
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Unit tests for {@link PermanentTokenLogin}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.3.0
 */
public class PermanentTokenLoginTest {
  /**
   * Test same youtrack URL is returned in session.
   * @throws Exception 
   * @since 0.3.0
   */
  @Test
  public void assertBaseUrl() throws Exception {
    final URL youtrackUrl = new URL("http://some.url");
    assertThat(
        new PermanentTokenLogin(
            youtrackUrl, 
            "123234345tlkjslfkjglkew4jl3k2j"
        ).login()
            .baseUrl(),
        is(youtrackUrl)
    );
  }

  /**
   * Verify correct http header name.
   * @throws Exception 
   * @since 0.3.0
   */
  @Test
  public void assertTokenHeaderName() throws Exception {
    assertThat(
        new PermanentTokenLogin(
            new URL("http://some.url"),
            "123456"
        ).login()
            .cookies()
            .stream()
            .map(h -> h.getName())
            .collect(toList()),
        hasItem("Authorization")
    );
  }

  /**
   * Correct http header value.
   * @throws Exception 
   * @since 0.3.0
   */
  @Test
  public void assertTokenHeaderValue() throws Exception {
    final String token = "aslkjf Q#$KJTl3k5tj l2kj";
    assertThat(
        new PermanentTokenLogin(
            new URL("http://some.url"),
            token
        ).login()
            .cookies()
            .stream()
            .map(h -> h.getValue())
            .collect(toList()),
        hasItem("Bearer ".concat(token))
    );
  }
}