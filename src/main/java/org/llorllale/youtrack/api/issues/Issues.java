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
package org.llorllale.youtrack.api.issues;

import java.io.IOException;
import java.util.Optional;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * CRUD interface for {@link Issue}.
 * @author George Aristy
 * @since 1.0.0
 */
public interface Issues {
  /**
   * Optionally returns the {@link Issue} with id {@code issueID}, if it exists.
   * @param issueID
   * @return
   * @throws UnauthorizedException
   * @throws IOException 
   * @since 1.0.0
   */
  public Optional<Issue> withID(String issueID) 
      throws UnauthorizedException, IOException;
}