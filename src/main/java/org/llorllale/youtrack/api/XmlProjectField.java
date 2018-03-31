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
import java.util.stream.Stream;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * JAXB adapter for {@link Field}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 */
final class XmlProjectField implements ProjectField {
  private final Xml xml;
  private final Project project;
  private final Session session;
  private final HttpClient httpClient;

  /**
   * Ctor.
   * 
   * @param xml the XML object received for this field from YouTrack
   * @param project the owner {@link Project}
   * @param session the user's {@link Session}
   * @since 0.8.0
   */
  XmlProjectField(Xml xml, Project project, Session session) {
    this.xml = xml;
    this.project = project;
    this.session = session;
    this.httpClient = HttpClients.createDefault();
  }

  @Override
  public Project project() {
    return this.project;
  }

  @Override
  public String name() {
    return this.xml.textOf("@name").get();
  }

  @Override
  public Stream<FieldValue> values() throws IOException, UnauthorizedException {
    final String bundleName = new XmlsOf(
      "/projectCustomField/param",
      new HttpResponseAsResponse(
        this.httpClient.execute(
          new HttpRequestWithSession(
            this.session, 
            new HttpGet(
              this.session.baseUrl().toString()
                .concat("/admin/project/")
                .concat(this.project().id())
                .concat("/customfield/")
                .concat(new SubstringAfterLast(
                  this.xml.textOf("@url").get(), 
                  "/"
                ).get())
            )
          )
        )
      )
    ).stream().findAny().get().textOf("@value").get();

    return new StreamOf<>(
      new MappedCollection<>(
        x -> new XmlFieldValue(x, new XmlProjectField(this.xml, this.project, this.session)),
        new XmlsOf(
          "/enumeration/value",
          new HttpResponseAsResponse(
            this.httpClient.execute(
              new HttpRequestWithSession(
                this.session, 
                new HttpGet(
                  new UncheckedUriBuilder(
                    this.session.baseUrl(),
                    "/admin/customfield/bundle/".concat(bundleName)
                  ).build()
                )
              )
            )
          )
        )
      )
    );
  }

  @Override
  public int hashCode() {
    return this.name().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Field)) {
      return false;
    }

    final Field other = (Field) obj;
    return this.isSameField(other);
  }
}
