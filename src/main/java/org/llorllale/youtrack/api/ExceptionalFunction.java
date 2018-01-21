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

import java.util.function.Function;

/**
 * Generic function that can throw an {@link Exception}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @param <T> the input parameter type
 * @param <R> the output parameter type
 * @param <E> the exception type
 * @since 0.6.0
 */
interface ExceptionalFunction<T, R, E extends Exception> {
  /**
   * Applies this function to {@code input}, producing an output {@code R}.
   * 
   * @param input input argument
   * @return result of applying this function to the input
   * @throws E an exception 
   * @since 0.6.0
   */
  R apply(T input) throws E;

  /**
   * Returns a composed {@link ExceptionalFunction} that first applies itself to the input, then
   * applies {@code after} to the result.
   * 
   * @param <V> the type parameter for the resulting value
   * @param after the function to be called on the result of {@link #apply(java.lang.Object)}
   * @return a composed {@link ExceptionalFunction} that first applies itself to the input, then
   *     applies {@code after} to the result
   * @since 0.7.0
   */
  default <V> ExceptionalFunction<T, V, E> andThen(Function<R, V> after) {
    return (T input) -> after.apply(this.apply(input));
  }
}
