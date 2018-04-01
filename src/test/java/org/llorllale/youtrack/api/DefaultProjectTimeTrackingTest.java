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

// @checkstyle AvoidStaticImport (3 lines)
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import java.util.stream.Collectors;
import org.junit.Test;
import org.llorllale.youtrack.api.mock.MockProject;
import org.llorllale.youtrack.api.mock.http.MockHttpClient;
import org.llorllale.youtrack.api.mock.http.MockSession;
import org.llorllale.youtrack.api.mock.http.response.MockOkResponse;

/**
 * Unit tests for {@link DefaultProjectTimeTracking}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 * @checkstyle MultipleStringLiterals (500 lines)
 * @checkstyle MethodName (500 lines)
 */
public final class DefaultProjectTimeTrackingTest {
  /**
   * {@link DefaultProjectTimeTracking#project()} must return the same project.
   * @since 1.0.0
   */
  @Test
  public void project() {
    final Project project = new MockProject();
    assertThat(
      new DefaultProjectTimeTracking(project, new MockSession()).project(),
      is(project)
    );
  }

  /**
   * {@link DefaultProjectTimeTracking#enabled()} must be true if timetracking is enabled, and both
   * an estimation field and a spent time field have been configured.
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void enabled() throws Exception {
    assertThat(
      new DefaultProjectTimeTracking(
        new MockProject(),
        new MockSession(),
        new MockHttpClient(
          new MockOkResponse(
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
            + "<settings enabled=\"true\">\n"
            + "  <estimation name=\"Estimation\"\n"
            // @checkstyle LineLength (1 line)
            + "              url=\"http://***.myjetbrains.com/youtrack/rest/admin/project/{projectId}/customfield/Estimation\"/>\n"
            + "  <spentTime name=\"Spent time\"\n"
            // @checkstyle LineLength (1 line)
            + "              url=\"http://***.myjetbrains.com/youtrack/rest/admin/project/{projectId}/customfield/Spent%20time\"/>\n"
            + "</settings>"
          )
        )
      ).enabled(),
      is(true)
    );
  }

  /**
   * {@link DefaultProjectTimeTracking#enabled()} must be false if settings is disabled, even if an
   * estimation field and a spent time field have been configured.
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void enabledWithDisabledSettingsAndWithEstimationAndTimeSpent() throws Exception {
    assertThat(
      new DefaultProjectTimeTracking(
        new MockProject(),
        new MockSession(),
        new MockHttpClient(
          new MockOkResponse(
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
            + "<settings enabled=\"false\">\n"
            + "  <estimation name=\"Estimation\"\n"
            // @checkstyle LineLength (1 line)
            + "              url=\"http://***.myjetbrains.com/youtrack/rest/admin/project/{projectId}/customfield/Estimation\"/>\n"
            + "  <spentTime name=\"Spent time\"\n"
            // @checkstyle LineLength (1 line)
            + "              url=\"http://***.myjetbrains.com/youtrack/rest/admin/project/{projectId}/customfield/Spent%20time\"/>\n"
            + "</settings>"
          )
        )
      ).enabled(),
      is(false)
    );
  }

  /**
   * {@link DefaultProjectTimeTracking#enabled()} must be false if an estimation field has not been
   * configured, even if settings are enabled and a spent time field has been configured.
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void enabledWithEnabledSettingsAndWithoutEstimation() throws Exception {
    assertThat(
      new DefaultProjectTimeTracking(
        new MockProject(),
        new MockSession(),
        new MockHttpClient(
          new MockOkResponse(
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
            + "<settings enabled=\"true\">\n"
            + "  <spentTime name=\"Spent time\"\n"
            // @checkstyle LineLength (1 line)
            + "             url=\"http://***.myjetbrains.com/youtrack/rest/admin/project/{projectId}/customfield/Spent%20time\"/>\n"
            + "</settings>"
          )
        )
      ).enabled(),
      is(false)
    );
  }

  /**
   * {@link DefaultProjectTimeTracking#enabled()} must be false if a spent time field has not been
   * configured, even if settings are enabled and an estimation field has been configured.
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void enabledWithEnabledSettingsAndWithoutTimeSpent() throws Exception {
    assertThat(
      new DefaultProjectTimeTracking(
        new MockProject(),
        new MockSession(),
        new MockHttpClient(
          new MockOkResponse(
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
            + "<settings enabled=\"true\">\n"
            + "  <estimation name=\"Estimation\"\n"
            // @checkstyle LineLength (1 line)
            + "              url=\"http://***.myjetbrains.com/youtrack/rest/admin/project/{projectId}/customfield/Estimation\"/>\n"
            + "</settings>"
          )
        )
      ).enabled(),
      is(false)
    );
  }

  /**
   * {@link DefaultProjectTimeTracking#enabled()} must be false if neither a spent time nor an
   * estimation field have been configured, even if settings are enabled.
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void enabledWithEnabledSettingsAndWithoutEstimationNorTimeSpent() throws Exception {
    assertThat(
      new DefaultProjectTimeTracking(
        new MockProject(),
        new MockSession(),
        new MockHttpClient(
          new MockOkResponse(
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
            + "<settings enabled=\"true\">\n"
            + "</settings>"
          )
        )
      ).enabled(),
      is(false)
    );
  }

  /**
   * {@link DefaultProjectTimeTracking#types()} must return all the workItemTypes returned by the
   * server.
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void testTypes() throws Exception {
    assertThat(
      new DefaultProjectTimeTracking(
        new MockProject(),
        new MockSession(),
        new MockHttpClient(
          new MockOkResponse(
            "<?xml version=\"1.0\"?>\n"
            + "<workItemTypes>\n"
            + "  <workType>\n"
            + "    <name>Development</name>\n"
            + "    <id>201-0</id>\n"
            + "    <url>http://example.net/rest/admin/timetracking/worktype/201-0</url>\n"
            + "  </workType>\n"
            + "  <workType>\n"
            + "    <name>Testing</name>\n"
            + "    <id>201-1</id>\n"
            + "    <url>http://example.net/rest/admin/timetracking/worktype/201-1</url>\n"
            + "  </workType>\n"
            + "  <workType>\n"
            + "    <name>Documentation</name>\n"
            + "    <id>201-2</id>\n"
            + "    <url>http://example.net/rest/admin/timetracking/worktype/201-2</url>\n"
            + "  </workType>\n"
            + "</workItemTypes>"
          )
        )
      ).types().map(TimeTrackEntryType::asString).collect(Collectors.toList()),
      containsInAnyOrder("Development", "Testing", "Documentation")
    );
  }
}
