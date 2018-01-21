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

package org.llorllale.youtrack.api.session;

import java.io.IOException;

/**
 * Signals an error with the credentials that were provided.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @see Login
 * @since 0.1.0
 */
public final class AuthenticationException extends IOException {
  private static final long serialVersionUID = -6585053905710875326L;
  
  /**
   * Ctor.
   * 
   * @param message the exception message
   * @since 0.1.0
   */
  public AuthenticationException(String message) {
    super(message);
  }
}
