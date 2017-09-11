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
 * Unit tests for {@link BasicSession}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
public class BasicSessionTest {
  /**
   * Test of baseUrl method, of class AuthenticatedSession.
   * @since 0.1.0
   */
  @Test
  public void baseURL() throws Exception {
    assertThat(new BasicSession(
            new URL("http://some.url"), 
            Collections.emptyList()
        ).baseUrl(),
        is(new URL("http://some.url"))
    );
  }
}