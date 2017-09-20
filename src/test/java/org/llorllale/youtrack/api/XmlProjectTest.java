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
import org.llorllale.youtrack.api.mock.http.MockSession;
import org.llorllale.youtrack.api.util.XmlStringAsJaxb;

/**
 * Unit tests for {@link XmlProject}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public class XmlProjectTest {
  private static org.llorllale.youtrack.api.jaxb.Project jaxbProject;

  @BeforeClass
  public static void setup() throws Exception {
    jaxbProject = new XmlStringAsJaxb<>(
        org.llorllale.youtrack.api.jaxb.Project.class, 
        PROJECT
    ).jaxb();
  }

  @Test
  public void testId() {
    assertThat(
        new XmlProject(new MockSession(), jaxbProject).id(),
        is(jaxbProject.getShortName())
    );
  }

  @Test
  public void testName() {
    assertThat(
        new XmlProject(new MockSession(), jaxbProject).name(),
        is(jaxbProject.getName())
    );
  }

  @Test
  public void testDescription() {
    assertThat(
        new XmlProject(new MockSession(), jaxbProject).description().get(),
        is(jaxbProject.getDescription())
    );
  }

  private static final String PROJECT =
"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
"<project versions=\"[2.0, 2.0.1, 2.0.2, 2.0.3, 2.0.4, 2.0.5, 2.0.6, 2.0.7, 2.0.8]\" name=\"Hibero\" shortName=\"HBR\" description=\"Makes developing Hibernate applications a pleasure.\" isImporting=\"false\">\n" +
"  <assigneesFullName>\n" +
"    <sub value=\"Adam Jordens\"/>\n" +
"    <sub value=\"Application Exception\"/>\n" +
"  </assigneesFullName>\n" +
"  <assigneesLogin>\n" +
"    <sub value=\"ajordens\"/>\n" +
"    <sub value=\"app_exception\"/>\n" +
"  </assigneesLogin>\n" +
"  <subsystems>\n" +
"    <sub value=\"No subsystem\"/>\n" +
"    <sub value=\"Configuration\"/>\n" +
"    <sub value=\"HQL\"/>\n" +
"    <sub value=\"Settings\"/>\n" +
"    <sub value=\"UI\"/>\n" +
"  </subsystems>\n" +
"</project>";
}