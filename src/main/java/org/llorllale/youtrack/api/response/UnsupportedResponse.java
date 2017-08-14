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

package org.llorllale.youtrack.api.response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.llorllale.youtrack.api.session.UnauthorizedException;

import java.io.IOException;
import java.util.Optional;

/**
 * <p>
 * "Catch-all" {@link Response} that throws {@link UnsupportedResponseException}
 * if an unexpected response is returned from YouTrack.
 * 
 * This exception should ideally never be raised if all responses have been 
 * accounted for!
 * </p>
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 * @see UnsupportedOperationException
 */
public class UnsupportedResponse implements Response {
  private final HttpResponse httpResponse;

  /**
   * Ctor.
   * @param httpResponse the {@link HttpResponse} to be adapted
   * @since 0.1.0
   */
  public UnsupportedResponse(HttpResponse httpResponse) {
    this.httpResponse = httpResponse;
  }

  @Override
  public Optional<HttpEntity> payload() 
          throws UnauthorizedException, IOException {
    throw new UnsupportedResponseException(String.format(
            "Unsupported http status code '%s'", 
            httpResponse.getStatusLine().getStatusCode()
    ));
  }

  @Override
  public HttpResponse rawResponse() {
    return httpResponse;
  }
}