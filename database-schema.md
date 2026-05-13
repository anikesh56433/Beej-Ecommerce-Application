# Beej E-Commerce Database Schema

## Database Configuration
- **Database**: MySQL 8.0
- **Database Name**: `beej_ecommerce`
- **Connection URL**: `jdbc:mysql://localhost:3306/beej_ecommerce?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true`
- **Username**: `root`
- **Password**: `root`
- **Hibernate DDL**: `update` (auto-creates/updates tables)

## Entity Tables

### 1. User Management

#### user_profiles
```sql
CREATE TABLE user_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    display_name VARCHAR(150),
    phone_number VARCHAR(20),
    date_of_birth DATE,
    gender VARCHAR(20),
    bio TEXT,
    profile_picture_url VARCHAR(500),
    cover_photo_url VARCHAR(500),
    website VARCHAR(255),
    occupation VARCHAR(100),
    company VARCHAR(100),
    location VARCHAR(255),
    timezone VARCHAR(50),
    language VARCHAR(10) DEFAULT 'en',
    currency VARCHAR(3) DEFAULT 'USD',
    newsletter_subscribed BOOLEAN NOT NULL DEFAULT FALSE,
    marketing_emails_enabled BOOLEAN NOT NULL DEFAULT TRUE,
    order_notifications_enabled BOOLEAN NOT NULL DEFAULT TRUE,
    promotion_notifications_enabled BOOLEAN NOT NULL DEFAULT TRUE,
    two_factor_enabled BOOLEAN NOT NULL DEFAULT FALSE,
    two_factor_secret VARCHAR(255),
    last_login_at DATETIME,
    last_login_ip VARCHAR(45),
    login_count INT NOT NULL DEFAULT 0,
    account_status VARCHAR(50) DEFAULT 'ACTIVE',
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    version BIGINT
);
```

#### user_addresses
```sql
CREATE TABLE user_addresses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    address_type VARCHAR(50) NOT NULL,
    is_default BOOLEAN NOT NULL DEFAULT FALSE,
    recipient_name VARCHAR(150),
    recipient_phone VARCHAR(20),
    address_line1 VARCHAR(255) NOT NULL,
    address_line2 VARCHAR(255),
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100),
    postal_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL,
    country_code VARCHAR(2),
    latitude DOUBLE,
    longitude DOUBLE,
    delivery_instructions TEXT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    version BIGINT
);
```

#### user_activity_logs
```sql
CREATE TABLE user_activity_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    activity_type VARCHAR(100) NOT NULL,
    description TEXT,
    ip_address VARCHAR(45),
    user_agent VARCHAR(500),
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    version BIGINT
);
```

### 2. Product Management

#### categories
```sql
CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    slug VARCHAR(255) NOT NULL UNIQUE,
    image_url VARCHAR(500),
    parent_id BIGINT,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    version BIGINT,
    FOREIGN KEY (parent_id) REFERENCES categories(id)
);
```

#### products
```sql
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    short_description VARCHAR(500),
    sku VARCHAR(255) NOT NULL UNIQUE,
    price DECIMAL(10,2) NOT NULL,
    compare_price DECIMAL(10,2),
    cost_price DECIMAL(10,2),
    weight DECIMAL(10,2),
    dimensions VARCHAR(255),
    category_id BIGINT,
    brand VARCHAR(255),
    tags VARCHAR(255),
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    track_inventory BOOLEAN NOT NULL DEFAULT TRUE,
    featured BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    version BIGINT,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);
```

#### product_images
```sql
CREATE TABLE product_images (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    alt_text VARCHAR(255),
    sort_order INT DEFAULT 0,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    version BIGINT,
    FOREIGN KEY (product_id) REFERENCES products(id)
);
```

#### inventory
```sql
CREATE TABLE inventory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL UNIQUE,
    quantity INT NOT NULL DEFAULT 0,
    reserved_quantity INT NOT NULL DEFAULT 0,
    reorder_level INT NOT NULL DEFAULT 10,
    reorder_quantity INT NOT NULL DEFAULT 50,
    last_updated DATETIME NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id)
);
```

### 3. Shopping Cart

#### carts
```sql
CREATE TABLE carts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    session_id VARCHAR(255),
    subtotal DECIMAL(10,2) DEFAULT 0.00,
    tax_amount DECIMAL(10,2) DEFAULT 0.00,
    shipping_amount DECIMAL(10,2) DEFAULT 0.00,
    discount_amount DECIMAL(10,2) DEFAULT 0.00,
    total DECIMAL(10,2) DEFAULT 0.00,
    currency VARCHAR(3) DEFAULT 'USD',
    coupon_code VARCHAR(50),
    coupon_discount DECIMAL(10,2) DEFAULT 0.00,
    item_count INT DEFAULT 0,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    version BIGINT
);
```

