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

package org.llorllale.youtrack.api.util.response;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import org.llorllale.youtrack.api.session.UnauthorizedException;
import org.llorllale.youtrack.api.util.InputStreamAsString;

/**
 * Throws an {@link IOException} if the server responds with {@code 400 Bad Request}.
 * 
 * <p>Note: 'Bad Request' should never happen :-)</p>
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.7.0
 */
public final class BadRequest implements Response {
  private final Response response;

  /**
   * Ctor.
   * 
   * @param response the next link in the chain
   * @since 0.7.0
   */
  public BadRequest(Response response) {
    this.response = response;
  }

  @Override
  public HttpResponse httpResponse() throws IOException, UnauthorizedException {
    if (this.response.httpResponse().getStatusLine().getStatusCode() == HttpStatus.SC_BAD_REQUEST) {
      throw new IOException(
          String.format("Server returned 400 Bad Request. Payload: %s",
              new InputStreamAsString().apply(
                  this.response.httpResponse().getEntity().getContent()
              )
          )
      );
    }

    return this.response.httpResponse();
  }
}
