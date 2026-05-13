import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import orderService from '../../services/orderService'

// Async thunks
export const createOrder = createAsyncThunk(
  'order/createOrder',
  async (orderData, { rejectWithValue }) => {
    try {
      const response = await orderService.createOrder(orderData)
      return response
    } catch (error) {
      return rejectWithValue(error.response?.data?.message || 'Failed to create order')
    }
  }
)

export const fetchUserOrders = createAsyncThunk(
  'order/fetchUserOrders',
  async (params = {}, { rejectWithValue }) => {
    try {
      const response = await orderService.getUserOrders(params)
      return response
    } catch (error) {
      return rejectWithValue(error.response?.data?.message || 'Failed to fetch orders')
    }
  }
)

export const fetchOrderById = createAsyncThunk(
  'order/fetchOrderById',
  async (orderId, { rejectWithValue }) => {
    try {
      const response = await orderService.getOrderById(orderId)
      return response
    } catch (error) {
      return rejectWithValue(error.response?.data?.message || 'Failed to fetch order')
    }
  }
)

export const cancelOrder = createAsyncThunk(
  'order/cancelOrder',
  async (orderId, { rejectWithValue }) => {
    try {
      await orderService.cancelOrder(orderId)
      return orderId
    } catch (error) {
      return rejectWithValue(error.response?.data?.message || 'Failed to cancel order')
    }
  }
)

export const fetchOrderTracking = createAsyncThunk(
  'order/fetchOrderTracking',
  async (orderId, { rejectWithValue }) => {
    try {
      const response = await orderService.getOrderTracking(orderId)
      return { orderId, tracking: response }
    } catch (error) {
      return rejectWithValue(error.response?.data?.message || 'Failed to fetch tracking')
    }
  }
)

// Export aliases for compatibility with existing imports
export const fetchOrders = fetchUserOrders
export const trackOrder = fetchOrderTracking

const initialState = {
  orders: [],
  currentOrder: null,
  orderTracking: {},
  loading: false,
  error: null,
  createOrderLoading: false,
  pagination: {
    page: 1,
    limit: 10,
    total: 0,
    totalPages: 0,
  },
}

const orderSlice = createSlice({
  name: 'order',
  initialState,
  reducers: {
    clearError: (state) => {
      state.error = null
    },
    clearCurrentOrder: (state) => {
      state.currentOrder = null
    },
    updateOrderStatus: (state, action) => {
      const { orderId, status } = action.payload
      const order = state.orders.find(o => o.id === orderId)
      if (order) {
        order.status = status
      }
      if (state.currentOrder && state.currentOrder.id === orderId) {
        state.currentOrder.status = status
      }
    },
  },
  extraReducers: (builder) => {
    builder
      // Create Order
      .addCase(createOrder.pending, (state) => {
        state.createOrderLoading = true
        state.error = null
      })
      .addCase(createOrder.fulfilled, (state, action) => {
        state.createOrderLoading = false
        state.currentOrder = action.payload
        state.orders.unshift(action.payload)
        state.error = null
      })
      .addCase(createOrder.rejected, (state, action) => {
        state.createOrderLoading = false
        state.error = action.payload
      })
      // Fetch User Orders
      .addCase(fetchUserOrders.pending, (state) => {
        state.loading = true
        state.error = null
      })
      .addCase(fetchUserOrders.fulfilled, (state, action) => {
        state.loading = false
        state.orders = action.payload.orders || action.payload
        state.pagination = action.payload.pagination || state.pagination
        state.error = null
      })
      .addCase(fetchUserOrders.rejected, (state, action) => {
        state.loading = false
        state.error = action.payload
      })
      // Fetch Order By ID
      .addCase(fetchOrderById.pending, (state) => {
        state.loading = true
        state.error = null
      })
      .addCase(fetchOrderById.fulfilled, (state, action) => {
        state.loading = false
        state.currentOrder = action.payload
        state.error = null
      })
      .addCase(fetchOrderById.rejected, (state, action) => {
        state.loading = false
        state.error = action.payload
      })
      // Cancel Order
      .addCase(cancelOrder.pending, (state) => {
        state.loading = true
        state.error = null
      })
      .addCase(cancelOrder.fulfilled, (state, action) => {
        state.loading = false
        const orderId = action.payload
        state.orders = state.orders.filter(order => order.id !== orderId)
        if (state.currentOrder && state.currentOrder.id === orderId) {
          state.currentOrder = null
        }
        state.error = null
      })
      .addCase(cancelOrder.rejected, (state, action) => {
        state.loading = false
        state.error = action.payload
      })
      // Fetch Order Tracking
      .addCase(fetchOrderTracking.pending, (state) => {
        state.loading = true
        state.error = null
      })
      .addCase(fetchOrderTracking.fulfilled, (state, action) => {
        state.loading = false
        const { orderId, tracking } = action.payload
        state.orderTracking[orderId] = tracking
        state.error = null
      })
      .addCase(fetchOrderTracking.rejected, (state, action) => {
        state.loading = false
        state.error = action.payload
      })
  },
})

export const { clearError, clearCurrentOrder, updateOrderStatus } = orderSlice.actions
export default orderSlice.reducer
