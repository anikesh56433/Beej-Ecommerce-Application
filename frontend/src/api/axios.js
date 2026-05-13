import axios from 'axios'
import { toast } from 'react-toastify'

// Create axios instance
const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8082/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
})

// Request interceptor
api.interceptors.request.use(
  (config) => {
    // Add auth token to headers
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// Response interceptor
api.interceptors.response.use(
  (response) => {
    return response
  },
  (error) => {
    // Handle common error scenarios
    if (error.response) {
      const { status, data } = error.response

      switch (status) {
        case 401:
          // Unauthorized - Clear token and redirect to login
          localStorage.removeItem('token')
          window.location.href = '/login'
          toast.error('Session expired. Please login again.')
          break
        case 403:
          // Forbidden
          toast.error('You do not have permission to perform this action.')
          break
        case 404:
          // Not Found
          toast.error('Resource not found.')
          break
        case 422:
          // Validation Error
          if (data.details && Array.isArray(data.details)) {
            data.details.forEach(detail => {
              toast.error(detail)
            })
          } else {
            toast.error(data.message || 'Validation failed.')
          }
          break
        case 500:
          // Server Error
          toast.error('Server error. Please try again later.')
          break
        default:
          // Other errors
          toast.error(data.message || 'An error occurred.')
      }
    } else if (error.request) {
      // Network error
      toast.error('Network error. Please check your connection.')
    } else {
      // Other errors
      toast.error('An unexpected error occurred.')
    }

    return Promise.reject(error)
  }
)

export default api
