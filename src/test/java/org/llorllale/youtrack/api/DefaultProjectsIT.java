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

// @checkstyle AvoidStaticImport (2 lines)
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;
import org.apache.http.impl.client.HttpClients;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.session.Login;
import org.llorllale.youtrack.api.session.PermanentToken;

/**
 * Integration tests for {@link DefaultProjects}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.6.0
 * @checkstyle MethodName (500 lines)
 * @checkstyle AbbreviationAsWordInName (2 lines)
 */
public final class DefaultProjectsIT {
  private static IntegrationTestsConfig config;
  private static Login login;

  /**
   * Setup.
   * @throws Exception unexpected
   */
  @BeforeClass
  public static void setup() throws Exception {
    config = new IntegrationTestsConfig();
    login = new PermanentToken(
      config.youtrackUrl(),
      config.youtrackUserToken()
    );
  }

  /**
   * Checks that stream works insofar as to include the pre-existing project.
   * @throws Exception unexpected
   * @since 0.6.0
   */
  @Test
  public void testStream() throws Exception {
    assertTrue(
      new DefaultProjects(null, login, HttpClients::createDefault).stream()
        .anyMatch(p -> config.youtrackTestProjectId().equals(p.id()))
    );
  }

  /**
   * Should return pre-existing project.
   * @throws Exception unexpected
   * @since 0.6.0
   */
  @Test
  public void testGetExistingProject() throws Exception {
    assertTrue(
      new DefaultProjects(null, login, HttpClients::createDefault)
        .get(config.youtrackTestProjectId())
        .isPresent()
    );
  }

  /**
   * Should return an empty optional for an input that has not issues.
   * @throws Exception unexpected
   * @since 0.6.0
   */
  @Test
  public void testGetNonExistingProject() throws Exception {
    assertFalse(
      new DefaultProjects(null, login, HttpClients::createDefault)
        .get(String.valueOf(new Random(System.currentTimeMillis()).nextInt()))
        .isPresent()
    );
  }
}
