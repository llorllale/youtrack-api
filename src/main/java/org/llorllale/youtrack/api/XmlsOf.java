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
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.w3c.dom.NodeList;

/**
 * Encapsulates several kinds of inputs as a {@link Collection} of {@link Xml xml objects}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
final class XmlsOf extends AbstractCollection<Xml> {
  private final Collection<Xml> base;

  /**
   * Ctor.
   * 
   * <p>This constructor hides this tedious code:
   * <pre>
   * {@code 
   * final Collection<Xml> coll = new ArrayList<>();
   * for (int i = 0; i < nodeList.getLength(); i++) {
   *   coll.add(new XmlOf(nodeList.item(i)));
   * }
   * }
   * </pre>
   * 
   * @param nodeList the {@link NodeList} to encapsulate
   * @since 1.0.0
   */
  XmlsOf(NodeList nodeList) {
    this.base = new ArrayList<>();
    for (int i = 0; i < nodeList.getLength(); i++) {
      this.base.add(new XmlOf(nodeList.item(i)));
    }
  }

  /**
   * Ctor.
   * 
   * <p>This constructor hides this tedious code:
   * <pre>
   * {@code 
   * new XmlOf(
   *     new StringAsDocument(
   *         new InputStreamAsString().apply(
   *             response.httpResponse().getEntity().getContent()
   *         )
   *     )
   * ).children(xpath);
   * }
   * </pre>
   * 
   * @param xpath the xpath expression used to add Xml nodes to this collection
   * @param response the response object to encapsulate
   * @throws IOException if there's an error reading the response's entity's contents
   * @throws UncheckedException if there's an error parsing the xml payload into a document node or
   *    if there's an error while appyling {@code xpath}
   * @see InputStreamAsString
   * @see StringAsDocument
   * @see Xml
   * @since 1.0.0
   */
  XmlsOf(String xpath, Response response) throws IOException, UncheckedException {
    this.base = 
      new XmlOf(
        new StringAsDocument(
          new InputStreamAsString().apply(
            response.httpResponse().getEntity().getContent()
          )
        )
      ).children(xpath);
  }

  @Override
  public Iterator<Xml> iterator() {
    return this.base.iterator();
  }

  @Override
  public int size() {
    return this.base.size();
  }
}
