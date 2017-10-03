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

package org.llorllale.youtrack.api;

import java.time.Duration;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.Issues.IssueSpec;
import org.llorllale.youtrack.api.IssueTimeTracking.EntrySpec;
import org.llorllale.youtrack.api.session.PermanentTokenLogin;
import org.llorllale.youtrack.api.session.Session;

/**
 * Integration tests for {@link DefaultIssueTimeTracking}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public class DefaultIssueTimeTrackingIT {
  private static IntegrationTestsConfig config;
  private static Session session;
  private static Issue issue;

  @BeforeClass
  public static void setup() throws Exception {
    config = new IntegrationTestsConfig();

    session = new PermanentTokenLogin(
        config.youtrackUrl(), 
        config.youtrackUserToken()
    ).login();

    issue = new DefaultYouTrack(session)
        .projects()
        .stream()
        .findFirst()
        .get()
        .issues()
        .create(new IssueSpec(DefaultIssueTimeTrackingIT.class.getSimpleName(), "Description"));
  }

  @Test
  public void createAndCountAll() throws Exception {
    assertThat(new DefaultIssueTimeTracking(session, issue)
            .create(new EntrySpec(Duration.ofMinutes(45)))
            .create(new EntrySpec(Duration.ofHours(1)))
            .stream()
            .count(),
        is(2L)
    );
  }
}