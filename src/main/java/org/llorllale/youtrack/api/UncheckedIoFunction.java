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
import java.io.UncheckedIOException;
import java.util.function.Function;

/**
 * Wraps {@link IOException} thrown by an {@link ExceptionalFunction} in an
 * {@link UncheckedIOException}.
 * @author George Aristy (george.aristy@gmail.com)
 * @param <I> the input type
 * @param <O> the output type
 * @since 1.0.0
 */
final class UncheckedIoFunction<I, O> implements Function<I, O> {
  private final ExceptionalFunction<I, O, ? extends IOException> origin;

  /**
   * Ctor.
   * @param origin function to wrap
   */
  UncheckedIoFunction(ExceptionalFunction<I, O, ? extends IOException> origin) {
    this.origin = origin;
  }

  @Override
  public O apply(I input) {
    try {
      return this.origin.apply(input);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
