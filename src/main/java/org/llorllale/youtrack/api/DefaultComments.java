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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.llorllale.youtrack.api.response.HttpResponseAsResponse;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.HttpEntityAsJaxb;
import org.llorllale.youtrack.api.util.NonCheckedUriBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * Default implementation of {@link Comments}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
class DefaultComments implements Comments {
  private final Session session;
  private final Issue issue;
  private final HttpClient httpClient;

  /**
   * Primary ctor.
   * @param session the user's {@link Session}
   * @param issue the {@link Issue} on which the comments are attached to
   * @param httpClient the {@link HttpClient} to use
   * @since 0.4.0
   */
  DefaultComments(Session session, Issue issue, HttpClient httpClient) {
    this.session = session;
    this.issue = issue;
    this.httpClient = httpClient;
  }

  /**
   * Uses the {@link HttpClients#createDefault() default} http client.
   * @param session the user's {@link Session}
   * @param issue the {@link Issue} on which the comments are attached to
   * @since 0.4.0
   * @see #DefaultComments(org.llorllale.youtrack.api.session.Session, 
   *     org.llorllale.youtrack.api.Issue, org.apache.http.client.HttpClient) 
   */
  DefaultComments(Session session, Issue issue) {
    this(session, issue, HttpClients.createDefault());
  }

  @Override
  public List<Comment> all() throws IOException, UnauthorizedException {
    final URI uri = new NonCheckedUriBuilder(
        session.baseUrl()
            .toString()
            .concat("/issue/")
            .concat(issue.id())
            .concat("/comment")
    ).build();
    final HttpGet get = new HttpGet(uri);
    session.cookies().stream().forEach(get::addHeader);
    return new HttpResponseAsResponse(httpClient.execute(get)).asHttpResponse()
        .map(new HttpEntityAsJaxb<>(org.llorllale.youtrack.api.issues.jaxb.Comments.class))
        .map(c -> c.getComment().stream())
        .get()
        .map(c -> new XmlComment(issue, c))
        .collect(toList());
  }

  @Override
  public Comment post(String text) throws IOException, UnauthorizedException {
    final URI uri = new NonCheckedUriBuilder(
        session.baseUrl()
            .toString()
            .concat("/issue/")
            .concat(issue.id())
            .concat("execute")
    ).build();
    final HttpPost post = new HttpPost(uri);
    session.cookies().forEach(post::addHeader);
    post.setEntity(
        new StringEntity(
            "comment=".concat(text), 
            ContentType.APPLICATION_FORM_URLENCODED
        )
    );
    return new HttpResponseAsResponse(httpClient.execute(post))
        .asHttpResponse()
        .map(new HttpEntityAsJaxb<>(org.llorllale.youtrack.api.issues.jaxb.Comments.class))
        .map(c -> c.getComment().stream())
        .get()
        .map(c -> new XmlComment(issue, c))
        .findAny()
        .get();
  }
}
