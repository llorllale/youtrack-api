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

package org.llorllale.youtrack.api.projects;

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
 * Unit tests for {@link ProjectWithId}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.2.0
 */
public class ProjectWithIdTest {
  /**
   * Should produce a {@link Project} if YouTrack returns one.
   * @throws Exception 
   * @since 0.2.0
   */
  @Test
  public void returnsResult() throws Exception {
    assertThat(
        new ProjectWithId(
            "123", 
            new MockAuthenticatedSession(), 
            new MockHttpClient(
                new MockOkHttpResponse(
                    new MockXmlHttpEntity(RESPONSE)
                )
            )
        ).query().isPresent(),
        is(true)
    );
  }

  /**
   * Should throw {@link UnauthorizedException} if YouTrack returns an "unauthorized" response.
   * @throws Exception 
   * @since 0.2.0
   */
  @Test(expected = UnauthorizedException.class)
  public void unauthorizedException() throws Exception {
    new ProjectWithId(
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
    new ProjectWithId(
        "123",
        new MockAuthenticatedSession(),
        new MockThrowingHttpClient()
    ).query();
  }

  private static final String RESPONSE = 
"<project name=\"test project\" id=\"TP\" lead=\"root\"\n" +
"   assigneesUrl=\"http://localhost:8080/charisma/rest/admin/project/TP/assignee/\"\n" +
"   subsystemsUrl=\"http://localhost:8080/charisma/rest/admin/project/TP/subsystem/\"\n" +
"   buildsUrl=\"http://localhost:8080/charisma/rest/admin/project/TP/build/\"\n" +
"   versionsUrl=\"http://localhost:8080/charisma/rest/admin/project/TP/version/\"\n" +
"   startingNumber=\"1\"\n" +
"/>";
}