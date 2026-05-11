import React from 'react'
import { Link } from 'react-router-dom'

const CategoryGrid = () => {
  const categories = [
    {
      id: 1,
      name: 'Electronics',
      slug: 'electronics',
      image: 'https://via.placeholder.com/300x200/4F46E5/FFFFFF?text=Electronics',
      description: 'Latest gadgets and devices'
    },
    {
      id: 2,
      name: 'Clothing',
      slug: 'clothing',
      image: 'https://via.placeholder.com/300x200/10B981/FFFFFF?text=Clothing',
      description: 'Fashion for everyone'
    },
    {
      id: 3,
      name: 'Books',
      slug: 'books',
      image: 'https://via.placeholder.com/300x200/F59E0B/FFFFFF?text=Books',
      description: 'Books and literature'
    },
    {
      id: 4,
      name: 'Home & Garden',
      slug: 'home-garden',
      image: 'https://via.placeholder.com/300x300/8B5CF6/FFFFFF?text=Home+%26+Garden',
      description: 'Home improvement items'
    },
    {
      id: 5,
      name: 'Sports & Outdoors',
      slug: 'sports-outdoors',
      image: 'https://via.placeholder.com/300x200/EF4444/FFFFFF?text=Sports+%26+Outdoors',
      description: 'Sports equipment'
    },
    {
      id: 6,
      name: 'Toys & Games',
      slug: 'toys-games',
      image: 'https://via.placeholder.com/300x200/06B6D4/FFFFFF?text=Toys+%26+Games',
      description: 'Fun for all ages'
    }
  ]

  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
      {categories.map((category) => (
        <Link
          key={category.id}
          to={`/products?category=${category.slug}`}
          className="group block"
        >
          <div className="card overflow-hidden hover:shadow-lg transition-shadow duration-300">
            <div className="relative h-48 overflow-hidden">
              <img
                src={category.image}
                alt={category.name}
                className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
              />
              <div className="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent"></div>
              <div className="absolute bottom-0 left-0 right-0 p-4">
                <h3 className="text-white text-xl font-bold mb-1">
                  {category.name}
                </h3>
                <p className="text-white/90 text-sm">
                  {category.description}
                </p>
              </div>
            </div>
          </div>
        </Link>
      ))}
    </div>
  )
}

export default CategoryGrid
