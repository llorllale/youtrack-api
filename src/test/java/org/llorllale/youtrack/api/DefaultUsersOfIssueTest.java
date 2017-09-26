/*
 * Copyright 2017 George Aristy.
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

package org.llorllale.youtrack.api;

import static org.apache.commons.lang3.StringUtils.substringAfterLast;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;
import org.llorllale.youtrack.api.mock.MockIssue;
import org.llorllale.youtrack.api.mock.MockProject;
import org.llorllale.youtrack.api.mock.http.MockAssertRequestHttpClient;
import org.llorllale.youtrack.api.mock.http.MockHttpClient;
import org.llorllale.youtrack.api.mock.http.MockSession;
import org.llorllale.youtrack.api.mock.http.response.MockForbiddenResponse;
import org.llorllale.youtrack.api.mock.http.response.MockOkResponse;
import org.llorllale.youtrack.api.util.XmlStringAsJaxb;

/**
 * Unit tests for {@link DefaultUsersOfIssue}.
 * @author George Aristy (george.aristy@gmail.com)
 * @since 0.5.0
 */
public class DefaultUsersOfIssueTest {
  private static org.llorllale.youtrack.api.jaxb.Issue jaxbIssue;
  private static org.llorllale.youtrack.api.jaxb.User jaxbCreator;
  private static org.llorllale.youtrack.api.jaxb.User jaxbUpdater;
  private static org.llorllale.youtrack.api.jaxb.User jaxbAssignee;

  @BeforeClass
  public static void setup() throws Exception {
    jaxbIssue = new XmlStringAsJaxb<>(org.llorllale.youtrack.api.jaxb.Issue.class).apply(ISSUE_WITH_ASSIGNEE_UPDATER);
    jaxbCreator = new XmlStringAsJaxb<>(org.llorllale.youtrack.api.jaxb.User.class).apply(CREATOR);
    jaxbUpdater = new XmlStringAsJaxb<>(org.llorllale.youtrack.api.jaxb.User.class).apply(UPDATER);
    jaxbAssignee = new XmlStringAsJaxb<>(org.llorllale.youtrack.api.jaxb.User.class).apply(ASSIGNEE);
  }

  /**
   * DefaultUsersOfIssue must return the Issue's creator (there must always be a creator).
   * @since 0.5.0
   */
  @Test
  public void testCreator() throws Exception {
    assertThat(
        new DefaultUsersOfIssue(
            new MockSession(), 
            new MockIssue<>(
                new MockProject(), 
                jaxbIssue
            ),
            new MockAssertRequestHttpClient(
                new MockHttpClient(
                    new MockOkResponse(CREATOR)
                ),
                r -> substringAfterLast(r.getRequestLine().getUri(), "/")
                    .equals(jaxbIssue.getField()
                        .stream()
                        .filter(f -> "reporterName".equals(f.getName()))
                        .map(f -> f.getValue().getValue())
                        .findAny()
                        .get()
                    )
            )
        ).creator().name(),
        is(jaxbCreator.getFullName())
    );
  }

  /**
   * DefaultUsersForIssue must return the issue's updater.
   * @since 0.5.0
   */
  @Test
  public void testExistingUpdater() throws Exception {
    assertThat(
        new DefaultUsersOfIssue(
            new MockSession(), 
            new MockIssue<>(
                new MockProject(), 
                jaxbIssue
            ),
            new MockAssertRequestHttpClient(
                new MockHttpClient(
                    new MockOkResponse(UPDATER)
                ),
                r -> substringAfterLast(r.getRequestLine().getUri(), "/")
                    .equals(jaxbIssue.getField()
                        .stream()
                        .filter(f -> "updaterName".equals(f.getName()))
                        .map(f -> f.getValue().getValue())
                        .findAny()
                        .get()
                    )
            )
        ).updater().get().name(),
        is(jaxbUpdater.getFullName())
    );
  }

  /**
   * DefaultUsersOfIssue must return an empty optional if the issue has no updater.
   * @throws Exception 
   * @since 0.5.0
   */
  @Test
  public void testNoUpdater() throws Exception {
    assertFalse(
        new DefaultUsersOfIssue(
            new MockSession(), 
            new MockIssue<>(
                new MockProject(), 
                new XmlStringAsJaxb<>(org.llorllale.youtrack.api.jaxb.Issue.class)
                    .apply(ISSUE_NO_ASSIGNEE_UPDATER)
            ),
            new MockHttpClient(
                new MockForbiddenResponse()
            )
        ).updater().isPresent()
    );
  }

