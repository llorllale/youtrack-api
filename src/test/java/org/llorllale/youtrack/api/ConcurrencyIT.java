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

import java.io.IOException;
import java.util.function.Supplier;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Test;
import org.llorllale.cactoos.matchers.RunsInThreads;
import org.llorllale.youtrack.api.http.Client;
import org.llorllale.youtrack.api.http.Pooled;
import org.llorllale.youtrack.api.session.PermanentToken;

/**
 * Concurrency integration tests.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.1.0
 * @checkstyle AbbreviationAsWordInName (2 lines)
 */
public final class ConcurrencyIT {

  /**
   * The basic {@link Client} should run fine (no hanging) with two concurrent threads, since
   * calling {@link HttpClientBuilder#build()} without previous configuration results in the
   * {@link PoolingHttpClientConnectionManager} being used with default values,
   * which includes a maximum of 2 concurrent connections per host, and no more
   * than 20 connections in total.
   * @see <a href="https://hc.apache.org/httpcomponents-client-ga/tutorial/html/connmgmt.html">
   *    Pooling connection manager</a>
   * @since 1.1.0
   * @todo #240 Concurrency tests have been implemented. However, the build is failing with errors
   *  internal to youtrack, similar to "jetbrains.exodus.ExodusException: Can't read full bytes
   *  from log [/opt/youtrack/data/youtrack] with address 1245184". After that is fixed,
   *  make sure all connections are released as per #240, and make sure these tests pass.
   */
  @Test
  public void basicClient() {
    final int threads = 2;
    assertThat(
      String.format("can't run the basic client in %s threads", threads),
      this::steps,
      new RunsInThreads<>(new Client(), threads)
    );
  }

  /**
   * The {@link Pooled} client should be able to run concurrently in however many threads
   * you specify.
   * @since 1.1.0
   */
  @Test
  public void pooledClient() {
    final int threads = 50;
    assertThat(
      String.format("can't run the pooled client in %s threads", threads),
      this::steps,
      new RunsInThreads<>(new Pooled(threads, new Client()), threads)
    );
  }

  /**
   * Test steps per thread.
   * @param client client to use
   * @return whether steps were executed successfully or not
   */
  private boolean steps(Supplier<HttpClientBuilder> client) {
    final int iterations = 20;
    final IntegrationTestsConfig config = new IntegrationTestsConfig();
    boolean result;
    try {
      for (int i = 0; i < iterations; i++) {
        final Issue issue = new DefaultYouTrack(
          new PermanentToken(
            config.youtrackUrl(),
            config.youtrackUserToken()
          ),
          client
        ).projects().stream()
          .findAny().get()
          .issues()
          .create("concurrency test", "description");
        issue.comments().post("This is a test!");
      }
      result = true;
    } catch (IOException e) {
      e.printStackTrace();
      result = false;
    }
    return result;
  }
}
