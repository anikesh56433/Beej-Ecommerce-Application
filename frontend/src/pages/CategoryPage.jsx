import React, { useState, useEffect } from 'react'
import { useParams, Link } from 'react-router-dom'
import { motion } from 'framer-motion'
import { Filter, Grid, List, Search, Heart, ShoppingCart, Star } from 'lucide-react'
import { useDispatch, useSelector } from 'react-redux'
import { fetchProductsByCategory } from '../store/slices/productSlice'
import { addToCart } from '../store/slices/cartSlice'
import { addToWishlist } from '../store/slices/wishlistSlice'
import Button from '../components/ui/Button'
import Input from '../components/ui/Input'
import Badge from '../components/ui/Badge'
import LoadingSpinner from '../components/ui/LoadingSpinner'

const CategoryPage = () => {
  const { slug } = useParams()
  const dispatch = useDispatch()
  const { products, loading } = useSelector((state) => state.product)
  
  const [viewMode, setViewMode] = useState('grid')
  const [sortBy, setSortBy] = useState('name')
  const [searchTerm, setSearchTerm] = useState('')
  const [priceRange, setPriceRange] = useState([0, 1000])

  useEffect(() => {
    if (slug) {
      dispatch(fetchProductsByCategory(slug))
    }
  }, [dispatch, slug])

  const handleAddToCart = (product) => {
    dispatch(addToCart({
      productId: product.id,
      quantity: 1,
      price: product.price
    }))
  }

  const handleAddToWishlist = (productId) => {
    dispatch(addToWishlist(productId))
  }

  // Mock category data
  const categoryData = {
    'chia-seeds': {
      name: 'Chia Seeds',
      description: 'Nutrient-dense superfood rich in omega-3 fatty acids, fiber, and protein',
      icon: '🌱'
    },
    'flax-seeds': {
      name: 'Flax Seeds',
      description: 'Excellent source of lignans and omega-3, perfect for heart health',
      icon: '🌾'
    },
    'pumpkin-seeds': {
      name: 'Pumpkin Seeds',
      description: 'Rich in magnesium, zinc, and antioxidants for immune support',
      icon: '🎃'
    },
    'sunflower-seeds': {
      name: 'Sunflower Seeds',
      description: 'Packed with vitamin E and healthy fats for skin and heart health',
      icon: '🌻'
    }
  }

  const category = categoryData[slug] || {
    name: 'Category',
    description: 'Explore our premium selection of healthy seeds',
    icon: '🌰'
  }

  const filteredProducts = products?.filter(product => {
    const matchesSearch = product.name.toLowerCase().includes(searchTerm.toLowerCase())
    const matchesPrice = product.price >= priceRange[0] && product.price <= priceRange[1]
    return matchesSearch && matchesPrice
  }) || []

  const sortedProducts = [...filteredProducts].sort((a, b) => {
    switch (sortBy) {
      case 'price-low':
        return a.price - b.price
      case 'price-high':
        return b.price - a.price
      case 'name':
        return a.name.localeCompare(b.name)
      case 'rating':
        return (b.rating || 0) - (a.rating || 0)
      default:
        return 0
    }
  })

  const ProductCard = ({ product, index }) => (
    <motion.div
      key={product.id}
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.6, delay: index * 0.1 }}
    >
      <div className="bg-white rounded-2xl shadow-lg hover:shadow-xl transition-all duration-300 overflow-hidden group">
        <div className="relative">
          <div className="aspect-square bg-gradient-to-br from-beej-green/10 to-beej-green/20 flex items-center justify-center">
            <div className="text-6xl">{category.icon}</div>
          </div>
          <Badge variant="discount" className="absolute top-4 left-4">
            -20%
          </Badge>
          <button
            onClick={() => handleAddToWishlist(product.id)}
            className="absolute top-4 right-4 p-2 bg-white rounded-full shadow-md opacity-0 group-hover:opacity-100 transition-opacity"
          >
            <Heart className="h-4 w-4 text-beej-brown hover:text-red-500" />
          </button>
        </div>
        
        <div className="p-6">
          <h3 className="text-lg font-semibold text-beej-brown mb-2 group-hover:text-beej-green transition-colors">
            {product.name}
          </h3>
          <p className="text-beej-brown/60 text-sm mb-4 line-clamp-2">
            {product.description}
          </p>
          
          <div className="flex items-center mb-4">
            <div className="flex items-center">
              {[...Array(5)].map((_, i) => (
                <Star key={i} className={`h-4 w-4 ${i < (product.rating || 4) ? 'fill-yellow-400 text-yellow-400' : 'text-gray-300'}`} />
              ))}
            </div>
            <span className="ml-2 text-sm text-beej-brown/60">({product.reviews || 23})</span>
          </div>
          
          <div className="flex items-center justify-between mb-4">
            <div>
              <span className="text-xl font-bold text-beej-green">₹{product.price}</span>
              <span className="text-sm text-beej-brown/50 line-through ml-2">₹{product.originalPrice}</span>
            </div>
          </div>
          
          <Button
            variant="primary"
            size="sm"
            onClick={() => handleAddToCart(product)}
            className="w-full"
          >
            <ShoppingCart className="mr-2 h-4 w-4" />
            Add to Cart
          </Button>
        </div>
      </div>
    </motion.div>
  )

  if (loading) {
    return (
      <div className="min-h-screen bg-beej-beige flex items-center justify-center">
        <LoadingSpinner size="xl" />
      </div>
    )
  }

  return (
    <div className="min-h-screen bg-beej-beige">
      {/* Category Header */}
      <div className="bg-gradient-to-br from-beej-green to-beej-green-light text-white py-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex items-center space-x-4 mb-6">
            <div className="text-4xl">{category.icon}</div>
            <div>
              <h1 className="text-4xl md:text-5xl font-bold mb-2">{category.name}</h1>
              <p className="text-xl text-beej-beige/90 max-w-2xl">
                {category.description}
              </p>
            </div>
          </div>
        </div>
      </div>

      {/* Filters and Products */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="flex flex-col lg:flex-row gap-8">
          {/* Filters Sidebar */}
          <div className="lg:w-64 space-y-6">
            {/* Search */}
            <div className="bg-white rounded-xl p-6 shadow-lg">
              <h3 className="font-semibold text-beej-brown mb-4">Search</h3>
              <div className="relative">
                <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-beej-brown/50" />
                <Input
                  placeholder="Search products..."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  className="pl-10"
                />
              </div>
            </div>

            {/* Sort */}
            <div className="bg-white rounded-xl p-6 shadow-lg">
              <h3 className="font-semibold text-beej-brown mb-4">Sort By</h3>
              <select
                value={sortBy}
                onChange={(e) => setSortBy(e.target.value)}
                className="w-full px-4 py-2 border border-beej-green/30 rounded-lg focus:outline-none focus:ring-2 focus:ring-beej-green"
              >
                <option value="name">Name</option>
                <option value="price-low">Price: Low to High</option>
                <option value="price-high">Price: High to Low</option>
                <option value="rating">Rating</option>
              </select>
            </div>

            {/* Price Range */}
            <div className="bg-white rounded-xl p-6 shadow-lg">
              <h3 className="font-semibold text-beej-brown mb-4">Price Range</h3>
              <div className="space-y-4">
                <div className="flex items-center justify-between">
                  <span className="text-sm text-beej-brown/60">₹{priceRange[0]}</span>
                  <span className="text-sm text-beej-brown/60">₹{priceRange[1]}</span>
                </div>
                <input
                  type="range"
                  min="0"
                  max="1000"
                  value={priceRange[1]}
                  onChange={(e) => setPriceRange([priceRange[0], parseInt(e.target.value)])}
                  className="w-full"
                />
              </div>
            </div>

            {/* View Mode */}
            <div className="bg-white rounded-xl p-6 shadow-lg">
              <h3 className="font-semibold text-beej-brown mb-4">View</h3>
              <div className="flex space-x-2">
                <Button
                  variant={viewMode === 'grid' ? 'primary' : 'outline'}
                  size="sm"
                  onClick={() => setViewMode('grid')}
                  className="flex-1"
                >
                  <Grid className="h-4 w-4" />
                </Button>
                <Button
                  variant={viewMode === 'list' ? 'primary' : 'outline'}
                  size="sm"
                  onClick={() => setViewMode('list')}
                  className="flex-1"
                >
                  <List className="h-4 w-4" />
                </Button>
              </div>
            </div>
          </div>

          {/* Products Grid */}
          <div className="flex-1">
            {/* Results Header */}
            <div className="flex items-center justify-between mb-6">
              <div>
                <h2 className="text-2xl font-bold text-beej-brown">
                  {sortedProducts.length} Products
                </h2>
                <p className="text-beej-brown/60">
                  Showing {sortedProducts.length} of {products?.length || 0} products
                </p>
              </div>
              
              <Button variant="outline" onClick={() => window.open('https://wa.me/918319143937?text=Hello%20I%20want%20to%20order%20from%20' + category.name, '_blank')}>
                <span className="text-green-500">📱</span> WhatsApp Order
              </Button>
            </div>

            {/* Products */}
            {sortedProducts.length > 0 ? (
              <div className={
                viewMode === 'grid' 
                  ? 'grid md:grid-cols-2 lg:grid-cols-3 gap-6'
                  : 'space-y-4'
              }>
                {sortedProducts.map((product, index) => (
                  viewMode === 'grid' ? (
                    <ProductCard key={product.id} product={product} index={index} />
                  ) : (
                    <motion.div
                      key={product.id}
                      initial={{ opacity: 0, y: 20 }}
                      animate={{ opacity: 1, y: 0 }}
                      transition={{ duration: 0.6, delay: index * 0.1 }}
                      className="bg-white rounded-xl shadow-lg hover:shadow-xl transition-shadow p-6"
                    >
                      <div className="flex items-center space-x-4">
                        <div className="w-20 h-20 bg-beej-green/10 rounded-lg flex items-center justify-center">
                          <div className="text-2xl">{category.icon}</div>
                        </div>
                        <div className="flex-1">
                          <h3 className="font-semibold text-beej-brown">{product.name}</h3>
                          <p className="text-beej-brown/60 text-sm mb-2">{product.description}</p>
                          <div className="flex items-center justify-between">
                            <div>
                              <span className="text-lg font-bold text-beej-green">₹{product.price}</span>
                              <span className="text-sm text-beej-brown/50 line-through ml-2">₹{product.originalPrice}</span>
                            </div>
                            <Button
                              variant="primary"
                              size="sm"
                              onClick={() => handleAddToCart(product)}
                            >
                              <ShoppingCart className="h-4 w-4" />
                            </Button>
                          </div>
                        </div>
                      </div>
                    </motion.div>
                  )
                ))}
              </div>
            ) : (
              <div className="text-center py-12">
                <div className="text-6xl mb-4">🔍</div>
                <h3 className="text-xl font-semibold text-beej-brown mb-2">
                  No products found
                </h3>
                <p className="text-beej-brown/60">
                  Try adjusting your filters or search terms
                </p>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  )
}

export default CategoryPage
