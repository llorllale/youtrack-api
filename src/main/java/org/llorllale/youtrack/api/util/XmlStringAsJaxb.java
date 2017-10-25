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

import org.llorllale.youtrack.api.util.response.ParseException;

import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

/**
 * Utility class to unmarshal an XML document in string form to its JAXB 
 * representation.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @param <T> the type class of the XML root element
 * @since 0.1.0
 */
public class XmlStringAsJaxb<T> implements ExceptionalFunction<String, T, ParseException> {
  private final Class<T> rootType;

  /**
   * Ctor.
   * 
   * @param rootType the type class of the XML root element
   * @since 0.1.0
   */
  public XmlStringAsJaxb(Class<T> rootType) {
    this.rootType = rootType;
  }

  @Override
  public T apply(String xml) throws ParseException {
    try {
      final JAXBContext ctx = JAXBContext.newInstance(rootType);
      final Unmarshaller um = ctx.createUnmarshaller();
      return um.unmarshal(
          new StreamSource(
              new StringReader(xml)
          ), rootType
      ).getValue();
    } catch (JAXBException e) {
      throw new ParseException(
          String.format("Error parsing xml: rootType=%s xml=%s", rootType, xml), 
          e
      );
    }
  }
}