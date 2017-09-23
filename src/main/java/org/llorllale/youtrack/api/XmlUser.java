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
 * A JAXB implementation of {@link User}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
class XmlUser implements User {
  private final org.llorllale.youtrack.api.jaxb.User jaxbUser;

  /**
   * Ctor.
   * @param jaxbUser the jaxb instance
   * @since 0.4.0
   */
  public XmlUser(org.llorllale.youtrack.api.jaxb.User jaxbUser) {
    this.jaxbUser = jaxbUser;
  }

  @Override
  public String name() {
    return jaxbUser.getFullName();
  }

  @Override
  public String email() {
    return jaxbUser.getEmail();
  }
}
