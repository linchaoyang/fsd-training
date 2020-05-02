import { Injectable } from '@angular/core';
import { HttpService } from 'src/app/service/http/http.service';
import { Observable } from 'rxjs';
import { Product } from './product';
import { Api } from 'config/api';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  searchKeyWord: string;

  constructor(private http: HttpService) { }

  /** GET products from the server */
  searchProducts(term?: string): Observable<Product[]> {
    this.searchKeyWord = term || '';
    if (!this.searchKeyWord.trim()) {
      return this.http.getItems<Product>(Api.products_url, Api.httpOptions);
    }
    return this.http.searchItems<Product>(Api.products_url, this.searchKeyWord, Api.httpOptions);
  }
}
