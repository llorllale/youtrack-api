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
import java.io.IOException;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.UncheckedIOException;
import org.junit.Test;

/**
 * Unit tests for {@link UncheckedIoFunction}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public final class UncheckedIoFunctionTest {
  /**
   * Returns the wrapped function's result.
   * @since 1.0.0
   */
  @Test
  public void returnsResult() {
    assertThat(
      new UncheckedIoFunction<>(e -> 123).apply("test"),
      is(123)
    );
  }

  /**
   * Wraps the IOException in an UncheckedIOException.
   * @since 1.0.0
   */
  @Test(expected = UncheckedIOException.class)
  public void wrapsUnchecksIoException() {
    new UncheckedIoFunction<>(
      e -> { throw new IOException(); }
    ).apply("test");
  }
}