  /**
   * DefaultUsersOfIssue must return the issue's assignee.
   * @since 0.5.0
   */
  @Test
  public void testExistingAssignee() throws Exception {
    assertThat(
        new DefaultUsersOfIssue(
            new MockSession(), 
            new MockIssue<>(
                new MockProject(), 
                jaxbIssue
            ),
            new MockHttpClient(
                new MockOkResponse(ASSIGNEE)
            )
        ).updater().get().name(),
        is(jaxbAssignee.getFullName())
    );
  }

  /**
   * DefaultUsersOfIssue must return an empty optional if the issue has no assignees.
   * @throws Exception 
   * @since 0.5.0
   */
  @Test
  public void testNoAssignee() throws Exception {
    assertFalse(
        new DefaultUsersOfIssue(
            new MockSession(), 
            new MockIssue<>(
                new MockProject(), 
                new XmlStringAsJaxb<>(org.llorllale.youtrack.api.jaxb.Issue.class)
                    .apply(ISSUE_NO_ASSIGNEE_UPDATER)
            ),
            new MockHttpClient(
                new MockForbiddenResponse()
            )
        ).assignee().isPresent()
    );
  }

  private static final String CREATOR =
"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
"<user lastCreatedProject=\"HBR\" email=\"creator@gmail.com\" fullName=\"Creator user\"/>";

  private static final String UPDATER =
"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
"<user lastCreatedProject=\"HBR\" email=\"updater@gmail.com\" fullName=\"Updater user\"/>";

  private static final String ASSIGNEE =
"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
"<user lastCreatedProject=\"HBR\" email=\"assignee@gmail.com\" fullName=\"Assignee user\"/>";

  private static final String ISSUE_WITH_ASSIGNEE_UPDATER =
"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
"<issue id=\"HBR-63\">\n" +
"    <field name=\"attachments\">\n" +
"        <value url=\"/_persistent/uploadFile.html?file=45-46&amp;v=0&amp;c=true\">uploadFile.html</value>\n" +
"    </field>\n" +
"    <comment id=\"42-306\" author=\"root\" issueId=\"HBR-63\" deleted=\"false\" text=\"comment 1!\" shownForIssueAuthor=\"false\"\n" +
"             created=\"1267030230127\">\n" +
"        <replies/>\n" +
"    </comment>\n" +
"    <comment id=\"42-307\" author=\"root\" issueId=\"HBR-63\" deleted=\"false\" text=\"comment 2?\" shownForIssueAuthor=\"false\"\n" +
"             created=\"1267030238721\" updated=\"1267030230127\">\n" +
"        <replies/>\n" +
"    </comment>\n" +
"    <field name=\"Priority\">\n" +
"        <value>Normal</value>\n" +
"    </field>\n" +
"    <field name=\"Type\">\n" +
"        <value>Bug</value>\n" +
"    </field>\n" +
"    <field name=\"State\">\n" +
"        <value>Won't fix</value>\n" +
"    </field>\n" +
"    <field name=\"Assignee\">\n" +
"        <value>beto</value>\n" +
"    </field>\n" +
"    <field name=\"Subsystem\">\n" +
"        <value>Configuration</value>\n" +
"    </field>\n" +
"    <field name=\"Fix versions\">\n" +
"        <value>2.0</value>\n" +
"        <value>2.0.5</value>\n" +
"        <value>2.0.7</value>\n" +
"    </field>\n" +
"    <field name=\"cf\">\n" +
"        <value>0</value>\n" +
"        <value>!</value>\n" +
"    </field>\n" +
"    <field name=\"scf\">\n" +
"        <value>1265835603000</value>\n" +
"    </field>\n" +
"    <field name=\"links\">\n" +
"        <value type=\"Depend\" role=\"depends on\">HBR-62</value>\n" +
"        <value type=\"Duplicate\" role=\"duplicates\">HBR-57</value>\n" +
"        <value type=\"Duplicate\" role=\"is duplicated by\">HBR-54</value>\n" +
"        <value type=\"Relates\" role=\"relates to\">HBR-49</value>\n" +
"        <value type=\"Relates\" role=\"is related to\">HBR-51</value>\n" +
"        <value type=\"Depend\" role=\"is required for\">HBR-49</value>\n" +
"    </field>\n" +
"    <field name=\"projectShortName\">\n" +
"        <value>HBR</value>\n" +
"    </field>\n" +
"    <field name=\"numberInProject\">\n" +
"        <value>63</value>\n" +
"    </field>\n" +
"    <field name=\"summary\">\n" +
"        <value>summary</value>\n" +
"    </field>\n" +
"    <field name=\"description\">\n" +
"        <value>description</value>\n" +
"    </field>\n" +
"    <field name=\"created\">\n" +
"        <value>1262171005630</value>\n" +
"    </field>\n" +
"    <field name=\"updated\">\n" +
"        <value>1267630833573</value>\n" +
"    </field>\n" +
"    <field name=\"updaterName\">\n" +
"        <value>root</value>\n" +
"    </field>\n" +
"    <field name=\"resolved\">\n" +
"        <value>1267030108251</value>\n" +
"    </field>\n" +
"    <field name=\"reporterName\">\n" +
"        <value>root</value>\n" +
"    </field>\n" +
"    <field name=\"commentsCount\">\n" +
"        <value>2</value>\n" +
"    </field>\n" +
"    <field name=\"votes\">\n" +
"        <value>0</value>\n" +
"    </field>\n" +
"</issue>";

