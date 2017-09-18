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

package org.llorllale.youtrack.api.issues;

import org.llorllale.youtrack.api.XmlWorkItem;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.BeforeClass;
import org.llorllale.youtrack.api.util.XmlStringAsJaxb;

/**
 * Unit tests for {@link XmlWorkItem}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.3.0
 */
public class XmlWorkItemTest {
  private static org.llorllale.youtrack.api.issues.jaxb.WorkItem jaxbWorkItem;

  @BeforeClass
  public static void setup() throws Exception {
    jaxbWorkItem = new XmlStringAsJaxb<>(
        org.llorllale.youtrack.api.issues.jaxb.WorkItem.class, 
        XML
    ).jaxb();
  }

  /**
   * Tests correct implementation of {@link XmlWorkItem#date()}.
   * @since 0.3.0
   */
  @Test
  public void testDate() {
    assertThat(
        new XmlWorkItem(jaxbWorkItem).date(),
        is(
            Instant.ofEpochMilli(jaxbWorkItem.getDate())
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        )
    );
  }

  /**
   * Tests correct implementation of {@link XmlWorkItem#duration()}. 
   * @since 0.3.0
   */
  @Test
  public void testDuration() {
    assertThat(
        new XmlWorkItem(jaxbWorkItem).duration(),
        is(Duration.ofMinutes(jaxbWorkItem.getDuration()))
    );
  }

  /**
   * Tests correct implementation of {@link XmlWorkItem#description()}.
   * @since 0.3.0
   */
  @Test
  public void testDescription() {
    assertThat(
        new XmlWorkItem(jaxbWorkItem).description().get(),
        is(jaxbWorkItem.getDescription())
    );
  }

  /**
   * Tests correct implementation of {@link XmlWorkItem#authorLoginName()}.
   * @since 0.3.0
   */
  @Test
  public void testAuthorLoginName() {
    assertThat(
        new XmlWorkItem(jaxbWorkItem).authorLoginName(),
        is(jaxbWorkItem.getAuthor().getLogin())
    );
  }

  private static final String XML =
"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
"<workItem url=\"http://unit-258.labs.intellij.net:8080/charisma/rest/issue/HBR-63/timetracking/workitem/101-1\">\n" +
"  <id>101-1</id>\n" +
"  <date>1480204800000</date>\n" +
"  <duration>240</duration>\n" +
"  <description>first work item</description>\n" +
"  <author login=\"root\" url=\"http://unit-258.labs.intellij.net:8080/charisma/rest/admin/user/root\"/>\n" +
"</workItem>";
}