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
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.session.PermanentTokenLogin;
import org.llorllale.youtrack.api.session.Session;

/**
 * Integration tests for {@link DefaultStates}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.7.0
 */
public class DefaultStatesIT {
  private static Session session;
  private static Project project;

  @BeforeClass
  public static void setup() throws Exception {
    final IntegrationTestsConfig config = new IntegrationTestsConfig();
    session = new PermanentTokenLogin(config.youtrackUrl(), config.youtrackUserToken()).login();
    project = new DefaultYouTrack(session).projects().stream().findAny().get();
  }

  @Test
  public void testStream() throws Exception {
    assertThat(
        new DefaultStates(project, session).stream().count(),
        is(greaterThan(0L))
    );
  }

  @Test
  public void testResolving() throws Exception {
    assertThat(
        new DefaultStates(project, session).resolving().count(),
        is(greaterThan(0L))
    );
  }
}