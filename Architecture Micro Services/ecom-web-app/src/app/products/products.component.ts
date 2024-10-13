import { CommonModule } from '@angular/common';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [HttpClientModule, CommonModule, RouterModule],
  templateUrl: './products.component.html',
  styleUrl: './products.component.css',
})
export class ProductsComponent implements OnInit {
  products: any;
  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.http
      .get('http://localhost:8888/INVENTORY-SERVICE/products')
      .subscribe({
        next: (data) => {
          this.products = data;
        },
        error: (error) => console.error(error),
      });
  }
}
