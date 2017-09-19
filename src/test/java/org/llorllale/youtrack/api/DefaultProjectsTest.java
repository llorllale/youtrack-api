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

package org.llorllale.youtrack.api;

import org.apache.http.entity.StringEntity;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.llorllale.youtrack.api.mock.MockHttpClient;
import org.llorllale.youtrack.api.mock.MockSession;
import org.llorllale.youtrack.api.mock.response.MockOkResponse;

/**
 * Unit tests for {@link DefaultProjects}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public class DefaultProjectsTest {

  @Test
  public void responseOk() throws Exception {
    assertThat(
        new DefaultProjects(
            new MockSession(), 
            new MockHttpClient(
                new MockOkResponse(
                    new StringEntity(XML_RESPONSE)
                )
            )
        ).all().size(),
        is(2)
    );
  }

  private static final String XML_RESPONSE = 
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