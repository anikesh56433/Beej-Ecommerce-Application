import { createSlice } from '@reduxjs/toolkit'

const initialState = {
  // Loading states
  globalLoading: false,
  pageLoading: false,
  
  // Modal states
  cartModal: false,
  searchModal: false,
  mobileMenu: false,
  newsletterModal: false,
  
  // Toast notifications
  notifications: [],
  
  // Theme
  darkMode: false,
  
  // UI preferences
  sidebarOpen: false,
  scrollToTopVisible: false,
  
  // Search
  searchQuery: '',
  searchResults: [],
  searchLoading: false,
  
  // Newsletter
  newsletterSubscribed: false,
  showNewsletterPopup: false,
  
  // Promotional banners
  showPromoBanner: true,
  currentPromo: null,
}

const uiSlice = createSlice({
  name: 'ui',
  initialState,
  reducers: {
    // Loading states
    setGlobalLoading: (state, action) => {
      state.globalLoading = action.payload
    },
    setPageLoading: (state, action) => {
      state.pageLoading = action.payload
    },
    
    // Modal controls
    toggleCartModal: (state) => {
      state.cartModal = !state.cartModal
    },
    toggleSearchModal: (state) => {
      state.searchModal = !state.searchModal
    },
    toggleMobileMenu: (state) => {
      state.mobileMenu = !state.mobileMenu
    },
    toggleNewsletterModal: (state) => {
      state.newsletterModal = !state.newsletterModal
    },
    closeAllModals: (state) => {
      state.cartModal = false
      state.searchModal = false
      state.mobileMenu = false
      state.newsletterModal = false
    },
    
    // Notifications
    addNotification: (state, action) => {
      const notification = {
        id: Date.now(),
        type: 'info',
        duration: 5000,
        ...action.payload,
      }
      state.notifications.push(notification)
    },
    removeNotification: (state, action) => {
      state.notifications = state.notifications.filter(
        notification => notification.id !== action.payload
      )
    },
    clearNotifications: (state) => {
      state.notifications = []
    },
    
    // Theme
    toggleDarkMode: (state) => {
      state.darkMode = !state.darkMode
      // Save to localStorage
      localStorage.setItem('darkMode', state.darkMode)
    },
    setDarkMode: (state, action) => {
      state.darkMode = action.payload
      localStorage.setItem('darkMode', state.darkMode)
    },
    
    // UI preferences
    toggleSidebar: (state) => {
      state.sidebarOpen = !state.sidebarOpen
    },
    setScrollToTopVisible: (state, action) => {
      state.scrollToTopVisible = action.payload
    },
    
    // Search
    setSearchQuery: (state, action) => {
      state.searchQuery = action.payload
    },
    setSearchResults: (state, action) => {
      state.searchResults = action.payload
    },
    setSearchLoading: (state, action) => {
      state.searchLoading = action.payload
    },
    clearSearch: (state) => {
      state.searchQuery = ''
      state.searchResults = []
      state.searchLoading = false
    },
    
    // Newsletter
    setNewsletterSubscribed: (state, action) => {
      state.newsletterSubscribed = action.payload
      localStorage.setItem('newsletterSubscribed', state.newsletterSubscribed)
    },
    setShowNewsletterPopup: (state, action) => {
      state.showNewsletterPopup = action.payload
    },
    
    // Promotional banners
    setShowPromoBanner: (state, action) => {
      state.showPromoBanner = action.payload
    },
    setCurrentPromo: (state, action) => {
      state.currentPromo = action.payload
    },
  },
})

// Selectors
export const selectNotifications = (state) => state.ui.notifications
export const selectCartModalOpen = (state) => state.ui.cartModal
export const selectSearchModalOpen = (state) => state.ui.searchModal
export const selectMobileMenuOpen = (state) => state.ui.mobileMenu
export const selectDarkMode = (state) => state.ui.darkMode
export const selectGlobalLoading = (state) => state.ui.globalLoading
export const selectPageLoading = (state) => state.ui.pageLoading
export const selectScrollToTopVisible = (state) => state.ui.scrollToTopVisible
export const selectSearchQuery = (state) => state.ui.searchQuery
export const selectSearchResults = (state) => state.ui.searchResults
export const selectSearchLoading = (state) => state.ui.searchLoading
export const selectNewsletterSubscribed = (state) => state.ui.newsletterSubscribed
export const selectShowNewsletterPopup = (state) => state.ui.showNewsletterPopup

export const {
  setGlobalLoading,
  setPageLoading,
  toggleCartModal,
  toggleSearchModal,
  toggleMobileMenu,
  toggleNewsletterModal,
  closeAllModals,
  addNotification,
  removeNotification,
  clearNotifications,
  toggleDarkMode,
  setDarkMode,
  toggleSidebar,
  setScrollToTopVisible,
  setSearchQuery,
  setSearchResults,
  setSearchLoading,
  clearSearch,
  setNewsletterSubscribed,
  setShowNewsletterPopup,
  setShowPromoBanner,
  setCurrentPromo,
} = uiSlice.actions

export default uiSlice.reducer
