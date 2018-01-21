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
import org.llorllale.youtrack.api.Project;
import org.llorllale.youtrack.api.UsersOfIssue;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.IssueTimeTracking;
import org.llorllale.youtrack.api.UpdateIssue;

/**
 * Mock implementation of {@link Issue} suitable for unit tests.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public class MockIssue implements Issue {
  private final Project project;
  private final String id;
  private final Instant creationDate;
  private final String summary;
  private final String description;

  /**
   * Primary ctor.
   * 
   * @param project
   * @param id
   * @param creationDate
   * @param summary
   * @param description 
   * @since 0.4.0
   */
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
   * 
   * @param project 
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

  public MockIssue withId(String id) {
    return new MockIssue(
        this.project, 
        id, 
        this.creationDate, 
        this.summary, 
        this.description
    );
  }

  public MockIssue withCreationDate(Instant creationDate) {
    return new MockIssue(
        this.project, 
        this.id,
        creationDate, 
        this.summary, 
        this.description
    );
  }

  public MockIssue withSummary(String summary) {
    return new MockIssue(
        this.project, 
        this.id,
        this.creationDate, 
        summary, 
        this.description
    );
  }

  public MockIssue withDescription(String description) {
    return new MockIssue(
        this.project, 
        this.id,
        this.creationDate, 
        this.summary, 
        description
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
    return project;
  }

  @Override
  public String id() {
    return id;
  }

  @Override
  public Instant creationDate() {
    return creationDate;
  }

  @Override
  public String summary() {
    return summary;
  }

  @Override
  public Optional<String> description() {
    return Optional.of(description);
  }

  @Override
  public Comments comments() {
    throw new UnsupportedOperationException("Not supported yet."); //TODO implement
  }

  @Override
  public IssueTimeTracking timetracking() {
    throw new UnsupportedOperationException("Not supported yet."); //TODO implement
  }

  @Override
  public UsersOfIssue users() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Issue refresh() throws IOException, UnauthorizedException {
    return this;
  }

  @Override
  public List<AssignedField> fields() {
    throw new UnsupportedOperationException("Not supported yet."); //TODO implement
  }

  @Override
  public UpdateIssue update() {
    throw new UnsupportedOperationException("Not supported yet."); //TODO implement
  }
}
