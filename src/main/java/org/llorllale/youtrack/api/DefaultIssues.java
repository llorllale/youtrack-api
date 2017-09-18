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

import static java.util.stream.Collectors.toList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.llorllale.youtrack.api.response.HttpResponseAsResponse;
import org.llorllale.youtrack.api.response.Response;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.HttpEntityAsJaxb;
import org.llorllale.youtrack.api.util.NonCheckedUriBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpPut;

/**
 * Default implementation of {@link Issues}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
class DefaultIssues implements Issues {
  private static final String RESOURCE_PROJECT = "/issue/byproject/";
  private static final String RESOURCE_ISSUE = "/issue/";
  private final Project project;
  private final Session session;
  private final HttpClient httpClient;

  /**
   * Primary ctor.
   * @param project the parent {@link Project}
   * @param session the user {@link Session}
   * @param httpClient the {@link HttpClient} to use
   * @since 0.4.0
   */
  DefaultIssues(Project project, Session session, HttpClient httpClient) {
    this.project = project;
    this.session = session;
    this.httpClient = httpClient;
  }

  /**
   * Uses the {@link HttpClients#createDefault() default} httpClient.
   * @param project the parent {@link Project}
   * @param session the user {@link Session}
   * @since 0.4.0
   * @see #DefaultIssues(org.llorllale.youtrack.api.v2.Project, 
   *     org.llorllale.youtrack.api.session.Session, org.apache.http.client.HttpClient) 
   */
  DefaultIssues(Project project, Session session) {
    this(project, session, HttpClients.createDefault());
  }

  @Override
  public Project project() {
    return project;
  }

  @Override
  public List<Issue> all() throws IOException, UnauthorizedException {
    final URI uri = new NonCheckedUriBuilder(
        session.baseUrl()
            .toString()
            .concat(RESOURCE_PROJECT)
            .concat(project().id())
    ).build();
    final HttpGet get = new HttpGet(uri);
    session.cookies().forEach(get::addHeader);
    final Response response = new HttpResponseAsResponse(httpClient.execute(get));
    return response.payload()
        .map(new HttpEntityAsJaxb<>(org.llorllale.youtrack.api.issues.jaxb.Issues.class))
        .get()
        .getIssue()
        .stream()
        .map(i -> new XmlIssue(project, session, i))
        .collect(toList());
  }

  @Override
  public Optional<Issue> get(String id) throws IOException, UnauthorizedException {
    final NonCheckedUriBuilder ub = new NonCheckedUriBuilder(
        session.baseUrl()
            .toString()
            .concat(RESOURCE_ISSUE)
            .concat(id)
    );
    final HttpGet get = new HttpGet(ub.build());
    session.cookies().forEach(get::addHeader);
    final Response response = new HttpResponseAsResponse(httpClient.execute(get));
    return response.payload()
        .map(new HttpEntityAsJaxb<>(org.llorllale.youtrack.api.issues.jaxb.Issue.class))
        .map(i -> new XmlIssue(project, session, i));
  }

  @Override
  public Issue create(IssueSpec spec) throws IOException, UnauthorizedException {
    final URI uri = new NonCheckedUriBuilder(
        session.baseUrl()
            .toString()
            .concat("/issue")
    ).setParameter("project", project().id())
        .setParameter("summary", spec.summary)
        .setParameter("description", spec.description)
        .build();
    final HttpPut put = new HttpPut(uri);
    session.cookies()
        .stream()
        .forEach(put::addHeader);
    final Response response = new HttpResponseAsResponse(httpClient.execute(put));
    response.payload(); //TODO how to better trigger validation logic?
    final Header location = response.rawResponse().getFirstHeader("Location");
    return this.get(
        location.getValue()
            .substring(
                response.rawResponse()
                    .getFirstHeader("Location")
                    .getValue()
                    .lastIndexOf('/') + 1
        )
    ).get();
  }
}
