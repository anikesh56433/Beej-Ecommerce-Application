import { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { fetchProducts, fetchFeaturedProducts } from '../../store/slices/productSlice'

// Components
import Hero from '../../components/common/Hero'
import ProductGrid from '../../components/product/ProductGrid'
import CategoryGrid from '../../components/category/CategoryGrid'
import LoadingSpinner from '../../components/ui/LoadingSpinner'

const Home = () => {
  const dispatch = useDispatch()
  const { products, featuredProducts, loading, error } = useSelector((state) => state.product)

  useEffect(() => {
    dispatch(fetchProducts({ limit: 8 }))
    dispatch(fetchFeaturedProducts())
  }, [dispatch])

  if (loading && products.length === 0) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <LoadingSpinner size="lg" />
      </div>
    )
  }

  return (
    <div className="min-h-screen">
      {/* Hero Section */}
      <Hero />

      {/* Featured Products */}
      <section className="py-16 bg-gray-50">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-12">
            <h2 className="text-3xl font-bold text-gray-900 mb-4">
              Featured Products
            </h2>
            <p className="text-gray-600 max-w-2xl mx-auto">
              Discover our handpicked selection of premium products
            </p>
          </div>
          
          <ProductGrid 
            products={featuredProducts.length > 0 ? featuredProducts : products.slice(0, 4)} 
            loading={loading}
            error={error}
          />
        </div>
      </section>

      {/* Categories */}
      <section className="py-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-12">
            <h2 className="text-3xl font-bold text-gray-900 mb-4">
              Shop by Category
            </h2>
            <p className="text-gray-600 max-w-2xl mx-auto">
              Browse our wide range of product categories
            </p>
          </div>
          
          <CategoryGrid />
        </div>
      </section>

      {/* Recent Products */}
      <section className="py-16 bg-gray-50">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-12">
            <h2 className="text-3xl font-bold text-gray-900 mb-4">
              New Arrivals
            </h2>
            <p className="text-gray-600 max-w-2xl mx-auto">
              Check out our latest products
            </p>
          </div>
          
          <ProductGrid 
            products={products.slice(4, 8)} 
            loading={loading}
            error={error}
          />
        </div>
      </section>
    </div>
  )
}

export default Home
