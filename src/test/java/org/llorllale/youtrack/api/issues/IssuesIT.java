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
package org.llorllale.youtrack.api.issues;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.IntegrationTestsConfig;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UsernamePasswordLogin;

/**
 * Integration tests for different kinds of workflows with {@link Issue issues}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
public class IssuesIT {
  private static Session session;
  
  @BeforeClass
  public static void setUpClass() throws Exception {
    final IntegrationTestsConfig config = new IntegrationTestsConfig();
    session = new UsernamePasswordLogin(
        config.youtrackUrl(), 
        config.youtrackUser(), 
        config.youtrackPwd()
    ).login();
  } 

  /**
   * Creates some issues for existing project with ID "TP" and then queries YouTrack for the same 
   * issues.
   * @throws Exception 
   * @since 0.1.0
   */
  @Test
  public void createIssuesAndRetrieveThem() throws Exception {
    final String issueId1 = new CreateIssue(session)
        .forProjectId("TP")
        .withSummary("Some Test Issue")
        .withDescription("Test description")
        .create();

    final Issue issue = new IssueWithId(issueId1, session).query().get();

    assertThat(issue.id(), is(issueId1));
    assertThat(issue.projectId(), is("TP"));
    assertThat(issue.summary(), is("Some Test Issue"));
    assertThat(issue.description(), is("Test description"));

    final String issueId2 = new CreateIssue(session)
        .forProjectId("TP")
        .withSummary("Test Issue 2")
        .withDescription("Test description 2")
        .create();

    //TODO: currently, the test YouTrack image is being shipped with an issue created under project
    //"TP" - this should be changed so that there are no issues for this project
    assertThat(
        new IssuesForProject("TP", session).query()
            .stream()
            .map(Issue::id)
            .collect(toList()),
        hasItems(issueId1, issueId2)
    );
  }

  /**
   * Creates an Issue and adds some comments on it.
   * @throws Exception 
   * @since 0.2.0
   */
  @Test
  public void createIssueAndComment() throws Exception {
    final String issueId = new CreateIssue(session).forProjectId("TP")
        .withSummary("A test issue summary")
        .withDescription("Some test description")
        .create();

    new CreateComment(session).forIssueId(issueId)
        .withText("A test comment!!!")
        .create();

    new CreateComment(session).forIssueId(issueId)
        .withText("Another test comment!!!")
        .create();

    assertThat(
        new CommentsForIssue(issueId, session).query()
            .stream()
            .map(c -> c.text())
            .collect(toList()),
        hasItems("A test comment!!!", "Another test comment!!!")
    );
  }

  /**
   * 
   * @throws Exception 
   * @since 0.2.0
   */
  @Test
  public void createsIssueAndCommentThenUpdatesComment() throws Exception {
    final String issueId = new CreateIssue(session).forProjectId("TP")
        .withSummary("Testing updating comment text")
        .withDescription("Some test description")
        .create();

    new CreateComment(session).forIssueId(issueId)
        .withText("A comment to test updating comments!!!")
        .create();

    final Comment initial = new CommentsForIssue(issueId, session).query().get(0);

    new UpdateComment(initial, "Modified comment to test updating comments!!!", session).update();

    assertThat(
        new CommentsForIssue(issueId, session).query()
            .stream()
            .map(c -> c.text())
            .collect(toList()),
        hasItem("Modified comment to test updating coments!!!")
    );
  }
}
