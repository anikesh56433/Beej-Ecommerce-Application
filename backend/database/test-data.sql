-- =====================================================
-- BEEJ ECOMMERCE APPLICATION - TEST DATA GENERATION
-- =====================================================
-- This script creates comprehensive test data for all modules
-- Run this script after creating the database schema

-- =====================================================
-- 1. USERS AND USER PROFILES
-- =====================================================

-- Admin Users
INSERT INTO user_profiles (id, user_id, username, email, first_name, last_name, display_name, phone_number, language, currency, newsletter_subscribed, marketing_emails_enabled, order_notifications_enabled, promotion_notifications_enabled, two_factor_enabled, login_count, account_status, created_at, updated_at, version) VALUES
(1, 1, 'admin', 'admin@beej.com', 'Admin', 'User', 'Admin User', '+1-555-0101', 'en', 'USD', true, true, true, true, false, 42, 'ACTIVE', NOW(), NOW(), 1),
(2, 2, 'manager', 'manager@beej.com', 'Store', 'Manager', 'Store Manager', '+1-555-0102', 'en', 'USD', true, true, true, true, false, 28, 'ACTIVE', NOW(), NOW(), 1);

-- Regular Customers

INSERT INTO user_profiles (id, user_id, username, email, first_name, last_name, display_name, phone_number, date_of_birth, gender, bio, location, language, currency, newsletter_subscribed, marketing_emails_enabled, order_notifications_enabled, promotion_notifications_enabled, two_factor_enabled, login_count, account_status, created_at, updated_at, version) VALUES
(3, 3, 'john_doe', 'john.doe@email.com', 'John', 'Doe', 'John Doe', '+1-555-0103', '1990-05-15', 'MALE', 'Software engineer who loves online shopping', 'New York, USA', 'en', 'USD', true, true, true, true, false, 15, 'ACTIVE', NOW(), NOW(), 1),
(4, 4, 'jane_smith', 'jane.smith@email.com', 'Jane', 'Smith', 'Jane Smith', '+1-555-0104', '1992-08-22', 'FEMALE', 'Fashion enthusiast and regular shopper', 'Los Angeles, CA', 'en', 'USD', true, false, true, true, false, 8, 'ACTIVE', NOW(), NOW(), 1),
(5, 5, 'mike_wilson', 'mike.wilson@email.com', 'Mike', 'Wilson', 'Mike Wilson', '+1-555-0105', '1985-12-10', 'MALE', 'Tech gadget lover', 'Chicago, IL', 'en', 'USD', false, false, true, false, false, 3, 'ACTIVE', NOW(), NOW(), 1),
(6, 6, 'sarah_jones', 'sarah.jones@email.com', 'Sarah', 'Jones', 'Sarah Jones', '+1-555-0106', '1995-03-18', 'FEMALE', 'Home decor and lifestyle products', 'Seattle, WA', 'en', 'USD', true, true, true, true, false, 12, 'ACTIVE', NOW(), NOW(), 1),
(7, 7, 'david_brown', 'david.brown@email.com', 'David', 'Brown', 'David Brown', '+1-555-0107', '1988-07-25', 'MALE', 'Student and budget-conscious shopper', 'Boston, MA', 'en', 'USD', true, false, true, false, false, 5, 'ACTIVE', NOW(), NOW(), 1);

-- User Addresses
INSERT INTO user_addresses (id, user_id, address_type, is_default, recipient_name, recipient_phone, address_line1, city, state, postal_code, country, country_code, is_active, created_at, updated_at, version) VALUES
(1, 3, 'HOME', true, 'John Doe', '+1-555-0103', '123 Main Street Apt 4B', 'New York', 'NY', '10001', 'USA', 'US', true, NOW(), NOW(), 1),
(2, 3, 'WORK', false, 'John Doe', '+1-555-0103', '456 Tech Avenue Floor 12', 'New York', 'NY', '10005', 'USA', 'US', true, NOW(), NOW(), 1),
(3, 4, 'HOME', true, 'Jane Smith', '+1-555-0104', '789 Fashion Boulevard', 'Los Angeles', 'CA', '90210', 'USA', 'US', true, NOW(), NOW(), 1),
(4, 5, 'HOME', true, 'Mike Wilson', '+1-555-0105', '321 Innovation Drive', 'Chicago', 'IL', '60601', 'USA', 'US', true, NOW(), NOW(), 1),
(5, 6, 'HOME', true, 'Sarah Jones', '+1-555-0106', '456 Pine Street Apt 7', 'Seattle', 'WA', '98101', 'USA', 'US', true, NOW(), NOW(), 1),
(6, 7, 'HOME', true, 'David Brown', '+1-555-0107', '221 Commonwealth Ave', 'Boston', 'MA', '02130', 'USA', 'US', true, NOW(), NOW(), 1);

