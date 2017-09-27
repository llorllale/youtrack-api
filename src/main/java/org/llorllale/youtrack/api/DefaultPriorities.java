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

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.HttpEntityAsJaxb;
import org.llorllale.youtrack.api.util.HttpRequestWithSession;
import org.llorllale.youtrack.api.util.response.HttpResponseAsResponse;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * Default impl of {@link Priorities}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.6.0
 */
class DefaultPriorities implements Priorities {
  private final Session session;
  private final HttpClient httpClient;

  /**
   * Primary ctor.
   * @param session the user's {@link Session}
   * @param httpClient the {@link HttpClient} to use
   * @since 0.6.0
   */
  DefaultPriorities(Session session, HttpClient httpClient) {
    this.session = session;
    this.httpClient = httpClient;
  }

  /**
   * Uses the {@link HttpClients#createDefault() default} http client.
   * @param session the user's {@link Session}
   * @since 0.6.0
   */
  DefaultPriorities(Session session) {
    this(session, HttpClients.createDefault());
  }

  @Override
  public Stream<Priority> stream() throws IOException, UnauthorizedException {
    return new HttpEntityAsJaxb<>(org.llorllale.youtrack.api.jaxb.Enumeration.class).apply(
        new HttpResponseAsResponse(
            httpClient.execute(
                new HttpRequestWithSession(
                    session, 
                    new HttpGet(
                        session.baseUrl().toString()
                            .concat("/admin/customfield/bundle/Priorities")
                    )
                )
            )
        ).asHttpResponse().getEntity()
    ).getValue().stream().map(XmlPriority::new);
  }
}
