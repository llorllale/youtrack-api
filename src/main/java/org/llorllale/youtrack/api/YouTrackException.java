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

/**
 * Generic exception.
 * @author George Aristy
 * @since 1.0.0
 */
public class YouTrackException extends Exception {
  private static final long serialVersionUID = -2606475284678207769L;

  /**
   * 
   * @param message 
   * @since 1.0.0
   */
  public YouTrackException(String message) {
    super(message);
  }

  /**
   * 
   * @param message
   * @param cause 
   * @since 1.0.0
   */
  public YouTrackException(String message, Throwable cause) {
    super(message, cause);
  }
}