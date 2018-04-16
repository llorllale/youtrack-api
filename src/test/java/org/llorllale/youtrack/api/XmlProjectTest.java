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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.llorllale.youtrack.api.mock.MockProject;
import org.llorllale.youtrack.api.mock.http.MockSession;

/**
 * Unit tests for {@link XmlProject}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 * @checkstyle MethodName (500 lines)
 * @checkstyle MultipleStringLiterals (500 lines)
 */
public final class XmlProjectTest {
  /**
   * Must return the project's id from the @shortName.
   */
  @Test
  public void testIdFromShortName() {
    assertThat(
      new XmlProject(
        null,
        new MockSession(),
        new XmlOf(new StringAsDocument(
          // @checkstyle LineLength (1 line)
          "<project versions=\"[2.0, 2.0.1, 2.0.2, 2.0.3, 2.0.4, 2.0.5, 2.0.6, 2.0.7, 2.0.8]\" name=\"Hibero\" shortName=\"HBR\" description=\"Makes developing Hibernate applications a pleasure.\" isImporting=\"false\"/>"
        ))
      ).id(),
      is("HBR")
    );
  }

  /**
   * Must return the project's id from the @id.
   * @since 0.6.0
   */
  @Test
  public void testIdFromId() {
    assertThat(
      new XmlProject(
        null,
        new MockSession(),
        new XmlOf(new StringAsDocument(
          // @checkstyle LineLength (1 line)
          "<project versions=\"[2.0, 2.0.1, 2.0.2, 2.0.3, 2.0.4, 2.0.5, 2.0.6, 2.0.7, 2.0.8]\" name=\"Hibero\" id=\"IT-TEST\" description=\"Makes developing Hibernate applications a pleasure.\" isImporting=\"false\"/>"
        ))
      ).id(),
      is("IT-TEST")
    );
  }

  /**
   * Must return the project's @name.
   */
  @Test
  public void testName() {
    assertThat(
      new XmlProject(
        null,
        new MockSession(),
        new XmlOf(new StringAsDocument(
          // @checkstyle LineLength (1 line)
          "<project versions=\"[2.0, 2.0.1, 2.0.2, 2.0.3, 2.0.4, 2.0.5, 2.0.6, 2.0.7, 2.0.8]\" name=\"Hibero\" id=\"IT-TEST\" description=\"Makes developing Hibernate applications a pleasure.\" isImporting=\"false\"/>"
        ))
      ).name(),
      is("Hibero")
    );
  }

  /**
   * Must return the project's @description.
   */
  @Test
  public void testDescription() {
    assertThat(
      new XmlProject(
        null,
        new MockSession(),
        new XmlOf(new StringAsDocument(
          // @checkstyle LineLength (1 line)
          "<project versions=\"[2.0, 2.0.1, 2.0.2, 2.0.3, 2.0.4, 2.0.5, 2.0.6, 2.0.7, 2.0.8]\" name=\"Hibero\" id=\"IT-TEST\" description=\"Makes developing Hibernate applications a pleasure.\" isImporting=\"false\"/>"
        ))
      ).description().get(),
      is("Makes developing Hibernate applications a pleasure.")
    );
  }

  /**
   * Cannot be equal to {@code null}.
   */
  @Test
  public void notEqualsNull() {
    assertFalse(
      new XmlProject(
        null, null,
        new XmlOf(new StringAsDocument("<project id=\"IT-TEST\"/>"))
      ).equals(null)
    );
  }

  /**
   * Cannot be equal to an object of another type.
   */
  @Test
  public void notEqualsObject() {
    assertFalse(
      new XmlProject(
        null, null,
        new XmlOf(new StringAsDocument("<project id=\"IT-TEST\"/>"))
      ).equals(new Object())
    );
  }

  /**
   * Must be equal to itself.
   */
  @Test
  public void equalsItself() {
    final Project project = new XmlProject(
      null, null,
      new XmlOf(new StringAsDocument("<project id=\"IT-TEST\"/>"))
    );
    assertTrue(
      project.equals(project)
    );
  }

  /**
   * Two project's are equal if they both have the same id.
   */
  @Test
  public void equalsOtherProjectSameId() {
    assertTrue(
      new XmlProject(
        null, null,
        new XmlOf(new StringAsDocument("<project id=\"HBR\"/>"))
      ).equals(new MockProject("HBR", "", ""))
    );
  }

  /**
   * Cannot be equal to another project with a different id.
   */
  @Test
  public void notEqualsOtherProjectWithDiffId() {
    assertFalse(
      new XmlProject(
        null, null,
        new XmlOf(new StringAsDocument("<project id=\"HBR\"/>"))
      ).equals(new MockProject("IT-TEST", "", ""))
    );
  }
}
