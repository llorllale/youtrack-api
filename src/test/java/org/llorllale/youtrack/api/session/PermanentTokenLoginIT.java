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

import static java.util.Optional.empty;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.IntegrationTestsConfig;
import org.llorllale.youtrack.api.issues.CreateIssue;

/**
 * Integration tests for {@link PermanentTokenLogin}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.3.0
 */
public class PermanentTokenLoginIT {
  private static IntegrationTestsConfig config;

  @BeforeClass
  public static void setup() throws Exception {
    config = new IntegrationTestsConfig();
  }

  /**
   * Logs in using a {@link PermanentToken} and creates an issue.
   * @throws Exception 
   * @since 0.3.0
   */
  @Test
  public void loginAndCreateIssue() throws Exception {
    assertThat(
        new CreateIssue(
            new PermanentTokenLogin(
                config.youtrackUrl(), 
                config.youtrackUserToken()
            ).login()
        ).forProjectId(config.youtrackTestProjectId())
            .withSummary("Test summary")
            .withDescription("Test description")
            .create(),
        is(not(empty()))
    );
  }
}
