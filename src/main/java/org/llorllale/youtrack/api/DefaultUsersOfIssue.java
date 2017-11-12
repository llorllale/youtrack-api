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

package org.llorllale.youtrack.api;

import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.ApplyIf;

import java.io.IOException;
import java.util.Optional;

/**
 * Default implementation of {@link UsersOfIssue}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.5.0
 */
class DefaultUsersOfIssue implements UsersOfIssue {
  private final Issue issue;
  private final org.llorllale.youtrack.api.jaxb.Issue jaxbIssue;
  private final Field field;

  /**
   * Primary ctor.
   * 
   * @param issue the parent {@link Issue}
   * @param jaxbIssue the jaxb instance of an Issue
   * @since 0.5.0
   */
  DefaultUsersOfIssue(
      Issue issue,
      org.llorllale.youtrack.api.jaxb.Issue jaxbIssue
  ) {
    this.issue = issue;
    this.jaxbIssue = jaxbIssue;
    this.field = new BasicField("Assignee", issue.project());
  }

  @Override
  public User creator() throws IOException, UnauthorizedException {
    return this.issue().project().users().user(
        this.jaxbIssue.getField()
           .stream()
           .filter(f -> "reporterName".equals(f.getName()))
           .map(f -> f.getValue().getValue())
           .findFirst()
           .get()  //expected
    );
  }

  @Override
  public Optional<User> updater() throws IOException, UnauthorizedException {
    final Optional<String> updaterLoginName = this.jaxbIssue.getField()
        .stream()
        .filter(f -> "updaterName".equals(f.getName()))
        .map(f -> f.getValue().getValue())
        .findFirst();

    return new ApplyIf<Optional<String>, User, IOException>(
        login -> login.isPresent(),
        login -> this.issue().project().users().user(login.get())
    ).apply(updaterLoginName);
  }

  @Override
  public Optional<User> assignee() throws IOException, UnauthorizedException {
    final Optional<String> assigneeLoginName = this.jaxbIssue.getField()
        .stream()
        .filter(f -> this.field.name().equals(f.getName()))
        .map(f -> f.getValue().getValue())
        .findFirst();

    return new ApplyIf<Optional<String>, User, IOException>(
        login -> login.isPresent(),
        login -> this.issue().project().users().user(login.get())
    ).apply(assigneeLoginName);
  }

  @Override
  public UsersOfIssue assignTo(User user) throws IOException, UnauthorizedException {
    return this.issue().update().field(
        this.field,
        new BasicFieldValue(user.loginName(), this.field)
    ).users();
  }

  @Override
  public Issue issue() {
    return this.issue;
  }
}
