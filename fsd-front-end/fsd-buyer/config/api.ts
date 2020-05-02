import { HttpHeaders } from '@angular/common/http';
import { environment } from './../src/environments/environment';

export const Api = {

  httpOptions:  {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  },

  auth_url: environment.domain + '/auth',

  carousel_url: environment.domain + '/carousel',

  categories_url: environment.domain + '/categories',

  products_url: environment.domain + '/products'
};
