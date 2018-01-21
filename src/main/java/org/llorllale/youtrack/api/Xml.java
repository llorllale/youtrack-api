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

import java.util.Collection;
import java.util.Optional;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Node;

/**
 * Encapsulation of the XML messages sent to/received from YouTrack.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
interface Xml {
  /**
   * Returns the text content of the element selected with {@code xpath} if it exists, 
   * otherwise an empty optional.
   * 
   * @param xpath the xpath expression
   * @return the string obtained by applying {@code xpath} on the xml
   * @throws UncheckedException wrapping any {@link XPathExpressionException} thrown by java's xpath
   * @since 1.0.0
   */
  Optional<String> textOf(String xpath) throws UncheckedException;

  /**
   * Returns the first {@link Xml} node selected with {@code xpath}.
   * 
   * @param xpath the xpath expression that identifies the child node desired
   * @return the first {@link Xml} node selected with {@code xpath}
   * @throws UncheckedException wrapping any {@link XPathExpressionException} thrown by java
   * @since 1.0.0
   */
  Optional<Xml> child(String xpath) throws UncheckedException;

  /**
   * Returns all descendant {@link Xml} nodes selected with {@code xpath}.
   * 
   * @param xpath the xpath expression that identifies the child nodes desired
   * @return a collection of descendant {@link Xml} nodes selected with {@code xpath}
   * @throws UncheckedException wrapping any {@link XPathExpressionException} thrown by java
   * @since 1.0.0
   */
  Collection<Xml> children(String xpath) throws UncheckedException;

  /**
   * The {@link Node} encapsulated by this {@link Xml}.
   * 
   * @return the node encapsulated by this xml
   * @since 1.0.0
   */
  Node node();
}
