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

import static java.util.stream.Collectors.joining;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.llorllale.youtrack.api.Issues.IssueSpec;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.HttpRequestWithEntity;
import org.llorllale.youtrack.api.util.HttpRequestWithSession;
import org.llorllale.youtrack.api.util.UncheckedUriBuilder;
import org.llorllale.youtrack.api.util.response.HttpResponseAsResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

/**
 * Default impl of {@link UpdateIssue}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.9.0
 */
class DefaultUpdateIssue implements UpdateIssue {
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
    return this.updateSummaryDesc(new IssueSpec(summary));
  }

  @Override
  public Issue description(String description) throws IOException, UnauthorizedException {
    return this.updateSummaryDesc(new IssueSpec(issue.summary(), description));
  }

  @Override
  public Issue summaryAndDesc(String summary, String description) 
      throws IOException, UnauthorizedException {
    return this.updateSummaryDesc(new IssueSpec(summary, description));
  }

  @Override
  public Issue field(Field field, FieldValue value) throws IOException, UnauthorizedException {
    return this.updateFields(
        new IssueSpec(
            issue.summary(), 
            issue.description()
        ).with(field, value)
    );
  }

  @Override
  public Issue fields(Map<Field, FieldValue> fields) throws IOException, UnauthorizedException {
    final IssueSpec spec = new IssueSpec(issue.summary(), issue.description());
    fields.forEach(spec::with);
    return this.updateFields(spec);
  }

  /*
    Updates just the summary and description of the issue based on the spec.
  */
  private Issue updateSummaryDesc(IssueSpec spec) throws IOException, UnauthorizedException {
    new HttpResponseAsResponse(
        HttpClients.createDefault().execute(
            new HttpRequestWithSession(
                session, 
                new HttpPost(
                    new UncheckedUriBuilder(
                        session.baseUrl().toString()
                            .concat("/issue/")
                            .concat(issue.id())
                    ).addParameters(spec.asNameValuePairs())
                        .build()
                )
            )
        )
    ).asHttpResponse();

    return issue.refresh();
  }

  /*
    Updates the issue's fields based on the spec.
  */
  private Issue updateFields(IssueSpec spec) 
      throws IOException, UnauthorizedException {
    new HttpResponseAsResponse(
        HttpClients.createDefault().execute(
            new HttpRequestWithSession(
                session, 
                new HttpRequestWithEntity(
                    new UrlEncodedFormEntity(
                        Arrays.asList(
                            new BasicNameValuePair(
                                "command", 
                                spec.asFields().entrySet().stream()
                                    .map(
                                        e -> String.join(
                                            " ", 
                                            e.getKey().name(), 
                                            e.getValue().asString()
                                        )
                                    ).collect(joining(" "))
                            )
                        ),
                        StandardCharsets.UTF_8
                    ),
                    new HttpPost(
                        session.baseUrl().toString()
                            .concat("/issue/")
                            .concat(issue.id())
                            .concat("/execute")
                    )
                )
            )
        )
    ).asHttpResponse();

    return issue.refresh();
  }
}
