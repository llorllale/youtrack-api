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

package org.llorllale.youtrack.api.mock;

import java.io.IOException;
import org.llorllale.youtrack.api.AssignedState;
import org.llorllale.youtrack.api.Issue;
import org.llorllale.youtrack.api.State;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.7.0
 */
public class MockAssignedState implements AssignedState {
  private final String string;
  private final boolean resolved;

  /**
   * Primary ctor.
   * @param string
   * @param resolved 
   * @since 0.7.0
   */
  public MockAssignedState(String string, boolean resolved) {
    this.string = string;
    this.resolved = resolved;
  }

  @Override
  public Issue issue() {
    throw new UnsupportedOperationException("Not supported yet."); //TODO implement
  }

  @Override
  public boolean resolved() throws IOException, UnauthorizedException {
    return resolved;
  }

  @Override
  public AssignedState changeTo(State other) throws IOException, UnauthorizedException {
    throw new UnsupportedOperationException("Not supported yet."); //TODO implement
  }

  @Override
  public String asString() {
    return string;
  }
}
