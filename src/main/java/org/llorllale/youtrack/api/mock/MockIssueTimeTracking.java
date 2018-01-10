/*
 * Copyright 2018 George Aristy.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.llorllale.youtrack.api.mock;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Stream;
import org.llorllale.youtrack.api.Issue;
import org.llorllale.youtrack.api.IssueTimeTracking;
import org.llorllale.youtrack.api.TimeTrackEntry;
import org.llorllale.youtrack.api.TimeTrackEntryType;
import org.llorllale.youtrack.api.session.UnauthorizedException;

/**
 * Mock implementation of {@link IssueTimeTracking} suitable for tests.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public final class MockIssueTimeTracking implements IssueTimeTracking {
  private static final String DEFAULT_DESCRIPTION = "";
  private final Issue issue;
  private final Collection<TimeTrackEntry> entries;

  /**
   * Primary ctor.
   * 
   * @param issue the associated issue
   * @param entries entries to configure for this issue's {@link Issue#timetracking() timetracking}
   * @since 1.0.0
   */
  public MockIssueTimeTracking(Issue issue, Collection<TimeTrackEntry> entries) {
    this.issue = issue;
    this.entries = entries;
  }

  @Override
  public Stream<TimeTrackEntry> stream() throws IOException, UnauthorizedException {
    return this.entries.stream();
  }

  @Override
  public IssueTimeTracking create(
      LocalDate date, 
      Duration duration, 
      String description, 
      TimeTrackEntryType type
  ) throws IOException, UnauthorizedException {
    this.entries.add(
        new MockTimeTrackEntry(
            this.issue, 
            date, 
            duration, 
            description, 
            type
        )
    );
    return this;
  }

  @Override
  public IssueTimeTracking create(Duration duration) throws IOException, UnauthorizedException {
    return this.create(duration, DEFAULT_DESCRIPTION);
  }

  @Override
  public IssueTimeTracking create(Duration duration, String description) 
      throws IOException, UnauthorizedException {
    return this.create(duration, description, null);
  }

  @Override
  public IssueTimeTracking create(LocalDate date, Duration duration) 
      throws IOException, UnauthorizedException {
    return this.create(date, duration, DEFAULT_DESCRIPTION);
  }

  @Override
  public IssueTimeTracking create(Duration duration, TimeTrackEntryType type) 
      throws IOException, UnauthorizedException {
    return this.create(duration, DEFAULT_DESCRIPTION, type);
  }

  @Override
  public IssueTimeTracking create(Duration duration, String description, TimeTrackEntryType type) 
      throws IOException, UnauthorizedException {
    return this.create(LocalDate.now(), duration, description, type);
  }

  @Override
  public IssueTimeTracking create(LocalDate date, Duration duration, String description) 
      throws IOException, UnauthorizedException {
    return this.create(date, duration, description, null);
  }
}
