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

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author George Aristy george.aristy AT gmail DOT com
 */
public class UsernamePasswordLoginTest {
  /**
   * Login should be successful if remote API response is 200
   * @throws Exception
   */
  @Test
  public void successfulLogin() throws Exception {
    final HttpClient mockHttp = mock(HttpClient.class);
    when(mockHttp.execute(any(HttpUriRequest.class)))
            .thenReturn(new BasicHttpResponse(
                    new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), 200, "OK")
            ));

    new UsernamePasswordLogin(
            new URL("http://some.url"), 
            "test", 
            "123".toCharArray(), 
            mockHttp
    ).login();
  }

  /**
   * Authentication error if remote API response is different from 200
   * @throws Exception 
   */
  @Test(expected = AuthenticationException.class)
  public void authenticationError() throws Exception {
    final HttpClient mockHttp = mock(HttpClient.class);
    when(mockHttp.execute(any(HttpUriRequest.class)))
            .thenReturn(new BasicHttpResponse(
                    new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), 403, "Forbidden")
            ));

    new UsernamePasswordLogin(
            new URL("http://some.url"), 
            "test", 
            "123".toCharArray(), 
            mockHttp
    ).login();
  }

  /**
   * IOException error if there is an error establishing connection
   * @throws Exception 
   */
  @Test(expected = IOException.class)
  public void networkError() throws Exception {
    final HttpClient mockHttp = mock(HttpClient.class);
    when(mockHttp.execute(any(HttpUriRequest.class)))
            .thenThrow(new SocketException());

    new UsernamePasswordLogin(
            new URL("http://some.url"), 
            "test", 
            "123".toCharArray(), 
            mockHttp
    ).login();
  }

  /**
   * IllegalStateException if login() called more than once.
   * @throws Exception 
   */
  @Test(expected = IllegalStateException.class)
  public void errorWhenLoginCalledMoreThanOnce() throws Exception {
    final HttpClient mockHttp = mock(HttpClient.class);
    when(mockHttp.execute(any(HttpUriRequest.class)))
            .thenReturn(new BasicHttpResponse(
                    new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), 200, "OK")
            ));

    final Login login = new UsernamePasswordLogin(
            new URL("http://some.url"), 
            "test", 
            "123".toCharArray(), 
            mockHttp
    );

    login.login();
    login.login();  //exception thrown here
  }
}