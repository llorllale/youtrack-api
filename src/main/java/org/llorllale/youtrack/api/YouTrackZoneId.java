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

import java.time.ZoneId;

/**
 * The {@link ZoneId} relative to which YouTrack stores and returns datetimes.
 * 
 * <p>YouTrack receives, stores, and returns datetimes in <em>unix time</em>. All datetimes sent 
 * to YouTrack must be in unix time, and all datetimes received must be parsed as such.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @see <a href="https://github.com/llorllale/youtrack-api/issues/133">#133</a>
 * @see <a href="https://github.com/llorllale/youtrack-api/issues/141">#141</a>
 * @since 1.0.0
 */
final class YouTrackZoneId {
  /**
   * The {@link ZoneId} relative to which YouTrack stores and returns datetimes.
   * 
   * @return the {@link ZoneId} relative to which YouTrack stores and returns datetimes
   * @since 1.0.0
   */
  ZoneId toZoneId() {
    return ZoneId.of("GMT+0");
  }
}
