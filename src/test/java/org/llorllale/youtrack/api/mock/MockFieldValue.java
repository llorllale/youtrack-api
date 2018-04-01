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

package org.llorllale.youtrack.api.mock;

import org.llorllale.youtrack.api.Field;
import org.llorllale.youtrack.api.FieldValue;

/**
 * Mock implementation of {@link FieldValue} suitable for tests.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public final class MockFieldValue implements FieldValue {
  private final Field field;
  private final String value;

  /**
   * Primary ctor.
   * @param field the {@link #field() field}
   * @param value the {@link #asString() value}
   * @since 1.0.0
   */
  public MockFieldValue(Field field, String value) {
    this.field = field;
    this.value = value;
  }

  @Override
  public Field field() {
    return this.field;
  }

  @Override
  public String asString() {
    return this.value;
  }

  @Override
  public int hashCode() {
    return this.asString().hashCode();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof FieldValue)) {
      return false;
    }

    final FieldValue other = (FieldValue) object;
    return this.isEqualTo(other);
  }
}
