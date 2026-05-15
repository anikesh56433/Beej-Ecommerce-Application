import React, { useState, useEffect } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useSelector, useDispatch } from 'react-redux'
import { logout } from '../../store/slices/authSlice'
import { toggleMobileMenu, toggleSearchModal, selectMobileMenuOpen } from '../../store/slices/uiSlice'
import { ShoppingCart, User, Search, Menu, X, Heart } from 'lucide-react'
import { motion, AnimatePresence } from 'framer-motion'
import ThemeToggle from '../ui/ThemeToggle'

const Header = () => {
  const dispatch = useDispatch()
  const navigate = useNavigate()
  const { isAuthenticated, user } = useSelector((state) => state.auth)
  const { totalItems } = useSelector((state) => state.cart)
  const mobileMenuOpen = useSelector(selectMobileMenuOpen)
  const [searchQuery, setSearchQuery] = useState('')
  const [isScrolled, setIsScrolled] = useState(false)

  useEffect(() => {
    const handleScroll = () => {
      setIsScrolled(window.scrollY > 20)
    }
    window.addEventListener('scroll', handleScroll)
    return () => window.removeEventListener('scroll', handleScroll)
  }, [])

  const handleLogout = () => {
    dispatch(logout())
    navigate('/')
  }

  const handleSearch = (e) => {
    e.preventDefault()
    if (searchQuery.trim()) {
      navigate(`/products?q=${encodeURIComponent(searchQuery.trim())}`)
      setSearchQuery('')
      dispatch(toggleMobileMenu())
    }
  }

  const handleMobileMenuToggle = () => {
    dispatch(toggleMobileMenu())
  }

  const handleSearchModalOpen = () => {
    dispatch(toggleSearchModal())
  }

  return (
    <motion.header 
      className={`bg-white dark:bg-slate-900 backdrop-blur-md shadow-sm sticky top-0 z-50 transition-all duration-300 ${isScrolled ? 'shadow-lg' : 'shadow-sm'}`}
      initial={{ y: -100 }}
      animate={{ y: 0 }}
      transition={{ duration: 0.3 }}
    >
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-16">
          {/* Logo */}
          <Link to="/" className="flex items-center group">
            <motion.div
              whileHover={{ scale: 1.05 }}
              transition={{ duration: 0.2 }}
              className="flex items-center"
            >
              <span className="text-3xl font-bold bg-gradient-to-r from-beej-green to-beej-green-light bg-clip-text text-transparent">
                BEEJ
              </span>
              <span className="ml-2 text-xs text-beej-brown dark:text-beej-beige font-medium tracking-wider">
                Root. Rise. Refine.
              </span>
            </motion.div>
          </Link>

          {/* Search Bar - Desktop */}
          <form onSubmit={handleSearch} className="hidden md:flex flex-1 max-w-lg mx-8">
            <div className="relative w-full group">
              <input
                type="text"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                placeholder="Search for healthy seeds..."
                className="w-full pl-10 pr-4 py-2.5 border border-beej-green/30 rounded-full focus:outline-none focus:ring-2 focus:ring-beej-green focus:border-beej-green transition-all duration-200 bg-beej-beige/20 dark:bg-slate-800 focus:bg-white dark:focus:bg-slate-700 text-beej-brown dark:text-beej-beige"
              />
              <Search className="absolute left-3 top-2.5 h-5 w-5 text-beej-brown/50 dark:text-beej-beige/50 group-focus-within:text-beej-green transition-colors duration-200" />
            </div>
          </form>

          {/* Navigation - Desktop */}
          <nav className="hidden md:flex items-center space-x-4">
            {/* Theme Toggle */}
            <ThemeToggle />

            {/* Navigation Links */}
            <Link to="/products" className="text-beej-brown dark:text-beej-beige hover:text-beej-green dark:hover:text-beej-green font-medium transition-colors duration-200">
              Products
            </Link>
            <Link to="/categories" className="text-beej-brown dark:text-beej-beige hover:text-beej-green dark:hover:text-beej-green font-medium transition-colors duration-200">
              Categories
            </Link>
            <Link to="/about" className="text-beej-brown dark:text-beej-beige hover:text-beej-green dark:hover:text-beej-green font-medium transition-colors duration-200">
              About
            </Link>
            <Link to="/blog" className="text-beej-brown dark:text-beej-beige hover:text-beej-green dark:hover:text-beej-green font-medium transition-colors duration-200">
              Blog
            </Link>

            {isAuthenticated ? (
              <>
                {/* Wishlist */}
                <motion.button
                  whileHover={{ scale: 1.1 }}
                  whileTap={{ scale: 0.9 }}
                  className="p-2 rounded-full hover:bg-beej-green/10 dark:hover:bg-beej-green/20 transition-colors duration-200"
                >
                  <Heart className="h-5 w-5 text-beej-brown dark:text-beej-beige hover:text-red-500" />
                </motion.button>

                {/* Cart */}
                <motion.div whileHover={{ scale: 1.05 }} whileTap={{ scale: 0.95 }}>
                  <Link to="/cart" className="relative p-2 rounded-full hover:bg-beej-green/10 dark:hover:bg-beej-green/20 transition-colors duration-200">
                    <ShoppingCart className="h-5 w-5 text-beej-brown dark:text-beej-beige" />
                    {totalItems > 0 && (
                      <motion.span
                        initial={{ scale: 0 }}
                        animate={{ scale: 1 }}
                        className="absolute -top-1 -right-1 bg-red-500 text-white text-xs rounded-full h-5 w-5 flex items-center justify-center font-bold"
                      >
                        {totalItems}
                      </motion.span>
                    )}
                  </Link>
                </motion.div>

                {/* User Dropdown */}
                <div className="relative group">
                  <motion.button
                    whileHover={{ scale: 1.05 }}
                    className="flex items-center space-x-2 p-2 rounded-full hover:bg-beej-green/10 dark:hover:bg-beej-green/20 transition-colors duration-200"
                  >
                    <User className="h-5 w-5 text-beej-brown dark:text-beej-beige" />
                    <span className="text-sm font-medium text-beej-brown dark:text-beej-beige">
                      {user?.firstName || 'Account'}
                    </span>
                  </motion.button>
                  <motion.div
                    initial={{ opacity: 0, y: -10 }}
                    whileInView={{ opacity: 1, y: 0 }}
                    className="absolute right-0 mt-2 w-56 bg-white dark:bg-slate-800 rounded-xl shadow-xl border border-beej-green/20 py-2 opacity-0 invisible group-hover:opacity-100 group-hover:visible transition-all duration-200"
                  >
                    <Link
                      to="/profile"
                      className="block px-4 py-2 text-beej-brown dark:text-beej-beige hover:bg-beej-green/10 dark:hover:bg-beej-green/20 transition-colors duration-200"
                    >
                      Profile
                    </Link>
                    <Link
                      to="/orders"
                      className="block px-4 py-2 text-beej-brown dark:text-beej-beige hover:bg-beej-green/10 dark:hover:bg-beej-green/20 transition-colors duration-200"
                    >
                      Orders
                    </Link>
                    <Link
                      to="/wishlist"
                      className="block px-4 py-2 text-beej-brown dark:text-beej-beige hover:bg-beej-green/10 dark:hover:bg-beej-green/20 transition-colors duration-200"
                    >
                      Wishlist
                    </Link>
                    {user?.role === 'ADMIN' && (
                      <Link
                        to="/admin"
                        className="block px-4 py-2 text-beej-brown dark:text-beej-beige hover:bg-beej-green/10 dark:hover:bg-beej-green/20 transition-colors duration-200"
                      >
                        Admin Dashboard
                      </Link>
                    )}
                    <button
                      onClick={handleLogout}
                      className="block w-full text-left px-4 py-2 text-red-500 hover:bg-red-50 dark:hover:bg-red-900/20 transition-colors duration-200"
                    >
                      Logout
                    </button>
                  </motion.div>
                </div>
              </>
            ) : (
              <>
                <Link to="/login" className="text-beej-brown dark:text-beej-beige hover:text-beej-green dark:hover:text-beej-green font-medium transition-colors duration-200">
                  Login
                </Link>
                <motion.div whileHover={{ scale: 1.05 }} whileTap={{ scale: 0.95 }}>
                  <Link
                    to="/register"
                    className="bg-beej-green text-white px-6 py-2 rounded-full hover:bg-beej-green-dark transition-all duration-200 shadow-md hover:shadow-lg"
                  >
                    Sign Up
                  </Link>
                </motion.div>
              </>
            )}
          </nav>

          {/* Mobile Menu Button */}
          <motion.button
            whileHover={{ scale: 1.1 }}
            whileTap={{ scale: 0.9 }}
            onClick={handleMobileMenuToggle}
            className="md:hidden p-2 rounded-full hover:bg-beej-green/10 dark:hover:bg-beej-green/20 transition-colors duration-200"
          >
            {mobileMenuOpen ? (
              <X className="h-6 w-6 text-beej-brown dark:text-beej-beige" />
            ) : (
              <Menu className="h-6 w-6 text-beej-brown dark:text-beej-beige" />
            )}
          </motion.button>
        </div>

        {/* Mobile Menu */}
        <AnimatePresence>
          {mobileMenuOpen && (
            <motion.div
              initial={{ height: 0, opacity: 0 }}
              animate={{ height: 'auto', opacity: 1 }}
              exit={{ height: 0, opacity: 0 }}
              transition={{ duration: 0.3 }}
              className="md:hidden border-t border-beej-green/20 overflow-hidden"
            >
              <div className="py-4 space-y-4">
                {/* Mobile Theme Toggle */}
                <div className="px-4 flex items-center justify-between">
                  <span className="text-beej-brown dark:text-beej-beige font-medium">Theme</span>
                  <ThemeToggle />
                </div>

                {/* Mobile Search */}
                <form onSubmit={handleSearch} className="px-4">
                  <div className="relative">
                    <input
                      type="text"
                      value={searchQuery}
                      onChange={(e) => setSearchQuery(e.target.value)}
                      placeholder="Search for healthy seeds..."
                      className="w-full pl-10 pr-4 py-2.5 border border-beej-green/30 rounded-full focus:outline-none focus:ring-2 focus:ring-beej-green focus:border-beej-green bg-beej-beige/20 dark:bg-slate-800 text-beej-brown dark:text-beej-beige"
                    />
                    <Search className="absolute left-3 top-2.5 h-5 w-5 text-beej-brown/50 dark:text-beej-beige/50" />
                  </div>
                </form>

                {/* Mobile Navigation */}
                <nav className="space-y-1 px-4">
                  <Link
                    to="/products"
                    className="block px-4 py-3 text-beej-brown dark:text-beej-beige hover:bg-beej-green/10 dark:hover:bg-beej-green/20 rounded-lg transition-colors duration-200 font-medium"
                    onClick={handleMobileMenuToggle}
                  >
                    Products
                  </Link>
                  <Link
                    to="/categories"
                    className="block px-4 py-3 text-beej-brown dark:text-beej-beige hover:bg-beej-green/10 dark:hover:bg-beej-green/20 rounded-lg transition-colors duration-200 font-medium"
                    onClick={handleMobileMenuToggle}
                  >
                    Categories
                  </Link>
                  <Link
                    to="/about"
                    className="block px-4 py-3 text-beej-brown dark:text-beej-beige hover:bg-beej-green/10 dark:hover:bg-beej-green/20 rounded-lg transition-colors duration-200 font-medium"
                    onClick={handleMobileMenuToggle}
                  >
                    About
                  </Link>
                  <Link
                    to="/blog"
                    className="block px-4 py-3 text-beej-brown dark:text-beej-beige hover:bg-beej-green/10 dark:hover:bg-beej-green/20 rounded-lg transition-colors duration-200 font-medium"
                    onClick={handleMobileMenuToggle}
                  >
                    Blog
                  </Link>

                  {isAuthenticated ? (
                    <>
                      <div className="border-t border-beej-green/20 pt-2 mt-2">
                        <Link
                          to="/cart"
                          className="block px-4 py-3 text-beej-brown dark:text-beej-beige hover:bg-beej-green/10 dark:hover:bg-beej-green/20 rounded-lg transition-colors duration-200 font-medium"
                          onClick={handleMobileMenuToggle}
                        >
                          Cart ({totalItems})
                        </Link>
                        <Link
                          to="/wishlist"
                          className="block px-4 py-3 text-beej-brown dark:text-beej-beige hover:bg-beej-green/10 dark:hover:bg-beej-green/20 rounded-lg transition-colors duration-200 font-medium"
                          onClick={handleMobileMenuToggle}
                        >
                          Wishlist
                        </Link>
                        <Link
                          to="/profile"
                          className="block px-4 py-3 text-beej-brown dark:text-beej-beige hover:bg-beej-green/10 dark:hover:bg-beej-green/20 rounded-lg transition-colors duration-200 font-medium"
                          onClick={handleMobileMenuToggle}
                        >
                          Profile
                        </Link>
                        <Link
                          to="/orders"
                          className="block px-4 py-3 text-beej-brown dark:text-beej-beige hover:bg-beej-green/10 dark:hover:bg-beej-green/20 rounded-lg transition-colors duration-200 font-medium"
                          onClick={handleMobileMenuToggle}
                        >
                          Orders
                        </Link>
                        {user?.role === 'ADMIN' && (
                          <Link
                            to="/admin"
                            className="block px-4 py-3 text-beej-brown dark:text-beej-beige hover:bg-beej-green/10 dark:hover:bg-beej-green/20 rounded-lg transition-colors duration-200 font-medium"
                            onClick={handleMobileMenuToggle}
                          >
                            Admin Dashboard
                          </Link>
                        )}
                        <button
                          onClick={() => {
                            handleLogout()
                            handleMobileMenuToggle()
                          }}
                          className="block w-full text-left px-4 py-3 text-red-500 hover:bg-red-50 dark:hover:bg-red-900/20 rounded-lg transition-colors duration-200 font-medium"
                        >
                          Logout
                        </button>
                      </div>
                    </>
                  ) : (
                    <div className="border-t border-beej-green/20 pt-2 mt-2 space-y-2">
                      <Link
                        to="/login"
                        className="block w-full px-4 py-3 text-beej-green hover:bg-beej-green/10 dark:hover:bg-beej-green/20 rounded-lg transition-colors duration-200 font-medium text-center"
                        onClick={handleMobileMenuToggle}
                      >
                        Login
                      </Link>
                      <Link
                        to="/register"
                        className="block w-full px-4 py-3 bg-beej-green text-white hover:bg-beej-green-dark rounded-lg transition-colors duration-200 font-medium text-center"
                        onClick={handleMobileMenuToggle}
                      >
                        Sign Up
                      </Link>
                    </div>
                  )}
                </nav>
              </div>
            </motion.div>
          )}
        </AnimatePresence>
      </div>
    </motion.header>
  )
}

export default Header
