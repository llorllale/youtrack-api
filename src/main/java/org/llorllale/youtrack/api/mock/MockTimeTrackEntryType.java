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

package org.llorllale.youtrack.api.mock;

import org.llorllale.youtrack.api.TimeTrackEntryType;

/**
 * Mock implementation of {@link TimeTrackEntryType} suitable for tests.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public final class MockTimeTrackEntryType implements TimeTrackEntryType {
  private final String type;

  /**
   * Ctor.
   * 
   * @param type the type
   */
  public MockTimeTrackEntryType(String type) {
    this.type = type;
  }

  @Override
  public String asString() {
    return this.type;
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
