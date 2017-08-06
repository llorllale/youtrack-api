/*
 * Copyright 2017 George Aristy george.aristy AT gmail DOT com.
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

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Optional;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHttpResponse;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.llorllale.youtrack.api.session.Session;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author George Aristy george.aristy AT gmail DOT com
 */
public class XmlIssuesTest {
  @Test
  public void withIDThatExists() throws Exception {
    final Session mockSession = mock(Session.class);
    when(mockSession.cookies()).thenReturn(Collections.emptyList());
    when(mockSession.baseURL()).thenReturn(new URL("http://some.url/"));
    final BasicHttpEntity entity = new BasicHttpEntity();
    entity.setContentType("application/xml;charset=UTF-8");
    entity.setContentLength(WITH_ID_RESPONSE.getBytes().length);
    entity.setContent(new ByteArrayInputStream(WITH_ID_RESPONSE.getBytes()));
    entity.setContentEncoding("UTF-8");
    final HttpResponse httpResponse = new BasicHttpResponse(
            new ProtocolVersion("HTTP", 1, 1), 
            200, 
            "OK" 
    );
    httpResponse.setEntity(entity);
    final HttpClient mockHttpClient = mock(HttpClient.class);
    when(mockHttpClient.execute(any(HttpUriRequest.class)))
            .thenReturn(httpResponse);
    final Optional<Issue> issue = new XmlIssues(mockSession, mockHttpClient).withID("TP-2");

    assertTrue(issue.isPresent());
  }

  @Test
  public void withIDThatDoesNotExist() throws Exception {
    final Session mockSession = mock(Session.class);
    when(mockSession.cookies()).thenReturn(Collections.emptyList());
    when(mockSession.baseURL()).thenReturn(new URL("http://some.url/"));
    final BasicHttpEntity entity = new BasicHttpEntity();
    entity.setContentType("application/xml;charset=UTF-8");
    entity.setContentLength(NOT_FOUND_RESPONSE.getBytes().length);
    entity.setContent(new ByteArrayInputStream(NOT_FOUND_RESPONSE.getBytes()));
    entity.setContentEncoding("UTF-8");
    final HttpResponse httpResponse = new BasicHttpResponse(
            new ProtocolVersion("HTTP", 1, 1), 
            404, 
            "Not Found" 
    );
    httpResponse.setEntity(entity);
    final HttpClient mockHttpClient = mock(HttpClient.class);
    when(mockHttpClient.execute(any(HttpUriRequest.class)))
            .thenReturn(httpResponse);
    final Optional<Issue> issue = new XmlIssues(mockSession, mockHttpClient).withID("TP-2");

    assertFalse(issue.isPresent());
  }

  private static final String NOT_FOUND_RESPONSE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><error>Issue not found.</error>";

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