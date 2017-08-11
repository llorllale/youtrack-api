/**
 * Copyright 2017 George Aristy
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
package org.llorllale.youtrack.api.session;

import org.llorllale.youtrack.api.YouTrackException;

/**
 * Signals an error with the credentials that were provided.
 * @author George Aristy
 * @since 1.0.0
 * @see UsernamePasswordLogin
 */
public class AuthenticationException extends YouTrackException {
  private static final long serialVersionUID = -6585053905710875326L;
  
  /**
   * 
   * @param message 
   * @since 1.0.0
   */
  public AuthenticationException(String message) {
    super(message);
  }
}