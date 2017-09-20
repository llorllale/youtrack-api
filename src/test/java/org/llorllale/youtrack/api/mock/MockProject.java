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

package org.llorllale.youtrack.api.mock;

import java.util.Objects;
import java.util.Optional;
import org.llorllale.youtrack.api.Issues;
import org.llorllale.youtrack.api.Project;

/**
 * Mock implementation of {@link Project} suitable for unit tests.
 * 
 * Note: {@link  #issues()} throws an UnsupportedOperationException.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public class MockProject implements Project {
  private final String id;
  private final String name;
  private final Optional<String> description;

  /**
   * Ctor.
   * @param id the mock project's id
   * @param name the mock project's name
   * @param description the mock project's description
   * @since 0.4.0
   */
  public MockProject(String id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = Optional.of(description);
  }

  @Override
  public String id() {
    return id;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public Optional<String> description() {
    return description;
  }

  @Override
  public Issues issues() {
    throw new UnsupportedOperationException("Not supported yet."); //TODO implement
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 29 * hash + Objects.hashCode(this.id);
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
    final MockProject other = (MockProject) obj;
    if (!Objects.equals(this.id, other.id)) {
      return false;
    }
    return true;
  }
}
