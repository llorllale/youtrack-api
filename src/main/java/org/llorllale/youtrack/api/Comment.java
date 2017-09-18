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

import java.time.Instant;

/**
 * A comment created on a YouTrack {@link Issue}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.2.0
 */
public interface Comment {
  /**
   * The comment's ID.
   * @return the comment's ID
   * @since 0.2.0
   */
  public String id();

  /**
   * The instant when the comment was created.
   * @return the instant when the comment was created
   * @since 0.2.0
   */
  public Instant creationDate();

  /**
   * The text content of the comment.
   * @return the text content of the comment
   * @since 0.2.0
   */
  public String text();

  /**
   * The {@link Issue issue} on which this comment was created.
   * @return the {@link Issue issue} on which this comment was created
   * @since 0.2.0
   */
  public Issue issue();
}
