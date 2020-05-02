import { Component, OnInit } from '@angular/core';
import { Carousel } from './carousel';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-carousel',
  templateUrl: './carousel.component.html',
  styleUrls: ['./carousel.component.scss']
})
export class CarouselComponent implements OnInit {

  carousels: Carousel[] = [];

  constructor(private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.route.data.subscribe(
      (data: {carousels: Carousel[]}) => {
        this.carousels = data.carousels;
      }
    );
  }

}
