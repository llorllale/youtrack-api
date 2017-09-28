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

import com.google.common.collect.ImmutableMap;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.HttpEntityAsJaxb;
import org.llorllale.youtrack.api.util.HttpRequestWithSession;
import org.llorllale.youtrack.api.util.response.HttpResponseAsResponse;

import java.io.IOException;

/**
 * JAXB implementation of {@link AssignedState}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.7.0
 */
class XmlAssignedState implements AssignedState {
  private final Issue<org.llorllale.youtrack.api.jaxb.Issue> issue;
  private final Session session;
  private final HttpClient httpClient;

  /**
   * Primary ctor.
   * @param issue the parent {@link Issue}
   * @param session the user's {@link Session}
   * @param httpClient the {@link HttpClient} to use
   * @since 0.7.0
   */
  XmlAssignedState(
      Issue<org.llorllale.youtrack.api.jaxb.Issue> issue, 
      Session session, 
      HttpClient httpClient
  ) {
    this.issue = issue;
    this.session = session;
    this.httpClient = httpClient;
  }

  /**
   * Uses the {@link HttpClients#createDefault() default} http client.
   * @param issue the parent {@link Issue}
   * @param session the user's {@link Session}
   * @since 0.7.0
   */
  XmlAssignedState(
      Issue<org.llorllale.youtrack.api.jaxb.Issue> issue, 
      Session session
  ) {
    this(issue, session, HttpClients.createDefault());
  }

  @Override
  public Issue issue() {
    return issue;
  }

  @Override
  public boolean resolved() throws IOException, UnauthorizedException {
    return new HttpEntityAsJaxb<>(org.llorllale.youtrack.api.jaxb.StateBundle.class).apply(
        new HttpResponseAsResponse(
            httpClient.execute(
                new HttpRequestWithSession(
                    session, 
                    new HttpGet(
                        session.baseUrl().toString()
                            .concat("/admin/customfield/stateBundle/States")
                    )
                )
            )
        ).asHttpResponse().getEntity()
    ).getState().stream()
        .anyMatch(s -> s.getValue().equals(this.asString()) && s.isIsResolved());
  }

  @Override
  public AssignedState changeTo(State other) throws IOException, UnauthorizedException {
    return new XmlAssignedState(
        this.issue().update(
            ImmutableMap.of("State", other.asString())
        ), 
        session
    );
  }

  @Override
  public String asString() {
    return issue.asDto().getField()
        .stream()
        .filter(f -> "State".equals(f.getName()))
        .map(f -> f.getValue().getValue())
        .findAny()
        .get();
  }
}
