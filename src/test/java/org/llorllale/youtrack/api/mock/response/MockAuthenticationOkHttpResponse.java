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

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import static java.util.stream.Collectors.toList;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.params.HttpParams;

/**
 * Mock {@link HttpResponse} simulating a response with code 200.
 * 
 * Suitable for unit tests only.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
public class MockAuthenticationOkHttpResponse implements HttpResponse {
  private final StatusLine statusLine;
  private final List<Header> headers;

  /**
   * Ctor.
   * @since 0.1.0
   */
  public MockAuthenticationOkHttpResponse() {
    this.statusLine = new BasicStatusLine(
        new ProtocolVersion("HTTP", 1, 1), 
        200, 
        "OK"
    );
    this.headers = Arrays.asList(
        new BasicHeader("Set-Cookie", "12345"),
        new BasicHeader("Set-Cookie", "0892405u08")
    );
  }

  @Override
  public StatusLine getStatusLine() {
    return statusLine;
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
    return null;
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
    return statusLine.getProtocolVersion();
  }

  @Override
  public boolean containsHeader(String name) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Header[] getHeaders(String name) {
    return headers.stream()
        .filter(h -> h.getName().equals(name))
        .collect(toList())
        .toArray(new Header[]{});
  }

  @Override
  public Header getFirstHeader(String name) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Header getLastHeader(String name) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Header[] getAllHeaders() {
    return headers.toArray(new Header[]{});
  }

  @Override
  public void addHeader(Header header) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void addHeader(String name, String value) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void setHeader(Header header) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void setHeader(String name, String value) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void setHeaders(Header[] headers) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
