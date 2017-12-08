/*
 * Copyright 2017 George Aristy.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.llorllale.youtrack.api;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;

/**
 * An {@link Iterator} that holds the contents of a single page of results from the YouTrack server.
 * 
 * <p>Note: the {@link #hasNext()} and {@link #next()} methods wrap checked exceptions inside
 * {@link UncheckedException}.</p>
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @param <T> the type of the contents of this page
 * @see Pagination
 * @since 0.7.0
 */
final class Page<T> implements Iterator<T> {
  private final HttpUriRequest request;
  private final ExceptionalFunction<Response, Collection<T>, IOException> mapper;
  private final Deque<T> contents;

  /**
   * Ctor.
   * 
   * @param request the {@link HttpUriRequest} for the page
   * @param mapper the mapping function to transform the results from YouTrack into types T
   * @since 0.7.0
   */
  Page(
      HttpUriRequest request, 
      ExceptionalFunction<Response, Collection<T>, IOException> mapper
  ) {
    this.request = request;
    this.mapper = mapper;
    this.contents = new ArrayDeque<>();
  }

  @Override
  public boolean hasNext() {
    if (this.contents.isEmpty()) {
      try {
        this.contents.addAll(
            this.mapper.apply(
                new HttpResponseAsResponse(
                    HttpClients.createDefault().execute(this.request)
                )
            )
        );
      } catch (IOException e) {
        throw new UncheckedException(e);
      }
    }

    return !this.contents.isEmpty();
  }

  @Override
  public T next() {
    if (this.hasNext()) {
      return this.contents.pop();
    }

    throw new NoSuchElementException();
  }

  /**
   * Special {@link Page} that always returns {@code false} from {@link #hasNext()} and throws a
   * {@link NoSuchElementException} from {@link #next()}.
   * 
   * @param <T> the type parameter for the results enclosed
   * @since 0.7.0
   */
  public static final class Empty<T> implements Iterator<T> {
    @Override
    public boolean hasNext() {
      return false;
    }

    @Override
    public T next() {
      throw new NoSuchElementException();
    }
  }
}
