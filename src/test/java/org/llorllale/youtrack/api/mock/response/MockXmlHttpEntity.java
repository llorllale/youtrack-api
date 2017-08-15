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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.BasicHttpEntity;

/**
 * Mock {@link HttpEntity} suitable for unit tests.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.1.0
 */
public class MockXmlHttpEntity implements HttpEntity {
  private final BasicHttpEntity orig;

  /**
   * Ctor.
   * @param xml XML content
   * @since 0.1.0
   */
  public MockXmlHttpEntity(String xml) {
    this.orig = new BasicHttpEntity();
    orig.setContentEncoding("application/xml");
    orig.setContentLength(xml.getBytes().length);
    orig.setContentEncoding("UTF-8");
    orig.setContent(new ByteArrayInputStream(xml.getBytes()));
  }

  @Override
  public boolean isRepeatable() {
    return orig.isRepeatable();
  }

  @Override
  public boolean isChunked() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public long getContentLength() {
    return orig.getContentLength();
  }

  @Override
  public Header getContentType() {
    return orig.getContentType();
  }

  @Override
  public Header getContentEncoding() {
    return orig.getContentEncoding();
  }

  @Override
  public InputStream getContent() throws IOException, UnsupportedOperationException {
    return orig.getContent();
  }

  @Override
  public void writeTo(OutputStream outstream) throws IOException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean isStreaming() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void consumeContent() throws IOException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
