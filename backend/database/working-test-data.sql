-- WORKING TEST DATA MATCHING ACTUAL SCHEMA

-- Admin User
INSERT INTO user_profiles (id, user_id, username, email, first_name, last_name, display_name, phone_number, language, currency, newsletter_subscribed, marketing_emails_enabled, order_notifications_enabled, promotion_notifications_enabled, two_factor_enabled, login_count, account_status, created_at, updated_at, version) VALUES
(1, 1, 'admin', 'admin@beej.com', 'Admin', 'User', 'Admin User', '+1-555-0101', 'en', 'USD', true, true, true, true, false, 42, 'ACTIVE', NOW(), NOW(), 1);

-- Categories
INSERT INTO categories (id, name, description, parent_id, slug, created_at, updated_at, version) VALUES
(1, 'Electronics', 'Latest electronic gadgets and devices', NULL, 'electronics', NOW(), NOW(), 1);

-- Products
INSERT INTO products (id, name, description, short_description, sku, category_id, brand, price, compare_price, track_inventory, featured, status, created_at, updated_at, version) VALUES
(1, 'Laptop Pro 15"', 'High-performance laptop with 15" display, 16GB RAM, 512GB SSD', 'Premium laptop for professionals', 'LAPTOP-001', 1, 'TechBrand', 1299.99, 1499.99, 1, 1, 'ACTIVE', NOW(), NOW(), 1);
