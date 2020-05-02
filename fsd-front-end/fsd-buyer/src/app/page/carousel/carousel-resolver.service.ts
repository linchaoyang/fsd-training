import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable } from 'rxjs';
import { CarouselService } from './carousel.service';
import { Carousel } from './carousel';

@Injectable({
  providedIn: 'root'
})
export class CarouselResolverService implements Resolve<Carousel[]> {

  constructor(private cs: CarouselService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Carousel[]> | Observable<never> {
    return this.cs.getCarousels();

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
