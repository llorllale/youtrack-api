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
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.llorllale.youtrack.api.session.Login;

import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * JAXB implementation of {@link Issue}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
//equals and hashCode tip the method count to just over the max allowed (12) to the actual 14
@SuppressWarnings("checkstyle:MethodCount")
final class XmlIssue implements Issue {
  private final Project project;
  private final Login login;
  private final Xml xml;
  private final HttpClient client;

  /**
   * Primary ctor.
   * 
   * @param project this {@link Issue issue's} {@link Project}
   * @param login the user's {@link Login}
   * @param xml the xml object received from YouTrack
   * @param client the Http client to use
   * @since 0.1.0
   */
  XmlIssue(
      Project project, 
      Login login, 
      Xml xml,
      HttpClient client
  ) {
    this.project = project;
    this.login = login;
    this.xml = xml;
    this.client = client;
  }

  /**
   * Primary ctor.
   * 
   * @param project this {@link Issue issue's} {@link Project}
   * @param login the user's {@link Login}
   * @param xml the xml object received from YouTrack
   * @since 0.1.0
   */
  XmlIssue(
      Project project, 
      Login login, 
      Xml xml
  ) {
    this(project, login, xml, HttpClients.createDefault());
  }

  @Override
  public String id() {
    return this.xml.textOf("@id").get();
  }

  @Override
  public Instant creationDate() {
    return Instant.ofEpochMilli(
      Long.parseLong(
        this.xml.textOf("//field[@name = 'created']/value").get()
      )
    );
  }

  @Override
  public String summary() {
    return this.xml.textOf("//field[@name = 'summary']/value").get();
  }

  @Override
  public Optional<String> description() {
    return this.xml.textOf("//field[@name = 'description']/value");
  }

  @Override
  public Project project() {
    return this.project;
  }

  @Override
  public Comments comments() {
    return new DefaultComments(this.login, this);
  }

  @Override
  public IssueTimeTracking timetracking() {
    return new DefaultIssueTimeTracking(this.login, this);
  }

  @Override
  public UsersOfIssue users() {
    return new XmlUsersOfIssue(this, this.xml);
  }

  @Override
  public Issue refresh() throws IOException, UnauthorizedException {
    return this.project().issues()
      .get(this.id())
      .get();
  }

  @Override
  public UpdateIssue update() {
    return new DefaultUpdateIssue(this, this.login);
  }

  @Override
  public Collection<AssignedField> fields() {
    return new MappedCollection<>(
      x -> new XmlAssignedField(
          new BasicField(x.textOf("@name").get(), this.project()),
          this,
          x
      ),
      this.xml.children("//field[count(valueId) > 0]")
    );
  }

  @Override
  public int hashCode() {
    return this.id().hashCode();
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
  public Attachments attachments() {
    return new DefaultAttachments(this, this.login, this.client);
  }
}
