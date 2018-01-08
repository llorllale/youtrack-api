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
import java.time.Instant;
import java.util.Collections;
import java.util.stream.Collectors;
import org.llorllale.youtrack.api.Comment;
import org.llorllale.youtrack.api.Issue;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Mock implementation of {@link Comment} suitable for tests.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public final class MockComment implements Comment {
  private final String id;
  private final Instant creationDate;
  private final String text;
  private final Issue issue;

  /**
   * Primary ctor.
   * 
   * @param id this comment's id
   * @param creationDate this comment's date of creation
   * @param text this comment's text
   * @param issue the associated issue
   * @since 1.0.0
   */
  public MockComment(String id, Instant creationDate, String text, Issue issue) {
    this.id = id;
    this.creationDate = creationDate;
    this.text = text;
    this.issue = issue;
  }

  @Override
  public String id() {
    return this.id;
  }

  @Override
  public Instant creationDate() {
    return this.creationDate;
  }

  @Override
  public String text() {
    return this.text;
  }

  @Override
  public Issue issue() {
    return this.issue;
  }

  @Override
  public Comment update(String txt) throws IOException, UnauthorizedException {
    return new MockComment(this.id, this.creationDate, txt, this.issue);
  }

  @Override
  public Issue delete() throws IOException, UnauthorizedException {
    return new MockIssue(
        this.issue().project(),
        this.issue().id(),
        this.issue().creationDate(),
        this.issue().summary(), 
        this.issue().description().orElse(null),
        this.issue().fields().stream().collect(Collectors.toMap(
            f -> f,
            f -> f.value()
        )),
        this.issue().users().creator(), 
        this.issue().users().assignee().orElse(null), 
        this.issue().users().updater().orElse(null),
        this.issue().comments().stream()
            .filter(c -> !this.id().equals(c.id()))
            .collect(Collectors.toList()),
        Collections.emptyList()
    );
  }
}
