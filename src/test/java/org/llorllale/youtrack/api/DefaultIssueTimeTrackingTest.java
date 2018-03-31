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
 * Unit tests for {@link DefaultIssueTimeTracking}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 * @checkstyle MultipleStringLiterals (500 lines)
 */
public final class DefaultIssueTimeTrackingTest {
  private static final String WORKITEMS =
    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
    + "<workItems>\n"
    // @checkstyle LineLength (1 line)
    + "  <workItem url=\"http://unit-258.labs.intellij.net:8080/charisma/rest/issue/HBR-63/timetracking/workitem/101-1\">\n"
    + "    <id>101-1</id>\n"
    + "    <date>1480204800000</date>\n"
    + "    <duration>240</duration>\n"
    + "    <description>first work item</description>\n"
    // @checkstyle LineLength (1 line)
    + "    <author login=\"root\" url=\"http://unit-258.labs.intellij.net:8080/charisma/rest/admin/user/root\"/>\n"
    + "  </workItem>\n"
    // @checkstyle LineLength (1 line)
    + "  <workItem url=\"http://unit-258.labs.intellij.net:8080/charisma/rest/issue/HBR-63/timetracking/workitem/101-2\">\n"
    + "    <id>101-2</id>\n"
    + "    <date>1467936000000</date>\n"
    + "    <duration>480</duration>\n"
    + "    <description>second work item</description>\n"
    // @checkstyle LineLength (1 line)
    + "    <author login=\"root\" url=\"http://unit-258.labs.intellij.net:8080/charisma/rest/admin/user/root\"/>\n"
    + "  </workItem>\n"
    + "</workItems>";

  /**
   * Correctly counts the number of workItems returned by the YouTrack API.
   * 
   * @throws Exception unexpected
   */
  @Test
  public void countAll() throws Exception {
    assertThat(
      new DefaultIssueTimeTracking(
        new MockSession(), 
        new MockIssue(new MockProject()),
        new MockHttpClient(new MockOkResponse(WORKITEMS))
      ).stream().count(),
      is(2L)
    );
  }
}
