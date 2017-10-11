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
 * Default impl of {@link Field}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 */
class BasicField implements Field {
  private final String name;
  private final Project project;

  /**
   * Primary ctor.
   * 
   * @param name the field's name
   * @param project the parent {@link Project}
   * @since 0.8.0
   */
  BasicField(String name, Project project) {
    this.name = name;
    this.project = project;
  }

  @Override
  public Project project() {
    return project;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 67 * hash + Objects.hashCode(this.name());
    hash = 67 * hash + Objects.hashCode(this.project().id());
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

    if (!this.getClass().isAssignableFrom(obj.getClass())) {
      return false;
    }

    final BasicField other = (BasicField) obj;

    return this.isSameField(other);
  }
}
