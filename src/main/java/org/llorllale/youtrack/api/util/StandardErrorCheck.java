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

import java.util.function.Predicate;

/**
 * Used to test if the text contents of a response from YouTrack do NOT signify an "error".
 * 
 * <p>YouTrack sends error messages in the response using this format:</p>
 * <pre>
 *     &lt;?xml version="1.0" encoding="UTF-8" standalone="yes"?>
 *     &lt;error>SOME_MESSAGE&lt;/error>
 * </pre>
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.6.0
 */
public final class StandardErrorCheck implements Predicate<String> {
  @Override
  public boolean test(String xml) {
    return !xml.contains("<error>");
  }
}
