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
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;

/**
 * <p>
 * A login for the simple username/password use case.
 * </p>
 * 
 * Implementation note: the {@link #login() login()} method can only be called 
 * <strong>ONCE</strong> on an instance of {@code UsernamePassordLogin}
 * because the username and password fields are set to {@code null} internally in 
 * the first call due to security concerns. Therefore, you need to create another 
 * instance of {@code UsernamePasswordLogin} if the same credentials are to be 
 * retried. Further calls to the {@link #login() login()} method will result in an
 * {@code IllegalStateException} being thrown.
 * @author George Aristy
 * @see Login
 * @since 1.0.0
 */
public class UsernamePasswordLogin implements Login {
  private static final String LOGIN_RESOURCE = "/user/login";

  private final URL youtrackURL;
  private final HttpClient httpClient;
  private String username;
  private char[] password;

  /**
   * Primary constructor.
   * @param youtrackURL
   * @param username
   * @param password
   * @param httpClient http client to use to call the remote API
   * @since 1.0.0
   */
  public UsernamePasswordLogin(
          final URL youtrackURL, 
          String username, 
          char[] password, 
          HttpClient httpClient
  ) {
    this.youtrackURL = youtrackURL;
    this.httpClient = httpClient;
    this.username = username;
    this.password = password;
  }

  /**
   * Assumes the {@link HttpClients#createDefault() default http client} as the 
   * http client to use.
   * @param youtrackURL
   * @param username
   * @param password 
   * @since 1.0.0
   */
  public UsernamePasswordLogin(
          final URL youtrackURL, 
          String username, 
          char[] password
  ) {
    this(youtrackURL, username, password, HttpClients.createDefault());
  }
  
  @Override
  public final Session login() throws AuthenticationException, IOException {
    if(isNull(username)){
      throw new IllegalStateException(
          this.getClass()
              .getSimpleName()
              .concat(".login() cannot be called more than once.")
      );
    }

    try{
      final URIBuilder uri = 
              new URIBuilder(youtrackURL.toString() + LOGIN_RESOURCE);
      uri.setParameter("login", username)
          .setParameter("password", new String(password));

      this.username = null;
      this.password = null;

      final HttpPost post = new HttpPost(uri.build());
      final HttpResponse response = httpClient.execute(post);
      final List<Header> tokens = 
          Arrays.stream(response.getHeaders("Set-Cookie"))
              .collect(toList());
      return new AuthenticatedSession(youtrackURL, tokens);
    }catch(URISyntaxException e){
      throw new RuntimeException("LOGIN_RESOURCE has an unexpected error in syntax.", e);
    }
  }
}