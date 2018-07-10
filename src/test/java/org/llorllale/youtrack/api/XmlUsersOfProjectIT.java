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
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.apache.http.impl.client.HttpClients;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.session.Login;
import org.llorllale.youtrack.api.session.PermanentToken;

/**
 * Integration tests for {@link XmlUsersOfProject}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 * @checkstyle AbbreviationAsWordInName (2 lines)
 */
public final class XmlUsersOfProjectIT {
  private static IntegrationTestsConfig config;
  private static Login login;
  private static Project project;

  /**
   * Setup.
   * @throws Exception unexpected
   */
  @BeforeClass
  public static void setup() throws Exception {
    config = new IntegrationTestsConfig();
    login = new PermanentToken(config.youtrackUrl(), config.youtrackUserToken());
    project = new DefaultYouTrack(login).projects().stream().findAny().get();
  }

  /**
   * Returns the pre-configured user.
   * @throws Exception unexpected
   */
  @Test
  public void testUser() throws Exception {
    assertThat(
      new XmlUsersOfProject(
        project, login, this.xmlObject("random"), HttpClients.createDefault()
      ).user(config.youtrackUser())
        .loginName(),
      is(config.youtrackUser())
    );
  }

  /**
   * Returns the pre-configured assignee.
   * @throws Exception unexpected
   */
  @Test
  public void testAssignees() throws Exception {
    assertTrue(
      new XmlUsersOfProject(
        project, login, this.xmlObject(config.youtrackUser()), HttpClients.createDefault()
      ).assignees()
        .anyMatch(a -> config.youtrackUser().equals(a.loginName()))
    );
  }

  /**
   * Mock XML.
   * @param assigneeLogin mock assignee login name
   * @return mock XML
   * @throws Exception unexpected
   */
  private Xml xmlObject(String assigneeLogin) throws Exception {
    return new XmlOf(
      new StringAsDocument(
        String.format(
          "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
          + "<project>\n"
          + "  <assigneesLogin>\n"
          + "    <sub value=\"%s\"/>\n"
          + "  </assigneesLogin>\n"
          + "</project>",
          assigneeLogin
        )
      )
    );
  }
}
