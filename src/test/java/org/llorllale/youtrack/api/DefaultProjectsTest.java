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

package org.llorllale.youtrack.api;

// @checkstyle AvoidStaticImport (4 lines)
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.apache.http.entity.StringEntity;
import org.junit.Test;
import org.llorllale.youtrack.api.mock.http.MockHttpClient;
import org.llorllale.youtrack.api.mock.http.MockSession;
import org.llorllale.youtrack.api.mock.http.response.MockNotFoundResponse;
import org.llorllale.youtrack.api.mock.http.response.MockOkResponse;

/**
 * Unit tests for {@link DefaultProjects}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 * @checkstyle MultipleStringLiterals (500 lines)
 * @checkstyle MethodName (500 lines)
 */
public final class DefaultProjectsTest {
  private static final String ALL_PROJECTS_RESPONSE
    = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
    + "<projects>\n"
    // @checkstyle LineLength (1 line)
    + "  <project versions=\"[2.0, 2.0.1, 2.0.2, 2.0.3, 2.0.4, 2.0.5, 2.0.6, 2.0.7, 2.0.8]\" name=\"Hibero\" shortName=\"HBR\" description=\"Makes developing Hibernate applications a pleasure.\" isImporting=\"false\">\n"
    + "    <assigneesFullName>\n"
    + "      <sub value=\"Adam Jordens\"/>\n"
    + "      <sub value=\"Application Exception\"/>\n"
    + "    </assigneesFullName>\n"
    + "    <assigneesLogin>\n"
    + "      <sub value=\"ajordens\"/>\n"
    + "      <sub value=\"app_exception\"/>\n"
    + "    </assigneesLogin>\n"
    + "    <subsystems>\n"
    + "      <sub value=\"No subsystem\"/>\n"
    + "      <sub value=\"Configuration\"/>\n"
    + "      <sub value=\"HQL\"/>\n"
    + "      <sub value=\"Settings\"/>\n"
    + "      <sub value=\"UI\"/>\n"
    + "    </subsystems>\n"
    + "  </project>\n"
    // @checkstyle LineLength (1 line)
    + "  <project versions=\"[]\" name=\"some project\" shortName=\"SP\" isImporting=\"false\">\n"
    + "    <assigneesFullName>\n"
    + "      <sub value=\"Adam Jordens\"/>\n"
    + "      <sub value=\"Application Exception\"/>\n"
    + "    </assigneesFullName>\n"
    + "    <assigneesLogin>\n"
    + "      <sub value=\"ajordens\"/>\n"
    + "      <sub value=\"app_exception\"/>\n"
    + "    </assigneesLogin>\n"
    + "    <subsystems>\n"
    + "      <sub value=\"No subsystem\"/>\n"
    + "    </subsystems>\n"
    + "  </project>\n"
    + "</projects>";

  private static final String ONE_PROJECT_RESPONSE
    = "<project name=\"test project\" id=\"TP\" lead=\"root\"\n"
    // @checkstyle LineLength (4 lines)
    + "   assigneesUrl=\"http://localhost:8080/charisma/rest/admin/project/TP/assignee/\"\n"
    + "   subsystemsUrl=\"http://localhost:8080/charisma/rest/admin/project/TP/subsystem/\"\n"
    + "   buildsUrl=\"http://localhost:8080/charisma/rest/admin/project/TP/build/\"\n"
    + "   versionsUrl=\"http://localhost:8080/charisma/rest/admin/project/TP/version/\"\n"
    + "   startingNumber=\"1\"\n"
    + "/>";

  /**
   * Stream returns all projects.
   * @throws Exception unexpected
   */
  @Test
  public void streamWithResponseOk() throws Exception {
    assertThat(
      new DefaultProjects(
        null,
        new MockSession(),
        new MockHttpClient(
          new MockOkResponse(
            new StringEntity(ALL_PROJECTS_RESPONSE)
          )
        )
      ).stream().count(),
      is(2L)
    );
  }

  /**
   * Returns optional with project if it exists.
   * @throws Exception unexpected
   * @since 0.6.0
   */
  @Test
  public void getWithExistingProject() throws Exception {
    assertTrue(
      new DefaultProjects(
        null,
        new MockSession(),
        new MockHttpClient(
          new MockOkResponse(ONE_PROJECT_RESPONSE)
        )
      ).get("ID").isPresent()
    );
  }

  /**
   * Returns empty optional if project does not exist.
   * @throws Exception unexpected
   * @since 0.6.0
   */
  @Test
  public void getWithNonExistingProject() throws Exception {
    assertFalse(
      new DefaultProjects(
        null,
        new MockSession(),
        new MockHttpClient(
          new MockNotFoundResponse(
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
            + "<error>Project not found.</error>"
          )
        )
      ).get("ID").isPresent()
    );
  }
}
