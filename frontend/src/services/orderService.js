import api from '../api/axios'

const orderService = {
  // Create new order
  createOrder: async (orderData) => {
    const response = await api.post('/orders', orderData)
    return response.data.data
  },

  // Get user's orders
  getUserOrders: async (params = {}) => {
    const response = await api.get('/orders', { params })
    return response.data.data
  },

  // Get order by ID
  getOrderById: async (orderId) => {
    const response = await api.get(`/orders/${orderId}`)
    return response.data.data
  },

  // Update order status
  updateOrderStatus: async (orderId, status) => {
    const response = await api.patch(`/orders/${orderId}/status`, { status })
    return response.data.data
  },

  // Cancel order
  cancelOrder: async (orderId) => {
    const response = await api.post(`/orders/${orderId}/cancel`)
    return response.data.data
  },

  // Get order tracking information
  getOrderTracking: async (orderId) => {
    const response = await api.get(`/orders/${orderId}/tracking`)
    return response.data.data
  },

  // Request return/refund
  requestReturn: async (orderId, returnData) => {
    const response = await api.post(`/orders/${orderId}/return`, returnData)
    return response.data.data
  },

  // Get order history with filters
  getOrderHistory: async (filters = {}) => {
    const response = await api.get('/orders/history', { params: filters })
    return response.data.data
  },

  // Reorder previous order
  reorderItems: async (orderId) => {
    const response = await api.post(`/orders/${orderId}/reorder`)
    return response.data.data
  },

  // Download invoice
  downloadInvoice: async (orderId) => {
    const response = await api.get(`/orders/${orderId}/invoice`, {
      responseType: 'blob'
    })
    return response.data
  },

  // Apply discount code to order
  applyOrderDiscount: async (orderId, discountCode) => {
    const response = await api.post(`/orders/${orderId}/discount`, { discountCode })
    return response.data.data
  },

  // Get order statistics for user dashboard
  getOrderStats: async () => {
    const response = await api.get('/orders/stats')
    return response.data.data
  }
}

export default orderService
