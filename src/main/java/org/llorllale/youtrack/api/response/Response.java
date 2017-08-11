/**
 * Copyright 2017 George Aristy
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

import java.io.IOException;
import java.util.Optional;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Encapsulates handling logic for the HTTP responses received from the YouTrack
 * API.
 * @author George Aristy
 * @since 1.0.0
 */
public interface Response {
  /**
   * The payload received in the API's response.
   * @return The payload received in the API's response.
   * @throws UnauthorizedException if the remote API sent back an "unauthorized"
   * response.
   * @throws IOException if the remote API was unreachable.
   * @since 1.0.0
   */
  public Optional<HttpEntity> payload() 
      throws UnauthorizedException, IOException;

  /**
   * The unaltered {@link HttpResponse} received from the {@link HttpClient}.
   * @return 
   * @since 1.0.0
   */
  public HttpResponse rawResponse();
}