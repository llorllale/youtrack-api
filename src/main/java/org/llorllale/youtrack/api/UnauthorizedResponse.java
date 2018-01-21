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
import org.apache.http.HttpStatus;

import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Handles the case when the HTTP status code is {@code 401}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
final class UnauthorizedResponse implements Response {
  private final Response base;

  /**
   * Ctor.
   * 
   * @param base the next link in the chain
   * @since 0.1.0
   */
  UnauthorizedResponse(Response base) {
    this.base = base;
  }
  
  @Override
  public HttpResponse httpResponse() throws UnauthorizedException, IOException {
    if (this.base.httpResponse().getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
      throw new UnauthorizedException("401: Unauthorized");
    } else {
      return this.base.httpResponse();
    }
  }
}
