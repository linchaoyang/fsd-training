import { CategoryService } from './category.service';
import { Category } from './category';
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryResolverService implements Resolve<Category[]> {

  private searchKeyword: string;

  constructor(private cs: CategoryService, private router: Router) {
    const state = router.getCurrentNavigation().extras.state as { searchKeyword: string };
    if (state) {
      this.searchKeyword = state.searchKeyword;
    }
    this.searchKeyword = this.searchKeyword || '';
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Category[]> | Observable<never> {
    if (this.searchKeyword.trim() !== '') {
      // do not show category panel when search product
      return of([]);
    }
    return this.cs.getCategories();

    // return this.route.paramMap.pipe<Category[]>(
    //   switchMap(params => {
    //     const searchKeyword = params.get('searchKeyword') || '';
    //     if (searchKeyword.trim() !== '') {
    //       return of([]);
    //     }
    //     return this.cs.getCategories();
    //   })
    // );
  }
}
