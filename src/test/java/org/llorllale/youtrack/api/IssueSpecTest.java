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

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.llorllale.youtrack.api.Issues.IssueSpec;
import org.llorllale.youtrack.api.mock.MockField;
import org.llorllale.youtrack.api.mock.MockFieldValue;
import org.llorllale.youtrack.api.mock.MockProject;

/**
 * Unit tests for {@link IssueSpec}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public class IssueSpecTest {
  /**
   * {@link IssueSpec#summary()} must return the input summary text.
   * 
   * @since 1.0.0
   */
  @Test
  public void summary() {
    assertThat(
        new IssueSpec("summary").summary(),
        is("summary")
    );
  }

  /**
   * {@link IssueSpec#description()} must return an optional with the given description.
   * 
   * @since 1.0.0
   */
  @Test
  public void description() {
    assertThat(
        new IssueSpec("", "description").description(),
        is(Optional.of("description"))
    );
  }

  /**
   * {@link IssueSpec#description()} must return an empty optional if no description was provided.
   * 
   * @since 1.0.0
   */
  @Test
  public void noDescription() {
    assertThat(
        new IssueSpec("summary").description(),
        is(Optional.empty())
    );
  }

  /**
   * Test {@link IssueSpec#fields()}.
   * 
   * @since 0.8.0
   */
  @Test
  public void fields() {
    final Field f1 = new MockField("field1", new MockProject());
    final FieldValue v1 = new MockFieldValue(f1, "value1");
    final Field f2 = new MockField("field2", new MockProject());
    final FieldValue v2 = new MockFieldValue(f2, "value2");

    assertThat(
        new IssueSpec("", "").with(f1, v1).with(f2, v2).fields().entrySet(),
        containsInAnyOrder(
            new MapEntry(f1, v1),
            new MapEntry(f2, v2)
        )
    );
  }

  /**
   * Two IssueSpecs are equal if they are constructed from the same inputs.
   * 
   * @since 1.0.0
   */
  @Test
  public void equal() {
    final Field f1 = new MockField("field1", new MockProject());
    final FieldValue v1 = new MockFieldValue(f1, "value1");
    final Field f2 = new MockField("field2", new MockProject());
    final FieldValue v2 = new MockFieldValue(f2, "value2");   

    assertThat(
        new IssueSpec("", "").with(f1, v1).with(f2, v2),
        is(new IssueSpec("", "").with(f1, v1).with(f2, v2))
    );
  }

  /**
   * Two IssueSpecs are not equal if they are not constructed from the same inputs.
   * 
   * @since 1.0.0
   */
  @Test
  public void notEqual() {
    final Field f1 = new MockField("field1", new MockProject());
    final FieldValue v1 = new MockFieldValue(f1, "value1");
    final Field f2 = new MockField("field2", new MockProject());
    final FieldValue v2 = new MockFieldValue(f2, "value2");   

    assertThat(
        new IssueSpec("summary", "desc").with(f1, v1).with(f2, v2),
        is(not(new IssueSpec("summary", "description").with(f2, v2)))
    );
  }

  /**
   * Equals must return false if the input argument is {@code null}.
   * 
   * @since 1.0.0
   */
  @Test
  public void equalsIsFalseWithNull() {
    assertFalse(
        new IssueSpec("summary", "desc").equals(null)
    );
  }

  /**
   * equals must return false if the input object is not of type IssueSpec.
   * 
   * @since 1.0.0
   */
  @Test
  public void equalsIsFalseWithNonIssueSpec() {
    assertFalse(
        new IssueSpec("summary", "desc").equals(new Object())
    );
  }

  /**
   * equals must return false if fields differ in length.
   * 
   * @since 1.0.0
   */
  @Test
  public void equalsIfFalseWithDiffDescription() {
    final Field f1 = new MockField("field1", new MockProject());
    final FieldValue v1 = new MockFieldValue(f1, "value1");
    final Field f2 = new MockField("field2", new MockProject());
    final FieldValue v2 = new MockFieldValue(f2, "value2");

    assertFalse(
        new IssueSpec("summary").with(f1, v1).with(f2, v2).equals(
            new IssueSpec("summary", "description").with(f1, v1).with(f2, v2)
        )
    );
  }

  /**
   * Hashcode must be equal to hashcodes of the summary, description, and the fields.
   * 
   * @since 1.0.0
   */
  @Test
  public void testHashCode() {
    final Field f1 = new MockField("f1", new MockProject());
    final FieldValue v1 = new MockFieldValue(f1, "v1");
    final Field f2 = new MockField("f2", new MockProject());
    final FieldValue v2 = new MockFieldValue(f2, "v2");
    final IssueSpec spec = new IssueSpec("summary", "description")
        .with(f1, v1)
        .with(f2, v2);

    assertThat(
        spec.hashCode(),
        is(spec.summary().hashCode() + spec.description().hashCode() + spec.fields().hashCode())
    );
  }

  private static class MapEntry implements Map.Entry<Field,FieldValue> {
    private final Field key;
    private final FieldValue value;

    MapEntry(Field key, FieldValue value) {
      this.key = key;
      this.value = value;
    }

    @Override
    public Field getKey() {
      return key;
    }

    @Override
    public FieldValue getValue() {
      return value;
    }

    @Override
    public FieldValue setValue(FieldValue value) {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
      int hash = 7;
      hash = 23 * hash + Objects.hashCode(this.key);
      hash = 23 * hash + Objects.hashCode(this.value);
      return hash;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      final MapEntry other = (MapEntry) obj;
      if (!Objects.equals(this.key, other.key)) {
        return false;
      }
      if (!Objects.equals(this.value, other.value)) {
        return false;
      }
      return true;
    }
  }
}
