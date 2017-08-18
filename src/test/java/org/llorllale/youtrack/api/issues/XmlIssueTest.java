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

package org.llorllale.youtrack.api.issues;

import java.time.Instant;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.util.XmlStringAsJaxb;

/**
 * Unit tests for {@link XmlIssue}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.2.0
 */
public class XmlIssueTest {
  private static org.llorllale.youtrack.api.issues.jaxb.Issue jaxbIssue;

  @BeforeClass
  public static void setup() throws Exception {
    jaxbIssue = new XmlStringAsJaxb<>(
        org.llorllale.youtrack.api.issues.jaxb.Issue.class, 
        ISSUE
    ).jaxb();
  }

  @Test
  public void testId() {
    assertThat(
        new XmlIssue(jaxbIssue).id(),
        is(jaxbIssue.getId())
    );
  }

  @Test
  public void testCreationDate() {
    assertThat(
        new XmlIssue(jaxbIssue).creationDate(),
        is(jaxbIssue.getField()
            .stream()
            .filter(f -> "created".equals(f.getName()))
            .map(f -> f.getValue().getValue())
            .map(v -> Instant.ofEpochMilli(Long.parseLong(v)))
            .findAny()
            .get()
        )
    );
  }

  @Test
  public void testType() {
    assertThat(
        new XmlIssue(jaxbIssue).type(),
        is(jaxbIssue.getField()
            .stream()
            .filter(f -> "Type".equals(f.getName()))
            .map(f -> f.getValue().getValue())
            .findAny()
            .get()
        )
    );
  }

  @Test
  public void testState() {
    assertThat(
        new XmlIssue(jaxbIssue).state(),
        is(jaxbIssue.getField()
            .stream()
            .filter(f -> "State".equals(f.getName()))
            .map(f -> f.getValue().getValue())
            .findAny()
            .get()
        )
    );
  }

  @Test
  public void testAssignee() {
    assertThat(
        new XmlIssue(jaxbIssue).assignee(),
        is(jaxbIssue.getField()
            .stream()
            .filter(f -> "Assignee".equals(f.getName()))
            .map(f -> f.getValue().getValue())
            .findAny()
            .get()
        )
    );
  }

  @Test
  public void testPriority() {
    assertThat(
        new XmlIssue(jaxbIssue).priority(),
        is(jaxbIssue.getField()
            .stream()
            .filter(f -> "Priority".equals(f.getName()))
            .map(f -> f.getValue().getValue())
            .findAny()
            .get()
        )
    );
  }

  @Test
  public void testReporterName() {
    assertThat(
        new XmlIssue(jaxbIssue).reporterName(),
        is(jaxbIssue.getField()
            .stream()
            .filter(f -> "reporterName".equals(f.getName()))
            .map(f -> f.getValue().getValue())
            .findAny()
            .get()
        )
    );
  }

  @Test
  public void testUpdaterName() {
    assertThat(
        new XmlIssue(jaxbIssue).updaterName().get(),
        is(jaxbIssue.getField()
            .stream()
            .filter(f -> "updaterName".equals(f.getName()))
            .map(f -> f.getValue().getValue())
            .findAny()
            .get()
        )
    );
  }

  @Test
  public void testSummary() {
    assertThat(
        new XmlIssue(jaxbIssue).summary(),
        is(jaxbIssue.getField()
            .stream()
            .filter(f -> "summary".equals(f.getName()))
            .map(f -> f.getValue().getValue())
            .findAny()
            .get()
        )
    );
  }

  @Test
  public void testDescription() {
    assertThat(
        new XmlIssue(jaxbIssue).description(),
        is(jaxbIssue.getField()
            .stream()
            .filter(f -> "description".equals(f.getName()))
            .map(f -> f.getValue().getValue())
            .findAny()
            .get()
        )
    );
  }

  @Test
  public void testProjectId() {
    assertThat(
        new XmlIssue(jaxbIssue).projectId(),
        is(jaxbIssue.getField()
            .stream()
            .filter(f -> "projectShortName".equals(f.getName()))
            .map(f -> f.getValue().getValue())
            .findAny()
            .get()
        )
    );
  }

  private static final String ISSUE =
"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
"<issue id=\"TP-2\" entityId=\"91-4\">\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"projectShortName\">\n" +
"    <value>TP</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"numberInProject\">\n" +
"    <value>2</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"summary\">\n" +
"    <value>Test Issue 2</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"description\">\n" +
"    <value>This is the second test issue.</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"created\">\n" +
"    <value>1501810666764</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"updated\">\n" +
"    <value>1501810666764</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"updaterName\">\n" +
"    <value>root</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"updaterFullName\">\n" +
"    <value>root</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"reporterName\">\n" +
"    <value>root</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"reporterFullName\">\n" +
"    <value>root</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"commentsCount\">\n" +
"    <value>0</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"votes\">\n" +
"    <value>0</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CustomFieldValue\" name=\"Priority\">\n" +
"    <value>Normal</value>\n" +
"    <valueId>Normal</valueId>\n" +
"    <color>\n" +
"      <bg>#e6f6cf</bg>\n" +
"      <fg>#4da400</fg>\n" +
"    </color>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CustomFieldValue\" name=\"Type\">\n" +
"    <value>Bug</value>\n" +
"    <valueId>Bug</valueId>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CustomFieldValue\" name=\"State\">\n" +
"    <value>Open</value>\n" +
"    <valueId>Open</valueId>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"MultiUserField\" name=\"Assignee\">\n" +
"    <value fullName=\"root\">root</value>\n" +
"  </field>\n" +
"</issue>"; 
}