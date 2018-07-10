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

// @checkstyle AvoidStaticImport (1 lines)
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.youtrack.api.mock.MockLogin;
import org.llorllale.youtrack.api.mock.MockProject;
import org.llorllale.youtrack.api.mock.http.MockHttpClient;
import org.llorllale.youtrack.api.mock.http.response.MockOkResponse;

/**
 * Unit tests for {@link DefaultFields}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public final class DefaultFieldsTest {
  /**
   * {@link DefaultFields#project()} must return the same project.
   * @since 1.0.0
   */
  @Test
  public void project() {
    final Project project = new MockProject();
    assertThat(
      new DefaultFields(null, project, null).project(),
      new IsEqual<>(project)
    );
  }

  /**
   * {@link DefaultFields#stream()} must return all fields returned by the
   * server.
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void stream() throws Exception {
    assertThat(
      new DefaultFields(
        new MockLogin(),
        new MockProject(),
        new MockHttpClient(
          new MockOkResponse(
            "<projectCustomFieldRefs>\n"
            // @checkstyle LineLength (2 lines)
            + "  <projectCustomField name=\"Pyatiletka\" url=\"http://youtrack.jetbrains.net/rest/admin/project/CMN/customfield/Plan\"/>\n"
            + "  <projectCustomField name=\"Region\" url=\"http://youtrack.jetbrains.net/rest/admin/project/CMN/customfield/Region\"/>\n"
            + "</projectCustomFieldRefs>"
          )
        )
      ).stream().map(ProjectField::name).collect(Collectors.toList()),
      new IsIterableContainingInAnyOrder<>(
        Arrays.asList(
          new IsEqual<>("Pyatiletka"),
          new IsEqual<>("Region")
        )
      )
    );
  }
}
