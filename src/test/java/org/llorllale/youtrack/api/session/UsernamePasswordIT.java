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

// @checkstyle AvoidStaticImport (5 lines)
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.llorllale.youtrack.api.IntegrationTestsConfig;

/**
 * Integration tests for {@link UsernamePassword}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 * @checkstyle AbbreviationAsWordInName (500 lines)
 */
public final class UsernamePasswordIT {
  /**
   * Returns Session when logging in.
   * @throws Exception unexpected
   */
  @Test
  public void login() throws Exception {
    final IntegrationTestsConfig config = new IntegrationTestsConfig();
    assertThat(
      new UsernamePassword(
        config.youtrackUrl(), 
        config.youtrackUser(), 
        config.youtrackPwd()
      ).login()
        .cookies(),
      is(not(empty()))
    );
  }

  /**
   * Fix #126: UsernamePassword: NPE when login() multiple times.
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void multipleLogins() throws Exception {
    final IntegrationTestsConfig config = new IntegrationTestsConfig();
    final Login login = new UsernamePassword(
      config.youtrackUrl(), 
      config.youtrackUser(), 
      config.youtrackPwd()
    );
    login.login();
    assertThat(
      login.login().cookies(),
      is(not(empty()))
    );
  }
}
