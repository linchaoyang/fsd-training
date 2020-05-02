import { Injectable } from '@angular/core';
import { Carousel } from './carousel';
import { Observable } from 'rxjs';
import { HttpService } from 'src/app/service/http/http.service';
import { Api } from 'config/api';

@Injectable({
  providedIn: 'root'
})
export class CarouselService {

  constructor(private http: HttpService) { }

  /** GET carousels from the server */
  getCarousels(): Observable<Carousel[]> {
    return this.http.getItems<Carousel>(Api.carousel_url, Api.httpOptions);
  }
}
