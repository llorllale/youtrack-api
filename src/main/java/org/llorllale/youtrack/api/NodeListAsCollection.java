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

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Encapsulates a {@link NodeList} as a {@link Collection} of {@link Node nodes}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
final class NodeListAsCollection extends AbstractCollection<Node> {
  private final Collection<Node> base;

  /**
   * Ctor.
   * 
   * @param nodeList the {@link NodeList} to encapsulate
   * @since 1.0.0
   */
  NodeListAsCollection(NodeList nodeList) {
    this.base = new ArrayList<>();
    for (int i = 0; i < nodeList.getLength(); i++) {
      this.base.add(nodeList.item(i));
    }
  }

  @Override
  public Iterator<Node> iterator() {
    return this.base.iterator();
  }

  @Override
  public int size() {
    return this.base.size();
  }
}
