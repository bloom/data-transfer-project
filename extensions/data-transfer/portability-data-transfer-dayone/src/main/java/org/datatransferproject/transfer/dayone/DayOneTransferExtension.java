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
import com.google.api.client.http.HttpTransport;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.datatransferproject.api.launcher.ExtensionContext;
import org.datatransferproject.api.launcher.Monitor;
import org.datatransferproject.spi.transfer.extension.TransferExtension;
import org.datatransferproject.spi.transfer.provider.Exporter;
import org.datatransferproject.spi.transfer.provider.Importer;

public class DayOneTransferExtension implements TransferExtension {
  private boolean initialized = false;

  private DayOneImporter importer;
  private Monitor monitor;

  private static final ImmutableList<String> SUPPORTED_SERVICES = ImmutableList.of("SOCIAL-POSTS");

  @Override
  public void initialize(ExtensionContext context) {
    monitor = context.getMonitor();
    if (initialized) {
      monitor.severe(
          () -> "Tried to initialize DayOneTransferExtension, but it was already initialized.");
      return;
    }

    initialized = true;

    HttpTransport httpTransport = context.getService(HttpTransport.class);
    ObjectMapper objectMapper = context.getTypeManager().getMapper();

    importer = new DayOneImporter(httpTransport, objectMapper);
  }

  @Override
  public Exporter<?, ?> getExporter(String transferDataType) {
    monitor.severe(
        () ->
            "Attempted to use an exporter from DayOneTransferExtension, which is not implemented.");

    throw new IllegalArgumentException();
  }

  @Override
  public Importer<?, ?> getImporter(String transferDataType) {
    Preconditions.checkArgument(
        initialized, "Attempted to call getImporter before initializing DayOneTransferExtension.");
    Preconditions.checkArgument(
        SUPPORTED_SERVICES.contains(transferDataType),
        "Attempted to use an importer from DayOneTransferExtension with an unsupported type.");

    return importer;
  }

  @Override
  public String getServiceId() {
    return "Day_One";
  }
}
