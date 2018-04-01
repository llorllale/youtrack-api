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

import org.junit.Test;
import org.llorllale.youtrack.api.mock.http.response.MockOkResponse;

/**
 * Unit tests for {@link XmlsOf}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 * @checkstyle MultipleStringLiterals (500 lines)
 */
public final class XmlsOfTest {
  private static final String XML_ISSUES
    = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
    + "<issues>\n"
    + "  <issue id=\"TST-1\">\n"
    + "    <field name=\"attachments\">\n"
    // @checkstyle LineLength (3 line)
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
    // @checkstyle LineLength (3 line)
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

  /**
   * Encapsulates correct number of XMLs.
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void size() throws Exception {
    assertThat(
      new XmlsOf(
        "/issues/issue",
        new HttpResponseAsResponse(new MockOkResponse(XML_ISSUES))
      ).size(),
      is(2)
    );
  }
}
