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
import java.time.Instant;
import java.time.ZoneId;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.llorllale.youtrack.api.mock.MockIssue;
import org.llorllale.youtrack.api.mock.MockProject;

/**
 * Unit tests for {@link XmlTimeTrackEntry}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public class XmlTimeTrackEntryTest {
  private static Xml xml;

  @BeforeClass
  public static void setup() throws Exception {
    xml = new XmlOf(new StringAsDocument(XML));
  }

  @Test
  public void testIssue() {
    final Issue issue = issue();

    assertThat(
        new XmlTimeTrackEntry(issue, xml).issue(),
        is(issue)
    );
  }

  /**
   * @see <a href="https://github.com/llorllale/youtrack-api/issues/133">#133</a>
   */
  @Test
  public void testDate() {
    assertThat(
        new XmlTimeTrackEntry(issue(), xml).date(),
        is(
            Instant.ofEpochMilli(1480204800000L)
                .atZone(YouTrack.ZONE_ID)
                .toLocalDate()
        )
    );
  }

  @Test
  public void testDuration() {
    assertThat(
        new XmlTimeTrackEntry(issue(), xml).duration(),
        is(Duration.ofMinutes(240))
    );
  }

  @Test
  public void testDescription() {
    assertThat(
        new XmlTimeTrackEntry(issue(), xml).description().get(),
        is("first work item")
);
  }

  private Issue issue() {
    return new MockIssue(new MockProject("PR-X", "", "")).withId("ISSUE-1");
  }

  private static final String XML =
"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
"<workItem url=\"http://unit-258.labs.intellij.net:8080/charisma/rest/issue/HBR-63/timetracking/workitem/101-1\">\n" +
"  <id>101-1</id>\n" +
"  <date>1480204800000</date>\n" +
"  <duration>240</duration>\n" +
"  <description>first work item</description>\n" +
"  <author login=\"root\" url=\"http://unit-258.labs.intellij.net:8080/charisma/rest/admin/user/root\"/>\n" +
"</workItem>";
}
