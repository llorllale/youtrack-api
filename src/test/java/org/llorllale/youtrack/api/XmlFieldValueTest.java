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

package org.llorllale.youtrack.api;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.jaxb.Value;
import org.llorllale.youtrack.api.util.XmlStringAsJaxb;

/**
 * Unit tests for {@link XmlFieldValue}
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 */
public class XmlFieldValueTest {
  private static Value jaxb;

  @BeforeClass
  public static void setup() throws Exception {
    jaxb = new XmlStringAsJaxb<>(Value.class).apply(XML);
  }

  @Test
  public void testAsString() {
    assertThat(
        new XmlFieldValue(jaxb, null).asString(),
        is(jaxb.getValue())
    );
  }

  private static final String XML = "<value>Bug</value>";
}