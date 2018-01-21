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
import java.util.function.Function;
import java.util.function.Supplier;

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
 * @since 0.9.0
 */
final class MappedCollection<I, O> extends AbstractCollection<O> {
  private final Collection<O> output;

  /**
   * Similar to {@link #MappedCollection(Collection, ExceptionalFunction)}.
   * 
   * @param mappingFunction the {@link ExceptionalFunction mapping function}
   * @param collection input collection to map
   * @see #MappedCollection(Collection, Supplier) 
   * @since 1.0.0
   */
  MappedCollection(Function<I, O> mappingFunction, Collection<I> collection) {
    this.output = new ArrayList<>();
    collection.forEach(item -> {
      this.output.add(mappingFunction.apply(item));
    });
  }

  /**
   * Uses the collection supplied by the {@code supplier}.
   * 
   * @param function the mapping function
   * @param collection the collection to map
   * @throws IOException from the {@code supplier}
   * @see #MappedCollection(Collection, ExceptionalFunction) 
   * @since 1.0.0
   */
  MappedCollection(
      Supplier<ExceptionalFunction<I, O, IOException>> function,
      Collection<I> collection
  ) throws IOException {
    this.output = new ArrayList<>();
    for (I i : collection) {
      this.output.add(function.get().apply(i));
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
