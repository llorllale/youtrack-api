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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final Map<Field, FieldValue> fields;

    /**
     * Primary ctor.
     * 
     * @param summary the issue's summary (ie. its title)
     * @param description the issue's description
     * @param fields the fields to set
     * @since 0.8.0
     */
    private IssueSpec(String summary, String description, Map<Field, FieldValue> fields) {
      this.summary = summary;
      this.description = description;
      this.fields = fields;
    }

    /**
     * Ctor.
     * 
     * @param summary the issue's summary (ie. its title)
     * @param description the issue's description
     * @since 0.4.0
     */
    public IssueSpec(String summary, String description) {
      this(summary, description, new HashMap<>());
    }

    /**
     * Will set the issue's {@link Field field} to the specified {@link FieldValue value} once
     * {@link #create(org.llorllale.youtrack.api.Issues.IssueSpec) created}.
     * 
     * @param field the field to set
     * @param value the field's value
     * @return a new instance of this spec with the summary, description, and all fields
     * @since 0.8.0
     * @see Issue#update(org.llorllale.youtrack.api.Field, org.llorllale.youtrack.api.FieldValue) 
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
    public List<NameValuePair> asQueryParams() {
      return Arrays.asList(
          new BasicNameValuePair("summary", summary),
          new BasicNameValuePair("description", description)
      );
    }

    /**
     * Represents this spec as Issue {@link Field fields} and {@link FieldValue values}.
     * 
     * @return this spec as Issue {@link Field fields} and {@link FieldValue values}
     * @since 0.8.0
     */
    public Map<Field, FieldValue> asFields() {
      return Collections.unmodifiableMap(fields);
    }
  }
}
