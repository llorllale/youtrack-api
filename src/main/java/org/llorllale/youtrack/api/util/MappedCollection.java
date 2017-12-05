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

package org.llorllale.youtrack.api.util;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Handy class that maps a collection of type {@code I} to one of type {@code O} by applying 
 * a {@link ExceptionalFunction function} that can throw a checked exception.
 * 
 * <p><strong>Note:</strong><br>
 * This collection is not modifiable.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @param <I> input collection's generic type
 * @param <O> this collection's generic type
 * @param <E> checked exception declared by the mapping function
 * @since 0.9.0
 */
public final class MappedCollection<I, O, E extends Exception> extends AbstractCollection<O> {
  private final Collection<O> output;

  /**
   * Eagerly applies {@code function} on each element of {@code collection} in order to implement
   * the {@link Collection} contract. 
   * 
   * <p>The behaviour is like this:<br>
   * <pre>  {@code for (I e : collection) {
   *      this.add(function.apply(e));
   *   }}</pre>
   * 
   * <p>Iteration stops if {@code function} throws exception {@code E}, which in turn is propagated 
   * back to the caller.
   * 
   * @param collection input collection to map
   * @param function the {@link ExceptionalFunction mapping function}
   * @throws E the checked exception declared by {@code function}
   * @since 0.9.0
   */
  public MappedCollection(Collection<I> collection, ExceptionalFunction<I, O, E> function) 
      throws E {
    this.output = new ArrayList<>();
    for (I i : collection) {
      this.output.add(function.apply(i));
    }
  }

  @Override
  public Iterator<O> iterator() {
    return this.output.iterator();
  }

  @Override
  public int size() {
    return this.output.size();
  }
}