-- =====================================================
-- 2. PRODUCT CATALOG
-- =====================================================

-- Categories
INSERT INTO categories (id, name, description, parent_id, is_active, created_at, updated_at, version) VALUES
(1, 'Electronics', 'Latest electronic gadgets and devices', NULL, true, NOW(), NOW(), 1),
(2, 'Clothing', 'Fashion and apparel for all seasons', NULL, true, NOW(), NOW(), 1),
(3, 'Home & Garden', 'Everything for your home and garden needs', NULL, true, NOW(), NOW(), 1),
(4, 'Books', 'Wide selection of books and e-books', NULL, true, NOW(), NOW(), 1),
(5, 'Sports & Outdoors', 'Equipment for sports and outdoor activities', NULL, true, NOW(), NOW(), 1),
(6, 'Toys & Games', 'Toys and games for all ages', NULL, true, NOW(), NOW(), 1),
(7, 'Beauty & Personal Care', 'Cosmetics and personal care products', NULL, true, NOW(), NOW(), 1),
(8, 'Food & Beverages', 'Groceries and specialty foods', NULL, true, NOW(), NOW(), 1);

-- Products
INSERT INTO products (id, name, description, short_description, sku, category_id, brand, price, compare_price, cost_price, sale_price, weight, dimensions, track_inventory, stock_quantity, low_stock_threshold, is_active, is_featured, meta_title, meta_description, meta_keywords, created_at, updated_at, version) VALUES
-- Electronics
(1, 'Wireless Bluetooth Headphones', 'Premium noise-cancelling wireless headphones with 30-hour battery life and superior sound quality.', 'Premium wireless headphones', 'WBH-001', 1, 'AudioTech', 299.99, 399.99, 149.99, 249.99, 0.5, '7x6x3 inches', true, 50, 10, true, true, 'Best Wireless Headphones 2024', 'Premium noise-cancelling headphones with 30-hour battery life', 'headphones, wireless, bluetooth, audio', NOW(), NOW(), 1),
(2, 'Smart Watch Pro', 'Advanced fitness tracking smartwatch with heart rate monitor, GPS, and smartphone integration.', 'Fitness smartwatch with health tracking', 'SWP-002', 1, 'TechWear', 449.99, 599.99, 224.99, 349.99, 0.2, '1.8x1.6x0.4 inches', true, 30, 5, true, true, 'Smart Watch Pro - Best Fitness Tracker', 'Advanced fitness tracking smartwatch with health monitoring', 'smartwatch, fitness, health, GPS', NOW(), NOW(), 1),
(3, 'Laptop Ultra', 'High-performance laptop with 16GB RAM, 512GB SSD, and dedicated graphics card.', 'Gaming laptop with powerful specs', 'LU-003', 1, 'PowerPC', 1299.99, 1599.99, 899.99, 1099.99, 2.1, '14x9.5x0.7 inches', true, 15, 3, true, true, 'Gaming Laptop Ultra - High Performance', 'High-performance laptop for gaming and work', 'laptop, gaming, computer, electronics', NOW(), NOW(), 1),

-- Clothing
(4, 'Men''s Classic T-Shirt', 'Comfortable 100% cotton t-shirt in various colors.', 'Classic cotton t-shirt', 'MTS-004', 2, 'FashionBrand', 29.99, 39.99, 14.99, 24.99, 0.3, '20x16x1 inches', true, 100, 20, true, false, 'Men''s T-Shirt Classic Cotton', 'Comfortable cotton t-shirt for everyday wear', 't-shirt, men, cotton, casual', NOW(), NOW(), 1),
(5, 'Women''s Summer Dress', 'Elegant summer dress perfect for special occasions.', 'Floral summer dress', 'WSD-005', 2, 'ElegantWear', 89.99, 129.99, 44.99, 69.99, 0.4, '18x14x2 inches', true, 60, 15, true, true, 'Women''s Summer Dress Elegant', 'Beautiful summer dress for special occasions', 'dress, women, summer, elegant', NOW(), NOW(), 1),
(6, 'Denim Jacket', 'Classic denim jacket with modern fit and styling.', 'Modern denim jacket', 'DJ-006', 2, 'DenimCo', 119.99, 159.99, 69.99, 99.99, 0.8, '22x18x3 inches', true, 40, 8, true, false, 'Denim Jacket Classic Modern', 'Classic denim jacket with contemporary styling', 'jacket, denim, outerwear', NOW(), NOW(), 1),

