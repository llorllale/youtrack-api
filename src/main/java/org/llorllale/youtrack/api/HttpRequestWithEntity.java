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

import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.params.HttpParams;

/**
 * Thin decorator around Apace http requests that sets the request's entity 
 * payload through the constructor.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
@SuppressWarnings("checkstyle:MethodCount")
final class HttpRequestWithEntity extends HttpEntityEnclosingRequestBase {
  private final HttpEntityEnclosingRequestBase base;

  /**
   * Ctor.
   * @param entity the entity to attach
   * @param base the base request
   * @since 0.4.0
   */
  HttpRequestWithEntity(HttpEntity entity, HttpEntityEnclosingRequestBase base) {
    this.base = base;
    this.setEntity(entity);
  }

  @Override
  public String getMethod() {
    return this.base.getMethod();
  }

  @Override
  public URI getURI() {
    return this.base.getURI();
  }

  @Override
  public void abort() throws UnsupportedOperationException {
    this.base.abort();
  }

  @Override
  public boolean isAborted() {
    return this.base.isAborted();
  }

  @Override
  public RequestLine getRequestLine() {
    return this.base.getRequestLine();
  }

  @Override
  public ProtocolVersion getProtocolVersion() {
    return this.base.getProtocolVersion();
  }

  @Override
  public boolean containsHeader(String name) {
    return this.base.containsHeader(name);
  }

  @Override
  public Header[] getHeaders(String name) {
    return this.base.getHeaders(name);
  }

  @Override
  public Header getFirstHeader(String name) {
    return this.base.getFirstHeader(name);
  }

  @Override
  public Header getLastHeader(String name) {
    return this.base.getLastHeader(name);
  }

  @Override
  public Header[] getAllHeaders() {
    return this.base.getAllHeaders();
  }

  @Override
  public void addHeader(Header header) {
    this.base.addHeader(header);
  }

  @Override
  public void addHeader(String name, String value) {
    this.base.addHeader(name, value);
  }

  @Override
  public void setHeader(Header header) {
    this.base.setHeader(header);
  }

  @Override
  public void setHeader(String name, String value) {
    this.base.setHeader(name, value);
  }

  @Override
  public void setHeaders(Header[] headers) {
    this.base.setHeaders(headers);
  }

  @Override
  public void removeHeader(Header header) {
    this.base.removeHeader(header);
  }

  @Override
  public void removeHeaders(String name) {
    this.base.removeHeaders(name);
  }

  @Override
  public HeaderIterator headerIterator() {
    return this.base.headerIterator();
  }

  @Override
  public HeaderIterator headerIterator(String name) {
    return this.base.headerIterator(name);
  }

  @Override
  public HttpParams getParams() {
    return this.base.getParams();
  }

  @Override
  public void setParams(HttpParams params) {
    this.base.setParams(params);
  }

  @Override
  public HttpEntity getEntity() {
    return this.base.getEntity();
  }

  @Override
  public boolean expectContinue() {
    return this.base.expectContinue();
  }

  @Override
  public void setEntity(HttpEntity entity) {
    this.base.setEntity(entity);
  }
}
