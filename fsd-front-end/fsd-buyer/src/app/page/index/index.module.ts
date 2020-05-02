import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LayoutComponent } from './../layout/layout.component';
import { ProfileComponent } from './../profile/profile.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { IndexRoutingModule } from './index-routing.module';
import { IndexComponent } from './index.component';
import { CarouselComponent } from '../carousel/carousel.component';
import { HttpClientModule } from '@angular/common/http';
import { MyCardComponent } from '../my-card/my-card.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { CategoriesComponent } from '../categories/categories.component';
import { ProductListComponent } from '../product-list/product-list.component';


@NgModule({
  declarations: [IndexComponent,
    MyCardComponent,
    ProfileComponent,
    LayoutComponent,
    CarouselComponent,
    CategoriesComponent,
    ProductListComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    IndexRoutingModule,
    HttpClientModule,
    NgbModule
  ]
})
export class IndexModule { }
