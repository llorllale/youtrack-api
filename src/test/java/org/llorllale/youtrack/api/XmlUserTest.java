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

import static org.hamcrest.CoreMatchers.is;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for {@link XmlUser}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public class XmlUserTest {
  private static XmlObject xml;

  @BeforeClass
  public static void setUpClass() throws Exception {
    xml = new XmlObject(new StringAsDocument(USER));
  }

  /**
   * Test of name method, of class XmlUser.
   * 
   * @since 0.4.0
   */
  @Test
  public void testName() {
    assertThat(
        new XmlUser(xml).name(),
        is("Application Exception")
    );
  }

  /**
   * Test of email method, of class XmlUser.
   * @since 0.4.0
   */
  @Test
  public void testEmail() {
    assertThat(
        new XmlUser(xml).email(),
        is("vadim.gurov@gmail.com")
    );
  }

  @Test
  public void testLoginName() {
    assertThat(
        new XmlUser(xml).loginName(),
        is("exception")
    );
  }

  private static final String USER =
"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
"<user lastCreatedProject=\"HBR\" login=\"exception\" email=\"vadim.gurov@gmail.com\" fullName=\"Application Exception\"/>";
}