# API Testing Guide for Beej Ecommerce Application

## Overview
This guide provides comprehensive testing strategies for all PST application endpoints with the test data created in `test-data.sql`.

## Prerequisites
- Application running on `http://localhost:8081`
- Test data loaded (`test-data.sql` executed)
- API testing tool (Postman, Insomnia, or curl)

## Authentication
Most endpoints require JWT authentication. Use these test users:

### Admin User
- **Email:** `admin@beej.com`
- **Password:** `admin123`

### Regular Users
- **Email:** `john.doe@email.com` - **Password:** `password123`
- **Email:** `jane.smith@email.com` - **Password:** `password123`
- **Email:** `mike.wilson@email.com` - **Password:** `password123`
- **Email:** `sarah.jones@email.com` - **Password:** `password123`
- **Email:** `david.brown@email.com` - **Password:** `password123`

### JWT Token Generation
```bash
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@beej.com",
    "password": "admin123"
  }'
```

## API Endpoints Testing

### 1. Authentication Endpoints

#### Login
```bash
POST /api/auth/login
Content-Type: application/json

{
  "email": "admin@beej.com",
  "password": "admin123"
}
```

#### Register
```bash
POST /api/auth/register
Content-Type: application/json

{
  "username": "newuser",
  "email": "newuser@example.com",
  "password": "password123",
  "firstName": "New",
  "lastName": "User"
}
```

#### Refresh Token
```bash
POST /api/auth/refresh
Authorization: Bearer <refresh_token>
```

### 2. User Management

#### Get All Users
```bash
GET /api/admin/users
Authorization: Bearer <admin_token>
```

#### Get User by ID
```bash
GET /api/admin/users/3
Authorization: Bearer <admin_token>
```

#### Update User
```bash
PUT /api/admin/users/3
Authorization: Bearer <admin_token>
Content-Type: application/json

{
  "firstName": "Updated Name",
  "phoneNumber": "+1-555-010999"
}
```

### 3. Product Management

#### Get All Products
```bash
GET /api/products?page=0&size=10&sort=createdAt,desc
Authorization: Bearer <token>
```

#### Get Product by ID
```bash
GET /api/products/1
Authorization: Bearer <token>
```

#### Search Products
```bash
GET /api/products/search?query=laptop&category=1&minPrice=500&maxPrice=1500
Authorization: Bearer <token>
```

#### Create Product (Admin)
```bash
POST /api/admin/products
Authorization: Bearer <admin_token>
Content-Type: application/json

{
  "name": "New Product",
  "description": "Product description",
  "categoryId": 1,
  "price": 99.99,
  "sku": "NEW-001",
  "stockQuantity": 100
}
```

### 4. Order Management

#### Get User Orders
```bash
GET /api/orders/my-orders
Authorization: Bearer <user_token>
```

#### Get Order by ID
```bash
GET /api/orders/1
Authorization: Bearer <token>
```

#### Create Order
```bash
POST /api/orders
Authorization: Bearer <user_token>
Content-Type: application/json

{
  "items": [
    {
      "productId": 1,
      "quantity": 2
    }
  ],
  "shippingAddress": {
    "addressLine1": "123 Test St",
    "city": "Test City",
    "state": "TS",
    "postalCode": "12345",
    "country": "USA"
  }
}
```

#### Update Order Status (Admin)
```bash
PUT /api/admin/orders/1/status
Authorization: Bearer <admin_token>
Content-Type: application/json

{
  "status": "SHIPPED",
  "trackingNumber": "TRACK123456"
}
```

### 5. Cart Management

#### Get User Cart
```bash
GET /api/cart
Authorization: Bearer <user_token>
```

#### Add to Cart
```bash
POST /api/cart/items
Authorization: Bearer <user_token>
Content-Type: application/json

{
  "productId": 1,
  "quantity": 2
}
```

#### Update Cart Item
```bash
PUT /api/cart/items/1
Authorization: Bearer <user_token>
Content-Type: application/json

{
  "quantity": 3
}
```

#### Remove from Cart
```bash
DELETE /api/cart/items/1
Authorization: Bearer <user_token>
```

### 6. Wishlist Management

#### Get User Wishlists
```bash
GET /api/wishlists
Authorization: Bearer <user_token>
```

#### Get Wishlist Items
```bash
GET /api/wishlists/1/items
Authorization: Bearer <user_token>
```

#### Add to Wishlist
```bash
POST /api/wishlists/1/items
Authorization: Bearer <user_token>
Content-Type: application/json

{
  "productId": 1,
  "quantity": 1
}
```

### 7. Review Management

#### Get Product Reviews
```bash
GET /api/reviews/product/1?page=0&size=5
Authorization: Bearer <token>
```

#### Create Review
```bash
POST /api/reviews
Authorization: Bearer <user_token>
Content-Type: application/json

{
  "productId": 1,
  "orderId": 1,
  "rating": 5,
  "title": "Amazing Product!",
  "content": "This product exceeded my expectations. Highly recommend!"
}
```

#### Vote Review Helpful
```bash
POST /api/reviews/1/helpful
Authorization: Bearer <user_token>
Content-Type: application/json

{
  "isHelpful": true
}
```

### 8. Payment Processing

#### Process Payment
```bash
POST /api/payments/process
Authorization: Bearer <user_token>
Content-Type: application/json

{
  "orderId": 1,
  "paymentMethod": "CREDIT_CARD",
  "gatewayResponse": "success"
}
```

#### Get Payment History
```bash
GET /api/payments/my-payments
Authorization: Bearer <user_token>
```

### 9. Notifications

