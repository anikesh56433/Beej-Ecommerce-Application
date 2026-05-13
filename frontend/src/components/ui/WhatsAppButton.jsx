import React from 'react'
import { motion } from 'framer-motion'
import { MessageCircle, X } from 'lucide-react'

const WhatsAppButton = () => {
  const phoneNumber = '918319143937'
  const message = 'Hello%20I%20want%20to%20order%20BEEJ%20products'
  const whatsappUrl = `https://wa.me/${phoneNumber}?text=${message}`

  return (
    <>
      {/* WhatsApp Floating Button */}
      <motion.a
        href={whatsappUrl}
        target="_blank"
        rel="noopener noreferrer"
        className="fixed bottom-8 left-8 z-40 bg-green-500 text-white p-4 rounded-full shadow-lg hover:bg-green-600 transition-all duration-200 hover:shadow-xl"
        initial={{ opacity: 0, scale: 0 }}
        animate={{ opacity: 1, scale: 1 }}
        transition={{ delay: 2, duration: 0.3 }}
        whileHover={{ scale: 1.1 }}
        whileTap={{ scale: 0.9 }}
      >
        <MessageCircle className="h-6 w-6" />
        <span className="sr-only">Contact on WhatsApp</span>
      </motion.a>

      {/* WhatsApp Help Tooltip */}
      <motion.div
        className="fixed bottom-8 left-20 z-40 bg-white rounded-lg shadow-xl border border-beej-green/20 p-3 max-w-xs"
        initial={{ opacity: 0, x: -20 }}
        animate={{ opacity: 1, x: 0 }}
        transition={{ delay: 2.5, duration: 0.3 }}
      >
        <p className="text-sm text-beej-brown font-medium">
          Need help? Chat with us on WhatsApp!
        </p>
        <p className="text-xs text-beej-brown/60 mt-1">
          Instant support for your orders
        </p>
      </motion.div>
    </>
  )
}

export default WhatsAppButton
