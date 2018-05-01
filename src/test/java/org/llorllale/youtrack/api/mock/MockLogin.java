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

package org.llorllale.youtrack.api.mock;

import java.io.IOException;
import org.llorllale.youtrack.api.mock.http.MockSession;
import org.llorllale.youtrack.api.session.AuthenticationException;
import org.llorllale.youtrack.api.session.Login;
import org.llorllale.youtrack.api.session.Session;

/**
 * A mock {@link Login} suitable for tests.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public final class MockLogin implements Login {
  @Override
  public Session session() throws AuthenticationException, IOException {
    return new MockSession();
  }
}
