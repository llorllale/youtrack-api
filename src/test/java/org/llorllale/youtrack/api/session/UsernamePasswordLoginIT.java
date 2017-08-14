/**
 * Copyright 2017 George Aristy
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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.IntegrationTestsConfig;

/**
 *
 * @author George Aristy george.aristy AT gmail DOT com
 */
public class UsernamePasswordLoginIT {
  private static IntegrationTestsConfig config;
  
  @BeforeClass
  public static void setUpClass() throws Exception {
    config = new IntegrationTestsConfig();
  }
  
  /**
   * Test of login method, of class UsernamePasswordLogin.
   */
  @Test
  public void successfulLogin() throws Exception {
    final Session session = new UsernamePasswordLogin(
            config.youtrackUrl(), 
            config.youtrackUser(), 
            config.youtrackPwd()
    ).login();
    
    assertThat(session.baseUrl(), is(config.youtrackUrl()));
    assertThat(session.cookies().size(), is(2));
  }
}