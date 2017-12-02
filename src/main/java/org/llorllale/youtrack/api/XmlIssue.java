/* 
 * Copyright 2017 George Aristy (george.aristy@gmail.com).
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

package org.llorllale.youtrack.api;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * JAXB implementation of {@link Issue}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
class XmlIssue implements Issue {
  private final Project project;
  private final Session session;
  private final org.llorllale.youtrack.api.jaxb.Issue jaxbIssue;

  /**
   * Primary ctor.
   * 
   * @param project this {@link Issue issue's} {@link Project}
   * @param session the user's {@link Session}
   * @param jaxbIssue the JAXB issue to be adapted
   * @since 0.1.0
   */
  XmlIssue(
      Project project, 
      Session session, 
      org.llorllale.youtrack.api.jaxb.Issue jaxbIssue
  ) {
    this.project = project;
    this.session = session;
    this.jaxbIssue = jaxbIssue;
  }

  /**
   * For testing purposes.
   * 
   * @param prototype the prototype
   * @since 0.8.0
   */
  XmlIssue(XmlIssue prototype) {
    this(prototype.project(), prototype.session, prototype.jaxbIssue);
  }

  @Override
  public String id() {
    return this.jaxbIssue.getId();
  }

  @Override
  public Instant creationDate() {
    return Instant.ofEpochMilli(this.jaxbIssue.getField()
            .stream()
            .filter(f -> "created".equals(f.getName()))
            .map(f -> f.getValue().getValue())
            .map(Long::valueOf)
            .findFirst()
            .get()
    );
  }

  @Override
  public String summary() {
    return this.jaxbIssue.getField()
            .stream()
            .filter(f -> "summary".equals(f.getName()))
            .map(f -> f.getValue().getValue())
            .findFirst()
            .get();
  }

  @Override
  public Optional<String> description() {
    return this.jaxbIssue.getField()
            .stream()
            .filter(f -> "description".equals(f.getName()))
            .map(f -> f.getValue().getValue())
            .findAny();
  }

  @Override
  public Project project() {
    return this.project;
  }

  @Override
  public Comments comments() {
    return new DefaultComments(this.session, this);
  }

  @Override
  public IssueTimeTracking timetracking() {
    return new DefaultIssueTimeTracking(this.session, this);
  }

  @Override
  public UsersOfIssue users() {
    return new DefaultUsersOfIssue(this, this.jaxbIssue);
  }

  @Override
  public Issue refresh() throws IOException, UnauthorizedException {
    return this.project().issues()
        .get(this.id())
        .get();
  }

  @Override
  public UpdateIssue update() {
    return new DefaultUpdateIssue(this, this.session);
  }

  @Override
  public List<AssignedField> fields() {
    return this.jaxbIssue.getField().stream()
        .filter(f -> Objects.nonNull(f.getValueId()))
        .map(f -> 
            new DefaultAssignedField(
                new BasicField(f.getName(), this.project),
                this, 
                f
            )
        ).collect(Collectors.toList());
  }
}
