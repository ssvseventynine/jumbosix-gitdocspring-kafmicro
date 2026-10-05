import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// This structure maps exactly to the fields in your Spring Boot Product Entity
export interface Product {
  id?: number;
  name: string;
  sku: string;
  price: number;
}

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  // Point directly to our API Gateway port! 
  // The gateway automatically routes "/product-service" prefixes down to port 8081
  private baseUrl = 'http://localhost:8080/product-service/products';

  constructor(private http: HttpClient) { }

  // Fetch all products from MySQL via the Gateway
  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.baseUrl);
  }

  // POST a new product payload to the backend
  addProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(this.baseUrl, product);
  }
}