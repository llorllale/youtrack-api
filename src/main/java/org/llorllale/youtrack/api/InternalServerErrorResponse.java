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
 * A {@link Response} that throws an {@link IOException} if the server responds with code
 * 500.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
final class InternalServerErrorResponse implements Response {
  private final Response base;

  /**
   * Ctor.
   * 
   * @param base the next link in the chain
   * @since 0.4.0
   */
  InternalServerErrorResponse(Response base) {
    this.base = base;
  }

  @Override
  public HttpResponse httpResponse() throws IOException, UnauthorizedException {
    if (this.base.httpResponse().getStatusLine().getStatusCode() 
        == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
      throw new IOException("500 Internal Server error");
    }

    return this.base.httpResponse();
  }
}
