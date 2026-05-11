-- Beej E-Commerce Database Initialization Script
-- Created for MySQL 8.0+

-- Create database if not exists
CREATE DATABASE IF NOT EXISTS beej_ecommerce CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE beej_ecommerce;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone_number VARCHAR(20),
    role ENUM('ADMIN', 'USER', 'MANAGER') NOT NULL DEFAULT 'USER',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    account_non_expired BOOLEAN NOT NULL DEFAULT TRUE,
    account_non_locked BOOLEAN NOT NULL DEFAULT TRUE,
    credentials_non_expired BOOLEAN NOT NULL DEFAULT TRUE,
    email_verified BOOLEAN NOT NULL DEFAULT FALSE,
    verification_token VARCHAR(255),
    reset_token VARCHAR(255),
    reset_token_expiry DATETIME,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0
);

-- Categories table
CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    slug VARCHAR(100) NOT NULL UNIQUE,
    image_url VARCHAR(255),
    parent_id BIGINT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (parent_id) REFERENCES categories(id) ON DELETE SET NULL
);

-- Products table
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    short_description VARCHAR(500),
    sku VARCHAR(100) NOT NULL UNIQUE,
    price DECIMAL(10, 2) NOT NULL,
    compare_price DECIMAL(10, 2),
    cost_price DECIMAL(10, 2),
    weight DECIMAL(8, 2),
    dimensions VARCHAR(50),
    category_id BIGINT,
    brand VARCHAR(100),
    tags VARCHAR(255),
    status ENUM('ACTIVE', 'INACTIVE', 'DRAFT') NOT NULL DEFAULT 'ACTIVE',
    track_inventory BOOLEAN NOT NULL DEFAULT TRUE,
    featured BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL,
    INDEX idx_product_name (name),
    INDEX idx_product_sku (sku),
    INDEX idx_product_category (category_id),
    INDEX idx_product_status (status),
    INDEX idx_product_featured (featured)
);

-- Product Images table
CREATE TABLE IF NOT EXISTS product_images (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    alt_text VARCHAR(255),
    sort_order INT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    INDEX idx_product_image_product (product_id)
);

-- Inventory table
CREATE TABLE IF NOT EXISTS inventory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL UNIQUE,
    quantity INT NOT NULL DEFAULT 0,
    reserved_quantity INT NOT NULL DEFAULT 0,
    available_quantity INT GENERATED ALWAYS AS (quantity - reserved_quantity) STORED,
    reorder_level INT NOT NULL DEFAULT 10,
    reorder_quantity INT NOT NULL DEFAULT 50,
    last_updated DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Shopping Cart table
CREATE TABLE IF NOT EXISTS shopping_cart (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_cart (user_id)
);

-- Cart Items table
CREATE TABLE IF NOT EXISTS cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    unit_price DECIMAL(10, 2) NOT NULL,
    total_price DECIMAL(10, 2) GENERATED ALWAYS AS (quantity * unit_price) STORED,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (cart_id) REFERENCES shopping_cart(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    UNIQUE KEY unique_cart_product (cart_id, product_id)
);

-- Orders table
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_number VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    status ENUM('PENDING', 'CONFIRMED', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED', 'REFUNDED') NOT NULL DEFAULT 'PENDING',
    payment_status ENUM('PENDING', 'PAID', 'FAILED', 'REFUNDED') NOT NULL DEFAULT 'PENDING',
    payment_method VARCHAR(50),
    subtotal DECIMAL(10, 2) NOT NULL,
    tax_amount DECIMAL(10, 2) NOT NULL DEFAULT 0,
    shipping_amount DECIMAL(10, 2) NOT NULL DEFAULT 0,
    discount_amount DECIMAL(10, 2) NOT NULL DEFAULT 0,
    total_amount DECIMAL(10, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL DEFAULT 'USD',
    notes TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE RESTRICT,
    INDEX idx_order_user (user_id),
    INDEX idx_order_status (status),
    INDEX idx_order_number (order_number)
);

-- Order Items table
CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    product_name VARCHAR(200) NOT NULL,
    product_sku VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    total_price DECIMAL(10, 2) GENERATED ALWAYS AS (quantity * unit_price) STORED,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE RESTRICT
);

-- Addresses table
CREATE TABLE IF NOT EXISTS addresses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    type ENUM('BILLING', 'SHIPPING', 'BOTH') NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    company VARCHAR(100),
    address_line1 VARCHAR(255) NOT NULL,
    address_line2 VARCHAR(255),
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100),
    postal_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    is_default BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_address_user (user_id)
);

-- Wishlist table
CREATE TABLE IF NOT EXISTS wishlist (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_product_wishlist (user_id, product_id)
);

-- Product Reviews table
CREATE TABLE IF NOT EXISTS product_reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    title VARCHAR(255),
    content TEXT,
    verified_purchase BOOLEAN NOT NULL DEFAULT FALSE,
    helpful_count INT NOT NULL DEFAULT 0,
    status ENUM('PENDING', 'APPROVED', 'REJECTED') NOT NULL DEFAULT 'PENDING',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_product_review (user_id, product_id),
    INDEX idx_review_product (product_id),
    INDEX idx_review_rating (rating),
    INDEX idx_review_status (status)
);

