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

import java.util.HashMap;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import org.junit.BeforeClass;
import org.llorllale.youtrack.api.session.PermanentTokenLogin;
import org.llorllale.youtrack.api.session.Session;

/**
 * Integration tests for {@link DefaultUpdateIssue}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.9.0
 */
public class DefaultUpdateIssueIT {
  private static IntegrationTestsConfig config;
  private static Session session;
  private static Issue issue;

  @BeforeClass
  public static void setup() throws Exception {
    config = new IntegrationTestsConfig();
    session = new PermanentTokenLogin(config.youtrackUrl(), config.youtrackUserToken()).login();
    issue = new DefaultYouTrack(session).projects().stream()
        .findAny()
        .get()
        .issues()
        .create(
            DefaultUpdateIssueIT.class.getSimpleName(), 
            "integration tests"
        );
  }
  @Test
  public void testSummary() throws Exception {
    final String newSummary = DefaultUpdateIssueIT.class.getSimpleName().concat(".testSummary");
    assertNotEquals(
        new DefaultUpdateIssue(issue, session).summary(newSummary).summary(),
        issue.summary()
    );
  }

  @Test
  public void testDescription() throws Exception {
    final String newDescription = DefaultUpdateIssueIT.class.getSimpleName().concat("testDescription");
    assertNotEquals(
        new DefaultUpdateIssue(issue, session).description(newDescription).description(),
        issue.description()
    );
  }

  @Test
  public void testSummaryAndDesc() throws Exception {
    final String newSummary = DefaultUpdateIssueIT.class.getSimpleName().concat("testSummaryAndDesc_summ");
    final String newDescription = DefaultUpdateIssueIT.class.getSimpleName().concat("testSummaryAndDesc_desc");
    final Issue newIssue = new DefaultUpdateIssue(issue, session).summaryAndDesc(newSummary, newDescription);

    assertNotEquals(issue.summary(), newIssue.summary());
    assertNotEquals(issue.description(), newIssue.description());
  }

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
        new DefaultUpdateIssue(issue, session).field(field, newValue).fields().stream()
            .filter(f -> f.isSameField(field))
            .map(AssignedField::value).findAny().get(),
        oldValue
    );
  }

  @Test
  public void testFields() throws Exception {
    final AssignedField field1 = issue.fields().stream().findAny().get();
    final FieldValue oldValue1 = field1.value();
    final FieldValue newValue1 = issue.project().fields()
        .stream()
        .filter(f -> f.isSameField(field1))
        .findAny().get()
        .values()
        .filter(v -> !v.equals(oldValue1))
        .findAny().get();
    final AssignedField field2 = issue.fields().stream().findAny().get();
    final FieldValue oldValue2 = field2.value();
    final FieldValue newValue2 = issue.project().fields()
        .stream()
        .filter(f -> f.isSameField(field2))
        .findAny().get()
        .values()
        .filter(v -> !v.equals(oldValue2))
        .findAny().get();

    new DefaultUpdateIssue(issue, session).fields(
        new HashMap<Field, FieldValue>(){{
            put(field1, newValue1);
            put(field2, newValue2);
        }}
    );

    assertNotEquals(
        issue.refresh().fields().stream()
            .filter(f -> f.isSameField(field1))
            .map(AssignedField::value).findAny().get(),
        oldValue1
    );

    assertNotEquals(
        issue.refresh().fields().stream()
            .filter(f -> f.isSameField(field2))
            .map(AssignedField::value).findAny().get(),
        oldValue2
    );
  }
}