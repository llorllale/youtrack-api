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
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.mock.MockIssue;
import org.llorllale.youtrack.api.mock.MockProject;
import org.llorllale.youtrack.api.mock.http.MockSession;
import org.llorllale.youtrack.api.util.XmlStringAsJaxb;

/**
 * Unit tests for {@link XmlAssignedState}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.7.0
 */
public class XmlAssignedStateTest {
  private static org.llorllale.youtrack.api.jaxb.Issue jaxbIssue;

  @BeforeClass
  public static void setup() throws Exception {
    jaxbIssue = new XmlStringAsJaxb<>(org.llorllale.youtrack.api.jaxb.Issue.class).apply(ISSUE);
  }

  @Test
  public void testAsString() {
    assertThat(
        new XmlAssignedState(
            new MockIssue<>(
                new MockProject(), 
                jaxbIssue
            )        ).asString(),
        is(jaxbIssue.getField().stream()
            .filter(f -> "State".equals(f.getName()))
            .map(f -> f.getValue().getValue())
            .findAny()
            .get()
        )
    );
  }

  private static final String ISSUE =
"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
"<issue id=\"TPA-49\" entityId=\"2-50\">\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"projectShortName\">\n" +
"    <value>TPA</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"numberInProject\">\n" +
"    <value>49</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"summary\">\n" +
"    <value>DefaultCommentsIT</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"description\">\n" +
"    <value>Description</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"created\">\n" +
"    <value>1506113176667</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"updated\">\n" +
"    <value>1506523167492</value>\n" +
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
"    <value>3</value>\n" +
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
"    <value>Submitted</value>\n" +
"    <valueId>Submitted</valueId>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"MultiUserField\" name=\"Assignee\">\n" +
"    <value fullName=\"guest\">guest</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CustomFieldValue\" name=\"Estimation\">\n" +
"    <value>1440</value>\n" +
"    <valueId>3d</valueId>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CustomFieldValue\" name=\"Spent time\">\n" +
"    <value>960</value>\n" +
"    <valueId>2d</valueId>\n" +
"  </field>\n" +
"  <comment id=\"4-4\" author=\"root\" authorFullName=\"root\" issueId=\"TPA-49\" deleted=\"false\" text=\"First comment 757764468\" shownForIssueAuthor=\"false\" created=\"1506113176830\">\n" +
"    <replies/>\n" +
"  </comment>\n" +
"  <comment id=\"4-5\" author=\"root\" authorFullName=\"root\" issueId=\"TPA-49\" deleted=\"false\" text=\"Second comment 757764468\" shownForIssueAuthor=\"false\" created=\"1506113176909\">\n" +
"    <replies/>\n" +
"  </comment>\n" +
"  <comment id=\"4-6\" author=\"root\" authorFullName=\"root\" issueId=\"TPA-49\" deleted=\"false\" text=\"UpdatedComment_868956900\" shownForIssueAuthor=\"false\" created=\"1506113177057\" updated=\"1506113177155\">\n" +
"    <replies/>\n" +
"  </comment>\n" +
"</issue>";
}