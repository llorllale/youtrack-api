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

import org.llorllale.youtrack.api.XmlComment;
import java.time.Instant;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.BeforeClass;
import org.llorllale.youtrack.api.util.XmlStringAsJaxb;

/**
 * Unit tests for {@link XmlComment}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.2.0
 */
public class XmlCommentTest {
  private static org.llorllale.youtrack.api.issues.jaxb.Comment jaxbComment;

  @BeforeClass
  public static void setup() throws Exception {
    jaxbComment = new XmlStringAsJaxb<>(
        org.llorllale.youtrack.api.issues.jaxb.Comment.class, 
        XML
    ).jaxb();
  }

  @Test
  public void testId() {
    assertThat(
        new XmlComment(jaxbComment).id(),
        is(jaxbComment.getId())
    );
  }

  @Test
  public void testCreationDate() {
    assertThat(
        new XmlComment(jaxbComment).creationDate(),
        is(Instant.ofEpochMilli(jaxbComment.getCreated()))
    );
  }

  @Test
  public void testAuthorLoginName() {
    assertThat(
        new XmlComment(jaxbComment).authorLoginName(),
        is(jaxbComment.getAuthor())
    );
  }

  @Test
  public void testIssueId() {
    assertThat(
        new XmlComment(jaxbComment).issueId(),
        is(jaxbComment.getIssueId())
    );
  }

  @Test
  public void testText() {
    assertThat(
        new XmlComment(jaxbComment).text(),
        is(jaxbComment.getText())
    );
  }

  private static final String XML =
"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
"<comment id=\"42-307\" author=\"root\" issueId=\"HBR-63\" deleted=\"false\" text=\"comment 2?\" shownForIssueAuthor=\"false\"\n" +
"         created=\"1267030238721\" updated=\"1267030230127\">\n" +
"  <replies/>\n" +
"</comment>";
}