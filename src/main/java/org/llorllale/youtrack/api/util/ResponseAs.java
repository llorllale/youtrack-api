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
import org.llorllale.youtrack.api.util.response.ParseException;
import org.llorllale.youtrack.api.util.response.Response;

/**
 * Transforms a {@link Response} into an instance of type {@code T} if its 
 * {@link Response#httpResponse() httpResponse} complies with a given condition.
 * 
 * <p>YouTrack may return an error message in the response payload suggesting why the request 
 * couldn't be satisfied (like for instance, an issue of a requested ID does not exist). Since 
 * the contents of an http response's entity can only be read once, {@link ResponseAs} requires 
 * that the condition be of generic type String so that the payload is read once, tested, then 
 * acted upon if the condition is met.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @param <T> the generic type to transform the {@link Response} into
 * @since 1.0.0
 */
public final class ResponseAs<T> {
  private final Class<T> jaxb;
  private final Response response;
  private final Predicate<String> condition;

  /**
   * Ctor.
   * 
   * @param jaxb the type to transform the response into
   * @param condition the condition that must be met before transforming the response
   * @param response the response to transform
   * @since 1.0.0
   */
  public ResponseAs(Class<T> jaxb, Predicate<String> condition, Response response) {
    this.jaxb = jaxb;
    this.condition = condition;
    this.response = response;
  }

  /**
   * Uses the {@link StandardErrorCheck} condition by default.
   * 
   * @param jaxb the type to transform the response into
   * @param response the response to transform
   * @see #ResponseAs(Class, Predicate, Response) 
   * @since 1.0.0
   */
  public ResponseAs(Class<T> jaxb, Response response) {
    this(jaxb, new StandardErrorCheck(), response);
  }

  /**
   * An {@code Optional} describing the transformation's result. If the condition is not met then 
   * the resulting optional will be empty.
   * 
   * <p>Note: a null check on the payload is included for free.
   * 
   * @return the transformation's result
   * @throws ParseException from {@link XmlStringAsJaxb#apply(java.lang.String)}
   * @throws IOException from {@link InputStreamAsString#apply(java.io.InputStream)}
   * @see EmptyIfError
   * @see XmlStringAsJaxb
   * @since 1.0.0
   */
  public Optional<T> get() throws ParseException, IOException {
    return new OptionalMapping<>(
        () -> Optional.ofNullable(this.response.httpResponse().getEntity()),
        entity -> new EmptyIfError<>(new XmlStringAsJaxb<>(this.jaxb)).apply(entity)
    ).get().get();
  }
}
