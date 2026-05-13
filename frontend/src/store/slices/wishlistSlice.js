import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'

// Mock wishlist service - replace with actual service when backend is ready
const wishlistService = {
  getWishlist: async () => {
    // Mock implementation
    return Promise.resolve([])
  },
  addToWishlist: async (productId) => {
    // Mock implementation
    return Promise.resolve({ productId })
  },
  removeFromWishlist: async (productId) => {
    // Mock implementation
    return Promise.resolve(productId)
  },
  moveToCart: async (productId) => {
    // Mock implementation
    return Promise.resolve(productId)
  }
}

// Async thunks
export const fetchWishlist = createAsyncThunk(
  'wishlist/fetchWishlist',
  async (_, { rejectWithValue }) => {
    try {
      const response = await wishlistService.getWishlist()
      return response
    } catch (error) {
      return rejectWithValue(error.response?.data?.message || 'Failed to fetch wishlist')
    }
  }
)

export const addToWishlist = createAsyncThunk(
  'wishlist/addToWishlist',
  async (productId, { rejectWithValue }) => {
    try {
      const response = await wishlistService.addToWishlist(productId)
      return { productId, ...response }
    } catch (error) {
      return rejectWithValue(error.response?.data?.message || 'Failed to add to wishlist')
    }
  }
)

export const removeFromWishlist = createAsyncThunk(
  'wishlist/removeFromWishlist',
  async (productId, { rejectWithValue }) => {
    try {
      await wishlistService.removeFromWishlist(productId)
      return productId
    } catch (error) {
      return rejectWithValue(error.response?.data?.message || 'Failed to remove from wishlist')
    }
  }
)

export const moveToCart = createAsyncThunk(
  'wishlist/moveToCart',
  async (productId, { rejectWithValue }) => {
    try {
      const response = await wishlistService.moveToCart(productId)
      return { productId, ...response }
    } catch (error) {
      return rejectWithValue(error.response?.data?.message || 'Failed to move to cart')
    }
  }
)

const initialState = {
  items: [],
  loading: false,
  error: null,
}

const wishlistSlice = createSlice({
  name: 'wishlist',
  initialState,
  reducers: {
    clearError: (state) => {
      state.error = null
    },
    toggleWishlistItem: (state, action) => {
      const productId = action.payload
      const exists = state.items.find(item => item.id === productId)
      if (exists) {
        state.items = state.items.filter(item => item.id !== productId)
      } else {
        state.items.push({ id: productId })
      }
    },
    isInWishlist: (state, productId) => {
      return state.items.some(item => item.id === productId)
    },
  },
  extraReducers: (builder) => {
    builder
      // Fetch Wishlist
      .addCase(fetchWishlist.pending, (state) => {
        state.loading = true
        state.error = null
      })
      .addCase(fetchWishlist.fulfilled, (state, action) => {
        state.loading = false
        state.items = action.payload
        state.error = null
      })
      .addCase(fetchWishlist.rejected, (state, action) => {
        state.loading = false
        state.error = action.payload
      })
      // Add to Wishlist
      .addCase(addToWishlist.pending, (state) => {
        state.loading = true
        state.error = null
      })
      .addCase(addToWishlist.fulfilled, (state, action) => {
        state.loading = false
        const { productId } = action.payload
        if (!state.items.find(item => item.id === productId)) {
          state.items.push({ id: productId })
        }
        state.error = null
      })
      .addCase(addToWishlist.rejected, (state, action) => {
        state.loading = false
        state.error = action.payload
      })
      // Remove from Wishlist
      .addCase(removeFromWishlist.pending, (state) => {
        state.loading = true
        state.error = null
      })
      .addCase(removeFromWishlist.fulfilled, (state, action) => {
        state.loading = false
        state.items = state.items.filter(item => item.id !== action.payload)
        state.error = null
      })
      .addCase(removeFromWishlist.rejected, (state, action) => {
        state.loading = false
        state.error = action.payload
      })
      // Move to Cart
      .addCase(moveToCart.pending, (state) => {
        state.loading = true
        state.error = null
      })
      .addCase(moveToCart.fulfilled, (state, action) => {
        state.loading = false
        const { productId } = action.payload
        state.items = state.items.filter(item => item.id !== productId)
        state.error = null
      })
      .addCase(moveToCart.rejected, (state, action) => {
        state.loading = false
        state.error = action.payload
      })
  },
})

export const { clearError, toggleWishlistItem, isInWishlist } = wishlistSlice.actions
export default wishlistSlice.reducer
