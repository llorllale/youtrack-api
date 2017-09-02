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

package org.llorllale.youtrack.api.mock.response;

import java.util.Locale;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.params.HttpParams;

/**
 * Mock implementation of {@link HttpResponse} that simulates a {@code Created 201} http response.
 * Suitable for unit tests.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
public class MockCreatedHttpResponse implements HttpResponse {
  private final HttpResponse original;

  /**
   * Primary constructor.
   * @param headers the {@link Header headers}
   * @since 0.3.0
   */
  public MockCreatedHttpResponse(Header... headers) {
    this.original = new BasicHttpResponse(
        new BasicStatusLine(
            new ProtocolVersion("HTTP", 1, 1), 
            201, 
            "Created" 
        )
    );
    this.original.setHeaders(headers);
  }

  /**
   * No {@link #getAllHeaders() headers} are set.
   * @since 0.1.0
   */
  public MockCreatedHttpResponse() {
    this(new Header[]{});
  }

  @Override
  public StatusLine getStatusLine() {
    return original.getStatusLine();
  }

  @Override
  public void setStatusLine(StatusLine statusline) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void setStatusLine(ProtocolVersion ver, int code) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void setStatusLine(ProtocolVersion ver, int code, String reason) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void setStatusCode(int code) throws IllegalStateException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void setReasonPhrase(String reason) throws IllegalStateException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public HttpEntity getEntity() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void setEntity(HttpEntity entity) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Locale getLocale() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void setLocale(Locale loc) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public ProtocolVersion getProtocolVersion() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean containsHeader(String name) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Header[] getHeaders(String name) {
    return original.getHeaders(name);
  }

  @Override
  public Header getFirstHeader(String name) {
    return original.getFirstHeader(name);
  }

  @Override
  public Header getLastHeader(String name) {
    return original.getLastHeader(name);
  }

  @Override
  public Header[] getAllHeaders() {
    return original.getAllHeaders();
  }

  @Override
  public void addHeader(Header header) {
    original.addHeader(header);;
  }

  @Override
  public void addHeader(String name, String value) {
    original.addHeader(name, value);
  }

  @Override
  public void setHeader(Header header) {
    original.setHeader(header);
  }

  @Override
  public void setHeader(String name, String value) {
    original.setHeader(name, value);
  }

  @Override
  public void setHeaders(Header[] headers) {
    original.setHeaders(headers);
  }

  @Override
  public void removeHeader(Header header) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void removeHeaders(String name) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public HeaderIterator headerIterator() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public HeaderIterator headerIterator(String name) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public HttpParams getParams() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void setParams(HttpParams params) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}
