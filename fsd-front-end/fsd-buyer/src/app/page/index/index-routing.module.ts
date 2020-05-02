
import { AuthGuard } from '../auth/auth.guard';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { IndexComponent } from './index.component';
import { LayoutComponent } from '../layout/layout.component';
import { MyCardComponent } from '../my-card/my-card.component';
import { ProfileComponent } from '../profile/profile.component';
import { CategoriesComponent } from '../categories/categories.component';
import { CategoryResolverService } from '../categories/category-resolver.service';
import { CarouselComponent } from '../carousel/carousel.component';
import { CarouselResolverService } from '../carousel/carousel-resolver.service';
import { ProductsResolveService } from '../product-list/products-resolve.service';

const routes: Routes = [
  {
    path: '',
    component: IndexComponent,
    canActivate: [AuthGuard],
    resolve: { carousels: CarouselResolverService,
      categories: CategoryResolverService,
      products: ProductsResolveService},
    children: [
      {
        path: '', canActivateChild: [AuthGuard],
        children: [
          {
            path: '', component: LayoutComponent,
            children: [
              { path: 'carousel', component: CarouselComponent },
              { path: 'categories', component: CategoriesComponent }
            ]
          },
          { path: 'mycard/:id', component: MyCardComponent, data: { showSearch: false } },
          { path: 'profile/:id', component: ProfileComponent }

        ]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  declarations: [],
})
export class IndexRoutingModule { }
