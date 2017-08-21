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

package org.llorllale.youtrack.api.projects;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.IntegrationTestsConfig;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UsernamePasswordLogin;

/**
 * Integration tests for workflows related to {@link Project projects}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.2.0
 */
public class ProjectsIT {
  private static Session session;
  
  @BeforeClass
  public static void setUpClass() throws Exception {
    final IntegrationTestsConfig config = new IntegrationTestsConfig();
    session = new UsernamePasswordLogin(
        config.youtrackUrl(), 
        config.youtrackUser(), 
        config.youtrackPwd()
    ).login();
  }

  /**
   * There is a pre-configured project in the test docker image of YouTrack with ID "TP".
   * @throws Exception 
   * @since 0.2.0
   */
  @Test
  public void getAllProjects() throws Exception {
    assertThat(
        new AllProjects(session).query(),
        is(not(empty()))
    );
  }

  /**
   * Test that {@link ProjectWithId} can retrieve the test project.
   * @throws Exception 
   * @since 0.2.0
   */
  @Test
  public void getProjectById() throws Exception {
    assertThat(
        new ProjectWithId("TP", session).query().get().name(),
        is("Test Project")
    );
  }
}
