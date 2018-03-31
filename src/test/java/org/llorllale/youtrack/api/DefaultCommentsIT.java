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

import java.util.Random;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.session.PermanentToken;
import org.llorllale.youtrack.api.session.Session;

/**
 * Integration tests for {@link DefaultComments}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 * @checkstyle MethodName (500 lines)
 * @checkstyle AbbreviationAsWordInName (2 lines)
 */
public final class DefaultCommentsIT {
  private static IntegrationTestsConfig config;
  private static Session session;
  private static Issue issue;

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

    issue = new DefaultYouTrack(session)
        .projects()
        .stream()
        .findFirst()
        .get()
        .issues()
        .create(DefaultCommentsIT.class.getSimpleName(), "Description");
  }

  /**
   * Returns comments posted to issue.
   * @throws Exception unexpected
   */
  @Test
  public void postAndGetAll() throws Exception {
    final String first = "First comment " + new Random(System.currentTimeMillis()).nextInt();
    final String second = "Second comment " + new Random(System.currentTimeMillis()).nextInt();
    assertThat(
      new DefaultComments(session, issue)
        .post(first)
        .post(second)
        .stream()
        .filter(c -> first.equals(c.text()) || second.equals(c.text()))
        .count(),
      is(2L)
    );
  }
}
