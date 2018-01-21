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

import java.io.IOException;
import java.util.Map;

import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * API to update an {@link Issue}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.9.0
 */
public interface UpdateIssue {
  /**
   * Updates the issue's {@link Issue#summary() summary}.
   * 
   * @param summary the new text for the issue's summary
   * @return a new instance of the {@link Issue} reflecting the changes
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 0.9.0
   */
  Issue summary(String summary) throws IOException, UnauthorizedException;

  /**
   * Updates the issue's {@link Issue#description() description}.
   * 
   * @param description the new text for the issue's description
   * @return a new instance of the {@link Issue} reflecting the changes
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 0.9.0
   */
  Issue description(String description) throws IOException, UnauthorizedException;

  /**
   * Updates the issue's {@link Issue#summary() summary} and 
   * {@link Issue#description() description}.
   * 
   * @param summary the new text for the issue's summary
   * @param description the new text for the issue's description
   * @return a new instance of the {@link Issue} reflecting the changes
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 0.9.0
   */
  Issue summaryAndDesc(String summary, String description) 
      throws IOException, UnauthorizedException;

  /**
   * Updates the issue's {@link Field field} to the given {@link FieldValue value} and returns a
   * new {@link Issue} reflecting those changes.
   * 
   * @param field the issue's {@link Field field}
   * @param value the field's {@link FieldValue value}
   * @return a new instance of the {@link Issue} reflecting the changes
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @see Issue#fields() 
   * @since 0.9.0
   */
  Issue field(Field field, FieldValue value) throws IOException, UnauthorizedException;

  /**
   * Updates this issue with the {@code fields} provided, returning a new {@link Issue} reflecting
   * those changes.
   * 
   * @param fields the collection of fields and their values to update
   * @return a new instance of the {@link Issue} reflecting the changes
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is not authorized to perform this
   *     operation
   * @since 0.9.0
   */
  Issue fields(Map<Field, FieldValue> fields) throws IOException, UnauthorizedException;
}
