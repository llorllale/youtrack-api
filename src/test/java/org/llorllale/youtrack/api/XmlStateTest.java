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
import org.llorllale.youtrack.api.jaxb.StateBundle;
import org.llorllale.youtrack.api.util.XmlStringAsJaxb;

/**
 * Unit tests for {@link XmlState}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.7.0
 */
public class XmlStateTest {
  private static StateBundle stateBundle;

  @BeforeClass
  public static void setup() throws Exception {
    stateBundle = new XmlStringAsJaxb<>(StateBundle.class).apply(STATE_BUNDLE);
  }

  @Test
  public void testAsString() {
    assertThat(
        new XmlState(stateBundle.getState().get(0)).asString(),
        is(stateBundle.getState().get(0).getValue())
    );
  }

  private static final String STATE_BUNDLE =
"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
"<stateBundle name=\"States\">\n" +
"  <state isResolved=\"false\">Submitted</state>\n" +
"  <state isResolved=\"false\">Open</state>\n" +
"  <state isResolved=\"false\">In Progress</state>\n" +
"  <state isResolved=\"false\">To be discussed</state>\n" +
"  <state isResolved=\"false\">Reopened</state>\n" +
"  <state isResolved=\"true\">Can't Reproduce</state>\n" +
"  <state isResolved=\"true\">Duplicate</state>\n" +
"  <state isResolved=\"true\">Fixed</state>\n" +
"  <state isResolved=\"true\">Won't fix</state>\n" +
"  <state description=\"Issue description is incomplete\" isResolved=\"true\">Incomplete</state>\n" +
"  <state isResolved=\"true\">Obsolete</state>\n" +
"  <state description=\"Issue fix is verified\" isResolved=\"true\">Verified</state>\n" +
"</stateBundle>";
}