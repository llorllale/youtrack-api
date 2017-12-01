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

import java.io.IOException;
import java.util.stream.Stream;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import org.llorllale.youtrack.api.jaxb.Settings;
import org.llorllale.youtrack.api.jaxb.WorkItemTypes;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.HttpEntityAsJaxb;
import org.llorllale.youtrack.api.util.HttpRequestWithSession;
import org.llorllale.youtrack.api.util.response.HttpResponseAsResponse;

/**
 * Default impl of {@link ProjectTimeTracking}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 */
class DefaultProjectTimeTracking implements ProjectTimeTracking {
  private static final String PATH_TEMPLATE = "/admin/project/%s/timetracking";
  private final Project project;
  private final Session session;

  /**
   * Primary ctor.
   * 
   * @param project the parent {@link Project}
   * @param session the user's {@link Session}
   * @since 0.8.0
   */
  DefaultProjectTimeTracking(Project project, Session session) {
    this.project = project;
    this.session = session;
  }
  
  @Override
  public Project project() {
    return this.project;
  }

  @Override
  public boolean enabled() throws IOException, UnauthorizedException {
    final Settings settings = new HttpEntityAsJaxb<>(Settings.class).apply(
        new HttpResponseAsResponse(
            HttpClients.createDefault().execute(
                new HttpRequestWithSession(
                    this.session, 
                    new HttpGet(
                        this.session.baseUrl().toString()
                            .concat(String.format(PATH_TEMPLATE, this.project().id()))
                    )
                )
            )
        ).httpResponse().getEntity()
    );

    return settings.isEnabled() 
        && nonNull(settings.getEstimation()) 
        && nonNull(settings.getSpentTime());
  }

  @Override
  public Stream<TimeTrackEntryType> types() throws IOException, UnauthorizedException {
    return new HttpEntityAsJaxb<>(WorkItemTypes.class).apply(
        new HttpResponseAsResponse(
            HttpClients.createDefault().execute(
                new HttpRequestWithSession(
                    this.session, 
                    new HttpGet(
                        this.session.baseUrl().toString()
                            .concat(String.format(PATH_TEMPLATE, this.project().id()))
                            .concat("/worktype")
                    )
                )
            )
        ).httpResponse().getEntity()
    ).getWorkType().stream().map(XmlTimeTrackEntryType::new);
  }
}