#### cart_items
```sql
CREATE TABLE cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    product_sku VARCHAR(100),
    product_image VARCHAR(500),
    quantity INT NOT NULL DEFAULT 1,
    unit_price DECIMAL(10,2) NOT NULL,
    total_price DECIMAL(10,2),
    compare_price DECIMAL(10,2),
    discount_amount DECIMAL(10,2) DEFAULT 0.00,
    in_stock BOOLEAN DEFAULT TRUE,
    available_quantity INT,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    version BIGINT,
    FOREIGN KEY (cart_id) REFERENCES carts(id)
);
```

### 4. Order Management

#### orders
```sql
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_number VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    customer_email VARCHAR(255) NOT NULL,
    customer_first_name VARCHAR(100),
    customer_last_name VARCHAR(100),
    customer_phone VARCHAR(20),
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    payment_status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    payment_method VARCHAR(50),
    payment_transaction_id VARCHAR(255),
    subtotal DECIMAL(10,2) NOT NULL,
    tax_amount DECIMAL(10,2) DEFAULT 0.00,
    shipping_amount DECIMAL(10,2) DEFAULT 0.00,
    discount_amount DECIMAL(10,2) DEFAULT 0.00,
    coupon_code VARCHAR(50),
    coupon_discount DECIMAL(10,2) DEFAULT 0.00,
    total DECIMAL(10,2) NOT NULL,
    currency VARCHAR(3) DEFAULT 'USD',
    shipping_address TEXT,
    billing_address TEXT,
    tracking_number VARCHAR(255),
    carrier VARCHAR(100),
    estimated_delivery DATETIME,
    shipped_at DATETIME,
    delivered_at DATETIME,
    cancelled_at DATETIME,
    cancellation_reason TEXT,
    notes TEXT,
    internal_notes TEXT,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    version BIGINT
);
```

#### order_items
```sql
CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    product_sku VARCHAR(100),
    product_image VARCHAR(500),
    product_description VARCHAR(1000),
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    compare_price DECIMAL(10,2),
    discount_amount DECIMAL(10,2) DEFAULT 0.00,
    tax_amount DECIMAL(10,2) DEFAULT 0.00,
    shipping_amount DECIMAL(10,2) DEFAULT 0.00,
    product_weight DECIMAL(10,2),
    product_dimensions VARCHAR(100),
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    version BIGINT,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);
```

#### order_status_history
```sql
CREATE TABLE order_status_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,
    comments TEXT,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    version BIGINT,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);
```

### 5. Payment Management

#### payments
```sql
CREATE TABLE payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    transaction_id VARCHAR(255) NOT NULL UNIQUE,
    order_id BIGINT NOT NULL,
    order_number VARCHAR(50) NOT NULL,
    user_id BIGINT NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    payment_gateway VARCHAR(50) NOT NULL,
    gateway_transaction_id VARCHAR(255),
    amount DECIMAL(10,2) NOT NULL,
    currency VARCHAR(3) NOT NULL DEFAULT 'USD',
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    gateway_status VARCHAR(50),
    failure_reason VARCHAR(500),
    gateway_response TEXT,
    gateway_request TEXT,
    refund_amount DECIMAL(10,2) DEFAULT 0.00,
    refund_reason VARCHAR(500),
    refund_date DATETIME,
    partial_refund_count INT DEFAULT 0,
    customer_email VARCHAR(255),
    customer_name VARCHAR(255),
    billing_address TEXT,
    ip_address VARCHAR(45),
    user_agent VARCHAR(500),
    processed_at DATETIME,
    completed_at DATETIME,
    failed_at DATETIME,
    expires_at DATETIME,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    version BIGINT
);
```

#### payment_methods
```sql
CREATE TABLE payment_methods (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    method_type VARCHAR(50) NOT NULL,
    provider VARCHAR(100),
    account_number VARCHAR(255),
    expiry_date VARCHAR(50),
    cardholder_name VARCHAR(255),
    is_default BOOLEAN NOT NULL DEFAULT FALSE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    version BIGINT
);
```

#### refunds
```sql
CREATE TABLE refunds (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    payment_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL,
    refund_id VARCHAR(255) NOT NULL UNIQUE,
    amount DECIMAL(10,2) NOT NULL,
    reason VARCHAR(500),
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    refund_date DATETIME,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    version BIGINT,
    FOREIGN KEY (payment_id) REFERENCES payments(id)
);
```

### 6. Wishlist Management

#### wishlists
```sql
CREATE TABLE wishlists (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    is_default BOOLEAN NOT NULL DEFAULT FALSE,
    is_private BOOLEAN NOT NULL DEFAULT TRUE,
    share_token VARCHAR(255),
    item_count INT NOT NULL DEFAULT 0,
    total_value DECIMAL(10,2) DEFAULT 0.00,
    notify_on_price_drop BOOLEAN NOT NULL DEFAULT TRUE,
    notify_on_back_in_stock BOOLEAN NOT NULL DEFAULT TRUE,
    last_viewed_at DATETIME,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    version BIGINT
);
```

