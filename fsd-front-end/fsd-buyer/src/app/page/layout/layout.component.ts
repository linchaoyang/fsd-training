import { Router } from '@angular/router';
import { ProductsService } from './../product-list/products.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent implements OnInit {

  constructor(public productService: ProductsService, private router: Router) {
    console.log('layout component construct');
  }

  ngOnInit(): void {
    console.log('layout component init');
  }

}
