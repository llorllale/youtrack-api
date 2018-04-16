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

import java.time.Duration;
import java.time.Instant;
import org.junit.Test;
import org.llorllale.youtrack.api.mock.MockIssue;
import org.llorllale.youtrack.api.mock.MockProject;

/**
 * Unit tests for {@link XmlTimeTrackEntry}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 * @checkstyle MultipleStringLiterals (500 lines)
 * @checkstyle MethodName (500 lines)
 */
public final class XmlTimeTrackEntryTest {
  /**
   * Returns the parent issue.
   */
  @Test
  public void testIssue() {
    final Issue issue = this.issue();
    assertThat(
      new XmlTimeTrackEntry(
        issue,
        new XmlOf(new StringAsDocument(
          "<workItem>\n"
            + "  <id>101-1</id>\n"
            + "  <date>1480204800000</date>\n"
            + "  <duration>240</duration>\n"
            + "  <description>first work item</description>\n"
            + "  <author login=\"root\"/>\n"
            + "</workItem>"
        ))
      ).issue(),
      is(issue)
    );
  }

  /**
   * Must localize the date correctly.
   * @see <a href="https://github.com/llorllale/youtrack-api/issues/133">#133</a>
   */
  @Test
  public void testDate() {
    assertThat(
      new XmlTimeTrackEntry(
        this.issue(),
        new XmlOf(new StringAsDocument(
          "<workItem>\n"
            + "  <id>101-1</id>\n"
            + "  <date>1480204800000</date>\n"
            + "  <duration>240</duration>\n"
            + "  <description>first work item</description>\n"
            + "  <author login=\"root\"/>\n"
            + "</workItem>"
        ))
      ).date(),
      is(
        // @checkstyle MagicNumber (1 line)
        Instant.ofEpochMilli(1480204800000L)
          .atZone(new YouTrackZoneId().toZoneId())
          .toLocalDate()
      )
    );
  }

  /**
   * Returns the duration.
   */
  @Test
  public void testDuration() {
    assertThat(
      new XmlTimeTrackEntry(
        this.issue(),
        new XmlOf(new StringAsDocument(
          "<workItem>\n"
            + "  <id>101-1</id>\n"
            + "  <date>1480204800000</date>\n"
            + "  <duration>240</duration>\n"
            + "  <description>first work item</description>\n"
            + "  <author login=\"root\"/>\n"
            + "</workItem>"
        ))
      ).duration(),
      // @checkstyle MagicNumber (1 line)
      is(Duration.ofMinutes(240))
    );
  }

  /**
   * Returns the description.
   */
  @Test
  public void testDescription() {
    assertThat(
      new XmlTimeTrackEntry(
        this.issue(),
        new XmlOf(new StringAsDocument(
          "<workItem>\n"
            + "  <id>101-1</id>\n"
            + "  <date>1480204800000</date>\n"
            + "  <duration>240</duration>\n"
            + "  <description>first work item</description>\n"
            + "  <author login=\"root\"/>\n"
            + "</workItem>"
        ))
      ).description(),
      is("first work item")
    );
  }

  /**
   * Mock issue.
   * @return a mock issue for use in these tests
   */
  private Issue issue() {
    return new MockIssue(new MockProject("PR-X", "", "")).withId("ISSUE-1");
  }
}
