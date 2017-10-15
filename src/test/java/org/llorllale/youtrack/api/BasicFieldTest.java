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
 * Unit tests for {@link BasicField}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 */
public class BasicFieldTest {
  @Test
  public void testName() {
    final String name = "test";

    assertThat(
        new BasicField(name, null).name(),
        is(name)
    );
  }

  @Test
  public void equalsItself() {
    final Field field = new BasicField("field", new MockProject());

    assertTrue(
        field.equals(field)
    );
  }

  @Test
  public void equalsOtherField() {
    assertTrue(
        new BasicField("field", new MockProject()).equals(field("field", new MockProject()))
    );
  }

  @Test
  public void notEqualsNull() {
    assertFalse(
        new BasicField("field", new MockProject()).equals(null)
    );
  }

  @Test
  public void notEqualsDiffProjects() {
    assertFalse(
        new BasicField("field", new MockProject("p1", "p1", "")).equals(field("field", new MockProject("p2", "p2", "")))
    );
  }

  @Test
  public void notEqualsObject() {
    assertFalse(
        new BasicField("field", new MockProject()).equals(new Object())
    );
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
}