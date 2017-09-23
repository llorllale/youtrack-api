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
import java.util.Arrays;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for {@link DefaultSession}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public class DefaultSessionTest {
  @Test
  public void testBaseUrl() throws Exception {
    final URL url = new URL("http://some.url");

    assertThat(
        new DefaultSession(url).baseUrl(),
        is(url)
    );
  }

  @Test
  public void testCookies() throws Exception {
    final List<Header> headers = Arrays.asList(
        new BasicHeader("H1", "V1"),
        new BasicHeader("H2", "V2")
    );

    assertThat(
        new DefaultSession(
            new URL("http://some.url"), 
            headers
        ).cookies(),
        containsInAnyOrder(headers.toArray(new Header[]{}))
    );
  }
}