import React from 'react'
import { motion } from 'framer-motion'
import { Leaf, Heart, Award, Users, Target, Globe } from 'lucide-react'
import { Link } from 'react-router-dom'
import Button from '../components/ui/Button'
import Badge from '../components/ui/Badge'

const AboutPage = () => {
  const values = [
    {
      icon: Leaf,
      title: 'Natural & Organic',
      description: 'We source only the finest natural and organic seeds from trusted farmers who share our commitment to quality.'
    },
    {
      icon: Heart,
      title: 'Health First',
      description: 'Every product is carefully selected to support your health journey with maximum nutritional benefits.'
    },
    {
      icon: Award,
      title: 'Quality Assured',
      description: 'Our seeds undergo rigorous testing to ensure purity, freshness, and nutritional value.'
    },
    {
      icon: Users,
      title: 'Community Focused',
      description: 'We believe in building a community of health-conscious individuals supporting each other.'
    }
  ]

  const milestones = [
    { year: '2020', title: 'The Beginning', description: 'Started with a simple mission to bring quality seeds to every home' },
    { year: '2021', title: 'First 1000 Customers', description: 'Reached our first milestone with overwhelming customer love' },
    { year: '2022', title: 'Product Expansion', description: 'Expanded our range to include 20+ premium seed varieties' },
    { year: '2023', title: 'National Reach', description: 'Started shipping across India with our improved logistics' },
    { year: '2024', title: 'Community Building', description: 'Launched our wellness blog and community initiatives' }
  ]

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
            <Badge variant="secondary" className="mb-6">About BEEJ</Badge>
            <h1 className="text-4xl md:text-6xl font-bold mb-6">
              Our Story: Root. Rise. Refine.
            </h1>
            <p className="text-xl md:text-2xl text-beej-beige/90 max-w-3xl mx-auto leading-relaxed">
              From a small kitchen experiment to a nationwide movement for healthier living,
              BEEJ is on a mission to bring nature's most powerful superfoods to your doorstep.
            </p>
          </motion.div>
        </div>
      </motion.section>

      {/* Mission & Vision */}
      <section className="py-20 bg-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid md:grid-cols-2 gap-12 items-center">
            <motion.div
              initial={{ opacity: 0, x: -20 }}
              whileInView={{ opacity: 1, x: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.6 }}
            >
              <h2 className="text-3xl md:text-4xl font-bold text-beej-green mb-6">
                Our Mission
              </h2>
              <p className="text-lg text-beej-brown/70 mb-6 leading-relaxed">
                To make healthy living accessible and enjoyable for everyone by providing 
                premium quality seeds that nourish the body and delight the senses.
              </p>
              <h3 className="text-2xl font-semibold text-beej-brown mb-4">
                Our Vision
              </h3>
              <p className="text-lg text-beej-brown/70 leading-relaxed">
                To become India's most trusted brand for natural superfoods, 
                inspiring a generation to embrace healthier, more sustainable lifestyles.
              </p>
            </motion.div>
            <motion.div
              initial={{ opacity: 0, x: 20 }}
              whileInView={{ opacity: 1, x: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.6, delay: 0.2 }}
              className="bg-beej-green/10 rounded-2xl p-8"
            >
              <Leaf className="h-16 w-16 text-beej-green mb-4" />
              <h3 className="text-xl font-semibold text-beej-brown mb-3">
                Why "BEEJ"?
              </h3>
              <p className="text-beej-brown/70 leading-relaxed">
                "BEEJ" means seed in Hindi. We chose this name because seeds are the 
                beginning of all life - small but powerful, just like the impact our 
                products can have on your health journey.
              </p>
            </motion.div>
          </div>
        </div>
      </section>

      {/* Core Values */}
      <section className="py-20 bg-beej-beige/30">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <motion.div 
            className="text-center mb-16"
            initial={{ opacity: 0, y: 20 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
            transition={{ duration: 0.6 }}
          >
            <Badge variant="secondary" className="mb-4">Our Values</Badge>
            <h2 className="text-4xl md:text-5xl font-bold text-beej-green mb-6">
              What Drives Us
            </h2>
            <p className="text-xl text-beej-brown/70 max-w-2xl mx-auto">
              These core values guide every decision we make and every product we create
            </p>
          </motion.div>

          <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-8">
            {values.map((value, index) => (
              <motion.div
                key={index}
                initial={{ opacity: 0, y: 20 }}
                whileInView={{ opacity: 1, y: 0 }}
                viewport={{ once: true }}
                transition={{ duration: 0.6, delay: index * 0.1 }}
                className="text-center"
              >
                <div className="bg-white rounded-2xl p-6 shadow-lg hover:shadow-xl transition-shadow duration-300">
                  <value.icon className="h-12 w-12 text-beej-green mx-auto mb-4" />
                  <h3 className="text-xl font-semibold text-beej-brown mb-3">
                    {value.title}
                  </h3>
                  <p className="text-beej-brown/60 leading-relaxed">
                    {value.description}
                  </p>
                </div>
              </motion.div>
            ))}
          </div>
        </div>
      </section>

      {/* Journey Timeline */}
      <section className="py-20 bg-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <motion.div 
            className="text-center mb-16"
            initial={{ opacity: 0, y: 20 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
            transition={{ duration: 0.6 }}
          >
            <Badge variant="secondary" className="mb-4">Our Journey</Badge>
            <h2 className="text-4xl md:text-5xl font-bold text-beej-green mb-6">
              Growing Together
            </h2>
            <p className="text-xl text-beej-brown/70 max-w-2xl mx-auto">
              Every milestone represents our commitment to bringing you the best
            </p>
          </motion.div>

          <div className="relative">
            <div className="absolute left-1/2 transform -translate-x-1/2 h-full w-0.5 bg-beej-green/20"></div>
            {milestones.map((milestone, index) => (
              <motion.div
                key={index}
                initial={{ opacity: 0, x: index % 2 === 0 ? -20 : 20 }}
                whileInView={{ opacity: 1, x: 0 }}
                viewport={{ once: true }}
                transition={{ duration: 0.6, delay: index * 0.1 }}
                className={`flex items-center mb-12 ${
                  index % 2 === 0 ? 'flex-row-reverse' : ''
                }`}
              >
                <div className={`w-1/2 ${index % 2 === 0 ? 'text-right pr-8' : 'pl-8'}`}>
                  <div className="bg-white rounded-2xl p-6 shadow-lg border border-beej-green/20">
                    <Badge variant="secondary" className="mb-2">{milestone.year}</Badge>
                    <h3 className="text-xl font-semibold text-beej-brown mb-2">
                      {milestone.title}
                    </h3>
                    <p className="text-beej-brown/60">
                      {milestone.description}
                    </p>
                  </div>
                </div>
                <div className="w-12 h-12 bg-beej-green rounded-full flex items-center justify-center text-white font-bold z-10">
                  {index + 1}
                </div>
                <div className="w-1/2"></div>
              </motion.div>
            ))}
          </div>
        </div>
      </section>

      {/* Team Section */}
      <section className="py-20 bg-beej-beige/30">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <motion.div 
            className="text-center mb-16"
            initial={{ opacity: 0, y: 20 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
            transition={{ duration: 0.6 }}
          >
            <Badge variant="secondary" className="mb-4">Meet Our Team</Badge>
            <h2 className="text-4xl md:text-5xl font-bold text-beej-green mb-6">
              The People Behind BEEJ
            </h2>
            <p className="text-xl text-beej-brown/70 max-w-2xl mx-auto">
              Passionate individuals dedicated to making healthy living accessible to all
            </p>
          </motion.div>

          <div className="grid md:grid-cols-3 gap-8">
            {[
              {
                name: 'Anjali Sharma',
                role: 'Founder & CEO',
                bio: 'Nutritionist turned entrepreneur with a vision to make India healthier',
                avatar: '👩‍💼'
              },
              {
                name: 'Rahul Verma',
                role: 'Co-Founder & COO',
                bio: 'Operations expert ensuring quality reaches every doorstep',
                avatar: '👨‍💼'
              },
              {
                name: 'Priya Patel',
                role: 'Head of Product',
                bio: 'Food scientist passionate about natural superfoods',
                avatar: '👩‍🔬'
              }
            ].map((member, index) => (
              <motion.div
                key={index}
                initial={{ opacity: 0, y: 20 }}
                whileInView={{ opacity: 1, y: 0 }}
                viewport={{ once: true }}
                transition={{ duration: 0.6, delay: index * 0.1 }}
                className="text-center"
              >
                <div className="bg-white rounded-2xl p-8 shadow-lg hover:shadow-xl transition-shadow duration-300">
                  <div className="text-6xl mb-4">{member.avatar}</div>
                  <h3 className="text-xl font-semibold text-beej-brown mb-2">
                    {member.name}
                  </h3>
                  <p className="text-beej-green font-medium mb-3">{member.role}</p>
                  <p className="text-beej-brown/60 text-sm">
                    {member.bio}
                  </p>
                </div>
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
            <h2 className="text-4xl md:text-5xl font-bold">
              Join Our Health Revolution
            </h2>
            <p className="text-xl text-white/90 max-w-2xl mx-auto">
              Be part of a community that values health, wellness, and sustainable living
            </p>
            <div className="flex flex-col sm:flex-row gap-6 justify-center">
              <Button variant="secondary" size="lg" className="bg-white text-beej-green hover:bg-beej-beige">
                <Link to="/products">Explore Products</Link>
              </Button>
              <Button variant="outline" size="lg" className="border-white text-white hover:bg-white hover:text-beej-green">
                <Link to="/contact">Get in Touch</Link>
              </Button>
            </div>
          </motion.div>
        </div>
      </section>
    </div>
  )
}

export default AboutPage
