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
final class XmlObject {
  private final Node xml;

  /**
   * Primary ctor.
   * 
   * @param xml the node to operate against
   * @since 1.0.0
   */
  XmlObject(Node xml) {
    this.xml = xml;
  }

  /**
   * Encapsulates the given {@link Document} as a {@link XmlObject}.
   * 
   * @param document the document to encapsulate
   * @since 1.0.0
   */
  XmlObject(Document document) {
    this(document.getDocumentElement());
  }

  /**
   * Encapsulates the given {@link Response} as a {@link XmlObject}.
   * 
   * @param response the response to encapsulate
   * @throws IOException from {@link InputStreamAsString#apply(java.io.InputStream)}
   * @throws ParseException from {@link StringAsDocument}
   * @see #XmlObject(org.w3c.dom.Node) 
   * @since 1.0.0
   */
  XmlObject(Response response) throws ParseException, IOException {
    this(
        new StringAsDocument(
            new InputStreamAsString().apply(
                response.httpResponse().getEntity().getContent()
            )
        ).getDocumentElement()
    );
  }

  /**
   * Returns the string textOf resulting from applying {@code xpath} to the node if the xpath 
   * "exists", otherwise an empty optional.
   * 
   * @param xpath the xpath expression
   * @return the string textOf obtained by applying {@code xpath} on the node
   * @throws UncheckedException wrapping any {@link XPathExpressionException} thrown by java's xpath
   * @since 1.0.0
   */
  Optional<String> textOf(String xpath) throws UncheckedException {
    return this.child(xpath).map(x -> x.node().getTextContent());
  }

  /**
   * Returns the first {@link XmlObject} node selected with {@code xpath}.
   * 
   * @param xpath the xpath expression that identifies the child node desired
   * @return the first {@link XmlObject} node selected with {@code xpath}
   * @throws UncheckedException wrapping any {@link XPathExpressionException} thrown by java's xpath
   * @since 1.0.0
   */
  Optional<XmlObject> child(String xpath) throws UncheckedException {
    return this.children(xpath).stream().findFirst();
  }

  /**
   * Returns all descendant {@link XmlObject} nodes selected with {@code xpath}.
   * 
   * @param xpath the xpath expression that identifies the child nodes desired
   * @return a collection of descendant {@link XmlObject} nodes selected with {@code xpath}
   * @throws UncheckedException wrapping any {@link XPathExpressionException} thrown by java's xpath
   * @since 1.0.0
   */
  Collection<XmlObject> children(String xpath) throws UncheckedException {
    try {
      return new XmlObjects(
          (NodeList) XPathFactory.newInstance()
              .newXPath()
              .evaluate(xpath, this.node(), XPathConstants.NODESET)
      );
    } catch(XPathExpressionException e) {
      throw new UncheckedException(e.getMessage(), e);
    }
  }

  /**
   * Used internally to pass the encapsulated {@link Node} object.
   * 
   * @return the {@link Node} encapsulated by this {@link XmlObject}
   * @since 1.0.0
   */
  private Node node() {
    return this.xml;
  }
}
