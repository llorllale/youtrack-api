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

package org.llorllale.youtrack.api.issues;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import org.apache.http.message.BasicHeader;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.llorllale.youtrack.api.mock.MockAuthenticatedSession;
import org.llorllale.youtrack.api.mock.MockHttpClient;
import org.llorllale.youtrack.api.mock.MockThrowingHttpClient;
import org.llorllale.youtrack.api.mock.response.MockCreatedHttpResponse;
import org.llorllale.youtrack.api.mock.response.MockUnauthorizedHttpResponse;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Unit tests for {@link CreateWorkItem}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.3.0
 */
public class CreateWorkItemTest {
  /**
   * Tests whether the correct work item ID is returned upon successful "Created" response.
   * @throws Exception 
   * @since 0.3.0
   */
  @Test
  public void returnsId() throws Exception {
    assertThat(
        new CreateWorkItem(
            new MockAuthenticatedSession(), 
            new MockHttpClient(
                new MockCreatedHttpResponse(
                    new BasicHeader("Location", "http://some.url/rest/issue/workitem/TEST-WI-1")
                )
            )
        ).onIssue("")
            .workedOn(LocalDate.now())
            .forDuration(Duration.ZERO)
            .withDescription("")
            .withType("")
            .create(),
        is("TEST-WI-1")
    );
  }

  /**
   * Should throw {@link UnauthorizedException} if YouTrack response with "unauthorized".
   * @throws Exception 
   * @since 0.3.0
   */
  @Test(expected = UnauthorizedException.class)
  public void unauthorizedException() throws Exception {
    new CreateWorkItem(
        new MockAuthenticatedSession(), 
        new MockHttpClient(new MockUnauthorizedHttpResponse())
    ).onIssue("")
        .workedOn(LocalDate.now())
        .forDuration(Duration.ZERO)
        .withDescription("")
        .withType("")
        .create();
  }

  /**
   * Should throw {@link IOException} if YouTrack is unreachable.
   * @throws Exception 
   * @since 0.3.0
   */
  @Test(expected = IOException.class)
  public void ioException() throws Exception {
    new CreateWorkItem(
        new MockAuthenticatedSession(), 
        new MockThrowingHttpClient()
    ).onIssue("")
        .workedOn(LocalDate.now())
        .forDuration(Duration.ZERO)
        .withDescription("")
        .withType("")
        .create();
  }
}
