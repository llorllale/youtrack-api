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

// @checkstyle AvoidStaticImport (1 line)
import static org.junit.Assert.assertNotEquals;

import java.util.HashMap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.session.PermanentToken;
import org.llorllale.youtrack.api.session.Session;

/**
 * Integration tests for {@link DefaultUpdateIssue}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.9.0
 * @checkstyle MethodName (500 lines)
 * @checkstyle AbbreviationAsWordInName (2 lines)
 */
public final class DefaultUpdateIssueIT {
  private static IntegrationTestsConfig config;
  private static Session session;
  private static Issue issue;

  /**
   * Setup.
   * @throws Exception unexpected
   */
  @BeforeClass
  public static void setup() throws Exception {
    config = new IntegrationTestsConfig();
    session = new PermanentToken(
      config.youtrackUrl(),
      config.youtrackUserToken()
    ).login();
    issue = new DefaultYouTrack(session).projects().stream()
      .findAny()
      .get()
      .issues()
      .create(
        DefaultUpdateIssueIT.class.getSimpleName(),
        "integration tests"
      );
  }

  /**
   * Should update the issue's summary text.
   * @throws Exception unexpected
   */
  @Test
  public void testSummary() throws Exception {
    final String newSummary = DefaultUpdateIssueIT.class.getSimpleName()
      .concat(".testSummary");
    assertNotEquals(
      new DefaultUpdateIssue(issue, session).summary(newSummary).summary(),
      issue.summary()
    );
  }

  /**
   * Should update the issue's description text.
   * @throws Exception unexpected
   */
  @Test
  public void testDescription() throws Exception {
    final String newDesc = DefaultUpdateIssueIT.class.getSimpleName()
      .concat("testDescription");
    assertNotEquals(
      new DefaultUpdateIssue(issue, session)
        .description(newDesc)
        .description(),
      issue.description()
    );
  }

  /**
   * Should update the issue's summary and description.
   * @throws Exception unexpected
   */
  @Test
  public void testSummaryAndDesc() throws Exception {
    final String newSummary = DefaultUpdateIssueIT.class.getSimpleName()
      .concat("testSummaryAndDesc_summ");
    final String newDesc = DefaultUpdateIssueIT.class.getSimpleName()
      .concat("testSummaryAndDesc_desc");
    final Issue newIssue = new DefaultUpdateIssue(issue, session)
      .summaryAndDesc(newSummary, newDesc);
    assertNotEquals(issue.summary(), newIssue.summary());
    assertNotEquals(issue.description(), newIssue.description());
  }

  /**
   * Updataes a field's value.
   * @throws Exception unexpected
   */
  @Test
  public void testField() throws Exception {
    final AssignedField field = issue.fields().stream().findAny().get();
    final FieldValue oldValue = field.value();
    final FieldValue newValue = issue.project().fields()
      .stream()
      .filter(f -> f.isSameField(field))
      .findAny().get()
      .values()
      .filter(v -> !v.equals(oldValue))
      .findAny().get();
    assertNotEquals(
      new DefaultUpdateIssue(issue, session)
        .field(field, newValue).fields()
        .stream()
        .filter(f -> f.isSameField(field))
        .map(AssignedField::value)
        .findAny().get(),
      oldValue
    );
  }

  /**
   * Updates values of several fields.
   * @throws Exception unexpected
   */
  @Test
  public void testFields() throws Exception {
    final AssignedField firstField = issue.fields().stream().findAny().get();
    final FieldValue firstOldVal = firstField.value();
    final FieldValue firstNewVal = issue.project().fields()
      .stream()
      .filter(f -> f.isSameField(firstField))
      .findAny().get()
      .values()
      .filter(v -> !v.equals(firstOldVal))
      .findAny().get();
    final AssignedField secondField = issue.fields().stream().findAny().get();
    final FieldValue secondOldVal = secondField.value();
    final FieldValue secondNewVal = issue.project().fields()
      .stream()
      .filter(f -> f.isSameField(secondField))
      .findAny().get()
      .values()
      .filter(v -> !v.equals(secondOldVal))
      .findAny().get();
    new DefaultUpdateIssue(issue, session).fields(
      new HashMap<Field, FieldValue>() {
        {
          put(firstField, firstNewVal);
          put(secondField, secondNewVal);
        }
      }
    );
    assertNotEquals(
      issue.refresh().fields().stream()
        .filter(f -> f.isSameField(firstField))
        .map(AssignedField::value).findAny().get(),
      firstOldVal
    );
    assertNotEquals(
      issue.refresh().fields().stream()
        .filter(f -> f.isSameField(secondField))
        .map(AssignedField::value).findAny().get(),
      secondOldVal
    );
  }
}
