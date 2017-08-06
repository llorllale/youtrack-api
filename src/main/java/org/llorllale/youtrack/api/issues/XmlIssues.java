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
package org.llorllale.youtrack.api.issues;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.llorllale.youtrack.api.response.HttpResponseAsResponse;
import org.llorllale.youtrack.api.response.Response;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.HttpEntityAsJaxb;

/**
 * Parses the XML payloads of the YouTrack interface.
 * @author George Aristy
 * @since 1.0.0
 */
public class XmlIssues implements Issues {
  private static final String ISSUE_RESOURCE = "issue/";

  private final Session session;
  private final HttpClient httpClient;


  /**
   * 
   * @param session
   * @param httpClient 
   * @since 1.0.0
   */
  public XmlIssues(Session session, HttpClient httpClient) {
    this.session = session;
    this.httpClient = httpClient;
  }

  /**
   * 
   * @param session 
   * @since 1.0.0
   */
  public XmlIssues(Session session) {
    this(session, HttpClients.createDefault());
  }
  
  @Override
  public Optional<Issue> withID(String issueID) 
      throws UnauthorizedException, IOException 
  {
    final URIBuilder ub;

    try{
      ub = new URIBuilder(
          session.baseURL()
              .toString()
              .concat(ISSUE_RESOURCE)
              .concat(issueID)
      );
      final HttpGet get = new HttpGet(ub.build());
      get.addHeader("Accept", "application/xml");
      session.cookies().forEach(get::addHeader);
      final Response response = 
          new HttpResponseAsResponse(httpClient.execute(get));
      return response.payload()
          .map(new HttpEntityAsJaxb<>(org.llorllale.youtrack.api.jaxb.Issue.class))
          .map(XmlIssue::new);
    }catch(URISyntaxException e){
      throw new RuntimeException("ISSUE_RESOURCE has an unexpected syntax issue.", e);
    }
  }
}