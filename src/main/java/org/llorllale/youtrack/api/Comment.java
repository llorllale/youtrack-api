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
import java.time.Instant;

import org.llorllale.youtrack.api.session.Session;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * A comment created on a YouTrack {@link Issue}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.2.0
 */
public interface Comment {
  /**
   * The comment's ID.
   * 
   * @return the comment's ID
   * @since 0.2.0
   */
  String id();

  /**
   * The instant when the comment was created.
   * 
   * @return the instant when the comment was created
   * @since 0.2.0
   */
  Instant creationDate();

  /**
   * The text content of the comment.
   * 
   * @return the text content of the comment
   * @since 0.2.0
   */
  String text();

  /**
   * The {@link Issue issue} on which this comment was created.
   * 
   * @return the {@link Issue issue} on which this comment was created
   * @since 0.2.0
   */
  Issue issue();

  /**
   * Updates the text content of this {@link Comment}.
   * 
   * @param text the new text
   * @return this {@link Comment}, updated
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is unauthorized to perform this 
   *     operation
   * @since 0.9.0
   */
  Comment update(String text) throws IOException, UnauthorizedException;

  /**
   * Deletes this {@link Comment} from the {@link Issue}.
   * 
   * <p><strong>Note: </strong>deletion is not permanent; it is possible for an admin to restore 
   * a comment deleted with this operation.</p>
   * 
   * @return the parent {@link Issue} to which this comment belonged
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Session} is unauthorized to perform this 
   *     operation
   * @since 0.9.0
   */
  Issue delete() throws IOException, UnauthorizedException;
}
