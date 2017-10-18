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

import static org.apache.commons.lang3.StringUtils.substringAfterLast;

import com.google.common.net.UrlEscapers;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.llorllale.youtrack.api.jaxb.Enumeration;
import org.llorllale.youtrack.api.jaxb.ProjectCustomField;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.HttpEntityAsJaxb;
import org.llorllale.youtrack.api.util.HttpRequestWithSession;
import org.llorllale.youtrack.api.util.response.HttpResponseAsResponse;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * JAXB adapter for {@link Field}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 */
class XmlProjectField implements ProjectField {
  private final ProjectCustomField jaxb;
  private final Project project;
  private final Session session;
  private final HttpClient httpClient;

  /**
   * Ctor.
   * 
   * @param jaxb the jaxb object to adapt to {@link Field}
   * @param project the owner {@link Project}
   * @param session the user's {@link Session}
   * @since 0.8.0
   */
  XmlProjectField(ProjectCustomField jaxb, Project project, Session session) {
    this.jaxb = jaxb;
    this.project = project;
    this.session = session;
    this.httpClient = HttpClients.createDefault();
  }

  @Override
  public Project project() {
    return project;
  }

  @Override
  public String name() {
    return jaxb.getName();
  }

  @Override
  public Stream<FieldValue> values() throws IOException, UnauthorizedException {
    final String bundleName = UrlEscapers.urlPathSegmentEscaper().escape(
        new HttpEntityAsJaxb<>(ProjectCustomField.class).apply(
            new HttpResponseAsResponse(
                httpClient.execute(
                    new HttpRequestWithSession(
                        session, 
                        new HttpGet(
                            session.baseUrl().toString()
                                .concat("/admin/project/")
                                .concat(project().id())
                                .concat("/customfield/")
                                .concat(substringAfterLast(jaxb.getUrl(), "/"))
                        )
                    )
                )
            ).asHttpResponse().getEntity()
        ).getParam().getValue()
    );

    return new HttpEntityAsJaxb<>(Enumeration.class).apply(
        new HttpResponseAsResponse(
            httpClient.execute(
                new HttpRequestWithSession(
                    session, 
                    new HttpGet(
                        session.baseUrl().toString()
                            .concat("/admin/customfield/bundle/")
                            .concat(bundleName)
                    )
                )
            )
        ).asHttpResponse().getEntity()
    ).getValue().stream()
        .map(v -> 
            new XmlFieldValue(
                v, 
                new XmlProjectField(jaxb, project, session)
            )
        );
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 47 * hash + Objects.hashCode(this.name());
    hash = 47 * hash + Objects.hashCode(this.project().id());
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (!Field.class.isAssignableFrom(obj.getClass())) {
      return false;
    }

    final Field other = (Field) obj;

    return this.isSameField(other);
  }
}