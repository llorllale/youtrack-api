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

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.llorllale.youtrack.api.AssignedField;
import org.llorllale.youtrack.api.Comments;
import org.llorllale.youtrack.api.Issue;
import org.llorllale.youtrack.api.IssueTimeTracking;
import org.llorllale.youtrack.api.Project;
import org.llorllale.youtrack.api.UpdateIssue;
import org.llorllale.youtrack.api.UsersOfIssue;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Mock implementation of {@link Issue} suitable for unit tests.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
@SuppressWarnings(
  {"checkstyle:MethodCount", "checkstyle:MultipleStringLiterals", "checkstyle:MethodName"}
)
public final class MockIssue implements Issue {
  private final Project project;
  private final String id;
  private final Instant creationDate;
  private final String summary;
  private final String description;

  /**
   * Primary ctor.
   *
   * @param project the project
   * @param id the issue's id
   * @param creationDate issue's creation date
   * @param summary issue's summary
   * @param description issue's description
   * @since 0.4.0
   */
  @SuppressWarnings("checkstyle:ParameterNumber")
  public MockIssue(
    Project project,
    String id,
    Instant creationDate,
    String summary,
    String description
  ) {
    this.project = project;
    this.id = id;
    this.creationDate = creationDate;
    this.summary = summary;
    this.description = description;
  }

  /**
   * Ctor.
   * @param project the project
   * @since 0.4.0
   */
  public MockIssue(Project project) {
    this(
      project,
      "",
      Instant.now(),
      "",
      ""
    );
  }

  /**
   * Ctor.
   * @param project the project
   * @param id this issue's id
   */
  public MockIssue(Project project, String id) {
    this(
      project, id, Instant.now(), "", ""
    );
  }

  /**
   * Sets this issue's id.
   * @param issueId id for this issue
   * @return this issue
   */
  public MockIssue withId(String issueId) {
    return new MockIssue(
      this.project,
      issueId,
      this.creationDate,
      this.summary,
      this.description
    );
  }

  /**
   * Sets this issue's creation date.
   * @param date creation date
   * @return this issue
   */
  public MockIssue withCreationDate(Instant date) {
    return new MockIssue(
      this.project,
      this.id,
      date,
      this.summary,
      this.description
    );
  }

  /**
   * Sets this issue's summary.
   * @param text summary text
   * @return this issue
   */
  public MockIssue withSummary(String text) {
    return new MockIssue(
      this.project,
      this.id,
      this.creationDate,
      text,
      this.description
    );
  }

  /**
   * Sets this issue's description.
   * @param text descriptive text
   * @return this issue
   */
  public MockIssue withDescription(String text) {
    return new MockIssue(
      this.project,
      this.id,
      this.creationDate,
      this.summary,
      text
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
    return Optional.ofNullable(this.description);
  }

  @Override
  public Comments comments() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public IssueTimeTracking timetracking() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public UsersOfIssue users() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Issue refresh() throws IOException, UnauthorizedException {
    return this;
  }

  @Override
  public List<AssignedField> fields() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public UpdateIssue update() {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
