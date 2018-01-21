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

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * Simple counter utility.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.7.0
 */
final class Counter implements Supplier<Integer> {
  private final AtomicInteger start;
  private final int increment;

  /**
   * Ctor.
   * 
   * @param start the starting value
   * @param increment the amount to increment with each call to {@link #get()}
   * @since 0.7.0
   */
  Counter(int start, int increment) {
    this.start = new AtomicInteger(start);
    this.increment = increment;
  }

  @Override
  public Integer get() {
    return this.start.getAndAdd(this.increment);
  }
}
