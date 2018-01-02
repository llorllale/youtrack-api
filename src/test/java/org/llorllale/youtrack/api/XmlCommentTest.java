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

import java.time.Instant;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.llorllale.youtrack.api.mock.MockIssue;
import org.llorllale.youtrack.api.mock.MockProject;
import org.llorllale.youtrack.api.mock.http.MockSession;
import org.llorllale.youtrack.api.session.Session;

/**
 * Unit tests for {@link XmlComment}.
 * 
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public class XmlCommentTest {
  private static Xml xmlObject;

  @BeforeClass
  public static void setup() throws Exception {
    xmlObject = new XmlOf(new StringAsDocument(XML));
  }

  @Test
  public void testId() throws Exception {
    assertThat(
        new XmlComment(issue(), session(), xmlObject).id(),
        is("42-307")
    );
  }

  @Test
  public void testCreationDate() throws Exception {
    assertThat(
        new XmlComment(issue(), session(), xmlObject).creationDate(),
        is(Instant.ofEpochMilli(1267030238721L))
    );
  }

  @Test
  public void testText() throws Exception {
    assertThat(
        new XmlComment(issue(), session(), xmlObject).text(),
        is("comment 2?")
    );
  }

  private Issue issue() {
    return new MockIssue(new MockProject("PR-1", "", ""), "I-23");
  }

  private Session session() {
    return new MockSession();
  }

  private static final String XML =
"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
"<comment id=\"42-307\" author=\"root\" issueId=\"HBR-63\" deleted=\"false\" text=\"comment 2?\" shownForIssueAuthor=\"false\"\n" +
"         created=\"1267030238721\" updated=\"1267030230127\">\n" +
"  <replies/>\n" +
"</comment>";
}