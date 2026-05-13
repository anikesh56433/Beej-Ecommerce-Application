import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { motion, useScroll, useTransform } from 'framer-motion';
import { 
  ShoppingCart, 
  Search, 
  Star, 
  Heart, 
  TrendingUp, 
  Leaf, 
  Shield, 
  Award,
  ChevronRight,
  Sparkles,
  Package,
  Truck,
  RefreshCw
} from 'lucide-react';
import { useSelector, useDispatch } from 'react-redux';
import { fetchFeaturedProducts, fetchTrendingProducts } from '../store/slices/productSlice';
import { ProductCardSkeleton } from '../components/ui/SkeletonLoader';
import Badge from '../components/ui/Badge';
import Button from '../components/ui/Button';

const HomePage = () => {
  const dispatch = useDispatch();
  const { featuredProducts, trendingProducts, loading } = useSelector((state) => state.product);
  const { scrollY } = useScroll();
  
  const y = useTransform(scrollY, [0, 300], [0, -50]);
  const opacity = useTransform(scrollY, [0, 300], [1, 0]);

  useEffect(() => {
    dispatch(fetchFeaturedProducts());
    dispatch(fetchTrendingProducts());
  }, [dispatch]);

  // Mock data for demonstration
  const mockCategories = [
    { id: 1, name: 'Chia Seeds', slug: 'chia-seeds', icon: '🌱', description: 'Omega-3 rich superfood' },
    { id: 2, name: 'Flax Seeds', slug: 'flax-seeds', icon: '🌾', description: 'Fiber-packed nutrition' },
    { id: 3, name: 'Pumpkin Seeds', slug: 'pumpkin-seeds', icon: '🎃', description: 'Protein powerhouse' },
    { id: 4, name: 'Sunflower Seeds', slug: 'sunflower-seeds', icon: '🌻', description: 'Vitamin E rich' },
    { id: 5, name: 'Sabja Seeds', slug: 'sabja-seeds', icon: '💧', description: 'Cooling properties' },
    { id: 6, name: 'Sesame Seeds', slug: 'sesame-seeds', icon: '⚪', description: 'Calcium rich' },
  ];

  const benefits = [
    {
      icon: Leaf,
      title: '100% Natural',
      description: 'Pure, unprocessed seeds from organic farms',
      color: 'text-green-600',
      bgColor: 'bg-green-100'
    },
    {
      icon: Shield,
      title: 'No Chemicals',
      description: 'Free from pesticides and harmful additives',
      color: 'text-blue-600',
      bgColor: 'bg-blue-100'
    },
    {
      icon: Award,
      title: 'Premium Quality',
      description: 'Lab-tested for purity and nutrition',
      color: 'text-purple-600',
      bgColor: 'bg-purple-100'
    },
    {
      icon: Heart,
      title: 'Rich in Fiber',
      description: 'Supports digestive health and wellness',
      color: 'text-red-600',
      bgColor: 'bg-red-100'
    },
    {
      icon: TrendingUp,
      title: 'Omega-3 Rich',
      description: 'Essential fatty acids for brain health',
      color: 'text-indigo-600',
      bgColor: 'bg-indigo-100'
    },
    {
      icon: Sparkles,
      title: 'High Protein',
      description: 'Plant-based protein for muscle health',
      color: 'text-yellow-600',
      bgColor: 'bg-yellow-100'
    }
  ];

  const testimonials = [
    {
      id: 1,
      name: 'Priya Sharma',
      role: 'Nutritionist',
      content: 'BEEJ seeds have transformed my clients\' health journey. The quality is exceptional!',
      rating: 5,
      avatar: '👩‍⚕️'
    },
    {
      id: 2,
      name: 'Rahul Verma',
      role: 'Fitness Enthusiast',
      content: 'Best chia seeds I\'ve ever had. Perfect for my morning smoothies and protein bowls.',
      rating: 5,
      avatar: '👨‍💼'
    },
    {
      id: 3,
      name: 'Anita Patel',
      role: 'Yoga Instructor',
      content: 'The pumpkin seeds are amazing! Great energy source for my practice.',
      rating: 5,
      avatar: '👩‍🏫'
    }
  ];

  return (
    <div className="min-h-screen bg-beej-beige">
      {/* Hero Section */}
      <motion.section 
        className="relative overflow-hidden bg-gradient-to-br from-beej-green via-beej-green-light to-beej-green/90 text-white"
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ duration: 0.8 }}
      >
        {/* Animated Background Elements */}
        <div className="absolute inset-0 overflow-hidden">
          <motion.div
            className="absolute top-20 left-10 w-32 h-32 bg-white/10 rounded-full blur-xl"
            animate={{
              x: [0, 100, 0],
              y: [0, -50, 0],
            }}
            transition={{
              duration: 10,
              repeat: Infinity,
              repeatType: "reverse",
            }}
          />
          <motion.div
            className="absolute bottom-20 right-10 w-48 h-48 bg-white/5 rounded-full blur-2xl"
            animate={{
              x: [0, -100, 0],
              y: [0, 50, 0],
            }}
            transition={{
              duration: 15,
              repeat: Infinity,
              repeatType: "reverse",
            }}
          />
        </div>

        <div className="relative max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-24 lg:py-32">
          <motion.div 
            className="text-center space-y-8"
            initial={{ y: 20, opacity: 0 }}
            animate={{ y: 0, opacity: 1 }}
            transition={{ duration: 0.6, delay: 0.2 }}
          >
            <motion.div
              style={{ y, opacity }}
              className="space-y-6"
            >
              <Badge variant="secondary" className="inline-block">
                🌱 Premium Organic Seeds
              </Badge>
              
              <h1 className="text-4xl md:text-6xl lg:text-7xl font-bold leading-tight">
                <span className="block">Small Seeds.</span>
                <span className="block text-beej-beige">Big Impact.</span>
              </h1>
              
              <p className="text-xl md:text-2xl lg:text-3xl text-beej-beige/90 max-w-3xl mx-auto leading-relaxed">
                Discover nature's most powerful superfoods. 
                <span className="block font-semibold mt-2">Root. Rise. Refine.</span>
              </p>
            </motion.div>

            <motion.div 
              className="flex flex-col sm:flex-row gap-6 justify-center items-center pt-8"
              initial={{ y: 20, opacity: 0 }}
              animate={{ y: 0, opacity: 1 }}
              transition={{ duration: 0.6, delay: 0.4 }}
            >
              <Button 
                variant="premium" 
                size="lg"
                className="group"
              >
                <ShoppingCart className="mr-2 h-5 w-5" />
                Shop Now
                <ChevronRight className="ml-2 h-5 w-5 group-hover:translate-x-1 transition-transform" />
              </Button>
              <Button 
                variant="outline" 
                size="lg"
                className="border-beej-beige text-beej-beige hover:bg-beej-beige hover:text-beej-green"
              >
                <Search className="mr-2 h-5 w-5" />
                Explore Products
              </Button>
            </motion.div>

            {/* Trust Indicators */}
            <motion.div 
              className="grid grid-cols-2 md:grid-cols-4 gap-8 pt-12"
              initial={{ y: 20, opacity: 0 }}
              animate={{ y: 0, opacity: 1 }}
              transition={{ duration: 0.6, delay: 0.6 }}
            >
              {[
                { icon: Package, text: '10,000+ Orders', count: '10K+' },
                { icon: Star, text: '4.9 Rating', count: '4.9⭐' },
                { icon: Truck, text: 'Free Shipping', count: '🚚' },
                { icon: RefreshCw, text: '30-Day Returns', count: '🔄' }
              ].map((item, index) => (
                <div key={index} className="text-center">
                  <item.icon className="h-8 w-8 mx-auto mb-2 text-beej-beige" />
                  <div className="text-2xl font-bold text-beej-beige">{item.count}</div>
                  <div className="text-sm text-beej-beige/80">{item.text}</div>
                </div>
              ))}
            </motion.div>
          </motion.div>
        </div>
      </motion.section>

      {/* Featured Products */}
      <section className="py-20 bg-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <motion.div 
            className="text-center mb-16"
            initial={{ opacity: 0, y: 20 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
            transition={{ duration: 0.6 }}
          >
            <Badge variant="secondary" className="mb-4">Featured Products</Badge>
            <h2 className="text-4xl md:text-5xl font-bold text-beej-green mb-6">
              Premium Seed Selection
            </h2>
            <p className="text-xl text-beej-brown/70 max-w-2xl mx-auto">
              Hand-picked, nutritionally dense seeds to power your healthy lifestyle
            </p>
          </motion.div>

          {loading ? (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
              {[...Array(4)].map((_, index) => (
                <ProductCardSkeleton key={index} />
              ))}
            </div>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
              {featuredProducts.slice(0, 8).map((product, index) => (
                <motion.div
                  key={product.id || index}
                  initial={{ opacity: 0, y: 20 }}
                  whileInView={{ opacity: 1, y: 0 }}
                  viewport={{ once: true }}
                  transition={{ duration: 0.6, delay: index * 0.1 }}
                >
                  <Link to={`/products/${product.id || index}`}>
                    <div className="group bg-white rounded-2xl border border-beej-green/20 overflow-hidden hover:shadow-2xl transition-all duration-300 hover:-translate-y-2">
                      <div className="relative">
                        <div className="aspect-square bg-beej-beige/30 flex items-center justify-center">
                          <Leaf className="h-16 w-16 text-beej-green/30" />
                        </div>
                        <Badge variant="discount" className="absolute top-4 left-4">
                          -20%
                        </Badge>
                        <div className="absolute top-4 right-4 opacity-0 group-hover:opacity-100 transition-opacity">
                          <Heart className="h-6 w-6 text-white bg-red-500 p-1.5 rounded-full cursor-pointer hover:bg-red-600 transition-colors" />
                        </div>
                      </div>
                      <div className="p-6">
                        <h3 className="text-lg font-semibold text-beej-brown mb-2 group-hover:text-beej-green transition-colors">
                          {product.name || 'Premium Chia Seeds'}
                        </h3>
                        <p className="text-beej-brown/60 text-sm mb-4 line-clamp-2">
                          {product.description || 'Rich in omega-3 and fiber, perfect for healthy breakfast'}
                        </p>
                        <div className="flex items-center justify-between mb-4">
                          <div className="flex items-center space-x-1">
                            {[...Array(5)].map((_, i) => (
                              <Star key={i} className="h-4 w-4 fill-yellow-400 text-yellow-400" />
                            ))}
                            <span className="text-sm text-beej-brown/60 ml-1">(4.8)</span>
                          </div>
                        </div>
                        <div className="flex items-center justify-between">
                          <div>
                            <span className="text-2xl font-bold text-beej-green">
                              ${product.price || '29.99'}
                            </span>
                            <span className="text-sm text-beej-brown/50 line-through ml-2">
                              ${product.originalPrice || '39.99'}
                            </span>
                          </div>
                          <Button size="sm" variant="primary">
                            Add to Cart
                          </Button>
                        </div>
                      </div>
                    </div>
                  </Link>
                </motion.div>
              ))}
            </div>
          )}

          <div className="text-center mt-12">
            <Button variant="outline" size="lg">
              View All Products
              <ChevronRight className="ml-2 h-5 w-5" />
            </Button>
          </div>
        </div>
      </section>

      {/* Categories */}
      <section className="py-20 bg-beej-beige/30">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <motion.div 
            className="text-center mb-16"
            initial={{ opacity: 0, y: 20 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
            transition={{ duration: 0.6 }}
          >
            <Badge variant="secondary" className="mb-4">Shop by Category</Badge>
            <h2 className="text-4xl md:text-5xl font-bold text-beej-green mb-6">
              Explore Our Collections
            </h2>
            <p className="text-xl text-beej-brown/70 max-w-2xl mx-auto">
              Find the perfect seeds for your health goals and lifestyle
            </p>
          </motion.div>

          <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-6">
            {mockCategories.map((category, index) => (
              <motion.div
                key={category.id}
                initial={{ opacity: 0, scale: 0.9 }}
                whileInView={{ opacity: 1, scale: 1 }}
                viewport={{ once: true }}
                transition={{ duration: 0.6, delay: index * 0.1 }}
              >
                <Link 
                  to={`/categories/${category.slug}`}
                  className="group bg-white rounded-2xl p-6 text-center hover:shadow-xl transition-all duration-300 hover:-translate-y-2 border border-beej-green/20"
                >
                  <div className="text-4xl mb-4 group-hover:scale-110 transition-transform duration-300">
                    {category.icon}
                  </div>
                  <h3 className="text-lg font-semibold text-beej-brown mb-2 group-hover:text-beej-green transition-colors">
                    {category.name}
                  </h3>
                  <p className="text-sm text-beej-brown/60">
                    {category.description}
                  </p>
                </Link>
              </motion.div>
            ))}
          </div>
        </div>
      </section>

      {/* Benefits */}
      <section className="py-20 bg-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <motion.div 
            className="text-center mb-16"
            initial={{ opacity: 0, y: 20 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
            transition={{ duration: 0.6 }}
          >
            <Badge variant="secondary" className="mb-4">Why Choose BEEJ</Badge>
            <h2 className="text-4xl md:text-5xl font-bold text-beej-green mb-6">
              The BEEJ Advantage
            </h2>
            <p className="text-xl text-beej-brown/70 max-w-2xl mx-auto">
              Experience the difference with our premium quality seeds
            </p>
          </motion.div>

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            {benefits.map((benefit, index) => (
              <motion.div
                key={index}
                initial={{ opacity: 0, y: 20 }}
                whileInView={{ opacity: 1, y: 0 }}
                viewport={{ once: true }}
                transition={{ duration: 0.6, delay: index * 0.1 }}
                className="text-center group"
              >
                <div className={`${benefit.bgColor} w-20 h-20 rounded-2xl flex items-center justify-center mx-auto mb-6 group-hover:scale-110 transition-transform duration-300`}>
                  <benefit.icon className={`h-10 w-10 ${benefit.color}`} />
                </div>
                <h3 className="text-xl font-semibold text-beej-brown mb-3">
                  {benefit.title}
                </h3>
                <p className="text-beej-brown/60 leading-relaxed">
                  {benefit.description}
                </p>
              </motion.div>
            ))}
          </div>
        </div>
      </section>

      {/* Testimonials */}
      <section className="py-20 bg-beej-beige/30">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <motion.div 
            className="text-center mb-16"
            initial={{ opacity: 0, y: 20 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
            transition={{ duration: 0.6 }}
          >
            <Badge variant="secondary" className="mb-4">Customer Love</Badge>
            <h2 className="text-4xl md:text-5xl font-bold text-beej-green mb-6">
              What Our Customers Say
            </h2>
            <p className="text-xl text-beej-brown/70 max-w-2xl mx-auto">
              Join thousands of happy customers on their health journey
            </p>
          </motion.div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            {testimonials.map((testimonial, index) => (
              <motion.div
                key={testimonial.id}
                initial={{ opacity: 0, y: 20 }}
                whileInView={{ opacity: 1, y: 0 }}
                viewport={{ once: true }}
                transition={{ duration: 0.6, delay: index * 0.1 }}
                className="bg-white rounded-2xl p-8 shadow-lg hover:shadow-xl transition-shadow duration-300"
              >
                <div className="flex items-center mb-6">
                  <div className="text-3xl mr-4">{testimonial.avatar}</div>
                  <div>
                    <h4 className="text-lg font-semibold text-beej-brown">{testimonial.name}</h4>
                    <p className="text-sm text-beej-brown/60">{testimonial.role}</p>
                  </div>
                </div>
                <div className="flex mb-4">
                  {[...Array(testimonial.rating)].map((_, i) => (
                    <Star key={i} className="h-5 w-5 fill-yellow-400 text-yellow-400" />
                  ))}
                </div>
                <p className="text-beej-brown/70 leading-relaxed italic">
                  "{testimonial.content}"
                </p>
              </motion.div>
            ))}
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="py-20 bg-gradient-to-r from-beej-green to-beej-green-light text-white">
        <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
            transition={{ duration: 0.6 }}
            className="space-y-8"
          >
            <Badge variant="secondary" className="bg-white/20 text-white border-white/30">
              🎁 Special Offer
            </Badge>
            <h2 className="text-4xl md:text-5xl font-bold">
              Start Your Health Journey Today
            </h2>
            <p className="text-xl text-white/90 max-w-2xl mx-auto">
              Get 20% off your first order plus free shipping on all orders above ₹499
            </p>
            <div className="flex flex-col sm:flex-row gap-6 justify-center">
              <Button variant="secondary" size="lg" className="bg-white text-beej-green hover:bg-beej-beige">
                <ShoppingCart className="mr-2 h-5 w-5" />
                Shop Now - Save 20%
              </Button>
              <Button variant="outline" size="lg" className="border-white text-white hover:bg-white hover:text-beej-green">
                Learn More
              </Button>
            </div>
          </motion.div>
        </div>
      </section>
    </div>
  );
};

export default HomePage;
