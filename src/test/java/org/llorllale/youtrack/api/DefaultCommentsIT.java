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

import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.Issues.IssueSpec;
import org.llorllale.youtrack.api.session.PermanentTokenLogin;
import org.llorllale.youtrack.api.session.Session;

/**
 * Integration tests for {@link DefaultComments}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public class DefaultCommentsIT {
  private static IntegrationTestsConfig config;
  private static Session session;
  private static Issue issue;
  private static final String comment1 = "First comment";
  private static final String comment2 = "Second comment";

  @BeforeClass
  public static void setup() throws Exception {
    config = new IntegrationTestsConfig();

    session = new PermanentTokenLogin(
        config.youtrackUrl(), 
        config.youtrackUserToken()
    ).login();

    issue = new DefaultYouTrack(session)
        .projects()
        .all()
        .stream()
        .findFirst()
        .get()
        .issues()
        .create(new IssueSpec("Summary", "Description"));
  }

  @Test
  public void postAndGetAll() throws Exception {
    issue.comments()
        .post(comment1)
        .post(comment2);

    assertTrue(
        new DefaultComments(session, issue)
            .all()
            .stream()
            .allMatch(c -> c.text().equals(comment1) || c.text().equals(comment2))
    );
  }
}