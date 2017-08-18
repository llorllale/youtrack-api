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
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import org.llorllale.youtrack.api.mock.MockAuthenticatedSession;
import org.llorllale.youtrack.api.mock.MockHttpClient;
import org.llorllale.youtrack.api.mock.MockThrowingHttpClient;
import org.llorllale.youtrack.api.mock.response.MockOkHttpResponse;
import org.llorllale.youtrack.api.mock.response.MockUnauthorizedHttpResponse;
import org.llorllale.youtrack.api.mock.response.MockXmlHttpEntity;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Unit tests for {@link CommentsForIssue}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.2.0
 */
public class CommentsForIssueTest {
  /**
   * Produces a {@link Comment} for each comment returned by YouTrack.
   * @throws Exception 
   * @since 0.2.0
   */
  @Test
  public void testQuery() throws Exception {
    assertThat(
        new CommentsForIssue(
            "123", 
            new MockAuthenticatedSession(), 
            new MockHttpClient(
                new MockOkHttpResponse(
                    new MockXmlHttpEntity(XML)
                )
            )
        ).query().size(),
        is(2)
    );
  }

  /**
   * Should throw {@link UnauthorizedException} if YouTrack responds with code 401.
   * @throws Exception 
   * @since 0.2.0
   */
  @Test(expected = UnauthorizedException.class)
  public void unauthorizedException() throws Exception {
    new CommentsForIssue(
        "123",
        new MockAuthenticatedSession(),
        new MockHttpClient(new MockUnauthorizedHttpResponse())
    ).query();
  }

  /**
   * Should throw {@link IOException} if YouTrack is unreachable.
   * @throws Exception 
   * @since 0.2.0
   */
  @Test(expected = IOException.class)
  public void ioException() throws Exception {
    new CommentsForIssue(
        "123", 
        new MockAuthenticatedSession(), 
        new MockThrowingHttpClient()
    ).query();
  }

  private static final String XML = 
"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
"<comments>\n" +
"    <comment id=\"42-306\" author=\"root\" issueId=\"HBR-63\" deleted=\"false\" text=\"comment 1!\" shownForIssueAuthor=\"false\"\n" +
"             created=\"1267030230127\">\n" +
"        <replies/>\n" +
"    </comment>\n" +
"    <comment id=\"42-307\" author=\"root\" issueId=\"HBR-63\" deleted=\"false\" text=\"comment 2?\" shownForIssueAuthor=\"false\"\n" +
"             created=\"1267030238721\" updated=\"1267030230127\">\n" +
"        <replies/>\n" +
"    </comment>\n" +
"</comments>";
}