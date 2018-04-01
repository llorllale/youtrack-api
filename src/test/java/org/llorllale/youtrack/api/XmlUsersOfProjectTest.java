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

// @checkstyle AvoidStaticImport (3 lines)
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import java.util.stream.Collectors;
import org.junit.Test;
import org.llorllale.youtrack.api.mock.MockProject;
import org.llorllale.youtrack.api.mock.http.MockHttpClient;
import org.llorllale.youtrack.api.mock.http.MockSession;
import org.llorllale.youtrack.api.mock.http.response.MockOkResponse;

/**
 * Unit tests for {@link XmlUsersOfProject}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 * @checkstyle MultipleStringLiterals (500 lines)
 */
public final class XmlUsersOfProjectTest {
  /**
   * {@link XmlUsersOfProject#project()} must return the same project.
   * @since 1.0.0
   */
  @Test
  public void project() {
    final Project project = new MockProject();
    assertThat(
      new XmlUsersOfProject(project, null, null).project(),
      is(project)
    );
  }

  /**
   * {@link XmlUsersOfProject#user(java.lang.String)} must return the same user returned by the
   * server.
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void user() throws Exception {
    assertThat(
      new XmlUsersOfProject(
        new MockProject(),
        new MockSession(),
        null,
        new MockHttpClient(
          new MockOkResponse(
            "<user login=\"root\" \n"
            + "      fullName=\"root\" \n"
            + "      groupsUrl=\"http://localhost:8080/charisma/rest/admin/user/root/group/\" \n"
            + "      rolesUrl=\"http://localhost:8080/charisma/rest/admin/user/root/role/\"\n"
            + "/>"
          )
        )
      ).user("login").name(),
      is("root")
    );
  }

  /**
   * {@link XmlUsersOfProject#assignees()} should only query the server for as many times as
   * occurrences there are of //assigneesLogin/sub in the project's XML.
   * 
   * <p>Below, we've configured 3 responses in the mock http client, of which only the first
   * two should be fetched.
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  @SuppressWarnings("checkstyle:MethodLength")
  public void assignees() throws Exception {
    assertThat(
      new XmlUsersOfProject(
        new MockProject(),
        new MockSession(),
        new XmlOf(new StringAsDocument(
          // @checkstyle LineLength (1 line)
          "<project versions=\"[2.0, 2.0.1, 2.0.2]\" name=\"Hibero\" shortName=\"HBR\" description=\"Test project.\" isImporting=\"false\">\n"
          + "  <assigneesFullName>\n"
          + "    <sub value=\"Adam Jordens\"/>\n"
          + "    <sub value=\"Application Exception\"/>\n"
          + "  </assigneesFullName>\n"
          + "  <assigneesLogin>\n"
          + "    <sub value=\"ajordens\"/>\n"
          + "    <sub value=\"garisty\"/>\n"
          + "  </assigneesLogin>\n"
          + "  <subsystems>\n"
          + "    <sub value=\"No subsystem\"/>\n"
          + "    <sub value=\"Configuration\"/>\n"
          + "    <sub value=\"HQL\"/>\n"
          + "    <sub value=\"Settings\"/>\n"
          + "    <sub value=\"UI\"/>\n"
          + "  </subsystems>\n"
          + "</project>"
        )),
        new MockHttpClient(
          new MockOkResponse(
            "<user login=\"random\" \n"
            + "      fullName=\"Random User that should not be queried\" \n"
            + "      groupsUrl=\"http://localhost:8080/charisma/rest/admin/user/root/group/\" \n"
            + "      rolesUrl=\"http://localhost:8080/charisma/rest/admin/user/root/role/\"\n"
            + "/>"
          ),
          new MockOkResponse(
            "<user login=\"ajordens\" \n"
            + "      fullName=\"Abraham Jordens\" \n"
            + "      groupsUrl=\"http://localhost:8080/charisma/rest/admin/user/root/group/\" \n"
            + "      rolesUrl=\"http://localhost:8080/charisma/rest/admin/user/root/role/\"\n"
            + "/>"
          ),
          new MockOkResponse(
            "<user login=\"garisty\" \n"
            + "      fullName=\"George Aristy\" \n"
            + "      groupsUrl=\"http://localhost:8080/charisma/rest/admin/user/root/group/\" \n"
            + "      rolesUrl=\"http://localhost:8080/charisma/rest/admin/user/root/role/\"\n"
            + "/>"
          )
        )
      ).assignees().map(User::name).collect(Collectors.toList()),
      containsInAnyOrder("Abraham Jordens", "George Aristy")
    );
  }
}
