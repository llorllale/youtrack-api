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
import org.apache.http.HttpResponse;

import org.llorllale.youtrack.api.session.UnauthorizedException;

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
final class HttpResponseAsResponse implements Response {
  private final Response base;

  /**
   * Adapts the given {@code httpResponse} into a {@link Response}.
   * @param httpResponse the {@link HttpResponse} to be adapted
   * @since 0.1.0
   */
  HttpResponseAsResponse(HttpResponse httpResponse) {
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
  public HttpResponse httpResponse() throws UnauthorizedException, IOException {
    return this.base.httpResponse();
  }
}
