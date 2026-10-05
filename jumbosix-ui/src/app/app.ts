import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductService, Product } from './services/product';
import { OrderService, Order } from './services/order'; // 1. Import Order service elements

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class AppComponent implements OnInit {
  products: Product[] = [];
  orders: Order[] = []; // 2. Declare the reactive orders array state
  
  // Bound variables for the Product Input Form
  newProductName: string = '';
  newProductSku: string = '';
  newProductPrice: number = 0;

  // Bound variables for the New Order Purchase Form
  selectedProductId!: string;
  orderQuantity: number = 1;

  // 3. Inject OrderService alongside ProductService
  constructor(
    private productService: ProductService,
    private orderService: OrderService
  ) {}

  ngOnInit(): void {
    this.loadProducts();
    this.loadOrders(); // 4. Automatically pull order logs on startup
  }

  // --- PRODUCT LOGIC ---
  loadProducts(): void {
    this.productService.getProducts().subscribe({
      next: (data) => this.products = data,
      error: (err) => console.error('Error fetching catalog data:', err)
    });
  }

  onCreateProduct(): void {
    if (!this.newProductName || !this.newProductSku || this.newProductPrice <= 0) {
      alert('Please fill out all product details correctly.');
      return;
    }

    const payload: Product = {
      name: this.newProductName,
      sku: this.newProductSku,
      price: this.newProductPrice
    };

    this.productService.addProduct(payload).subscribe({
      next: () => {
        this.loadProducts();
        this.clearForm();
      },
      error: (err) => alert('Failed to create product: ' + err.message)
    });
  }

  clearForm(): void {
    this.newProductName = '';
    this.newProductSku = '';
    this.newProductPrice = 0;
  }

  calculateOrderTotal(orderItem: any, quantity: number): number {
    if (!orderItem || !orderItem.productSku) return 0;
    
    // Cross-reference the order item's SKU against our live memory catalog array
    const matchingProduct = this.products.find(p => p.sku === orderItem.productSku);
    if (!matchingProduct) return 0;
    
    return matchingProduct.price * quantity;
  }
  
  // --- ORDER LOGIC ---
  loadOrders(): void {
    this.orderService.getOrders().subscribe({
      next: (data) => this.orders = data,
      error: (err) => console.error('Error fetching orders data:', err)
    });
  }

  // Replace your old onPlaceOrder() method with this clean lookup logic
  onPlaceOrder(): void {
    if (!this.selectedProductId || this.orderQuantity <= 0) {
      alert('Please select a valid product and quantity.');
      return;
    }

    // Now searching directly by matching SKU string cleanly!
    const targetProduct = this.products.find(p => p.sku === this.selectedProductId);
    const calculatedTotal = targetProduct ? targetProduct.price * this.orderQuantity : 0;

    const payload: any = {
      productSku: this.selectedProductId,
      quantity: this.orderQuantity,
      totalPrice: calculatedTotal,
      status: 'PENDING'
    };

    this.orderService.createOrder(payload).subscribe({
      next: () => {
        this.loadOrders();
        this.orderQuantity = 1;
        alert('Order placed successfully!');
      },
      error: (err) => alert('Failed to route order: ' + err.message)
    });
  }
}