-- Home & Garden
(7, 'Smart LED Bulb Set', 'Energy-efficient LED bulbs with smartphone control and scheduling.', 'WiFi-controlled LED bulbs', 'SLB-007', 3, 'SmartHome', 49.99, 79.99, 19.99, 34.99, 0.2, '3x3x5 inches', true, 200, 50, true, false, 'Smart LED Bulb Set - Energy Efficient', 'WiFi-controlled LED lighting for smart homes', 'LED, smart home, lighting, energy efficient', NOW(), NOW(), 1),
(8, 'Indoor Plant Collection', 'Set of 6 low-maintenance indoor plants with decorative pots.', 'Easy care indoor plants', 'IPC-008', 3, 'GreenThumb', 89.99, 129.99, 39.99, 69.99, 2.5, '12x8x15 inches', true, 80, 20, true, false, 'Indoor Plant Collection Set', 'Perfect indoor plants for home decoration', 'plants, indoor, home decor, garden', NOW(), NOW(), 1),

-- Books
(9, 'JavaScript: The Complete Guide', 'Comprehensive guide to modern JavaScript development.', 'JavaScript programming book', 'JCG-009', 4, 'TechBooks', 49.99, 69.99, 19.99, 39.99, 1.2, '6x9x1 inches', true, 150, 30, true, false, 'JavaScript Complete Guide Programming', 'Learn modern JavaScript development', 'book, programming, JavaScript, web development', NOW(), NOW(), 1),
(10, 'Fiction Bestseller Collection', 'Collection of 5 bestselling fiction novels.', 'Popular fiction novels set', 'FBC-010', 4, 'BookWorld', 39.99, 59.99, 15.99, 29.99, 2.8, '6x9x8 inches', true, 120, 25, true, true, 'Fiction Bestseller Collection 5 Books', 'Bestselling fiction novels collection', 'books, fiction, novels, bestsellers', NOW(), NOW(), 1),

-- Product Images
INSERT INTO product_images (id, product_id, image_url, alt_text, display_order, is_primary, created_at, updated_at, version) VALUES
(1, 1, 'https://example.com/images/headphones-1.jpg', 'Wireless headphones front view', 1, true, NOW(), NOW(), 1),
(2, 1, 'https://example.com/images/headphones-2.jpg', 'Wireless headphones side view', 2, false, NOW(), NOW(), 1),
(3, 2, 'https://example.com/images/smartwatch-1.jpg', 'Smart watch display', 1, true, NOW(), NOW(), 1),
(4, 2, 'https://example.com/images/smartwatch-2.jpg', 'Smart watch fitness tracking', 2, false, NOW(), NOW(), 1),
(5, 3, 'https://example.com/images/laptop-1.jpg', 'Laptop open showing screen', 1, true, NOW(), NOW(), 1),
(6, 3, 'https://example.com/images/laptop-2.jpg', 'Laptop keyboard and trackpad', 2, false, NOW(), NOW(), 1),
(7, 4, 'https://example.com/images/tshirt-1.jpg', 'Classic t-shirt front view', 1, true, NOW(), NOW(), 1),
(8, 4, 'https://example.com/images/tshirt-2.jpg', 'T-shirt fabric detail', 2, false, NOW(), NOW(), 1),
(9, 5, 'https://example.com/images/dress-1.jpg', 'Summer dress full view', 1, true, NOW(), NOW(), 1),
(10, 5, 'https://example.com/images/dress-2.jpg', 'Dress back view', 2, false, NOW(), NOW(), 1);

-- =====================================================
-- 3. ORDERS AND ORDER ITEMS
-- =====================================================

