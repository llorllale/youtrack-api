/*
 * Copyright 2017 George Aristy.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.llorllale.youtrack.api;

import java.io.IOException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.6.0
 */
class XmlAssignedPriority implements AssignedPriority<org.llorllale.youtrack.api.jaxb.Value> {
  private final Priority<org.llorllale.youtrack.api.jaxb.Value> priority;
  private final Issue<?> issue;
  private final HttpClient httpClient;

  /**
   * 
   * @param priority
   * @param issue
   * @param httpClient 
   * @since 0.6.0
   */
  XmlAssignedPriority(Priority<org.llorllale.youtrack.api.jaxb.Value> priority, Issue<?> issue, HttpClient httpClient) {
    this.priority = priority;
    this.issue = issue;
    this.httpClient = httpClient;
  }

  /**
   * 
   * @param priority
   * @param issue 
   * @since 0.6.0
   */
  XmlAssignedPriority(Priority<org.llorllale.youtrack.api.jaxb.Value> priority, Issue<?> issue) {
    this(priority, issue, HttpClients.createDefault());
  }

  @Override
  public AssignedPriority<org.llorllale.youtrack.api.jaxb.Value> changeTo(Priority<?> other) throws IOException, UnauthorizedException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public String asString() {
    return priority.asString();
  }

  @Override
  public Priority<org.llorllale.youtrack.api.jaxb.Value> lower() throws IOException, UnauthorizedException {
    return new XmlAssignedPriority(priority.lower(), issue);
  }

  @Override
  public Priority<org.llorllale.youtrack.api.jaxb.Value> higher() throws IOException, UnauthorizedException {
    return new XmlAssignedPriority(priority.higher(), issue);
  }

  @Override
  public int compareTo(Priority<org.llorllale.youtrack.api.jaxb.Value> other) {
    return priority.compareTo(other);
  }

  @Override
  public org.llorllale.youtrack.api.jaxb.Value asDto() {
    return priority.asDto();
  }
}
