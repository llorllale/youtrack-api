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

package org.llorllale.youtrack.api.session;

// @checkstyle AvoidStaticImport (2 lines)
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Unit tests for {@link DefaultCookie}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public final class DefaultCookieTest {
  /**
   * {@link DefaultCookie#name()} should return the name passed through constructor.
   * @since 1.0.0
   * @checkstyle MultipleStringLiterals (500 lines)
   */
  @Test
  public void testName() {
    final String name = "SomeName";
    assertThat(
      new DefaultCookie(name, "").name(),
      is(name)
    );
  }

  /**
   * {@link DefaultCookie#value()} must return the value passed through the constructor.
   * @since 1.0.0
   */
  @Test
  public void testValue() {
    final String value = "SomeValue";
    assertThat(
      new DefaultCookie("", value).value(),
      is(value)
    );
  }
}
