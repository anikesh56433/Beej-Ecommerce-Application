import React, { useState } from 'react'
import { motion } from 'framer-motion'
import { Link, useNavigate } from 'react-router-dom'
import { Eye, EyeOff, Mail, Lock, Leaf } from 'lucide-react'
import { useDispatch, useSelector } from 'react-redux'
import { login } from '../../store/slices/authSlice'
import Button from '../../components/ui/Button'
import Input from '../../components/ui/Input'
import Badge from '../../components/ui/Badge'

const LoginPage = () => {
  const dispatch = useDispatch()
  const navigate = useNavigate()
  const { loading, error } = useSelector((state) => state.auth)
  
  const [formData, setFormData] = useState({
    email: '',
    password: ''
  })
  const [showPassword, setShowPassword] = useState(false)

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    })
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    
    try {
      await dispatch(login(formData)).unwrap()
      navigate('/')
    } catch (error) {
      console.error('Login failed:', error)
    }
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
          <motion.div
            initial={{ scale: 0 }}
            animate={{ scale: 1 }}
            transition={{ delay: 0.2, duration: 0.5 }}
            className="mx-auto h-16 w-16 bg-beej-green rounded-full flex items-center justify-center mb-6"
          >
            <Leaf className="h-8 w-8 text-white" />
          </motion.div>
          <h2 className="text-3xl font-bold text-beej-green">
            Welcome Back
          </h2>
          <p className="mt-2 text-beej-brown/60">
            Sign in to your BEEJ account
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
              placeholder="Enter your email"
              value={formData.email}
              onChange={handleChange}
              required
              icon={<Mail className="h-5 w-5" />}
            />

            <div>
              <label className="block text-sm font-medium text-beej-brown mb-2">
                Password
              </label>
              <div className="relative">
                <input
                  name="password"
                  type={showPassword ? 'text' : 'password'}
                  placeholder="Enter your password"
                  value={formData.password}
                  onChange={handleChange}
                  required
                  className="w-full pl-10 pr-10 py-3 border border-beej-green/30 rounded-lg focus:outline-none focus:ring-2 focus:ring-beej-green focus:border-beej-green transition-colors"
                />
                <Lock className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-beej-brown/50" />
                <button
                  type="button"
                  onClick={() => setShowPassword(!showPassword)}
                  className="absolute right-3 top-1/2 transform -translate-y-1/2 text-beej-brown/50 hover:text-beej-brown"
                >
                  {showPassword ? <EyeOff className="h-5 w-5" /> : <Eye className="h-5 w-5" />}
                </button>
              </div>
            </div>

            <div className="flex items-center justify-between">
              <div className="flex items-center">
                <input
                  id="remember-me"
                  name="remember-me"
                  type="checkbox"
                  className="h-4 w-4 text-beej-green focus:ring-beej-green border-beej-green/30 rounded"
                />
                <label htmlFor="remember-me" className="ml-2 block text-sm text-beej-brown/60">
                  Remember me
                </label>
              </div>

              <Link
                to="/forgot-password"
                className="text-sm text-beej-green hover:text-beej-green-dark transition-colors"
              >
                Forgot password?
              </Link>
            </div>

            <Button
              type="submit"
              variant="primary"
              size="lg"
              loading={loading}
              className="w-full"
            >
              Sign In
            </Button>
          </form>

          <div className="mt-6">
            <div className="relative">
              <div className="absolute inset-0 flex items-center">
                <div className="w-full border-t border-beej-green/20" />
              </div>
              <div className="relative flex justify-center text-sm">
                <span className="px-2 bg-white text-beej-brown/60">Or continue with</span>
              </div>
            </div>

            <div className="mt-6 grid grid-cols-2 gap-3">
              <Button
                variant="outline"
                size="sm"
                className="w-full"
                onClick={() => window.open('https://wa.me/918319143937?text=Hello%20I%20want%20to%20order%20BEEJ%20products', '_blank')}
              >
                <span className="text-green-500">📱</span> WhatsApp
              </Button>
              <Button
                variant="outline"
                size="sm"
                className="w-full"
              >
                <span>📧</span> Email
              </Button>
            </div>
          </div>
        </motion.div>

        {/* Footer */}
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.4, duration: 0.5 }}
          className="text-center"
        >
          <p className="text-beej-brown/60">
            Don't have an account?{' '}
            <Link
              to="/register"
              className="font-medium text-beej-green hover:text-beej-green-dark transition-colors"
            >
              Sign up for free
            </Link>
          </p>
        </motion.div>

        {/* Trust Badges */}
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.5, duration: 0.5 }}
          className="text-center space-y-4"
        >
          <div className="flex items-center justify-center space-x-4 text-xs text-beej-brown/50">
            <span>🔒 Secure login</span>
            <span>•</span>
            <span>🛡️ Protected data</span>
            <span>•</span>
            <span>✅ Verified accounts</span>
          </div>
        </motion.div>
      </motion.div>
    </div>
  )
}

export default LoginPage
