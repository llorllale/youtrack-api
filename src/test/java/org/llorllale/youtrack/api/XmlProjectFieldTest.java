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

import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.mock.MockField;
import org.llorllale.youtrack.api.mock.MockProject;
import org.llorllale.youtrack.api.mock.http.MockSession;

/**
 * Unit tests for {@link XmlProjectField}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 * @checkstyle MultipleStringLiterals (500 lines)
 * @checkstyle MethodName (500 lines)
 */
public final class XmlProjectFieldTest {
  private static final String XML =
    // @checkstyle LineLength (1 line)
    "<projectCustomField name=\"Priority\" url=\"http://localhost/rest/admin/project/TP/customfield/Priority\"/>"; 

  private static Xml xml;

  /**
   * Setup.
   * @throws Exception unexpected
   */
  @BeforeClass
  public static void setup() throws Exception {
    xml = new XmlOf(new StringAsDocument(XML));
  }

  /**
   * Returns the xml's @name.
   */
  @Test
  public void testName() {
    assertThat(
      new XmlProjectField(xml, new MockProject(), new MockSession()).name(),
      is("Priority")
    );
  }

  /**
   * Must equal itself.
   */
  @Test
  public void equalsItself() {
    final Field field = new XmlProjectField(xml, new MockProject(), new MockSession());
    assertTrue(
      field.equals(field)
    );
  }

  /**
   * Must equal other field with same name.
   */
  @Test
  public void equalsOtherField() {
    assertTrue(
      new XmlProjectField(
        xml, 
        new MockProject(), 
        new MockSession()
      ).equals(
        new MockField("Priority", new MockProject())
      )
    );
  }

  /**
   * Cannot equal {@code null}.
   */
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

  /**
   * Cannot equal object that is not a ProjectField.
   */
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

  /**
   * Cannot equal a field witha different name.
   * @throws Exception unexpected
   */
  @Test
  public void notEqualsFieldWithDiffName() throws Exception {
    assertFalse(
      new XmlProjectField(
        new XmlOf(new StringAsDocument(
          // @checkstyle LineLength (1 line)
          "<projectCustomField name=\"name1\" url=\"http://localhost/rest/admin/project/TP/customfield/Priority\"/>"
        )), 
        new MockProject(), 
        new MockSession()
      ).equals(
        new MockField("name2", new MockProject())
      )
    );
  }

  /**
   * Cannot equal a field from another project.
   * @throws Exception unexpected
   */
  @Test
  public void notEqualsFieldFromDiffProject() throws Exception {
    assertFalse(
      new XmlProjectField(
        new XmlOf(new StringAsDocument(
          // @checkstyle LineLength (1 line)
          "<projectCustomField name=\"name\" url=\"http://localhost/rest/admin/project/TP/customfield/Priority\"/>"
        )), 
        new MockProject("p1", "p1", ""), 
        new MockSession()
      ).equals(
        new MockField("name", new MockProject("p2", "p2", ""))
      )
    );
  }
}
