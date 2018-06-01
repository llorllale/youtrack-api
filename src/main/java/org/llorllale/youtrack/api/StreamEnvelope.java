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

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
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

/**
 * Stream envelope.
 * @author George Aristy (george.aristy@gmail.com)
 * @param <T> This stream's type
 * @since 1.1.0
 */
@SuppressWarnings("checkstyle:MethodCount")
abstract class StreamEnvelope<T> implements Stream<T> {
  private final Stream<T> stream;

  /**
   * Primary ctor.
   * 
   * @param stream the actual stream implementation 
   * @since 1.0.0
   */
  protected StreamEnvelope(Stream<T> stream) {
    this.stream = stream;
  }

  @Override
  public final Stream<T> filter(Predicate<? super T> predicate) {
    return this.stream.filter(predicate);
  }

  @Override
  public final <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
    return this.stream.map(mapper);
  }

  @Override
  public final IntStream mapToInt(ToIntFunction<? super T> mapper) {
    return this.stream.mapToInt(mapper);
  }

  @Override
  public final LongStream mapToLong(ToLongFunction<? super T> mapper) {
    return this.stream.mapToLong(mapper);
  }

  @Override
  public final DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
    return this.stream.mapToDouble(mapper);
  }

  @Override
  public final <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
    return this.stream.flatMap(mapper);
  }

  @Override
  public final IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
    return this.stream.flatMapToInt(mapper);
  }

  @Override
  public final LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
    return this.stream.flatMapToLong(mapper);
  }

  @Override
  public final DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
    return this.stream.flatMapToDouble(mapper);
  }

  @Override
  public final Stream<T> distinct() {
    return this.stream.distinct();
  }

  @Override
  public final Stream<T> sorted() {
    return this.stream.sorted();
  }

  @Override
  public final Stream<T> sorted(Comparator<? super T> comparator) {
    return this.stream.sorted(comparator);
  }

  @Override
  public final Stream<T> peek(Consumer<? super T> action) {
    return this.stream.peek(action);
  }

  @Override
  public final Stream<T> limit(long maxSize) {
    return this.stream.limit(maxSize);
  }

  @Override
  public final Stream<T> skip(long n) {
    return this.stream.skip(n);
  }

  @Override
  public final void forEach(Consumer<? super T> action) {
    this.stream.forEach(action);
  }

  @Override
  public final void forEachOrdered(Consumer<? super T> action) {
    this.stream.forEachOrdered(action);
  }

  @Override
  public final Object[] toArray() {
    return this.stream.toArray();
  }

  @Override
  public final <A> A[] toArray(IntFunction<A[]> generator) {
    return this.stream.toArray(generator);
  }

  @Override
  public final T reduce(T identity, BinaryOperator<T> accumulator) {
    return this.stream.reduce(identity, accumulator);
  }

  @Override
  public final Optional<T> reduce(BinaryOperator<T> accumulator) {
    return this.stream.reduce(accumulator);
  }

  @Override
  public final <U> U reduce(
      U identity, BiFunction<U, ? super T, U> accumulator, 
      BinaryOperator<U> combiner
  ) {
    return this.stream.reduce(identity, accumulator, combiner);
  }

  @Override
  public final <R> R collect(
      Supplier<R> supplier, 
      BiConsumer<R, ? super T> accumulator, 
      BiConsumer<R, R> combiner
  ) {
    return this.stream.collect(supplier, accumulator, combiner);
  }

  @Override
  public final <R, A> R collect(Collector<? super T, A, R> collector) {
    return this.stream.collect(collector);
  }

  @Override
  public final Optional<T> min(Comparator<? super T> comparator) {
    return this.stream.min(comparator);
  }

  @Override
  public final Optional<T> max(Comparator<? super T> comparator) {
    return this.stream.max(comparator);
  }

  @Override
  public final long count() {
    return this.stream.count();
  }

  @Override
  public final boolean anyMatch(Predicate<? super T> predicate) {
    return this.stream.anyMatch(predicate);
  }

  @Override
  public final boolean allMatch(Predicate<? super T> predicate) {
    return this.stream.allMatch(predicate);
  }

  @Override
  public final boolean noneMatch(Predicate<? super T> predicate) {
    return this.stream.noneMatch(predicate);
  }

  @Override
  public final Optional<T> findFirst() {
    return this.stream.findFirst();
  }

  @Override
  public final Optional<T> findAny() {
    return this.stream.findAny();
  }

  @Override
  public final Iterator<T> iterator() {
    return this.stream.iterator();
  }

  @Override
  public final Spliterator<T> spliterator() {
    return this.stream.spliterator();
  }

  @Override
  public final boolean isParallel() {
    return this.stream.isParallel();
  }

  @Override
  public final Stream<T> sequential() {
    return this.stream.sequential();
  }

  @Override
  public final Stream<T> parallel() {
    return this.stream.parallel();
  }

  @Override
  public final Stream<T> unordered() {
    return this.stream.unordered();
  }

  @Override
  public final Stream<T> onClose(Runnable closeHandler) {
    return this.stream.onClose(closeHandler);
  }

  @Override
  public final void close() {
    this.stream.close();
  }
}
