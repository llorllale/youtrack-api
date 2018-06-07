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

// @checkstyle AvoidStaticImport (3 lines)
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.hamcrest.core.IsEqual;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.session.Login;
import org.llorllale.youtrack.api.session.PermanentToken;

/**
 * Integration tests for {@link DefaultProjectTimeTracking}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 * @checkstyle AbbreviationAsWordInName (2 lines)
 */
public final class DefaultProjectTimeTrackingIT {
  private static Login login;
  private static Project project;

  /**
   * Setup.
   * @throws Exception unexpected
   */
  @BeforeClass
  public static void setup() throws Exception {
    final IntegrationTestsConfig config = new IntegrationTestsConfig();
    login = new PermanentToken(config.youtrackUrl(), config.youtrackUserToken());
    project = new DefaultYouTrack(login).projects().stream().findAny().get();
  }

  /**
   * Pre-configured project must have timetracking enabled.
   * 
   * @throws Exception unexpected
   */
  @Test
  public void testEnabled() throws Exception {
    assertTrue(
      new DefaultProjectTimeTracking(project, login).enabled()
    );
  }

  /**
   * Pre-configured project has timetracking types configured.
   * 
   * @throws Exception unexpected
   */
  @Test
  public void testTypes() throws Exception {
    assertThat(
      new DefaultProjectTimeTracking(project, login).types().count(),
      new IsEqual<>(greaterThan(0L))
    );
  }
}
