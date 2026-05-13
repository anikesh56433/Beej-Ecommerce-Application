import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import productService from '../../services/productService'

// Async thunks
export const fetchProducts = createAsyncThunk(
  'product/fetchProducts',
  async (params = {}, { rejectWithValue }) => {
    try {
      const response = await productService.getProducts(params)
      return response
    } catch (error) {
      return rejectWithValue(error.response?.data?.message || 'Failed to fetch products')
    }
  }
)

export const fetchProductById = createAsyncThunk(
  'product/fetchProductById',
  async (id, { rejectWithValue }) => {
    try {
      const response = await productService.getProductById(id)
      return response
    } catch (error) {
      return rejectWithValue(error.response?.data?.message || 'Failed to fetch product')
    }
  }
)

export const fetchCategories = createAsyncThunk(
  'product/fetchCategories',
  async (_, { rejectWithValue }) => {
    try {
      const response = await productService.getCategories()
      return response
    } catch (error) {
      return rejectWithValue(error.response?.data?.message || 'Failed to fetch categories')
    }
  }
)

export const fetchFeaturedProducts = createAsyncThunk(
  'product/fetchFeaturedProducts',
  async (_, { rejectWithValue }) => {
    try {
      const response = await productService.getProducts({ featured: true, limit: 4 })
      return response
    } catch (error) {
      return rejectWithValue(error.response?.data?.message || 'Failed to fetch featured products')
    }
  }
)

export const searchProducts = createAsyncThunk(
  'product/searchProducts',
  async (query, { rejectWithValue }) => {
    try {
      const response = await productService.searchProducts(query)
      return response
    } catch (error) {
      return rejectWithValue(error.response?.data?.message || 'Failed to search products')
    }
  }
)

export const fetchTrendingProducts = createAsyncThunk(
  'product/fetchTrendingProducts',
  async (_, { rejectWithValue }) => {
    try {
      const response = await productService.getProducts({ trending: true, limit: 8 })
      return response
    } catch (error) {
      return rejectWithValue(error.response?.data?.message || 'Failed to fetch trending products')
    }
  }
)

export const fetchProductsByCategory = createAsyncThunk(
  'product/fetchProductsByCategory',
  async (categorySlug, { rejectWithValue }) => {
    try {
      const response = await productService.getProductsByCategory(categorySlug)
      return response
    } catch (error) {
      return rejectWithValue(error.response?.data?.message || 'Failed to fetch products by category')
    }
  }
)

const initialState = {
  products: [],
  currentProduct: null,
  categories: [],
  featuredProducts: [],
  trendingProducts: [],
  loading: false,
  error: null,
  pagination: {
    page: 1,
    limit: 20,
    total: 0,
    totalPages: 0,
  },
  filters: {
    category: '',
    priceRange: [0, 10000],
    rating: 0,
    sortBy: 'createdAt',
    sortOrder: 'desc',
  },
}

const productSlice = createSlice({
  name: 'product',
  initialState,
  reducers: {
    clearError: (state) => {
      state.error = null
    },
    setCurrentProduct: (state, action) => {
      state.currentProduct = action.payload
    },
    setFilters: (state, action) => {
      state.filters = { ...state.filters, ...action.payload }
    },
    clearFilters: (state) => {
      state.filters = initialState.filters
    },
    setPagination: (state, action) => {
      state.pagination = { ...state.pagination, ...action.payload }
    },
  },
  extraReducers: (builder) => {
    builder
      // Fetch Products
      .addCase(fetchProducts.pending, (state) => {
        state.loading = true
        state.error = null
      })
      .addCase(fetchProducts.fulfilled, (state, action) => {
        state.loading = false
        state.products = action.payload.products || []
        state.pagination = {
          page: action.payload.page || 1,
          limit: action.payload.limit || 20,
          total: action.payload.total || 0,
          totalPages: action.payload.totalPages || 0,
        }
      })
      .addCase(fetchProducts.rejected, (state, action) => {
        state.loading = false
        state.error = action.payload
        state.products = []
      })
      // Fetch Product by ID
      .addCase(fetchProductById.pending, (state) => {
        state.loading = true
        state.error = null
      })
      .addCase(fetchProductById.fulfilled, (state, action) => {
        state.loading = false
        state.currentProduct = action.payload
      })
      .addCase(fetchProductById.rejected, (state, action) => {
        state.loading = false
        state.error = action.payload
        state.currentProduct = null
      })
      // Fetch Categories
      .addCase(fetchCategories.pending, (state) => {
        state.loading = true
        state.error = null
      })
      .addCase(fetchCategories.fulfilled, (state, action) => {
        state.loading = false
        state.categories = action.payload || []
      })
      .addCase(fetchCategories.rejected, (state, action) => {
        state.loading = false
        state.error = action.payload
        state.categories = []
      })
      // Fetch Featured Products
      .addCase(fetchFeaturedProducts.pending, (state) => {
        state.loading = true
      })
      .addCase(fetchFeaturedProducts.fulfilled, (state, action) => {
        state.loading = false
        state.featuredProducts = action.payload.products || []
      })
      .addCase(fetchFeaturedProducts.rejected, (state) => {
        state.loading = false
        state.featuredProducts = []
      })
      // Search Products
      .addCase(searchProducts.pending, (state) => {
        state.loading = true
        state.error = null
      })
      .addCase(searchProducts.fulfilled, (state, action) => {
        state.loading = false
        state.products = action.payload.products || []
        state.pagination = {
          page: action.payload.page || 1,
          limit: action.payload.limit || 20,
          total: action.payload.total || 0,
          totalPages: action.payload.totalPages || 0,
        }
      })
      .addCase(searchProducts.rejected, (state, action) => {
        state.loading = false
        state.error = action.payload
        state.products = []
      })
      // Fetch Trending Products
      .addCase(fetchTrendingProducts.pending, (state) => {
        state.loading = true
      })
      .addCase(fetchTrendingProducts.fulfilled, (state, action) => {
        state.loading = false
        state.trendingProducts = action.payload.products || []
      })
      .addCase(fetchTrendingProducts.rejected, (state, action) => {
        state.loading = false
        state.trendingProducts = []
      })
      // Fetch Products by Category
      .addCase(fetchProductsByCategory.pending, (state) => {
        state.loading = true
        state.error = null
      })
      .addCase(fetchProductsByCategory.fulfilled, (state, action) => {
        state.loading = false
        state.products = action.payload.products || []
      })
      .addCase(fetchProductsByCategory.rejected, (state, action) => {
        state.loading = false
        state.error = action.payload
        state.products = []
      })
  },
})

export const { 
  clearError, 
  setCurrentProduct, 
  setFilters, 
  clearFilters, 
  setPagination 
} = productSlice.actions

export default productSlice.reducer
