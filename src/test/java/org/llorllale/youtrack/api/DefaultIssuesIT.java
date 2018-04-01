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

// @checkstyle AvoidStaticImport (1 line)
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.session.PermanentToken;
import org.llorllale.youtrack.api.session.Session;

/**
 * Integration tests for {@link DefaultIssues}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 * @checkstyle MultipleStringLiterals (500 lines)
 * @checkstyle MethodName (500 lines)
 * @checkstyle AbbreviationAsWordInName (2 lines)
 */
public final class DefaultIssuesIT {
  private static IntegrationTestsConfig config;
  private static Session session;
  private static Project project;

  /**
   * Setup.
   * @throws Exception unexpected
   */
  @BeforeClass
  public static void setup() throws Exception {
    config = new IntegrationTestsConfig();
    session = new PermanentToken(
      config.youtrackUrl(), 
      config.youtrackUserToken()
    ).login();
    project = new DefaultYouTrack(session).projects().stream().findAny().get();
  }

  /**
   * Returns newly created issue in stream.
   * @throws Exception unexpected
   */
  @Test
  public void testStream() throws Exception {
    final Issue issue = new DefaultIssues(project, session)
      .create(DefaultIssuesIT.class.getSimpleName().concat(".testStream"), "description");
    assertTrue(
      new DefaultIssues(project, session)
        .stream()
        .anyMatch(i -> i.id().equals(issue.id()))
    );
  }

  /**
   * Returns newly created issue.
   * @throws Exception unexpected
   */
  @Test
  public void createAndGetIssue() throws Exception {
    final Issue issue = new DefaultIssues(project, session)
      .create(DefaultIssuesIT.class.getSimpleName().concat(".testGet"), "description");
    assertTrue(
      new DefaultIssues(
        project,
        session
      ).get(issue.id()).isPresent()
    );
  }
}
