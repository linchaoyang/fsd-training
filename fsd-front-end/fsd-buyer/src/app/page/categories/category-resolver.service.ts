import { CategoryService } from './category.service';
import { Category } from './category';
import { Injectable } from '@angular/core';
import { Resolve, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryResolverService implements Resolve<Category[]> {

  constructor(private cs: CategoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Category[]> | Observable<never> {
    return this.cs.getCategories();
  }
}
