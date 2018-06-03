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
import java.io.InputStream;
import org.llorllale.youtrack.api.session.Login;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * An attachment.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.1.0
 */
public interface Attachment {
  /**
   * This attachment's name.
   * @return name of attachment
   * @since 1.1.0
   */
  String name();

  /**
   * This attachment's creator.
   * @return the creator
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Login} is not authorized to perform this
   *     operation
   * @since 1.1.0
   */
  User creator() throws IOException, UnauthorizedException;

  /**
   * The contents of this attachment.
   * @return the contents
   * @throws IOException if the server is unavailable
   * @since 1.1.0
   */
  InputStream contents() throws IOException;

  /**
   * Delete this attachment.
   * @return the {@link Attachments} API
   * @throws IOException if the server is unavailable
   * @throws UnauthorizedException if the user's {@link Login} is not authorized to perform this
   *     operation
   * @since 1.1.0
   */
  Attachments delete() throws IOException, UnauthorizedException;
}
