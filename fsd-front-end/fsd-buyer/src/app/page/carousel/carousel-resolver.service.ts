import { switchMap } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import {
  Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot,
  Router
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { CarouselService } from './carousel.service';
import { Carousel } from './carousel';

@Injectable({
  providedIn: 'root'
})
export class CarouselResolverService implements Resolve<Carousel[]> {

  private searchKeyword: string;

  constructor(private cs: CarouselService, private router: Router) {
    const state = router.getCurrentNavigation().extras.state as { searchKeyword: string };
    if (state) {
      this.searchKeyword = state.searchKeyword;
    }
    this.searchKeyword = this.searchKeyword || '';
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Carousel[]> | Observable<never> {
    if (this.searchKeyword.trim() !== '') {
      return of([]);
    }
    return this.cs.getCarousels();

    // return this.route.paramMap.pipe<Carousel[]>(
    //   switchMap(params => {
    //     const searchKeyword = params.get('searchKeyword') || '';
    //     if (searchKeyword.trim() !== '') {
    //       return of([]);
    //     }
    //     return this.cs.getCarousels();
    //   })
    // );

    // let id = route.paramMap.get('id');

    // return this.cs.getCarousels().pipe(
    //   take(1),
    //   mergeMap(carousels => {
    //     if (carousels) {
    //       return of(carousels);
    //     } else { // id not found
    //       //this.router.navigate(['/crisis-center']);
    //       return EMPTY;
    //     }
    //   })
    // );
  }
}
