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

package org.llorllale.youtrack.api.util;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Applies a given {@link ExceptionalFunction function} if some condition on the function's
 * input is met.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @param <T> the type parameter for the input
 * @param <R> the type parameter for the result
 * @param <E> the type parameter for the exception thrown
 * @since 0.6.0
 */
public final class ApplyIf<T, R, E extends Exception> 
    implements ExceptionalFunction<T, Optional<R>, E> {
  private final Predicate<T> condition;
  private final ExceptionalFunction<T, R, E> function;

  /**
   * Ctor.
   * 
   * @param condition the condition that needs to be met
   * @param function the function to apply if {@code condition} is met
   * @since 0.6.0
   */
  public ApplyIf(Predicate<T> condition, ExceptionalFunction<T, R, E> function) {
    this.condition = condition;
    this.function = function;
  }

  @Override
  public Optional<R> apply(T input) throws E {
    final Optional<R> result;

    if (this.condition.test(input)) {
      result = Optional.of(this.function.apply(input));
    } else {
      result = Optional.empty();
    }

    return result;
  }
}
