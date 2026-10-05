import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// This structure maps to the fields in your Spring Boot Order Microservice Entity
// Look near the top of your app.ts file for the interface declaration
export interface Order {
  id?: number;
  productSku: string; // <-- ENSURE THIS IS PRESENT
  quantity: number;
  totalPrice: number;
  status: string;
}

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  // Point directly to our API Gateway port!
  // The gateway routes "/order-service" prefixes down to your Order Microservice (e.g., Port 8082)
  private baseUrl = 'http://localhost:8080/order-service/orders';

  constructor(private http: HttpClient) { }

  // Fetch all orders from your Order Database via the Gateway
  getOrders(): Observable<Order[]> {
    return this.http.get<Order[]>(this.baseUrl);
  }

  // POST a new order allocation request to the backend
  createOrder(order: Order): Observable<Order> {
    return this.http.post<Order>(this.baseUrl, order);
  }
}