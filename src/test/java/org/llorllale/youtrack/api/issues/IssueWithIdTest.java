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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import java.net.URL;
import org.llorllale.youtrack.api.mock.MockAuthenticatedSession;
import org.llorllale.youtrack.api.mock.MockHttpClient;
import org.llorllale.youtrack.api.mock.MockThrowingHttpClient;
import org.llorllale.youtrack.api.mock.response.MockForbiddenHttpResponse;
import org.llorllale.youtrack.api.mock.response.MockFoundHttpResponse;
import org.llorllale.youtrack.api.mock.response.MockNotFoundHttpResponse;
import org.llorllale.youtrack.api.mock.response.MockXmlHttpEntity;
import org.llorllale.youtrack.api.session.UnauthorizedException;
/**
 * Unit tests for {@link IssueWithId}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
public class IssueWithIdTest {
  /**
   * Issue should be present when response is code 304.
   * @throws Exception 
   * @since 0.1.0
   */
  @Test
  public void withIdThatExists() throws Exception {
    assertTrue(
        new IssueWithId(
            "TP-2", 
            new MockAuthenticatedSession(new URL("http://some.url")), 
            new MockHttpClient(
                new MockFoundHttpResponse(
                    new MockXmlHttpEntity(WITH_ID_RESPONSE)
                )
            )
        ).query()
        .isPresent()
    );
  }

  /**
   * Issue should not be present when response is code 404.
   * @throws Exception 
   * @since 0.1.0
   */
  @Test
  public void withIdThatDoesNotExist() throws Exception {
    assertFalse(
        new IssueWithId(
            "TP-2", 
            new MockAuthenticatedSession(new URL("http://some.url")), 
            new MockHttpClient( 
                new MockNotFoundHttpResponse()
            )
        ).query()
        .isPresent()
    );
  }

  /**
   * {@link IssueWithId} should propagate any IOException.
   * @throws Exception 
   * @since 0.1.0
   */
  @Test(expected = IOException.class)
  public void propagationOfIoException() throws Exception {
    new IssueWithId(
        "", 
        new MockAuthenticatedSession(new URL("http://some.url")), 
        new MockThrowingHttpClient()
    ).query();
  }

  /**
   * {@link IssueWithId} should throw {@link UnauthorizedException} if the user's session is not 
   * allowed to access the resource.
   * @throws Exception 
   * @since 0.1.0
   */
  @Test(expected = UnauthorizedException.class)
  public void unauthorziedException() throws Exception {
    new IssueWithId(
        "", 
        new MockAuthenticatedSession(new URL("http://some.url")), 
        new MockHttpClient(new MockForbiddenHttpResponse())
    ).query();
  }

  private static final String WITH_ID_RESPONSE =
"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
"<issue id=\"TP-2\" entityId=\"91-4\">\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"projectShortName\">\n" +
"    <value>TP</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"numberInProject\">\n" +
"    <value>2</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"summary\">\n" +
"    <value>Test Issue 2</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"description\">\n" +
"    <value>This is the second test issue.</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"created\">\n" +
"    <value>1501810666764</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"updated\">\n" +
"    <value>1501810666764</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"updaterName\">\n" +
"    <value>root</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"updaterFullName\">\n" +
"    <value>root</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"reporterName\">\n" +
"    <value>root</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"reporterFullName\">\n" +
"    <value>root</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"commentsCount\">\n" +
"    <value>0</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"SingleField\" name=\"votes\">\n" +
"    <value>0</value>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CustomFieldValue\" name=\"Priority\">\n" +
"    <value>Normal</value>\n" +
"    <valueId>Normal</valueId>\n" +
"    <color>\n" +
"      <bg>#e6f6cf</bg>\n" +
"      <fg>#4da400</fg>\n" +
"    </color>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CustomFieldValue\" name=\"Type\">\n" +
"    <value>Bug</value>\n" +
"    <valueId>Bug</valueId>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CustomFieldValue\" name=\"State\">\n" +
"    <value>Open</value>\n" +
"    <valueId>Open</valueId>\n" +
"  </field>\n" +
"  <field xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"MultiUserField\" name=\"Assignee\">\n" +
"    <value fullName=\"root\">root</value>\n" +
"  </field>\n" +
"</issue>"; 
}
