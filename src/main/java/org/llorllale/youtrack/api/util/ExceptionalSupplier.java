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

package org.llorllale.youtrack.api.util;

/**
 * Generic supplier that can throw an exception.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @param <T> the type of objects produced by this supplier
 * @param <E> the exception's type
 * @since 1.0.0
 */
public interface ExceptionalSupplier<T, E extends Exception> {
  /**
   * Returns an instance of {@code T}.
   * 
   * @return an instance of {@code T}
   * @throws E if there's an error
   * @since 1.0.0
   */
  T get() throws E;
}
