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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * Loads configurations required for integration tests.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
public final class IntegrationTestsConfig {
  private static final String CONFIG_FILE
    = "/integration-tests-config.properties";
  private static final Properties CONFIG = new Properties();

  /**
   * Loads test config from a properties file.
   */
  private static synchronized void loadConfig() {
    if (CONFIG.isEmpty()) {
      // @checkstyle LineLength (1 line)
      try (InputStream input = IntegrationTestsConfig.class.getResourceAsStream(CONFIG_FILE)) {
        CONFIG.load(input);
      } catch (IOException e) {
        throw new RuntimeException("Missing integration-tests configuration!", e);
      }
    }
  }

  /**
   * The username for the dockerzied YouTrack service.
   * @return The username for the dockerzied YouTrack service.
   */
  public String youtrackUser() {
    loadConfig();
    return CONFIG.getProperty("youtrack.test.user");
  }

  /**
   * The password credentials for the dockerized YouTrack service.
   * @return The password credentials for the dockerized YouTrack service.
   * @since 0.1.0
   */
  public char[] youtrackPwd() {
    loadConfig();
    return CONFIG.getProperty("youtrack.test.pwd").toCharArray();
  }

  /**
   * The endpoint URL of the dockerized YouTrack service.
   * @return The endpoint URL of the dockerized YouTrack service.
   * @since 0.1.0
   */
  public URL youtrackUrl() {
    final String property = "youtrack.test.url";
    loadConfig();
    try {
      return new URL(CONFIG.getProperty(property));
    } catch (MalformedURLException e) {
      throw new RuntimeException("Malformed URL: " + CONFIG.getProperty(property));
    }
  }

  /**
   * The permanent token for the {@link #youtrackUser() user}.
   * @return the user's token
   * @since 0.3.0
   */
  public String youtrackUserToken() {
    loadConfig();
    return CONFIG.getProperty("youtrack.test.user.token");
  }

  /**
   * The ID of the pre-created YouTrack project.
   * @return the pre-configured project's id
   * @since 0.3.0
   */
  @SuppressWarnings("checkstyle:MethodName")
  public String youtrackTestProjectId() {
    loadConfig();
    return CONFIG.getProperty("youtrack.test.project.id");
  }
}
