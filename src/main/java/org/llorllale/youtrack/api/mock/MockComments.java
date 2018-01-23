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

package org.llorllale.youtrack.api.mock;

import java.io.IOException;
import java.time.Instant;
import java.util.Collection;
import java.util.stream.Stream;
import org.llorllale.youtrack.api.Comment;
import org.llorllale.youtrack.api.Comments;
import org.llorllale.youtrack.api.Issue;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Mock implementation of {@link Comments} suitable for tests.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public final class MockComments implements Comments {
  private final Collection<Comment> comments;
  private final Issue issue;

  /**
   * Primary ctor.
   * 
   * @param comments the collection of comments for the issue
   * @param issue the associated issue
   * @since 1.0.0
   */
  public MockComments(Collection<Comment> comments, Issue issue) {
    this.comments = comments;
    this.issue = issue;
  }

  @Override
  public Stream<Comment> stream() throws IOException, UnauthorizedException {
    return this.comments.stream();
  }

  @Override
  public Comments post(String text) throws IOException, UnauthorizedException {
    this.comments.add(
        new MockComment(
            String.valueOf(System.currentTimeMillis()), 
            Instant.now(), 
            text, 
            this.issue()
        )
    );
    return this;
  }

  @Override
  public Issue issue() {
    return this.issue;
  }
}
