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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

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
   * Specifications for an {@link Issue}.
   * 
   * @since 0.4.0
   */
  class IssueSpec {
    private final String summary;
    private final Optional<String> description;
    private final Map<Field, FieldValue> fields;

    /**
     * Primary ctor.
     * 
     * @param summary the issue's summary (ie. its title)
     * @param description the issue's description
     * @param fields the fields to set
     * @since 0.8.0
     */
    private IssueSpec(String summary, Optional<String> description, Map<Field, FieldValue> fields) {
      this.summary = summary;
      this.description = description;
      this.fields = fields;
    }

    /**
     * Sets the fields to an empty map.
     * 
     * @param summary the issue's summary
     * @param description an optional describing the issue's <em>description</em> attribute
     * @since 0.9.0
     */
    IssueSpec(String summary, Optional<String> description) {
      this(summary, description, new HashMap<>());
    }

    /**
     * Specify the issue's {@link Issue#summary() summary} and 
     * {@link Issue#description() description}.
     * 
     * @param summary the issue's summary (ie. its title)
     * @param description the issue's description
     * @since 0.4.0
     */
    public IssueSpec(String summary, String description) {
      this(summary, Optional.of(description));
    }

    /**
     * Specify the issue's {@link Issue#summary() summary} only.
     * 
     * @param summary the issue's summary 
     * @since 0.9.0
     */
    public IssueSpec(String summary) {
      this(summary, Optional.empty());
    }

    /**
     * Will set the issue's {@link Field field} to the specified {@link FieldValue value} once
     * {@link #create(org.llorllale.youtrack.api.Issues.IssueSpec) created}.
     * 
     * @param field the field to set
     * @param value the field's value
     * @return a new instance of this spec with the summary, description, and all fields
     * @see UpdateIssue#field(org.llorllale.youtrack.api.Field, 
     *     org.llorllale.youtrack.api.FieldValue) 
     * @since 0.8.0
     */
    public IssueSpec with(Field field, FieldValue value) {
      this.fields.put(field, value);
      return new IssueSpec(this.summary, this.description, this.fields);
    }

    /**
     * Represents this spec as name-value pairs.
     * 
     * @return this spec as name-value pairs
     * @since 0.4.0
     */
    public List<NameValuePair> nameValuePairs() {
      final List<NameValuePair> pairs = new ArrayList<>();
      pairs.add(new BasicNameValuePair("summary", this.summary));
      this.description.ifPresent(d -> pairs.add(new BasicNameValuePair("description", d)));
      return pairs;
    }

    /**
     * Represents this spec as Issue {@link Field fields} and {@link FieldValue values}.
     * 
     * @return this spec as Issue {@link Field fields} and {@link FieldValue values}
     * @since 0.8.0
     */
    public Map<Field, FieldValue> fields() {
      return Collections.unmodifiableMap(this.fields);
    }
  }
}
