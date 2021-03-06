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

package org.llorllale.youtrack.api.http;

import java.util.function.Supplier;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Basic client.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.1.0
 */
public final class Client implements Supplier<HttpClientBuilder> {
  @Override
  public HttpClientBuilder get() {
    return HttpClientBuilder.create();
  }
}
