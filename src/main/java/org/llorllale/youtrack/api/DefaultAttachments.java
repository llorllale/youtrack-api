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
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.UUID;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.FormBodyPartBuilder;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.llorllale.youtrack.api.session.Login;

/**
 * Default {@link Attachments}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.1.0
 */
final class DefaultAttachments extends StreamEnvelope<Attachment> implements Attachments {
  private static final String ATTACHMENTS_PATH = "/issue/%s/attachment";

  private final Issue issue;
  private final Login login;
  private final HttpClient client;

  /**
   * Ctor.
   * @param issue owning issue
   * @param login the user's login
   * @param client the Http client to use
   * @since 1.1.0
   */
  DefaultAttachments(Issue issue, Login login, HttpClient client) {
    super(() -> {
      try {
        return new StreamOf<>(
          new MappedCollection<>(
            xml -> new XmlAttachment(xml, issue),
            new XmlsOf(
              "/fileUrls/fileUrl",
              new HttpResponseAsResponse(
                client.execute(
                  new HttpRequestWithSession(
                    login.session(),
                    new HttpGet(
                      login.session().baseUrl().toString().concat(
                        String.format(ATTACHMENTS_PATH, issue.id())
                      )
                    )
                  )
                )
              )
            )
          )
        );
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    });
    this.issue = issue;
    this.login = login;
    this.client = client;
  }

  @Override
  public Attachments create(String name, String type, InputStream contents) throws IOException {
    new HttpResponseAsResponse(
      this.client.execute(
        new HttpRequestWithEntity(
          MultipartEntityBuilder.create()
            .setContentType(ContentType.MULTIPART_FORM_DATA)
            .setBoundary(UUID.randomUUID().toString())
            .addPart(
              FormBodyPartBuilder.create()
                .setName(name)
                .setBody(new InputStreamBody(contents, ContentType.create(type)))
                .build()
            ).build(),
          new HttpRequestWithSession(
            this.login.session(),
            new HttpPost(
              this.login.session().baseUrl().toString().concat(
                String.format(ATTACHMENTS_PATH, this.issue.id())
              )
            )
          )
        )
      )
    ).httpResponse();
    return new DefaultAttachments(this.issue, this.login, this.client);
  }
}
