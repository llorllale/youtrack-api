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
import java.util.function.Supplier;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
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
  private final Supplier<CloseableHttpClient> client;

  /**
   * Ctor.
   * @param issue owning issue
   * @param login the user's login
   * @param client the Http client to use
   * @since 1.1.0
   */
  DefaultAttachments(Issue issue, Login login, Supplier<CloseableHttpClient> client) {
    super(() -> {
      try {
        return new StreamOf<>(
          new MappedCollection<>(
            xml -> new XmlAttachment(xml, issue, login, client),
            new XmlsOf(
              "/fileUrls/fileUrl",
              new HttpResponseAsResponse(
                client.get().execute(
                  new Authenticated(
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
  public Attachments create(String filename, String type, InputStream contents) throws IOException {
    new HttpResponseAsResponse(
      this.client.get().execute(
        new Loaded(
          MultipartEntityBuilder.create()
            .setBoundary(UUID.randomUUID().toString())
            .addBinaryBody(filename, contents, ContentType.create(type), filename)
            .build(),
          new Authenticated(
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
