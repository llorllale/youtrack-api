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

import java.time.Instant;
import java.util.Objects;
import org.llorllale.youtrack.api.Comments;
import org.llorllale.youtrack.api.Issue;
import org.llorllale.youtrack.api.Project;
import org.llorllale.youtrack.api.TimeTracking;

/**
 * Mock implementation of {@link Issue} suitable for unit tests.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public class MockIssue implements Issue {
  private final Project project;
  private final String id;
  private final Instant creationDate;
  private final String type;
  private final String state;
  private final String priority;
  private final String summary;
  private final String description;

  /**
   * Primary ctor.
   * @param project
   * @param id
   * @param creationDate
   * @param type
   * @param state
   * @param priority
   * @param summary
   * @param description 
   * @since 0.4.0
   */
  public MockIssue(
      Project project, 
      String id, 
      Instant creationDate, 
      String type, 
      String state, 
      String priority, 
      String summary, 
      String description
  ) {
    this.project = project;
    this.id = id;
    this.creationDate = creationDate;
    this.type = type;
    this.state = state;
    this.priority = priority;
    this.summary = summary;
    this.description = description;
  }

  /**
   * 
   * @param project 
   * @since 0.4.0
   */
  public MockIssue(Project project) {
    this(project, "", Instant.now(), "", "", "", "", "");
  }

  public MockIssue withId(String id) {
    return new MockIssue(
        this.project, 
        id, 
        this.creationDate, 
        this.type, 
        this.state, 
        this.priority, 
        this.summary, 
        this.description
    );
  }

  public MockIssue withCreationDate(Instant creationDate) {
    return new MockIssue(
        this.project, 
        this.id,
        creationDate, 
        this.type, 
        this.state, 
        this.priority, 
        this.summary, 
        this.description
    );
  }

  public MockIssue withType(String type) {
    return new MockIssue(
        this.project, 
        this.id,
        this.creationDate, 
        type, 
        this.state, 
        this.priority, 
        this.summary, 
        this.description
    );
  }

  public MockIssue withState(String state) {
    return new MockIssue(
        this.project, 
        this.id,
        this.creationDate, 
        this.type, 
        state, 
        this.priority, 
        this.summary, 
        this.description
    );
  }

  public MockIssue withPriority(String priority) {
    return new MockIssue(
        this.project, 
        this.id,
        this.creationDate, 
        this.type, 
        this.state, 
        priority, 
        this.summary, 
        this.description
    );
  }

  public MockIssue withSummary(String summary) {
    return new MockIssue(
        this.project, 
        this.id,
        this.creationDate, 
        this.type, 
        this.state, 
        this.priority, 
        summary, 
        this.description
    );
  }

  public MockIssue withDescription(String description) {
    return new MockIssue(
        this.project, 
        this.id,
        this.creationDate, 
        this.type, 
        this.state, 
        this.priority, 
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
    final MockIssue other = (MockIssue) obj;
    if (!Objects.equals(this.id, other.id)) {
      return false;
    }
    if (!Objects.equals(this.project, other.project)) {
      return false;
    }
    return true;
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
  public String type() {
    return type;
  }

  @Override
  public String state() {
    return state;
  }

  @Override
  public String priority() {
    return priority;
  }

  @Override
  public String summary() {
    return summary;
  }

  @Override
  public String description() {
    return description;
  }

  @Override
  public Comments comments() {
    throw new UnsupportedOperationException("Not supported yet."); //TODO implement
  }

  @Override
  public TimeTracking timetracking() {
    throw new UnsupportedOperationException("Not supported yet."); //TODO implement
  }

}
