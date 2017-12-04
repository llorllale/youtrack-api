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
import org.apache.http.message.BasicNameValuePair;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.llorllale.youtrack.api.Issues.IssueSpec;

/**
 * Unit tests for {@link IssueSpec}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public class IssueSpecTest {
  @Test
  public void nameValuePairs() {
    assertThat(
        new IssueSpec("summary", "description").nameValuePairs(),
        containsInAnyOrder(
            new BasicNameValuePair("summary", "summary"),
            new BasicNameValuePair("description", "description")
        )
    );
  }

  /**
   * Test {@link IssueSpec#fields()}.
   * 
   * @since 0.8.0
   */
  @Test
  public void fields() {
    final Field f1 = new TestField("field1");
    final FieldValue v1 = new TestFieldValue("value1");
    final Field f2 = new TestField("field2");
    final FieldValue v2 = new TestFieldValue("value2");

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
    final Field f1 = new TestField("field1");
    final FieldValue v1 = new TestFieldValue("value1");
    final Field f2 = new TestField("field2");
    final FieldValue v2 = new TestFieldValue("value2");   

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
    final Field f1 = new TestField("field1");
    final FieldValue v1 = new TestFieldValue("value1");
    final Field f2 = new TestField("field2");
    final FieldValue v2 = new TestFieldValue("value2");   

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
  public void equalsIfFalseWithDiffNameValuePairs() {
    final Field f1 = new TestField("field1");
    final FieldValue v1 = new TestFieldValue("value1");
    final Field f2 = new TestField("field2");
    final FieldValue v2 = new TestFieldValue("value2");

    assertFalse(
        new IssueSpec("summary").with(f1, v1).with(f2, v2).equals(
            new IssueSpec("summary", "description").with(f1, v1).with(f2, v2)
        )
    );
  }

  /**
   * Hashcode must be equal to hashcodes of namevaluepairs and fields.
   * 
   * @since 1.0.0
   */
  @Test
  public void testHashCode() {
    final IssueSpec spec = new IssueSpec("summary", "description")
        .with(new TestField("f1"), new TestFieldValue("v1"))
        .with(new TestField("f2"), new TestFieldValue("v2"));

    assertThat(
        spec.hashCode(),
        is(spec.nameValuePairs().hashCode() + spec.fields().hashCode())
    );
  }

  private static class TestField implements Field {
    private final String name;

    TestField(String name) {
      this.name = name;
    }

    @Override
    public Project project() {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String name() {
      return name;
    }

    @Override
    public int hashCode() {
      int hash = 7;
      hash = 37 * hash + Objects.hashCode(this.name);
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
      final TestField other = (TestField) obj;
      if (!Objects.equals(this.name, other.name)) {
        return false;
      }
      return true;
    }
  }

  private static class TestFieldValue implements FieldValue {
    private final String string;

    TestFieldValue(String string) {
      this.string = string;
    }

    @Override
    public Field field() {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String asString() {
      return string;
    }

    @Override
    public int hashCode() {
      int hash = 7;
      hash = 13 * hash + Objects.hashCode(this.string);
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
      final TestFieldValue other = (TestFieldValue) obj;
      if (!Objects.equals(this.string, other.string)) {
        return false;
      }
      return true;
    }
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
