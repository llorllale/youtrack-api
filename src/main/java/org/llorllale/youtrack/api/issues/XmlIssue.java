/* 
 * Copyright 2017 George Aristy (george.aristy@gmail.com).
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

import org.llorllale.youtrack.api.jaxb.Field;
import org.llorllale.youtrack.api.jaxb.Value;

import java.time.Instant;
import java.util.Optional;

/**
 * JAXB implementation of {@link Issue}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
public class XmlIssue implements Issue {
  private final org.llorllale.youtrack.api.jaxb.Issue jaxbIssue;

  /**
   * Ctor.
   * @param jaxbIssue the JAXB issue to be adapted
   * @since 0.1.0
   */
  public XmlIssue(org.llorllale.youtrack.api.jaxb.Issue jaxbIssue) {
    this.jaxbIssue = jaxbIssue;
  }

  @Override
  public String id() {
    return jaxbIssue.getId();
  }

  @Override
  public Instant creationDate() {
    return Instant.ofEpochMilli(jaxbIssue.getField()
            .stream()
            .filter(f -> "created".equals(f.getName()))
            .map(Field::getValue)
            .map(Value::getValue)
            .map(Long::valueOf)
            .findFirst()
            .get()
    );
  }

  @Override
  public String type() {
    return jaxbIssue.getField()
            .stream()
            .filter(f -> "Type".equals(f.getName()))
            .map(Field::getValue)
            .map(Value::getValue)
            .findFirst()
            .get();
  }

  @Override
  public String state() {
    return jaxbIssue.getField()
            .stream()
            .filter(f -> "State".equals(f.getName()))
            .map(Field::getValue)
            .map(Value::getValue)
            .findFirst()
            .get();
  }

  @Override
  public String assignee() {
    return jaxbIssue.getField()
            .stream()
            .filter(f -> "Assignee".equals(f.getName()))
            .map(Field::getValue)
            .map(Value::getValue)
            .findFirst()
            .get();
  }

  @Override
  public String priority() {
    return jaxbIssue.getField()
            .stream()
            .filter(f -> "Priority".equals(f.getName()))
            .map(Field::getValue)
            .map(Value::getValue)
            .findFirst()
            .get();
  }

  @Override
  public String reporterName() {
    return jaxbIssue.getField()
            .stream()
            .filter(f -> "reporterName".equals(f.getName()))
            .map(Field::getValue)
            .map(Value::getValue)
            .findFirst()
            .get();
  }

  @Override
  public Optional<String> updaterName() {
    return jaxbIssue.getField()
            .stream()
            .filter(f -> "updaterName".equals(f.getName()))
            .map(Field::getValue)
            .map(Value::getValue)
            .findFirst();
  }

  @Override
  public String summary() {
    return jaxbIssue.getField()
            .stream()
            .filter(f -> "summary".equals(f.getName()))
            .map(Field::getValue)
            .map(Value::getValue)
            .findFirst()
            .get();
  }

  @Override
  public String description() {
    return jaxbIssue.getField()
            .stream()
            .filter(f -> "description".equals(f.getName()))
            .map(Field::getValue)
            .map(Value::getValue)
            .findFirst()
            .get();
  }

  @Override
  public String projectShortName() {
    return jaxbIssue.getField()
            .stream()
            .filter(f -> "projectShortName".equals(f.getName()))
            .map(Field::getValue)
            .map(Value::getValue)
            .findFirst()
            .get();
  }
}