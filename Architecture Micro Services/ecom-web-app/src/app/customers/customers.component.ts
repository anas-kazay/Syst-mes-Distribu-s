import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-customers',
  standalone: true,
  imports: [HttpClientModule, CommonModule],
  templateUrl: './customers.component.html',
  styleUrl: './customers.component.css',
})
export class CustomersComponent implements OnInit {
  getOrders(c: any) {
    this.router.navigateByUrl('/orders/' + c.id);
  }
  customers: any;
  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit() {
    this.http
      .get('http://localhost:8888/CUSTOMER-SERVICE/customers')
      .subscribe({
        next: (data) => {
          this.customers = data;
        },
        error: (error) => console.error(error),
      });
  }
}
