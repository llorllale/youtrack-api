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

// @checkstyle AvoidStaticImport (2 lines)
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;
import org.junit.Test;
import org.llorllale.youtrack.api.mock.MockIssue;
import org.llorllale.youtrack.api.mock.MockProject;
import org.llorllale.youtrack.api.mock.MockUser;

/**
 * Unit tests for {@link XmlUsersOfIssue}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 * @checkstyle MultipleStringLiterals (500 lines)
 * @checkstyle MethodName (500 lines)
 */
public final class XmlUsersOfIssueTest {
  /**
   * {@link XmlUsersOfIssue#creator()} must return the value of the field named "reporterName".
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void testCreator() throws Exception {
    assertThat(
      new XmlUsersOfIssue(
        new MockIssue(
          new MockProject()
            .withUser(new MockUser("creator", "creator@gmail.com", "TestCreatorUserLogin"))
        ),
        new XmlOf(new StringAsDocument(
          "<issue id=\"HBR-63\">\n"
            + "    <field name=\"reporterName\">\n"
            + "        <value>TestCreatorUserLogin</value>\n"
            + "    </field>\n"
            + "</issue>"
        ))
      ).creator().loginName(),
      is("TestCreatorUserLogin")
    );
  }

  /**
   * {@link XmlUsersOfIssue#updater()} must return the user whose login is the value of the field
   * "updaterName".
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void existingUpdater() throws Exception {
    final Project project = new MockProject()
      .withUser(new MockUser("updater", "updater@gmail.com", "UpdaterLogin"));
    final Optional<User> updater = new XmlUsersOfIssue(
      new MockIssue(project),
      new XmlOf(new StringAsDocument(
          "<issue id=\"HBR-63\">\n"
            + "    <field name=\"updaterName\">\n"
            + "        <value>UpdaterLogin</value>\n"
            + "    </field>\n"
            + "</issue>"
      ))
    ).updater();
    assertThat(
      updater.isPresent(),
      is(true)
    );
    assertThat(
      updater.get().loginName(),
      is("UpdaterLogin")
    );
  }

  /**
   * {@link XmlUsersOfIssue#updater()} should return an empty optional if the issue's XML has no
   * field with name "updaterName".
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void nonExistingUpdater() throws Exception {
    assertThat(
      new XmlUsersOfIssue(
        new MockIssue(new MockProject()),
        new XmlOf(new StringAsDocument("<issue id=\"HBR-63\"/>"))
      ).updater().isPresent(),
      is(false)
    );
  }

  /**
   * {@link XmlUsersOfIssue#assignee()} must return the user whose login is the value of the field
   * "Assignee".
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void existingAssignee() throws Exception {
    final Optional<User> assignee = new XmlUsersOfIssue(
      new MockIssue(
        new MockProject().withUser(new MockUser("Beto", "beto@gmail.com", "beto"))
      ),
      new XmlOf(new StringAsDocument(
        "<issue id=\"HBR-63\">\n"
          + "    <field name=\"Assignee\">\n"
          + "        <value>beto</value>\n"
          + "    </field>\n"
          + "</issue>"
      ))
    ).assignee();
    assertThat(
      assignee.isPresent(),
      is(true)
    );
    assertThat(
      assignee.get().loginName(),
      is("beto")
    );
  }

  /**
   * {@link XmlUsersOfIssue#assignee()} should return an empty optional if the issue's XML has no
   * field with name "Assignee".
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void nonExistingAssignee() throws Exception {
    assertThat(
      new XmlUsersOfIssue(
        new MockIssue(new MockProject()),
        new XmlOf(new StringAsDocument("<issue id=\"HBR-63\"/>"))
      ).assignee().isPresent(),
      is(false)
    );
  }

  /**
   * {@link XmlUsersOfIssue#issue()} should return the same issue.
   * @since 1.0.0
   */
  @Test
  public void testIssue() {
    final Issue issue = new MockIssue(new MockProject());
    assertThat(
      new XmlUsersOfIssue(issue, null).issue(),
      is(issue)
    );
  }
}
