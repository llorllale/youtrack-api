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

import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import org.llorllale.youtrack.api.mock.MockField;
import org.llorllale.youtrack.api.mock.MockFieldValue;
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
        new BasicFieldValue("", new MockField("test", new MockProject())).equals(new Object())
    );
  }

  @Test
  public void notEqualsWithNull() {
    assertFalse(
        new BasicFieldValue("", new MockField("test", new MockProject())).equals(null)
    );
  }

  @Test
  public void notEqualsWithDiffFieldValue() {
    final Field field = new MockField("test", new MockProject());
    assertFalse(
        new BasicFieldValue(
            "value1", 
            field
        ).equals(new MockFieldValue(field, "value2"))
    );
  }

  @Test
  public void notEqualsWithDiffFields() {
    assertFalse(
        new BasicFieldValue(
            "value", 
            new MockField("field1", new MockProject())
        ).equals(
            new MockFieldValue(
                new MockField("field2", new MockProject()), 
                "value"
            )
        )
    );
  }

  @Test
  public void equalsItself() {
    final BasicFieldValue fv = new BasicFieldValue("value", new MockField("field", new MockProject()));

    assertTrue(
        fv.equals(fv)
    );
  }

  @Test
  public void equalsOtherFieldValue() {
    final Field field = new MockField("field", new MockProject());
    assertTrue(
      new BasicFieldValue("value", field).equals(new MockFieldValue(field, "value"))
    );
  }
}
