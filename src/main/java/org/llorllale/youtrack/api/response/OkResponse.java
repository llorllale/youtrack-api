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
 * Returns the payload when the HTTP status code is {@code 200}.
 * </p>
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
public class OkResponse implements Response {
  private final Response delegate;

  /**
   * Constructor.
   * @param delegate the next link in the chain
   * @since 0.1.0
   */
  public OkResponse(Response delegate) {
    this.delegate = delegate;
  }

  @Override
  public Optional<HttpEntity> payload() 
          throws UnauthorizedException, IOException {
    if (delegate.rawResponse().getStatusLine().getStatusCode() == 200) {
      return Optional.ofNullable(delegate.rawResponse().getEntity());
    } else {
      return delegate.payload();
    }
  }

  @Override
  public HttpResponse rawResponse() {
    return delegate.rawResponse();
  }
}