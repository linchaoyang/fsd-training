import { InMemoryData } from './in-memory-data';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InMemoryWebApiModule } from 'angular-in-memory-web-api';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    InMemoryWebApiModule.forRoot(InMemoryData)
  ]
})
export class MockWebApiModule { }
