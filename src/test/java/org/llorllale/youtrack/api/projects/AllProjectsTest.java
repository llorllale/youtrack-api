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
import java.net.URL;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import org.llorllale.youtrack.api.mock.MockAuthenticatedSession;
import org.llorllale.youtrack.api.mock.MockHttpClient;
import org.llorllale.youtrack.api.mock.MockThrowingHttpClient;
import org.llorllale.youtrack.api.mock.response.MockOkHttpResponse;
import org.llorllale.youtrack.api.mock.response.MockUnauthorizedHttpResponse;
import org.llorllale.youtrack.api.mock.response.MockXmlHttpEntity;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Unit tests for {@link AllProjects}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.2.0
 */
public class AllProjectsTest {
  /**
   * Should produce all projects returned by YouTrack's API with no errors.
   * @throws Exception 
   * @since 0.2.0
   */
  @Test
  public void responseWithProjects() throws Exception {
    assertThat(
        new AllProjects(
            new MockAuthenticatedSession(
                new URL("http://some.url")
            ),
            new MockHttpClient(
                new MockOkHttpResponse(
                    new MockXmlHttpEntity(PROJECTS)
                )
            )
        ).query().size(),
        is(2)
    );
  }

  /**
   * Should throw {@link UnauthorizedException} if the user's {@link Session} is not allowed
   * to access the resource.
   * @throws Exception 
   * @since 0.2.0
   */
  @Test(expected = UnauthorizedException.class)
  public void unauthorizedException() throws Exception {
    new AllProjects(
        new MockAuthenticatedSession(
            new URL("http://some.url")
        ), 
        new MockHttpClient(
            new MockUnauthorizedHttpResponse()
        )
    ).query();
  }

  /**
   * Should throw {@link IOException} is the YouTrack service is unreachable.
   * @throws Exception 
   * @since 0.2.0
   */
  @Test(expected = IOException.class)
  public void ioexception() throws Exception {
    new AllProjects(
        new MockAuthenticatedSession(
            new URL("http://some.url")
        ), 
        new MockThrowingHttpClient()
    ).query();
  }

  private static final String PROJECTS =
"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
"<projects>\n" +
"  <project versions=\"[2.0, 2.0.1, 2.0.2, 2.0.3, 2.0.4, 2.0.5, 2.0.6, 2.0.7, 2.0.8]\" name=\"Hibero\" shortName=\"HBR\" description=\"Makes developing Hibernate applications a pleasure.\" isImporting=\"false\">\n" +
"    <assigneesFullName>\n" +
"      <sub value=\"Adam Jordens\"/>\n" +
"      <sub value=\"Application Exception\"/>\n" +
"    </assigneesFullName>\n" +
"    <assigneesLogin>\n" +
"      <sub value=\"ajordens\"/>\n" +
"      <sub value=\"app_exception\"/>\n" +
"    </assigneesLogin>\n" +
"    <subsystems>\n" +
"      <sub value=\"No subsystem\"/>\n" +
"      <sub value=\"Configuration\"/>\n" +
"      <sub value=\"HQL\"/>\n" +
"      <sub value=\"Settings\"/>\n" +
"      <sub value=\"UI\"/>\n" +
"    </subsystems>\n" +
"  </project>\n" +
"  <project versions=\"[]\" name=\"some project\" shortName=\"SP\" isImporting=\"false\">\n" +
"    <assigneesFullName>\n" +
"      <sub value=\"Adam Jordens\"/>\n" +
"      <sub value=\"Application Exception\"/>\n" +
"    </assigneesFullName>\n" +
"    <assigneesLogin>\n" +
"      <sub value=\"ajordens\"/>\n" +
"      <sub value=\"app_exception\"/>\n" +
"    </assigneesLogin>\n" +
"    <subsystems>\n" +
"      <sub value=\"No subsystem\"/>\n" +
"    </subsystems>\n" +
"  </project>\n" +
"</projects>";
}
