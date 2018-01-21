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

/**
 * Adapts an {@link Xml} received from YouTrack to a {@link TimeTrackEntryType}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 */
final class XmlTimeTrackEntryType implements TimeTrackEntryType {
  private final Xml xml;

  /**
   * Ctor.
   * 
   * @param xml the xml recieved from YouTrack
   * @since 0.8.0
   */
  XmlTimeTrackEntryType(Xml xml) {
    this.xml = xml;
  }

  @Override
  public String asString() {
    return this.xml.textOf("name").get();
  }

  @Override
  public int hashCode() {
    return this.asString().hashCode();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof TimeTrackEntryType)) {
      return false;
    }

    final TimeTrackEntryType other = (TimeTrackEntryType) object;
    return this.asString().equals(other.asString());
  }
}
