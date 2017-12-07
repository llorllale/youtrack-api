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

package org.llorllale.youtrack.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

/**
 * <p>Hides all {@link URISyntaxException}s.</p>
 * 
 * <p>This class uses the {@link URIBuilder} internally but does not expose the aforementioned 
 * exception. A {@link RuntimeException} is thrown if the internal {@link URIBuilder} throws 
 * {@link URISyntaxException}.</p>
 * 
 * <p>The main motivator for this class is to reduce all the {@code try...catch} noise in the 
 * calling code. These errors should never occur anyway once the code is shipped.</p>
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
final class UncheckedUriBuilder {
  private final URIBuilder builder;

  /**
   * Constructs the internal {@link URIBuilder} with the given {@code baseUrl}.
   * 
   * @param baseUrl an initial url
   * @throws RuntimeException wrapping any internal {@link URISyntaxException}
   * @since 0.1.0
   */
  UncheckedUriBuilder(String baseUrl) {
    try {
      this.builder = new URIBuilder(baseUrl);
    } catch (URISyntaxException e) {
      throw new RuntimeException("This should not have happened: syntax issue with URL", e);
    }
  }

  /**
   * Calls the internal {@link URIBuilder#setParameter(String, String)} with the values provided.
   * 
   * @param name the name of the parameter
   * @param value the value of the parameter
   * @return this object
   * @since 0.1.0
   */
  UncheckedUriBuilder setParameter(String name, String value) {
    this.builder.setParameter(name, value);
    return this;
  }

  /**
   * Adds {@code params} on the internal {@link URIBuilder}.
   * 
   * @param params the query parameters
   * @return this object
   * @since 0.4.0
   */
  UncheckedUriBuilder addParameters(List<NameValuePair> params) {
    this.builder.addParameters(params);
    return this;
  }

  /**
   * Builds the {@link URI}.
   * 
   * @return the {@link URI} built by the internal {@link URIBuilder}
   * @throws RuntimeException wrapping any internal {@link URISyntaxException}
   * @since 0.1.0
   */
  URI build() {
    try {
      return this.builder.build();
    } catch (URISyntaxException e) {
      throw new RuntimeException("This should not have happened: syntax issue with a URL", e);     
    }
  }
}
