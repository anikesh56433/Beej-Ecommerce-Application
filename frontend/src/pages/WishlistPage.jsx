import React, { useState, useEffect } from 'react'
import { motion } from 'framer-motion'
import { Link } from 'react-router-dom'
import { 
  Heart, 
  ShoppingCart, 
  Trash2, 
  Search, 
  Grid, 
  List,
  Star,
  Plus,
  Minus
} from 'lucide-react'
import { useSelector, useDispatch } from 'react-redux'
import { fetchWishlist, removeFromWishlist } from '../store/slices/wishlistSlice'
import { addToCart } from '../store/slices/cartSlice'
import Button from '../components/ui/Button'
import Input from '../components/ui/Input'
import Badge from '../components/ui/Badge'
import LoadingSpinner from '../components/ui/LoadingSpinner'

const WishlistPage = () => {
  const dispatch = useDispatch()
  const { items, loading } = useSelector((state) => state.wishlist)
  
  const [viewMode, setViewMode] = useState('grid')
  const [searchTerm, setSearchTerm] = useState('')
  const [quantities, setQuantities] = useState({})

  useEffect(() => {
    dispatch(fetchWishlist())
  }, [dispatch])

  const handleRemoveFromWishlist = (productId) => {
    dispatch(removeFromWishlist(productId))
  }

  const handleAddToCart = (product) => {
    const quantity = quantities[product.id] || 1
    dispatch(addToCart({
      productId: product.id,
      quantity,
      price: product.price
    }))
  }

  const updateQuantity = (productId, change) => {
    setQuantities(prev => ({
      ...prev,
      [productId]: Math.max(1, (prev[productId] || 1) + change)
    }))
  }

  // Mock wishlist data for demonstration
  const mockWishlistItems = [
    {
      id: 1,
      name: 'Premium Chia Seeds',
      price: 499,
      originalPrice: 599,
      description: 'Rich in omega-3 and fiber, perfect for healthy breakfast',
      rating: 4.8,
      reviews: 234,
      inStock: true,
      icon: '🌱'
    },
    {
      id: 2,
      name: 'Organic Flax Seeds',
      price: 399,
      originalPrice: 499,
      description: 'Excellent source of lignans and omega-3 for heart health',
      rating: 4.7,
      reviews: 189,
      inStock: true,
      icon: '🌾'
    },
    {
      id: 3,
      name: 'Pumpkin Seeds',
      price: 299,
      originalPrice: 399,
      description: 'Rich in magnesium and zinc, perfect for immune support',
      rating: 4.6,
      reviews: 156,
      inStock: false,
      icon: '🎃'
    },
    {
      id: 4,
      name: 'Sunflower Seeds',
      price: 199,
      originalPrice: 299,
      description: 'Packed with vitamin E and healthy fats',
      rating: 4.5,
      reviews: 123,
      inStock: true,
      icon: '🌻'
    }
  ]

  const wishlistItems = items.length > 0 ? items : mockWishlistItems

  const filteredItems = wishlistItems.filter(item =>
    item.name.toLowerCase().includes(searchTerm.toLowerCase())
  )

  const ProductCard = ({ item, index }) => (
    <motion.div
      key={item.id}
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.6, delay: index * 0.1 }}
      className="bg-white rounded-2xl shadow-lg hover:shadow-xl transition-all duration-300 overflow-hidden group"
    >
      <div className="relative">
        <div className="aspect-square bg-gradient-to-br from-beej-green/10 to-beej-green/20 flex items-center justify-center">
          <div className="text-6xl">{item.icon}</div>
        </div>
        
        <button
          onClick={() => handleRemoveFromWishlist(item.id)}
          className="absolute top-4 right-4 p-2 bg-white rounded-full shadow-md opacity-0 group-hover:opacity-100 transition-all duration-200 hover:scale-110"
        >
          <Trash2 className="h-4 w-4 text-red-500" />
        </button>
        
        {!item.inStock && (
          <div className="absolute inset-0 bg-black/50 flex items-center justify-center">
            <Badge variant="destructive">Out of Stock</Badge>
          </div>
        )}
      </div>
      
      <div className="p-6">
        <h3 className="text-lg font-semibold text-beej-brown mb-2 group-hover:text-beej-green transition-colors">
          {item.name}
        </h3>
        <p className="text-beej-brown/60 text-sm mb-4 line-clamp-2">
          {item.description}
        </p>
        
        <div className="flex items-center mb-4">
          <div className="flex items-center">
            {[...Array(5)].map((_, i) => (
              <Star key={i} className={`h-4 w-4 ${i < Math.floor(item.rating) ? 'fill-yellow-400 text-yellow-400' : 'text-gray-300'}`} />
            ))}
          </div>
          <span className="ml-2 text-sm text-beej-brown/60">({item.reviews})</span>
        </div>
        
        <div className="flex items-center justify-between mb-4">
          <div>
            <span className="text-xl font-bold text-beej-green">₹{item.price}</span>
            <span className="text-sm text-beej-brown/50 line-through ml-2">₹{item.originalPrice}</span>
          </div>
          <Badge variant="discount">
            -{Math.round((1 - item.price / item.originalPrice) * 100)}%
          </Badge>
        </div>
        
        {item.inStock ? (
          <div className="space-y-3">
            <div className="flex items-center justify-between">
              <span className="text-sm text-beej-brown/60">Quantity:</span>
              <div className="flex items-center space-x-2">
                <button
                  onClick={() => updateQuantity(item.id, -1)}
                  className="p-1 rounded hover:bg-beej-green/10 transition-colors"
                >
                  <Minus className="h-4 w-4" />
                </button>
                <span className="w-8 text-center font-medium">{quantities[item.id] || 1}</span>
                <button
                  onClick={() => updateQuantity(item.id, 1)}
                  className="p-1 rounded hover:bg-beej-green/10 transition-colors"
                >
                  <Plus className="h-4 w-4" />
                </button>
              </div>
            </div>
            
            <Button
              variant="primary"
              size="sm"
              onClick={() => handleAddToCart(item)}
              className="w-full"
            >
              <ShoppingCart className="mr-2 h-4 w-4" />
              Add to Cart
            </Button>
          </div>
        ) : (
          <Button variant="outline" size="sm" disabled className="w-full">
            Out of Stock
          </Button>
        )}
      </div>
    </motion.div>
  )

  const ProductListItem = ({ item, index }) => (
    <motion.div
      key={item.id}
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.6, delay: index * 0.1 }}
      className="bg-white rounded-xl shadow-lg hover:shadow-xl transition-shadow p-6"
    >
      <div className="flex items-center space-x-4">
        <div className="w-20 h-20 bg-beej-green/10 rounded-lg flex items-center justify-center flex-shrink-0">
          <div className="text-3xl">{item.icon}</div>
        </div>
        
        <div className="flex-1">
          <h3 className="font-semibold text-beej-brown mb-1">{item.name}</h3>
          <p className="text-beej-brown/60 text-sm mb-2">{item.description}</p>
          
          <div className="flex items-center justify-between">
            <div>
              <span className="text-lg font-bold text-beej-green">₹{item.price}</span>
              <span className="text-sm text-beej-brown/50 line-through ml-2">₹{item.originalPrice}</span>
            </div>
            
            <div className="flex items-center space-x-2">
              {item.inStock ? (
                <>
                  <div className="flex items-center space-x-1">
                    <button
                      onClick={() => updateQuantity(item.id, -1)}
                      className="p-1 rounded hover:bg-beej-green/10 transition-colors"
                    >
                      <Minus className="h-3 w-3" />
                    </button>
                    <span className="w-6 text-center text-sm font-medium">{quantities[item.id] || 1}</span>
                    <button
                      onClick={() => updateQuantity(item.id, 1)}
                      className="p-1 rounded hover:bg-beej-green/10 transition-colors"
                    >
                      <Plus className="h-3 w-3" />
                    </button>
                  </div>
                  
                  <Button
                    variant="primary"
                    size="sm"
                    onClick={() => handleAddToCart(item)}
                  >
                    <ShoppingCart className="h-4 w-4" />
                  </Button>
                </>
              ) : (
                <Button variant="outline" size="sm" disabled>
                  Out of Stock
                </Button>
              )}
              
              <button
                onClick={() => handleRemoveFromWishlist(item.id)}
                className="p-2 text-red-500 hover:bg-red-50 rounded-lg transition-colors"
              >
                <Trash2 className="h-4 w-4" />
              </button>
            </div>
          </div>
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
      {/* Header */}
      <div className="bg-white border-b border-beej-green/20">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
          <div className="flex items-center justify-between">
            <div className="flex items-center space-x-4">
              <Heart className="h-6 w-6 text-beej-green" />
              <h1 className="text-2xl font-bold text-beej-green">My Wishlist</h1>
              <Badge variant="secondary">{filteredItems.length} items</Badge>
            </div>
            
            <div className="flex items-center space-x-4">
              <div className="relative">
                <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-beej-brown/50" />
                <Input
                  placeholder="Search wishlist..."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  className="pl-10 w-64"
                />
              </div>
              
              <div className="flex space-x-2">
                <Button
                  variant={viewMode === 'grid' ? 'primary' : 'outline'}
                  size="sm"
                  onClick={() => setViewMode('grid')}
                >
                  <Grid className="h-4 w-4" />
                </Button>
                <Button
                  variant={viewMode === 'list' ? 'primary' : 'outline'}
                  size="sm"
                  onClick={() => setViewMode('list')}
                >
                  <List className="h-4 w-4" />
                </Button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        {filteredItems.length > 0 ? (
          <>
            {/* Actions Bar */}
            <div className="bg-white rounded-xl shadow-lg p-4 mb-8">
              <div className="flex items-center justify-between">
                <div className="text-beej-brown">
                  <span className="font-medium">{filteredItems.length}</span> items in wishlist
                </div>
                
                <div className="flex items-center space-x-4">
                  <Button
                    variant="outline"
                    onClick={() => {
                      filteredItems.forEach(item => {
                        if (item.inStock) {
                          handleAddToCart(item)
                        }
                      })
                    }}
                  >
                    <ShoppingCart className="h-4 w-4 mr-2" />
                    Add All to Cart
                  </Button>
                  
                  <Link to="/products">
                    <Button variant="primary">
                      Continue Shopping
                    </Button>
                  </Link>
                </div>
              </div>
            </div>

            {/* Products */}
            <div className={
              viewMode === 'grid' 
                ? 'grid md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6'
                : 'space-y-4'
            }>
              {filteredItems.map((item, index) => (
                viewMode === 'grid' ? (
                  <ProductCard key={item.id} item={item} index={index} />
                ) : (
                  <ProductListItem key={item.id} item={item} index={index} />
                )
              ))}
            </div>
          </>
        ) : (
          <div className="text-center py-16">
            <div className="max-w-md mx-auto">
              <motion.div
                initial={{ scale: 0 }}
                animate={{ scale: 1 }}
                transition={{ duration: 0.5 }}
                className="w-24 h-24 bg-beej-green/10 rounded-full flex items-center justify-center mx-auto mb-6"
              >
                <Heart className="h-12 w-12 text-beej-green" />
              </motion.div>
              
              <h2 className="text-2xl font-bold text-beej-brown mb-4">
                Your wishlist is empty
              </h2>
              
              <p className="text-beej-brown/60 mb-8">
                Save your favorite products for later. Start adding items to your wishlist to see them here.
              </p>
              
              <div className="space-y-4">
                <Link to="/products">
                  <Button variant="primary" className="w-full">
                    <ShoppingCart className="mr-2 h-5 w-5" />
                    Start Shopping
                  </Button>
                </Link>
                
                <Button
                  variant="outline"
                  onClick={() => window.open('https://wa.me/918319143937?text=Hello%20I%20want%20to%20order%20BEEJ%20products', '_blank')}
                  className="w-full"
                >
                  <span className="text-green-500">📱</span> WhatsApp Order
                </Button>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  )
}

export default WishlistPage
