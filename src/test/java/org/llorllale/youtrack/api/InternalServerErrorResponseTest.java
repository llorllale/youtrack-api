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
import org.junit.Test;
import org.llorllale.youtrack.api.mock.http.response.MockInternalErrorResponse;

/**
 * Unit tests for {@link InternalServerErrorResponse}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 * @checkstyle MethodName (50 lines)
 */
public final class InternalServerErrorResponseTest {
  /**
   * Should throw an {@link IOException} if the YouTrack server returns an HTTP response with
   * code 500.
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test(expected = IOException.class)
  public void testHttpResponse() throws Exception {
    new InternalServerErrorResponse(() -> new MockInternalErrorResponse()).httpResponse();
  }
}
