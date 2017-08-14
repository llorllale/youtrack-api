/* 
 * Copyright 2017 George Aristy (george.aristy@gmail.com).
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

import org.apache.http.HttpEntity;
import org.llorllale.youtrack.api.response.ParseException;

import java.io.IOException;
import java.util.function.Function;
import javax.xml.bind.JAXBException;

/**
 * Utility class to read the text content received from YouTrack in this 
 * {@link HttpEntity} and convert it to its JAXB representation.
 * @author George Aristy (george.aristy@gmail.com)
 * @param <T> the JAXB root element type class
 * @since 0.1.0
 */
public class HttpEntityAsJaxb<T> implements Function<HttpEntity, T> {
  private final Class<T> rootType;

  /**
   * Ctor.
   * @param rootType the type for the XML's root element
   * @since 0.1.0
   */
  public HttpEntityAsJaxb(Class<T> rootType) {
    this.rootType = rootType;
  }

  @Override
  public T apply(HttpEntity entity) {
    try {
      return new XmlStringAsJaxb<>(
          rootType,
          new InputStreamAsString(entity.getContent()).string()
      ).jaxb();
    } catch (JAXBException | IOException e) {
      throw new ParseException(
          String.format("Unable to parse entity for type %s", 
              rootType.getName()
          ), 
          e
      );
    }
  }
}