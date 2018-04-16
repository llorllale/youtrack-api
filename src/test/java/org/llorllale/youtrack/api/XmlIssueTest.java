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

// @checkstyle AvoidStaticImport (5 lines)
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.time.Instant;
import org.junit.Test;
import org.llorllale.youtrack.api.mock.MockIssue;
import org.llorllale.youtrack.api.mock.MockProject;
import org.llorllale.youtrack.api.mock.http.MockSession;

/**
 * Unit tests for {@link XmlIssue}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 * @checkstyle MultipleStringLiterals (500 lines)
 * @checkstyle MethodName (500 lines)
 */
public final class XmlIssueTest {
  /**
   * Returns the issue's id.
   */
  @Test
  public void testId() {
    assertThat(
      new XmlIssue(
        new MockProject(),
        new MockSession(),
        new XmlOf(new StringAsDocument("<issue id=\"HBR-63\"/>"))
      ).id(),
      is("HBR-63")
    );
  }

  /**
   * Returns the issue's creation date.
   */
  @Test
  public void testCreationDate() {
    assertThat(
      new XmlIssue(
        new MockProject(),
        new MockSession(),
        new XmlOf(new StringAsDocument(
            "<issue id=\"HBR-63\">\n"
            + "    <field name=\"created\">\n"
            + "        <value>1262171005630</value>\n"
            + "    </field>\n"
            + "</issue>"
        ))
      ).creationDate(),
      // @checkstyle MagicNumber (1 line)
      is(Instant.ofEpochMilli(1262171005630L))
    );
  }

  /**
   * Returns the issue's summary text.
   */
  @Test
  public void testSummary() {
    assertThat(
      new XmlIssue(
        new MockProject(),
        new MockSession(),
        new XmlOf(new StringAsDocument(
          "<issue id=\"HBR-63\">\n"
          + "    <field name=\"summary\">\n"
          + "        <value>summary text</value>\n"
          + "    </field>\n"
          + "</issue>"
        ))
      ).summary(),
      is("summary text")
    );
  }

  /**
   * Returns the issue's description.
   */
  @Test
  public void testDescription() {
    assertThat(
      new XmlIssue(
        new MockProject(),
        new MockSession(),
        new XmlOf(new StringAsDocument(
          "<issue id=\"HBR-63\">\n"
          + "    <field name=\"description\">\n"
          + "        <value>descriptive text</value>\n"
          + "    </field>\n"
          + "</issue>"
        ))
      ).description().get(),
      is("descriptive text")
    );
  }

  /**
   * Correctly counts the issue's fields. The fields to be included are the ones with the
   * 'valueId' child element.
   */
  @Test
  public void testFields() {
    assertThat(
      new XmlIssue(
        new MockProject(),
        new MockSession(),
        new XmlOf(new StringAsDocument(
          "<issue id=\"HBR-63\">\n"
            // @checkstyle LineLength (1 line)
            + "    <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CustomFieldValue\" name=\"Priority\">\n"
            + "      <value>Normal</value>\n"
            + "      <valueId>Normal</valueId>\n"
            + "      <color>\n"
            + "        <bg>#e6f6cf</bg>\n"
            + "        <fg>#4da400</fg>\n"
            + "      </color>\n"
            + "    </field>\n"
            // @checkstyle LineLength (1 line)
            + "    <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CustomFieldValue\" name=\"Type\">\n"
            + "      <value>Task</value>\n"
            + "      <valueId>Task</valueId>\n"
            + "    </field>\n"
            // @checkstyle LineLength (1 line)
            + "    <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CustomFieldValue\" name=\"State\">\n"
            + "      <value>Open</value>\n"
            + "      <valueId>Open</valueId>\n"
            + "    </field>"
            + "</issue>"
        ))
      ).fields().size(),
      // @checkstyle MagicNumber (1 line)
      is(3)
    );
  }

  /**
   * {@link XmlIssue#hashCode()} must be equal to it's ID's hashCode.
   * @since 1.0.0
   */
  @Test
  public void testHashCode() {
    assertThat(
      new XmlIssue(
        new MockProject(),
        new MockSession(),
        new XmlOf(new StringAsDocument("<issue id=\"HBR-63\"/>"))
      ).id().hashCode(),
      is("HBR-63".hashCode())
    );
  }

  /**
   * Two issues are equal if both their IDs and projects are equal.
   * @since 1.0.0
   */
  @Test
  public void equals() {
    final Project project = new MockProject("PR-1", "name", "description");
    assertEquals(
      new XmlIssue(
        project,
        new MockSession(),
        new XmlOf(new StringAsDocument("<issue id=\"HBR-63\"/>"))
      ),
      new MockIssue(project, "HBR-63")
    );
  }

  /**
   * An {@link XmlIssue} cannot be equal to {@code null}.
   * @since 1.0.0
   */
  @Test
  public void equalsNullIsFalse() {
    assertFalse(
      new XmlIssue(
        new MockProject(),
        new MockSession(),
        new XmlOf(new StringAsDocument("<issue id=\"HBR-63\"/>"))
      ).equals(null)
    );
  }

  /**
   * An {@link XmlIssue} cannot be equal to a type other than another {@link Issue}.
   * @since 1.0.0
   */
  @Test
  public void equalsObjectIsFalse() {
    assertFalse(
      new XmlIssue(
        new MockProject(),
        new MockSession(),
        new XmlOf(new StringAsDocument("<issue id=\"HBR-63\"/>"))
      ).equals(new Object())
    );
  }

  /**
   * An issue cannot be equal to an issue that belongs to another project, even if both their ids
   * are the same.
   * @since 1.0.0
   */
  @Test
  public void equalsWithDifferentProjectIsFalse() {
    assertNotEquals(
      new XmlIssue(
        new MockProject("PR-1", "name", "description"),
        new MockSession(),
        new XmlOf(new StringAsDocument("<issue id=\"HBR-63\"/>"))
      ),
      new MockIssue(
        new MockProject("PR-2", "name", "description"),
        "HBR-63"
      )
    );
  }

  /**
   * An issue cannot be equal to another issue with a different ID, even if both belong to the same
   * project.
   * @since 1.0.0
   */
  @Test
  public void equalsWithDifferentIssueIdIsFalse() {
    assertNotEquals(
      new XmlIssue(
        new MockProject("PR-1", "name", "description"),
        new MockSession(),
        new XmlOf(new StringAsDocument("<issue id=\"HBR-63\"/>"))
      ),
      new MockIssue(
        new MockProject("PR-1", "name", "description"),
        "HBR-64"
      )
    );
  }
}
