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
import org.llorllale.youtrack.api.mock.MockProject;

/**
 * Unit tests for {@link BasicField}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 * @checkstyle MultipleStringLiterals (500 lines)
 * @checkstyle MethodName (500 lines)
 */
public final class BasicFieldTest {
  /**
   * Must return the given name.
   */
  @Test
  public void testName() {
    final String name = "test";
    assertThat(
      new BasicField(name, null).name(),
      new IsEqual<>(name)
    );
  }

  /**
   * Must be equal to itself.
   */
  @Test
  public void equalsItself() {
    final Field field = new BasicField("field", new MockProject());
    assertThat(
      field.equals(field),
      new IsEqual<>(true)
    );
  }

  /**
   * Must equal another field with the same name and belonging to the same project.
   */
  @Test
  public void equalsOtherField() {
    assertThat(
      new BasicField(
        "field",
        new MockProject()
      ),
      new IsEqual<>(
        new MockField(
          "field",
          new MockProject()
        )
      )
    );
  }

  /**
   * Cannot be equal to null.
   */
  @Test
  public void notEqualsNull() {
    assertThat(
      new BasicField("field", new MockProject()),
      new IsNot<>(new IsEqual<>(null))
    );
  }

  /**
   * Cannot be equal to another field with the same name but belong to a different project.
   */
  @Test
  public void notEqualsDiffProjects() {
    assertThat(
      new BasicField(
        "field",
        new MockProject("p1", "p1", "")
      ),
      new IsNot<>(
        new IsEqual<>(
          new MockField("field", new MockProject("p2", "p2", ""))
        )
      )
    );
  }

  /**
   * Cannot be equal to another type.
   */
  @Test
  public void notEqualsObject() {
    assertThat(
      new BasicField("field", new MockProject()),
      new IsNot<>(new IsEqual<>(new Object()))
    );
  }
}
