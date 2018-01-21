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

package org.llorllale.youtrack.api.mock.http;

import java.io.IOException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

/**
 * Mock impl. of {@link HttpClient} that always throws an {@link IOException}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public final class MockThrowingHttpClient implements HttpClient {
  private final IOException exception;

  /**
   * Ctor.
   * 
   * @since 1.0.0
   */
  public MockThrowingHttpClient() {
    this.exception = new IOException("always fails");
  }

  @Override
  public HttpParams getParams() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public ClientConnectionManager getConnectionManager() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public HttpResponse execute(HttpUriRequest request) throws IOException, ClientProtocolException {
    throw this.exception;
  }

  @Override
  public HttpResponse execute(HttpUriRequest request, HttpContext context) 
      throws IOException, ClientProtocolException {
    throw this.exception;
  }

  @Override
  public HttpResponse execute(HttpHost target, HttpRequest request) 
      throws IOException, ClientProtocolException {
    throw this.exception;
  }

  @Override
  public HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context) 
      throws IOException, ClientProtocolException {
    throw this.exception;
  }

  @Override
  public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler) 
      throws IOException, ClientProtocolException {
    throw this.exception;
  }

  @Override
  public <T> T execute(
      HttpUriRequest request, 
      ResponseHandler<? extends T> responseHandler, 
      HttpContext context
  ) throws IOException, ClientProtocolException {
    throw this.exception;
  }

  @Override
  public <T> T execute(
      HttpHost target, 
      HttpRequest request, 
      ResponseHandler<? extends T> responseHandler
  ) throws IOException, ClientProtocolException {
    throw this.exception;
  }

  @Override
  public <T> T execute(
      HttpHost target, 
      HttpRequest request, 
      ResponseHandler<? extends T> responseHandler, 
      HttpContext context
  ) throws IOException, ClientProtocolException {
    throw this.exception;
  }
}
