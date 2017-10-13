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
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.Issues.IssueSpec;
import org.llorllale.youtrack.api.session.PermanentTokenLogin;
import org.llorllale.youtrack.api.session.Session;

/**
 * Integration tests for {@link XmlIssue}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.7.0
 */
public class XmlIssueIT {
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
            new IssueSpec(
                XmlIssueIT.class.getSimpleName().concat(".testUpdateAndRefresh"), 
                "integration tests"
            )
        );
  }

  /**
   * Test of the 
   * {@link Issue#update(org.llorllale.youtrack.api.Field, org.llorllale.youtrack.api.FieldValue) single update} 
   * method (the refresh operation is implicit).
   * 
   * @throws Exception 
   * @since 0.7.0
   */
  @Test
  public void testSingleUpdateAndRefresh() throws Exception {
    final Field field = new BasicField("Assignee", issue.project());

    assertThat(
      new XmlIssue((XmlIssue) issue).update(field, new BasicFieldValue(config.youtrackUser(), field))
          .users().assignee().get().loginName(),
        is(config.youtrackUser())
    );
  }

  /**
   * Test of the 
   * {@link Issue#update(org.llorllale.youtrack.api.Field, org.llorllale.youtrack.api.FieldValue) single update} 
   * method (the refresh operation is implicit).
   * 
   * @throws Exception 
   * @since 0.8.0
   */
  @Test
  public void testMultiUpdateAndRefresh() throws Exception {
    final Field f1 = new BasicField("Assignee", issue.project());   
    final FieldValue v1 = new BasicFieldValue(config.youtrackUser(), f1);
    final Field f2 = new BasicField("State", issue.project());
    final FieldValue v2 = new BasicFieldValue("Closed", f2);

//    final Issue i = new XmlIssue((XmlIssue) issue).update(
//        new HashMap<Field, FieldValue>(){{put(f1,v1); put(f2,v2);}}
//    );

    final Issue i = new XmlIssue((XmlIssue) issue).update(f1, v1).update(f2, v2);

    assertTrue(
        i.fields().stream().anyMatch(f -> f.equals(f1) && f.value().equals(v1))
    );

    assertTrue(
        i.fields().stream().anyMatch(f -> f.equals(f2) && f.value().equals(v2))
    );
  }
}