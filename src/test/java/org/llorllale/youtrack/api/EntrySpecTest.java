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
import java.time.ZoneId;
import java.util.Optional;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.llorllale.youtrack.api.IssueTimeTracking.EntrySpec;
import org.llorllale.youtrack.api.mock.MockTimeTrackEntryType;

/**
 * Unit tests for {@link EntrySpec}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
public class EntrySpecTest {
  /**
   * The specified duration should be present in the XML produced by the EntrySpec.
   * 
   * @since 1.0.0
   */
  @Test
  public void duration() {
    assertThat(
        new EntrySpec(Duration.ofMinutes(10)).asXml().textOf("/workItem/duration"),
        is(Optional.of("10"))
    );
  }

  /**
   * The specified date should be present in the XML produced by the EntrySpec.
   * 
   * @since 1.0.0
   */
  @Test
  public void date() {
    assertThat(
        new EntrySpec(LocalDate.now(), Duration.ZERO).asXml().textOf("/workItem/date"),
        is(Optional.of(String.valueOf(
            LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        )))
    );
  }

  /**
   * The specified description should be present in the XML produced by the EntrySpec.
   * 
   * @since 1.0.0
   */
  @Test
  public void description() {
    assertThat(
        new EntrySpec(Duration.ZERO, "Test").asXml().textOf("/workItem/description"),
        is(Optional.of("Test"))
    );
  }

  /**
   * The specified {@link TimeTrackEntryType type} should be present in the XML produced by the 
   * EntrySpec.
   * 
   * @since 1.0.0
   */
  @Test
  public void type() {
    assertThat(
        new EntrySpec(Duration.ZERO, new MockTimeTrackEntryType("Test"))
            .asXml().textOf("/workItem/worktype/name"),
        is(Optional.of("Test"))
    );
  }

  /**
   * Hashcode should be equal to ORing all properties of the entry spec.
   * 
   * @since 1.0.0
   */
  @Test
  public void testHashCode() {
    assertThat(
        new EntrySpec(
            LocalDate.now(), 
            Duration.ofMinutes(10), 
            "description", 
            new MockTimeTrackEntryType("Type")
        ).hashCode(),
        is(
            LocalDate.now().hashCode() 
                ^ Duration.ofMinutes(10).hashCode() 
                ^ "description".hashCode() 
                ^ new MockTimeTrackEntryType("Type").hashCode())
    );
  }

  /**
   * Two specs with equivalent inputs must be equal.
   * 
   * @since 1.0.0
   */
  @Test
  public void equals() {
    assertThat(
        new EntrySpec(
            LocalDate.now(),
            Duration.ofMinutes(60),
            "description",
            new MockTimeTrackEntryType("Development")
        ),
        is(
            new EntrySpec(
                LocalDate.now(),
                Duration.ofHours(1),
                "description",
                new MockTimeTrackEntryType("Development")
            )
        )
    );
  }

  /**
   * Two specs with different dates cannot be equal.
   * 
   * @since 1.0.0
   */
  @Test
  public void equalsNotEqualWithDiffDates() {
    assertThat(
        new EntrySpec(
            LocalDate.now(),
            Duration.ofMinutes(60),
            "description",
            new MockTimeTrackEntryType("Development")
        ),
        is(not(
            new EntrySpec(
                LocalDate.of(1900, 1, 1),
                Duration.ofMinutes(60),
                "description",
                new MockTimeTrackEntryType("Development")
            )
        ))
    );
  }

  /**
   * Two specs with different durations cannot be equal.
   * 
   * @since 1.0.0
   */
  @Test
  public void equalsNotEqualWithDiffDurations() {
    assertThat(
        new EntrySpec(
            LocalDate.now(),
            Duration.ofMinutes(60),
            "description",
            new MockTimeTrackEntryType("Development")
        ),
        is(not(
            new EntrySpec(
                LocalDate.now(),
                Duration.ofMinutes(100),
                "description",
                new MockTimeTrackEntryType("Development")
            )
        ))
    );
  }

  /**
   * Two specs with different descriptions cannot be equal.
   * 
   * @since 1.0.0
   */
  @Test
  public void equalsNotEqualWithDiffDescriptions() {
    assertThat(
        new EntrySpec(
            LocalDate.now(),
            Duration.ofMinutes(60),
            "description",
            new MockTimeTrackEntryType("Development")
        ),
        is(not(
            new EntrySpec(
                LocalDate.now(),
                Duration.ofMinutes(60),
                "different description",
                new MockTimeTrackEntryType("Development")
            )
        ))
    );
  }

  /**
   * Two specs with different types cannot be equal.
   * 
   * @since 1.0.0
   */
  @Test
  public void equalsNotEqualWithDiffTypes() {
    assertThat(
        new EntrySpec(
            LocalDate.now(),
            Duration.ofMinutes(60),
            "description",
            new MockTimeTrackEntryType("Development")
        ),
        is(not(
            new EntrySpec(
                LocalDate.now(),
                Duration.ofMinutes(60),
                "description",
                new MockTimeTrackEntryType("Analysis")
            )
        ))
    );
  }

  /**
   * A spec cannot be equal to {@code null}.
   * 
   * @since 1.0.0
   */
  @Test
  public void equalsIsFalseWithNull() {
    assertFalse(
        new EntrySpec(Duration.ZERO).equals(null)
    );
  }

  /**
   * A spec cannot be equal to an object that is not another {@link EntrySpec}.
   * 
   * @since 1.0.0
   */
  @Test
  public void equalsIsFalseWithObject() {
    assertFalse(
        new EntrySpec(Duration.ZERO).equals(new Object())
    );
  }
}
