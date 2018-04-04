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

import org.junit.Test;
import org.llorllale.youtrack.api.mock.MockIssue;
import org.llorllale.youtrack.api.mock.MockProject;
import org.llorllale.youtrack.api.mock.http.MockHttpClient;
import org.llorllale.youtrack.api.mock.http.MockSession;
import org.llorllale.youtrack.api.mock.http.response.MockOkResponse;

/**
 * Unit tests for {@link DefaultComments}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 * @checkstyle MultipleStringLiterals (500 lines)
 */
public final class DefaultCommentsTest {
  private static final String COMMENTS
    = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
    + "<comments>\n"
    // @checkstyle LineLength (1 line)
    + "  <comment id=\"42-306\" author=\"root\" issueId=\"HBR-63\" deleted=\"false\" text=\"comment 1!\" shownForIssueAuthor=\"false\"\n"
    + "           created=\"1267030230127\">\n"
    + "      <replies/>\n"
    + "  </comment>\n"
    // @checkstyle LineLength (1 line)
    + "  <comment id=\"42-307\" author=\"root\" issueId=\"HBR-63\" deleted=\"false\" text=\"comment 2?\" shownForIssueAuthor=\"false\"\n"
    + "           created=\"1267030238721\" updated=\"1267030230127\">\n"
    + "      <replies/>\n"
    + "  </comment>\n"
    + "</comments>";

  /**
   * Stream returns all comments.
   * @throws Exception unexpected
   */
  @Test
  public void testStream() throws Exception {
    assertThat(
      new DefaultComments(
        new MockSession(),
        new MockIssue(
          new MockProject("", "", "")
        ),
        new MockHttpClient(
          new MockOkResponse(COMMENTS)
        )
      ).stream().count(),
      is(2L)
    );
  }
}
