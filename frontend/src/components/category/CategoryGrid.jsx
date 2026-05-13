import React from 'react'
import { Link } from 'react-router-dom'

const CategoryGrid = () => {
  const categories = [
    {
      id: 1,
      name: 'Chia Seeds',
      slug: 'chia-seeds',
      image: 'https://via.placeholder.com/300x200/10B981/FFFFFF?text=Chia+Seeds',
      description: 'Premium organic chia seeds for nutrition'
    },
    {
      id: 2,
      name: 'Pumpkin Seeds',
      slug: 'pumpkin-seeds',
      image: 'https://via.placeholder.com/300x200/F59E0B/FFFFFF?text=Pumpkin+Seeds',
      description: 'Roasted pumpkin seeds rich in minerals'
    },
    {
      id: 3,
      name: 'Sunflower Seeds',
      slug: 'sunflower-seeds',
      image: 'https://via.placeholder.com/300x200/EF4444/FFFFFF?text=Sunflower+Seeds',
      description: 'Nutritious sunflower seeds and kernels'
    },
    {
      id: 4,
      name: 'Sesame Seeds',
      slug: 'sesame-seeds',
      image: 'https://via.placeholder.com/300x200/8B5CF6/FFFFFF?text=Sesame+Seeds',
      description: 'High-quality sesame seeds for cooking'
    },
    {
      id: 5,
      name: 'Watermelon Seeds',
      slug: 'watermelon-seeds',
      image: 'https://via.placeholder.com/300x200/06B6D4/FFFFFF?text=Watermelon+Seeds',
      description: 'Premium watermelon seeds for gardening'
    },
    {
      id: 6,
      name: 'Flax Seeds',
      slug: 'flax-seeds',
      image: 'https://via.placeholder.com/300x200/4F46E5/FFFFFF?text=Flax+Seeds',
      description: 'Omega-3 rich flax seeds and ground flax'
    },
    {
      id: 7,
      name: 'Mixed Seeds',
      slug: 'mixed-seeds',
      image: 'https://via.placeholder.com/300x200/059669/FFFFFF?text=Mixed+Seeds',
      description: 'Nutritious seed blends and trail mixes'
    },
    {
      id: 8,
      name: 'Exotic Seeds',
      slug: 'exotic-seeds',
      image: 'https://via.placeholder.com/300x200/DC2626/FFFFFF?text=Exotic+Seeds',
      description: 'Rare and exotic seed varieties'
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
