/*
 * Copyright 2021 The Data Transfer Project Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.datatransferproject.auth.dayone;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.datatransferproject.auth.OAuth2Config;

import java.util.Map;
import java.util.Set;

/** Class that provides Day One-specific information for OAuth2 */
public class DayOneOAuthConfig implements OAuth2Config {
  private static final String BASE_URL = "https://stg.dayone.app";

  @Override
  public String getServiceName() {
    return "Day_One";
  }

  @Override
  public String getAuthUrl() {
    return BASE_URL + "/oauth";
  }

  @Override
  public String getTokenUrl() {
    return BASE_URL + "/api/oauth/redeem";
  }

  // Day One doesn't require the use of scopes.
  @Override
  public Map<String, Set<String>> getExportScopes() {
    return ImmutableMap.of("SOCIAL-POSTS", ImmutableSet.of(""));
  }

  @Override
  public Map<String, Set<String>> getImportScopes() {
    return ImmutableMap.of("SOCIAL-POSTS", ImmutableSet.of(""));
  }

  @Override
  public Map<String, String> getAdditionalAuthUrlParameters() {
    return ImmutableMap.of(
        "state", "{}",
        "serviceId", "dtp-fb",
        "serviceName", "dtp-fb");
  }
}
