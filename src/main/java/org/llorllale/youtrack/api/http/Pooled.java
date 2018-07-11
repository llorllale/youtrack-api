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
 * Connection pooling.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.1.0
 */
public final class Pooled implements Supplier<HttpClientBuilder> {
  private final int size;
  private final Supplier<HttpClientBuilder> decorated;

  /**
   * Ctor.
   * @param size pool size
   * @param decorated supplier to decorate
   * @since 1.1.0
   */
  public Pooled(int size, Supplier<HttpClientBuilder> decorated) {
    this.size = size;
    this.decorated = decorated;
  }

  @Override
  public HttpClientBuilder get() {
    return this.decorated.get()
      .setMaxConnPerRoute(this.size)
      .setMaxConnTotal(this.size);
  }
}
