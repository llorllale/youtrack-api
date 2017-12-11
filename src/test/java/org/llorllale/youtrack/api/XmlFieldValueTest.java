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
import org.junit.Test;
import org.llorllale.youtrack.api.mock.MockProject;

/**
 * Unit tests for {@link XmlFieldValue}
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 */
public class XmlFieldValueTest {
  @Test
  public void testAsString() throws Exception {
    assertThat(
        new XmlFieldValue(
            new XmlObject(new StringAsDocument("<value>Bug</value>")), 
            null
        ).asString(),
        is("Bug")
    );
  }

  @Test
  public void equalsItself() throws Exception {
    final FieldValue fv = new XmlFieldValue(
        new XmlObject(new StringAsDocument("<value>v</value>")), 
        field("f")
    );

    assertTrue(
        fv.equals(fv)
    );
  }

  @Test
  public void equalsOtherFieldValue() throws Exception {
    assertTrue(
      new XmlFieldValue(
          new XmlObject(new StringAsDocument("<value>value</value>")), 
          field("field")
      ).equals(fieldValue("field", "value"))
    );
  }

  @Test
  public void notEqualsNull() throws Exception {
    assertFalse(
        new XmlFieldValue(
            new XmlObject(new StringAsDocument("<value>Bug</value>")), 
            field("field")
        ).equals(null)
    );
  }

  @Test
  public void notEqualsObject() throws Exception {
    assertFalse(
        new XmlFieldValue(
            new XmlObject(new StringAsDocument("<value>Test</value>")), 
            field("field")
        ).equals(new Object())
    );
  }

  @Test
  public void notEqualsOtherField() throws Exception {
    assertFalse(
        new XmlFieldValue(
            new XmlObject(new StringAsDocument("<value>test</value>")), 
            field("field1")
        ).equals(fieldValue("value", "field2"))
    );
  }

  private Field field(String name) {
    return field(name, new MockProject());
  }

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

  private FieldValue fieldValue(String fieldName, String fieldValue) {
    return new FieldValue() {
      @Override
      public Field field() {
        return XmlFieldValueTest.this.field(fieldName);
      }

      @Override
      public String asString() {
        return fieldValue;
      }
    };
  }
}