/*
 * Copyright 2017 George Aristy.
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

import com.google.common.collect.ImmutableMap;

import org.llorllale.youtrack.api.session.UnauthorizedException;

import java.io.IOException;

/**
 * JAXB implementation of {@link AssignedState}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.7.0
 */
class XmlAssignedState implements AssignedState {
  private final Issue<org.llorllale.youtrack.api.jaxb.Issue> issue;

  /**
   * Primary ctor.
   * @param issue the parent {@link Issue}
   * @since 0.7.0
   */
  XmlAssignedState(Issue<org.llorllale.youtrack.api.jaxb.Issue> issue) {
    this.issue = issue;
  }

  @Override
  public Issue issue() {
    return issue;
  }

  @Override
  public boolean resolved() throws IOException, UnauthorizedException {
    return this.issue()
        .project()
        .states()
        .resolving()
        .anyMatch(s -> s.asString().equals(this.asString()));
  }

  @Override
  public AssignedState changeTo(State other) throws IOException, UnauthorizedException {
    return this.issue()
        .update(ImmutableMap.of("State", other.asString()))
        .state();
  }

  @Override
  public String asString() {
    return issue.asDto().getField()
        .stream()
        .filter(f -> "State".equals(f.getName()))
        .map(f -> f.getValue().getValue())
        .findAny()
        .get();
  }
}
