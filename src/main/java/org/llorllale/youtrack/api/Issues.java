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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Issues API.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public interface Issues {
  /**
   * This {@link Issues}' {@link Project}.
   * @return this {@link Issues}' {@link Project}
   * @since 0.4.0
   */
  public Project project();

  /**
   * A {@link Stream} with all {@link Issue issues} created for this {@link Project}.
   * @return a {@link Stream} with all {@link Issue issues} for this {@link #project() project}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to access this
   *     resource
   * @since 0.4.0
   */
  public Stream<Issue> stream() throws IOException, UnauthorizedException;

  /**
   * The {@link Issue} with the given {@code id}, if it exists.
   * @param id the {@link Issue#id() issue's id}
   * @return The {@link Issue} with the given {@code id}, if it exists.
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is unauthorized to perform this
   *     operation
   * @since 0.4.0
   */
  public Optional<Issue> get(String id) throws IOException, UnauthorizedException;

  /**
   * Creates an issue according to the {@link IssueSpec spec}.
   * @param spec the specifications for creating the issue
   * @return the newly-created {@link Issue}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is unauthorized to perform this
   *     operation
   * @since 0.4.0
   */
  public Issue create(IssueSpec spec) throws IOException, UnauthorizedException;

  /**
   * Specifications for creating an {@link Issue}.
   * @since 0.4.0
   */
  public static class IssueSpec {
    private final String summary;
    private final String description;

    /**
     * Primary ctor.
     * @param summary the issue's summary (ie. its title)
     * @param description the issue's description
     * @since 0.4.0
     */
    public IssueSpec(String summary, String description) {
      this.summary = summary;
      this.description = description;
    }

    /**
     * Returns a list of query parameters for this spec.
     * @return a list of query parameters for this spec.
     * @since 0.4.0
     */
    public List<NameValuePair> asQueryParams() {
      return Arrays.asList(
          new BasicNameValuePair("summary", summary),
          new BasicNameValuePair("description", description)
      );
    }
  }
}
