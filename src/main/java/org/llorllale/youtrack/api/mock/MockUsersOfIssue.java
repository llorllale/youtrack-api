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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.llorllale.youtrack.api.Issue;
import org.llorllale.youtrack.api.User;
import org.llorllale.youtrack.api.UsersOfIssue;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Mock implementation of {@link UsersOfIssue} suitable for tests.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public final class MockUsersOfIssue implements UsersOfIssue {
  private final Issue issue;
  private final User creator;
  private final User assignee;
  private final User updater;

  /**
   * Primary ctor.
   * 
   * @param issue the associated issue
   * @param creator the issue's creator
   * @param assignee the issue's assignee (may be {@code null})
   * @param updater the issue's updater (may be {@code null})
   * @since 1.0.0
   */
  public MockUsersOfIssue(Issue issue, User creator, User assignee, User updater) {
    this.issue = issue;
    this.creator = creator;
    this.assignee = assignee;
    this.updater = updater;
  }

  /**
   * Ctor.
   * 
   * @param issue the associated issue
   * @param creator the issue's creator
   * @since 1.0.0
   */
  public MockUsersOfIssue(Issue issue, User creator) {
    this(issue, creator, null, null);
  }

  @Override
  public User creator() throws IOException, UnauthorizedException {
    return Objects.requireNonNull(this.creator, "A creator was not set!");
  }

  @Override
  public Optional<User> updater() throws IOException, UnauthorizedException {
    return Optional.ofNullable(this.updater);
  }

  @Override
  public Optional<User> assignee() throws IOException, UnauthorizedException {
    return Optional.ofNullable(this.assignee);
  }

  @Override
  public UsersOfIssue assignTo(User user) throws IOException, UnauthorizedException {
    return new MockUsersOfIssue(
        new MockIssue(
            this.issue().project(), 
            this.issue().id(), 
            this.issue().creationDate(), 
            this.issue().summary(), 
            this.issue().description().orElse(null), 
            this.issue().fields().stream().collect(Collectors.toMap(
                f -> f, 
                f -> f.value()
            )), 
            this.creator(), 
            user, 
            this.updater().orElse(null), 
            this.issue.comments().stream().collect(Collectors.toList()),
            this.issue().timetracking().stream().collect(Collectors.toList())
        ), 
        this.creator(), 
        user, 
        this.updater().orElse(null)
    );
  }

  @Override
  public Issue issue() {
    return this.issue;
  }
}
