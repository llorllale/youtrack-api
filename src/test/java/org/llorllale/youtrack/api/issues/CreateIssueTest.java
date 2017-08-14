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

import java.net.URL;
import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicHttpResponse;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.llorllale.youtrack.api.mock.MockAuthenticatedSession;
import org.llorllale.youtrack.api.mock.MockCreatedHttpResponse;
import org.llorllale.youtrack.api.mock.MockHttpClient;

/**
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
public class CreateIssueTest {
  @Test
  public void summary() throws Exception {
    assertThat(
        new CreateIssue(
            new MockAuthenticatedSession(new URL("http://some.url")), 
            "",
            new MockHttpClient(
                new MockCreatedHttpResponse()
            )
        ).withSummary("Test Summary")
        .create()
        .summary(),
      is("Test Summary")
    );
  }

  @Test
  public void description() throws Exception {
    assertThat(
        new CreateIssue(
            new MockAuthenticatedSession(new URL("http://some.url")), 
            "",
            new MockHttpClient(
                new MockCreatedHttpResponse()
            )
        ).withDescription("Test Description")
            .create()
            .summary(),
      is("Test Description")
    );
  }

  @Test
  public void create() throws Exception {
    assertThat(
        new CreateIssue(
            new MockAuthenticatedSession(new URL("http://some.url")), 
            "",
            new MockHttpClient(
                new MockCreatedHttpResponse()
            )
        ).withSummary("Test Summary")
            .withDescription("Test Description")
            .create()
            .summary(),
      is("Test Description")
    );
  }
  
}
