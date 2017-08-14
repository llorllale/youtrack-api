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
 * Handles the case when the HTTP status code is {@code 401}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
public class UnauthorizedResponse implements Response {
  private final Response delegate;

  /**
   * Ctor.
   * @param delegate the next link in the chain
   * @since 0.1.0
   */
  public UnauthorizedResponse(Response delegate) {
    this.delegate = delegate;
  }
  
  @Override
  public Optional<HttpEntity> payload() 
      throws UnauthorizedException, IOException {
    if (delegate.rawResponse().getStatusLine().getStatusCode() == 401) {
      throw new UnauthorizedException("User unauthorized to access resource.");
    } else {
      return delegate.payload();
    }
  }

  @Override
  public HttpResponse rawResponse() {
    return delegate.rawResponse();
  }
}