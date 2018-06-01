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
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * An attachment based on XMLs.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.1.0
 */
final class XmlAttachment implements Attachment {
  private final Xml fileUrl;
  private final Issue issue;

  /**
   * Ctor.
   * @param fileUrl the 'fileUrl' XML element
   * @param issue the issue of this attachment
   */
  XmlAttachment(Xml fileUrl, Issue issue) {
    this.fileUrl = fileUrl;
    this.issue = issue;
  }

  @Override
  public String name() {
    return this.fileUrl.textOf("@name").get();
  }

  @Override
  public User creator() throws IOException, UnauthorizedException {
    return this.issue.project().users().user(
      this.fileUrl.textOf("@authorLogin").get()
    );
  }
}
