import React, { useState } from 'react'
import { motion } from 'framer-motion'
import { Link, useNavigate } from 'react-router-dom'
import { Eye, EyeOff, Mail, Lock, User, Phone, Leaf, Check } from 'lucide-react'
import { useDispatch, useSelector } from 'react-redux'
import { register } from '../../store/slices/authSlice'
import Button from '../../components/ui/Button'
import Input from '../../components/ui/Input'
import Badge from '../../components/ui/Badge'

const RegisterPage = () => {
  const dispatch = useDispatch()
  const navigate = useNavigate()
  const { loading, error } = useSelector((state) => state.auth)
  
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    phone: '',
    password: '',
    confirmPassword: ''
  })
  const [showPassword, setShowPassword] = useState(false)
  const [showConfirmPassword, setShowConfirmPassword] = useState(false)
  const [agreedToTerms, setAgreedToTerms] = useState(false)

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    })
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    
    if (formData.password !== formData.confirmPassword) {
      alert('Passwords do not match')
      return
    }

    if (!agreedToTerms) {
      alert('Please agree to the terms and conditions')
      return
    }
    
    try {
      await dispatch(register({
        firstName: formData.firstName,
        lastName: formData.lastName,
        email: formData.email,
        phone: formData.phone,
        password: formData.password
      })).unwrap()
      navigate('/')
    } catch (error) {
      console.error('Registration failed:', error)
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
            Join BEEJ Community
          </h2>
          <p className="mt-2 text-beej-brown/60">
            Create your account and start your health journey
          </p>
        </div>

        {/* Benefits */}
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.3, duration: 0.5 }}
          className="bg-white/50 rounded-xl p-4 border border-beej-green/20"
        >
          <div className="flex items-center justify-center space-x-6 text-sm">
            <div className="flex items-center text-beej-green">
              <Check className="h-4 w-4 mr-1" />
              Free Shipping
            </div>
            <div className="flex items-center text-beej-green">
              <Check className="h-4 w-4 mr-1" />
              15% Off First Order
            </div>
            <div className="flex items-center text-beej-green">
              <Check className="h-4 w-4 mr-1" />
              Health Tips
            </div>
          </div>
        </motion.div>

        {/* Form */}
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.4, duration: 0.5 }}
          className="bg-white rounded-2xl shadow-xl p-8"
        >
          <form onSubmit={handleSubmit} className="space-y-6">
            {error && (
              <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg text-sm">
                {error}
              </div>
            )}

            <div className="grid grid-cols-2 gap-4">
              <Input
                name="firstName"
                label="First Name"
                placeholder="John"
                value={formData.firstName}
                onChange={handleChange}
                required
                icon={<User className="h-5 w-5" />}
              />
              <Input
                name="lastName"
                label="Last Name"
                placeholder="Doe"
                value={formData.lastName}
                onChange={handleChange}
                required
              />
            </div>

            <Input
              name="email"
              type="email"
              label="Email Address"
              placeholder="john@example.com"
              value={formData.email}
              onChange={handleChange}
              required
              icon={<Mail className="h-5 w-5" />}
            />

            <Input
              name="phone"
              type="tel"
              label="Phone Number"
              placeholder="+91 98765 43210"
              value={formData.phone}
              onChange={handleChange}
              icon={<Phone className="h-5 w-5" />}
            />

            <div>
              <label className="block text-sm font-medium text-beej-brown mb-2">
                Password
              </label>
              <div className="relative">
                <input
                  name="password"
                  type={showPassword ? 'text' : 'password'}
                  placeholder="Create a strong password"
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

            <div>
              <label className="block text-sm font-medium text-beej-brown mb-2">
                Confirm Password
              </label>
              <div className="relative">
                <input
                  name="confirmPassword"
                  type={showConfirmPassword ? 'text' : 'password'}
                  placeholder="Confirm your password"
                  value={formData.confirmPassword}
                  onChange={handleChange}
                  required
                  className="w-full pl-10 pr-10 py-3 border border-beej-green/30 rounded-lg focus:outline-none focus:ring-2 focus:ring-beej-green focus:border-beej-green transition-colors"
                />
                <Lock className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-beej-brown/50" />
                <button
                  type="button"
                  onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                  className="absolute right-3 top-1/2 transform -translate-y-1/2 text-beej-brown/50 hover:text-beej-brown"
                >
                  {showConfirmPassword ? <EyeOff className="h-5 w-5" /> : <Eye className="h-5 w-5" />}
                </button>
              </div>
            </div>

            <div className="flex items-start">
              <input
                id="terms"
                name="terms"
                type="checkbox"
                checked={agreedToTerms}
                onChange={(e) => setAgreedToTerms(e.target.checked)}
                className="h-4 w-4 text-beej-green focus:ring-beej-green border-beej-green/30 rounded mt-1"
              />
              <label htmlFor="terms" className="ml-2 block text-sm text-beej-brown/60">
                I agree to the{' '}
                <Link to="/terms" className="text-beej-green hover:text-beej-green-dark transition-colors">
                  Terms and Conditions
                </Link>
                {' '}and{' '}
                <Link to="/privacy" className="text-beej-green hover:text-beej-green-dark transition-colors">
                  Privacy Policy
                </Link>
              </label>
            </div>

            <Button
              type="submit"
              variant="primary"
              size="lg"
              loading={loading}
              className="w-full"
            >
              Create Account
            </Button>
          </form>

          <div className="mt-6">
            <div className="relative">
              <div className="absolute inset-0 flex items-center">
                <div className="w-full border-t border-beej-green/20" />
              </div>
              <div className="relative flex justify-center text-sm">
                <span className="px-2 bg-white text-beej-brown/60">Or sign up with</span>
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
          transition={{ delay: 0.5, duration: 0.5 }}
          className="text-center"
        >
          <p className="text-beej-brown/60">
            Already have an account?{' '}
            <Link
              to="/login"
              className="font-medium text-beej-green hover:text-beej-green-dark transition-colors"
            >
              Sign in
            </Link>
          </p>
        </motion.div>

        {/* Security Note */}
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.6, duration: 0.5 }}
          className="text-center"
        >
          <p className="text-xs text-beej-brown/50">
            🔒 Your information is secure and encrypted
          </p>
        </motion.div>
      </motion.div>
    </div>
  )
}

export default RegisterPage
