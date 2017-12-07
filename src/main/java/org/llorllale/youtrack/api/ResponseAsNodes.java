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
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import org.apache.http.HttpEntity;
import org.w3c.dom.Node;

/**
 * Handy class that encapsulates a given {@link Response} into a collection of {@link Node nodes}.
 * 
 * <p>The nodes are added by applying a given xpath expression on the response's entity's 
 * {@link HttpEntity#getContent() contents}.
 * 
 * <p>This class hides tedious code:
 * <pre>
 * {@code 
 * new XmlObject(
 *     new StringAsDocument(
 *         new InputStreamAsString().apply(
 *             response.httpResponse().getEntity().getContent()
 *         )
 *     )
 * ).children(xpath);
 * }
 * </pre>
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
final class ResponseAsNodes extends AbstractCollection<Node> {
  private final Collection<Node> coll;

  /**
   * Ctor.
   * 
   * @param response the response object to encapsulate
   * @param xpath the xpath expression used to add nodes to this collection
   * @throws ParseException if there's an error applying the xpath expression
   * @throws IOException if there's an error reading the response's entity's contents
   * @see InputStreamAsString
   * @see StringAsDocument
   * @see XmlObject
   * @since 1.0.0
   */
  ResponseAsNodes(Response response, String xpath) throws ParseException, IOException {
    this.coll = 
        new XmlObject(
            new StringAsDocument(
                new InputStreamAsString().apply(
                    response.httpResponse().getEntity().getContent()
                )
            )
        ).children(xpath);
  }

  @Override
  public Iterator<Node> iterator() {
    return this.coll.iterator();
  }

  @Override
  public int size() {
    return this.coll.size();
  }
}
