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
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.llorllale.youtrack.api.Field;
import org.llorllale.youtrack.api.FieldValue;
import org.llorllale.youtrack.api.Issue;
import org.llorllale.youtrack.api.Issues;
import org.llorllale.youtrack.api.Project;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Mock implementation of {@link Issues} suitable for tests.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public final class MockIssues implements Issues {
  private final Collection<Issue> issues;
  private final Project project;

  /**
   * Primary ctor.
   * 
   * @param issues the associated project's issues
   * @param project the associated project
   * @since 1.0.0
   */
  public MockIssues(Collection<Issue> issues, Project project) {
    this.issues = issues;
    this.project = project;
  }

  @Override
  public Project project() {
    return this.project;
  }

  @Override
  public Stream<Issue> stream() throws IOException, UnauthorizedException {
    return this.issues.stream();
  }

  @Override
  public Optional<Issue> get(String id) throws IOException, UnauthorizedException {
    return this.issues.stream()
        .filter(i -> id.equals(i.id()))
        .findAny();
  }

  @Override
  public Issue create(String summary, String description) 
      throws IOException, UnauthorizedException {
    return this.create(summary, description, Collections.emptyMap());
  }

  @Override
  public Issue create(String summary, String description, Map<Field, FieldValue> fields) 
      throws IOException, UnauthorizedException {
    final Issue issue = new MockIssue(
        this.project(),
        this.project().id() + System.currentTimeMillis(), 
        Instant.now(),
        summary, 
        description, 
        fields, 
        null, 
        null, 
        null, 
        Collections.emptyList(), 
        Collections.emptyList()
    );
    this.issues.add(issue);
    return issue;
  }
}
