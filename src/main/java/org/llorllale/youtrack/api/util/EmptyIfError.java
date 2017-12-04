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
import java.util.function.Predicate;
import org.apache.http.HttpEntity;

/**
 * Applies a {@code mappingFunction} on an {@link HttpEntity} if the 
 * {@link HttpEntity#getContent() contents} do not correspond to an error from the YouTrack server.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @param <R> the resulting output type of the mapping function
 * @see StandardErrorCheck
 * @since 1.0.0
 */
public final class EmptyIfError<R> implements ExceptionalFunction<HttpEntity, Optional<R>, IOException> {
  private final ExceptionalFunction<String, R, ? extends IOException> mappingFunction;
  private final Predicate<String> condition;

  /**
   * Ctor.
   * 
   * @param mappingFunction the function that will map the entity's contents to an object
   * @since 1.0.0
   */
  public EmptyIfError(ExceptionalFunction<String, R, ? extends IOException> mappingFunction) {
    this.mappingFunction = mappingFunction;
    this.condition = new StandardErrorCheck();
  }

  @Override
  public Optional<R> apply(HttpEntity input) throws IOException {
    final Optional<R> result;
    final String xml = new InputStreamAsString().apply(input.getContent());

    if (this.condition.test(xml)) {
      result = Optional.of(this.mappingFunction.apply(xml));
    } else {
      result = Optional.empty();
    }

    return result;
  }
}
