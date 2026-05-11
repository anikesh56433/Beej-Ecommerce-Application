import React, { useEffect, useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { useSelector, useDispatch } from 'react-redux'
import { fetchProductById } from '../../store/slices/productSlice'
import { addToCart } from '../../store/slices/cartSlice'
import { formatPrice } from '../../utils/formatters'
import { toast } from 'react-toastify'

// Components
import Button from '../../components/ui/Button'
import LoadingSpinner from '../../components/ui/LoadingSpinner'
import { ShoppingCart, Heart, Share2, Star } from 'lucide-react'

const ProductDetail = () => {
  const { id } = useParams()
  const dispatch = useDispatch()
  const navigate = useNavigate()
  const { currentProduct, loading, error } = useSelector((state) => state.product)
  const { isAuthenticated } = useSelector((state) => state.auth)
  
  const [selectedImage, setSelectedImage] = useState(0)
  const [quantity, setQuantity] = useState(1)
  const [isAddingToCart, setIsAddingToCart] = useState(false)

  useEffect(() => {
    if (id) {
      dispatch(fetchProductById(id))
    }
  }, [id, dispatch])

  const handleAddToCart = async () => {
    if (!isAuthenticated) {
      navigate('/login', { state: { from: `/products/${id}` } })
      return
    }

    setIsAddingToCart(true)
    try {
      await dispatch(addToCart({ productId: parseInt(id), quantity })).unwrap()
      toast.success('Product added to cart!')
    } catch (error) {
      toast.error(error || 'Failed to add to cart')
    } finally {
      setIsAddingToCart(false)
    }
  }

  const handleQuantityChange = (newQuantity) => {
    if (newQuantity >= 1 && newQuantity <= (currentProduct?.stockQuantity || 1)) {
      setQuantity(newQuantity)
    }
  }

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <LoadingSpinner size="lg" />
      </div>
    )
  }

  if (error || !currentProduct) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="text-center">
          <h2 className="text-2xl font-bold text-gray-900 mb-4">Product Not Found</h2>
          <p className="text-gray-600 mb-6">The product you're looking for doesn't exist.</p>
          <Button onClick={() => navigate('/products')}>
            Back to Products
          </Button>
        </div>
      </div>
    )
  }

  const images = currentProduct.images || [
    `https://via.placeholder.com/600x400/4F46E5/FFFFFF?text=${currentProduct.name}`
  ]

  const averageRating = currentProduct.averageRating || 0
  const reviewCount = currentProduct.reviewCount || 0

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        {/* Product Images */}
        <div>
          <div className="mb-4">
            <img
              src={images[selectedImage]}
              alt={currentProduct.name}
              className="w-full h-96 object-cover rounded-lg"
            />
          </div>
          
          {images.length > 1 && (
            <div className="grid grid-cols-4 gap-2">
              {images.map((image, index) => (
                <button
                  key={index}
                  onClick={() => setSelectedImage(index)}
                  className={`border-2 rounded-lg overflow-hidden ${
                    selectedImage === index ? 'border-primary' : 'border-gray-200'
                  }`}
                >
                  <img
                    src={image}
                    alt={`${currentProduct.name} ${index + 1}`}
                    className="w-full h-20 object-cover"
                  />
                </button>
              ))}
            </div>
          )}
        </div>

        {/* Product Info */}
        <div>
          <div className="mb-6">
            <h1 className="text-3xl font-bold text-gray-900 mb-2">
              {currentProduct.name}
            </h1>
            
            {/* Rating */}
            <div className="flex items-center mb-4">
              <div className="flex items-center">
                {[...Array(5)].map((_, index) => (
                  <Star
                    key={index}
                    className={`h-5 w-5 ${
                      index < Math.floor(averageRating)
                        ? 'text-yellow-400 fill-current'
                        : 'text-gray-300'
                    }`}
                  />
                ))}
              </div>
              <span className="ml-2 text-gray-600">
                {averageRating.toFixed(1)} ({reviewCount} reviews)
              </span>
            </div>

            {/* Price */}
            <div className="flex items-center mb-6">
              <span className="text-3xl font-bold text-primary">
                {formatPrice(currentProduct.price)}
              </span>
              {currentProduct.comparePrice && (
                <span className="ml-3 text-lg text-gray-500 line-through">
                  {formatPrice(currentProduct.comparePrice)}
                </span>
              )}
              {currentProduct.comparePrice && (
                <span className="ml-3 bg-green-100 text-green-800 px-2 py-1 rounded-full text-sm">
                  Save {formatPrice(currentProduct.comparePrice - currentProduct.price)}
                </span>
              )}
            </div>

            {/* Stock Status */}
            <div className="mb-6">
              {currentProduct.stockQuantity > 0 ? (
                <div className="flex items-center text-green-600">
                  <div className="w-3 h-3 bg-green-600 rounded-full mr-2"></div>
                  In Stock ({currentProduct.stockQuantity} available)
                </div>
              ) : (
                <div className="flex items-center text-red-600">
                  <div className="w-3 h-3 bg-red-600 rounded-full mr-2"></div>
                  Out of Stock
                </div>
              )}
            </div>

            {/* Description */}
            <div className="mb-6">
              <h3 className="text-lg font-semibold mb-2">Description</h3>
              <p className="text-gray-600 leading-relaxed">
                {currentProduct.description || currentProduct.shortDescription}
              </p>
            </div>

            {/* Quantity and Add to Cart */}
            <div className="space-y-4">
              <div className="flex items-center space-x-4">
                <label className="text-sm font-medium">Quantity:</label>
                <div className="flex items-center border border-gray-300 rounded-md">
                  <button
                    onClick={() => handleQuantityChange(quantity - 1)}
                    className="px-3 py-2 text-gray-600 hover:bg-gray-100"
                    disabled={quantity <= 1}
                  >
                    -
                  </button>
                  <span className="px-4 py-2 text-center w-16">
                    {quantity}
                  </span>
                  <button
                    onClick={() => handleQuantityChange(quantity + 1)}
                    className="px-3 py-2 text-gray-600 hover:bg-gray-100"
                    disabled={quantity >= currentProduct.stockQuantity}
                  >
                    +
                  </button>
                </div>
              </div>

              <div className="flex space-x-4">
                <Button
                  onClick={handleAddToCart}
                  disabled={currentProduct.stockQuantity === 0 || isAddingToCart}
                  className="flex-1"
                >
                  {isAddingToCart ? (
                    <>
                      <LoadingSpinner size="sm" className="mr-2" />
                      Adding...
                    </>
                  ) : currentProduct.stockQuantity === 0 ? (
                    'Out of Stock'
                  ) : (
                    <>
                      <ShoppingCart className="h-5 w-5 mr-2" />
                      Add to Cart
                    </>
                  )}
                </Button>
                
                <Button variant="outline" size="icon">
                  <Heart className="h-5 w-5" />
                </Button>
                
                <Button variant="outline" size="icon">
                  <Share2 className="h-5 w-5" />
                </Button>
              </div>
            </div>

            {/* Product Details */}
            <div className="mt-8 pt-8 border-t">
              <h3 className="text-lg font-semibold mb-4">Product Details</h3>
              <div className="grid grid-cols-2 gap-4 text-sm">
                <div>
                  <span className="text-gray-600">SKU:</span>
                  <span className="ml-2 font-medium">{currentProduct.sku}</span>
                </div>
                <div>
                  <span className="text-gray-600">Category:</span>
                  <span className="ml-2 font-medium">{currentProduct.category?.name}</span>
                </div>
                {currentProduct.brand && (
                  <div>
                    <span className="text-gray-600">Brand:</span>
                    <span className="ml-2 font-medium">{currentProduct.brand}</span>
                  </div>
                )}
                {currentProduct.weight && (
                  <div>
                    <span className="text-gray-600">Weight:</span>
                    <span className="ml-2 font-medium">{currentProduct.weight} kg</span>
                  </div>
                )}
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Product Reviews Section */}
      <div className="mt-12">
        <h2 className="text-2xl font-bold mb-6">Customer Reviews</h2>
        {reviewCount > 0 ? (
          <div className="space-y-4">
            {/* Reviews would be displayed here */}
            <p className="text-gray-600">Reviews section coming soon...</p>
          </div>
        ) : (
          <p className="text-gray-600">No reviews yet. Be the first to review this product!</p>
        )}
      </div>
    </div>
  )
}

export default ProductDetail
