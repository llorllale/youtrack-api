/*
 * Copyright 2017 George Aristy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.llorllale.youtrack.api;

// @checkstyle AvoidStaticImport (4 lines)
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.llorllale.youtrack.api.mock.MockTimeTrackEntryType;

/**
 * Unit tests for {@link XmlTimeTrackEntryType}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.8.0
 * @checkstyle MultipleStringLiterals (500 lines)
 * @checkstyle MethodName (500 lines)
 */
public final class XmlTimeTrackEntryTypeTest {
  /**
   * {@link XmlTimeTrackEntryType#asString()} should return the work item's name.
   * @since 0.8.0
   */
  @Test
  public void testAsString() {
    assertThat(
      new XmlTimeTrackEntryType(new XmlOf(
        "<worktype>\n"
        + "  <name>Development</name>\n"
        + "  <id>43-0</id>\n"
        + "  <autoAttached>true</autoAttached>\n"
        + "  <url>http://localhost/rest/admin/timetracking/worktype/43-0</url>\n"
        + "</worktype>"
      )).asString(),
      is("Development")
    );
  }

  /**
   * The hashcode should equal the work item name's hashcode.
   * @since 1.0.0
   */
  @Test
  public void testHashCode() {
    assertThat(
      new XmlTimeTrackEntryType(new XmlOf(
        "<worktype>\n"
        + "  <name>Development</name>\n"
        + "  <id>43-0</id>\n"
        + "  <autoAttached>true</autoAttached>\n"
        + "  <url>http://localhost/rest/admin/timetracking/worktype/43-0</url>\n"
        + "</worktype>"
      )).hashCode(),
      is("Development".hashCode())
    );
  }

  /**
   * Two time track entry types are equal if both encapsulate a workItem with the same name.
   * @since 1.0.0
   */
  @Test
  public void equals() {
    assertThat(
      new XmlTimeTrackEntryType(new XmlOf(
        "<worktype>\n"
        + "  <name>Development</name>\n"
        + "  <id>43-0</id>\n"
        + "  <autoAttached>true</autoAttached>\n"
        + "  <url>http://localhost/rest/admin/timetracking/worktype/43-0</url>\n"
        + "</worktype>"
      )),
      is(new MockTimeTrackEntryType("Development"))
    );
  }

  /**
   * Two time track entry types are not equal if they encapsulate workItems with different names.
   * @since 1.0.0
   */
  @Test
  public void equalsWithDifferentTypeIsFalse() {
    assertThat(
      new XmlTimeTrackEntryType(new XmlOf(
        "<worktype>\n"
        + "  <name>Development</name>\n"
        + "  <id>43-0</id>\n"
        + "  <autoAttached>true</autoAttached>\n"
        + "  <url>http://localhost/rest/admin/timetracking/worktype/43-0</url>\n"
        + "</worktype>"
      )),
      is(not(new MockTimeTrackEntryType("Some other type")))
    );
  }

  /**
   * A time track entry cannot be equal to {@code null}.
   * @since 1.0.0
   */
  @Test
  public void equalsWithNullIsFalse() {
    assertFalse(
      new XmlTimeTrackEntryType(new XmlOf(
        "<worktype>\n"
        + "  <name>Development</name>\n"
        + "  <id>43-0</id>\n"
        + "  <autoAttached>true</autoAttached>\n"
        + "  <url>http://localhost/rest/admin/timetracking/worktype/43-0</url>\n"
        + "</worktype>"
      )).equals(null)
    );
  }

  /**
   * A time track entry cannot be equal an object of a type that is not assignable from
   * {@link TimeTrackEntryType}.
   * @since 1.0.0
   */
  @Test
  public void equalsWithObjectIsFalse() {
    assertFalse(
      new XmlTimeTrackEntryType(new XmlOf(
        "<worktype>\n"
        + "  <name>Development</name>\n"
        + "  <id>43-0</id>\n"
        + "  <autoAttached>true</autoAttached>\n"
        + "  <url>http://localhost/rest/admin/timetracking/worktype/43-0</url>\n"
        + "</worktype>"
      )).equals(new Object())
    );
  }
}
