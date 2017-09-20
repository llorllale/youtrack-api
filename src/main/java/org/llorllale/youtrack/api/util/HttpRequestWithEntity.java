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

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

/**
 * Thin decorator around {@link HttpEntityEnclosingRequestBase} that sets the request's entity 
 * payload through the constructor.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public class HttpRequestWithEntity extends HttpEntityEnclosingRequestBase {
  private final HttpEntityEnclosingRequest base;

  /**
   * Ctor.
   * @param entity the entity to attach
   * @param base the base request
   * @since 0.4.0
   */
  public HttpRequestWithEntity(HttpEntity entity, HttpEntityEnclosingRequestBase base) {
    this.base = base;
    this.base.setEntity(entity);
  }

  @Override
  public String getMethod() {
    return base.getRequestLine().getMethod();
  }
}