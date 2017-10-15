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
import org.junit.Test;
import static org.junit.Assert.*;
import org.llorllale.youtrack.api.mock.MockProject;

/**
 * Unit tests for {@link BasicFieldValue}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0-SNAPSHOT
 */
public class BasicFieldValueTest {
  @Test
  public void testAsString() {
    final String string = "test";

    assertThat(
        new BasicFieldValue(string, null).asString(),
        is(string)
    );
  }

  @Test
  public void notEqualsWithObject() {
    assertFalse(
        new BasicFieldValue("", field("test")).equals(new Object())
    );
  }

  @Test
  public void notEqualsWithNull() {
    assertFalse(
        new BasicFieldValue("", field("test")).equals(null)
    );
  }

  @Test
  public void notEqualsWithDiffFieldValue() {
    assertFalse(
        new BasicFieldValue("value1", field("test")).equals(fieldValue("test", "value2"))
    );
  }

  @Test
  public void notEqualsWithDiffFields() {
    assertFalse(
        new BasicFieldValue("value", field("field1")).equals(fieldValue("field2", "value"))
    );
  }

  @Test
  public void equalsItself() {
    final BasicFieldValue fv = new BasicFieldValue("value", field("field"));

    assertTrue(
        fv.equals(fv)
    );
  }

  @Test
  public void equalsOtherFieldValue() {
    assertTrue(
      new BasicFieldValue("value", field("field")).equals(fieldValue("field", "value"))
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
        return BasicFieldValueTest.this.field(fieldName);
      }

      @Override
      public String asString() {
        return fieldValue;
      }
    };
  }
}