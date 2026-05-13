import { Routes, Route } from 'react-router-dom'
import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'
import { useSelector } from 'react-redux'
import { useEffect } from 'react'

// Layout Components
import Layout from './components/layout/Layout'
import PublicLayout from './components/layout/PublicLayout'

// Pages
import HomePage from './pages/HomePage'
import ProductsPage from './pages/ProductsPage'
import CartPage from './pages/CartPage'
import AboutPage from './pages/AboutPage'
import ContactPage from './pages/ContactPage'
import BlogPage from './pages/BlogPage'

// Authentication Pages
import LoginPage from './pages/auth/LoginPage'
import RegisterPage from './pages/auth/RegisterPage'
import ForgotPasswordPage from './pages/auth/ForgotPasswordPage'
import OTPVerificationPage from './pages/auth/OTPVerificationPage'

// Product Pages
import ProductDetailPage from './pages/ProductDetailPage'
import CategoryPage from './pages/CategoryPage'

// User Pages
import CheckoutPage from './pages/CheckoutPage'
import OrdersPage from './pages/OrdersPage'
import UserDashboardPage from './pages/UserDashboardPage'
import WishlistPage from './pages/WishlistPage'

// Admin Pages
import AdminDashboard from './pages/admin/AdminDashboard'

// Protected Route Component
import ProtectedRoute from './components/common/ProtectedRoute'

// UI Components
import ScrollToTop from './components/ui/ScrollToTop'
import WhatsAppButton from './components/ui/WhatsAppButton'
import NewsletterPopup from './components/ui/NewsletterPopup'

function App() {
  const { isAuthenticated } = useSelector((state) => state.auth)

  return (
    <div className="App">
      <Routes>
        {/* Public Routes */}
        <Route path="/" element={<PublicLayout />}>
          <Route index element={<HomePage />} />
          <Route path="products" element={<ProductsPage />} />
          <Route path="products/:id" element={<ProductDetailPage />} />
          <Route path="categories/:slug" element={<CategoryPage />} />
          <Route path="about" element={<AboutPage />} />
          <Route path="contact" element={<ContactPage />} />
          <Route path="blog" element={<BlogPage />} />
          
          {/* Authentication Routes */}
          <Route path="login" element={<LoginPage />} />
          <Route path="register" element={<RegisterPage />} />
          <Route path="forgot-password" element={<ForgotPasswordPage />} />
          <Route path="verify-otp" element={<OTPVerificationPage />} />
        </Route>

        {/* Protected Routes */}
        <Route path="/" element={<Layout />}>
          <Route element={<ProtectedRoute />}>
            <Route path="cart" element={<CartPage />} />
            <Route path="checkout" element={<CheckoutPage />} />
            <Route path="profile" element={<UserDashboardPage />} />
            <Route path="orders" element={<OrdersPage />} />
            <Route path="wishlist" element={<WishlistPage />} />
          </Route>
          
          {/* Admin Routes */}
          <Route element={<ProtectedRoute requiredRole="ADMIN" />}>
            <Route path="admin" element={<AdminDashboard />} />
            <Route path="admin/*" element={<AdminDashboard />} />
          </Route>
        </Route>

        {/* Fallback Route */}
        <Route path="*" element={
          <div className="min-h-screen flex items-center justify-center bg-beej-beige">
            <div className="text-center">
              <h1 className="text-4xl font-bold text-beej-green mb-4">404</h1>
              <p className="text-beej-brown mb-8">Page not found</p>
              <button 
                onClick={() => window.history.back()}
                className="bg-beej-green text-white px-6 py-2 rounded-full hover:bg-beej-green-dark transition-colors"
              >
                Go Back
              </button>
            </div>
          </div>
        } />
      </Routes>

      {/* Global UI Components */}
      <ScrollToTop />
      <WhatsAppButton />
      <NewsletterPopup />

      <ToastContainer
        position="top-right"
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="light"
        className="toast-container"
        toastClassName="bg-white border border-beej-green/20 text-beej-brown shadow-lg"
        progressClassName="bg-beej-green"
      />
    </div>
  )
}

export default App
