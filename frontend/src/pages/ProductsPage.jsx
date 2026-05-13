import React, { useState, useEffect } from 'react';
import { Link, useSearchParams } from 'react-router-dom';
import { Search, ShoppingCart, Filter, Grid3X3 } from 'lucide-react';

const ProductsPage = () => {
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchParams] = useSearchParams();
  
  const searchTerm = searchParams.get('search') || '';
  const categoryFilter = searchParams.get('category') || '';
  const sortBy = searchParams.get('sort') || 'featured';
  const [priceRange, setPriceRange] = useState({ min: '', max: '' });

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        setLoading(true);
        let url = `${import.meta.env.VITE_API_BASE_URL}/products?`;
        
        if (searchTerm) {
          url += `search=${encodeURIComponent(searchTerm)}&`;
        }
        if (categoryFilter) {
          url += `category=${encodeURIComponent(categoryFilter)}&`;
        }
        if (sortBy) {
          url += `sort=${encodeURIComponent(sortBy)}&`;
        }
        if (priceRange.min) {
          url += `minPrice=${priceRange.min}&`;
        }
        if (priceRange.max) {
          url += `maxPrice=${priceRange.max}`;
        }

        const response = await fetch(url);
        const data = await response.json();
        setProducts(data.data || []);
      } catch (error) {
        console.error('Error fetching products:', error);
      } finally {
        setLoading(false);
      }
    };

    const fetchCategories = async () => {
      try {
        const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/categories`);
        const data = await response.json();
        setCategories(data.data || []);
      } catch (error) {
        console.error('Error fetching categories:', error);
      }
    };

    fetchProducts();
    fetchCategories();
  }, [searchTerm, categoryFilter, sortBy, priceRange]);

  const handleSearch = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const newSearchParams = new URLSearchParams();
    
    if (formData.get('search')) {
      newSearchParams.set('search', formData.get('search'));
    } else {
      newSearchParams.delete('search');
    }
    
    window.location.search = newSearchParams.toString();
  };

  const handleFilter = (type, value) => {
    const newSearchParams = new URLSearchParams(window.location.search);
    
    if (value) {
      newSearchParams.set(type, value);
    } else {
      newSearchParams.delete(type);
    }
    
    window.location.search = newSearchParams.toString();
  };

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <div className="bg-white shadow-sm">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between h-16">
            <div className="flex items-center space-x-4">
              <Link to="/" className="text-2xl font-bold text-green-600 hover:text-green-700">
                Beej Seeds
              </Link>
            </div>
            
            <div className="flex items-center space-x-4">
              <form onSubmit={handleSearch} className="flex items-center space-x-2">
                <div className="relative">
                  <input
                    type="text"
                    name="search"
                    placeholder="Search products..."
                    defaultValue={searchTerm}
                    className="w-64 px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-transparent"
                  />
                  <button
                    type="submit"
                    className="absolute right-2 top-1/2 p-2 text-gray-600 hover:text-green-600"
                  >
                    <Search className="h-5 w-5" />
                  </button>
                </div>
              </form>
              
              <Link to="/cart" className="relative p-2 text-gray-600 hover:text-green-600">
                <ShoppingCart className="h-6 w-6" />
                {products.length > 0 && (
                  <span className="absolute -top-1 -right-1 bg-red-500 text-white text-xs rounded-full h-5 w-5 flex items-center justify-center">
                    {products.length}
                  </span>
                )}
              </Link>
            </div>
          </div>
        </div>
      </div>

      {/* Filters and Results */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="flex flex-col lg:flex-row gap-8">
          {/* Sidebar Filters */}
          <div className="lg:w-1/4">
            <div className="bg-white rounded-lg shadow-md p-6">
              <h3 className="text-lg font-semibold text-gray-900 mb-4">
                <Filter className="inline h-5 w-5 mr-2" />
                Filters
              </h3>
              
              {/* Categories */}
              <div className="mb-6">
                <h4 className="text-sm font-medium text-gray-700 mb-3">Categories</h4>
                <div className="space-y-2">
                  <button
                    onClick={() => handleFilter('category', '')}
                    className={`w-full text-left px-3 py-2 rounded-md text-sm ${!categoryFilter ? 'bg-green-100 text-green-700' : 'bg-gray-100 text-gray-700'}`}
                  >
                    All Categories
                  </button>
                  {categories.map((category) => (
                    <button
                      key={category.id}
                      onClick={() => handleFilter('category', category.slug)}
                      className={`w-full text-left px-3 py-2 rounded-md text-sm ${categoryFilter === category.slug ? 'bg-green-100 text-green-700' : 'bg-gray-100 text-gray-700'}`}
                    >
                      {category.name}
                    </button>
                  ))}
                </div>
              </div>

              {/* Price Range */}
              <div className="mb-6">
                <h4 className="text-sm font-medium text-gray-700 mb-3">Price Range</h4>
                <div className="space-y-3">
                  <input
                    type="number"
                    placeholder="Min Price"
                    value={priceRange.min}
                    onChange={(e) => setPriceRange(prev => ({ ...prev, min: e.target.value }))}
                    className="w-full px-3 py-2 border border-gray-300 rounded-md text-sm"
                  />
                  <input
                    type="number"
                    placeholder="Max Price"
                    value={priceRange.max}
                    onChange={(e) => setPriceRange(prev => ({ ...prev, max: e.target.value }))}
                    className="w-full px-3 py-2 border border-gray-300 rounded-md text-sm"
                  />
                </div>
              </div>

              {/* Sort */}
              <div className="mb-6">
                <h4 className="text-sm font-medium text-gray-700 mb-3">Sort By</h4>
                <select
                  value={sortBy}
                  onChange={(e) => handleFilter('sort', e.target.value)}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md text-sm"
                >
                  <option value="featured">Featured</option>
                  <option value="price-low">Price: Low to High</option>
                  <option value="price-high">Price: High to Low</option>
                  <option value="name">Name: A-Z</option>
                  <option value="rating">Highest Rated</option>
                </select>
              </div>
            </div>
          </div>

          {/* Products Grid */}
          <div className="lg:w-3/4">
            <div className="flex items-center justify-between mb-6">
              <h2 className="text-2xl font-bold text-gray-900">
                {searchTerm ? `Search Results: "${searchTerm}"` : 'All Products'}
              </h2>
              <p className="text-gray-600">
                {products.length} products found
              </p>
            </div>

            {loading ? (
              <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
                {[...Array(6)].map((_, index) => (
                  <div key={index} className="bg-white rounded-lg shadow-md p-4">
                    <div className="bg-gray-200 h-48 rounded-md mb-4 animate-pulse"></div>
                    <div className="h-4 bg-gray-200 rounded mb-2"></div>
                    <div className="h-4 bg-gray-200 rounded mb-2"></div>
                  </div>
                ))}
              </div>
            ) : products.length > 0 ? (
              <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
                {products.map((product) => (
                  <Link
                    key={product.id}
                    to={`/products/${product.id}`}
                    className="bg-white rounded-lg shadow-md hover:shadow-lg transition-shadow p-4 group"
                  >
                    <div className="relative">
                      {product.isFeatured && (
                        <div className="absolute top-2 right-2 bg-red-500 text-white px-2 py-1 rounded text-xs font-semibold">
                          Featured
                        </div>
                      )}
                      <div className="aspect-w-1 aspect-h-1 bg-gray-200 rounded-md mb-4 group-hover:bg-gray-100"></div>
                      <h3 className="text-lg font-semibold text-gray-900 mb-2">
                        {product.name}
                      </h3>
                      <p className="text-gray-600 text-sm mb-3 line-clamp-2">
                        {product.shortDescription}
                      </p>
                      <div className="flex items-center justify-between">
                        <span className="text-2xl font-bold text-green-600">
                          ${product.price}
                        </span>
                        <div className="flex items-center">
                          <Grid3X3 className="h-4 w-4 text-yellow-400" />
                          <span className="text-sm text-gray-600 ml-1">
                            {product.rating || '4.5'}
                          </span>
                        </div>
                      </div>
                      <div className="flex items-center space-x-2 mt-3">
                        <span className="text-xs bg-green-100 text-green-800 px-2 py-1 rounded">
                          {product.category}
                        </span>
                        <span className="text-xs bg-blue-100 text-blue-800 px-2 py-1 rounded">
                          In Stock: {product.stockQuantity}
                        </span>
                      </div>
                    </div>
                  </Link>
                ))}
              </div>
            ) : (
              <div className="text-center py-12">
                <Grid3X3 className="h-16 w-16 text-gray-400 mx-auto mb-4" />
                <h3 className="text-xl font-semibold text-gray-900 mb-2">
                  No products found
                </h3>
                <p className="text-gray-600 mb-4">
                  Try adjusting your filters or search terms
                </p>
                <button
                  onClick={() => window.location.href = '/products'}
                  className="bg-green-600 text-white px-6 py-3 rounded-lg font-semibold hover:bg-green-700"
                >
                  Clear Filters
                </button>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductsPage;
