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

/**
 * Fluent, object-oriented API for YouTrack.
 * 
 * <h2>Login</h2>
 * YouTrack supports various authentication strategies. {@code youtrack-api} implements:
 * <ul>
 *   <li>Username/password</li>
 *   <li>Permanent tokens</li>
 *   <li>Anonymous sessions (ie. the <em>guest</em> user)</li>
 * </ul> 
 * 
 * <p><strong>Example:</strong> 
 * {@code final Session session = new PermanentToken(youtrackUrl, myToken).login();}<br>
 * See the {@link org.llorllale.youtrack.api.session} package for more info.
 * 
 * <h2>Main Entry Point</h2>
 * The main entrypoint for the API is the {@link org.llorllale.youtrack.api.YouTrack} interface. 
 * Obtain an instance of the default implementation like this:<br>
 * {@code final YouTrack yt = new DefaultYouTrack(session);}.
 * 
 * <h2>Projects</h2>
 * The {@link org.llorllale.youtrack.api.Projects} interface is used to find 
 * {@link org.llorllale.youtrack.api.Project projects}.<br>
 * <strong>Example:</strong> 
 * <pre>  {@code final Optional<Project> project = new DefaultYoutrack(session)
 *       .projects()          //returns the Projects interface
 *       .get("projectId");   //returns an Optional<Project>}</pre>
 * See interfaces {@link org.llorllale.youtrack.api.Projects} and 
 * {@link org.llorllale.youtrack.api.Project}.
 * 
 * <h2>Issues</h2>
 * You can find an {@link org.llorllale.youtrack.api.Issue issue} like this:<br>
 * <pre>  {@code final Optional<Issue> issue = project.issues().get("issueId");}</pre>
 * To create an issue, use 
 * {@link org.llorllale.youtrack.api.Issues#create(String, String)}:
 * <pre>  {@code final Issue is = project.issues().create("summary", "description");}
 * </pre>See {@link org.llorllale.youtrack.api.Issues} for more info.
 * 
 * <h2>Fields of an Issue</h2>
 * <strong>Note:</strong><br>
 * It is important to know that some fields typically found in most issue tracking software - like
 * <em>State</em> and <em>Priority</em> - are <strong>not</strong> <em>first class citizens</em> in
 * YouTrack, although they are typically present in project configurations on most teams. First 
 * class attributes of issues in YouTrack include properties such as {@code creationDate}, 
 * {@code summary}, and {@code description} (although optional). Behind the scenes, YouTrack 
 * supports fields like <em>State</em> and <em>Priority</em> through the use of 
 * <em>custom fields</em> that are configured (sometimes <em>pre</em>configured) through a project's
 * settings.<br><br>
 * <strong>Usage:</strong><br>
 * To access a project's preconfigured {@link org.llorllale.youtrack.api.Field fields}:
 * <pre>  {@code final Stream<ProjectField> fields = project.fields().stream();}</pre>
 * To access all possible {@link org.llorllale.youtrack.api.FieldValue values} for a field:
 * <pre>  {@code final Stream<FieldValue> values = projectField.values();}</pre>
 * Updating an issue's fields can be done via {@link org.llorllale.youtrack.api.Issue#update()}.
 * Example:
 * <pre>  {@code final ProjectField state = project.fields().stream()
 *        .filter(f -> "State".equals(f.name()))
 *        .findAny().get();
 *  final FieldValue done = state.values()
 *        .filter(v -> "Done".equals(v.asString())
 *        .findAny().get();
 *  final Issue issueUpdated = issue.update().field(state, done);}</pre>
 *
 * @since 0.9.0
 * @see <a href="https://llorllale.github.io/youtrack-api">Project Site.</a>
 */
package org.llorllale.youtrack.api;