-- Insert default admin user
INSERT IGNORE INTO users (
    username, email, password, first_name, last_name, role, email_verified, enabled
) VALUES (
    'admin', 
    'admin@beej.com', 
    '$2a$10$EflZdJQp9rQ2rQ2rQ2rQ2uEflZdJQp9rQ2rQ2rQ2rQ2rQ2rQ2rQ2rQ2rQ2rQ2r', 
    'Admin', 
    'User', 
    'ADMIN', 
    TRUE, 
    TRUE
);

-- Insert default categories
INSERT IGNORE INTO categories (name, description, slug) VALUES
('Electronics', 'Electronic devices and gadgets', 'electronics'),
('Clothing', 'Fashion and apparel', 'clothing'),
('Books', 'Books and literature', 'books'),
('Home & Garden', 'Home improvement and garden supplies', 'home-garden'),
('Sports & Outdoors', 'Sports equipment and outdoor gear', 'sports-outdoors'),
('Toys & Games', 'Toys and games for all ages', 'toys-games');

-- Insert sample products
INSERT IGNORE INTO products (name, description, short_description, sku, price, category_id, status, featured) VALUES
('Laptop Pro 15"', 'High-performance laptop with 15-inch display, 16GB RAM, 512GB SSD', 'Powerful laptop for professionals', 'LP-15-001', 999.99, 1, 'ACTIVE', TRUE),
('Wireless Headphones', 'Premium noise-cancelling wireless headphones', 'High-quality audio experience', 'WH-001', 199.99, 1, 'ACTIVE', TRUE),
('Cotton T-Shirt', 'Comfortable 100% cotton t-shirt', 'Classic cotton t-shirt', 'CT-001', 19.99, 2, 'ACTIVE', FALSE),
('JavaScript Guide', 'Complete guide to JavaScript programming', 'Learn JavaScript from scratch', 'BK-001', 29.99, 3, 'ACTIVE', FALSE),
('Garden Tool Set', 'Complete set of essential garden tools', 'Everything you need for gardening', 'GT-001', 49.99, 4, 'ACTIVE', FALSE),
('Yoga Mat', 'Non-slip exercise yoga mat', 'Perfect for yoga and exercise', 'YM-001', 29.99, 5, 'ACTIVE', TRUE);

-- Update inventory for sample products
INSERT IGNORE INTO inventory (product_id, quantity, reorder_level) VALUES
(1, 50, 10),
(2, 100, 20),
(3, 200, 50),
(4, 75, 15),
(5, 30, 10),
(6, 60, 15);

-- Insert sample product images
INSERT IGNORE INTO product_images (product_id, image_url, alt_text, sort_order) VALUES
(1, 'https://via.placeholder.com/400x300/4F46E5/FFFFFF?text=Laptop+Pro', 'Laptop Pro 15 inch', 1),
(2, 'https://via.placeholder.com/400x300/10B981/FFFFFF?text=Wireless+Headphones', 'Wireless Headphones', 1),
(3, 'https://via.placeholder.com/400x300/F59E0B/FFFFFF?text=Cotton+T-Shirt', 'Cotton T-Shirt', 1),
(4, 'https://via.placeholder.com/400x300/EF4444/FFFFFF?text=JavaScript+Guide', 'JavaScript Guide Book', 1),
(5, 'https://via.placeholder.com/400x300/8B5CF6/FFFFFF?text=Garden+Tools', 'Garden Tool Set', 1),
(6, 'https://via.placeholder.com/400x300/06B6D4/FFFFFF?text=Yoga+Mat', 'Yoga Mat', 1);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_products_name_category ON products(name, category_id);
CREATE INDEX IF NOT EXISTS idx_products_price_range ON products(price);
CREATE INDEX IF NOT EXISTS idx_orders_user_status ON orders(user_id, status);
CREATE INDEX IF NOT EXISTS idx_order_items_order_product ON order_items(order_id, product_id);
CREATE INDEX IF NOT EXISTS idx_cart_items_cart_product ON cart_items(cart_id, product_id);

-- Create view for product summary
CREATE OR REPLACE VIEW product_summary AS
SELECT 
    p.id,
    p.name,
    p.price,
    p.status,
    p.featured,
    c.name as category_name,
    COALESCE(i.quantity, 0) as stock_quantity,
    COALESCE(i.available_quantity, 0) as available_quantity,
    (SELECT AVG(rating) FROM product_reviews pr WHERE pr.product_id = p.id AND pr.status = 'APPROVED') as average_rating,
    (SELECT COUNT(*) FROM product_reviews pr WHERE pr.product_id = p.id AND pr.status = 'APPROVED') as review_count
FROM products p
LEFT JOIN categories c ON p.category_id = c.id
LEFT JOIN inventory i ON p.id = i.product_id;

-- Create view for order summary
CREATE OR REPLACE VIEW order_summary AS
SELECT 
    o.id,
    o.order_number,
    o.user_id,
    u.username,
    u.email,
    o.status,
    o.payment_status,
    o.total_amount,
    o.created_at,
    COUNT(oi.id) as item_count
FROM orders o
JOIN users u ON o.user_id = u.id
LEFT JOIN order_items oi ON o.id = oi.order_id
GROUP BY o.id, o.order_number, o.user_id, u.username, u.email, o.status, o.payment_status, o.total_amount, o.created_at;
