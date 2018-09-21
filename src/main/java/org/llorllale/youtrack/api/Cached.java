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

import java.util.function.Supplier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Cached supplier of clients.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.1.0
 */
final class Cached implements Supplier<CloseableHttpClient> {
  private final Supplier<HttpClientBuilder> source;
  private volatile CloseableHttpClient cached;

  /**
   * Ctor.
   * @param source source
   * @since 1.1.0
   */
  Cached(Supplier<HttpClientBuilder> source) {
    this.source = source;
  }

  @Override
  public CloseableHttpClient get() {
    if (this.cached == null) {
      synchronized(this.source) {
        if (this.cached == null) {
          this.cached = this.source.get().build();
        }
      }
    }
    return this.cached;
  }
}
