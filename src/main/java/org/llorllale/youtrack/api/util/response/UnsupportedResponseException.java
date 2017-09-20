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

package org.llorllale.youtrack.api.util.response;

import org.apache.http.HttpResponse;

/**
 * Signals that a response received from YouTrack is of a type that is 
 * currently not supported by this library.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
public class UnsupportedResponseException extends RuntimeException {
  private static final long serialVersionUID = -4501820211696912590L;
  
  private final HttpResponse httpResponse;

  /**
   * Ctor.
   * @param message the exception message
   * @param httpResponse the unsupported {@link HttpResponse} that was received
   * @since 0.1.0
   */
  public UnsupportedResponseException(String message, HttpResponse httpResponse) {
    super(message);
    this.httpResponse = httpResponse;
  }

  /**
   * The unsupported {@link HttpResponse} that was received.
   * @return the unsupported {@link HttpResponse} that was received
   * @since 0.1.0
   */
  public HttpResponse httpResponse() {
    return httpResponse;
  }
}