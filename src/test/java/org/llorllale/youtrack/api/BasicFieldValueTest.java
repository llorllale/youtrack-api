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

// @checkstyle AvoidStaticImport (1 lines)
import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Test;
import org.llorllale.youtrack.api.mock.MockField;
import org.llorllale.youtrack.api.mock.MockFieldValue;
import org.llorllale.youtrack.api.mock.MockProject;

/**
 * Unit tests for {@link BasicFieldValue}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 * @checkstyle MultipleStringLiterals (500 lines)
 * @checkstyle MethodName (500 lines)
 */
public final class BasicFieldValueTest {
  /**
   * Returns given string.
   */
  @Test
  public void testAsString() {
    final String string = "test";
    assertThat(
      new BasicFieldValue(string, null).asString(),
      new IsEqual<>(string)
    );
  }

  /**
   * Cannot equal another type.
   */
  @Test
  public void notEqualsWithObject() {
    assertThat(
      new BasicFieldValue("", new MockField("test", new MockProject())),
      new IsNot<>(new IsEqual<>(new Object()))
    );
  }

  /**
   * Cannot equal null.
   */
  @Test
  public void notEqualsWithNull() {
    assertThat(
      new BasicFieldValue("", new MockField("test", new MockProject())),
      new IsNot<>(new IsEqual<>(null))
    );
  }

  /**
   * Cannot field another fieldValue with different value.
   */
  @Test
  public void notEqualsWithDiffFieldValue() {
    final Field field = new MockField("test", new MockProject());
    assertThat(
      new BasicFieldValue("value1", field),
      new IsNot<>(new IsEqual<>(
        new MockFieldValue(field, "value2")
      ))
    );
  }

  /**
   * Cannot equal another fieldValue with different field name.
   */
  @Test
  public void notEqualsWithDiffFields() {
    assertThat(
      new BasicFieldValue(
        "value",
        new MockField("field1", new MockProject())
      ),
      new IsNot<>(new IsEqual<>(
        new MockFieldValue(
          new MockField("field2", new MockProject()),
          "value"
        )     
      ))
    );
  }

  /**
   * Must equal itself.
   */
  @Test
  public void equalsItself() {
    final BasicFieldValue fieldValue = new BasicFieldValue(
      "value", new MockField("field", new MockProject())
    );
    assertThat(
      fieldValue,
      new IsEqual<>(fieldValue)
    );
  }

  /**
   * Must equal another fieldValue provided that both the name and value are equal.
   */
  @Test
  public void equalsOtherFieldValue() {
    final Field field = new MockField("field", new MockProject());
    assertThat(
      new BasicFieldValue("value", field),
      new IsEqual<>(new MockFieldValue(field, "value"))
    );
  }
}
