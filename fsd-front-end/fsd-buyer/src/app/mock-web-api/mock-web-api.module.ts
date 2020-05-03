import { environment } from './../../environments/environment';
import { InMemoryData } from './in-memory-data';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InMemoryWebApiModule } from 'angular-in-memory-web-api';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    // In production, HTTP requests to go to the real server and probably have no need for the in-memory provider
    environment.production ? [] : InMemoryWebApiModule.forRoot(InMemoryData)
  ]
})
export class MockWebApiModule { }
