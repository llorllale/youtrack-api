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

import org.llorllale.youtrack.api.jaxb.Value;

/**
 * JAXB impl of {@link FieldValue}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 */
class XmlFieldValue implements FieldValue {
  private final Value jaxb;
  private final Field field;

  /**
   * Primary ctor.
   * @param jaxb the jaxb instance to adapt
   * @param field the parent {@link Field}
   * @since 0.8.0
   */
  XmlFieldValue(Value jaxb, Field field) {
    this.jaxb = jaxb;
    this.field = field;
  }

  @Override
  public Field field() {
    return this.field;
  }

  @Override
  public String asString() {
    return this.jaxb.getValue();
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 59 * hash + Objects.hashCode(this.asString());
    hash = 59 * hash + Objects.hashCode(this.field);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof FieldValue)) {
      return false;
    }

    final FieldValue other = (FieldValue) obj;

    if (!Objects.equals(this.asString(), other.asString())) {
      return false;
    }

    return Objects.equals(this.field(), other.field());
  }
}
