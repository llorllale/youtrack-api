/*
 * Copyright 2017 George Aristy.
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

package org.llorllale.youtrack.api.mock;

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
 * <p>Mock implementation of {@link HttpClient} suitable for unit tests.</p>
 * 
 * <p>The {@link HttpResponse} is configurable via its 
 * {@link #MockHttpClient(org.apache.http.HttpResponse) constructor}.</p>
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
public class MockHttpClient implements HttpClient {
  private final HttpResponse response;

  /**
   * Configures the mock {@link HttpResponse} to be used as part of the unit test.
   * @param response the mock {@link HttpResponse} to be used as part of the unit test
   * @since 0.1.0
   */
  public MockHttpClient(HttpResponse response) {
    this.response = response;
  }

  @Override
  public HttpParams getParams() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public ClientConnectionManager getConnectionManager() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public HttpResponse execute(HttpUriRequest request) throws IOException, ClientProtocolException {
    return response;
  }

  @Override
  public HttpResponse execute(HttpUriRequest request, HttpContext context) throws IOException, ClientProtocolException {
    return response;
  }

  @Override
  public HttpResponse execute(HttpHost target, HttpRequest request) throws IOException, ClientProtocolException {
    return response;
  }

  @Override
  public HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context) throws IOException, ClientProtocolException {
    return response;
  }

  @Override
  public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context) throws IOException, ClientProtocolException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context) throws IOException, ClientProtocolException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
