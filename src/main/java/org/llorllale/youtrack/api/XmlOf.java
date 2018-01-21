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
import java.util.Collection;
import java.util.Optional;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Handy class that exposes XPath operations on a {@link Node}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
final class XmlOf implements Xml {
  private final Node xml;

  /**
   * Primary ctor.
   * 
   * @param xml the node to operate against
   * @since 1.0.0
   */
  XmlOf(Node xml) {
    this.xml = xml;
  }

  /**
   * Encapsulates the given {@link Document} as a {@link XmlObject}.
   * 
   * @param document the document to encapsulate
   * @since 1.0.0
   */
  XmlOf(Document document) {
    this(document.getDocumentElement());
  }

  /**
   * Encapsulates the given {@link Response} as a {@link XmlObject}.
   * 
   * @param response the response to encapsulate
   * @throws IOException from {@link InputStreamAsString#apply(java.io.InputStream)}
   * @throws UncheckedException from {@link StringAsDocument}
   * @see #XmlOf(org.w3c.dom.Node) 
   * @since 1.0.0
   */
  XmlOf(Response response) throws UncheckedException, IOException {
    this(
        new StringAsDocument(
            new InputStreamAsString().apply(
                response.httpResponse().getEntity().getContent()
            )
        ).getDocumentElement()
    );
  }

  /**
   * Encapsulates {@code xml} as a {@link Xml}.
   * 
   * @param xml the xml string
   * @throws UncheckedException from {@link StringAsDocument}
   * @see #XmlOf(org.w3c.dom.Document) 
   * @since 1.0.0
   */
  XmlOf(String xml) throws UncheckedException {
    this(new StringAsDocument(xml));
  }

  @Override
  public Optional<String> textOf(String xpath) throws UncheckedException {
    return this.child(xpath).map(x -> x.node().getTextContent());
  }

  @Override
  public Optional<Xml> child(String xpath) throws UncheckedException {
    return this.children(xpath).stream().findFirst();
  }

  @Override
  public Collection<Xml> children(String xpath) throws UncheckedException {
    try {
      return new XmlsOf(
          (NodeList) XPathFactory.newInstance()
              .newXPath()
              .evaluate(xpath, this.node(), XPathConstants.NODESET)
      );
    } catch(XPathExpressionException e) {
      throw new UncheckedException(e.getMessage(), e);
    }
  }

  @Override
  public Node node() {
    return this.xml;
  }
}
