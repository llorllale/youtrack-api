/* 
 * Copyright 2017 George Aristy (george.aristy@gmail.com).
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

package org.llorllale.youtrack.api.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Utility class to read all text content from an {@link InputStream} and
 * return it in string form.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
public class InputStreamAsString implements AutoCloseable {
  private static final String NEW_LINE = System.getProperty("line.separator");
  private final InputStream inputStream;

  /**
   * Ctor.
   * @param inputStream the inputstream from which to read the text contents
   * @since 0.1.0
   */
  public InputStreamAsString(InputStream inputStream) {
    this.inputStream = inputStream;
  }


  /**
   * Returns the contents of the underlying {@link InputStream} in string form.
   * @return the contents of the underlying {@link InputStream}
   * @throws IOException thrown by the underlying {@link InputStream}
   * @since 0.1.0
   */
  public String string() throws IOException {
    return new BufferedReader(new InputStreamReader(inputStream))
        .lines()
        .reduce((l1,l2) -> l1.concat(l2).concat(NEW_LINE))
        .get()
        .trim();
  }

  @Override
  public void close() throws IOException {
    this.inputStream.close();
  }
}