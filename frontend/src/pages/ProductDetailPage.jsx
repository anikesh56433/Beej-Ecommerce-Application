import React, { useState, useEffect } from 'react'
import { useParams, Link } from 'react-router-dom'
import { motion } from 'framer-motion'
import { 
  Star, 
  Heart, 
  ShoppingCart, 
  Plus, 
  Minus, 
  Truck, 
  Shield, 
  RefreshCw,
  Check,
  Share2,
  ChevronRight,
  Package
} from 'lucide-react'
import { useDispatch, useSelector } from 'react-redux'
import { fetchProductById } from '../store/slices/productSlice'
import { addToCart } from '../store/slices/cartSlice'
import { addToWishlist } from '../store/slices/wishlistSlice'
import Button from '../components/ui/Button'
import Badge from '../components/ui/Badge'
import LoadingSpinner from '../components/ui/LoadingSpinner'

const ProductDetailPage = () => {
  const { id } = useParams()
  const dispatch = useDispatch()
  const { currentProduct, loading } = useSelector((state) => state.product)
  const { isAuthenticated } = useSelector((state) => state.auth)
  
  const [quantity, setQuantity] = useState(1)
  const [selectedImage, setSelectedImage] = useState(0)
  const [isWishlisted, setIsWishlisted] = useState(false)

  useEffect(() => {
    if (id) {
      dispatch(fetchProductById(id))
    }
  }, [dispatch, id])

  const handleAddToCart = () => {
    if (currentProduct) {
      dispatch(addToCart({
        productId: currentProduct.id,
        quantity,
        price: currentProduct.price
      }))
    }
  }

  const handleAddToWishlist = () => {
    if (currentProduct && isAuthenticated) {
      dispatch(addToWishlist(currentProduct.id))
      setIsWishlisted(!isWishlisted)
    }
  }

  const handleShare = () => {
    if (navigator.share) {
      navigator.share({
        title: currentProduct?.name,
        text: currentProduct?.description,
        url: window.location.href
      })
    } else {
      navigator.clipboard.writeText(window.location.href)
      alert('Product link copied to clipboard!')
    }
  }

  if (loading) {
    return (
      <div className="min-h-screen bg-beej-beige flex items-center justify-center">
        <LoadingSpinner size="xl" />
      </div>
    )
  }

  if (!currentProduct) {
    return (
      <div className="min-h-screen bg-beej-beige flex items-center justify-center">
        <div className="text-center">
          <h2 className="text-2xl font-bold text-beej-brown mb-4">Product Not Found</h2>
          <Link to="/products" className="text-beej-green hover:text-beej-green-dark">
            Back to Products
          </Link>
        </div>
      </div>
    )
  }

  // Mock data for demonstration
  const product = {
    ...currentProduct,
    images: [
      '🌱', '🥗', '💪', '🌾'
    ],
    rating: 4.8,
    reviews: 234,
    inStock: true,
    nutritionInfo: {
      calories: 486,
      protein: '16.5g',
      fiber: '34.4g',
      omega3: '17.8g'
    },
    benefits: [
      'Rich in Omega-3 fatty acids',
      'High in dietary fiber',
      'Supports heart health',
      'Aids in weight management'
    ]
  }

  return (
    <div className="min-h-screen bg-beej-beige">
      {/* Breadcrumb */}
      <div className="bg-white border-b border-beej-green/20">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
          <nav className="flex items-center space-x-2 text-sm">
            <Link to="/" className="text-beej-brown/60 hover:text-beej-green">Home</Link>
            <ChevronRight className="h-4 w-4 text-beej-brown/40" />
            <Link to="/products" className="text-beej-brown/60 hover:text-beej-green">Products</Link>
            <ChevronRight className="h-4 w-4 text-beej-brown/40" />
            <span className="text-beej-brown font-medium">{product.name}</span>
          </nav>
        </div>
      </div>

      {/* Product Details */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="grid lg:grid-cols-2 gap-12">
          {/* Product Images */}
          <motion.div
            initial={{ opacity: 0, x: -20 }}
            animate={{ opacity: 1, x: 0 }}
            transition={{ duration: 0.6 }}
          >
            <div className="space-y-4">
              {/* Main Image */}
              <div className="aspect-square bg-white rounded-2xl shadow-lg flex items-center justify-center overflow-hidden">
                <div className="text-8xl">{product.images[selectedImage]}</div>
              </div>
              
              {/* Thumbnail Gallery */}
              <div className="flex space-x-4">
                {product.images.map((image, index) => (
                  <button
                    key={index}
                    onClick={() => setSelectedImage(index)}
                    className={`aspect-square w-20 bg-white rounded-lg shadow-md flex items-center justify-center transition-all duration-200 ${
                      selectedImage === index ? 'ring-2 ring-beej-green' : 'hover:shadow-lg'
                    }`}
                  >
                    <div className="text-3xl">{image}</div>
                  </button>
                ))}
              </div>
            </div>
          </motion.div>

          {/* Product Info */}
          <motion.div
            initial={{ opacity: 0, x: 20 }}
            animate={{ opacity: 1, x: 0 }}
            transition={{ duration: 0.6, delay: 0.2 }}
            className="space-y-6"
          >
            {/* Title and Rating */}
            <div>
              <Badge variant="secondary" className="mb-3">Organic</Badge>
              <h1 className="text-3xl md:text-4xl font-bold text-beej-brown mb-4">
                {product.name}
              </h1>
              
              <div className="flex items-center space-x-4">
                <div className="flex items-center">
                  {[...Array(5)].map((_, i) => (
                    <Star key={i} className={`h-5 w-5 ${i < 4 ? 'fill-yellow-400 text-yellow-400' : 'text-gray-300'}`} />
                  ))}
                  <span className="ml-2 text-beej-brown/60">{product.rating}</span>
                </div>
                <span className="text-beej-brown/60">({product.reviews} reviews)</span>
              </div>
            </div>

            {/* Price */}
            <div className="flex items-baseline space-x-4">
              <span className="text-3xl font-bold text-beej-green">₹{product.price}</span>
              <span className="text-xl text-beej-brown/50 line-through">₹{product.originalPrice}</span>
              <Badge variant="discount">Save ₹{product.originalPrice - product.price}</Badge>
            </div>

            {/* Description */}
            <div>
              <h3 className="text-lg font-semibold text-beej-brown mb-2">Description</h3>
              <p className="text-beej-brown/70 leading-relaxed">
                {product.description}
              </p>
            </div>

            {/* Benefits */}
            <div>
              <h3 className="text-lg font-semibold text-beej-brown mb-3">Health Benefits</h3>
              <ul className="space-y-2">
                {product.benefits.map((benefit, index) => (
                  <li key={index} className="flex items-center text-beej-brown/70">
                    <Check className="h-5 w-5 text-beej-green mr-2 flex-shrink-0" />
                    {benefit}
                  </li>
                ))}
              </ul>
            </div>

            {/* Quantity and Actions */}
            <div className="space-y-4">
              <div className="flex items-center space-x-4">
                <label className="text-sm font-medium text-beej-brown">Quantity:</label>
                <div className="flex items-center border border-beej-green/30 rounded-lg">
                  <button
                    onClick={() => setQuantity(Math.max(1, quantity - 1))}
                    className="p-2 hover:bg-beej-green/10 transition-colors"
                  >
                    <Minus className="h-4 w-4" />
                  </button>
                  <input
                    type="number"
                    value={quantity}
                    onChange={(e) => setQuantity(Math.max(1, parseInt(e.target.value) || 1))}
                    className="w-16 text-center border-0 focus:ring-0"
                    min="1"
                  />
                  <button
                    onClick={() => setQuantity(quantity + 1)}
                    className="p-2 hover:bg-beej-green/10 transition-colors"
                  >
                    <Plus className="h-4 w-4" />
                  </button>
                </div>
                <span className="text-sm text-beej-brown/60">
                  {product.inStock ? 'In Stock' : 'Out of Stock'}
                </span>
              </div>

              <div className="flex space-x-4">
                <Button
                  variant="primary"
                  size="lg"
                  onClick={handleAddToCart}
                  className="flex-1"
                  disabled={!product.inStock}
                >
                  <ShoppingCart className="mr-2 h-5 w-5" />
                  Add to Cart
                </Button>
                
                <Button
                  variant="outline"
                  size="lg"
                  onClick={handleAddToWishlist}
                  className={isWishlisted ? 'text-red-500' : ''}
                >
                  <Heart className={`h-5 w-5 ${isWishlisted ? 'fill-current' : ''}`} />
                </Button>
                
                <Button
                  variant="outline"
                  size="lg"
                  onClick={handleShare}
                >
                  <Share2 className="h-5 w-5" />
                </Button>
              </div>
            </div>

            {/* Trust Badges */}
            <div className="grid grid-cols-3 gap-4 pt-6 border-t border-beej-green/20">
              <div className="text-center">
                <Truck className="h-8 w-8 text-beej-green mx-auto mb-2" />
                <p className="text-xs text-beej-brown/60">Free Shipping</p>
              </div>
              <div className="text-center">
                <Shield className="h-8 w-8 text-beej-green mx-auto mb-2" />
                <p className="text-xs text-beej-brown/60">Secure Payment</p>
              </div>
              <div className="text-center">
                <RefreshCw className="h-8 w-8 text-beej-green mx-auto mb-2" />
                <p className="text-xs text-beej-brown/60">30-Day Returns</p>
              </div>
            </div>
          </motion.div>
        </div>

        {/* Additional Information Tabs */}
        <div className="mt-16">
          <div className="bg-white rounded-2xl shadow-lg p-8">
            <div className="border-b border-beej-green/20">
              <nav className="flex space-x-8">
                <button className="py-4 px-1 border-b-2 border-beej-green text-beej-green font-medium">
                  Nutrition Facts
                </button>
                <button className="py-4 px-1 border-b-2 border-transparent text-beej-brown/60 hover:text-beej-brown">
                  How to Use
                </button>
                <button className="py-4 px-1 border-b-2 border-transparent text-beej-brown/60 hover:text-beej-brown">
                  Storage
                </button>
                <button className="py-4 px-1 border-b-2 border-transparent text-beej-brown/60 hover:text-beej-brown">
                  Reviews
                </button>
              </nav>
            </div>
            
            <div className="py-8">
              <div className="grid md:grid-cols-2 gap-8">
                <div>
                  <h3 className="text-lg font-semibold text-beej-brown mb-4">Nutrition Information</h3>
                  <div className="space-y-3">
                    <div className="flex justify-between">
                      <span className="text-beej-brown/60">Calories</span>
                      <span className="font-medium text-beej-brown">{product.nutritionInfo.calories} kcal</span>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-beej-brown/60">Protein</span>
                      <span className="font-medium text-beej-brown">{product.nutritionInfo.protein}</span>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-beej-brown/60">Dietary Fiber</span>
                      <span className="font-medium text-beej-brown">{product.nutritionInfo.fiber}</span>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-beej-brown/60">Omega-3</span>
                      <span className="font-medium text-beej-brown">{product.nutritionInfo.omega3}</span>
                    </div>
                  </div>
                </div>
                
                <div>
                  <h3 className="text-lg font-semibold text-beej-brown mb-4">Serving Size: 2 tbsp (15g)</h3>
                  <p className="text-beej-brown/60 text-sm">
                    Per 100g serving. Daily values are based on a 2000 calorie diet.
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default ProductDetailPage
