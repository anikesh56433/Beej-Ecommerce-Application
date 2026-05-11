import React from 'react'
import { Link } from 'react-router-dom'
import { formatPrice } from '../../utils/formatters'
import Button from '../ui/Button'
import LoadingSpinner from '../ui/LoadingSpinner'
import { useDispatch } from 'react-redux'
import { addToCart } from '../../store/slices/cartSlice'
import { toast } from 'react-toastify'

const ProductGrid = ({ products, loading, error }) => {
  const dispatch = useDispatch()

  const handleAddToCart = async (productId) => {
    try {
      await dispatch(addToCart({ productId, quantity: 1 })).unwrap()
      toast.success('Product added to cart!')
    } catch (error) {
      toast.error(error || 'Failed to add to cart')
    }
  }

  if (loading) {
    return (
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
        {[...Array(8)].map((_, index) => (
          <div key={index} className="animate-pulse">
            <div className="bg-gray-200 rounded-lg h-48 mb-4"></div>
            <div className="h-4 bg-gray-200 rounded mb-2"></div>
            <div className="h-4 bg-gray-200 rounded w-3/4"></div>
          </div>
        ))}
      </div>
    )
  }

  if (error) {
    return (
      <div className="text-center py-12">
        <p className="text-red-500 mb-4">{error}</p>
        <Button onClick={() => window.location.reload()}>
          Try Again
        </Button>
      </div>
    )
  }

  if (!products || products.length === 0) {
    return (
      <div className="text-center py-12">
        <p className="text-gray-500 mb-4">No products found</p>
      </div>
    )
  }

  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
      {products.map((product) => (
        <div key={product.id} className="card group">
          <div className="relative overflow-hidden rounded-t-lg">
            <img
              src={product.imageUrl || `https://via.placeholder.com/400x300/4F46E5/FFFFFF?text=${product.name}`}
              alt={product.name}
              className="w-full h-48 object-cover group-hover:scale-105 transition-transform duration-300"
            />
            {product.featured && (
              <span className="absolute top-2 left-2 bg-yellow-400 text-yellow-900 text-xs px-2 py-1 rounded-full">
                Featured
              </span>
            )}
          </div>
          
          <div className="p-4">
            <h3 className="font-semibold text-lg mb-2 line-clamp-2">
              <Link 
                to={`/products/${product.id}`}
                className="hover:text-primary transition-colors"
              >
                {product.name}
              </Link>
            </h3>
            
            <p className="text-gray-600 text-sm mb-3 line-clamp-2">
              {product.shortDescription || product.description}
            </p>
            
            <div className="flex items-center justify-between mb-4">
              <div>
                <span className="text-2xl font-bold text-primary">
                  {formatPrice(product.price)}
                </span>
                {product.comparePrice && (
                  <span className="text-sm text-gray-500 line-through ml-2">
                    {formatPrice(product.comparePrice)}
                  </span>
                )}
              </div>
            </div>
            
            <div className="flex gap-2">
              <Link to={`/products/${product.id}`} className="flex-1">
                <Button variant="outline" size="sm" className="w-full">
                  View Details
                </Button>
              </Link>
              <Button 
                size="sm" 
                onClick={() => handleAddToCart(product.id)}
                disabled={product.stockQuantity === 0}
              >
                {product.stockQuantity === 0 ? 'Out of Stock' : 'Add to Cart'}
              </Button>
            </div>
          </div>
        </div>
      ))}
    </div>
  )
}

export default ProductGrid