-- Orders with different statuses
INSERT INTO orders (id, order_number, user_id, customer_email, customer_first_name, customer_last_name, customer_phone, status, payment_status, payment_method, subtotal, tax_amount, shipping_amount, discount_amount, total, currency, shipping_address, billing_address, created_at, updated_at, version) VALUES
(1, 'ORD-2024-001', 3, 'john.doe@email.com', 'John', 'Doe', '+1-555-0103', 'DELIVERED', 'COMPLETED', 'CREDIT_CARD', 259.98, 25.99, 0.00, 0.00, 285.97, 'USD', '123 Main Street Apt 4B, New York, NY 10001', '123 Main Street Apt 4B, New York, NY 10001', '2024-01-15 10:30:00', NOW(), 1),
(2, 'ORD-2024-002', 4, 'jane.smith@email.com', 'Jane', 'Smith', '+1-555-0104', 'PROCESSING', 'PENDING', 'PAYPAL', 159.98, 15.99, 9.99, 0.00, 185.96, 'USD', '789 Fashion Boulevard, Los Angeles, CA 90210', '789 Fashion Boulevard, Los Angeles, CA 90210', '2024-02-20 14:15:00', NOW(), 1),
(3, 'ORD-2024-003', 5, 'mike.wilson@email.com', 'Mike', 'Wilson', '+1-555-0105', 'SHIPPED', 'COMPLETED', 'CREDIT_CARD', 1099.99, 109.99, 0.00, 0.00, 1209.98, 'USD', '321 Innovation Drive, Chicago, IL 60601', '321 Innovation Drive, Chicago, IL 60601', '2024-03-10 09:45:00', NOW(), 1),
(4, 'ORD-2024-004', 6, 'sarah.jones@email.com', 'Sarah', 'Jones', '+1-555-0106', 'CANCELLED', 'REFUNDED', 'CREDIT_CARD', 89.99, 8.99, 5.00, 0.00, 103.98, 'USD', '456 Pine Street Apt 7, Seattle, WA 98101', '456 Pine Street Apt 7, Seattle, WA 98101', '2024-04-05 16:20:00', NOW(), 1),
(5, 'ORD-2024-005', 7, 'david.brown@email.com', 'David', 'Brown', '+1-555-0107', 'PENDING', 'PENDING', NULL, 39.99, 3.99, 0.00, 0.00, 43.98, 'USD', '221 Commonwealth Ave, Boston, MA 02130', '221 Commonwealth Ave, Boston, MA 02130', '2024-04-25 11:30:00', NOW(), 1);

