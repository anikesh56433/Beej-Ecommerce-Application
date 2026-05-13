import { configureStore } from '@reduxjs/toolkit'
import authSlice from './slices/authSlice'
import cartSlice from './slices/cartSlice'
import productSlice from './slices/productSlice'
import orderSlice from './slices/orderSlice'
import wishlistSlice from './slices/wishlistSlice'
import uiSlice from './slices/uiSlice'

export const store = configureStore({
  reducer: {
    auth: authSlice,
    cart: cartSlice,
    product: productSlice,
    order: orderSlice,
    wishlist: wishlistSlice,
    ui: uiSlice,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: {
        ignoredActions: ['persist/PERSIST', 'persist/REHYDRATE'],
      },
    }),
})

export default store
