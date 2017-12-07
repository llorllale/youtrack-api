/*
 * Copyright 2017 George Aristy.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.llorllale.youtrack.api;

import java.io.IOException;

/**
 * Handy class to map an input to an output.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @param <T> the input type
 * @param <R> the output type
 * @since 1.0.0
 */
final class Mapping<T, R> implements ExceptionalSupplier<R, IOException> {
  private final ExceptionalSupplier<T, IOException> input;
  private final ExceptionalFunction<T, R, IOException> mappingFunction;

  /**
   * Ctor.
   * 
   * @param input supplies the input argument
   * @param mappingFunction the function to map {@code input} into the output
   * @since 1.0.0
   */
  Mapping(
      ExceptionalSupplier<T, IOException> input, 
      ExceptionalFunction<T, R, IOException> mappingFunction
  ) {
    this.input = input;
    this.mappingFunction = mappingFunction;
  }

  /**
   * Applies the mappingFunction to the input and returns the result.
   * 
   * @return the result of applying the mapping function to the input
   * @throws IOException from the {@link ExceptionalFunction mappingFunction}
   * @since 1.0.0
   */
  @Override
  public R get() throws IOException {
    return this.mappingFunction.apply(this.input.get());
  }
}
