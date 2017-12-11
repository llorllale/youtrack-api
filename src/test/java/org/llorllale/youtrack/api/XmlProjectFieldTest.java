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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.mock.MockProject;
import org.llorllale.youtrack.api.mock.http.MockSession;

/**
 * Unit tests for {@link XmlProjectField}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 */
public class XmlProjectFieldTest {
  private static XmlObject xml;

  @BeforeClass
  public static void setup() throws Exception {
    xml = new XmlObject(new StringAsDocument(XML));
  }

  @Test
  public void testName() {
    assertThat(
        new XmlProjectField(xml, new MockProject(), new MockSession()).name(),
        is("Priority")
    );
  }

  @Test
  public void equalsItself() {
    final Field f = new XmlProjectField(xml, new MockProject(), new MockSession());

    assertTrue(
        f.equals(f)
    );
  }

  @Test
  public void equalsOtherField() {
    assertTrue(
        new XmlProjectField(
            xml, 
            new MockProject(), 
            new MockSession()
        ).equals(field("Priority", new MockProject()))
    );
  }

  @Test
  public void notEqualsNull() {
    assertFalse(
        new XmlProjectField(
            xml, 
            new MockProject(), 
            new MockSession()
        ).equals(null)
    );
  }

  @Test
  public void notEqualsObject() {
    assertFalse(
        new XmlProjectField(
            xml, 
            new MockProject(), 
            new MockSession()
        ).equals(new Object())
    );
  }

  @Test
  public void notEqualsFieldWithDiffName() throws Exception {
    assertFalse(
        new XmlProjectField(
            new XmlObject(new StringAsDocument(
                "<projectCustomField name=\"name1\" url=\"http://localhost/rest/admin/project/TP/customfield/Priority\"/>"
            )), 
            new MockProject(), 
            new MockSession()
        ).equals(field("name2", new MockProject()))
    );
  }

  @Test
  public void notEqualsFieldFromDiffProject() throws Exception {
    assertFalse(
        new XmlProjectField(
            new XmlObject(new StringAsDocument(
                "<projectCustomField name=\"name\" url=\"http://localhost/rest/admin/project/TP/customfield/Priority\"/>"
            )), 
            new MockProject("p1", "p1", ""), 
            new MockSession()
        ).equals(field("name", new MockProject("p2", "p2", "")))
    );
  }

  private static final String XML =
"<projectCustomField name=\"Priority\" url=\"http://localhost/rest/admin/project/TP/customfield/Priority\"/>"; 

  private Field field(String name, Project project) {
    return new Field() {
      @Override
      public Project project() {
        return project;
      }

      @Override
      public String name() {
        return name;
      }

      @Override
      public boolean equals(Object obj) {
        if(!(obj instanceof Field)) {
          return false;
        }

        final Field other = (Field) obj;
        return this.isSameField(other);
      }
    };
  }
}