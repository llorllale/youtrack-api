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
import java.io.IOException;
import java.util.UUID;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClients;
import org.hamcrest.core.IsEqual;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.session.Login;
import org.llorllale.youtrack.api.session.PermanentToken;

/**
 * Integration tests for {@link DefaultAttachments}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.1.0
 * @checkstyle MultipleStringLiterals (500 lines)
 * @checkstyle MethodName (500 lines)
 * @checkstyle AbbreviationAsWordInName (3 lines)
 */
public final class DefaultAttachmentsIT {
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
    issue = new DefaultYouTrack(login)
      .projects()
      .stream()
      .findFirst()
      .get()
      .issues()
      .create(DefaultAttachmentsIT.class.getSimpleName(), "Description");
  }

  /**
   * Creates the attachment.
   * @throws Exception unexpected
   * @since 1.1.0
   */
  @Test
  public void createsAttachment() throws Exception {
    assertThat(
      new DefaultAttachments(issue, login, HttpClients::createDefault)
        .create(
          "test.txt", ContentType.TEXT_PLAIN.getMimeType(),
          new ByteArrayInputStream("This is a test attachment".getBytes())
        ).count(),
      new IsEqual<>(1L)
    );
  }

  /**
   * Creates the attachment with the given name.
   * @throws Exception unexpected
   * @since 1.1.0
   */
  @Test
  public void createsAttachmentWithName() throws Exception {
    final String name = UUID.randomUUID().toString();
    assertThat(
      new DefaultAttachments(issue, login, HttpClients::createDefault)
        .create(
          name, ContentType.TEXT_PLAIN.getMimeType(),
          new ByteArrayInputStream("This is a test attachment".getBytes())
        ).anyMatch(a -> name.equals(a.name())),
      new IsEqual<>(true)
    );   
  }

  /**
   * Creates the attachment with the same user login used in this test.
   * @throws Exception unexpected
   * @since 1.1.0
   */
  @Test
  public void createsTheAttachmentWithTheUser() throws Exception {
    final String name = UUID.randomUUID().toString();
    assertThat(
      new DefaultAttachments(issue, login, HttpClients::createDefault)
        .create(
          name, ContentType.TEXT_PLAIN.getMimeType(),
          new ByteArrayInputStream("This is a test attachment".getBytes())
        ).anyMatch(a -> {
          try {
            return name.equals(a.name()) && config.youtrackUser().equals(a.creator().name());
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        }),
      new IsEqual<>(true)
    );   
  }
}
