import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-orders',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.css',
})
export class OrdersComponent implements OnInit {
  orders: any;
  customerId!: number;
  constructor(
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.customerId = route.snapshot.params['customerId'];
  }

  ngOnInit() {
    this.http
      .get('http://localhost:8888/BILLING-SERVICE/bills/' + this.customerId)
      .subscribe({
        next: (data) => {
          this.orders = data;
        },
        error: (error) => console.error(error),
      });
  }
}
