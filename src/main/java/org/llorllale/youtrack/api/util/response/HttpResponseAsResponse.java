/* 
 * Copyright 2017 George Aristy (george.aristy@gmail.com).
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

package org.llorllale.youtrack.api.util.response;

import static java.util.Objects.nonNull;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.ApplyIf;
import org.llorllale.youtrack.api.util.ExceptionalFunction;
import org.llorllale.youtrack.api.util.InputStreamAsString;
import org.llorllale.youtrack.api.util.StandardErrorCheck;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * <p>
 * {@link HttpResponse} -&gt; {@link Response} adapter class.
 * </p>
 * 
 * <p>
 * Client code should only have to rely on this implementation of 
 * {@link Response}.
 * </p>
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
public class HttpResponseAsResponse implements Response {
  private final Response base;

  /**
   * Adapts the given {@code httpResponse} into a {@link Response}.
   * @param httpResponse the {@link HttpResponse} to be adapted
   * @since 0.1.0
   */
  public HttpResponseAsResponse(HttpResponse httpResponse) {
    this.base = 
        new UnauthorizedResponse(
            new ForbiddenResponse(
                new InternalServerErrorResponse(
                    new BadRequest(
                        new IdentityResponse(
                            httpResponse
                        )
                    )
                )
            )
        );
  }

  @Override
  public HttpResponse asHttpResponse() throws UnauthorizedException, IOException {
    return this.base.asHttpResponse();
  }

  /**
   * Extracts the text contents of the {@link HttpEntity} if present on the underlying 
   * {@link HttpResponse}, then applies the {@code function} on the result if it matches 
   * the {@code condition}.
   * 
   * <p>Note: the underlying {@link HttpEntity} is retrieved by calling {@link #asHttpResponse()}
   * on this object, therefore all validations are carried through to this method.</p>
   * 
   * @param <R> the resulting type parameter of the {@code function}
   * @param <E> the type of the exception thrown by {@code function}
   * @param function transforms the text contents of the {@link HttpEntity} into something useful
   * @param condition if an {@link HttpEntity} is present, {@code function} is applied only if 
   *     the contents of the entity do not match this condition
   * @return the result of applying {@code function} on the text contents of the {@link HttpEntity}
   * @throws IOException if the server is unavailable or if there's an error while buffering the
   *     contents of the underlying {@link HttpEntity}
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform
   *     some operation
   * @throws E the exception declared by {@code function}
   * @since 0.6.0
   * @see #asHttpResponse() 
   * @see StandardErrorCheck
   */
  public <R,E extends Exception> Optional<R> applyOnEntity(
      ExceptionalFunction<String,R,E> function,
      Predicate<String> condition
  ) throws IOException, UnauthorizedException, E {
    final Optional<R> result;

    if (nonNull(this.asHttpResponse().getEntity())) {
      result = new ApplyIf<>(condition,function)
          .apply(
              new InputStreamAsString().apply(
                  this.asHttpResponse().getEntity().getContent()
              )
          );
    } else {
      result = Optional.empty();
    }

    return result;
  }
}
