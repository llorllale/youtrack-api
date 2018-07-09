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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.hamcrest.core.IsEqual;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.session.Login;
import org.llorllale.youtrack.api.session.PermanentToken;

/**
 * Integration tests for {@link XmlIssue}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.1.0
 * @checkstyle MethodName (200 lines)
 * @checkstyle AbbreviationAsWordInName (2 lines)
 */
public final class XmlIssueIT {
  private static IntegrationTestsConfig config;
  private static Login login;

  /**
   * Setup.
   */
  @BeforeClass
  public static void setup() {
    config = new IntegrationTestsConfig();
    login = new PermanentToken(
      config.youtrackUrl(),
      config.youtrackUserToken()
    );
  }

  /**
   * XmlIssue can read attachments multiple times without hanging.
   * @throws IOException unexpected
   * @since 1.1.0
   */
  @Test
  public void canReadAttachmentsMultipleTimesWithoutHanging() throws IOException {
    final Issue issue = new DefaultYouTrack(login)
      .projects()
      .get(config.youtrackTestProjectId()).get()
      .issues()
      .create(XmlIssueIT.class.getSimpleName(), "canReadAttachmentsMultipleTimesWithoutHanging");
    issue.attachments().create(
      "test.txt", "text/plain",
      new ByteArrayInputStream("test".getBytes())
    );
    // @checkstyle MagicNumber (1 line)
    for (int i = 0; i < 20; i++) {
      assertThat(
        issue.attachments().count() > 0,
        new IsEqual<>(true)
      );
    }
  }

  /**
   * XmlIssue can read attachments from multiple threads without hanging.
   * @throws IOException unexpected
   * @since 1.1.0
   * @checkstyle MagicNumber (3 lines)
   */
  @SuppressWarnings("checkstyle:NPathComplexity")
  @Test(timeout = 5000L)
  public void canReadAttachmentsFromMultipleThreads() throws IOException {
    final Issue issue = new DefaultYouTrack(login)
      .projects()
      .get(config.youtrackTestProjectId()).get()
      .issues()
      .create(XmlIssueIT.class.getSimpleName(), "canReadAttachmentsFromMultipleThreads");
    issue.attachments().create(
      "file.txt", "text/xml",
      new ByteArrayInputStream("<empty/>".getBytes())
    );
    final int count = 50;
    final ExecutorService threads = Executors.newFixedThreadPool(count);
    final List<Future<Long>> counts = new ArrayList<>();
    try {
      for (int i = 0; i < count; i++) {
        counts.add(threads.submit(() -> issue.attachments().count()));
      }
    } finally {
      threads.shutdown();
    }
    assertThat(
      counts.stream()
        .mapToLong(future -> {
          try {
            return future.get();
          } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
          }
        }).sum(),
      new IsEqual<>((long) count)
    );
  }
}
