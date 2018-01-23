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

package org.llorllale.youtrack.api;

import java.io.IOException;
import java.util.Optional;

import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Default implementation of {@link UsersOfIssue}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.5.0
 */
final class XmlUsersOfIssue implements UsersOfIssue {
  private final Issue issue;
  private final Xml xml;

  /**
   * Primary ctor.
   * 
   * @param issue the parent {@link Issue}
   * @param xml the XML received from YouTrack for the relevant issue
   * @since 0.5.0
   */
  XmlUsersOfIssue(
      Issue issue,
      Xml xml
  ) {
    this.issue = issue;
    this.xml = xml;
  }

  @Override
  public User creator() throws IOException, UnauthorizedException {
    return this.issue().project().users().user(
        this.xml.textOf("//field[@name = 'reporterName']/value").get()
    );
  }

  @Override
  public Optional<User> updater() throws IOException, UnauthorizedException {
    return this.user(
        this.xml.textOf("//field[@name = 'updaterName']/value")
    );
  }

  @Override
  public Optional<User> assignee() throws IOException, UnauthorizedException {
    return this.user(
        this.xml.textOf("//field[@name = 'Assignee']/value")
    );
  }

  @Override
  public UsersOfIssue assignTo(User user) throws IOException, UnauthorizedException {
    final Field field = new BasicField("Assignee", this.issue().project());
    return this.issue().update().field(
        field,
        new BasicFieldValue(user.loginName(), field)
    ).users();
  }

  @Override
  public Issue issue() {
    return this.issue;
  }

  /**
   * A little common code to fetch a user whose login is optional.
   * 
   * @param login the user's login
   * @return an optional describing the user
   * @throws UnauthorizedException thrown by {@link UsersOfProject#user(java.lang.String)}
   * @throws IOException thrown by {@link UsersOfProject#user(java.lang.String)}
   * @since 1.0.0
   */
  private Optional<User> user(Optional<String> login) throws UnauthorizedException, IOException {
    final Optional<User> user;

    if (login.isPresent()) {
      user = Optional.of(this.issue().project().users().user(login.get()));
    } else {
      user = Optional.empty();
    }

    return user;
  }
}
