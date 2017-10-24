/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { SelectDataTypeService } from './select-data-type.service';
import { StartCopyComponent } from './start-copy/start-copy.component';
import { DemoComponent } from './demo/demo.component';
import { NextComponent } from './next/next.component';
import { SimpleLoginComponent } from './simplelogin/simplelogin.component';

@NgModule({
  declarations: [
    AppComponent,
    StartCopyComponent,
    DemoComponent,
    NextComponent,
    SimpleLoginComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    FormsModule,
    HttpModule,
  ],
  providers: [ SelectDataTypeService ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
