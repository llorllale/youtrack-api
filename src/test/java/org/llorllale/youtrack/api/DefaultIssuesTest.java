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

// @checkstyle AvoidStaticImport (5 lines)
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.llorllale.youtrack.api.mock.MockProject;
import org.llorllale.youtrack.api.mock.http.MockHttpClient;
import org.llorllale.youtrack.api.mock.http.MockSession;
import org.llorllale.youtrack.api.mock.http.response.MockNotFoundResponse;
import org.llorllale.youtrack.api.mock.http.response.MockOkResponse;

/**
 * Unit tests for {@link DefaultIssues}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 * @checkstyle MultipleStringLiterals (500 lines)
 * @checkstyle MethodName (500 lines)
 */
public final class DefaultIssuesTest {
  private static final String ISSUES_PAGE1
    = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
    + "<issues>\n"
    + "  <issue id=\"TST-1\">\n"
    + "    <field name=\"attachments\">\n"
    // @checkstyle LineLength (3 lines)
    + "      <value url=\"/_persistent/user.properties?file=45-83&amp;v=0&amp;c=true\">user.properties</value>\n"
    + "      <value url=\"/_persistent/foo1.properties?file=45-84&amp;v=0&amp;c=true\">foo1.properties</value>\n"
    + "      <value url=\"/_persistent/foo2.properties?file=45-85&amp;v=0&amp;c=true\">foo2.properties</value>\n"
    + "    </field>\n"
    + "    <field name=\"Priority\">\n"
    + "      <value>Show-stopper</value>\n"
    + "    </field>\n"
    + "    <field name=\"Type\">\n"
    + "      <value>Feature</value>\n"
    + "    </field>\n"
    + "    <field name=\"State\">\n"
    + "      <value>Reopened</value>\n"
    + "    </field>\n"
    + "    <field name=\"Assignee\">\n"
    + "      <value>beto</value>\n"
    + "    </field>\n"
    + "    <field name=\"Subsystem\">\n"
    + "      <value>UI</value>\n"
    + "    </field>\n"
    + "    <field name=\"Affected versions\">\n"
    + "      <value>2.0.1</value>\n"
    + "    </field>\n"
    + "    <field name=\"Fix versions\">\n"
    + "      <value>2.0.1</value>\n"
    + "    </field>\n"
    + "    <field name=\"projectShortName\">\n"
    + "      <value>TST</value>\n"
    + "    </field>\n"
    + "    <field name=\"numberInProject\">\n"
    + "      <value>2</value>\n"
    + "    </field>\n"
    + "    <field name=\"summary\">\n"
    + "      <value>new issue</value>\n"
    + "    </field>\n"
    + "    <field name=\"description\">\n"
    + "      <value>description of new issue</value>\n"
    + "    </field>\n"
    + "    <field name=\"created\">\n"
    + "      <value>1320664502969</value>\n"
    + "    </field>\n"
    + "    <field name=\"updated\">\n"
    + "      <value>1320664503229</value>\n"
    + "    </field>\n"
    + "    <field name=\"updaterName\">\n"
    + "      <value>app_exception</value>\n"
    + "    </field>\n"
    + "    <field name=\"reporterName\">\n"
    + "      <value>app_exception</value>\n"
    + "    </field>\n"
    + "    <field name=\"commentsCount\">\n"
    + "      <value>0</value>\n"
    + "    </field>\n"
    + "    <field name=\"votes\">\n"
    + "      <value>0</value>\n"
    + "    </field>\n"
    + "  </issue>\n"
    + "  <issue id=\"TST-2\">\n"
    + "    <field name=\"attachments\">\n"
    // @checkstyle LineLength (3 lines)
    + "      <value url=\"/_persistent/user.properties?file=45-83&amp;v=0&amp;c=true\">user.properties</value>\n"
    + "      <value url=\"/_persistent/foo1.properties?file=45-84&amp;v=0&amp;c=true\">foo1.properties</value>\n"
    + "      <value url=\"/_persistent/foo2.properties?file=45-85&amp;v=0&amp;c=true\">foo2.properties</value>\n"
    + "    </field>\n"
    + "    <field name=\"Priority\">\n"
    + "      <value>Show-stopper</value>\n"
    + "    </field>\n"
    + "    <field name=\"Type\">\n"
    + "      <value>Feature</value>\n"
    + "    </field>\n"
    + "    <field name=\"State\">\n"
    + "      <value>Reopened</value>\n"
    + "    </field>\n"
    + "    <field name=\"Assignee\">\n"
    + "      <value>beto</value>\n"
    + "    </field>\n"
    + "    <field name=\"Subsystem\">\n"
    + "      <value>UI</value>\n"
    + "    </field>\n"
    + "    <field name=\"Affected versions\">\n"
    + "      <value>2.0.1</value>\n"
    + "    </field>\n"
    + "    <field name=\"Fix versions\">\n"
    + "      <value>2.0.1</value>\n"
    + "    </field>\n"
    + "    <field name=\"projectShortName\">\n"
    + "      <value>TST</value>\n"
    + "    </field>\n"
    + "    <field name=\"numberInProject\">\n"
    + "      <value>2</value>\n"
    + "    </field>\n"
    + "    <field name=\"summary\">\n"
    + "      <value>new issue</value>\n"
    + "    </field>\n"
    + "    <field name=\"description\">\n"
    + "      <value>description of new issue</value>\n"
    + "    </field>\n"
    + "    <field name=\"created\">\n"
    + "      <value>1320664502969</value>\n"
    + "    </field>\n"
    + "    <field name=\"updated\">\n"
    + "      <value>1320664503229</value>\n"
    + "    </field>\n"
    + "    <field name=\"updaterName\">\n"
    + "      <value>app_exception</value>\n"
    + "    </field>\n"
    + "    <field name=\"reporterName\">\n"
    + "      <value>app_exception</value>\n"
    + "    </field>\n"
    + "    <field name=\"commentsCount\">\n"
    + "      <value>0</value>\n"
    + "    </field>\n"
    + "    <field name=\"votes\">\n"
    + "      <value>0</value>\n"
    + "    </field>\n"
    + "  </issue>\n"
    + "</issues>";

