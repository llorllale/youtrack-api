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
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
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
 * Mock implementation of {@link HttpClient} suitable for unit tests.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public final class MockHttpClient implements HttpClient {
  private final HttpResponse finalResponse;
  private final Deque<HttpResponse> intermediateResponses;

  /**
   * Primary ctor.
   *
   * <p>Each call to
   * {@link #execute(org.apache.http.client.methods.HttpUriRequest)} will return
   * one of the {@code intermediateResponses}, in the encountered order, after
   * which {@code finalResponse} will be consistently returned on all subsequent
   * calls. This is useful for testing the <em>streaming</em> functionalities.
   * @param finalResponse the response to return after all intermediate
   * responses have been exhausted
   * @param intermediateResponses the mock {@link HttpResponse responses} to
   * return before the {@code finalResponse}
   * @since 0.4.0
   */
  public MockHttpClient(HttpResponse finalResponse, HttpResponse... intermediateResponses) {
    this.finalResponse = finalResponse;
    this.intermediateResponses = new ArrayDeque<>(Arrays.asList(intermediateResponses));
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
    final HttpResponse response;
    if (!this.intermediateResponses.isEmpty()) {
      response = this.intermediateResponses.pop();
    } else {
      response = this.finalResponse;
    }
    return response;
  }

  @Override
  public HttpResponse execute(
    HttpUriRequest request, HttpContext context
  ) throws IOException, ClientProtocolException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public HttpResponse execute(
    HttpHost target, HttpRequest request
  ) throws IOException, ClientProtocolException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public HttpResponse execute(
    HttpHost target, HttpRequest request, HttpContext context
  ) throws IOException, ClientProtocolException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public <T> T execute(
    HttpUriRequest request, ResponseHandler<? extends T> responseHandler
  ) throws IOException, ClientProtocolException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public <T> T execute(
    HttpUriRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context
  ) throws IOException, ClientProtocolException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public <T> T execute(
    HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler
  ) throws IOException, ClientProtocolException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public <T> T execute(
    HttpHost target, HttpRequest request,
    ResponseHandler<? extends T> responseHandler, HttpContext context
  ) throws IOException, ClientProtocolException {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
