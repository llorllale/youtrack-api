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

package org.llorllale.youtrack.api;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;
import org.apache.http.HttpEntity;

/**
 * Applies a {@code mappingFunction} on an {@link HttpEntity} if the latter is present.
 * 
 * <p>Handy to filter "OK" responses from YouTrack that contain an "error" message in the payload
 * (like when you request an Issue by its ID and the server returns an OK response with an
 * error xml in the payload).
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @param <R> the resulting output type of the mapping function
 * @see StandardErrorCheck
 * @since 1.0.0
 */
final class MapIfNoError<R> implements ExceptionalSupplier<Optional<R>, IOException> {
  private final ExceptionalSupplier<Optional<HttpEntity>, IOException> supplier;
  private final ExceptionalFunction<String, R, IOException> mappingFunction;
  private final Predicate<String> condition;

  /**
   * Ctor.
   * 
   * @param supplier supplies the input HttpEntity
   * @param mappingFunction the function that will map the entity's contents to an object
   * @since 1.0.0
   */
  MapIfNoError(
      ExceptionalSupplier<Optional<HttpEntity>, IOException> supplier, 
      ExceptionalFunction<String, R, IOException> mappingFunction
  ) {
    this.supplier = supplier;
    this.mappingFunction = mappingFunction;
    this.condition = new StandardErrorCheck();
  }

  @Override
  public Optional<R> get() throws IOException {
    final Optional<R> result;
    final Optional<HttpEntity> entity = this.supplier.get();

    if (entity.isPresent()) {
      final String xml = new InputStreamAsString().apply(entity.get().getContent());

      if (this.condition.test(xml)) {
        result = Optional.of(this.mappingFunction.apply(xml));
      } else {
        result = Optional.empty();
      }
    } else {
      result = Optional.empty();
    }

    return result;
  }
}
