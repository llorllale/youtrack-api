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
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.llorllale.youtrack.api.jaxb.WorkType;
import org.llorllale.youtrack.api.util.XmlStringAsJaxb;

/**
 * Unit tests for {@link XmlTimeTrackEntryType}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 */
public class XmlTimeTrackEntryTypeTest {
  private static WorkType jaxb;

  @BeforeClass
  public static void setup() throws Exception {
    jaxb = new XmlStringAsJaxb<>(WorkType.class).apply(WORK_TYPE);
  }

  @Test
  public void testAsString() {
    assertThat(
        new XmlTimeTrackEntryType(jaxb).asString(),
        is(jaxb.getName())
    );
  }

  private static final String WORK_TYPE =
"    <worktype>\n" +
"      <name>Development</name>\n" +
"      <id>43-0</id>\n" +
"      <autoAttached>true</autoAttached>\n" +
"      <url>http://localhost/rest/admin/timetracking/worktype/43-0</url>\n" +
"    </worktype>";
}