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
import java.util.stream.Stream;

/**
 * Attachments of an {@link Issue}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.1.0
 */
public interface Attachments extends Stream<Attachment> {
  /**
   * Attaches {@code contents} to the issue.
   * @param filename the attachment's filename
   * @param type content-type
   * @param contents the contents
   * @return this {@link Attachments}
   * @throws IOException if the server is unavailable or if {@code contents} cannot be read
   * @since 1.1.0
   */
  Attachments create(String filename, String type, InputStream contents) throws IOException;
}
