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
 * Primitive and handy class that substitutes StringUtils.substringAfter() (from commons-lang).
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
final class SubstringAfterLast {
  private final String text;
  private final String delimiter;

  /**
   * Ctor.
   * 
   * @param text the text
   * @param delimiter the delimiter
   * @since 1.0.0
   */
  SubstringAfterLast(String text, String delimiter) {
    this.text = text;
    this.delimiter = delimiter;
  }

  /**
   * Returns the substring beginning after the last occurrence of the delimiter.
   * 
   * @return the substring beginning after the last occurrence of the delimiter
   * @since 1.0.0
   */
  public String get() {
    final String[] tokens = this.text.split(this.delimiter);
    return tokens[tokens.length - 1];
  }
}
