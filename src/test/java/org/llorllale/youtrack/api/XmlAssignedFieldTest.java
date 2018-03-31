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

// @checkstyle AvoidStaticImport (4 lines)
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.llorllale.youtrack.api.mock.MockAssignedField;
import org.llorllale.youtrack.api.mock.MockField;
import org.llorllale.youtrack.api.mock.MockFieldValue;
import org.llorllale.youtrack.api.mock.MockIssue;
import org.llorllale.youtrack.api.mock.MockProject;

/**
 * Unit tests for {@link XmlAssignedField}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 * @checkstyle MultipleStringLiterals (500 lines)
 * @checkstyle MethodName (500 lines)
 */
public final class XmlAssignedFieldTest {
  /**
   * {@link XmlAssignedField#issue()} must return the same issue.
   * @since 1.0.0
   */
  @Test
  public void issue() {
    final Issue issue = new MockIssue(new MockProject());
    assertThat(
        new XmlAssignedField(null, issue, null).issue(),
        is(issue)
    );
  }

  /**
   * {@link XmlAssignedField#value()} must return a {@link FieldValue} with a value equal 
   * to the "value" element in the XML.
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void value() throws Exception {
    final String xml =
      // @checkstyle LineLength (1 line)
      "    <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CustomFieldValue\" name=\"Priority\">\n"
      + "      <value>Normal</value>\n"
      + "      <valueId>Normal</valueId>\n"
      + "      <color>\n"
      + "        <bg>#e6f6cf</bg>\n"
      + "        <fg>#4da400</fg>\n"
      + "      </color>\n"
      + "    </field>";
    final Field field = new MockField("Priority", new MockProject());
    assertThat(
      new XmlAssignedField(
        field, 
        new MockIssue(field.project()), 
        new XmlOf(new StringAsDocument(xml))
      ).value(),
      is(new MockFieldValue(field, "Normal"))
    );
  }

  /**
   * {@link XmlAssignedField#project()} must return the same project.
   * @since 1.0.0
   */
  @Test
  public void project() {
    final Project project = new MockProject();
    assertThat(
      new XmlAssignedField(
        new MockField("field", project), 
        null, 
        null
      ).project(),
      is(project)
    );
  }

  /**
   * {@link XmlAssignedField#name()} must return the enclosed field's name.
   * @since 1.0.0
   */
  @Test
  public void name() {
    //the name of interest
    final String name = "abc123";
    assertThat(
      new XmlAssignedField(
        new MockField(name, new MockProject()),
        null, 
        null
      ).name(),
      is(name)
    );
  }

  /**
   * {@link XmlAssignedField#hashCode()} must be equal to its name's hashcode.
   * @since 1.0.0
   */
  @Test
  public void testHashCode() {
    final String name = "abc123";
    assertThat(
      new XmlAssignedField(new MockField(name, new MockProject()), null, null).hashCode(),
      is(name.hashCode())
    );
  }

