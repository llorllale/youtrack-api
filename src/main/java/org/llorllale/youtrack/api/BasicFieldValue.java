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

import java.util.Objects;

/**
 * Basic impl of {@link FieldValue}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 */
class BasicFieldValue implements FieldValue {
  private final String value;
  private final Field field;

  /**
   * Ctor.
   * 
   * @param value the value
   * @param field the owner {@link Field}
   * @since 0.8.0
   */
  BasicFieldValue(String value, Field field) {
    this.value = value;
    this.field = field;
  }

  @Override
  public Field field() {
    return field;
  }

  @Override
  public String asString() {
    return value;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 29 * hash + Objects.hashCode(this.value);
    hash = 29 * hash + Objects.hashCode(this.field);
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

    if (!FieldValue.class.isAssignableFrom(obj.getClass())) {
      return false;
    }

    final FieldValue other = (FieldValue) obj;

    if (!Objects.equals(this.asString(), other.asString())) {
      return false;
    }

    return Objects.equals(this.field(), other.field());
  }
}
