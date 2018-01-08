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

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.llorllale.youtrack.api.Field;
import org.llorllale.youtrack.api.FieldValue;
import org.llorllale.youtrack.api.Fields;
import org.llorllale.youtrack.api.Issue;
import org.llorllale.youtrack.api.Issues;
import org.llorllale.youtrack.api.Project;
import org.llorllale.youtrack.api.ProjectTimeTracking;
import org.llorllale.youtrack.api.TimeTrackEntryType;
import org.llorllale.youtrack.api.User;
import org.llorllale.youtrack.api.UsersOfProject;
import org.llorllale.youtrack.api.YouTrack;

/**
 * Mock implementation of {@link Project} suitable for unit tests.
 * 
 * <p>Note: {@link  #issues()} throws an UnsupportedOperationException.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public final class MockProject implements Project {
  private static final String DEFAULT_ATTR_VALUE = "";
  private final String id;
  private final String name;
  private final String description;
  private final Map<Field, Collection<FieldValue>> fields;
  private final Collection<User> users;
  private final Collection<Issue> issues;
  private final Collection<TimeTrackEntryType> timetrackTypes;
  private final YouTrack youtrack;

  /**
   * Primary ctor.
   * 
   * @param id this project's id
   * @param name this project's name
   * @param description this project's description
   * @param fields the fields configured for this project
   * @param users the users configured for this project
   * @param issues the issues belonging to this project
   * @param timeTrackTypes the {@link TimeTrackEntryType timetrack entry types} to configure for 
   *     this project
   * @param youtrack the parent {@link YouTrack}
   * @since 1.0.0
   */
  @SuppressWarnings("checkstyle:ParameterNumber")
  public MockProject(
      String id,
      String name,
      String description,
      Map<Field, Collection<FieldValue>> fields,
      Collection<User> users,
      Collection<Issue> issues,
      Collection<TimeTrackEntryType> timeTrackTypes,
      YouTrack youtrack
  ) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.fields = fields;
    this.users = users;
    this.issues = issues;
    this.timetrackTypes = timeTrackTypes;
    this.youtrack = youtrack;
  }

  /**
   * Shorthand ctor to quickly create a {@link MockProject} with {@link User users}.
   * 
   * @param id this project's id
   * @param name this project's name
   * @param description this project's description
   * @param users the users to configure for this project
   * @since 1.0.0
   */
  public MockProject(String id, String name, String description, Collection<User> users) {
    this(
        id, 
        name, 
        description, 
        Collections.emptyMap(), 
        users, 
        Collections.emptyList(), 
        Collections.emptyList(),
        null
    );
  }

  /**
   * Ctor.
   * 
   * @param id the mock project's id
   * @param name the mock project's name
   * @param description the mock project's description
   * @since 0.4.0
   */
  public MockProject(String id, String name, String description) {
    this(
        id, 
        name, 
        description, 
        Collections.emptyList()
    );
  }

  /**
   * Ctor.
   * 
   * <p>Assumes default values:
   * <ul>
   *    <li>id -> ""</li>
   *    <li>name -> ""</li>
   *    <li>description -> ""</li> 
   * </ul>
   * 
   * @since 0.4.0
   */
  public MockProject() {
    this(DEFAULT_ATTR_VALUE, DEFAULT_ATTR_VALUE, DEFAULT_ATTR_VALUE);
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
    return Optional.ofNullable(this.description);
  }

  @Override
  public Issues issues() {
    return new MockIssues(this.issues, this);
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
    return Objects.requireNonNull(this.youtrack, "YouTrack instance not provided.");
  }

  @Override
  public Fields fields() {
    return new MockFields(
        this, 
        Collections.unmodifiableCollection(
            this.fields.entrySet().stream().map(entry -> 
                new MockProjectField(
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
    return new MockProjectTimeTracking(
        this, 
        Collections.unmodifiableCollection(this.timetrackTypes)
    );
  }

  @Override
  public UsersOfProject users() {
    return new MockUsersOfProject(this, Collections.unmodifiableCollection(this.users));
  }
}
