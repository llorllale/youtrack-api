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

// @checkstyle AvoidStaticImport (1 line)
import static org.junit.Assert.assertFalse;

import java.util.Collections;
import java.util.NoSuchElementException;
import org.junit.Test;
import org.llorllale.youtrack.api.mock.http.MockHttpClient;
import org.llorllale.youtrack.api.mock.http.MockThrowingHttpClient;
import org.llorllale.youtrack.api.mock.http.response.MockOkResponse;

/**
 * Unit tests for {@link Page}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 * @checkstyle MethodName (500 lines)
 */
public final class PageTest {
  /**
   * Ctor must wrap IOExceptions in UncheckedException.
   * @since 1.0.0
   */
  @Test(expected = UncheckedException.class)
  public void ctorUncheckedExceptionThrow() {
    new Page<>(
      null,
      r -> Collections.<String>emptyList(),
      new MockThrowingHttpClient()
    );
  }

  /**
   * next() must throw NoSuchElementException if contents is empty.
   * @since 1.0.0
   */
  @Test(expected = NoSuchElementException.class)
  public void nextNoSuchElementException() {
    new Page<>(
      null,
      r -> Collections.<String>emptyList(),
      new MockHttpClient(new MockOkResponse())
    ).next();
  }

  /**
   * Page.Empty.hasNext() should always return {@code false}.
   * @since 1.0.0
   */
  @Test
  public void emptyHasNextAlwaysFalse() {
    assertFalse(
      new Page.Empty<>().hasNext()
    );
  }

  /**
   * Page.Empty.next() should always throw {@link NoSuchElementException}.
   * @since 1.0.0
   */
  @Test(expected = NoSuchElementException.class)
  public void emptyNextAlwaysThrowsNoSuchElementException() {
    new Page.Empty<>().next();
  }
}
