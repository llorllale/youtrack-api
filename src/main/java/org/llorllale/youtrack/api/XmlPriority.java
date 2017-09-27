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

import java.io.IOException;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * JAXB adapter for {@link Priority}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.6.0
 */
class XmlPriority implements Priority<org.llorllale.youtrack.api.jaxb.Value> {
  private final org.llorllale.youtrack.api.jaxb.Value jaxb;
  private final YouTrack youtrack;

  /**
   * Ctor.
   * @param jaxb the DTO
   * @param youtrack the {@link YouTrack} parent
   * @since 0.6.0
   */
  XmlPriority(org.llorllale.youtrack.api.jaxb.Value jaxb, YouTrack youtrack) {
    this.jaxb = jaxb;
    this.youtrack = youtrack;
  }

  @Override
  public String asString() {
    return jaxb.getValue();
  }

  @Override
  public Priority<org.llorllale.youtrack.api.jaxb.Value> lower() throws IOException, UnauthorizedException {
    return youtrack.priorities().lower(this);
  }

  @Override
  public Priority<org.llorllale.youtrack.api.jaxb.Value> higher() throws IOException, UnauthorizedException {
    return youtrack.priorities().higher(this);
  }

  @Override
  public int compareTo(Priority<org.llorllale.youtrack.api.jaxb.Value> other) {
    return this.asDto().getColorIndex() - other.asDto().getColorIndex();
  }

  @Override
  public org.llorllale.youtrack.api.jaxb.Value asDto() {
    return jaxb;
  }
}
