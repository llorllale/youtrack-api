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

/**
 * XML impl of {@link FieldValue}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 */
final class XmlFieldValue implements FieldValue {
  private final Xml xml;
  private final Field field;

  /**
   * Primary ctor.
   * 
   * @param xml the xml recieved from YouTrack
   * @param field the parent {@link Field}
   * @since 0.8.0
   */
  XmlFieldValue(Xml xml, Field field) {
    this.xml = xml;
    this.field = field;
  }

  @Override
  public Field field() {
    return this.field;
  }

  @Override
  public String asString() {
    return this.xml.textOf("text()").get();
  }

  @Override
  public int hashCode() {
    return this.asString().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof FieldValue)) {
      return false;
    }

    final FieldValue other = (FieldValue) obj;
    return this.isEqualTo(other);
  }
}
