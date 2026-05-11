import React, { useEffect, useState } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { useSearchParams } from 'react-router-dom'
import { fetchProducts, setFilters, clearFilters } from '../../store/slices/productSlice'

// Components
import ProductGrid from '../../components/product/ProductGrid'
import LoadingSpinner from '../../components/ui/LoadingSpinner'
import Button from '../../components/ui/Button'
import Input from '../../components/ui/Input'

const Products = () => {
  const dispatch = useDispatch()
  const [searchParams] = useSearchParams()
  const { products, loading, error, filters, pagination } = useSelector((state) => state.product)
  
  const [priceRange, setPriceRange] = useState([0, 10000])
  const [selectedCategory, setSelectedCategory] = useState('')
  const [sortBy, setSortBy] = useState('createdAt')
  const [sortOrder, setSortOrder] = useState('desc')

  useEffect(() => {
    const category = searchParams.get('category')
    const query = searchParams.get('q')
    
    const params = {
      page: 1,
      limit: 12,
      category: category || '',
      search: query || '',
      minPrice: priceRange[0],
      maxPrice: priceRange[1],
      sortBy,
      sortOrder,
    }
    
    dispatch(fetchProducts(params))
    
    if (category) {
      setSelectedCategory(category)
    }
  }, [searchParams, dispatch, priceRange, sortBy, sortOrder])

  const handleFilterChange = (newFilters) => {
    dispatch(setFilters(newFilters))
    const params = {
      ...filters,
      ...newFilters,
      page: 1,
    }
    dispatch(fetchProducts(params))
  }

  const handleSortChange = (field) => {
    const newOrder = sortBy === field && sortOrder === 'desc' ? 'asc' : 'desc'
    setSortBy(field)
    setSortOrder(newOrder)
    handleFilterChange({ sortBy: field, sortOrder: newOrder })
  }

  const handlePriceRangeChange = (range) => {
    setPriceRange(range)
    handleFilterChange({ minPrice: range[0], maxPrice: range[1] })
  }

  const handleClearFilters = () => {
    setPriceRange([0, 10000])
    setSelectedCategory('')
    setSortBy('createdAt')
    setSortOrder('desc')
    dispatch(clearFilters())
    dispatch(fetchProducts({ page: 1, limit: 12 }))
  }

  const categories = [
    { id: 'electronics', name: 'Electronics' },
    { id: 'clothing', name: 'Clothing' },
    { id: 'books', name: 'Books' },
    { id: 'home-garden', name: 'Home & Garden' },
    { id: 'sports-outdoors', name: 'Sports & Outdoors' },
    { id: 'toys-games', name: 'Toys & Games' },
  ]

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="flex flex-col lg:flex-row gap-8">
        {/* Filters Sidebar */}
        <div className="w-full lg:w-64 flex-shrink-0">
          <div className="bg-white rounded-lg shadow-md p-6">
            <div className="flex justify-between items-center mb-6">
              <h2 className="text-lg font-semibold">Filters</h2>
              <Button
                variant="ghost"
                size="sm"
                onClick={handleClearFilters}
              >
                Clear
              </Button>
            </div>

            {/* Categories */}
            <div className="mb-6">
              <h3 className="font-medium mb-3">Categories</h3>
              <div className="space-y-2">
                {categories.map((category) => (
                  <label key={category.id} className="flex items-center">
                    <input
                      type="radio"
                      name="category"
                      value={category.id}
                      checked={selectedCategory === category.id}
                      onChange={(e) => {
                        setSelectedCategory(e.target.value)
                        handleFilterChange({ category: e.target.value })
                      }}
                      className="mr-2"
                    />
                    <span className="text-sm">{category.name}</span>
                  </label>
                ))}
              </div>
            </div>

            {/* Price Range */}
            <div className="mb-6">
              <h3 className="font-medium mb-3">Price Range</h3>
              <div className="space-y-3">
                <div className="flex items-center space-x-2">
                  <Input
                    type="number"
                    placeholder="Min"
                    value={priceRange[0]}
                    onChange={(e) => {
                      const newRange = [parseInt(e.target.value) || 0, priceRange[1]]
                      setPriceRange(newRange)
                      handlePriceRangeChange(newRange)
                    }}
                    className="w-full"
                  />
                  <span className="text-gray-500">-</span>
                  <Input
                    type="number"
                    placeholder="Max"
                    value={priceRange[1]}
                    onChange={(e) => {
                      const newRange = [priceRange[0], parseInt(e.target.value) || 10000]
                      setPriceRange(newRange)
                      handlePriceRangeChange(newRange)
                    }}
                    className="w-full"
                  />
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Products Grid */}
        <div className="flex-1">
          {/* Header */}
          <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center mb-6">
            <div>
              <h1 className="text-2xl font-bold text-gray-900">Products</h1>
              <p className="text-gray-600 mt-1">
                {pagination.total} products found
              </p>
            </div>
            
            {/* Sort Options */}
            <div className="mt-4 sm:mt-0">
              <select
                value={`${sortBy}-${sortOrder}`}
                onChange={(e) => {
                  const [field, order] = e.target.value.split('-')
                  handleSortChange(field)
                }}
                className="border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-primary"
              >
                <option value="createdAt-desc">Newest First</option>
                <option value="createdAt-asc">Oldest First</option>
                <option value="price-asc">Price: Low to High</option>
                <option value="price-desc">Price: High to Low</option>
                <option value="name-asc">Name: A to Z</option>
                <option value="name-desc">Name: Z to A</option>
              </select>
            </div>
          </div>

          {/* Products */}
          {loading && products.length === 0 ? (
            <div className="flex justify-center py-12">
              <LoadingSpinner size="lg" />
            </div>
          ) : error ? (
            <div className="text-center py-12">
              <p className="text-red-500 mb-4">{error}</p>
              <Button onClick={() => window.location.reload()}>
                Try Again
              </Button>
            </div>
          ) : (
            <>
              <ProductGrid products={products} loading={loading} error={error} />
              
              {/* Pagination */}
              {pagination.totalPages > 1 && (
                <div className="flex justify-center mt-8">
                  <div className="flex space-x-2">
                    <Button
                      variant="outline"
                      disabled={pagination.page === 1}
                      onClick={() => {
                        const newPage = pagination.page - 1
                        dispatch(fetchProducts({ ...filters, page: newPage }))
                      }}
                    >
                      Previous
                    </Button>
                    
                    <span className="flex items-center px-4 py-2">
                      Page {pagination.page} of {pagination.totalPages}
                    </span>
                    
                    <Button
                      variant="outline"
                      disabled={pagination.page === pagination.totalPages}
                      onClick={() => {
                        const newPage = pagination.page + 1
                        dispatch(fetchProducts({ ...filters, page: newPage }))
                      }}
                    >
                      Next
                    </Button>
                  </div>
                </div>
              )}
            </>
          )}
        </div>
      </div>
    </div>
  )
}

export default Products
