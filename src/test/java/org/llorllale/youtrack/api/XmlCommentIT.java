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

// @checkstyle AvoidStaticImport (1 line)
import static org.junit.Assert.assertTrue;

import java.util.Random;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.session.PermanentToken;
import org.llorllale.youtrack.api.session.Session;

/**
 * Integration tests for {@link XmlComment}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.9.0
 * @checkstyle MultipleStringLiterals (500 lines)
 * @checkstyle AbbreviationAsWordInName (2 lines)
 */
public final class XmlCommentIT {
  private static Session session;
  private static Issue issue;

  /**
   * Setup.
   * @throws Exception unexpected
   */
  @BeforeClass
  public static void setUpClass() throws Exception {
    final IntegrationTestsConfig config = new IntegrationTestsConfig();
    session = new PermanentToken(
      config.youtrackUrl(),
      config.youtrackUserToken()
    ).login();
    issue = new DefaultYouTrack(session)
      .projects()
      .stream()
      .findAny()
      .get()
      .issues()
      .create(XmlCommentIT.class.getSimpleName(), "");
  }

  /**
   * Test of update method, of class XmlComment.
   * @throws Exception unexpected
   */
  @Test
  public void testUpdate() throws Exception {
    final String initialText = "Comment_" + new Random(System.currentTimeMillis()).nextInt();
    final String finalText = "UpdatedComment_" + new Random(System.currentTimeMillis()).nextInt();
    final Comment comment = new DefaultComments(session, issue)
      .post(initialText)
      .stream()
      .filter(c -> initialText.equals(c.text()))
      .findFirst()
      .get();
    new XmlComment(issue, session, this.xmlObject(comment)).update(finalText);

    assertTrue(
      issue.comments().stream().noneMatch(c -> initialText.equals(c.text()))
    );
  }

  /**
   * Test of delete method, of class XmlComment.
   * @throws Exception unexpected
   */
  @Test
  public void testDelete() throws Exception {
    final String initialText = "Comment_" + new Random(System.currentTimeMillis()).nextInt();
    final Comment comment = new DefaultComments(session, issue)
      .post(initialText)
      .stream()
      .filter(c -> initialText.equals(c.text()))
      .findFirst()
      .get();
    new XmlComment(issue, session, this.xmlObject(comment)).delete();

    assertTrue(
      issue.comments().stream().noneMatch(c -> comment.id().equals(c.id()))
    );
  }

  /**
   * Mock xml.
   * @param base prototype comment
   * @return xml
   * @throws Exception unexpected
   */
  private Xml xmlObject(Comment base) throws Exception {
    final String template
      = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
      // @checkstyle LineLength (1 line)
      + "<comment id=\"%ID%\" author=\"%AUTHOR%\" issueId=\"%ISSUE%\" deleted=\"false\" text=\"%TEXT%\" shownForIssueAuthor=\"false\"\n"
      + "         created=\"%CREATED%\" updated=\"1267030230127\">\n"
      + "  <replies/>\n"
      + "</comment>";
    return new XmlOf(
      new StringAsDocument(
        template
          .replace("%ID%", base.id())
          .replace("%ISSUE%", base.issue().id())
          .replace("%TEXT%", base.text())
          .replace("%CREATED%", String.valueOf(base.creationDate().toEpochMilli()))
      )
    );
  }
}
