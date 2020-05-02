import { TestBed } from '@angular/core/testing';

import { CarouselResolverService } from './carousel-resolver.service';

describe('CarouselResolverService', () => {
  let service: CarouselResolverService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CarouselResolverService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
