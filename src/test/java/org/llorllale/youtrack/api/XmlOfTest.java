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
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.Optional;
import org.junit.Test;

/**
 * Unit tests for {@link Xml}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 * @checkstyle MethodName (500 lines)
 * @checkstyle MultipleStringLiterals (500 lines)
 */
public final class XmlOfTest {
  /**
   * {@link Xml#textOf(java.lang.String)} must return the string value if given an 
   * valid xpath.
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void textAtWithValidXpath() throws Exception {
    assertThat(
      new XmlOf(new StringAsDocument(
        "<issue id='123'>"
        + "  <summary>Test Summary</summary>"
        + "  <description>Test Description</description>"
        + "</issue>"
      )).textOf("@id"),
      is(Optional.of("123"))
    );
  }

  /**
   * Result of {@link Xml#textOf(java.lang.String)} with an invalid xpath must be an empty
   * optional.
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void textAtWithInvalidXpath() throws Exception {
    assertThat(
      new XmlOf(new StringAsDocument(
        "<issue id='123'>"
        + "  <summary>Test Summary</summary>"
        + "  <description>Test Description</description>"
        + "</issue>"
      )).textOf("//asdf"),
      is(Optional.empty())
    );
  }

  /**
   * Result of {@link Xml#child(java.lang.String)} with an invalid xpath must be an empty
   * optional.
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void childWithInvalidXpath() throws Exception {
    assertFalse(
      new XmlOf(new StringAsDocument(
        "<issue id='123'>"
        + "  <summary>Test Summary</summary>"
        + "  <description>Test Description</description>"
        + "</issue>"
      )).child("//asdf").isPresent()
    );
  }

  /**
   * {@link Xml#child(java.lang.String)} with a valid xpath must return the correct 
   * {@link Xml}.
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void childWithValidXpath() throws Exception {
    assertThat(
      new XmlOf(new StringAsDocument(
        "<issue id='123'>"
        + "  <summary>Test Summary</summary>"
        + "  <description>Test Description</description>"
        + "</issue>"
      )).child("description").get().textOf("text()").get(),
      is("Test Description")
    );
  }

  /**
   * Result of {@link Xml#children(java.lang.String)} with valid xpath should consist of a 
   * collection of the expected size.
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void testChildrenWithValidXpath() throws Exception {
    assertThat(
      new XmlOf(new StringAsDocument(
        "<issue id='123'>"
        + "  <summary>Test Summary</summary>"
        + "  <description>Test Description</description>"
        + "  <fields>"
        + "    <field name='field1'/>"
        + "    <field name='field2'/>"
        + "  </fields>"
        + "</issue>"
      )).children("//field").size(),
      is(2)
    );
  }

  /**
   * Result of {@link Xml#children(java.lang.String)} with an invalid xpath should be an 
   * empty collection.
   * @throws Exception unexpected
   * @since 1.0.0
   */
  @Test
  public void testChildrenWithInvalidXpath() throws Exception {
    assertThat(
      new XmlOf(new StringAsDocument(
        "<issue id='123'>"
        + "  <summary>Test Summary</summary>"
        + "  <description>Test Description</description>"
        + "  <fields>"
        + "    <field name='field1'/>"
        + "    <field name='field2'/>"
        + "  </fields>"
        + "</issue>"
      )).children("//asdf"),
      is(empty())
    );
  }
}
