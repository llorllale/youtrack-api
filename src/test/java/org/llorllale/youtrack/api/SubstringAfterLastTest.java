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
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Unit tests for {@link SubstringAfterLast}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public final class SubstringAfterLastTest {
  /**
   * Returns the substring after last occurrence of the given delimiter.
   */
  @Test
  public void testGet() {
    assertThat(
      new SubstringAfterLast("http://some.host.com:8080/some/path/to/some/resource", "/").get(),
      is("resource")
    );
  }
}
