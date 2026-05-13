import React, { useState, useEffect } from 'react'
import { motion, AnimatePresence } from 'framer-motion'
import { X, Mail, Gift } from 'lucide-react'
import { useSelector, useDispatch } from 'react-redux'
import { selectNewsletterSubscribed, selectShowNewsletterPopup, setNewsletterSubscribed, setShowNewsletterPopup } from '../../store/slices/uiSlice'
import Button from './Button'

const NewsletterPopup = () => {
  const dispatch = useDispatch()
  const isSubscribed = useSelector(selectNewsletterSubscribed)
  const showPopup = useSelector(selectShowNewsletterPopup)
  const [email, setEmail] = useState('')
  const [loading, setLoading] = useState(false)
  const [show, setShow] = useState(false)

  useEffect(() => {
    // Show popup after 10 seconds if not subscribed
    if (!isSubscribed && showPopup) {
      const timer = setTimeout(() => {
        setShow(true)
      }, 10000)
      return () => clearTimeout(timer)
    }
  }, [isSubscribed, showPopup])

  const handleSubmit = async (e) => {
    e.preventDefault()
    if (!email) return

    setLoading(true)
    try {
      // Mock API call
      await new Promise(resolve => setTimeout(resolve, 1000))
      
      dispatch(setNewsletterSubscribed(true))
      setShow(false)
      dispatch(setShowNewsletterPopup(false))
      
      // Show success message
      alert('Thank you for subscribing! Check your email for a special discount.')
    } catch (error) {
      console.error('Newsletter subscription failed:', error)
    } finally {
      setLoading(false)
    }
  }

  const handleClose = () => {
    setShow(false)
    dispatch(setShowNewsletterPopup(false))
  }

  const handleLater = () => {
    setShow(false)
  }

  if (isSubscribed || !show) return null

  return (
    <AnimatePresence>
      {show && (
        <>
          {/* Backdrop */}
          <motion.div
            className="fixed inset-0 bg-black/50 z-50"
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            onClick={handleClose}
          />
          
          {/* Popup */}
          <motion.div
            className="fixed inset-0 z-50 flex items-center justify-center p-4"
            initial={{ opacity: 0, scale: 0.9 }}
            animate={{ opacity: 1, scale: 1 }}
            exit={{ opacity: 0, scale: 0.9 }}
            transition={{ duration: 0.3 }}
          >
            <div 
              className="bg-white rounded-2xl shadow-2xl max-w-md w-full overflow-hidden"
              onClick={(e) => e.stopPropagation()}
            >
              {/* Header */}
              <div className="bg-gradient-to-r from-beej-green to-beej-green-light p-6 text-white relative">
                <button
                  onClick={handleClose}
                  className="absolute top-4 right-4 text-white/80 hover:text-white transition-colors"
                >
                  <X className="h-5 w-5" />
                </button>
                
                <div className="flex items-center space-x-3">
                  <div className="bg-white/20 p-3 rounded-full">
                    <Gift className="h-6 w-6" />
                  </div>
                  <div>
                    <h3 className="text-xl font-bold">Get 15% Off!</h3>
                    <p className="text-white/90 text-sm">Subscribe to our newsletter</p>
                  </div>
                </div>
              </div>

              {/* Content */}
              <div className="p-6">
                <div className="text-center mb-6">
                  <Mail className="h-12 w-12 text-beej-green mx-auto mb-3" />
                  <h4 className="text-lg font-semibold text-beej-brown mb-2">
                    Join the BEEJ Community
                  </h4>
                  <p className="text-beej-brown/60 text-sm">
                    Get exclusive offers, health tips, and be the first to know about new products!
                  </p>
                </div>

                <form onSubmit={handleSubmit} className="space-y-4">
                  <div>
                    <input
                      type="email"
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
                      placeholder="Enter your email address"
                      className="w-full px-4 py-3 border border-beej-green/30 rounded-lg focus:outline-none focus:ring-2 focus:ring-beej-green focus:border-beej-green transition-colors"
                      required
                    />
                  </div>
                  
                  <div className="flex gap-3">
                    <Button
                      type="submit"
                      variant="primary"
                      size="sm"
                      loading={loading}
                      className="flex-1"
                    >
                      Subscribe Now
                    </Button>
                    <Button
                      type="button"
                      variant="ghost"
                      size="sm"
                      onClick={handleLater}
                    >
                      Maybe Later
                    </Button>
                  </div>
                </form>

                <p className="text-xs text-beej-brown/50 text-center mt-4">
                  No spam, unsubscribe anytime. We respect your privacy.
                </p>
              </div>
            </div>
          </motion.div>
        </>
      )}
    </AnimatePresence>
  )
}

export default NewsletterPopup
