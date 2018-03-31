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
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Default impl of {@link UpdateIssue}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.9.0
 */
final class DefaultUpdateIssue implements UpdateIssue {
  private static final String PATH_TEMPLATE = "/issue/%s";
  private final Issue issue;
  private final Session session;

  /**
   * Primary ctor.
   * 
   * @param issue the issue to update
   * @param session the user's {@link Session}
   * @since 0.9.0
   */
  DefaultUpdateIssue(Issue issue, Session session) {
    this.issue = issue;
    this.session = session;
  }

  @Override
  public Issue summary(String summary) throws IOException, UnauthorizedException {
    return this.updateSmmryDesc(summary, this.issue.description().orElse(null));
  }

  @Override
  public Issue description(String description) throws IOException, UnauthorizedException {
    return this.updateSmmryDesc(this.issue.summary(), description);
  }

  @Override
  public Issue summaryAndDesc(String summary, String description) 
      throws IOException, UnauthorizedException {
    return this.updateSmmryDesc(summary, description);
  }

  @Override
  public Issue field(Field field, FieldValue value) throws IOException, UnauthorizedException {
    final Map<Field, FieldValue> fields = new HashMap<>();
    fields.put(field, value);
    return this.fields(fields);
  }

  @Override
  public Issue fields(Map<Field, FieldValue> fields) throws IOException, UnauthorizedException {
    final String separator = " ";
    new HttpResponseAsResponse(
      HttpClients.createDefault().execute(
        new HttpRequestWithSession(
          this.session, 
          new HttpRequestWithEntity(
            new UrlEncodedFormEntity(
              Arrays.asList(
                new BasicNameValuePair(
                  "command", 
                  fields.entrySet().stream()
                    .map(
                      e -> String.join(
                        separator, 
                        e.getKey().name(), 
                        e.getValue().asString()
                      )
                    ).collect(Collectors.joining(separator))
                )
              ),
              StandardCharsets.UTF_8
            ),
            new HttpPost(
              this.session.baseUrl().toString()
                .concat(String.format(PATH_TEMPLATE, this.issue.id()))
                .concat("/execute")
            )
          )
        )
      )
    ).httpResponse();

    return this.issue.refresh();
  }

  /**
   * Updates just the summary and description of the issue.
   * 
   * @param summary the issue's summary
   * @param description the issue's description (may be {@code null})
   * @return a new instance of the same issue after being refreshed
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's is unauthorized
   */
  private Issue updateSmmryDesc(String summary, String description) 
      throws IOException, UnauthorizedException {
    new HttpResponseAsResponse(
      HttpClients.createDefault().execute(
        new HttpRequestWithSession(
          this.session, 
          new HttpPost(
            new UncheckedUriBuilder(
              this.session.baseUrl().toString()
                .concat(String.format(PATH_TEMPLATE, this.issue.id()))
            ).param("summary", summary)
              .paramIfPresent("description", Optional.ofNullable(description))
              .build()
          )
        )
      )
    ).httpResponse();

    return this.issue.refresh();
  }
}
