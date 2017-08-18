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

import java.time.Instant;

/**
 * JAXB implementation of {@link Comment}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.2.0
 */
class XmlComment implements Comment {
  private final org.llorllale.youtrack.api.issues.jaxb.Comment jaxbComment;

  /**
   * Ctor.
   * @param jaxbComment the jaxb instance to be adapted into {@link Comment}
   * @since 0.2.0
   */
  XmlComment(org.llorllale.youtrack.api.issues.jaxb.Comment jaxbComment) {
    this.jaxbComment = jaxbComment;
  }

  @Override
  public String id() {
    return jaxbComment.getId();
  }

  @Override
  public Instant creationDate() {
    return Instant.ofEpochMilli(jaxbComment.getCreated());
  }

  @Override
  public String authorLoginName() {
    return jaxbComment.getAuthor();
  }

  @Override
  public String issueId() {
    return jaxbComment.getIssueId();
  }

  @Override
  public String text() {
    return jaxbComment.getText();
  }
}