#### Get User Notifications
```bash
GET /api/notifications?page=0&size=10&unreadOnly=true
Authorization: Bearer <user_token>
```

#### Mark Notification as Read
```bash
PUT /api/notifications/1/read
Authorization: Bearer <user_token>
```

## Testing Scenarios

### Scenario 1: Complete Purchase Flow
1. **Login** as regular user
2. **Browse products** and search for items
3. **Add items** to cart
4. **Create order** from cart
5. **Process payment** for order
6. **Verify order status** changes
7. **Leave review** for purchased items

### Scenario 2: Admin Order Management
1. **Login** as admin user
2. **View all orders** with different statuses
3. **Update order status** (PENDING → PROCESSING → SHIPPED → DELIVERED)
4. **Handle order cancellation** and refunds

### Scenario 3: Wishlist Management
1. **Login** as user
2. **Create multiple wishlists**
3. **Add items** to wishlists
4. **Move items** between wishlists
5. **Monitor price drop notifications**

### Scenario 4: Review System
1. **Login** as user who made purchases
2. **View existing reviews**
3. **Create new reviews**
4. **Vote on reviews** (helpful/not helpful)
5. **Test review moderation** (admin approval)

## Load Testing

### Performance Testing
```bash
# Simulate 100 concurrent users
ab -n 100 -c 10 -t 60s http://localhost:8081/api/products
```

### Stress Testing
```bash
# Gradually increase load
hey -z 30s -c 50 -q 1000 http://localhost:8081/api/products
```

## Database Verification

After running tests, verify data integrity:

```sql
-- Check record counts
SELECT 'users' as table_name, COUNT(*) as count FROM user_profiles
UNION ALL
SELECT 'products', COUNT(*) FROM products
UNION ALL
SELECT 'orders', COUNT(*) FROM orders
UNION ALL
SELECT 'reviews', COUNT(*) FROM reviews;

-- Check data relationships
SELECT u.username, COUNT(o.id) as order_count 
FROM user_profiles u 
LEFT JOIN orders o ON u.user_id = o.user_id 
GROUP BY u.username;
```

## Common Test Cases

### Negative Testing
- **Invalid credentials**: Wrong email/password
- **Missing fields**: Incomplete request data
- **Invalid data**: Malformed JSON, invalid IDs
- **Unauthorized**: Access without proper tokens
- **Rate limiting**: Multiple rapid requests

### Edge Cases
- **Empty results**: Search with no matches
- **Pagination**: Large page numbers, invalid sizes
- **Special characters**: Unicode in names/descriptions
- **Large data**: Maximum field lengths
- **Concurrent operations**: Simultaneous cart modifications

## Automation Scripts

### Bash Script for User Registration
```bash
#!/bin/bash
for i in {1..10}; do
  curl -X POST http://localhost:8081/api/auth/register \
    -H "Content-Type: application/json" \
    -d "{
      \"username\": \"testuser$i\",
      \"email\": \"testuser$i@example.com\",
      \"password\": \"password123\",
      \"firstName\": \"Test\",
      \"lastName\": \"User$i\"
    }"
  echo "Created testuser$i"
done
```

### Python Script for Order Creation
```python
import requests
import json
import random

BASE_URL = "http://localhost:8081"
PRODUCT_IDS = [1, 2, 3, 4, 5]

def create_order(user_token, product_ids):
    headers = {"Authorization": f"Bearer {user_token}"}
    order_data = {
        "items": [{"productId": pid, "quantity": random.randint(1, 3)} for pid in product_ids],
        "shippingAddress": {
            "addressLine1": f"{random.randint(100, 999)} Test St",
            "city": "Test City",
            "state": "TS",
            "postalCode": "12345",
            "country": "USA"
        }
    }
    
    response = requests.post(f"{BASE_URL}/api/orders", 
                           json=order_data, 
                           headers=headers)
    return response.json()

# Get auth token first
auth_response = requests.post(f"{BASE_URL}/api/auth/login", 
                            json={"email": "john.doe@email.com", "password": "password123"})
token = auth_response.json()["token"]

# Create multiple orders
for i in range(5):
    result = create_order(token, PRODUCT_IDS)
    print(f"Order {i+1} created: {result['id']}")
```

## Monitoring and Debugging

### Enable Debug Logging
Add to `application.yml`:
```yaml
logging:
  level:
    com.beej: DEBUG
    org.springframework.security: DEBUG
    org.springframework.web: DEBUG
```

### Common Issues and Solutions

1. **401 Unauthorized**
   - Check token validity
   - Verify token format in Authorization header
   - Ensure user has proper permissions

2. **400 Bad Request**
   - Validate JSON syntax
   - Check required fields
   - Verify data types and formats

3. **404 Not Found**
   - Confirm endpoint URLs
   - Check resource IDs
   - Verify API version

4. **500 Internal Server Error**
   - Check application logs
   - Verify database connectivity
   - Review recent code changes

## Test Data Summary

The test data includes:
- **7 Users** (2 admin, 5 customers)
- **8 Categories** (Electronics, Clothing, etc.)
- **10 Products** (Various categories and price ranges)
- **5 Orders** (Different statuses: DELIVERED, PROCESSING, SHIPPED, CANCELLED, PENDING)
- **3 Active Carts** with 6 items
- **4 Wishlists** with 5 items
- **6 Reviews** with ratings and images
- **4 Payments** (Completed, Processing, Refunded)
- **5 Notifications** (Various types and priorities)
- **1 Admin Dashboard Stats**

This comprehensive dataset allows testing of all major application features and workflows.
