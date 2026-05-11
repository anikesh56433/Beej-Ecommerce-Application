import api from '../api/axios'

const cartService = {
  // Get user's cart
  getCart: async () => {
    const response = await api.get('/cart')
    return response.data.data
  },

  // Add item to cart
  addToCart: async (productId, quantity) => {
    const response = await api.post('/cart/add', { productId, quantity })
    return response.data.data
  },

  // Update cart item quantity
  updateCartItem: async (itemId, quantity) => {
    const response = await api.put(`/cart/items/${itemId}`, { quantity })
    return response.data.data
  },

  // Remove item from cart
  removeFromCart: async (itemId) => {
    const response = await api.delete(`/cart/items/${itemId}`)
    return response.data
  },

  // Clear entire cart
  clearCart: async () => {
    const response = await api.delete('/cart/clear')
    return response.data
  },

  // Get cart summary (total items, total price)
  getCartSummary: async () => {
    const response = await api.get('/cart/summary')
    return response.data.data
  },

  // Apply coupon code
  applyCoupon: async (couponCode) => {
    const response = await api.post('/cart/apply-coupon', { couponCode })
    return response.data.data
  },

  // Remove coupon code
  removeCoupon: async () => {
    const response = await api.delete('/cart/remove-coupon')
    return response.data.data
  },

  // Move item to wishlist
  moveToWishlist: async (itemId) => {
    const response = await api.post(`/cart/items/${itemId}/move-to-wishlist`)
    return response.data.data
  },
}

export default cartService
