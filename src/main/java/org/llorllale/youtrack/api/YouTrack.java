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

import java.time.ZoneId;

/**
 * Entry point for the YouTrack API.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public interface YouTrack {
  /**
   * All date/time fields in YouTrack's API are expressed relative to this ZoneId.
   * 
   * @since 1.0.0
   */
  ZoneId ZONE_ID = ZoneId.of("GMT+0");

  /**
   * Access to the {@link Project projects} API.
   * 
   * @return Access to the {@link Project projects} API.
   * @since 0.4.0
   */
  Projects projects();
}
