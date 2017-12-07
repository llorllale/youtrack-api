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
import org.llorllale.youtrack.api.jaxb.ProjectCustomField;
import org.llorllale.youtrack.api.mock.MockProject;
import org.llorllale.youtrack.api.mock.http.MockSession;

/**
 * Unit tests for {@link XmlProjectField}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 */
public class XmlProjectFieldTest {
  private static ProjectCustomField jaxb;

  @BeforeClass
  public static void setup() throws Exception {
    jaxb = new XmlStringAsJaxb<>(ProjectCustomField.class).apply(XML);
  }

  @Test
  public void testName() {
    assertThat(
        new XmlProjectField(jaxb, new MockProject(), new MockSession()).name(),
        is(jaxb.getName())
    );
  }

  @Test
  public void equalsItself() {
    final Field f = new XmlProjectField(jaxb, new MockProject(), new MockSession());

    assertTrue(
        f.equals(f)
    );
  }

  @Test
  public void equalsOtherField() {
    assertTrue(
        new XmlProjectField(jaxb, new MockProject(), new MockSession()).equals(field(jaxb.getName(), new MockProject()))
    );
  }

  @Test
  public void notEqualsNull() {
    assertFalse(
        new XmlProjectField(jaxb, new MockProject(), new MockSession()).equals(null)
    );
  }

  @Test
  public void notEqualsObject() {
    assertFalse(
        new XmlProjectField(jaxb, new MockProject(), new MockSession()).equals(new Object())
    );
  }

  @Test
  public void notEqualsFieldWithDiffName() {
    assertFalse(
        new XmlProjectField(
            new ProjectCustomField(){{setName("name1");}}, 
            new MockProject(), 
            new MockSession()
        ).equals(field("name2", new MockProject()))
    );
  }

  @Test
  public void notEqualsFieldFromDiffProject() {
    assertFalse(
        new XmlProjectField(
            new ProjectCustomField(){{setName("name");}}, 
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