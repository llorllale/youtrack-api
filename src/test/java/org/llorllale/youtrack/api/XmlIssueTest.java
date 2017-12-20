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

import java.time.Instant;
import java.util.stream.Collectors;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.Issues.IssueSpec;
import org.llorllale.youtrack.api.mock.MockAssignedField;
import org.llorllale.youtrack.api.mock.MockIssue;
import org.llorllale.youtrack.api.mock.MockProject;
import org.llorllale.youtrack.api.mock.http.MockSession;

/**
 * Unit tests for {@link XmlIssue}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public class XmlIssueTest {
  private static Xml xml;

  @BeforeClass
  public static void setup() throws Exception {
    xml = new XmlOf(new StringAsDocument(XML_ISSUE));
  }

  @Test
  public void testId() {
    assertThat(
        new XmlIssue(
            new MockProject(),
            new MockSession(),
            xml
        ).id(),
        is("HBR-63")
    );
  }

  @Test
  public void testCreationDate() {
    assertThat(
        new XmlIssue(
            new MockProject(),
            new MockSession(),
            xml
        ).creationDate(),
        is(Instant.ofEpochMilli(1262171005630L))
    );
  }

  @Test
  public void testSummary() {
    assertThat(
        new XmlIssue(
            new MockProject(),
            new MockSession(),
            xml
        ).summary(),
        is("summary")
    );
  }

  @Test
  public void testDescription() {
    assertThat(
        new XmlIssue(
            new MockProject(),
            new MockSession(),
            xml
        ).description().get(),
        is("description")
    );
  }

  @Test
  public void testFields() {
    assertThat(
        new XmlIssue(new MockProject(), new MockSession(), xml).fields().size(),
        is(3)
    );
  }

  /**
   * {@link XmlIssue#spec()} should describe issue accurately.
   * 
   * @since 1.0.0
   */
  @Test
  public void spec() {
    assertThat(
        new XmlIssue(
            new MockProject(),
            new MockSession(),
            xml
        ).spec(),
        is(new IssueSpec(
            "summary", 
            "description",
            xml.children("//field[count(valueId) > 0]")
                .stream()
                .map(x -> 
                    new MockAssignedField(
                        x.textOf("@name").get(), 
                        new MockIssue(new MockProject(), "HBR-63", null, null, null), 
                        x.textOf("value").get()
                    )
                ).collect(Collectors.toMap(
                    f -> f,
                    f -> f.value()
            ))
        ))
    );
  }

  /**
   * {@link XmlIssue#hashCode()} must be equal to it's ID's hashCode.
   * 
   * @since 1.0.0
   */
  @Test
  public void testHashCode() {
    assertThat(
        new XmlIssue(new MockProject(), new MockSession(), xml).id().hashCode(),
        is("HBR-63".hashCode())
    );
  }

  /**
   * Two issues are equal if both their IDs and projects are equal.
   * 
   * @since 1.0.0
   */
  @Test
  public void equals() {
    final Project project = new MockProject("PR-1", "name", "description");
    assertEquals(
        new XmlIssue(project, new MockSession(), xml),
        new MockIssue(project, "HBR-63", null, null, null)
    );
  }

  /**
   * An {@link XmlIssue} cannot be equal to {@code null}.
   * 
   * @since 1.0.0
   */
  @Test
  public void equalsNullIsFalse() {
    assertFalse(
        new XmlIssue(new MockProject(), new MockSession(), xml).equals(null)
    );
  }

  /**
   * An {@link XmlIssue} cannot be equal to a type other than another {@link Issue}.
   * 
   * @since 1.0.0
   */
  @Test
  public void equalsObjectIsFalse() {
    assertFalse(
        new XmlIssue(new MockProject(), new MockSession(), xml).equals(new Object())
    );
  }

  /**
   * An issue cannot be equal to an issue that belongs to another project, even if both their 
   * ids are the same.
   * 
   * @since 1.0.0
   */
  @Test 
  public void equalsWithDifferentProjectIsFalse() {
    assertNotEquals( 
        new XmlIssue(
            new MockProject("PR-1", "name", "description"), 
            new MockSession(), 
            xml
        ),
        new MockIssue(
            new MockProject("PR-2", "name", "description"), 
            "HBR-63", 
            null, 
            null, 
            null
        )
    );
  }

  /**
   * An issue cannot be equal to another issue with a different ID, even if both belong to the 
   * same project.
   * 
   * @since 1.0.0
   */
  @Test
  public void equalsWithDifferentIssueIdIsFalse() {
    assertNotEquals( 
        new XmlIssue(
            new MockProject("PR-1", "name", "description"), 
            new MockSession(), 
            xml
        ),
        new MockIssue(
            new MockProject("PR-1", "name", "description"), 
            "HBR-64", 
            null, 
            null, 
            null
        )
    );
  }

  private static final String XML_ISSUE =
