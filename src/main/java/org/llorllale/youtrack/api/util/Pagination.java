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

package org.llorllale.youtrack.api.util;

import org.apache.http.HttpEntity;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An {@link Iterator} that encapsulates a paginated resource from the YouTrack server.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @param <T> the resource's type
 * @since 0.7.0
 * @see Page
 */
public class Pagination<T> implements Iterator<T> {
  private final PageUri pageRequest;
  private final ExceptionalFunction<HttpEntity, Collection<T>, IOException> mapper;

  private Page<T> page;

  /**
   * Ctor.
   * @param pageRequest the supplier for each {@link Page page's} uri
   * @param mapper maps each page's URI into its corresponding contents
   * @since 0.7.0
   */
  public Pagination(
      PageUri pageRequest,
      ExceptionalFunction<HttpEntity, Collection<T>, IOException> mapper
  ) {
    this.pageRequest = pageRequest;
    this.mapper = mapper;
    this.page = new Page.Empty<>();
  }

  @Override
  public boolean hasNext() {
    if (!page.hasNext()) {
      page = new Page<>(pageRequest.get(), mapper);
    }

    return page.hasNext();
  }

  @Override
  public T next() {
    if (hasNext()) {
      return page.next();
    }

    throw new NoSuchElementException();
  }
}
