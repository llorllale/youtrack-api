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
import org.llorllale.youtrack.api.mock.MockUser;
import org.llorllale.youtrack.api.mock.http.MockHttpClient;
import org.llorllale.youtrack.api.mock.http.response.MockOkResponse;

/**
 * Unit tests for {@link XmlAttachment}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.1.0
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public final class XmlAttachmentTest {
  /**
   * XmlAttachment returns the value of the @name attribute.
   * @since 1.1.0
   */
  @Test
  public void returnsName() {
    assertThat(
      new XmlAttachment(
        // @checkstyle LineLength (1 line)
        new XmlOf("<fileUrl url=\"/_persistent/uploadFile.html?file=45-46&amp;v=0&amp;c=false\" name=\"uploadFile.html\"/>"),
        new MockIssue(new MockProject()),
        new MockLogin(),
        () -> new MockHttpClient()
      ).name(),
      new IsEqual<>("uploadFile.html")
    );
  }

  /**
   * XmlAttachment returns the creator.
   * @throws Exception unexpected
   * @since 1.1.0
   */
  @Test
  public void returnsCreator() throws Exception {
    final User creator = new MockUser("Joe Rogan", "joe@test.com", "jrogan");
    assertThat(
      new XmlAttachment(
        // @checkstyle LineLength (1 line)
        new XmlOf("<fileUrl authorLogin=\"jrogan\" url=\"/_persistent/uploadFile.html?file=45-46&amp;v=0&amp;c=false\" name=\"uploadFile.html\"/>"),
        new MockIssue(new MockProject().withUser(creator)),
        new MockLogin(),
        () -> new MockHttpClient()
      ).creator(),
      new IsEqual<>(creator)
    );
  }

  /**
   * XmlAttachment returns the contents.
   * @throws Exception unexpected
   * @since 1.1.0
   */
  @Test
  public void returnsContents() throws Exception {
    final String contents = "test content";
    assertThat(
      new InputStreamAsString().apply(
        new XmlAttachment(
          new XmlOf(
            // @checkstyle LineLength (1 line)
            "<fileUrl authorLogin=\"jrogan\" url=\"/_persistent/uploadFile.html?file=45-46&amp;v=0&amp;c=false\" name=\"uploadFile.html\"/>"
          ),
          new MockIssue(new MockProject()),
          new MockLogin(),
          () -> new MockHttpClient(new MockOkResponse(contents))
        ).contents()
      ),
      new IsEqual<>(contents)
    );
  }
}
