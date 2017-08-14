/* 
 * Copyright 2017 George Aristy (george.aristy@gmail.com).
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

/**
 * <p>
 * The APIs in this package are intended for internal use by this library.
 * 
 * The principal interface is
 * {@link org.llorllale.youtrack.api.response.Response} that defines the set of
 * methods used by other library components to obtain access to the response
 * payloads from the remote YouTrack API.
 *
 * The job of each {@code Response} implementation is to properly and
 * transparently handle each type of HTTP response received from the server
 * according to the status code.
 *
 * Client code is relieved from knowing the existence of all implementations by
 * use of the {@link org.llorllale.youtrack.api.response.HttpResponseAsResponse}
 * that internally employs the Chain of Responsibility pattern with all other
 * implementations.
 * </p>
 * @since 0.1.0
 */
package org.llorllale.youtrack.api.response;
