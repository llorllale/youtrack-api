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

import java.io.IOException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.llorllale.youtrack.api.session.Login;

/**
 * Default {@link Attachments}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.1.0
 */
final class DefaultAttachments extends StreamEnvelope<Attachment> implements Attachments {
  private final Issue issue;
  private final HttpClient client;

  /**
   * Ctor.
   * @param issue owning issue
   * @param login the user's login
   * @param client the Http client to use
   * @throws IOException If an I/O error occurs
   * @since 1.1.0
   */
  DefaultAttachments(Issue issue, Login login, HttpClient client) throws IOException {
    super(
      new StreamOf<>(
        new MappedCollection<>(
          xml -> new XmlAttachment(xml, issue),
          new XmlsOf(
            "/fileUrls/fileUrl",
            new HttpResponseAsResponse(
              client.execute(
                new HttpGet(
                  login.session().baseUrl().toString().concat(
                    String.format("/issue/%s/attachment", issue.id())
                  )
                )
              )
            )
          )
        )
      )
    );
    this.issue = issue;
    this.client = client;
  }
}
