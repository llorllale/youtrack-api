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

import java.util.Objects;
import java.util.Optional;

import org.llorllale.youtrack.api.session.Session;

/**
 * Adapter {@link org.llorllale.youtrack.api.issues.jaxb.Project} -> {@link Project}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.2.0
 */
class XmlProject implements Project {
  private final YouTrack youtrack;
  private final Session session;
  private final org.llorllale.youtrack.api.jaxb.Project jaxbProject;

  /**
   * Ctor.
   * @param youtrack the parent {@link YouTrack}
   * @param session the user's {@link Session}
   * @param jaxbProject the JAXB instance to be adapted into {@link Project}
   * @since 0.2.0
   */
  XmlProject(
      YouTrack youtrack, 
      Session session, 
      org.llorllale.youtrack.api.jaxb.Project jaxbProject
  ) {
    this.youtrack = youtrack;
    this.session = session;
    this.jaxbProject = jaxbProject;
  }

  @Override
  public String id() {
    return Optional.ofNullable(this.jaxbProject.getShortName())
        .orElse(this.jaxbProject.getId());
  }

  @Override
  public String name() {
    return this.jaxbProject.getName();
  }

  @Override
  public Optional<String> description() {
    return Optional.ofNullable(this.jaxbProject.getDescription());
  }

  @Override
  public Issues issues() {
    return new DefaultIssues(this, this.session);
  }

  @Override
  public YouTrack youtrack() {
    return this.youtrack;
  }

  @Override
  public Fields fields() {
    return new DefaultFields(this.session, this);
  }

  @Override
  public ProjectTimeTracking timetracking() {
    return new DefaultProjectTimeTracking(this, this.session);
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
    return Objects.equals(this.id(), other.id());
  }

  @Override
  public UsersOfProject users() {
    return new DefaultUsersOfProject(this, this.session, this.jaxbProject);
  }
}
