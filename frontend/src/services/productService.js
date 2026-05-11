import api from '../api/axios'

const productService = {
  // Get all products with filters and pagination
  getProducts: async (params = {}) => {
    const response = await api.get('/products', { params })
    return response.data.data
  },

  // Get product by ID
  getProductById: async (id) => {
    const response = await api.get(`/products/${id}`)
    return response.data.data
  },

  // Get all categories
  getCategories: async () => {
    const response = await api.get('/categories')
    return response.data.data
  },

  // Get products by category
  getProductsByCategory: async (categoryId, params = {}) => {
    const response = await api.get(`/categories/${categoryId}/products`, { params })
    return response.data.data
  },

  // Search products
  searchProducts: async (query, params = {}) => {
    const response = await api.get('/products/search', { 
      params: { q: query, ...params } 
    })
    return response.data.data
  },

  // Get featured products
  getFeaturedProducts: async () => {
    const response = await api.get('/products/featured')
    return response.data.data
  },

  // Get trending products
  getTrendingProducts: async () => {
    const response = await api.get('/products/trending')
    return response.data.data
  },

  // Get new arrivals
  getNewArrivals: async () => {
    const response = await api.get('/products/new-arrivals')
    return response.data.data
  },

  // Get related products
  getRelatedProducts: async (productId) => {
    const response = await api.get(`/products/${productId}/related`)
    return response.data.data
  },

  // Get product reviews
  getProductReviews: async (productId, params = {}) => {
    const response = await api.get(`/products/${productId}/reviews`, { params })
    return response.data.data
  },

  // Add product review
  addProductReview: async (productId, reviewData) => {
    const response = await api.post(`/products/${productId}/reviews`, reviewData)
    return response.data.data
  },
}

export default productService
