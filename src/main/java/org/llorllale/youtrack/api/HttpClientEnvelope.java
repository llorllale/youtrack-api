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

package org.llorllale.youtrack.api;

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
 * {@link HttpClient} envelope.
 * @author George Aristy (george.aristy@gmail.com)
 * @see <a href="https://www.yegor256.com/2017/01/31/decorating-envelopes.html">
 *   Decorating Envelopes</a>
 * @since 1.1.0
 */
public abstract class HttpClientEnvelope implements HttpClient {
  private final HttpClient enveloped;

  /**
   * Ctor.
   * @param origin the enveloped http client
   */
  protected HttpClientEnvelope(HttpClient origin) {
    this.enveloped = origin;
  }

  @Override
  public final HttpParams getParams() {
    return this.enveloped.getParams();
  }

  @Override
  public final ClientConnectionManager getConnectionManager() {
    return this.enveloped.getConnectionManager();
  }

  @Override
  public final HttpResponse execute(
    HttpUriRequest request
  ) throws IOException, ClientProtocolException {
    return this.enveloped.execute(request);
  }

  @Override
  public final HttpResponse execute(
    HttpUriRequest request, HttpContext context
  ) throws IOException, ClientProtocolException {
    return this.enveloped.execute(request, context);
  }

  @Override
  public final HttpResponse execute(
    HttpHost target, HttpRequest request
  ) throws IOException, ClientProtocolException {
    return this.enveloped.execute(target, request);
  }

  @Override
  public final HttpResponse execute(
    HttpHost target, HttpRequest request, HttpContext context
  ) throws IOException, ClientProtocolException {
    return this.enveloped.execute(target, request, context);
  }

  @Override
  public final <T> T execute(
    HttpUriRequest request, ResponseHandler<? extends T> responseHandler
  ) throws IOException, ClientProtocolException {
    return this.enveloped.execute(request, responseHandler);
  }

  @Override
  public final <T> T execute(
    HttpUriRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context
  ) throws IOException, ClientProtocolException {
    return this.enveloped.execute(request, responseHandler, context);
  }

  @Override
  public final <T> T execute(
    HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler
  ) throws IOException, ClientProtocolException {
    return this.enveloped.execute(target, request, responseHandler);
  }

  @Override
  public final <T> T execute(
    HttpHost target, HttpRequest request,
    ResponseHandler<? extends T> responseHandler, HttpContext context
  ) throws IOException, ClientProtocolException {
    return this.enveloped .execute(target, request, responseHandler, context);
  }
}
