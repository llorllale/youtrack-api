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

import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

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
   * All {@link Issue issues} created for this {@link Project}.
   * @return a non-{@code null} list of {@link Issue issues} for this {@link #project() project}
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to access this
   *     resource
   * @since 0.4.0
   */
  public List<Issue> all() throws IOException, UnauthorizedException;

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
    final Instant creationDate;
    final String type;
    final String state;
    final String priority;
    final String summary;
    final String description;

    /**
     * Primary ctor.
     * @param creationDate the date the issue was created on
     * @param type the issue's type (eg. "Bug")
     * @param state the issue's state (eg. "Open")
     * @param priority the issue's priority (eg. "Normal")
     * @param summary the issue's summary (ie. its title)
     * @param description the issue's description
     * @since 0.4.0
     */
    public IssueSpec(
        Instant creationDate, 
        String type, 
        String state, 
        String priority, 
        String summary, 
        String description
    ) {
      this.creationDate = creationDate;
      this.type = type;
      this.state = state;
      this.priority = priority;
      this.summary = summary;
      this.description = description;
    }

    /**
     * Shorthand constructor that makes the following assumptions:
     * <ul>
     *   <li>creationDate is <em>now</em></li>
     *   <li>status is <em>Open</em></li>
     *   <li>priority is <em>Normal</em></li>
     *   <li>description is empty</li>
     * </ul>
     * @param summary the issue's summary (ie. its title)
     * @param type the issue's type (eg. "Bug")
     * @since 0.4.0
     * @see #IssueSpec(java.time.Instant, java.lang.String, java.lang.String, java.lang.String, 
     *     java.lang.String, java.lang.String) 
     */
    public IssueSpec(String summary, String type) {
      this(Instant.now(), type, "Open", "Normal", summary, "");
    }

    /**
     * Returns a new spec with {@code creationDate} and using {@code this} as a prototype.
     * @param creationDate the date the issue was created on
     * @return a new spec with {@code creationDate} and using {@code this} as a prototype
     * @since 0.4.0
     */
    public IssueSpec withCreationDate(Instant creationDate) {
      return new IssueSpec(
          creationDate, 
          this.type, 
          this.state, 
          this.priority, 
          this.summary, 
          this.description
      );
    }

    /**
     * Returns a new spec with the given {@code state} and using {@code this} as a prototype.
     * @param state the issue's state
     * @return a new spec with the given {@code state} and using {@code this} as a prototype
     * @since 0.4.0
     */
    public IssueSpec withState(String state) {
      return new IssueSpec(
          this.creationDate, 
          this.type, 
          state, 
          this.priority, 
          this.summary, 
          this.description
      );
    }

    /**
     * Returns a new spec with the given {@code priority} and using {@code this} as a prototype.
     * @param priority the issue's priority
     * @return a new spec with the given {@code priority} and using {@code this} as a prototype
     * @since 0.4.0
     */
    public IssueSpec withPriority(String priority) {
      return new IssueSpec(
          this.creationDate, 
          this.type, 
          this.state,
          priority, 
          this.summary, 
          this.description
      );
    }

    /**
     * Returns a new spec with the given {@code description} and using {@code this} as a prototype.
     * @param description the issue's description
     * @return a new spec with the given {@code description} and using {@code this} as a prototype
     * @since 0.4.0
     */
    public IssueSpec withDescription(String description) {
      return new IssueSpec(
          this.creationDate, 
          this.type, 
          this.state,
          this.priority, 
          this.summary, 
          description
      );
    }
  }
}
