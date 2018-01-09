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

import java.time.Duration;
import java.time.LocalDate;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.session.PermanentTokenLogin;
import org.llorllale.youtrack.api.session.Session;

/**
 * Integration tests for {@link DefaultIssueTimeTracking}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.4.0
 */
public class DefaultIssueTimeTrackingIT {
  private static IntegrationTestsConfig config;
  private static Session session;

  @BeforeClass
  public static void setup() throws Exception {
    config = new IntegrationTestsConfig();

    session = new PermanentTokenLogin(
        config.youtrackUrl(), 
        config.youtrackUserToken()
    ).login();
  }

  @Test
  public void createAndCountAll() throws Exception {
    final Issue issue = this.issue(".createAndCountAll");
    assertThat(
        new DefaultIssueTimeTracking(session, issue)
            .create(Duration.ofMinutes(45))
            .create(Duration.ofHours(1))
            .stream()
            .count(),
        is(2L)
    );
  }

  @Test
  public void createWithDurationAndDescription() throws Exception {
    final Issue issue = this.issue(".createWithDurationAndDescription");
    final String description = issue.id() + "_duration_description";
    final Duration duration = Duration.ofMinutes(100);
    final IssueTimeTracking itt = new DefaultIssueTimeTracking(session, issue)
        .create(duration, description);
    assertTrue(
        itt.stream()
            .anyMatch(e -> 
                description.equals(e.description().get()) 
                    && duration.equals(e.duration())
            )
    );
  }

  @Test
  public void createWithDurationAndType() throws Exception {
    final Issue issue = this.issue(".createWithDurationAndType");
    final Duration duration = Duration.ofMinutes(123);
    final TimeTrackEntryType type = issue.project().timetracking().types().findAny().get();
    final IssueTimeTracking itt = new DefaultIssueTimeTracking(session, issue)
        .create(duration, type);
    assertTrue(
        itt.stream().anyMatch(e -> duration.equals(e.duration()) && type.equals(e.type().get()))
    );
  }

  @Test
  public void createWithDateAndDuration() throws Exception {
    final Issue issue = this.issue(".createWithDateAndDuration");
    final LocalDate date = LocalDate.now();
    final Duration duration = Duration.ofMinutes(345);
    final IssueTimeTracking itt = new DefaultIssueTimeTracking(session, issue)
        .create(date, duration);
    assertTrue(
        itt.stream().anyMatch(e -> date.equals(e.date()) && duration.equals(e.duration()))
    );
  }

  @Test
  public void createWithDurationAndDescriptionAndType() throws Exception {
    final Issue issue = this.issue(".createWithDurationAndDescriptionAndType");
    final Duration duration = Duration.ofMinutes(512);
    final String description = issue.id() + "_duration_description_type";
    final TimeTrackEntryType type = issue.project().timetracking().types().findAny().get();
    final IssueTimeTracking itt = new DefaultIssueTimeTracking(session, issue)
        .create(duration, description, type);
    assertTrue(
        itt.stream().anyMatch(e -> 
            duration.equals(e.duration()) 
                && description.equals(e.description().get()) 
                && type.equals(e.type().get())
        )
    );
  }

  @Test
  public void createWithDateAndDurationAndDescription() throws Exception {
    final Issue issue = this.issue(".createWithDateAndDurationAndDescription");
    final LocalDate date = LocalDate.now();
    final Duration duration = Duration.ofMinutes(828);
    final String description = issue.id() + "_date_duration_description";
    final IssueTimeTracking itt = new DefaultIssueTimeTracking(session, issue)
        .create(date, duration, description);
    assertTrue(
        itt.stream().anyMatch(e -> 
            date.equals(e.date())
                && duration.equals(e.duration())
                && description.equals(e.description().get())
        )
    );
  }

  private Issue issue(String description) throws Exception {
    return new DefaultYouTrack(session)
        .projects()
        .stream()
        .findFirst()
        .get()
        .issues()
        .create(DefaultIssueTimeTrackingIT.class.getSimpleName(), description);
  }
}
