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

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import org.llorllale.youtrack.api.util.UncheckedUriBuilder;

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
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @see Login
 * @since 0.1.0
 */
public final class UsernamePasswordLogin implements Login {
  private final URL youtrackUrl;
  private final HttpClient httpClient;
  private String username;
  private char[] password;

  /**
   * Primary constructor.
   * 
   * @param youtrackUrl the URL of the YouTrack API endpoint
   * @param username the principal
   * @param password the credentials
   * @param httpClient http client to use to call the remote API
   * @since 0.1.0
   */
  UsernamePasswordLogin(
          final URL youtrackUrl, 
          String username, 
          char[] password, 
          HttpClient httpClient
  ) {
    this.youtrackUrl = youtrackUrl;
    this.httpClient = httpClient;
    this.username = username;
    this.password = Arrays.copyOf(password, password.length);
  }

  /**
   * Assumes the {@link HttpClients#createDefault() default http client} as the 
   * http client to use.
   * 
   * @param youtrackUrl the URL of the YouTrack API endpoint
   * @param username the principal
   * @param password the credentials
   * @see #UsernamePasswordLogin(URL, String, char[], HttpClient) 
   * @since 0.1.0
   */
  public UsernamePasswordLogin(
          final URL youtrackUrl,
          String username, 
          char[] password
  ) {
    this(youtrackUrl, username, password, HttpClients.createDefault());
  }
  
  @Override
  public Session login() throws AuthenticationException, IOException {
    if (Objects.isNull(this.username)) {
      throw new IllegalStateException(
          this.getClass()
              .getSimpleName()
              .concat(".login() cannot be called more than once.")
      );
    }

    final URI uri = new UncheckedUriBuilder(
        this.youtrackUrl.toString().concat("/user/login")
    ).setParameter("login", this.username)
        .setParameter("password", new String(this.password))
        .build();

    this.username = null;
    this.password = null;

    final HttpPost post = new HttpPost(uri);
    final HttpResponse response = this.httpClient.execute(post);

    //@checkstyle todo there is more branching here
    if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
      throw new AuthenticationException("Invalid credentials.");
    }

    final String cookieHeader = "Cookie";
    final Header cookie = Arrays.asList(response.getAllHeaders())
        .stream()
        .filter(header -> "Set-Cookie".equals(header.getName()))
        .map(header -> new BasicHeader(cookieHeader, header.getValue().split(";")[0]))
        .reduce(
            (headerA, headerB) -> new BasicHeader(
                cookieHeader, 
                headerA.getValue()
                    .concat("; ")
                    .concat(headerB.getValue())
            )
        ).get();

    return new DefaultSession(this.youtrackUrl, cookie);
  }
}
