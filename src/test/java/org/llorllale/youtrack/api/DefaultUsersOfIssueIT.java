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
import static org.junit.Assert.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.Issues.IssueSpec;
import org.llorllale.youtrack.api.mock.MockUser;
import org.llorllale.youtrack.api.session.PermanentTokenLogin;
import org.llorllale.youtrack.api.session.Session;

/**
 * Integration tests for {@link DefaultUsersOfIssue}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public class DefaultUsersOfIssueIT {
  private static IntegrationTestsConfig config;
  private static Session session;

  @BeforeClass
  public static void setup() throws Exception {
    config = new IntegrationTestsConfig();

    session = new PermanentTokenLogin(
        config.youtrackUrl(), 
        config.youtrackUserToken()
    ).login();
  }

  @Test
  public void testCreator() throws Exception {
    assertThat(
        new DefaultUsersOfIssue(
            session, 
            issue(DefaultUsersOfIssueIT.class.getSimpleName().concat(".testCreator"))
        ).creator().loginName(),
        is(config.youtrackUser())
    );
  }

  @Test
  public void testAssignToAndUpdater() throws Exception {
    final Issue issue = new DefaultUsersOfIssue(
        session, 
        issue(DefaultUsersOfIssueIT.class.getSimpleName().concat(".testAssignToAndUpdater"))
    ).assignTo(
        new MockUser(
            config.youtrackUser(), 
            "test@test.com", 
            config.youtrackUser()
        )
    ).issue();

    assertThat(
        new DefaultUsersOfIssue(session, issue.refresh())
            .updater().get()
            .name(),
        is(config.youtrackUser())
    );
  }

  @Test
  public void testAssignToAndAssignee() throws Exception {
    final Issue issue = new DefaultUsersOfIssue(
        session, 
        issue(DefaultUsersOfIssueIT.class.getSimpleName().concat(".testAssignToAndAssignee"))
    ).assignTo(
        new MockUser(
            config.youtrackUser(), 
            "test@test.com", 
            config.youtrackUser()
        )
    ).issue();

    assertThat(
        new DefaultUsersOfIssue(session, issue.refresh())
            .assignee().get()
            .name(),
        is(config.youtrackUser())
    );
  }

  private final Issue issue(String name) throws Exception {
    return new DefaultYouTrack(session)
        .projects()
        .stream()
        .findFirst()
        .get()
        .issues()
        .create(
            new IssueSpec(
                name, 
                "integration tests"
            )
        );
  }
}