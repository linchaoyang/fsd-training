import { Component, OnInit } from '@angular/core';
import { Product } from './product';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit {

  products: Product[] = [];

  manufacturers: string[] = [];

  constructor(private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.route.data.subscribe(
      (data: {products: Product[]}) => {
        this.products = data.products;
        this.products.map((product: Product) => product.manufacturer).sort().reduce((prev, current, currentIndex) => {
          if (currentIndex <= 1) {
            if (this.manufacturers.indexOf(prev) === -1) {
              this.manufacturers.push(prev);
            }
          }
          if (prev !== current) {
            this.manufacturers.push(current);
          }
          return current;
        });
      }
    );
  }

  filter(manufacture: string = '', lowPrice?: number, highPrice?: number) {
  }

}
