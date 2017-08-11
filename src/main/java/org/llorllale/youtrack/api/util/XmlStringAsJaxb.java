/**
 * Copyright 2017 George Aristy
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

import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

/**
 * Utility class to unmarshal an XML document in string form to its JAXB 
 * representation.
 * @author George Aristy
 * @param <T>
 * @since 1.0.0
 */
public class XmlStringAsJaxb<T> {
  private final Class<T> rootType;
  private final String xml;

  /**
   * 
   * @param rootType
   * @param xml 
   * @since 1.0.0
   */
  public XmlStringAsJaxb(Class<T> rootType, String xml) {
    this.rootType = rootType;
    this.xml = xml;
  }

  /**
   * 
   * @return
   * @throws JAXBException 
   * @since 1.0.0
   */
  public T asJaxb() throws JAXBException {
    final JAXBContext ctx = JAXBContext.newInstance(rootType);
    final Unmarshaller um = ctx.createUnmarshaller();
    return um.unmarshal(new StreamSource(new StringReader(xml)), rootType)
            .getValue();
  }
}