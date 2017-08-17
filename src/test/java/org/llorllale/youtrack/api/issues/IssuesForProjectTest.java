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
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.llorllale.youtrack.api.mock.MockAuthenticatedSession;
import org.llorllale.youtrack.api.mock.MockHttpClient;
import org.llorllale.youtrack.api.mock.MockThrowingHttpClient;
import org.llorllale.youtrack.api.mock.response.MockOkHttpResponse;
import org.llorllale.youtrack.api.mock.response.MockUnauthorizedHttpResponse;
import org.llorllale.youtrack.api.mock.response.MockXmlHttpEntity;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Unit tests for {@link IssuesForProject}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.2.0
 */
public class IssuesForProjectTest {
  /**
   * Should produce all {@link Issue issues} returned by YouTrack.
   * @throws Exception 
   * @since 0.2.0
   */
  @Test
  public void queryReturnsIssues() throws Exception {
    assertThat(
        new IssuesForProject(
            "TST", 
            new MockAuthenticatedSession(), 
            new MockHttpClient(
                new MockOkHttpResponse(
                    new MockXmlHttpEntity(PROJECTS)
                )
            )
        ).query().size(),
        is(1)
    );
  }

  /**
   * Should throw {@link UnauthorizedException} if YouTrack returns an 'unauthorized' response.
   * @throws Exception 
   * @since 0.2.0
   */
  @Test(expected = UnauthorizedException.class)
  public void unauthorizedException() throws Exception {
    new IssuesForProject(
        "TST", 
        new MockAuthenticatedSession(), 
        new MockHttpClient(
            new MockUnauthorizedHttpResponse()
        )
    ).query();
  }

  /**
   * Should throw {@link IOException} if YouTrack is unreachable.
   * @throws Exception 
   * @since 0.2.0
   */
  @Test(expected = IOException.class)
  public void ioexception() throws Exception {
    new IssuesForProject(
        "TST", 
        new MockAuthenticatedSession(), 
        new MockThrowingHttpClient()
    ).query();
  }

  private static final String PROJECTS =
"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
"<issues>\n" +
"  <issue id=\"TST-2\">\n" +
"    <field name=\"attachments\">\n" +
"      <value url=\"/_persistent/user.properties?file=45-83&amp;v=0&amp;c=true\">user.properties</value>\n" +
"      <value url=\"/_persistent/foo1.properties?file=45-84&amp;v=0&amp;c=true\">foo1.properties</value>\n" +
"      <value url=\"/_persistent/foo2.properties?file=45-85&amp;v=0&amp;c=true\">foo2.properties</value>\n" +
"    </field>\n" +
"    <field name=\"Priority\">\n" +
"      <value>Show-stopper</value>\n" +
"    </field>\n" +
"    <field name=\"Type\">\n" +
"      <value>Feature</value>\n" +
"    </field>\n" +
"    <field name=\"State\">\n" +
"      <value>Reopened</value>\n" +
"    </field>\n" +
"    <field name=\"Assignee\">\n" +
"      <value>beto</value>\n" +
"    </field>\n" +
"    <field name=\"Subsystem\">\n" +
"      <value>UI</value>\n" +
"    </field>\n" +
"    <field name=\"Affected versions\">\n" +
"      <value>2.0.1</value>\n" +
"    </field>\n" +
"    <field name=\"Fix versions\">\n" +
"      <value>2.0.1</value>\n" +
"    </field>\n" +
"    <field name=\"projectShortName\">\n" +
"      <value>TST</value>\n" +
"    </field>\n" +
"    <field name=\"numberInProject\">\n" +
"      <value>2</value>\n" +
"    </field>\n" +
"    <field name=\"summary\">\n" +
"      <value>new issue</value>\n" +
"    </field>\n" +
"    <field name=\"description\">\n" +
"      <value>description of new issue</value>\n" +
"    </field>\n" +
"    <field name=\"created\">\n" +
"      <value>1320664502969</value>\n" +
"    </field>\n" +
"    <field name=\"updated\">\n" +
"      <value>1320664503229</value>\n" +
"    </field>\n" +
"    <field name=\"updaterName\">\n" +
"      <value>app_exception</value>\n" +
"    </field>\n" +
"    <field name=\"reporterName\">\n" +
"      <value>app_exception</value>\n" +
"    </field>\n" +
"    <field name=\"commentsCount\">\n" +
"      <value>0</value>\n" +
"    </field>\n" +
"    <field name=\"votes\">\n" +
"      <value>0</value>\n" +
"    </field>\n" +
"  </issue>\n" +
"</issues>";
}
