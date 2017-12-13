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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.llorllale.youtrack.api.mock.MockField;
import org.llorllale.youtrack.api.mock.MockFieldValue;
import org.llorllale.youtrack.api.mock.MockIssue;
import org.llorllale.youtrack.api.mock.MockProject;

/**
 * Unit tests for {@link XmlAssignedField}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public class XmlAssignedFieldTest {
  @Test
  public void testIssue() {
    final Issue issue = new MockIssue(new MockProject());
    assertThat(
        new XmlAssignedField(null, issue, null).issue(),
        is(issue)
    );
  }

  @Test
  public void testValue() throws Exception {
    final String FIELD_XML =
"    <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CustomFieldValue\" name=\"Priority\">\n" +
"      <value>Normal</value>\n" +
"      <valueId>Normal</valueId>\n" +
"      <color>\n" +
"        <bg>#e6f6cf</bg>\n" +
"        <fg>#4da400</fg>\n" +
"      </color>\n" +
"    </field>";
    final Field field = new MockField("Priority", new MockProject());
    assertThat(
        new XmlAssignedField(
            field, 
            new MockIssue(field.project()), 
            new XmlObject(new StringAsDocument(FIELD_XML))
        ).value(),
        is(new MockFieldValue(field, "Normal"))
    );
  }

  @Test
  public void testChange() throws Exception {
  }

  @Test
  public void testProject() {
  }

  @Test
  public void testName() {
  }

  @Test
  public void testHashCode() {
  }

  @Test
  public void testEquals() {
  }
}