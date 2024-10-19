import { Component, Input } from '@angular/core';
import { SingleCardsComponent } from '../single-cards/single-cards.component';
import type { Deals } from '../../shared/interfaces/deals';
import { Stores, stores } from '../../shared/interfaces/stores';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-deal-cards',
  standalone: true,
  imports: [SingleCardsComponent,CommonModule],
  templateUrl: './deal-cards.component.html',
  styleUrl: './deal-cards.component.css'
})
export class DealCardsComponent {
@Input() store:Stores | undefined;
stores = stores;


 deals: Deals[]= [
  {
       id: 1,
       name: "iPhone 15 Pro",
       store: this.stores[0].name,
       category: "Smartphones",
       coupon: "Not Available",
       url: "https://www.apple.com",
       price: 1199,
       img: "iphone.jpg",
       createdAt: Date.now(),
       updatedAt: Date.now(),
     }, {
       id: 2,
       name: "Samsung Galaxy S23 Ultra",
       store: "Amazon",
       category: "Smartphones",
       coupon: "SAVE50",
       price: 999,
       url: "https://www.amazon.com",
       img: "s23ultra.jpg",
       createdAt: Date.now(),
       updatedAt: Date.now(),
     },{
       id: 3,
       name: "MacBook Pro 16",
       store: "Apple",
       category: "Laptops",
       coupon: "Not Available",
       price: 2499,
       url: "https://www.apple.com",
       img: "macbook.jpg",
       createdAt: Date.now(),
       updatedAt: Date.now(),
     },{ 
       id: 4,
       name: "Sony WH-1000XM5",
       store: "Best Buy",
       category: "Headphones",
       coupon: "DISCOUNT10",
       price: 399,
       url: "https://www.bestbuy.com",  
       img: "sonyheadphones.jpg",
       createdAt: Date.now(),
       updatedAt: Date.now(),
     },{
       id: 5,
       name: "PlayStation 5",
       store: "GameStop",
       category: "Gaming",
       url: "https://www.gamestop.com",
       coupon: "GAMER15",
       price: 499,
       img: "ps5.jpg",
       createdAt: Date.now(),
       updatedAt: Date.now(),
     },{ 
    
       id: 6,
       name: "Dell XPS 13",
       store: "Dell",
       category: "Laptops",
       coupon: "Not Available",
       price: 1099,
       img: "dellxps13.jpg",
       url: "https://www.dell.com",
       createdAt: Date.now(),
       updatedAt: Date.now(),
     },{
       id: 7,
       name: "GoPro Hero 12",
       store: "Banggood",
       category: "Cameras",
       coupon: "GOPRO5",
       price: 499,
       url: "https://www.banggood.com",
       img: "gopro.jpg",
       createdAt: Date.now(),
       updatedAt: Date.now(),
     }
    ]
     
}