#### wishlist_items
```sql
CREATE TABLE wishlist_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    wishlist_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    product_sku VARCHAR(100),
    product_image VARCHAR(500),
    product_price DECIMAL(10,2),
    priority INT DEFAULT 0,
    notes TEXT,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    version BIGINT,
    FOREIGN KEY (wishlist_id) REFERENCES wishlists(id)
);
```

### 7. Review Management

#### reviews
```sql
CREATE TABLE reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    order_id BIGINT,
    user_id BIGINT NOT NULL,
    customer_name VARCHAR(255),
    customer_email VARCHAR(255),
    rating INT NOT NULL,
    title VARCHAR(255),
    content TEXT,
    is_verified BOOLEAN NOT NULL DEFAULT FALSE,
    is_approved BOOLEAN NOT NULL DEFAULT FALSE,
    is_featured BOOLEAN NOT NULL DEFAULT FALSE,
    helpful_count INT NOT NULL DEFAULT 0,
    not_helpful_count INT NOT NULL DEFAULT 0,
    reviewer_ip VARCHAR(45),
    reviewer_user_agent VARCHAR(500),
    admin_response TEXT,
    admin_responded_at DATETIME,
    admin_responded_by VARCHAR(255),
    admin_responded_by_id BIGINT,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at DATETIME,
    deleted_by VARCHAR(255),
    deleted_by_id BIGINT,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    version BIGINT
);
```

#### review_images
```sql
CREATE TABLE review_images (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    review_id BIGINT NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    alt_text VARCHAR(255),
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    version BIGINT,
    FOREIGN KEY (review_id) REFERENCES reviews(id)
);
```

#### review_helpful
```sql
CREATE TABLE review_helpful (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    review_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    is_helpful BOOLEAN NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    version BIGINT,
    FOREIGN KEY (review_id) REFERENCES reviews(id)
);
```

## Database Setup Instructions

### 1. Create Database
```sql
CREATE DATABASE beej_ecommerce CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. Create User (Optional)
```sql
CREATE USER 'beej_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON beej_ecommerce.* TO 'beej_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Auto-Generation
The application will automatically create all tables when started with `spring.jpa.hibernate.ddl-auto=update` in the configuration.

## Indexes (Recommended for Performance)

```sql
-- User indexes
CREATE INDEX idx_user_profiles_email ON user_profiles(email);
CREATE INDEX idx_user_profiles_username ON user_profiles(username);
CREATE INDEX idx_user_addresses_user_id ON user_addresses(user_id);

-- Product indexes
CREATE INDEX idx_products_category_id ON products(category_id);
CREATE INDEX idx_products_sku ON products(sku);
CREATE INDEX idx_products_status ON products(status);
CREATE INDEX idx_categories_slug ON categories(slug);
CREATE INDEX idx_categories_parent_id ON categories(parent_id);

-- Order indexes
CREATE INDEX idx_orders_user_id ON orders(user_id);
CREATE INDEX idx_orders_order_number ON orders(order_number);
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_order_items_order_id ON order_items(order_id);
CREATE INDEX idx_order_items_product_id ON order_items(product_id);

-- Cart indexes
CREATE INDEX idx_carts_user_id ON carts(user_id);
CREATE INDEX idx_cart_items_cart_id ON cart_items(cart_id);
CREATE INDEX idx_cart_items_product_id ON cart_items(product_id);

-- Payment indexes
CREATE INDEX idx_payments_order_id ON payments(order_id);
CREATE INDEX idx_payments_transaction_id ON payments(transaction_id);
CREATE INDEX idx_payments_status ON payments(status);

-- Review indexes
CREATE INDEX idx_reviews_product_id ON reviews(product_id);
CREATE INDEX idx_reviews_user_id ON reviews(user_id);
CREATE INDEX idx_reviews_rating ON reviews(rating);
CREATE INDEX idx_reviews_is_approved ON reviews(is_approved);

-- Wishlist indexes
CREATE INDEX idx_wishlists_user_id ON wishlists(user_id);
CREATE INDEX idx_wishlist_items_wishlist_id ON wishlist_items(wishlist_id);
CREATE INDEX idx_wishlist_items_product_id ON wishlist_items(product_id);
```

## Foreign Key Constraints
All necessary foreign key constraints are defined in the entity classes and will be auto-generated by Hibernate.

## Notes
- All tables use `BIGINT AUTO_INCREMENT` for primary keys
- All tables include audit fields: `created_at`, `updated_at`, `version`
- The database uses MySQL 8.0 with UTF-8 character set
- Hibernate will automatically handle table creation and updates
- Consider adding indexes for frequently queried columns for better performance
