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

import org.llorllale.youtrack.api.User;

/**
 * Mock implementation of {@link User} suitable for tests.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.5.0
 */
public final class MockUser implements User {
  private final String name;
  private final String email;
  private final String loginName;

  /**
   * Ctor.
   * @param name username
   * @param email user's email
   * @param login user's login
   * @since 0.5.0
   */
  public MockUser(String name, String email, String login) {
    this.name = name;
    this.email = email;
    this.loginName = login;
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public String email() {
    return this.email;
  }

  @Override
  public String loginName() {
    return this.loginName;
  }
}
