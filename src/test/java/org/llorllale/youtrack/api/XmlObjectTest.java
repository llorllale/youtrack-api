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

import java.util.Optional;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit tests for {@link XmlObject}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public class XmlObjectTest {
  private static XmlObject xml;

  @BeforeClass
  public static void setup() throws ParseException {
    xml = new XmlObject(new StringAsDocument(XML));
  }

  /**
   * {@link XmlObject#textOf(java.lang.String)} must return the string value if given an 
   * valid xpath.
   * 
   * @since 1.0.0
   */
  @Test
  public void textAtWithValidXpath() {
    assertThat(
        xml.textOf("@id"),
        is(Optional.of("HBR-63"))
    );
  }

  /**
   * Result of {@link XmlObject#textOf(java.lang.String)} with an invalid xpath must be an empty
   * optional.
   * 
   * @since 1.0.0
   */
  @Test
  public void textAtWithInvalidXpath() {
    assertFalse(
        xml.textOf("//asdf").isPresent()
    );
  }

  /**
   * Result of {@link XmlObject#child(java.lang.String)} with an invalid xpath must be an empty
   * optional.
   * 
   * @since 1.0.0
   */
  @Test
  public void childWithInvalidXpath() {
    assertFalse(
        xml.child("//asdfd").isPresent()
    );
  }

  /**
   * {@link XmlObject#child(java.lang.String)} with a valid xpath must return the correct 
   * {@link XmlObject}
   * 
   * @since 1.0.0
   */
  @Test
  public void childWithValidXpath() {
    assertThat(
        xml.child("//field[@name='attachments']").get().textOf("value").get(),
        is("uploadFile.html")
    );
  }

  /**
   * Result of {@link XmlObject#children(java.lang.String)} with valid xpath should consist of a 
   * collection of the expected size.
   * 
   * @since 1.0.0
   */
  @Test
  public void testChildrenWithValidXpath() {
    assertThat(
        xml.children("//comment").size(),
        is(2)
    );
  }

  /**
   * Result of {@link XmlObject#children(java.lang.String)} with an invalid xpath should be an 
   * empty collection.
   * 
   * @since 1.0.0
   */
  @Test
  public void testChildrenWithInvalidXpath() {
    assertThat(
        xml.children("//asdfb"),
        is(empty())
    );
  }

  private static final String XML =
"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
"<issue id=\"HBR-63\">\n" +
"    <field name=\"attachments\">\n" +
"        <value url=\"/_persistent/uploadFile.html?file=45-46&amp;v=0&amp;c=true\">uploadFile.html</value>\n" +
"    </field>\n" +
"    <comment id=\"42-306\" author=\"root\" issueId=\"HBR-63\" deleted=\"false\" text=\"comment 1!\" shownForIssueAuthor=\"false\"\n" +
"             created=\"1267030230127\">\n" +
"        <replies/>\n" +
"    </comment>\n" +
"    <comment id=\"42-307\" author=\"root\" issueId=\"HBR-63\" deleted=\"false\" text=\"comment 2?\" shownForIssueAuthor=\"false\"\n" +
"             created=\"1267030238721\" updated=\"1267030230127\">\n" +
"        <replies/>\n" +
"    </comment>\n" +
"    <field name=\"Priority\">\n" +
"        <value>Normal</value>\n" +
"    </field>\n" +
"    <field name=\"Type\">\n" +
"        <value>Bug</value>\n" +
"    </field>\n" +
"    <field name=\"State\">\n" +
"        <value>Won't fix</value>\n" +
"    </field>\n" +
"    <field name=\"Assignee\">\n" +
"        <value>beto</value>\n" +
"    </field>\n" +
"    <field name=\"Subsystem\">\n" +
"        <value>Configuration</value>\n" +
"    </field>\n" +
"    <field name=\"Fix versions\">\n" +
"        <value>2.0</value>\n" +
"        <value>2.0.5</value>\n" +
"        <value>2.0.7</value>\n" +
"    </field>\n" +
"    <field name=\"cf\">\n" +
"        <value>0</value>\n" +
"        <value>!</value>\n" +
"    </field>\n" +
"    <field name=\"scf\">\n" +
"        <value>1265835603000</value>\n" +
"    </field>\n" +
"    <field name=\"links\">\n" +
"        <value type=\"Depend\" role=\"depends on\">HBR-62</value>\n" +
"        <value type=\"Duplicate\" role=\"duplicates\">HBR-57</value>\n" +
"        <value type=\"Duplicate\" role=\"is duplicated by\">HBR-54</value>\n" +
"        <value type=\"Relates\" role=\"relates to\">HBR-49</value>\n" +
"        <value type=\"Relates\" role=\"is related to\">HBR-51</value>\n" +
"        <value type=\"Depend\" role=\"is required for\">HBR-49</value>\n" +
"    </field>\n" +
"    <field name=\"projectShortName\">\n" +
"        <value>HBR</value>\n" +
"    </field>\n" +
"    <field name=\"numberInProject\">\n" +
"        <value>63</value>\n" +
"    </field>\n" +
"    <field name=\"summary\">\n" +
"        <value>summary</value>\n" +
"    </field>\n" +
"    <field name=\"description\">\n" +
"        <value>description</value>\n" +
"    </field>\n" +
"    <field name=\"created\">\n" +
"        <value>1262171005630</value>\n" +
"    </field>\n" +
"    <field name=\"updated\">\n" +
"        <value>1267630833573</value>\n" +
"    </field>\n" +
"    <field name=\"updaterName\">\n" +
"        <value>root</value>\n" +
"    </field>\n" +
"    <field name=\"resolved\">\n" +
"        <value>1267030108251</value>\n" +
"    </field>\n" +
"    <field name=\"reporterName\">\n" +
"        <value>root</value>\n" +
"    </field>\n" +
"    <field name=\"commentsCount\">\n" +
"        <value>2</value>\n" +
"    </field>\n" +
"    <field name=\"votes\">\n" +
"        <value>0</value>\n" +
"    </field>\n" +
"</issue>";
}
