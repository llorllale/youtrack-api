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

import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.junit.BeforeClass;
import org.llorllale.youtrack.api.Issues.IssueSpec;
import org.llorllale.youtrack.api.session.PermanentTokenLogin;

/**
 * Integration tests for {@link XmlAssignedField}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 */
public class DefaultAssignedFieldIT {
  private static Issue issue;

  @BeforeClass
  public static void setup() throws Exception {
    final IntegrationTestsConfig config = new IntegrationTestsConfig();
    issue = new DefaultYouTrack(
        new PermanentTokenLogin(
            config.youtrackUrl(), 
            config.youtrackUserToken()
        ).login()
    ).projects()
        .stream()
        .findAny()
        .get()
        .issues()
        .create(new IssueSpec(DefaultAssignedFieldIT.class.getSimpleName(), "integration tests"));
  }

  @Test
  public void testChange() throws Exception {
    final XmlAssignedField field = (XmlAssignedField) issue.fields().stream().findFirst().get();
    final FieldValue other = issue.project().fields().stream()
        .filter(f -> field.isSameField(f))
        .findAny()
        .get()
        .values()
        .filter(v -> !v.isEqualTo(field.value()))
        .findAny()
        .get();

    assertFalse(
        field.change()
            .filter(v -> v.isEqualTo(other))
            .findAny().get().apply().fields().stream()
            .filter(f -> f.isSameField(field))
            .findAny().get()
            .value()
            .isEqualTo(other)
    );
  }
}