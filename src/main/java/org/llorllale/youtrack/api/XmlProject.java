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

import org.llorllale.youtrack.api.session.Session;

import java.util.Optional;

/**
 * Adapter {@link org.llorllale.youtrack.api.issues.jaxb.Project} -> {@link Project}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.2.0
 */
class XmlProject implements Project {
  private final Session session;
  private final org.llorllale.youtrack.api.jaxb.Project jaxbProject;

  /**
   * Ctor.
   * @param jaxbProject the JAXB instance to be adapted into {@link Project}
   * @since 0.2.0
   */
  XmlProject(Session session, org.llorllale.youtrack.api.jaxb.Project jaxbProject) {
    this.session = session;
    this.jaxbProject = jaxbProject;
  }

  @Override
  public String id() {
    return Optional.ofNullable(jaxbProject.getShortName())
        .orElse(jaxbProject.getId());
  }

  @Override
  public String name() {
    return jaxbProject.getName();
  }

  @Override
  public Optional<String> description() {
    return Optional.ofNullable(jaxbProject.getDescription());
  }

  @Override
  public Issues issues() {
    return new DefaultIssues(this, session);
  }
}
