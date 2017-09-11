/* 
 * Copyright 2017 George Aristy (george.aristy@gmail.com).
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

import static java.util.Objects.isNull;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.llorllale.youtrack.api.util.NonCheckedUriBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

/**
 * <p>A login for the simple username/password use case.</p>
 * 
 * <p>
 * Implementation note: the {@link #login() login()} method can only be called 
 * <strong>ONCE</strong> on an instance of {@code UsernamePassordLogin}
 * because the username and password fields are set to {@code null} internally in 
 * the first call due to security concerns. Therefore, you need to create another 
 * instance of {@code UsernamePasswordLogin} if the same credentials are to be 
 * retried. Further calls to the {@link #login() login()} method will result in an
 * {@code IllegalStateException} being thrown.
 * </p>
 * @author George Aristy (george.aristy@gmail.com)
 * @see Login
 * @since 0.1.0
 */
public class UsernamePasswordLogin implements Login {
  private static final String LOGIN_RESOURCE = "/user/login";

  private final URL youtrackUrl;
  private final HttpClient httpClient;
  private String username;
  private char[] password;

  /**
   * Primary constructor.
   * @param youtrackUrl the URL of the YouTrack API endpoint
   * @param username the principal
   * @param password the credentials
   * @param httpClient http client to use to call the remote API
   * @since 0.1.0
   */
  public UsernamePasswordLogin(
          final URL youtrackUrl, 
          String username, 
          char[] password, 
          HttpClient httpClient
  ) {
    this.youtrackUrl = youtrackUrl;
    this.httpClient = httpClient;
    this.username = username;
    this.password = password;
  }

  /**
   * Assumes the {@link HttpClients#createDefault() default http client} as the 
   * http client to use.
   * @param youtrackUrl the URL of the YouTrack API endpoint
   * @param username the principal
   * @param password the credentials
   * @since 0.1.0
   * @see #UsernamePasswordLogin(URL, String, char[], HttpClient) 
   */
  public UsernamePasswordLogin(
          final URL youtrackUrl,
          String username, 
          char[] password
  ) {
    this(youtrackUrl, username, password, HttpClients.createDefault());
  }
  
  @Override
  public final Session login() throws AuthenticationException, IOException {
    if (isNull(username)) {
      throw new IllegalStateException(
          this.getClass()
              .getSimpleName()
              .concat(".login() cannot be called more than once.")
      );
    }

    final NonCheckedUriBuilder ub = 
            new NonCheckedUriBuilder(youtrackUrl.toString() + LOGIN_RESOURCE);
    ub.setParameter("login", username)
        .setParameter("password", new String(password));

    this.username = null;
    this.password = null;

    final HttpPost post = new HttpPost(ub.build());
    final HttpResponse response = httpClient.execute(post);

    if (response.getStatusLine().getStatusCode() != 200) {    //TODO there is more branching here
      throw new AuthenticationException("Invalid credentials.");
    }

    final Header cookie = Arrays.asList(response.getAllHeaders())
        .stream()
        .filter(h -> "Set-Cookie".equals(h.getName()))
        .map(h -> new BasicHeader("Cookie", h.getValue().split(";")[0]))
        .reduce(
            (h1, h2) -> new BasicHeader(
                "Cookie", 
                h1.getValue()
                    .concat("; ")
                    .concat(h2.getValue())
            )
        ).get();    //expected

    return new BasicSession(youtrackUrl, Arrays.asList(cookie));
  }
}