-- =====================================================
-- BEEJ ECOMMERCE APPLICATION - SIMPLIFIED TEST DATA
-- =====================================================

-- 1. USERS AND USER PROFILES
-- =====================================================

-- Admin Users
INSERT INTO user_profiles (id, user_id, username, email, first_name, last_name, display_name, phone_number, language, currency, newsletter_subscribed, marketing_emails_enabled, order_notifications_enabled, promotion_notifications_enabled, two_factor_enabled, login_count, account_status, created_at, updated_at, version) VALUES
(1, 1, 'admin', 'admin@beej.com', 'Admin', 'User', 'Admin User', '+1-555-0101', 'en', 'USD', true, true, true, true, false, 42, 'ACTIVE', NOW(), NOW(), 1);

-- 2. CATEGORIES
-- =====================================================

-- Categories
INSERT INTO categories (id, name, description, parent_id, slug, created_at, updated_at, version) VALUES
(1, 'Electronics', 'Latest electronic gadgets and devices', NULL, 'electronics', NOW(), NOW(), 1),
(2, 'Clothing', 'Fashion and apparel for all seasons', NULL, 'clothing', NOW(), NOW(), 1),
(3, 'Home & Garden', 'Everything for your home and garden needs', NULL, 'home-garden', NOW(), NOW(), 1);

-- 3. PRODUCTS
-- =====================================================

-- Products
INSERT INTO products (id, name, description, short_description, sku, category_id, brand, price, compare_price, track_inventory, stock_quantity, is_active, is_featured, created_at, updated_at, version) VALUES
(1, 'Laptop Pro 15"', 'High-performance laptop with 15" display, 16GB RAM, 512GB SSD', 'Premium laptop for professionals', 'LAPTOP-001', 1, 'TechBrand', 1299.99, 1499.99, true, 50, true, true, NOW(), NOW(), 1),
(2, 'Wireless Headphones', 'Noise-cancelling wireless headphones with 30-hour battery life', 'Premium audio experience', 'HEADPHONES-001', 1, 'AudioBrand', 199.99, 249.99, true, 100, true, false, NOW(), NOW(), 1),
(3, 'Smart Watch', 'Fitness tracking smartwatch with heart rate monitor', 'Track your fitness goals', 'WATCH-001', 1, 'TechBrand', 299.99, 399.99, true, 75, true, false, NOW(), NOW(), 1);
