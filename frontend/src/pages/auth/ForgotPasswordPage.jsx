import React, { useState } from 'react'
import { motion } from 'framer-motion'
import { Link } from 'react-router-dom'
import { Mail, ArrowLeft, Leaf, Check } from 'lucide-react'
import { useDispatch, useSelector } from 'react-redux'
import { forgotPassword } from '../../store/slices/authSlice'
import Button from '../../components/ui/Button'
import Input from '../../components/ui/Input'
import Badge from '../../components/ui/Badge'

const ForgotPasswordPage = () => {
  const dispatch = useDispatch()
  const { loading, error } = useSelector((state) => state.auth)
  const [email, setEmail] = useState('')
  const [submitted, setSubmitted] = useState(false)

  const handleSubmit = async (e) => {
    e.preventDefault()
    
    try {
      await dispatch(forgotPassword(email)).unwrap()
      setSubmitted(true)
    } catch (error) {
      console.error('Password reset failed:', error)
    }
  }

  if (submitted) {
    return (
      <div className="min-h-screen bg-beej-beige flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
        <motion.div
          initial={{ opacity: 0, scale: 0.9 }}
          animate={{ opacity: 1, scale: 1 }}
          transition={{ duration: 0.6 }}
          className="max-w-md w-full"
        >
          <div className="bg-white rounded-2xl shadow-xl p-8 text-center">
            <motion.div
              initial={{ scale: 0 }}
              animate={{ scale: 1 }}
              transition={{ delay: 0.2, duration: 0.5 }}
              className="mx-auto h-16 w-16 bg-green-100 rounded-full flex items-center justify-center mb-6"
            >
              <Check className="h-8 w-8 text-green-600" />
            </motion.div>
            
            <h2 className="text-2xl font-bold text-beej-green mb-4">
              Check Your Email
            </h2>
            
            <p className="text-beej-brown/70 mb-6">
              We've sent password reset instructions to:
              <br />
              <span className="font-medium text-beej-brown">{email}</span>
            </p>
            
            <div className="space-y-4">
              <div className="bg-beej-beige/30 rounded-lg p-4">
                <p className="text-sm text-beej-brown/60 mb-2">
                  📧 Check your inbox (and spam folder)
                </p>
                <p className="text-sm text-beej-brown/60">
                  ⏰ The link expires in 24 hours
                </p>
              </div>
              
              <Button
                variant="outline"
                size="sm"
                onClick={() => setSubmitted(false)}
                className="w-full"
              >
                Try Another Email
              </Button>
              
              <Link
                to="/login"
                className="block text-sm text-beej-green hover:text-beej-green-dark transition-colors"
              >
                Back to Sign In
              </Link>
            </div>
          </div>
        </motion.div>
      </div>
    )
  }

  return (
    <div className="min-h-screen bg-beej-beige flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6 }}
        className="max-w-md w-full space-y-8"
      >
        {/* Header */}
        <div className="text-center">
          <Link
            to="/login"
            className="inline-flex items-center text-beej-brown/60 hover:text-beej-green transition-colors mb-6"
          >
            <ArrowLeft className="h-4 w-4 mr-2" />
            Back to Sign In
          </Link>
          
          <motion.div
            initial={{ scale: 0 }}
            animate={{ scale: 1 }}
            transition={{ delay: 0.2, duration: 0.5 }}
            className="mx-auto h-16 w-16 bg-beej-green rounded-full flex items-center justify-center mb-6"
          >
            <Leaf className="h-8 w-8 text-white" />
          </motion.div>
          
          <h2 className="text-3xl font-bold text-beej-green">
            Reset Your Password
          </h2>
          <p className="mt-2 text-beej-brown/60">
            Enter your email address and we'll send you a link to reset your password
          </p>
        </div>

        {/* Form */}
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.3, duration: 0.5 }}
          className="bg-white rounded-2xl shadow-xl p-8"
        >
          <form onSubmit={handleSubmit} className="space-y-6">
            {error && (
              <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg text-sm">
                {error}
              </div>
            )}

            <Input
              name="email"
              type="email"
              label="Email Address"
              placeholder="Enter your registered email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              icon={<Mail className="h-5 w-5" />}
            />

            <Button
              type="submit"
              variant="primary"
              size="lg"
              loading={loading}
              className="w-full"
            >
              Send Reset Link
            </Button>
          </form>

          {/* Help Section */}
          <div className="mt-6 pt-6 border-t border-beej-green/20">
            <div className="text-center space-y-3">
              <p className="text-sm text-beej-brown/60">
                Need help? Contact our support team
              </p>
              <div className="flex items-center justify-center space-x-4">
                <Button
                  variant="outline"
                  size="sm"
                  onClick={() => window.open('https://wa.me/918319143937?text=Hello%20I%20need%20help%20with%20password%20reset', '_blank')}
                >
                  <span className="text-green-500">📱</span> WhatsApp Support
                </Button>
                <Button
                  variant="outline"
                  size="sm"
                  onClick={() => window.open('mailto:support@beej.in?subject=Password Reset Help', '_blank')}
                >
                  <span>📧</span> Email Support
                </Button>
              </div>
            </div>
          </div>
        </motion.div>

        {/* Alternative Options */}
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.4, duration: 0.5 }}
          className="text-center space-y-4"
        >
          <div className="bg-beej-beige/30 rounded-xl p-4">
            <p className="text-sm text-beej-brown/60 mb-2">
              🔐 Remember your password?
            </p>
            <Link
              to="/login"
              className="text-beej-green hover:text-beej-green-dark font-medium transition-colors"
            >
              Sign In Instead
            </Link>
          </div>
          
          <div className="bg-beej-beige/30 rounded-xl p-4">
            <p className="text-sm text-beej-brown/60 mb-2">
              🆕 New to BEEJ?
            </p>
            <Link
              to="/register"
              className="text-beej-green hover:text-beej-green-dark font-medium transition-colors"
            >
              Create Account
            </Link>
          </div>
        </motion.div>
      </motion.div>
    </div>
  )
}

export default ForgotPasswordPage
