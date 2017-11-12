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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.BeforeClass;
import org.llorllale.youtrack.api.jaxb.Sub;
import org.llorllale.youtrack.api.session.PermanentTokenLogin;
import org.llorllale.youtrack.api.session.Session;

/**
 * Integration tests for {@link DefaultUsersOfProject}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 */
public class DefaultUsersOfProjectIT {
  private static IntegrationTestsConfig config;
  private static Session session;
  private static Project project;

  @BeforeClass
  public static void setup() throws Exception {
    config = new IntegrationTestsConfig();
    session = new PermanentTokenLogin(config.youtrackUrl(), config.youtrackUserToken()).login();
    project = new DefaultYouTrack(session).projects().stream().findAny().get();
  }

  @Test
  public void testUser() throws Exception {
    assertThat(
        new DefaultUsersOfProject(project, session, jaxb("random"))
            .user(config.youtrackUser())
            .loginName(),
        is(config.youtrackUser())
    );
  }

  @Test
  public void testAssignees() throws Exception {
    assertTrue(
        new DefaultUsersOfProject(project, session, jaxb(config.youtrackUser()))
            .assignees()
            .anyMatch(a -> config.youtrackUser().equals(a.loginName()))
    );
  }

  private org.llorllale.youtrack.api.jaxb.Project jaxb(String assigneeLogin) {
    final org.llorllale.youtrack.api.jaxb.Project project = new org.llorllale.youtrack.api.jaxb.Project();
    final org.llorllale.youtrack.api.jaxb.Project.AssigneesLogin logins = new org.llorllale.youtrack.api.jaxb.Project.AssigneesLogin();
    final Sub sub = new Sub();
    sub.setValue(assigneeLogin);
    logins.getSub().add(sub);
    project.setAssigneesLogin(logins);
    return project;
  }
}