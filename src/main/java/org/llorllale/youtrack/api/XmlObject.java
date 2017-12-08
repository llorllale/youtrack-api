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

import java.util.Collection;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
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
   * Ctor.
   * 
   * @param xml the node to operate against
   * @since 1.0.0
   */
  XmlObject(Node xml) {
    this.xml = xml;
  }

  /**
   * Returns the string value resulting from applying {@code xpath} to the node.
   * 
   * @param xpath the xpath expression
   * @return the string value obtained by applying {@code xpath} on the node
   * @throws UncheckedException wrapping any {@link XPathExpressionException} thrown by java's xpath
   * @since 1.0.0
   */
  String value(String xpath) throws UncheckedException {
    try {
      return XPathFactory.newInstance()
          .newXPath()
          .evaluate(xpath, this.xml);
    } catch(XPathExpressionException e) {
      throw new UncheckedException(e.getMessage(), e);
    }
  }

  /**
   * Returns all descendant nodes selected with {@code xpath}.
   * 
   * @param xpath the xpath expression that identifies the child nodes desired
   * @return a collection of descendant nodes selected with {@code xpath}
   * @throws UncheckedException wrapping any {@link XPathExpressionException} thrown by java's xpath
   * @since 1.0.0
   */
  Collection<Node> children(String xpath) throws UncheckedException {
    try {
      return new NodeListAsCollection(
          (NodeList) XPathFactory.newInstance()
              .newXPath()
              .evaluate(xpath, this.xml, XPathConstants.NODESET)
      );
    } catch(XPathExpressionException e) {
      throw new UncheckedException(e.getMessage(), e);
    }
  }
}
