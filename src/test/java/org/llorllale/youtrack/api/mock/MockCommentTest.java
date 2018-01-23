/*
 * Copyright 2018 George Aristy.
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

package org.llorllale.youtrack.api.mock;

import java.time.Instant;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.llorllale.youtrack.api.Comment;
import org.llorllale.youtrack.api.Issue;

/**
 * Unit tests for {@link MockComment}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public class MockCommentTest {
  /**
   * Returns the id.
   * 
   * @since 1.0.0
   */
  @Test
  public void returnsTheId() {
    final String id = "abc123";
    assertThat(
        new MockComment(id, null, null, null).id(),
        is(id)
    );
  }

  /**
   * Returns the creationDate.
   * 
   * @since 1.0.0
   */
  @Test
  public void returnsTheCreationDate() {
    final Instant date = Instant.EPOCH;
    assertThat(
        new MockComment(null, date, null, null).creationDate(),
        is(date)
    );
  }

  /**
   * Returns the text.
   * 
   * @since 1.0.0
   */
  @Test
  public void returnsTheText() {
    final String text = "This is a test!";
    assertThat(
        new MockComment(null, null, text, null).text(),
        is(text)
    );
  }

  /**
   * Returns the issue.
   * 
   * @since 1.0.0
   */
  @Test
  public void returnsTheIssue() {
    final Issue issue = new MockIssue(new MockProject());
    assertThat(
        new MockComment(null, null, null, issue).issue(),
        is(issue)
    );
  }

  /**
   * Returns a new comment with an updated text.
   * 
   * @since 1.0.0
   */
  @Test
  public void updateText() throws Exception {
    final Comment comment = new MockComment(
        "abc123", 
        Instant.now(), 
        "oldText!", 
        new MockIssue(new MockProject())
    );
    final String newText = "new updated text!";
    assertThat(
        comment.update(newText).text(),
        is(newText)
    );
  }

  /**
   * Returns the issue with the comment no longer present.
   * 
   * @since 1.0.0
   */
  @Test
  public void testDelete() throws Exception {
    fail("TODO");
  }
}