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

import java.util.Optional;
import org.apache.http.client.HttpClient;
import org.llorllale.youtrack.api.session.Login;

/**
 * Adapter {@link org.llorllale.youtrack.api.issues.jaxb.Project} -> {@link Project}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.2.0
 */
final class XmlProject implements Project {
  private final YouTrack youtrack;
  private final Login login;
  private final Xml xml;
  private final HttpClient client;

  /**
   * Ctor.
   * 
   * @param youtrack the parent {@link YouTrack}
   * @param login the user's {@link Login}
   * @param xml the XML object received from YouTrack to be adapted into {@link Project}
   * @param client the {@link HttpClient} to use
   * @since 0.2.0
   */
  XmlProject(
      YouTrack youtrack, 
      Login login, 
      Xml xml,
      HttpClient client
  ) {
    this.youtrack = youtrack;
    this.login = login;
    this.xml = xml;
    this.client = client;
  }

  @Override
  public String id() {
    return this.xml.textOf(
        "(@id | @shortName)[last()]"
    ).get();
  }

  @Override
  public String name() {
    return this.xml.textOf("@name").get();
  }

  @Override
  public Optional<String> description() {
    return this.xml.textOf("@description");
  }

  @Override
  public Issues issues() {
    return new DefaultIssues(this, this.login, this.client);
  }

  @Override
  public YouTrack youtrack() {
    return this.youtrack;
  }

  @Override
  public Fields fields() {
    return new DefaultFields(this.login, this, this.client);
  }

  @Override
  public ProjectTimeTracking timetracking() {
    return new DefaultProjectTimeTracking(this, this.login);
  }

  @Override
  public int hashCode() {
    return this.id().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Project)) {
      return false;
    }

    final Project other = (Project) obj;
    return this.id().equals(other.id());
  }

  @Override
  public UsersOfProject users() {
    return new XmlUsersOfProject(this, this.login, this.xml, this.client);
  }
}
