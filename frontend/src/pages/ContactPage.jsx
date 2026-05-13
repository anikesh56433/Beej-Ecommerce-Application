import React, { useState } from 'react'
import { motion } from 'framer-motion'
import { Mail, Phone, MapPin, MessageCircle, Send, Clock } from 'lucide-react'
import Button from '../components/ui/Button'
import Input from '../components/ui/Input'
import Badge from '../components/ui/Badge'

const ContactPage = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    phone: '',
    subject: '',
    message: ''
  })
  const [loading, setLoading] = useState(false)
  const [submitted, setSubmitted] = useState(false)

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    })
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setLoading(true)
    
    try {
      // Mock API call
      await new Promise(resolve => setTimeout(resolve, 1000))
      setSubmitted(true)
      setFormData({ name: '', email: '', phone: '', subject: '', message: '' })
    } catch (error) {
      console.error('Form submission failed:', error)
    } finally {
      setLoading(false)
    }
  }

  const contactInfo = [
    {
      icon: Phone,
      title: 'Call Us',
      details: ['+91 83191 43937', 'Mon-Sat: 9AM-6PM'],
      action: 'tel:+918319143937'
    },
    {
      icon: Mail,
      title: 'Email Us',
      details: ['support@beej.in', 'hello@beej.in'],
      action: 'mailto:support@beej.in'
    },
    {
      icon: MapPin,
      title: 'Visit Us',
      details: ['Mumbai, Maharashtra', 'India'],
      action: null
    }
  ]

  const faqs = [
    {
      question: 'How long does delivery take?',
      answer: 'Standard delivery takes 3-5 business days. Express delivery is available in major cities.'
    },
    {
      question: 'Are your products organic?',
      answer: 'Yes, most of our products are certified organic. Look for the organic badge on product pages.'
    },
    {
      question: 'What is your return policy?',
      answer: 'We offer 30-day return policy for unopened products in original packaging.'
    },
    {
      question: 'Do you ship internationally?',
      answer: 'Currently we ship within India. International shipping is coming soon!'
    }
  ]

  if (submitted) {
    return (
      <div className="min-h-screen bg-beej-beige flex items-center justify-center">
        <motion.div
          initial={{ opacity: 0, scale: 0.9 }}
          animate={{ opacity: 1, scale: 1 }}
          className="bg-white rounded-2xl p-8 max-w-md w-full text-center shadow-xl"
        >
          <div className="text-6xl mb-4">✅</div>
          <h2 className="text-2xl font-bold text-beej-green mb-4">
            Thank You!
          </h2>
          <p className="text-beej-brown/70 mb-6">
            We've received your message and will get back to you within 24 hours.
          </p>
          <Button variant="primary" onClick={() => setSubmitted(false)}>
            Send Another Message
          </Button>
        </motion.div>
      </div>
    )
  }

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
            <Badge variant="secondary" className="mb-6">Get in Touch</Badge>
            <h1 className="text-4xl md:text-6xl font-bold mb-6">
              We're Here to Help
            </h1>
            <p className="text-xl md:text-2xl text-beej-beige/90 max-w-3xl mx-auto leading-relaxed">
              Have questions about our products? Need health advice? 
              We're just a message away.
            </p>
          </motion.div>
        </div>
      </motion.section>

      {/* Contact Info Cards */}
      <section className="py-20">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid md:grid-cols-3 gap-8">
            {contactInfo.map((info, index) => (
              <motion.div
                key={index}
                initial={{ opacity: 0, y: 20 }}
                whileInView={{ opacity: 1, y: 0 }}
                viewport={{ once: true }}
                transition={{ duration: 0.6, delay: index * 0.1 }}
              >
                <div className="bg-white rounded-2xl p-8 shadow-lg hover:shadow-xl transition-shadow duration-300 text-center">
                  <info.icon className="h-12 w-12 text-beej-green mx-auto mb-4" />
                  <h3 className="text-xl font-semibold text-beej-brown mb-4">
                    {info.title}
                  </h3>
                  {info.details.map((detail, i) => (
                    <p key={i} className="text-beej-brown/70 mb-2">
                      {detail}
                    </p>
                  ))}
                  {info.action && (
                    <Button 
                      variant="outline" 
                      size="sm" 
                      className="mt-4"
                      onClick={() => window.open(info.action, '_blank')}
                    >
                      {info.title.includes('Call') ? 'Call Now' : info.title.includes('Email') ? 'Send Email' : 'View Map'}
                    </Button>
                  )}
                </div>
              </motion.div>
            ))}
          </div>
        </div>
      </section>

      {/* Contact Form & WhatsApp */}
      <section className="py-20 bg-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid lg:grid-cols-2 gap-12">
            {/* Contact Form */}
            <motion.div
              initial={{ opacity: 0, x: -20 }}
              whileInView={{ opacity: 1, x: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.6 }}
            >
              <Badge variant="secondary" className="mb-4">Send us a Message</Badge>
              <h2 className="text-3xl font-bold text-beej-green mb-6">
                We'd Love to Hear From You
              </h2>
              
              <form onSubmit={handleSubmit} className="space-y-6">
                <div className="grid md:grid-cols-2 gap-6">
                  <Input
                    name="name"
                    label="Your Name"
                    value={formData.name}
                    onChange={handleChange}
                    required
                  />
                  <Input
                    name="email"
                    type="email"
                    label="Email Address"
                    value={formData.email}
                    onChange={handleChange}
                    required
                  />
                </div>
                
                <Input
                  name="phone"
                  type="tel"
                  label="Phone Number"
                  value={formData.phone}
                  onChange={handleChange}
                />
                
                <Input
                  name="subject"
                  label="Subject"
                  value={formData.subject}
                  onChange={handleChange}
                  required
                />
                
                <div>
                  <label className="block text-sm font-medium text-beej-brown mb-2">
                    Message
                  </label>
                  <textarea
                    name="message"
                    value={formData.message}
                    onChange={handleChange}
                    rows={5}
                    className="w-full px-4 py-3 border border-beej-green/30 rounded-lg focus:outline-none focus:ring-2 focus:ring-beej-green focus:border-beej-green transition-colors"
                    placeholder="Tell us how we can help you..."
                    required
                  />
                </div>
                
                <Button
                  type="submit"
                  variant="primary"
                  size="lg"
                  loading={loading}
                  className="w-full"
                >
                  <Send className="mr-2 h-5 w-5" />
                  Send Message
                </Button>
              </form>
            </motion.div>

            {/* WhatsApp & Quick Contact */}
            <motion.div
              initial={{ opacity: 0, x: 20 }}
              whileInView={{ opacity: 1, x: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.6, delay: 0.2 }}
            >
              <div className="bg-green-50 rounded-2xl p-8 border border-green-200 mb-8">
                <div className="flex items-center mb-6">
                  <MessageCircle className="h-8 w-8 text-green-500 mr-3" />
                  <div>
                    <h3 className="text-xl font-semibold text-beej-brown">
                      Quick Support on WhatsApp
                    </h3>
                    <p className="text-beej-brown/60 text-sm">
                      Get instant answers to your questions
                    </p>
                  </div>
                </div>
                
                <p className="text-beej-brown/70 mb-6">
                  Chat with our product experts for personalized recommendations, 
                  order updates, and quick support.
                </p>
                
                <Button
                  variant="whatsapp"
                  size="lg"
                  className="w-full"
                  onClick={() => window.open('https://wa.me/918319143937?text=Hello%20I%20want%20to%20order%20BEEJ%20products', '_blank')}
                >
                  <MessageCircle className="mr-2 h-5 w-5" />
                  Chat on WhatsApp
                </Button>
              </div>

              {/* Business Hours */}
              <div className="bg-beej-beige/30 rounded-2xl p-8">
                <div className="flex items-center mb-6">
                  <Clock className="h-8 w-8 text-beej-green mr-3" />
                  <h3 className="text-xl font-semibold text-beej-brown">
                    Business Hours
                  </h3>
                </div>
                
                <div className="space-y-3">
                  <div className="flex justify-between">
                    <span className="text-beej-brown/70">Monday - Friday</span>
                    <span className="text-beej-brown font-medium">9:00 AM - 6:00 PM</span>
                  </div>
                  <div className="flex justify-between">
                    <span className="text-beej-brown/70">Saturday</span>
                    <span className="text-beej-brown font-medium">10:00 AM - 4:00 PM</span>
                  </div>
                  <div className="flex justify-between">
                    <span className="text-beej-brown/70">Sunday</span>
                    <span className="text-beej-brown font-medium">Closed</span>
                  </div>
                </div>
                
                <p className="text-beej-brown/60 text-sm mt-4">
                  WhatsApp support available 24/7 for urgent queries
                </p>
              </div>
            </motion.div>
          </div>
        </div>
      </section>

      {/* FAQ Section */}
      <section className="py-20 bg-beej-beige/30">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <motion.div 
            className="text-center mb-16"
            initial={{ opacity: 0, y: 20 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
            transition={{ duration: 0.6 }}
          >
            <Badge variant="secondary" className="mb-4">Frequently Asked Questions</Badge>
            <h2 className="text-4xl md:text-5xl font-bold text-beej-green mb-6">
              Quick Answers
            </h2>
            <p className="text-xl text-beej-brown/70 max-w-2xl mx-auto">
              Find answers to common questions about our products and services
            </p>
          </motion.div>

          <div className="grid md:grid-cols-2 gap-8">
            {faqs.map((faq, index) => (
              <motion.div
                key={index}
                initial={{ opacity: 0, y: 20 }}
                whileInView={{ opacity: 1, y: 0 }}
                viewport={{ once: true }}
                transition={{ duration: 0.6, delay: index * 0.1 }}
                className="bg-white rounded-2xl p-6 shadow-lg"
              >
                <h3 className="text-lg font-semibold text-beej-brown mb-3">
                  {faq.question}
                </h3>
                <p className="text-beej-brown/60">
                  {faq.answer}
                </p>
              </motion.div>
            ))}
          </div>
        </div>
      </section>
    </div>
  )
}

export default ContactPage
