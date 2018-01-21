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

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import org.apache.http.client.utils.URIBuilder;

/**
 * <p>Hides all {@link URISyntaxException}s.</p>
 * 
 * <p>This class uses the {@link URIBuilder} internally but does not expose the aforementioned 
 * exception. A {@link UncheckedException} is thrown if the internal {@link URIBuilder} throws 
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
   * @throws UncheckedException wrapping any internal {@link URISyntaxException}
   * @since 0.1.0
   */
  UncheckedUriBuilder(String baseUrl) throws UncheckedException {
    try {
      this.builder = new URIBuilder(baseUrl);
    } catch (URISyntaxException e) {
      throw new UncheckedException(
          String.format("This should not have happened: syntax issue with URL: %s", baseUrl),
          e
      );
    }
  }

  /**
   * Forms a new URI by joining {@code baseUrl} and {@code path}.
   * 
   * @param baseUrl the URL for the YouTrack RESTful endpoint API
   * @param path the resource's sub-path
   * @throws UncheckedException wrapping any internal {@link URISyntaxException}
   * @see <a href="https://stackoverflow.com/a/724764/1623885">StackOverflow</a>
   * @since 1.0.0
   */
  UncheckedUriBuilder(URL baseUrl, String path) throws UncheckedException {
    try {
      this.builder = new URIBuilder(
          new URI(
              baseUrl.getProtocol(),
              null,
              baseUrl.getHost(),
              baseUrl.getPort(),
              baseUrl.getPath().concat(path),
              null,
              null
          )
      );
    } catch (URISyntaxException e) {
      throw new UncheckedException(
          String.format(
              "This should not have happened: syntax issue baseUrl=%s path=%s", 
              baseUrl.toString(), path
          ),
          e
      );
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
  public UncheckedUriBuilder param(String name, String value) {
    this.builder.setParameter(name, value);
    return this;
  }

  /**
   * Calls the internal {@link URIBuilder#setParameter(java.lang.String, java.lang.String)} with
   * the values provided, if {@code value} is present.
   * 
   * @param name the name of the parameter
   * @param value the parameter's value
   * @return this object
   * @since 1.0.0
   */
  public UncheckedUriBuilder paramIfPresent(String name, Optional<String> value) {
    value.ifPresent(v -> this.param(name, v));
    return this;
  }

  /**
   * Builds the {@link URI}.
   * 
   * @return the {@link URI} built by the internal {@link URIBuilder}
   * @throws UncheckedException wrapping any internal {@link URISyntaxException}
   * @since 0.1.0
   */
  public URI build() throws UncheckedException {
    try {
      return this.builder.build();
    } catch (URISyntaxException e) {
      throw new UncheckedException("This should not have happened: syntax issue with a URL", e);
    }
  }
}
