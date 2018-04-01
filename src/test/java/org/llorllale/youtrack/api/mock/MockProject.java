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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.llorllale.youtrack.api.Field;
import org.llorllale.youtrack.api.FieldValue;
import org.llorllale.youtrack.api.Fields;
import org.llorllale.youtrack.api.Issues;
import org.llorllale.youtrack.api.Project;
import org.llorllale.youtrack.api.ProjectField;
import org.llorllale.youtrack.api.ProjectTimeTracking;
import org.llorllale.youtrack.api.User;
import org.llorllale.youtrack.api.UsersOfProject;
import org.llorllale.youtrack.api.YouTrack;

/**
 * Mock implementation of {@link Project} suitable for unit tests.
 *
 * <p>Note: {@link  #issues()} throws an UnsupportedOperationException.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
@SuppressWarnings({"checkstyle:MethodCount", "checkstyle:MultipleStringLiterals"})
public final class MockProject implements Project {
  private final String id;
  private final String name;
  private final Optional<String> description;
  private final Map<Field, List<FieldValue>> fields;
  private final List<User> users;

  /**
   * Primary ctor.
   * @param id the mock project's id
   * @param name the mock project's name
   * @param description the mock project's description
   * @since 0.4.0
   */
  public MockProject(String id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = Optional.of(description);
    this.fields = new HashMap<>();
    this.users = new ArrayList<>();
  }

  /**
   * Ctor.
   *
   * <p>Assumes default values:
   * <ul>
   * <li>id -> ""</li>
   * <li>name -> ""</li>
   * <li>description -> ""</li>
   * </ul>
   * @since 0.4.0
   */
  public MockProject() {
    this("", "", "");
  }

  /**
   * Add {@code field} to this project's collection of configured {@link ProjectField fields}.
   * @param field field to configure for this project
   * @return this project
   * @since 1.0.0
   */
  public MockProject withField(MockProjectField field) {
    this.fields.merge(
      field,
      field.values().collect(Collectors.toList()),
      (first, second) -> {
        first.addAll(second);
        return first;
      }
    );
    return this;
  }

  /**
   * Shorthand for {@link #withField(org.llorllale.youtrack.api.ProjectField)}. Equivalent to doing:
   * <pre>
   * {@code
   * final MockProject project = ...;
   * final String fieldName = ...;
   * final String fieldValue = ...;
   * project.withField(
   *    new MockProjectField(
   *        fieldName,
   *        project,
   *        new MockFieldValue(
   *            new MockField(fieldName, project),
   *            fieldValue
   *        )
   *    )
   * );
   * }
   * </pre>
   * @param fieldName name of field
   * @param fieldValue value of field
   * @return this project
   * @since 1.0.0
   */
  public MockProject withFieldValue(String fieldName, String fieldValue) {
    return this.withField(
      new MockProjectField(
        fieldName,
        this,
        new MockFieldValue(
          new MockField(fieldName, this),
          fieldValue
        )
      )
    );
  }

  /**
   * Adds {@code user} to the collection of configured {@link User users} for this project.
   * @param user the user to add
   * @return this object
   * @since 1.0.0
   */
  public MockProject withUser(User user) {
    this.users.add(user);
    return this;
  }

  @Override
  public String id() {
    return this.id;
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public Optional<String> description() {
    return this.description;
  }

  @Override
  public Issues issues() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public int hashCode() {
    return this.id().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Project)) {
      return false;
    }

    final Project other = (Project) obj;
    return this.id().equals(other.id());
  }

  @Override
  public YouTrack youtrack() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Fields fields() {
    return new MockFields(
      this,
      Collections.unmodifiableCollection(
        this.fields.entrySet().stream().map(entry
          -> new MockProjectField(
          entry.getKey().name(),
          entry.getKey().project(),
          entry.getValue().toArray(new FieldValue[] {})
        )
        ).collect(Collectors.toList())
      )
    );
  }

  @Override
  public ProjectTimeTracking timetracking() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public UsersOfProject users() {
    return new MockUsersOfProject(this, Collections.unmodifiableList(this.users));
  }
}
