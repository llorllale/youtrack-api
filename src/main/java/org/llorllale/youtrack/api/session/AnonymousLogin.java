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
package org.llorllale.youtrack.api.session;

import java.io.IOException;
import java.net.URL;

/**
 * <p>
 * A {@link Login} for anonymous sessions if your YouTrack supports it (ie. the
 * "guest" user is not banned).
 * </p>
 * Calling {@link #login() login()} on an {@code AnonymousLogin} is 
 * guaranteed to always succeed.
 * @author George Aristy
 * @since 1.0.0
 */
public class AnonymousLogin implements Login {
  private final URL youtrackURL;

  /**
   * 
   * @param youtrackURL 
   * @since 1.0.0
   */
  public AnonymousLogin(URL youtrackURL) {
    this.youtrackURL = youtrackURL;
  }
  
  @Override
  public Session login() throws AuthenticationException, IOException {
    return new AnonymousSession(youtrackURL);
  }
}