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
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.util.UUID;
import org.hamcrest.core.IsEqual;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.http.Client;
import org.llorllale.youtrack.api.http.Pooled;
import org.llorllale.youtrack.api.session.Login;
import org.llorllale.youtrack.api.session.PermanentToken;

/**
 * Integration tests for {@link XmlAttachment}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.1.0
 * @checkstyle AbbreviationAsWordInName (3 lines)
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public final class XmlAttachmentIT {
  private static IntegrationTestsConfig config;
  private static Login login;
  private static Issue issue;

  /**
   * Setup.
   * @throws Exception unexpected
   * @since 1.1.0
   */
  @BeforeClass
  public static void setup() throws Exception {
    config = new IntegrationTestsConfig();
    login = new PermanentToken(
      config.youtrackUrl(), 
      config.youtrackUserToken()
    );
    // @checkstyle MagicNumber (1 line)
    issue = new DefaultYouTrack(login, new Pooled(10, new Client()))
      .projects()
      .stream()
      .findFirst()
      .get()
      .issues()
      .create(DefaultAttachmentsIT.class.getSimpleName(), "Description");
  }

  /**
   * XmlAttachment can effectively delete itself.
   * @throws Exception unexpected
   * @since 1.1.0
   */
  @Test
  public void deletesAttachment() throws Exception {
    final String name = UUID.randomUUID().toString();
    assertThat(
      issue.attachments().create(
        name, "text/plain", new ByteArrayInputStream("test".getBytes())
      ).filter(att -> name.equals(att.name()))
        .findAny().get()
        .delete()
        .anyMatch(att -> name.equals(att.name())),
      new IsEqual<>(false)
    );
  }

  /**
   * XmlAttachment can read the contents.
   * @throws Exception unexpected
   * @since 1.1.0
   */
  @Test
  public void downloadsAttachment() throws Exception {
    final String name = UUID.randomUUID().toString();
    final String contents = UUID.randomUUID().toString();
    assertThat(
      new InputStreamAsString().apply(
        issue.attachments().create(
          name, "text/plain", new ByteArrayInputStream(contents.getBytes())
        ).filter(att -> name.equals(att.name()))
          .findAny().get()
          .contents()
        ),
      new IsEqual<>(contents)
    );
  }
}
