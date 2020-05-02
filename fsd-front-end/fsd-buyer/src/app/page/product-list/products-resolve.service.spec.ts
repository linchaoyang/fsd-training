import { TestBed } from '@angular/core/testing';

import { ProductsResolveService } from './products-resolve.service';

describe('ProductsResolveService', () => {
  let service: ProductsResolveService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductsResolveService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