-- Order Items
INSERT INTO order_items (id, order_id, product_id, product_name, product_sku, product_description, quantity, unit_price, total_price, compare_price, discount_amount, tax_amount, shipping_amount, created_at, updated_at, version) VALUES
-- Order 1 items (John's delivered order)
(1, 1, 1, 'Wireless Bluetooth Headphones', 'WBH-001', 'Premium noise-cancelling wireless headphones...', 1, 299.99, 299.99, 399.99, 0.00, 25.99, 0.00, '2024-01-15 10:30:00', NOW(), 1),
(2, 1, 4, 'Men''s Classic T-Shirt', 'MTS-004', 'Comfortable 100% cotton t-shirt...', 1, 29.99, 29.99, 39.99, 0.00, 2.99, 0.00, '2024-01-15 10:30:00', NOW(), 1),

-- Order 2 items (Jane's processing order)
(3, 2, 5, 'Women''s Summer Dress', 'WSD-005', 'Elegant summer dress...', 1, 89.99, 89.99, 129.99, 0.00, 8.99, 5.00, '2024-02-20 14:15:00', NOW(), 1),
(4, 2, 7, 'Smart LED Bulb Set', 'SLB-007', 'Energy-efficient LED bulbs...', 1, 49.99, 49.99, 79.99, 0.00, 4.99, 0.00, '2024-02-20 14:15:00', NOW(), 1),

-- Order 3 items (Mike's shipped order)
(5, 3, 3, 'Laptop Ultra', 'LU-003', 'High-performance laptop...', 1, 1099.99, 1099.99, 1599.99, 0.00, 109.99, 0.00, '2024-03-10 09:45:00', NOW(), 1),

-- Order 4 items (Sarah's cancelled order)
(6, 4, 4, 'Denim Jacket', 'DJ-006', 'Classic denim jacket...', 1, 119.99, 119.99, 159.99, 0.00, 11.99, 5.00, '2024-04-05 16:20:00', NOW(), 1),

-- Order 5 items (David's pending order)
(7, 5, 9, 'JavaScript: The Complete Guide', 'JCG-009', 'Comprehensive guide to modern JavaScript...', 1, 39.99, 39.99, 69.99, 0.00, 3.99, 0.00, '2024-04-25 11:30:00', NOW(), 1);

-- =====================================================
-- 4. CARTS AND CART ITEMS
-- =====================================================

-- Active Carts
INSERT INTO carts (id, user_id, session_id, subtotal, tax_amount, shipping_amount, discount_amount, total, currency, item_count, created_at, updated_at, version) VALUES
(1, 3, NULL, 89.98, 8.99, 0.00, 0.00, 98.97, 'USD', 2, NOW(), NOW(), 1),
(2, 5, NULL, 39.99, 3.99, 0.00, 0.00, 43.98, 'USD', 1, NOW(), NOW(), 1),
(3, 6, NULL, 69.99, 6.99, 0.00, 0.00, 76.98, 'USD', 1, NOW(), NOW(), 1);

-- Cart Items
INSERT INTO cart_items (id, cart_id, product_id, product_name, product_sku, product_image, quantity, unit_price, total_price, compare_price, discount_amount, in_stock, created_at, updated_at, version) VALUES
-- John's cart (2 items)
(1, 1, 1, 'Wireless Bluetooth Headphones', 'WBH-001', 'https://example.com/images/headphones-1.jpg', 1, 299.99, 299.99, 399.99, 0.00, true, NOW(), NOW(), 1),
(2, 1, 4, 'Men''s Classic T-Shirt', 'MTS-004', 'https://example.com/images/tshirt-1.jpg', 1, 29.99, 299.99, 39.99, 0.00, true, NOW(), NOW(), 1),

-- Mike's cart (1 item)
(3, 2, 3, 'Laptop Ultra', 'LU-003', 'https://example.com/images/laptop-1.jpg', 1, 1099.99, 1099.99, 1599.99, 0.00, true, NOW(), NOW(), 1),

-- Sarah's cart (1 item)
(4, 3, 5, 'Women''s Summer Dress', 'WSD-005', 'https://example.com/images/dress-1.jpg', 1, 89.99, 89.99, 129.99, 0.00, true, NOW(), NOW(), 1);

-- =====================================================
-- 5. WISHLISTS AND WISHLIST ITEMS
-- =====================================================

-- Wishlists
INSERT INTO wishlists (id, user_id, name, description, is_default, is_private, item_count, total_value, notify_on_price_drop, notify_on_back_in_stock, created_at, updated_at, version) VALUES
(1, 3, 'My Tech Wishlist', 'Gadgets and electronics I want to buy', true, false, 3, 749.98, true, true, NOW(), NOW(), 1),
(2, 4, 'Fashion Favorites', 'Stylish clothing and accessories', true, false, 2, 209.97, true, true, NOW(), NOW(), 1),
(3, 5, 'Home Upgrade Ideas', 'Items for home improvement', false, false, 4, 189.97, false, true, NOW(), NOW(), 1),
(4, 6, 'Reading List', 'Books I want to read', true, true, 2, 89.98, false, true, NOW(), NOW(), 1);

-- Wishlist Items
INSERT INTO wishlist_items (id, wishlist_id, product_id, product_name, product_sku, product_image, quantity, unit_price, total_price, priority, is_available, created_at, updated_at, version) VALUES
-- John's tech wishlist
(1, 1, 1, 'Wireless Bluetooth Headphones', 'WBH-001', 'https://example.com/images/headphones-1.jpg', 1, 299.99, 299.99, 'HIGH', true, NOW(), NOW(), 1),
(2, 1, 2, 'Smart Watch Pro', 'SWP-002', 'https://example.com/images/smartwatch-1.jpg', 1, 449.99, 449.99, 'HIGH', true, NOW(), NOW(), 1),
(3, 1, 3, 'Laptop Ultra', 'LU-003', 'https://example.com/images/laptop-1.jpg', 1, 1299.99, 1299.99, 'HIGH', true, NOW(), NOW(), 1),

-- Jane's fashion wishlist
(4, 2, 4, 'Women''s Summer Dress', 'WSD-005', 'https://example.com/images/dress-1.jpg', 1, 89.99, 89.99, 'NORMAL', true, NOW(), NOW(), 1),
(5, 2, 6, 'Denim Jacket', 'DJ-006', 'https://example.com/images/dress-2.jpg', 1, 119.99, 119.99, 'NORMAL', true, NOW(), NOW(), 1),

-- =====================================================
-- 6. REVIEWS AND RATINGS
-- =====================================================

-- Reviews
INSERT INTO reviews (id, product_id, order_id, user_id, customer_name, customer_email, rating, title, content, is_verified, is_approved, is_featured, helpful_count, not_helpful_count, created_at, updated_at, version) VALUES
(1, 1, 1, 3, 'John Doe', 'john.doe@email.com', 5, 'Excellent headphones!', 'These headphones exceeded my expectations. The noise cancellation is incredible, battery life lasts for days, and sound quality is crystal clear. Highly recommend!', true, true, true, 12, 1, NOW(), NOW(), 1),
(2, 2, 2, 4, 'Jane Smith', 'jane.smith@email.com', 4, 'Beautiful dress', 'Love this dress! The fabric is high quality and the fit is perfect. Received many compliments when I wore it to a wedding.', true, true, false, 8, 0, NOW(), NOW(), 1),
(3, 3, 3, 5, 'Mike Wilson', 'mike.wilson@email.com', 5, 'Powerful laptop', 'Amazing performance for both gaming and work. The display is stunning and keyboard is comfortable. Fast shipping and great packaging.', true, true, true, 15, 2, NOW(), NOW(), 1),
(4, 5, NULL, 6, 'Sarah Jones', 'sarah.jones@email.com', 3, 'Good value', 'Nice jacket but the sizing runs small. I usually wear medium but needed large in this. Quality is good though.', true, true, false, 3, 1, NOW(), NOW(), 1),
(5, 7, NULL, 7, 'David Brown', 'david.brown@email.com', 4, 'Great learning resource', 'Very comprehensive JavaScript guide. Covers modern ES6+ features and best practices. Good for both beginners and experienced developers.', true, true, false, 6, 0, NOW(), NOW(), 1),
(6, 1, NULL, 3, 'Anonymous User', 'guest@email.com', 2, 'Disappointing experience', 'The dress looked beautiful online but the quality was poor. Fabric felt cheap and stitching came loose after one wash. Not worth the price.', true, false, false, 2, 3, NOW(), NOW(), 1);

-- Review Images
INSERT INTO review_images (id, review_id, image_url, alt_text, display_order, created_at, updated_at, version) VALUES
(1, 1, 'https://example.com/reviews/headphones-user-1.jpg', 'Headphones in use', 1, NOW(), NOW(), 1),
(2, 1, 'https://example.com/reviews/headphones-user-2.jpg', 'Headphones packaging', 2, NOW(), NOW(), 1),
(3, 2, 'https://example.com/reviews/dress-user-1.jpg', 'Dress being worn', 1, NOW(), NOW(), 1),
(4, 3, 'https://example.com/reviews/laptop-user-1.jpg', 'Laptop setup photo', 1, NOW(), NOW(), 1),
(5, 5, 'https://example.com/reviews/jacket-user-1.jpg', 'Jacket fit photo', 1, NOW(), NOW(), 1);

-- Review Helpful Votes
INSERT INTO review_helpful (review_id, user_id, is_helpful, ip_address, user_agent, created_at, updated_at, version) VALUES
(1, 3, true, '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36', NOW(), NOW(), 1),
(1, 4, true, '192.168.1.101', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36', NOW(), NOW(), 1),
(2, 4, true, '192.168.1.102', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36', NOW(), NOW(), 1),
(3, 5, true, '192.168.1.103', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36', NOW(), NOW(), 1),
(6, 7, true, '192.168.1.104', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36', NOW(), NOW(), 1),
(4, 6, false, '192.168.1.105', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36', NOW(), NOW(), 1);

-- =====================================================
-- 7. PAYMENTS AND REFUNDS
-- =====================================================

-- Payments
INSERT INTO payments (id, transaction_id, order_id, order_number, user_id, payment_method, payment_gateway, gateway_transaction_id, amount, currency, status, gateway_status, customer_email, customer_name, billing_address, ip_address, user_agent, processed_at, created_at, updated_at, version) VALUES
(1, 'TXN-2024-001', 1, 'ORD-2024-001', 3, 'CREDIT_CARD', 'STRIPE', 'ch_1A2B3C4D5E6F7', 285.97, 'USD', 'COMPLETED', 'succeeded', 'john.doe@email.com', 'John Doe', '123 Main Street Apt 4B, New York, NY 10001', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36', '2024-01-15 10:35:00', NOW(), NOW(), 1),
(2, 'TXN-2024-002', 2, 'ORD-2024-002', 4, 'PAYPAL', 'PAYPAL', 'PAYPAL-12345', 185.96, 'USD', 'COMPLETED', 'completed', 'jane.smith@email.com', 'Jane Smith', '789 Fashion Boulevard, Los Angeles, CA 90210', '192.168.1.101', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36', '2024-02-20 14:20:00', NOW(), NOW(), 1),
(3, 'TXN-2024-003', 3, 'ORD-2024-003', 5, 'CREDIT_CARD', 'STRIPE', 'ch_3H4I5J6K7L8M9N', 1209.98, 'USD', 'COMPLETED', 'succeeded', 'mike.wilson@email.com', 'Mike Wilson', '321 Innovation Drive, Chicago, IL 60601', '192.168.1.102', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36', '2024-03-10 09:50:00', NOW(), NOW(), 1),
(4, 'TXN-2024-004', 4, 'ORD-2024-004', 6, 'CREDIT_CARD', 'STRIPE', 'ch_5O6P7Q8R9S2T3U4', 103.98, 'USD', 'REFUNDED', 'failed', 'sarah.jones@email.com', 'Sarah Jones', '456 Pine Street Apt 7, Seattle, WA 98101', '192.168.1.103', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36', '2024-04-05 16:25:00', NOW(), NOW(), 1);

-- Refunds
INSERT INTO refunds (id, refund_id, payment_id, payment_transaction_id, order_id, order_number, user_id, amount, currency, reason, status, gateway_refund_id, processed_by, processed_by_id, processed_at, customer_notified, customer_notified_at, created_at, updated_at, version) VALUES
(1, 'REF-2024-001', 4, 'TXN-2024-004', 'ORD-2024-004', 6, 'ORD-2024-004', 6, 103.98, 'USD', 'Customer requested cancellation due to sizing issues', 'COMPLETED', 'rf_12345', 'admin@beej.com', 1, '2024-04-06 10:00:00', true, '2024-04-06 10:05:00', NOW(), NOW(), 1);

-- Payment Methods
INSERT INTO payment_methods (id, user_id, method_type, provider, method_identifier, card_last_four, card_brand, card_expiry_month, card_expiry_year, cardholder_name, gateway_customer_id, gateway_payment_method_id, is_default, is_active, billing_address, created_at, updated_at, version) VALUES
(1, 3, 'CREDIT_CARD', 'STRIPE', 'card_12345', '1234', 'Visa', '12', '2025', 'John Doe', 'cus_12345', 'pm_12345', true, true, '123 Main Street Apt 4B, New York, NY 10001', NOW(), NOW(), 1),
(2, 4, 'PAYPAL', 'PAYPAL', 'paypal_67890', NULL, NULL, NULL, NULL, NULL, 'paypal_67890', 'pp_67890', false, true, '789 Fashion Boulevard, Los Angeles, CA 90210', NOW(), NOW(), 1),
(3, 5, 'CREDIT_CARD', 'STRIPE', 'card_24680', '5678', 'Mastercard', '08', '2024', 'Mike Wilson', 'cus_24680', 'pm_24680', true, true, '321 Innovation Drive, Chicago, IL 60601', NOW(), NOW(), 1);

-- =====================================================
-- 8. NOTIFICATIONS AND USER ACTIVITIES
-- =====================================================

-- Notifications
INSERT INTO notifications (id, user_id, title, message, type, category, priority, is_read, is_email_sent, is_push_sent, is_sms_sent, action_url, action_text, icon_url, image_url, created_at, updated_at, version) VALUES
(1, 3, 'Order Delivered!', 'Your order ORD-2024-001 has been delivered successfully. Track your package and enjoy your purchase!', 'ORDER', 'ORDER_STATUS', 'NORMAL', false, true, true, false, '/api/orders/1', 'View Order', 'https://example.com/icons/delivery.png', 'https://example.com/images/package.png', NOW(), NOW(), 1),
(2, 4, 'Payment Processing', 'We are processing your payment for order ORD-2024-002. You will receive another notification once payment is confirmed.', 'PAYMENT', 'PAYMENT', 'HIGH', false, true, true, false, '/api/orders/2', 'View Order', 'https://example.com/icons/payment.png', 'https://example.com/images/payment-processing.jpg', NOW(), NOW(), 1),
(3, 5, 'Item Back in Stock!', 'The Laptop Ultra you wanted is now back in stock. Limited quantities available!', 'PRODUCT', 'INVENTORY', 'HIGH', false, true, true, false, '/api/products/3', 'View Product', 'https://example.com/icons/stock.png', 'https://example.com/images/laptop-back-in-stock.jpg', NOW(), NOW(), 1),
(4, 6, 'Special Offer!', 'Get 20% off on all electronics this weekend only!', 'PROMOTION', 'MARKETING', 'NORMAL', false, true, true, false, '/api/promotions/electronics', 'Shop Now', 'https://example.com/icons/sale.png', 'https://example.com/images/electronics-sale.jpg', NOW(), NOW(), 1),
(5, 7, 'Review Request', 'Please share your experience with recent purchases to help other customers.', 'REVIEW', 'ENGAGEMENT', 'LOW', false, false, false, false, '/api/reviews/pending', 'Write Reviews', 'https://example.com/icons/review.png', 'https://example.com/images/review-request.jpg', NOW(), NOW(), 1);

-- Notification Preferences
INSERT INTO notification_preferences (id, user_id, category, is_email_enabled, is_push_enabled, is_sms_enabled, is_in_app_enabled, frequency, quiet_hours_enabled, quiet_hours_start, quiet_hours_end, created_at, updated_at, version) VALUES
(1, 3, 'ORDER', true, true, false, true, 'IMMEDIATE', false, '22:00', '08:00', NOW(), NOW(), 1),
(2, 3, 'PROMOTION', true, true, false, true, 'DAILY', true, '09:00', '21:00', NOW(), NOW(), 1),
(3, 3, 'PRODUCT', true, true, false, true, 'WEEKLY', false, NULL, NULL, NOW(), NOW(), 1),
(4, 3, 'REVIEW', false, true, false, true, 'NEVER', false, NULL, NULL, NOW(), NOW(), 1),
(5, 4, 'ORDER', true, true, false, true, 'IMMEDIATE', false, '22:00', '08:00', NOW(), NOW(), 1),
(6, 4, 'PROMOTION', false, false, false, true, 'WEEKLY', false, NULL, NULL, NOW(), NOW(), 1);

-- =====================================================
-- 9. ADMIN DASHBOARD STATS
-- =====================================================

INSERT INTO admin_dashboard_stats (id, total_users, total_products, total_orders, total_revenue, active_users, pending_orders, processing_orders, shipped_orders, delivered_orders, cancelled_orders, low_stock_products, out_of_stock_products, recent_signups, top_selling_products, revenue_by_period, orders_by_status, created_at, updated_at, version) VALUES
(1, 7, 10, 5, 1625.89, 4, 1, 0, 1, 1, 1, 0, 2, 0, 'John, Jane, Mike, Sarah, David', 'Laptop Ultra, Wireless Bluetooth Headphones', '{"2024-01": 13500.50, "2024-02": 8900.25, "2024-03": 1209.98, "2024-04": 430.00}', '{"PENDING": 1, "PROCESSING": 1, "SHIPPED": 1, "DELIVERED": 1, "CANCELLED": 1}', NOW(), NOW(), 1);

-- =====================================================
-- SUMMARY OF TEST DATA
-- =====================================================
-- Users: 7 (2 admin, 5 customers)
-- Categories: 8
-- Products: 10
-- Orders: 5 (various statuses)
-- Order Items: 9
-- Carts: 3 active carts
-- Cart Items: 6
-- Wishlists: 4
-- Wishlist Items: 5
-- Reviews: 6
-- Review Images: 5
-- Review Helpful Votes: 7
-- Payments: 4
-- Refunds: 1
-- Payment Methods: 3
-- Notifications: 5
-- Notification Preferences: 6
-- Admin Dashboard Stats: 1

-- Total Records: ~75 comprehensive test records
