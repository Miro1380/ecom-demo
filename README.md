# Demo API

A RESTful API built with Spring Boot for managing customers, orders, and products. Orders are integrated with Kafka for event-driven processing.

## Base URLs

| Resource | Base URL |
|---|---|
| Customers | `/api/customers` |
| Orders | `/api/orders` |
| Products | `/api/products` |

---

## Customers

### Get All Customers
**GET** `/api/customers`

- **200 OK** – Returns a list of all customers
- **204 No Content** – No customers found

---

### Get Customer by ID
**GET** `/api/customers/{customerId}`

| Path Variable | Type | Description |
|---|---|---|
| `customerId` | `String` | The unique customer ID |

- **200 OK** – Returns the matching customer
- **404 Not Found** – Customer not found

---

### Get Orders by Customer ID
**GET** `/api/customers/{customerId}/orders`

Returns all orders associated with a specific customer.

| Path Variable | Type | Description |
|---|---|---|
| `customerId` | `String` | The unique customer ID |

- **200 OK** – Returns a list of `OrderResponse` objects
- **204 No Content** – No orders found for this customer

---

### Get Customers by Status
**GET** `/api/customers/status/{status}`

Returns all customers with the given status (e.g. `ACTIVE`, `INACTIVE`).

| Path Variable | Type | Description |
|---|---|---|
| `status` | `String` | The customer status |

- **200 OK** – Returns a list of `CustomerStatusResponse` objects
- **204 No Content** – No customers found with this status

---

### Create Customer
**POST** `/api/customers`

Creates a new customer.

- **Request Body:** `CreateCustomerRequest` (JSON)
- **200 OK** – Returns the newly created customer

---

## Orders

### Create Order
**POST** `/api/orders`

Places a new order and publishes an event to Kafka.

- **Request Body:** `CreateOrderRequest` (JSON)
- **200 OK** – `"Order placed and event sent to kafka"`

---

### Get All Orders
**GET** `/api/orders`

- **200 OK** – Returns a list of all orders
- **204 No Content** – No orders found

---

### Get Order by ID
**GET** `/api/orders/{orderId}`

| Path Variable | Type | Description |
|---|---|---|
| `orderId` | `String` | The unique order ID |

- **200 OK** – Returns the matching order
- **404 Not Found** – Order not found

---

### Get Orders by Status
**GET** `/api/orders/status/{orderStatus}`

| Path Variable | Type | Description |
|---|---|---|
| `orderStatus` | `String` | The order status |

- **200 OK** – Returns matching orders
- **204 No Content** – No orders found with this status

---

### Update Order Status
**PATCH** `/api/orders/{orderId}/status`

Updates the status of an existing order.

| Path Variable | Type | Description |
|---|---|---|
| `orderId` | `String` | The unique order ID |

| Query Param | Type | Description |
|---|---|---|
| `status` | `String` | The new order status |

- **200 OK** – `"Order Status updated for order: {orderId}"`

---

## Products

### Get All Products
**GET** `/api/products`

- **200 OK** – Returns a list of all products
- **204 No Content** – No products found

---

### Get Product by ID
**GET** `/api/products/{productId}`

| Path Variable | Type | Description |
|---|---|---|
| `productId` | `String` | The unique product ID |

- **200 OK** – Returns the matching product

---

### Get Products by Category
**GET** `/api/products/category/{category}`

| Path Variable | Type | Description |
|---|---|---|
| `category` | `String` | The product category |

- **200 OK** – Returns matching products
- **204 No Content** – No products found in this category

---

### Get Products by Status
**GET** `/api/products/status/{status}`

| Path Variable | Type | Description |
|---|---|---|
| `status` | `String` | The product status |

- **200 OK** – Returns matching products
- **204 No Content** – No products found with this status

---

### Create Product
**POST** `/api/products`

Creates a new product.

- **Request Body:** `CreateProductRequest` (JSON)
- **200 OK** – Returns the newly created product

---

## Tech Stack

- Java
- Spring Boot
- Lombok
- Apache Kafka

## Getting Started

1. Clone the repository
   ```bash
   git clone <your-repo-url>
   ```
2. Navigate to the project directory
   ```bash
   cd demo
   ```
3. Ensure Kafka is running locally (default port `9092`)

4. Build and run the application
   ```bash
   ./mvnw spring-boot:run
   ```

The API will be available at `https://ecom-demo-production-f909.up.railway.app/api`.