"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
"<issue id=\"HBR-63\">\n" +
"    <field name=\"attachments\">\n" +
"        <value url=\"/_persistent/uploadFile.html?file=45-46&amp;v=0&amp;c=true\">uploadFile.html</value>\n" +
"    </field>\n" +
"    <comment id=\"42-306\" author=\"root\" issueId=\"HBR-63\" deleted=\"false\" text=\"comment 1!\" shownForIssueAuthor=\"false\"\n" +
"             created=\"1267030230127\">\n" +
"        <replies/>\n" +
"    </comment>\n" +
"    <comment id=\"42-307\" author=\"root\" issueId=\"HBR-63\" deleted=\"false\" text=\"comment 2?\" shownForIssueAuthor=\"false\"\n" +
"             created=\"1267030238721\" updated=\"1267030230127\">\n" +
"        <replies/>\n" +
"    </comment>\n" +
"    <field name=\"Priority\">\n" +
"        <value>Normal</value>\n" +
"    </field>\n" +
"    <field name=\"Type\">\n" +
"        <value>Bug</value>\n" +
"    </field>\n" +
"    <field name=\"State\">\n" +
"        <value>Won't fix</value>\n" +
"    </field>\n" +
"    <field name=\"Assignee\">\n" +
"        <value>beto</value>\n" +
"    </field>\n" +
"    <field name=\"Subsystem\">\n" +
"        <value>Configuration</value>\n" +
"    </field>\n" +
"    <field name=\"Fix versions\">\n" +
"        <value>2.0</value>\n" +
"        <value>2.0.5</value>\n" +
"        <value>2.0.7</value>\n" +
"    </field>\n" +
"    <field name=\"cf\">\n" +
"        <value>0</value>\n" +
"        <value>!</value>\n" +
"    </field>\n" +
"    <field name=\"scf\">\n" +
"        <value>1265835603000</value>\n" +
"    </field>\n" +
"    <field name=\"links\">\n" +
"        <value type=\"Depend\" role=\"depends on\">HBR-62</value>\n" +
"        <value type=\"Duplicate\" role=\"duplicates\">HBR-57</value>\n" +
"        <value type=\"Duplicate\" role=\"is duplicated by\">HBR-54</value>\n" +
"        <value type=\"Relates\" role=\"relates to\">HBR-49</value>\n" +
"        <value type=\"Relates\" role=\"is related to\">HBR-51</value>\n" +
"        <value type=\"Depend\" role=\"is required for\">HBR-49</value>\n" +
"    </field>\n" +
"    <field name=\"projectShortName\">\n" +
"        <value>HBR</value>\n" +
"    </field>\n" +
"    <field name=\"numberInProject\">\n" +
"        <value>63</value>\n" +
"    </field>\n" +
"    <field name=\"summary\">\n" +
"        <value>summary</value>\n" +
"    </field>\n" +
"    <field name=\"description\">\n" +
"        <value>description</value>\n" +
"    </field>\n" +
"    <field name=\"created\">\n" +
"        <value>1262171005630</value>\n" +
"    </field>\n" +
"    <field name=\"updated\">\n" +
"        <value>1267630833573</value>\n" +
"    </field>\n" +
"    <field name=\"updaterName\">\n" +
"        <value>root</value>\n" +
"    </field>\n" +
"    <field name=\"resolved\">\n" +
"        <value>1267030108251</value>\n" +
"    </field>\n" +
"    <field name=\"reporterName\">\n" +
"        <value>root</value>\n" +
"    </field>\n" +
"    <field name=\"commentsCount\">\n" +
"        <value>2</value>\n" +
"    </field>\n" +
"    <field name=\"votes\">\n" +
"        <value>0</value>\n" +
"    </field>\n" +
"    <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CustomFieldValue\" name=\"Priority\">\n" +
"      <value>Normal</value>\n" +
"      <valueId>Normal</valueId>\n" +
"      <color>\n" +
"        <bg>#e6f6cf</bg>\n" +
"        <fg>#4da400</fg>\n" +
"      </color>\n" +
"    </field>\n" +
"    <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CustomFieldValue\" name=\"Type\">\n" +
"      <value>Task</value>\n" +
"      <valueId>Task</valueId>\n" +
"    </field>\n" +
"    <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CustomFieldValue\" name=\"State\">\n" +
"      <value>Open</value>\n" +
"      <valueId>Open</valueId>\n" +
"    </field>" +
"</issue>";
}