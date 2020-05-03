import { Carousel } from './../carousel/carousel';

export class Product {

   id: number;

   /** Product category */
   category: string;

   /** Product subcategory */
   subcategory: string;

   /** image src */
   image: string;

   carousels: Carousel[];

   /** Product amount to be sold */
   stockNumber: number;

   price: number;

   /** Product tax */
   tax: number;

   /** Product description to be displayed on the page */
   name: string;

   manufacturer: string;

   seller: string;

   /** Product specifiactions */
   specifications: { [name: string]: string }[];
}
