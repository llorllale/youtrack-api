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
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;

/**
 * <p>A login for the simple username/password use case.</p>
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @see Login
 * @since 0.1.0
 */
public final class UsernamePassword implements Login {
  private final URL youtrackUrl;
  private final HttpClient httpClient;
  private final String username;
  private final char[] password;

  /**
   * Primary constructor.
   * 
   * @param youtrackUrl the URL of the YouTrack API endpoint
   * @param username the principal
   * @param password the credentials
   * @param httpClient http client to use to call the remote API
   * @since 0.1.0
   */
  UsernamePassword(
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
   * Ctor.
   * 
   * @param youtrackUrl the URL of the YouTrack API endpoint
   * @param username the principal
   * @param password the credentials
   * @see #UsernamePassword(URL, String, char[], HttpClient) 
   * @since 0.1.0
   */
  public UsernamePassword(
          final URL youtrackUrl,
          String username, 
          char[] password
  ) {
    this(youtrackUrl, username, password, HttpClients.createDefault());
  }
  
  @Override
  public Session login() throws AuthenticationException, IOException {
    try {
      final HttpResponse response = this.httpClient.execute(
          new HttpPost(
              new URIBuilder(
                  this.youtrackUrl.toString().concat("/user/login")
              ).setParameter("login", this.username)
              .setParameter("password", new String(this.password))
              .build()
          )
      );
  
      //@checkstyle todo there is more branching here
      if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
        throw new AuthenticationException("Invalid credentials.");
      }
  
      final String cookieHeader = "Cookie";
      final Cookie cookie = Arrays.asList(response.getAllHeaders())
          .stream()
          .filter(header -> "Set-Cookie".equals(header.getName()))
          .map(header -> new DefaultCookie(cookieHeader, header.getValue().split(";")[0]))
          .reduce(
              (cookieA, cookieB) -> new DefaultCookie(
                  cookieHeader, 
                  cookieA.value()
                      .concat("; ")
                      .concat(cookieB.value())
              )
          ).get();
  
      return new DefaultSession(this.youtrackUrl, cookie);
    } catch(URISyntaxException e) {
      throw new IOException(e.getMessage());
    }
  }
}
