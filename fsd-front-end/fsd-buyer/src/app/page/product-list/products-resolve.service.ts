import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Product } from './product';
import { ProductsService } from './products.service';
import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ProductsResolveService implements Resolve<Product[]> {

  private searchKeyword: string;

  constructor(private cs: ProductsService, private router: Router) {
    const state = router.getCurrentNavigation().extras.state as { searchKeyword: string };
    if (state) {
      this.searchKeyword = state.searchKeyword;
    }
    this.searchKeyword = this.searchKeyword || '';
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Product[]> | Observable<never> {

    return this.cs.searchProducts(this.searchKeyword);
    // return this.route.paramMap.pipe<Product[]>(
    //   switchMap(params => {
    //     const searchKeyword = params.get('searchKeyword') || '';
    //     return this.cs.searchProducts(searchKeyword);
    //   })
    // );
  }
}
