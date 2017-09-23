/*
 * Copyright 2017 George Aristy.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.llorllale.youtrack.api.util.response;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.InputStreamAsString;

import java.io.IOException;

/**
 * A {@link Response} that throws an {@link IOException} if the server responds with code
 * 500.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public class InternalServerErrorResponse implements Response {
  private final Response base;

  /**
   * Ctor.
   * @param base the next link in the chain
   * @since 0.4.0
   */
  public InternalServerErrorResponse(Response base) {
    this.base = base;
  }

  @Override
  public HttpResponse asHttpResponse() throws IOException, UnauthorizedException {
    if (base.asHttpResponse().getStatusLine().getStatusCode() 
        == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
      throw new IOException(
          String.format(
              "500 Internal Server error. Payload: %s", 
              new InputStreamAsString(
                  base.asHttpResponse().getEntity().getContent()
              ).string()
          )
      );
    }

    return base.asHttpResponse();
  }
}