  private static final String ISSUE_NO_ASSIGNEE_UPDATER =
"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
"<issue id=\"HBR-63\">\n" +
"    <field name=\"attachments\">\n" +
"        <value url=\"/_persistent/uploadFile.html?file=45-46&amp;v=0&amp;c=true\">uploadFile.html</value>\n" +
"    </field>\n" +
"    <comment id=\"42-306\" author=\"root\" issueId=\"HBR-63\" deleted=\"false\" text=\"comment 1!\" shownForIssueAuthor=\"false\"\n" +
"             created=\"1267030230127\">\n" +
"        <replies/>\n" +
"    </comment>\n" +
"    <comment id=\"42-307\" author=\"root\" issueId=\"HBR-63\" deleted=\"false\" text=\"comment 2?\" shownForIssueAuthor=\"false\"\n" +
"             created=\"1267030238721\" updated=\"1267030230127\">\n" +
"        <replies/>\n" +
"    </comment>\n" +
"    <field name=\"Priority\">\n" +
"        <value>Normal</value>\n" +
"    </field>\n" +
"    <field name=\"Type\">\n" +
"        <value>Bug</value>\n" +
"    </field>\n" +
"    <field name=\"State\">\n" +
"        <value>Won't fix</value>\n" +
"    </field>\n" +
"    <field name=\"Subsystem\">\n" +
"        <value>Configuration</value>\n" +
"    </field>\n" +
"    <field name=\"Fix versions\">\n" +
"        <value>2.0</value>\n" +
"        <value>2.0.5</value>\n" +
"        <value>2.0.7</value>\n" +
"    </field>\n" +
"    <field name=\"cf\">\n" +
"        <value>0</value>\n" +
"        <value>!</value>\n" +
"    </field>\n" +
"    <field name=\"scf\">\n" +
"        <value>1265835603000</value>\n" +
"    </field>\n" +
"    <field name=\"links\">\n" +
"        <value type=\"Depend\" role=\"depends on\">HBR-62</value>\n" +
"        <value type=\"Duplicate\" role=\"duplicates\">HBR-57</value>\n" +
"        <value type=\"Duplicate\" role=\"is duplicated by\">HBR-54</value>\n" +
"        <value type=\"Relates\" role=\"relates to\">HBR-49</value>\n" +
"        <value type=\"Relates\" role=\"is related to\">HBR-51</value>\n" +
"        <value type=\"Depend\" role=\"is required for\">HBR-49</value>\n" +
"    </field>\n" +
"    <field name=\"projectShortName\">\n" +
"        <value>HBR</value>\n" +
"    </field>\n" +
"    <field name=\"numberInProject\">\n" +
"        <value>63</value>\n" +
"    </field>\n" +
"    <field name=\"summary\">\n" +
"        <value>summary</value>\n" +
"    </field>\n" +
"    <field name=\"description\">\n" +
"        <value>description</value>\n" +
"    </field>\n" +
"    <field name=\"created\">\n" +
"        <value>1262171005630</value>\n" +
"    </field>\n" +
"    <field name=\"updated\">\n" +
"        <value>1267630833573</value>\n" +
"    </field>\n" +
"    <field name=\"resolved\">\n" +
"        <value>1267030108251</value>\n" +
"    </field>\n" +
"    <field name=\"reporterName\">\n" +
"        <value>root</value>\n" +
"    </field>\n" +
"    <field name=\"commentsCount\">\n" +
"        <value>2</value>\n" +
"    </field>\n" +
"    <field name=\"votes\">\n" +
"        <value>0</value>\n" +
"    </field>\n" +
"</issue>";
}