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
import org.llorllale.youtrack.api.mock.MockField;
import org.llorllale.youtrack.api.mock.MockFieldValue;
import org.llorllale.youtrack.api.mock.MockProject;

/**
 * Unit tests for {@link XmlFieldValue}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 * @checkstyle MultipleStringLiterals (500 lines)
 * @checkstyle MethodName (500 lines)
 */
public final class XmlFieldValueTest {
  /**
   * The field's value is its representation in string form.
   * @throws Exception unexpected
   */
  @Test
  public void testAsString() throws Exception {
    assertThat(
      new XmlFieldValue(
        new XmlOf(new StringAsDocument("<value>Bug</value>")),
        null
      ).asString(),
      is("Bug")
    );
  }

  /**
   * Must be equal to itself.
   * @throws Exception unexpected
   */
  @Test
  public void equalsItself() throws Exception {
    final FieldValue fieldValue = new XmlFieldValue(
      new XmlOf(new StringAsDocument("<value>v</value>")),
      new MockField("f", new MockProject())
    );
    assertTrue(
      fieldValue.equals(fieldValue)
    );
  }

  /**
   * Must be equal to another fieldValue belonging to the same project, with the same name, and with
   * the same value.
   * @throws Exception unexpected
   */
  @Test
  public void equalsOtherFieldValue() throws Exception {
    final Field field = new MockField("field", new MockProject());
    assertTrue(
      new XmlFieldValue(
        new XmlOf(new StringAsDocument("<value>value</value>")),
        field
      ).equals(new MockFieldValue(field, "value"))
    );
  }

  /**
   * Cannot be equal to null.
   * @throws Exception unexpected
   */
  @Test
  public void notEqualsNull() throws Exception {
    assertFalse(
      new XmlFieldValue(
        new XmlOf(new StringAsDocument("<value>Bug</value>")),
        new MockField("field", new MockProject())
      ).equals(null)
    );
  }

  /**
   * Cannot be equal to another type.
   * @throws Exception unexpected
   */
  @Test
  public void notEqualsObject() throws Exception {
    assertFalse(
      new XmlFieldValue(
        new XmlOf(new StringAsDocument("<value>Test</value>")),
        new MockField("field", new MockProject())
      ).equals(new Object())
    );
  }

  /**
   * Cannot be equal to another fieldValue with a different name or value.
   * @throws Exception unexpected
   */
  @Test
  public void notEqualsOtherField() throws Exception {
    assertFalse(
      new XmlFieldValue(
        new XmlOf(new StringAsDocument("<value>test</value>")),
        new MockField("field1", new MockProject())
      ).equals(
        new MockFieldValue(
          new MockField("field2", new MockProject()
          ),
          "value"
        )
      )
    );
  }
}
