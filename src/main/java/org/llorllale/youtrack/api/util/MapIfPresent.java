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

import java.io.IOException;
import java.util.Optional;

/**
 * Handy class, similar to {@link Mapping}, but that conditionally maps an object of type 
 * {@code T} into one of type {@code R}.
 * 
 * <p>Users must provide an {@link ExceptionalSupplier supplier} that produces an optional 
 * describing some type {@code T}. If the optional is not empty then the {@code mappingFunction} 
 * is used to map the contents of this optional into an instance of the final type {@code R}. 
 * 
 * <p>Pseudo code:
 * <pre>
 * {@code 
 *   final Optional<T> tmp = supplier.get();
 * 
 *   if (tmp.isPresent()) {
 *     return Optional.of(mappingFunction.apply(tmp.get()));
 *   } else {
 *     return Optional.empty();
 *   }
 * }
 * </pre>
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @param <T> the supplier's type
 * @param <R> the final output type
 * @see Mapping
 * @since 1.0.0
 */
public final class MapIfPresent<T, R> implements ExceptionalSupplier<Optional<R>, IOException> {
  private final ExceptionalSupplier<Optional<T>, IOException> supplier;
  private final ExceptionalFunction<T, R, IOException> mappingFunction;

  /**
   * Ctor.
   * 
   * @param supplier the supplier of instances of {@code T}
   * @param mappingFunction the final mapping function
   * @since 1.0.0
   */
  public MapIfPresent(
      ExceptionalSupplier<Optional<T>, IOException> supplier, 
      ExceptionalFunction<T, R, IOException> mappingFunction
  ) {
    this.supplier = supplier;
    this.mappingFunction = mappingFunction;
  }

  /**
   * Applies the rules for transformation and returns the result.
   * 
   * @return the resulting transformed object
   * @throws java.io.IOException from the {@code mappingFunction}
   * @since 1.0.0
   */
  @Override
  public Optional<R> get() throws IOException {
    final Optional<R> result;
    final Optional<T> tmp = this.supplier.get();

    if (tmp.isPresent()) {
      result = Optional.of(this.mappingFunction.apply(tmp.get()));
    } else {
      result = Optional.empty();
    }

    return result;
  }
}
