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

import java.util.Random;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.session.PermanentTokenLogin;
import org.llorllale.youtrack.api.session.Session;

/**
 * Integration tests for {@link DefaultProjects}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.6.0
 */
public class DefaultProjectsIT {
  private static IntegrationTestsConfig config;
  private static Session session;

  @BeforeClass
  public static void setup() throws Exception {
    config = new IntegrationTestsConfig();
    session = new PermanentTokenLogin(
        config.youtrackUrl(), 
        config.youtrackUserToken()
    ).login();
  }

  /**
   * Checks that stream works insofar as to include the pre-existing project.
   * @throws Exception 
   * @since 0.6.0
   */
  @Test
  public void testStream() throws Exception {
    assertTrue(
        new DefaultProjects(null, session).stream()
            .anyMatch(p -> config.youtrackTestProjectId().equals(p.id()))
    );
  }

  /**
   * Should return pre-existing project.
   * @throws Exception 
   * @since 0.6.0
   */
  @Test
  public void testGetExistingProject() throws Exception {
    assertTrue(
        new DefaultProjects(null, session)
            .get(config.youtrackTestProjectId())
            .isPresent()
    );
  }

  /**
   * Should return an empty optional for an input that has not issues.
   * @throws Exception 
   * @since 0.6.0
   */
  @Test
  public void testGetNonExistingProject() throws Exception {
    assertFalse(
        new DefaultProjects(null, session)
            .get(String.valueOf(new Random(System.currentTimeMillis()).nextInt()))
            .isPresent()
    );
  }
}