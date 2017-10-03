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

package org.llorllale.youtrack.api;

import org.llorllale.youtrack.api.session.UnauthorizedException;

import java.io.IOException;

/**
 * Adapts a given {@link FieldValue} into a {@link SelectableFieldValue}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 */
class DefaultSelectableFieldValue implements SelectableFieldValue {
  private final FieldValue fieldValue;
  private final Issue issue;

  /**
   * Ctor.
   * 
   * @param fieldValue the {@link FieldValue} to adapt
   * @param issue the parent {@link Issue}
   * @since 0.8.0
   */
  DefaultSelectableFieldValue(FieldValue fieldValue, Issue issue) {
    this.fieldValue = fieldValue;
    this.issue = issue;
  }

  @Override
  public Issue apply() throws IOException, UnauthorizedException {
    return this.issue.update(this.field(), this);
  }

  @Override
  public Field field() {
    return fieldValue.field();
  }

  @Override
  public String asString() {
    return fieldValue.asString();
  }
}
