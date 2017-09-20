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

package org.llorllale.youtrack.api.response;

import org.apache.http.HttpResponse;
import org.llorllale.youtrack.api.session.UnauthorizedException;

import java.io.IOException;

/**
 * Throws an {@link UnauthorizedException} if status error code {@code 403} is received from 
 * YouTrack.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
public class ForbiddenResponse implements Response {
  private final Response base;

  /**
   * Ctor.
   * @param base the next link in the chain
   * @since 0.1.0
   * @see HttpResponseAsResponse
   */
  public ForbiddenResponse(Response base) {
    this.base = base;
  }

  @Override
  public HttpResponse asHttpResponse() throws UnauthorizedException, IOException {
    if (base.asHttpResponse().getStatusLine().getStatusCode() == 403) {
      throw new UnauthorizedException(
          "403: Forbidden", 
          base.asHttpResponse()
      );
    } else {
      return base.asHttpResponse();
    }
  }
}