  private static final String ISSUES_PAGE2
    = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
    + "<issues>\n"
    + "  <issue id=\"TST-3\">\n"
    + "    <field name=\"attachments\">\n"
    // @checkstyle LineLength (3 lines)
    + "      <value url=\"/_persistent/user.properties?file=45-83&amp;v=0&amp;c=true\">user.properties</value>\n"
    + "      <value url=\"/_persistent/foo1.properties?file=45-84&amp;v=0&amp;c=true\">foo1.properties</value>\n"
    + "      <value url=\"/_persistent/foo2.properties?file=45-85&amp;v=0&amp;c=true\">foo2.properties</value>\n"
    + "    </field>\n"
    + "    <field name=\"Priority\">\n"
    + "      <value>Show-stopper</value>\n"
    + "    </field>\n"
    + "    <field name=\"Type\">\n"
    + "      <value>Feature</value>\n"
    + "    </field>\n"
    + "    <field name=\"State\">\n"
    + "      <value>Reopened</value>\n"
    + "    </field>\n"
    + "    <field name=\"Assignee\">\n"
    + "      <value>beto</value>\n"
    + "    </field>\n"
    + "    <field name=\"Subsystem\">\n"
    + "      <value>UI</value>\n"
    + "    </field>\n"
    + "    <field name=\"Affected versions\">\n"
    + "      <value>2.0.1</value>\n"
    + "    </field>\n"
    + "    <field name=\"Fix versions\">\n"
    + "      <value>2.0.1</value>\n"
    + "    </field>\n"
    + "    <field name=\"projectShortName\">\n"
    + "      <value>TST</value>\n"
    + "    </field>\n"
    + "    <field name=\"numberInProject\">\n"
    + "      <value>2</value>\n"
    + "    </field>\n"
    + "    <field name=\"summary\">\n"
    + "      <value>new issue</value>\n"
    + "    </field>\n"
    + "    <field name=\"description\">\n"
    + "      <value>description of new issue</value>\n"
    + "    </field>\n"
    + "    <field name=\"created\">\n"
    + "      <value>1320664502969</value>\n"
    + "    </field>\n"
    + "    <field name=\"updated\">\n"
    + "      <value>1320664503229</value>\n"
    + "    </field>\n"
    + "    <field name=\"updaterName\">\n"
    + "      <value>app_exception</value>\n"
    + "    </field>\n"
    + "    <field name=\"reporterName\">\n"
    + "      <value>app_exception</value>\n"
    + "    </field>\n"
    + "    <field name=\"commentsCount\">\n"
    + "      <value>0</value>\n"
    + "    </field>\n"
    + "    <field name=\"votes\">\n"
    + "      <value>0</value>\n"
    + "    </field>\n"
    + "  </issue>\n"
    + "  <issue id=\"TST-4\">\n"
    + "    <field name=\"attachments\">\n"
    // @checkstyle LineLength (3 lines)
    + "      <value url=\"/_persistent/user.properties?file=45-83&amp;v=0&amp;c=true\">user.properties</value>\n"
    + "      <value url=\"/_persistent/foo1.properties?file=45-84&amp;v=0&amp;c=true\">foo1.properties</value>\n"
    + "      <value url=\"/_persistent/foo2.properties?file=45-85&amp;v=0&amp;c=true\">foo2.properties</value>\n"
    + "    </field>\n"
    + "    <field name=\"Priority\">\n"
    + "      <value>Show-stopper</value>\n"
    + "    </field>\n"
    + "    <field name=\"Type\">\n"
    + "      <value>Feature</value>\n"
    + "    </field>\n"
    + "    <field name=\"State\">\n"
    + "      <value>Reopened</value>\n"
    + "    </field>\n"
    + "    <field name=\"Assignee\">\n"
    + "      <value>beto</value>\n"
    + "    </field>\n"
    + "    <field name=\"Subsystem\">\n"
    + "      <value>UI</value>\n"
    + "    </field>\n"
    + "    <field name=\"Affected versions\">\n"
    + "      <value>2.0.1</value>\n"
    + "    </field>\n"
    + "    <field name=\"Fix versions\">\n"
    + "      <value>2.0.1</value>\n"
    + "    </field>\n"
    + "    <field name=\"projectShortName\">\n"
    + "      <value>TST</value>\n"
    + "    </field>\n"
    + "    <field name=\"numberInProject\">\n"
    + "      <value>2</value>\n"
    + "    </field>\n"
    + "    <field name=\"summary\">\n"
    + "      <value>new issue</value>\n"
    + "    </field>\n"
    + "    <field name=\"description\">\n"
    + "      <value>description of new issue</value>\n"
    + "    </field>\n"
    + "    <field name=\"created\">\n"
    + "      <value>1320664502969</value>\n"
    + "    </field>\n"
    + "    <field name=\"updated\">\n"
    + "      <value>1320664503229</value>\n"
    + "    </field>\n"
    + "    <field name=\"updaterName\">\n"
    + "      <value>app_exception</value>\n"
    + "    </field>\n"
    + "    <field name=\"reporterName\">\n"
    + "      <value>app_exception</value>\n"
    + "    </field>\n"
    + "    <field name=\"commentsCount\">\n"
    + "      <value>0</value>\n"
    + "    </field>\n"
    + "    <field name=\"votes\">\n"
    + "      <value>0</value>\n"
    + "    </field>\n"
    + "  </issue>\n"
    + "</issues>";

