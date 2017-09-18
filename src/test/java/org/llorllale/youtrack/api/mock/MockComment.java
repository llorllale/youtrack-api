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

package org.llorllale.youtrack.api.mock;

import java.time.Instant;
import org.llorllale.youtrack.api.Comment;

/**
 * <p>Mock implementation of {@link Comment} suitable for unit tests.</p>
 * 
 * <p>
 * The values for attributes of this {@link Comment} are hardcoded as follows:
 * <ul>
 *   <li>id -> "MOCK-COMMENT-1"</li>
 *   <li>creationDate -> {@link Instant#now()}</li>
 *   <li>authorLoginName -> "MOCK-COMMENT-AUTHOR"</li>
 *   <li>issueId -> "MOCK-COMMENT-ISSUE-ID"</li>
 *   <li>text -> "MOCK-COMMENT-TEXT"</li>
 * </ul>
 * </p>
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.2.0
 */
public class MockComment implements Comment {
  @Override
  public String id() {
    return "MOCK-COMMENT-1";
  }

  @Override
  public Instant creationDate() {
    return Instant.now();
  }

  @Override
  public String authorLoginName() {
    return "MOCK-COMMENT-AUTHOR";
  }

  @Override
  public String issueId() {
    return "MOCK-COMMENT-ISSUE-ID";
  }

  @Override
  public String text() {
    return "MOCK-COMMENT-TEXT";
  }
}
