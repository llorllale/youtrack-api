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
 * Combines and adapts a given {@link Field} and a given 
 * {@link org.llorllale.youtrack.api.jaxb.Field} into an {@link AssignedField}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 */
final class XmlAssignedField implements AssignedField {
  private final Field field;
  private final Issue issue;
  private final Xml xml;

  /**
   * Ctor.
   * 
   * @param field the {@link Field} to adapt
   * @param issue the parent {@link Issue}
   * @param xml the jaxb instance
   * @since 0.8.0
   */
  XmlAssignedField(Field field, Issue issue, Xml xml) {
    this.field = field;
    this.issue = issue;
    this.xml = xml;
  }

  @Override
  public Issue issue() {
    return this.issue;
  }

  @Override
  public FieldValue value() {
    return new XmlFieldValue(this.xml.child("value").get(), this);
  }

  @Override
  public Project project() {
    return this.field.project();
  }

  @Override
  public String name() {
    return this.field.name();
  }

  @Override
  public int hashCode() {
    return this.name().hashCode();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof AssignedField)) {
      return false;
    }

    final AssignedField other = (AssignedField) object;
    return this.field.isSameField(other) && this.value().equals(other.value());
  }
}
