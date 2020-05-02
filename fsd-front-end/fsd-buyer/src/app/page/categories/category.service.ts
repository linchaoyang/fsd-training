
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService } from 'src/app/service/http/http.service';
import { Category } from './category';
import { Api } from 'config/api';


@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpService) { }

  /** GET categories from the server */
  getCategories(): Observable<Category[]> {
    return this.http.getItems<Category>(Api.categories_url, Api.httpOptions);
  }
}
