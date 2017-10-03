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

import org.apache.http.client.methods.HttpUriRequest;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Combines a supplier of page numbers and a mapper of these numbers to working 
 * {@link HttpUriRequest http requests} where each combination belongs to a distinct {@link Page}
 * of results.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.7.0
 * @see Pagination
 */
public class PageUri implements Supplier<HttpUriRequest> {
  private final Supplier<Integer> pageNum;
  private final Function<Integer, HttpUriRequest> combiner;

  /**
   * Ctor.
   * 
   * @param pageNum the page number supplier
   * @param combiner accepts a value from {@code pageNum} and produces a working 
   *     {@link HttpUriRequest} for a {@link Page}
   * @since 0.7.0
   */
  public PageUri(Supplier<Integer> pageNum, Function<Integer, HttpUriRequest> combiner) {
    this.pageNum = pageNum;
    this.combiner = combiner;
  }

  @Override
  public HttpUriRequest get() {
    return combiner.apply(pageNum.get());
  }
}
