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

package org.llorllale.youtrack.api.session;

import java.io.IOException;

/**
 * <p>
 * Performs the {@link #login() login}.
 * </p>
 * 
 * <p>
 * YouTrack supports different authentication strategies (username/password, token, 
 * etc.), hence it's up to implementations to know what to do with 
 * {@link #login() login()}.
 * </p>
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @see UsernamePassword
 * @see AnonymousLogin
 * @since 0.1.0
 */
public interface Login {
  /**
   * Performs the login function and returns a {@link Session} with sufficient
   * state to allow further transactions with YouTrack.
   * 
   * @return a session object with state usable for further transactions.
   * @throws AuthenticationException if the login process fails due to invalid
   *     credentials
   * @throws IOException if the YouTrack endpoint is unreachable
   * @since 0.1.0
   */
  Session login() throws AuthenticationException, IOException;
}
