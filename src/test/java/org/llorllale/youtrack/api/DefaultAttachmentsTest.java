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

// @checkstyle AvoidStaticImport (1 line)
import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.youtrack.api.mock.MockIssue;
import org.llorllale.youtrack.api.mock.MockLogin;
import org.llorllale.youtrack.api.mock.MockProject;
import org.llorllale.youtrack.api.mock.http.MockHttpClient;
import org.llorllale.youtrack.api.mock.http.response.MockOkResponse;

/**
 * Unit tests for {@link DefaultAttachments}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.1.0
 * @checkstyle MethodName (500 lines)
 * @checkstyle MagicNumber (500 lines)
 */
public final class DefaultAttachmentsTest {
  /**
   * DefaultAttachments must stream all attachments for the issue.
   * @throws Exception unexpected
   * @since 1.1.0
   */
  @Test
  public void streamsAttachments() throws Exception {
    assertThat(
      new DefaultAttachments(
        new MockIssue(new MockProject()),
        new MockLogin(),
        () -> new MockHttpClient(
          new MockOkResponse(
            "<fileUrls>\n"
            // @checkstyle LineLength (3 lines)
            + "  <fileUrl url=\"/_persistent/uploadFile.html?file=45-46&amp;v=0&amp;c=false\" name=\"file1.html\"/>\n"
            + "  <fileUrl url=\"/_persistent/uploadFile.html?file=45-46&amp;v=0&amp;c=false\" name=\"file2.html\"/>\n"
            + "  <fileUrl url=\"/_persistent/uploadFile.html?file=45-46&amp;v=0&amp;c=false\" name=\"file3.html\"/>\n"
            + "</fileUrls>"
          )
        )
      ).count(),
      new IsEqual<>(3L)
    );
  }
}
