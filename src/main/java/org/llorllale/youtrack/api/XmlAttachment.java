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
import java.util.function.Supplier;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.llorllale.youtrack.api.session.Login;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * An attachment based on XMLs.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.1.0
 */
final class XmlAttachment implements Attachment {
  private final Xml fileUrl;
  private final Issue issue;
  private final Login login;
  private final Supplier<CloseableHttpClient> client;

  /**
   * Ctor.
   * @param fileUrl the 'fileUrl' XML element
   * @param issue the issue of this attachment
   * @param login the user's {@link Login}
   * @param client the http client to use
   * @since 1.1.0
   */
  XmlAttachment(Xml fileUrl, Issue issue, Login login, Supplier<CloseableHttpClient> client) {
    this.fileUrl = fileUrl;
    this.issue = issue;
    this.login = login;
    this.client = client;
  }

  @Override
  public String name() {
    return this.fileUrl.textOf("@name").get();
  }

  @Override
  public User creator() throws IOException, UnauthorizedException {
    return this.issue.project().users().user(
      this.fileUrl.textOf("@authorLogin").get()
    );
  }

  @Override
  public InputStream contents() throws IOException {
    return new HttpResponseAsResponse(
      this.client.get().execute(
        new Authenticated(
          this.login.session(),
          new HttpGet(
            this.fileUrl.textOf("@url").get()
          )
        )
      )
    ).httpResponse().getEntity().getContent();
  }

  @Override
  public Attachments delete() throws IOException, UnauthorizedException {
    new HttpResponseAsResponse(
      this.client.get().execute(
        new Authenticated(
          this.login.session(),
          new HttpDelete(
            this.login.session().baseUrl().toString().concat(
              String.format(
                "/issue/%s/attachment/%s",
                this.issue.id(), this.fileUrl.textOf("@id").get()
              )
            )
          )
        )
      )
    ).httpResponse();
    return this.issue.attachments();
  }
}
