/**
 * Copyright 2017 George Aristy
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
package org.llorllale.youtrack.api.util;

import org.junit.Test;
import org.llorllale.youtrack.api.issues.jaxb.Issue;

/**
 *
 * @author George Aristy
 * @since 0.1.0
 */
public class XmlStringAsJaxbTest {
  /**
   * Test of jaxb method, of class XmlStringAsJaxb.
   */
  @Test
  public void testAsJaxb() throws Exception {
    final Issue issue = new XmlStringAsJaxb<>(Issue.class, ISSUE).jaxb();
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