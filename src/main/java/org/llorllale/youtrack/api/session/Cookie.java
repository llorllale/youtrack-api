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

/**
 * An HTTP cookie held by the user's {@link Session}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @see Session#cookies() 
 * @since 1.0.0
 */
public interface Cookie {
  /**
   * The cookie's name.
   * 
   * @return the cookie's name
   * @since 1.0.0
   */
  String name();

  /**
   * The cookie's value.
   * 
   * @return the cookie's value
   * @since 1.0.0
   */
  String value();
}
