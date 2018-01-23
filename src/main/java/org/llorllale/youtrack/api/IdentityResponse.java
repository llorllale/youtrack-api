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
 * Special {@link Response} that does no validation and just returns the {@link HttpResponse}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
final class IdentityResponse implements Response {
  private final HttpResponse httpResponse;

  /**
   * Ctor.
   * 
   * @param httpResponse the http response
   * @since 0.4.0
   */
  IdentityResponse(HttpResponse httpResponse) {
    this.httpResponse = httpResponse;
  }

  @Override
  public HttpResponse httpResponse() throws IOException, UnauthorizedException {
    return this.httpResponse;
  }
}