  private static final String ONE_ISSUE
    = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
    + "<issue id=\"HBR-63\">\n"
    + "    <field name=\"attachments\">\n"
    // @checkstyle LineLength (1 lines)
    + "        <value url=\"/_persistent/uploadFile.html?file=45-46&amp;v=0&amp;c=true\">uploadFile.html</value>\n"
    + "    </field>\n"
    // @checkstyle LineLength (1 lines)
    + "    <comment id=\"42-306\" author=\"root\" issueId=\"HBR-63\" deleted=\"false\" text=\"comment 1!\" shownForIssueAuthor=\"false\"\n"
    + "             created=\"1267030230127\">\n"
    + "        <replies/>\n"
    + "    </comment>\n"
    // @checkstyle LineLength (1 lines)
    + "    <comment id=\"42-307\" author=\"root\" issueId=\"HBR-63\" deleted=\"false\" text=\"comment 2?\" shownForIssueAuthor=\"false\"\n"
    + "             created=\"1267030238721\" updated=\"1267030230127\">\n"
    + "        <replies/>\n"
    + "    </comment>\n"
    + "    <field name=\"Priority\">\n"
    + "        <value>Normal</value>\n"
    + "    </field>\n"
    + "    <field name=\"Type\">\n"
    + "        <value>Bug</value>\n"
    + "    </field>\n"
    + "    <field name=\"State\">\n"
    + "        <value>Won't fix</value>\n"
    + "    </field>\n"
    + "    <field name=\"Assignee\">\n"
    + "        <value>beto</value>\n"
    + "    </field>\n"
    + "    <field name=\"Subsystem\">\n"
    + "        <value>Configuration</value>\n"
    + "    </field>\n"
    + "    <field name=\"Fix versions\">\n"
    + "        <value>2.0</value>\n"
    + "        <value>2.0.5</value>\n"
    + "        <value>2.0.7</value>\n"
    + "    </field>\n"
    + "    <field name=\"cf\">\n"
    + "        <value>0</value>\n"
    + "        <value>!</value>\n"
    + "    </field>\n"
    + "    <field name=\"scf\">\n"
    + "        <value>1265835603000</value>\n"
    + "    </field>\n"
    + "    <field name=\"links\">\n"
    + "        <value type=\"Depend\" role=\"depends on\">HBR-62</value>\n"
    + "        <value type=\"Duplicate\" role=\"duplicates\">HBR-57</value>\n"
    + "        <value type=\"Duplicate\" role=\"is duplicated by\">HBR-54</value>\n"
    + "        <value type=\"Relates\" role=\"relates to\">HBR-49</value>\n"
    + "        <value type=\"Relates\" role=\"is related to\">HBR-51</value>\n"
    + "        <value type=\"Depend\" role=\"is required for\">HBR-49</value>\n"
    + "    </field>\n"
    + "    <field name=\"projectShortName\">\n"
    + "        <value>HBR</value>\n"
    + "    </field>\n"
    + "    <field name=\"numberInProject\">\n"
    + "        <value>63</value>\n"
    + "    </field>\n"
    + "    <field name=\"summary\">\n"
    + "        <value>summary</value>\n"
    + "    </field>\n"
    + "    <field name=\"description\">\n"
    + "        <value>description</value>\n"
    + "    </field>\n"
    + "    <field name=\"created\">\n"
    + "        <value>1262171005630</value>\n"
    + "    </field>\n"
    + "    <field name=\"updated\">\n"
    + "        <value>1267630833573</value>\n"
    + "    </field>\n"
    + "    <field name=\"updaterName\">\n"
    + "        <value>root</value>\n"
    + "    </field>\n"
    + "    <field name=\"resolved\">\n"
    + "        <value>1267030108251</value>\n"
    + "    </field>\n"
    + "    <field name=\"reporterName\">\n"
    + "        <value>root</value>\n"
    + "    </field>\n"
    + "    <field name=\"commentsCount\">\n"
    + "        <value>2</value>\n"
    + "    </field>\n"
    + "    <field name=\"votes\">\n"
    + "        <value>0</value>\n"
    + "    </field>\n"
    + "</issue>";