  /**
   * An {@link XmlAssignedField} must be equal to another {@link AssignedField} with the same name
   * and value. The {@link XmlAssignedField}'s name and value are set in its enclosed XML.
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void equals() throws Exception {
    final String xml =
      // @checkstyle LineLength (1 line)
      "<field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CustomFieldValue\" name=\"Priority\">\n"
      + "  <value>Normal</value>\n"
      + "  <valueId>Normal</valueId>\n"
      + "  <color>\n"
      + "    <bg>#e6f6cf</bg>\n"
      + "    <fg>#4da400</fg>\n"
      + "  </color>\n"
      + "</field>";
    final Field field = new MockField("Normal", new MockProject());
    final Issue issue = new MockIssue(field.project());
    assertTrue(
      new XmlAssignedField(
        field, 
        issue,
        new XmlOf(new StringAsDocument(xml))
      ).equals(
        new MockAssignedField(field.name(), issue, "Normal")
      )
    );
  }

  /**
   * An {@link XmlAssignedField} cannot be equal to another {@link AssignedField} with with a 
   * different name.
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void notEqualsWithFieldOfDifferentName() throws Exception {
    final String xml =
      // @checkstyle LineLength (1 line)
      "<field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CustomFieldValue\" name=\"Priority\">\n"
      + "  <value>Normal</value>\n"
      + "  <valueId>Normal</valueId>\n"
      + "  <color>\n"
      + "    <bg>#e6f6cf</bg>\n"
      + "    <fg>#4da400</fg>\n"
      + "  </color>\n"
      + "</field>";
    final Field field = new MockField("Normal", new MockProject());
    final Issue issue = new MockIssue(field.project());
    assertFalse(
      new XmlAssignedField(
        field, 
        issue,
        new XmlOf(new StringAsDocument(xml))
      ).equals(
        new MockAssignedField("Some Other Name", issue, "Normal")
      )
    );
  }

  /**
   * An {@link XmlAssignedField} cannot be equal to another {@link AssignedField} that, 
   * although belonging to the same {@link Field}, has a different {@link FieldValue value}.
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void notEqualsWithFieldOfDifferentValue() throws Exception {
    final String xml =
      // @checkstyle LineLength (1 line)
      "<field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CustomFieldValue\" name=\"Priority\">\n"
      + "  <value>Normal</value>\n"
      + "  <valueId>Normal</valueId>\n"
      + "  <color>\n"
      + "    <bg>#e6f6cf</bg>\n"
      + "    <fg>#4da400</fg>\n"
      + "  </color>\n"
      + "</field>";
    final Field field = new MockField("Normal", new MockProject());
    final Issue issue = new MockIssue(field.project());
    assertFalse(
      new XmlAssignedField(
        field, 
        issue,
        new XmlOf(new StringAsDocument(xml))
      ).equals(
        new MockAssignedField(field.name(), issue, "Some Other Value")
      )
    );
  }

  /**
   * An {@link XmlAssignedField} cannot be equal to a field associated with a different project,
   * even if both share the same name and value.
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void notEqualsWithFieldOfDifferentProject() throws Exception {
    final String xml =
      // @checkstyle LineLength (1 line)
      "<field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CustomFieldValue\" name=\"Priority\">\n"
      + "  <value>Normal</value>\n"
      + "  <valueId>Normal</valueId>\n"
      + "  <color>\n"
      + "    <bg>#e6f6cf</bg>\n"
      + "    <fg>#4da400</fg>\n"
      + "  </color>\n"
      + "</field>";
    final Project firstProject = new MockProject("PR-1", "Project Name 1", "desc1");
    final Project secondProject = new MockProject("PR-2", "Project Name 2", "desc2");
    assertFalse(
      new XmlAssignedField(
        new MockField("Normal", firstProject),
        new MockIssue(firstProject),
        new XmlOf(new StringAsDocument(xml))
      ).equals(
        new MockAssignedField(
          "Normal", 
          new MockIssue(secondProject), 
          "Normal"
        )
      )
    );
  }

  /**
   * An {@link XmlAssignedField} cannot be equal to {@code null}.
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void notEqualsWithNull() throws Exception {
    final String xml =
      // @checkstyle LineLength (1 line)
      "<field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CustomFieldValue\" name=\"Priority\">\n"
      + "  <value>Normal</value>\n"
      + "  <valueId>Normal</valueId>\n"
      + "  <color>\n"
      + "    <bg>#e6f6cf</bg>\n"
      + "    <fg>#4da400</fg>\n"
      + "  </color>\n"
      + "</field>";
    assertFalse(
      new XmlAssignedField(
        new MockField("field", new MockProject()), 
        new MockIssue(new MockProject()),
        new XmlOf(new StringAsDocument(xml))
      ).equals(null)
    );
  }

  /**
   * An {@link XmlAssignedField} cannot be equal to an object that is not an {@link AssignedField}.
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void notEqualsWithOtherType() throws Exception {
    final String xml =
      // @checkstyle LineLength (1 line)
      "<field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CustomFieldValue\" name=\"Priority\">\n"
      + "  <value>Normal</value>\n"
      + "  <valueId>Normal</valueId>\n"
      + "  <color>\n"
      + "    <bg>#e6f6cf</bg>\n"
      + "    <fg>#4da400</fg>\n"
      + "  </color>\n"
      + "</field>";
    assertFalse(
      new XmlAssignedField(
        new MockField("field", new MockProject()), 
        new MockIssue(new MockProject()),
        new XmlOf(new StringAsDocument(xml))
      ).equals(new Object())
    );
  }
}
