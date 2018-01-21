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
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Handy class to encapsulate several inputs as a {@link Stream}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @param <T> the stream's type
 * @since 1.0.0
 */
@SuppressWarnings({"checkstyle:MethodCount", "checkstyle:ClassFanOutComplexity"})
final class StreamOf<T> implements Stream<T> {
  private final Stream<T> stream;

  /**
   * Primary ctor.
   * 
   * @param stream the actual stream implementation 
   * @since 1.0.0
   */
  private StreamOf(Stream<T> stream) {
    this.stream = stream;
  }

  /**
   * Encapsulates the collection as a stream.
   * 
   * @param coll collection to encapsulate
   * @since 1.0.0
   */
  StreamOf(Collection<T> coll) {
    this(coll.stream());
  }

  /**
   * Encapsulates the given iterator as a stream.
   * 
   * @param iter the iterator to encapsulate
   * @since 1.0.0
   */
  StreamOf(Iterator<T> iter) {
    this(
        StreamSupport.stream(
            Spliterators.spliteratorUnknownSize(iter, Spliterator.DISTINCT), 
            false
        )
    );
  }

  @Override
  public Stream<T> filter(Predicate<? super T> predicate) {
    return this.stream.filter(predicate);
  }

  @Override
  public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
    return this.stream.map(mapper);
  }

  @Override
  public IntStream mapToInt(ToIntFunction<? super T> mapper) {
    return this.stream.mapToInt(mapper);
  }

  @Override
  public LongStream mapToLong(ToLongFunction<? super T> mapper) {
    return this.stream.mapToLong(mapper);
  }

  @Override
  public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
    return this.stream.mapToDouble(mapper);
  }

  @Override
  public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
    return this.stream.flatMap(mapper);
  }

  @Override
  public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
    return this.stream.flatMapToInt(mapper);
  }

  @Override
  public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
    return this.stream.flatMapToLong(mapper);
  }

  @Override
  public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
    return this.stream.flatMapToDouble(mapper);
  }

  @Override
  public Stream<T> distinct() {
    return this.stream.distinct();
  }

  @Override
  public Stream<T> sorted() {
    return this.stream.sorted();
  }

  @Override
  public Stream<T> sorted(Comparator<? super T> comparator) {
    return this.stream.sorted(comparator);
  }

  @Override
  public Stream<T> peek(Consumer<? super T> action) {
    return this.stream.peek(action);
  }

  @Override
  public Stream<T> limit(long maxSize) {
    return this.stream.limit(maxSize);
  }

  @Override
  public Stream<T> skip(long n) {
    return this.stream.skip(n);
  }

  @Override
  public void forEach(Consumer<? super T> action) {
    this.stream.forEach(action);
  }

  @Override
  public void forEachOrdered(Consumer<? super T> action) {
    this.stream.forEachOrdered(action);
  }

  @Override
  public Object[] toArray() {
    return this.stream.toArray();
  }

  @Override
  public <A> A[] toArray(IntFunction<A[]> generator) {
    return this.stream.toArray(generator);
  }

  @Override
  public T reduce(T identity, BinaryOperator<T> accumulator) {
    return this.stream.reduce(identity, accumulator);
  }

  @Override
  public Optional<T> reduce(BinaryOperator<T> accumulator) {
    return this.stream.reduce(accumulator);
  }

  @Override
  public <U> U reduce(
      U identity, BiFunction<U, ? super T, U> accumulator, 
      BinaryOperator<U> combiner
  ) {
    return this.stream.reduce(identity, accumulator, combiner);
  }

  @Override
  public <R> R collect(
      Supplier<R> supplier, 
      BiConsumer<R, ? super T> accumulator, 
      BiConsumer<R, R> combiner
  ) {
    return this.stream.collect(supplier, accumulator, combiner);
  }

  @Override
  public <R, A> R collect(Collector<? super T, A, R> collector) {
    return this.stream.collect(collector);
  }

  @Override
  public Optional<T> min(Comparator<? super T> comparator) {
    return this.stream.min(comparator);
  }

  @Override
  public Optional<T> max(Comparator<? super T> comparator) {
    return this.stream.max(comparator);
  }

  @Override
  public long count() {
    return this.stream.count();
  }

  @Override
  public boolean anyMatch(Predicate<? super T> predicate) {
    return this.stream.anyMatch(predicate);
  }

  @Override
  public boolean allMatch(Predicate<? super T> predicate) {
    return this.stream.allMatch(predicate);
  }

  @Override
  public boolean noneMatch(Predicate<? super T> predicate) {
    return this.stream.noneMatch(predicate);
  }

  @Override
  public Optional<T> findFirst() {
    return this.stream.findFirst();
  }

  @Override
  public Optional<T> findAny() {
    return this.stream.findAny();
  }

  @Override
  public Iterator<T> iterator() {
    return this.stream.iterator();
  }

  @Override
  public Spliterator<T> spliterator() {
    return this.stream.spliterator();
  }

  @Override
  public boolean isParallel() {
    return this.stream.isParallel();
  }

  @Override
  public Stream<T> sequential() {
    return this.stream.sequential();
  }

  @Override
  public Stream<T> parallel() {
    return this.stream.parallel();
  }

  @Override
  public Stream<T> unordered() {
    return this.stream.unordered();
  }

  @Override
  public Stream<T> onClose(Runnable closeHandler) {
    return this.stream.onClose(closeHandler);
  }

  @Override
  public void close() {
    this.stream.close();
  }
}
