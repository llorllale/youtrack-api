/*
 * Copyright 2017 George Aristy.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.llorllale.youtrack.api.mock;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;
import org.llorllale.youtrack.api.AssignedField;
import org.llorllale.youtrack.api.Field;
import org.llorllale.youtrack.api.FieldValue;
import org.llorllale.youtrack.api.Issue;
import org.llorllale.youtrack.api.UpdateIssue;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Mock implementation of {@link UpdateIssue} suitable for tests.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public final class MockUpdateIssue implements UpdateIssue {
  private final Issue issue;

  /**
   * Primary ctor.
   * 
   * @param issue the associated issue
   * @since 1.0.0
   */
  public MockUpdateIssue(Issue issue) {
    this.issue = issue;
  }

  @Override
  public Issue summary(String summary) throws IOException, UnauthorizedException {
    return new MockIssue(
        this.issue.project(), 
        this.issue.id(), 
        this.issue.creationDate(), 
        summary, 
        this.issue.description().orElse(null), 
        this.issue.fields().stream().collect(Collectors.toMap(
            f -> f, 
            f -> f.value()
        )), 
        this.issue.users().creator(), 
        this.issue.users().assignee().orElse(null), 
        this.issue.users().updater().orElse(null), 
        this.issue.comments().stream().collect(Collectors.toList()),
        Collections.emptyList()
    );
  }

  @Override
  public Issue description(String description) throws IOException, UnauthorizedException {
    return new MockIssue(
        this.issue.project(), 
        this.issue.id(), 
        this.issue.creationDate(), 
        this.issue.summary(), 
        description, 
        this.issue.fields().stream().collect(Collectors.toMap(
            f -> f, 
            f -> f.value()
        )),
        this.issue.users().creator(), 
        this.issue.users().assignee().orElse(null), 
        this.issue.users().updater().orElse(null), 
        this.issue.comments().stream().collect(Collectors.toList()),
        Collections.emptyList()
    );
  }

  @Override
  public Issue summaryAndDesc(String summary, String description) 
      throws IOException, UnauthorizedException {
    return new MockIssue(
        this.issue.project(), 
        this.issue.id(), 
        this.issue.creationDate(), 
        summary, 
        description, 
        this.issue.fields().stream().collect(Collectors.toMap(
            f -> f, 
            f -> f.value()
        )),
        this.issue.users().creator(), 
        this.issue.users().assignee().orElse(null), 
        this.issue.users().updater().orElse(null), 
        this.issue.comments().stream().collect(Collectors.toList()),
        Collections.emptyList()
    );
  }

  @Override
  public Issue field(Field field, FieldValue value) throws IOException, UnauthorizedException {
    return this.fields(
        Collections.singletonMap(field, value)
    );
  }

  @Override
  public Issue fields(Map<Field, FieldValue> fields) throws IOException, UnauthorizedException {
    final Collection<AssignedField> copy = new HashSet<>(this.issue.fields());
    fields.forEach((field, value) -> 
        copy.add(
            new MockAssignedField(
                field.name(), 
                this.issue, 
                value.asString()
            )
        )
    );
    return new MockIssue(
        this.issue.project(),
        this.issue.id(),
        this.issue.creationDate(),
        this.issue.summary(),
        this.issue.description().orElse(null),
        copy.stream().collect(Collectors.toMap(
            f -> f,
            f -> f.value()
        )),
        this.issue.users().creator(),
        this.issue.users().assignee().orElse(null),
        this.issue.users().updater().orElse(null),
        this.issue.comments().stream().collect(Collectors.toList()),
        Collections.emptyList()
    );
  }
}
