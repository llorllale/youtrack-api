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

import com.google.common.collect.ImmutableMap;

import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;

import java.io.IOException;

/**
 * JAXB impl of {@link AssignedPriority}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.6.0
 */
class XmlAssignedPriority implements AssignedPriority {
  private final Issue<org.llorllale.youtrack.api.jaxb.Issue> issue;
  private final Session session;

  /**
   * Primary ctor.
   * @param issue the parent {@link Issue}
   * @param session the user's {@link Session}
   * @since 0.6.0
   */
  XmlAssignedPriority(
      Issue<org.llorllale.youtrack.api.jaxb.Issue> issue,
      Session session
  ) {
    this.issue = issue;
    this.session = session;
  }

  @Override
  public Issue issue() {
    return issue;
  }

  @Override
  public AssignedPriority changeTo(Priority other) throws IOException, UnauthorizedException {
    return this.issue()
        .update(ImmutableMap.of("Priority", other.asString()))
        .priority();
  }

  @Override
  public String asString() {
    return issue.asDto().getField()
        .stream()
        .filter(f -> "Priority".equals(f.getName()))
        .map(f -> f.getValue().getValue())
        .findAny()
        .get();
  }
}