  /**
   * {@link DefaultIssues} must return all issues (4 in this case, divided in 2 pages of results).
   * @throws Exception unexpected
   */
  @Test
  public void testStream() throws Exception {
    assertThat(
      new DefaultIssues(
        new MockProject(),
        new MockSession(),
        new MockHttpClient(
          new MockOkResponse(
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><issues></issues>"
          ),
          new MockOkResponse(ISSUES_PAGE1),
          new MockOkResponse(ISSUES_PAGE2)
        )
      ).stream().map(Issue::id).collect(toList()),
      containsInAnyOrder("TST-1", "TST-2", "TST-3", "TST-4")
    );
  }

  /**
   * DefaultIssues must return the Issue if present.
   * @throws Exception unexpected
   */
  @Test
  public void testGetExistingIssue() throws Exception {
    assertTrue(
      new DefaultIssues(
        new MockProject("ID1", "Name", "Desc"),
        new MockSession(),
        new MockHttpClient(
          new MockOkResponse(ONE_ISSUE)
        )
      ).get("ID").isPresent()
    );
  }

  /**
   * DefaultIssues must return an empty optional if the Issue is not found.
   * @throws Exception unexpected
   */
  @Test
  public void testGetNonExistingIssue() throws Exception {
    assertFalse(
      new DefaultIssues(
        new MockProject("ID1", "Name", "Desc"),
        new MockSession(),
        new MockHttpClient(
          new MockNotFoundResponse(
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
            + "<error>Issue not found.</error>"
          )
        )
      ).get("ID").isPresent()
    );
  }
}
