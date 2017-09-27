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

package org.llorllale.youtrack.api;

/**
 * JAXB adapter for {@link Priority}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.6.0
 */
class XmlPriority implements Priority {
  private final org.llorllale.youtrack.api.jaxb.Value jaxb;

  /**
   * Ctor.
   * @param jaxb the DTO
   * @param youtrack the {@link YouTrack} parent
   * @since 0.6.0
   */
  XmlPriority(org.llorllale.youtrack.api.jaxb.Value jaxb) {
    this.jaxb = jaxb;
  }

  @Override
  public String asString() {
    return jaxb.getValue();
  }
}
