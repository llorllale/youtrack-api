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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.Issues.IssueSpec;
import org.llorllale.youtrack.api.session.PermanentTokenLogin;
import org.llorllale.youtrack.api.session.Session;

/**
 * Integration tests for {@link XmlAssignedState}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.7.0
 */
public class XmlAssignedStateIT {
  private static IntegrationTestsConfig config;
  private static Session session;
  private static Issue issueChangeTo;
  private static Issue issueResolved;

  @BeforeClass
  public static void setup() throws Exception {
    config = new IntegrationTestsConfig();
    session = new PermanentTokenLogin(config.youtrackUrl(), config.youtrackUserToken()).login();
    issueChangeTo = new DefaultYouTrack(session).projects().stream()
        .findAny()
        .get()
        .issues()
        .create(
            new IssueSpec(
                XmlAssignedStateIT.class.getSimpleName().concat(".testChangeTo"), 
                "integration tests"
            )
        );
    issueResolved = new DefaultYouTrack(session).projects().stream()
        .findAny()
        .get()
        .issues()
        .create(
            new IssueSpec(
                XmlAssignedStateIT.class.getSimpleName().concat(".testResolved"), 
                "integration tests"
            )
        );
  }

  /**
   * Tests the resolved method.
   * 
   * Assumption: YouTrack creates issues in the "Open" state by default.
   * 
   * @throws Exception 
   * @since 0.7.0
   */
  @Test
  public void testResolved() throws Exception {
    final State resolving = issueResolved.project().youtrack().states()
        .resolving()
        .findAny()
        .get();

    assertFalse(
        new XmlAssignedState(issueChangeTo, session).resolved()
    );

    assertTrue(
        new XmlAssignedState(issueChangeTo, session).changeTo(resolving).resolved()
    );
  }

  @Test
  public void testChangeTo() throws Exception {
    final State initial = issueChangeTo.state();
    final State other = issueChangeTo.project().youtrack().states()
        .stream()
        .filter(s -> s.asString().equals(initial.asString()))
        .findAny()
        .get();

    assertThat(
        new XmlAssignedState(issueChangeTo, session).changeTo(other).asString(),
        is(not(initial.asString()))
    );
  }
}
