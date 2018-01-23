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
 * Default implementation of {@link Cookie}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
final class DefaultCookie implements Cookie {
  private final String name;
  private final String value;

  /**
   * Primary ctor.
   * 
   * @param name the cookie's name
   * @param value the cookie's value
   * @since 1.0.0
   */
  DefaultCookie(String name, String value) {
    this.name = name;
    this.value = value;
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public String value() {
    return this.value;
  }
}
