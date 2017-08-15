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
import java.util.List;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author George Aristy george.aristy AT gmail DOT com
 */
public class AuthenticatedSessionTest {
  /**
   * Test of baseUrl method, of class AuthenticatedSession.
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
   * Test of cookies method, of class AuthenticatedSession.
   */
  @Test
  public void cookies() throws Exception {
    final List<Header> cookies = Arrays.asList(
            new BasicHeader("h1", "12345"),
            new BasicHeader("h2", "432665"),
            new BasicHeader("h3", "2134982")
    );
    assertThat(
        new AuthenticatedSession(new URL("http://some.url"), cookies).cookies(),
        containsInAnyOrder(cookies.toArray(new Header[]{}))
    );
  }
}