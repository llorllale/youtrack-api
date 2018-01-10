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

import java.io.IOException;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.llorllale.youtrack.api.AssignedField;
import org.llorllale.youtrack.api.Comment;
import org.llorllale.youtrack.api.Comments;
import org.llorllale.youtrack.api.Field;
import org.llorllale.youtrack.api.FieldValue;
import org.llorllale.youtrack.api.Issue;
import org.llorllale.youtrack.api.IssueTimeTracking;
import org.llorllale.youtrack.api.Project;
import org.llorllale.youtrack.api.TimeTrackEntry;
import org.llorllale.youtrack.api.UpdateIssue;
import org.llorllale.youtrack.api.User;
import org.llorllale.youtrack.api.UsersOfIssue;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Mock implementation of {@link Issue} suitable for unit tests.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
//method count currently at 14
@SuppressWarnings("checkstyle:MethodCount")
public final class MockIssue implements Issue {
  private static final String DEFAULT_ATTR_VALUE = "";
  private final Project project;
  private final String id;
  private final Instant creationDate;
  private final String summary;
  private final String description;
  private final Map<Field, FieldValue> fields;
  private final UsersOfIssue usersOfIssue;
  private final Collection<Comment> comments;
  private final Collection<TimeTrackEntry> entries;

  /**
   * Primary ctor.
   * 
   * @param project the associate project
   * @param id the id for this issue
   * @param creationDate this issue's date of creation
   * @param summary this issue's summary
   * @param description this issue's description (may be {@code null})
   * @param fields this issue's assigned fields
   * @param creator the user that created this issue
   * @param assignee the user assigned to this issue (may be {@code null})
   * @param updater the last user that updated this issue (may be {@code null})
   * @param comments all comments for this issue
   * @param entries all {@link TimeTrackEntry entries} for this issue
   * @since 0.4.0
   */
  @SuppressWarnings("checkstyle:ParameterNumber")
  public MockIssue(
      Project project, 
      String id, 
      Instant creationDate, 
      String summary, 
      String description,
      Map<Field, FieldValue> fields,
      User creator,
      User assignee,
      User updater,
      Collection<Comment> comments,
      Collection<TimeTrackEntry> entries
  ) {
    this.project = project;
    this.id = id;
    this.creationDate = creationDate;
    this.summary = summary;
    this.description = description;
    this.fields = fields;
    this.usersOfIssue = new MockUsersOfIssue(this, creator, assignee, updater);
    this.comments = comments;
    this.entries = entries;
  }

  /**
   * Ctor.
   * 
   * @param project the associate project
   * @param id the id for this issue
   * @param creationDate this issue's date of creation
   * @param summary this issue's summary
   * @param description this issue's description (may be {@code null})
   * @param fields this issue's assigned fields
   * @param creator the user that created this issue
   * @since 1.0.0
   */
  @SuppressWarnings("checkstyle:ParameterNumber")
  public MockIssue(
      Project project, 
      String id, 
      Instant creationDate, 
      String summary, 
      String description,
      Map<Field, FieldValue> fields,
      User creator
  ) {
    this(
        project, 
        id, 
        creationDate, 
        summary, 
        description, 
        fields, 
        creator, 
        null, 
        null, 
        Collections.emptyList(),
        Collections.emptyList()
    );
  }

  /**
   * This issue's {@link #id() id}, {@link #summary() summary}, and 
   * {@link #description() description} are all set to empty strings. Its creation date is
   * set to {@link Instant#now() now}.
   * 
   * @param project the associated project
   * @since 0.4.0
   */
  public MockIssue(Project project) {
    this(
        project, 
        DEFAULT_ATTR_VALUE, 
        Instant.now(), 
        DEFAULT_ATTR_VALUE, 
        DEFAULT_ATTR_VALUE,
        Collections.emptyMap(),
        null
    );
  }

  /**
   * This issue's {@link #summary() summary} and {@link #description() description} are set to
   * empty strings. Its creation date is set to {@link Instant#now() now}.
   * 
   * @param project the associated project
   * @param id this issue's id
   * @since 1.0.0
   */
  public MockIssue(Project project, String id) {
    this(
        project, 
        id, 
        Instant.now(), 
        DEFAULT_ATTR_VALUE, 
        DEFAULT_ATTR_VALUE, 
        Collections.emptyMap(),
        null
    );
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 97 * hash + Objects.hashCode(this.project);
    hash = 97 * hash + Objects.hashCode(this.id);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Issue)) {
      return false;
    }

    final Issue other = (Issue) object;
    return this.id().equals(other.id()) && this.project().equals(other.project());
  }

  @Override
  public Project project() {
    return this.project;
  }

  @Override
  public String id() {
    return this.id;
  }

  @Override
  public Instant creationDate() {
    return this.creationDate;
  }

  @Override
  public String summary() {
    return this.summary;
  }

  @Override
  public Optional<String> description() {
    return Optional.of(this.description);
  }

  @Override
  public Comments comments() {
    return new MockComments(Collections.unmodifiableCollection(this.comments), this);
  }

  @Override
  public IssueTimeTracking timetracking() {
    return new MockIssueTimeTracking(this, this.entries);
  }

  @Override
  public UsersOfIssue users() {
    return this.usersOfIssue;
  }

  @Override
  public Issue refresh() throws IOException, UnauthorizedException {
    return this;
  }

  @Override
  public Collection<AssignedField> fields() {
    return this.fields.entrySet().stream()
        .map(entry -> 
            new MockAssignedField(
                entry.getKey().name(), 
                this, 
                entry.getValue().asString()
            )
        ).collect(Collectors.toList());
  }

  @Override
  public UpdateIssue update() {
    return new MockUpdateIssue(this);
  }
}
