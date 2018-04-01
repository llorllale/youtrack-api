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

package org.llorllale.youtrack.api;

// @checkstyle AvoidStaticImport (2 lines)
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import org.junit.Test;
import org.llorllale.youtrack.api.mock.http.MockSession;
import org.llorllale.youtrack.api.session.AuthenticationException;
import org.llorllale.youtrack.api.session.Login;
import org.llorllale.youtrack.api.session.Session;

/**
 * Unit tests for {@link CachedLogin}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 * @checkstyle MethodName (500 lines)
 */
public final class CachedLoginTest {
  /**
   * Must return the same cached session object.
   * @throws Exception unexpected
   */
  @Test
  public void returnsSameSession() throws Exception {
    final Session session = new MockSession();
    final Login login = new CachedLogin(() -> session);
    assertThat(
      login.login(),
      is(session)
    );
    assertThat(
      login.login(),
      is(session)
    );
  }

  /**
   * Propagates the origin's authentication error.
   * @throws Exception auth error
   */
  @Test(expected = AuthenticationException.class)
  public void propagatesAuthException() throws Exception {
    new CachedLogin(() -> {
      throw new AuthenticationException(null);
    }).login();
  }

  /**
   * Propagates the origin's io error.
   * @throws Exception io error
   */
  @Test(expected = IOException.class)
  public void propagatesIoException() throws Exception {
    new CachedLogin(() -> {
      throw new IOException();
    }).login();
  }
}
