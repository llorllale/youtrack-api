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

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Issues API.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public interface Issues {
  /**
   * This {@link Issues}' {@link Project}.
   * 
   * @return this {@link Issues}' {@link Project}
   * @since 0.4.0
   */
  Project project();

  /**
   * A {@link Stream} with all {@link Issue issues} created for this {@link Project}.
   * 
   * @return a {@link Stream} with all {@link Issue issues} for this {@link #project() project}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to access this
   *     resource
   * @since 0.4.0
   */
  Stream<Issue> stream() throws IOException, UnauthorizedException;

  /**
   * The {@link Issue} with the given {@code id}, if it exists.
   * 
   * @param id the {@link Issue#id() issue's id}
   * @return The {@link Issue} with the given {@code id}, if it exists.
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is unauthorized to perform this
   *     operation
   * @since 0.4.0
   */
  Optional<Issue> get(String id) throws IOException, UnauthorizedException;

  /**
   * Creates an issue according to the {@link IssueSpec spec}.
   * 
   * @param spec the specifications for creating the issue
   * @return the newly-created {@link Issue}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is unauthorized to perform this
   *     operation
   * @since 0.4.0
   */
  Issue create(IssueSpec spec) throws IOException, UnauthorizedException;

  /**
   * Specifications for building an {@link Issue}.
   * 
   * <p>There are two main (expected) ways to use specs:
   * <pre>
   *   a) <strong>To create issues.</strong> Users can use any of the constructors to "fill out
   *      the spec for the issue" and then call {@link Issues#create(IssueSpec)}.
   *   b) <strong>To test for end-user changes to an issue.</strong> Since this library will
   *      probably end up being used to display data on a GUI with modifiable fields, and since
   *      {@link Issue issues} are immutable, client code can manually build a spec from the 
   *      GUI's state and test for equality against the issue's own {@link Issue#spec() spec} to
   *      detect any differences.
   * </pre>
   * 
   * <p>Note the following:
   * 
   * <ul>
   *   <li>Two Issues created from the same spec are <strong>not</strong> equal.</li>
   *   <li>Two specs derived from the same issue <strong>are</strong> equal.</li>
   * </ul>
   * 
   * @since 0.4.0
   */
  final class IssueSpec {
    private final String summary;
    private final String description;
    private final Map<Field, FieldValue> fields;

    /**
     * Primary ctor.
     * 
     * @param summary the issue's summary (ie. its title)
     * @param description the issue's description (may be {@code null})
     * @param fields the fields to set
     * @since 0.8.0
     */
    public IssueSpec(String summary, String description, Map<Field, FieldValue> fields) {
      this.summary = summary;
      this.description = description;
      this.fields = fields;
    }

    /**
     * Sets no fields.
     * 
     * @param summary the issue's summary
     * @param description the issue's description (may be {@code null})
     * @since 0.9.0
     */
    public IssueSpec(String summary, String description) {
      this(summary, description, Collections.emptyMap());
    }

    /**
     * Specify the issue's {@link Issue#summary() summary} and 
     * {@link Issue#description() description}.
     * 
     * @param summary the issue's summary (ie. its title)
     * @param fields the issue's fields
     * @since 0.4.0
     */
    public IssueSpec(String summary, Map<Field, FieldValue> fields) {
      this(summary, (String) null, fields);
    }

    /**
     * Specify the issue's {@link Issue#summary() summary} only.
     * 
     * @param summary the issue's summary 
     * @since 0.9.0
     */
    public IssueSpec(String summary) {
      this(summary, Collections.emptyMap());
    }

    /**
     * The summary text for the issue.
     * 
     * @return the summary text for the issue 
     * @since 1.0.0
     */
    public String summary() {
      return this.summary;
    }

    /**
     * The descriptive text for the issue, if specified.
     * 
     * @return the descriptive text for the issue, if specified
     * @since 1.0.0
     */
    public Optional<String> description() {
      return Optional.ofNullable(this.description);
    }

    /**
     * A view of this spec as {@link Field fields} and {@link FieldValue values}.
     * 
     * @return this spec as Issue {@link Field fields} and {@link FieldValue values}
     * @since 0.8.0
     */
    public Map<Field, FieldValue> fields() {
      return Collections.unmodifiableMap(this.fields);
    }

    @Override
    public boolean equals(Object object) {
      if (!(object instanceof IssueSpec)) {
        return false;
      }

      final IssueSpec other = (IssueSpec) object;
      return this.summary().equals(other.summary())
          && this.description().equals(other.description())
          && this.fields().equals(other.fields());
    }

    @Override
    public int hashCode() {
      return this.summary().hashCode() + this.description().hashCode() + this.fields().hashCode();
    }
  }
}
