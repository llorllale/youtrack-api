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

/**
 * A runtime exception used to wrap checked exceptions.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.7.0
 */
final class UncheckedException extends RuntimeException {
  private static final long serialVersionUID = -9216739746684341214L;

  /**
   * Ctor.
   * 
   * @param cause the exception's cause
   * @since 0.7.0
   */
  UncheckedException(Throwable cause) {
    super(cause);
  }
}
