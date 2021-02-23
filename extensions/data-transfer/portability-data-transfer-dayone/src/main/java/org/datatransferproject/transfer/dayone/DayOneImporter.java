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

package org.datatransferproject.transfer.dayone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.*;
import org.datatransferproject.spi.transfer.idempotentexecutor.IdempotentImportExecutor;
import org.datatransferproject.spi.transfer.provider.ImportResult;
import org.datatransferproject.spi.transfer.provider.Importer;
import org.datatransferproject.types.common.models.social.SocialActivityContainerResource;
import org.datatransferproject.types.transfer.auth.TokensAndUrlAuthData;

import java.net.URL;
import java.util.UUID;

public class DayOneImporter
    implements Importer<TokensAndUrlAuthData, SocialActivityContainerResource> {
  private static final String BASE_URL = "https://gaged1.ngrok.io";

  private final HttpTransport httpTransport;
  private final ObjectMapper objectMapper;

  public DayOneImporter(HttpTransport httpTransport, ObjectMapper objectMapper) {
    this.httpTransport = httpTransport;
    this.objectMapper = objectMapper;
  }

  @Override
  public ImportResult importItem(
      UUID jobId,
      IdempotentImportExecutor idempotentExecutor,
      TokensAndUrlAuthData authData,
      SocialActivityContainerResource data)
      throws Exception {
    idempotentExecutor.executeAndSwallowIOExceptions(
        data.getId(),
        "", // TODO: What should go here? We're taking all of the data at once and it suggests a human readable name.
        () -> {
          // TODO: Once Gage's endpoint is up and running, we'll want to chunk the data here and send it to ingest in parts.
          HttpRequestFactory factory = httpTransport.createRequestFactory();
          String serializedData = objectMapper.writeValueAsString(data);
          HttpContent content = ByteArrayContent.fromString("application/json", serializedData);

          HttpRequest request =
              factory.buildPostRequest(
                  new GenericUrl(new URL(BASE_URL + "/api/dtp/ingest")), content);
          request.setReadTimeout(120_000);
          HttpResponse response = request.execute();
          // Handle possible failures?
          System.out.println(response);

          return null;
        });
    return ImportResult.OK;
  }
}
