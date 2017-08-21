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

package org.llorllale.youtrack.api.issues;

import java.io.IOException;
import org.junit.Test;
import org.llorllale.youtrack.api.mock.MockAuthenticatedSession;
import org.llorllale.youtrack.api.mock.MockComment;
import org.llorllale.youtrack.api.mock.MockHttpClient;
import org.llorllale.youtrack.api.mock.MockThrowingHttpClient;
import org.llorllale.youtrack.api.mock.response.MockOkHttpResponse;
import org.llorllale.youtrack.api.mock.response.MockUnauthorizedHttpResponse;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Unit tests for {@link UpdateComment}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.2.0
 */
public class UpdateCommentTest {
  /**
   * Should execute silently if YouTrack returns an OK response.
   * @throws Exception 
   * @since 0.2.0
   */
  @Test
  public void successfulUpdate() throws Exception {
    new UpdateComment(
        new MockComment(), 
        "new text", 
        new MockAuthenticatedSession(), 
        new MockHttpClient(new MockOkHttpResponse())
    ).update();
  }

  /**
   * Should throw {@link UnauthorizedException} if YouTrack returns an "unauthorized" response.
   * @throws Exception 
   * @since 0.2.0
   */
  @Test(expected = UnauthorizedException.class)
  public void unauthorizedException() throws Exception {
    new UpdateComment(
        new MockComment(), 
        "new text", 
        new MockAuthenticatedSession(), 
        new MockHttpClient(new MockUnauthorizedHttpResponse())
    ).update();
  }

  /**
   * Should throw {@link IOException} if YouTrack is unreachable.
   * @throws Exception 
   * @since 0.2.0
   */
  @Test(expected = IOException.class)
  public void ioException() throws Exception {
    new UpdateComment(
        new MockComment(),
        "new text",
        new MockAuthenticatedSession(),
        new MockThrowingHttpClient()
    ).update();
  }
}