/*
 * Copyright 2017 George Aristy.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.llorllale.youtrack.api.util;

import java.io.IOException;

/**
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public class Mapping<T, R> {
  private final ExceptionalSupplier<T, IOException> input;
  private final ExceptionalFunction<T, R, IOException> mappingFunction;

  /**
   * 
   * @param input
   * @param mappingFunction 
   * @since 1.0.0
   */
  public Mapping(ExceptionalSupplier<T, IOException> input, ExceptionalFunction<T, R, IOException> mappingFunction) {
    this.input = input;
    this.mappingFunction = mappingFunction;
  }

  /**
   * 
   * @return
   * @throws IOException 
   * @since 1.0.0
   */
  public R get() throws IOException {
    return this.mappingFunction.apply(this.input.get());
  }
}
