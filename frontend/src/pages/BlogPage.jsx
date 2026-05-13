import React, { useState } from 'react'
import { motion } from 'framer-motion'
import { Calendar, Clock, User, Heart, MessageCircle, Share2, Search, Filter } from 'lucide-react'
import { Link } from 'react-router-dom'
import Button from '../components/ui/Button'
import Input from '../components/ui/Input'
import Badge from '../components/ui/Badge'

const BlogPage = () => {
  const [searchTerm, setSearchTerm] = useState('')
  const [selectedCategory, setSelectedCategory] = useState('all')

  const categories = [
    { id: 'all', name: 'All Articles', count: 24 },
    { id: 'nutrition', name: 'Nutrition', count: 8 },
    { id: 'weight-loss', name: 'Weight Loss', count: 6 },
    { id: 'recipes', name: 'Recipes', count: 5 },
    { id: 'health-tips', name: 'Health Tips', count: 5 }
  ]

  const blogPosts = [
    {
      id: 1,
      title: 'The Ultimate Guide to Chia Seeds: Benefits and Uses',
      excerpt: 'Discover why chia seeds are considered a superfood and learn creative ways to incorporate them into your daily diet.',
      category: 'nutrition',
      author: 'Dr. Priya Sharma',
      date: '2024-01-15',
      readTime: '5 min read',
      image: '🌱',
      likes: 234,
      comments: 18,
      featured: true
    },
    {
      id: 2,
      title: '10 Delicious Recipes with Flax Seeds for Weight Loss',
      excerpt: 'Transform your meals with these healthy and tasty flax seed recipes that support your weight loss journey.',
      category: 'recipes',
      author: 'Chef Rahul Verma',
      date: '2024-01-12',
      readTime: '8 min read',
      image: '🥗',
      likes: 189,
      comments: 12
    },
    {
      id: 3,
      title: 'How Sabja Seeds Can Help with Summer Hydration',
      excerpt: 'Stay cool and hydrated this summer with the amazing benefits of sabja seeds. Learn the science behind it.',
      category: 'health-tips',
      author: 'Nutritionist Anita Patel',
      date: '2024-01-10',
      readTime: '4 min read',
      image: '💧',
      likes: 156,
      comments: 8
    },
    {
      id: 4,
      title: 'Pumpkin Seeds: The Underrated Superfood You Need',
      excerpt: 'Uncover the hidden benefits of pumpkin seeds and why they should be part of your daily nutrition.',
      category: 'nutrition',
      author: 'Dr. Sameer Kumar',
      date: '2024-01-08',
      readTime: '6 min read',
      image: '🎃',
      likes: 201,
      comments: 15
    },
    {
      id: 5,
      title: 'Morning Routine: Starting Your Day with Seeds',
      excerpt: 'Build the perfect morning routine with seed-based nutrition that energizes you throughout the day.',
      category: 'health-tips',
      author: 'Wellness Coach Meera',
      date: '2024-01-05',
      readTime: '7 min read',
      image: '☀️',
      likes: 178,
      comments: 11
    },
    {
      id: 6,
      title: 'The Science Behind Seed-Based Diets for Weight Management',
      excerpt: 'Understanding how different seeds contribute to effective and sustainable weight management.',
      category: 'weight-loss',
      author: 'Dr. Rajesh Malhotra',
      date: '2024-01-03',
      readTime: '10 min read',
      image: '🔬',
      likes: 267,
      comments: 22
    }
  ]

  const filteredPosts = blogPosts.filter(post => {
    const matchesSearch = post.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         post.excerpt.toLowerCase().includes(searchTerm.toLowerCase())
    const matchesCategory = selectedCategory === 'all' || post.category === selectedCategory
    return matchesSearch && matchesCategory
  })

  const featuredPost = blogPosts.find(post => post.featured)

  return (
    <div className="min-h-screen bg-beej-beige">
      {/* Hero Section */}
      <motion.section 
        className="bg-gradient-to-br from-beej-green to-beej-green-light text-white py-20"
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ duration: 0.8 }}
      >
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <motion.div 
            className="text-center"
            initial={{ y: 20, opacity: 0 }}
            animate={{ y: 0, opacity: 1 }}
            transition={{ duration: 0.6, delay: 0.2 }}
          >
            <Badge variant="secondary" className="mb-6">Health & Wellness Blog</Badge>
            <h1 className="text-4xl md:text-6xl font-bold mb-6">
              Seeds of Wisdom
            </h1>
            <p className="text-xl md:text-2xl text-beej-beige/90 max-w-3xl mx-auto leading-relaxed">
              Expert insights, recipes, and tips to help you make the most of nature's superfoods
            </p>
          </motion.div>
        </div>
      </motion.section>

      {/* Search and Filter */}
      <section className="py-12">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="bg-white rounded-2xl p-6 shadow-lg">
            <div className="grid md:grid-cols-2 gap-6">
              <div className="relative">
                <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-beej-brown/50" />
                <Input
                  placeholder="Search articles..."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  className="pl-10"
                />
              </div>
              <div className="flex items-center space-x-4">
                <Filter className="h-5 w-5 text-beej-brown" />
                <div className="flex flex-wrap gap-2">
                  {categories.map((category) => (
                    <button
                      key={category.id}
                      onClick={() => setSelectedCategory(category.id)}
                      className={`px-4 py-2 rounded-full text-sm font-medium transition-colors ${
                        selectedCategory === category.id
                          ? 'bg-beej-green text-white'
                          : 'bg-beej-beige text-beej-brown hover:bg-beej-green/10'
                      }`}
                    >
                      {category.name} ({category.count})
                    </button>
                  ))}
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Featured Article */}
      {featuredPost && (
        <section className="py-12">
          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.6 }}
            >
              <Badge variant="secondary" className="mb-4">Featured Article</Badge>
              <div className="bg-white rounded-2xl overflow-hidden shadow-xl hover:shadow-2xl transition-shadow duration-300">
                <div className="md:flex">
                  <div className="md:w-1/2 bg-gradient-to-br from-beej-green to-beej-green-light p-12 flex items-center justify-center">
                    <div className="text-8xl">{featuredPost.image}</div>
                  </div>
                  <div className="md:w-1/2 p-8">
                    <div className="flex items-center space-x-4 mb-4">
                      <Badge variant="secondary">{featuredPost.category}</Badge>
                      <div className="flex items-center text-beej-brown/60 text-sm">
                        <Clock className="h-4 w-4 mr-1" />
                        {featuredPost.readTime}
                      </div>
                    </div>
                    <h2 className="text-2xl md:text-3xl font-bold text-beej-green mb-4">
                      {featuredPost.title}
                    </h2>
                    <p className="text-beej-brown/70 mb-6 leading-relaxed">
                      {featuredPost.excerpt}
                    </p>
                    <div className="flex items-center justify-between mb-6">
                      <div className="flex items-center space-x-4">
                        <div className="flex items-center text-beej-brown/60 text-sm">
                          <User className="h-4 w-4 mr-1" />
                          {featuredPost.author}
                        </div>
                        <div className="flex items-center text-beej-brown/60 text-sm">
                          <Calendar className="h-4 w-4 mr-1" />
                          {new Date(featuredPost.date).toLocaleDateString()}
                        </div>
                      </div>
                      <div className="flex items-center space-x-3">
                        <span className="flex items-center text-beej-brown/60 text-sm">
                          <Heart className="h-4 w-4 mr-1" />
                          {featuredPost.likes}
                        </span>
                        <span className="flex items-center text-beej-brown/60 text-sm">
                          <MessageCircle className="h-4 w-4 mr-1" />
                          {featuredPost.comments}
                        </span>
                      </div>
                    </div>
                    <Button variant="primary" className="w-full">
                      Read Full Article
                    </Button>
                  </div>
                </div>
              </div>
            </motion.div>
          </div>
        </section>
      )}

      {/* Blog Posts Grid */}
      <section className="py-12">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-8">
            {filteredPosts.map((post, index) => (
              <motion.div
                key={post.id}
                initial={{ opacity: 0, y: 20 }}
                whileInView={{ opacity: 1, y: 0 }}
                viewport={{ once: true }}
                transition={{ duration: 0.6, delay: index * 0.1 }}
              >
                <div className="bg-white rounded-2xl overflow-hidden shadow-lg hover:shadow-xl transition-all duration-300 hover:-translate-y-2">
                  <div className="h-48 bg-gradient-to-br from-beej-green to-beej-green-light flex items-center justify-center">
                    <div className="text-6xl">{post.image}</div>
                  </div>
                  <div className="p-6">
                    <div className="flex items-center justify-between mb-3">
                      <Badge variant="secondary" className="text-xs">
                        {post.category}
                      </Badge>
                      <div className="flex items-center text-beej-brown/60 text-xs">
                        <Clock className="h-3 w-3 mr-1" />
                        {post.readTime}
                      </div>
                    </div>
                    <h3 className="text-xl font-semibold text-beej-brown mb-3 line-clamp-2">
                      {post.title}
                    </h3>
                    <p className="text-beej-brown/60 text-sm mb-4 line-clamp-3">
                      {post.excerpt}
                    </p>
                    <div className="flex items-center justify-between mb-4">
                      <div className="flex items-center text-beej-brown/60 text-xs">
                        <User className="h-3 w-3 mr-1" />
                        {post.author}
                      </div>
                      <div className="flex items-center text-beej-brown/60 text-xs">
                        <Calendar className="h-3 w-3 mr-1" />
                        {new Date(post.date).toLocaleDateString()}
                      </div>
                    </div>
                    <div className="flex items-center justify-between">
                      <div className="flex items-center space-x-3">
                        <button className="flex items-center text-beej-brown/60 hover:text-red-500 transition-colors">
                          <Heart className="h-4 w-4 mr-1" />
                          <span className="text-xs">{post.likes}</span>
                        </button>
                        <span className="flex items-center text-beej-brown/60 text-xs">
                          <MessageCircle className="h-4 w-4 mr-1" />
                          {post.comments}
                        </span>
                        <button className="flex items-center text-beej-brown/60 hover:text-beej-green transition-colors">
                          <Share2 className="h-4 w-4" />
                        </button>
                      </div>
                      <Button variant="outline" size="sm">
                        Read More
                      </Button>
                    </div>
                  </div>
                </div>
              </motion.div>
            ))}
          </div>

          {filteredPosts.length === 0 && (
            <div className="text-center py-12">
              <div className="text-6xl mb-4">🔍</div>
              <h3 className="text-xl font-semibold text-beej-brown mb-2">
                No articles found
              </h3>
              <p className="text-beej-brown/60">
                Try adjusting your search or filter criteria
              </p>
            </div>
          )}
        </div>
      </section>

      {/* Newsletter Section */}
      <section className="py-20 bg-gradient-to-r from-beej-green to-beej-green-light text-white">
        <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
            transition={{ duration: 0.6 }}
            className="space-y-8"
          >
            <h2 className="text-4xl md:text-5xl font-bold">
              Stay Updated
            </h2>
            <p className="text-xl text-white/90 max-w-2xl mx-auto">
              Get the latest health tips, recipes, and exclusive offers delivered to your inbox
            </p>
            <div className="flex flex-col sm:flex-row gap-4 max-w-md mx-auto">
              <Input
                placeholder="Enter your email"
                className="bg-white/20 border-white/30 text-white placeholder-white/70"
              />
              <Button variant="secondary" className="bg-white text-beej-green hover:bg-beej-beige">
                Subscribe
              </Button>
            </div>
          </motion.div>
        </div>
      </section>
    </div>
  )
}

export default BlogPage
