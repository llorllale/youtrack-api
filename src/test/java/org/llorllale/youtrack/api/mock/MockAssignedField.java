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

import org.llorllale.youtrack.api.AssignedField;
import org.llorllale.youtrack.api.FieldValue;
import org.llorllale.youtrack.api.Issue;
import org.llorllale.youtrack.api.Project;

/**
 * Mock implementation of {@link AssignedField} suitable for tests.
 * 
 * <p>Note: the {@link #change()} operation is not supported.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public final class MockAssignedField implements AssignedField {
  private final String name;
  private final Issue issue;
  private final String value;

  /**
   * Primary ctor.
   * @param name this field's name
   * @param issue the issue this field is assigned to
   * @param value this field's {@link MockFieldValue value}
   * @since 1.0.0
   */
  public MockAssignedField(String name, Issue issue, String value) {
    this.name = name;
    this.issue = issue;
    this.value = value;
  }

  @Override
  public Issue issue() {
    return this.issue;
  }

  @Override
  public FieldValue value() {
    return new MockFieldValue(this, this.value);
  }

  @Override
  public Project project() {
    return this.issue().project();
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof AssignedField)) {
      return false;
    }

    final AssignedField other = (AssignedField) object;
    return this.isSameField(other) && this.value().equals(other.value());
  }

  @Override
  public int hashCode() {
    return this.name().hashCode();
  }
}
