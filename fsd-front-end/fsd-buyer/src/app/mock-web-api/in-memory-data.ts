import { InMemoryDbService, RequestInfo, STATUS } from 'angular-in-memory-web-api';
import { Observable } from 'rxjs/internal/Observable';
import { of } from 'rxjs';

export class InMemoryData extends InMemoryDbService {
  createDb(reqInfo?: RequestInfo): {} | Observable<{}> | Promise<{}> {
    return {
      auth: [
        { id: 1, username: 'lisagoog', password: '123456' },
        { id: 2, username: 'avadami', password: '123456' }
      ],
      carousel: [
        { id: 1, productId: 1, src: 'assets/images/v2_pyl8jq.jpg', caption: 'Product 1 caption', alt: 'product 1 tips' },
        { id: 2, productId: 2, src: 'assets/images/v2_pyl8k6.jpg', caption: 'Product 2 caption', alt: 'product 2 tips' }
      ],
      categories: [
        {id: 1, name: 'Electronic', description: 'Mobile Laptop', css: 'electronic'},
        {id: 2, name: 'Fashion', description: 'Dress', css: 'fashion'},
        {id: 3, name: 'Residential', description: 'Furniture', css: '.residential'}
      ],
      products: [
        {id: 1, category: 'Electronic', subcategory: 'Laptop', image: 'assets/images/products/v2_pyhhub.png',
        price: 8900, description: 'Apple 2019 Macbook pro 13.3 inch', seller: 'Seller1'},
        {id: 2, category: 'Fashoin', subcategory: 'Dress', image: 'assets/images/products/v2_q8ohsk.png',
        price: 120, description: 'Man T-shirt white-green', seller: 'Seller2'},
        {id: 3, category: 'Redidential', subcategory: 'Furniture', image: 'assets/images/products/v2_q8ohxn.png',
        price: 300, description: 'Classic cabinet (small size)', seller: 'Seller3'}
      ]
    };
  }

  post(reqInfo: RequestInfo): {} | Observable<{}> | Promise<{}> {
    if (reqInfo.collectionName === 'auth') {
      const loginUser = reqInfo.utils.getJsonBody(reqInfo.req);
      let item = reqInfo.collection.filter(
        (user) => {
          return user.username === loginUser.username &&
                user.password === loginUser.password;
        }
      );

      if (item && item.length > 0) {
        item = item[0];
        const resOption = {
          body: item,
          url: reqInfo.url,
          headers: reqInfo.headers,
          status: STATUS.OK
        };

        return reqInfo.utils.createResponse$(() => resOption);

      } else {
        const errOption = {
          body: { error: 'Bad credentials' },
          url: reqInfo.url,
          headers: reqInfo.headers,
          status: STATUS.INTERNAL_SERVER_ERROR
        };

        return reqInfo.utils.createResponse$(() => errOption);
        // resOptions = this.createErrorResponseOptions(url, STATUS.NOT_FOUND, "Collection '" + collectionName + "' not found");
        // return this.createResponse$(function () { return resOptions; });
      }
    }
    return undefined;
  }
}
