import React, { useState, useEffect } from 'react'
import { motion } from 'framer-motion'
import { Link, useNavigate, useLocation } from 'react-router-dom'
import { ArrowLeft, Shield, Check, RefreshCw } from 'lucide-react'
import { useDispatch, useSelector } from 'react-redux'
import { verifyEmail } from '../../store/slices/authSlice'
import Button from '../../components/ui/Button'
import Input from '../../components/ui/Input'
import Badge from '../../components/ui/Badge'

const OTPVerificationPage = () => {
  const dispatch = useDispatch()
  const navigate = useNavigate()
  const location = useLocation()
  const { loading, error } = useSelector((state) => state.auth)
  
  const [otp, setOtp] = useState(['', '', '', '', '', ''])
  const [timeLeft, setTimeLeft] = useState(300) // 5 minutes
  const [canResend, setCanResend] = useState(false)
  const [verified, setVerified] = useState(false)
  
  const email = location.state?.email || 'your email'

  useEffect(() => {
    if (timeLeft > 0) {
      const timer = setTimeout(() => setTimeLeft(timeLeft - 1), 1000)
      return () => clearTimeout(timer)
    } else {
      setCanResend(true)
    }
  }, [timeLeft])

  const handleOtpChange = (index, value) => {
    if (value.length > 1) return
    
    const newOtp = [...otp]
    newOtp[index] = value
    setOtp(newOtp)
    
    // Auto focus next input
    if (value && index < 5) {
      const nextInput = document.getElementById(`otp-${index + 1}`)
      if (nextInput) nextInput.focus()
    }
  }

  const handleKeyDown = (index, e) => {
    if (e.key === 'Backspace' && !otp[index] && index > 0) {
      const prevInput = document.getElementById(`otp-${index - 1}`)
      if (prevInput) prevInput.focus()
    }
  }

  const handlePaste = (e) => {
    e.preventDefault()
    const pastedData = e.clipboardData.getData('text').slice(0, 6)
    const newOtp = pastedData.split('')
    setOtp(newOtp.padEnd(6, ''))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    const otpCode = otp.join('')
    
    if (otpCode.length !== 6) {
      alert('Please enter all 6 digits')
      return
    }
    
    try {
      await dispatch(verifyEmail(otpCode)).unwrap()
      setVerified(true)
      setTimeout(() => {
        navigate('/')
      }, 2000)
    } catch (error) {
      console.error('OTP verification failed:', error)
    }
  }

  const handleResendOTP = () => {
    // Mock resend OTP functionality
    setTimeLeft(300)
    setCanResend(false)
    setOtp(['', '', '', '', '', ''])
    // In real app, this would call an API to resend OTP
  }

  const formatTime = (seconds) => {
    const mins = Math.floor(seconds / 60)
    const secs = seconds % 60
    return `${mins}:${secs.toString().padStart(2, '0')}`
  }

  if (verified) {
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
              Email Verified!
            </h2>
            
            <p className="text-beej-brown/70 mb-6">
              Your email has been successfully verified. 
              Redirecting you to your account...
            </p>
            
            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-beej-green mx-auto"></div>
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
            <Shield className="h-8 w-8 text-white" />
          </motion.div>
          
          <h2 className="text-3xl font-bold text-beej-green">
            Verify Your Email
          </h2>
          <p className="mt-2 text-beej-brown/60">
            We've sent a 6-digit code to:
            <br />
            <span className="font-medium text-beej-brown">{email}</span>
          </p>
        </div>

        {/* OTP Form */}
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

            {/* OTP Input Fields */}
            <div>
              <label className="block text-sm font-medium text-beej-brown mb-4">
                Enter verification code
              </label>
              <div className="flex justify-center space-x-2">
                {otp.map((digit, index) => (
                  <input
                    key={index}
                    id={`otp-${index}`}
                    type="text"
                    inputMode="numeric"
                    pattern="[0-9]"
                    maxLength={1}
                    value={digit}
                    onChange={(e) => handleOtpChange(index, e.target.value)}
                    onKeyDown={(e) => handleKeyDown(index, e)}
                    onPaste={handlePaste}
                    className="w-12 h-12 text-center text-xl font-bold border-2 border-beej-green/30 rounded-lg focus:outline-none focus:ring-2 focus:ring-beej-green focus:border-beej-green transition-colors"
                    autoFocus={index === 0}
                  />
                ))}
              </div>
            </div>

            {/* Timer */}
            <div className="text-center">
              <p className="text-sm text-beej-brown/60">
                Code expires in: <span className="font-medium text-beej-green">{formatTime(timeLeft)}</span>
              </p>
            </div>

            <Button
              type="submit"
              variant="primary"
              size="lg"
              loading={loading}
              className="w-full"
            >
              Verify Email
            </Button>
          </form>

          {/* Resend Section */}
          <div className="mt-6 pt-6 border-t border-beej-green/20">
            <div className="text-center">
              <p className="text-sm text-beej-brown/60 mb-3">
                Didn't receive the code?
              </p>
              
              {canResend ? (
                <Button
                  variant="outline"
                  size="sm"
                  onClick={handleResendOTP}
                  className="w-full"
                >
                  <RefreshCw className="mr-2 h-4 w-4" />
                  Resend Code
                </Button>
              ) : (
                <div className="text-sm text-beej-brown/50">
                  Resend code available in {formatTime(timeLeft)}
                </div>
              )}
            </div>
          </div>

          {/* Help Section */}
          <div className="text-center">
            <p className="text-sm text-beej-brown/60 mb-3">
              Need help? Contact us
            </p>
            <div className="flex items-center justify-center space-x-3">
              <Button
                variant="outline"
                size="sm"
                onClick={() => window.open('https://wa.me/918319143937?text=Hello%20I%20need%20help%20with%20OTP%20verification', '_blank')}
              >
                <span className="text-green-500">📱</span> WhatsApp
              </Button>
              <Button
                variant="outline"
                size="sm"
                onClick={() => window.open('mailto:support@beej.in?subject=OTP Verification Help', '_blank')}
              >
                <span>📧</span> Email
              </Button>
            </div>
          </div>
        </motion.div>

        {/* Tips */}
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.4, duration: 0.5 }}
          className="bg-beej-beige/30 rounded-xl p-4"
        >
          <h3 className="font-medium text-beej-brown mb-2">💡 Tips:</h3>
          <ul className="text-sm text-beej-brown/60 space-y-1">
            <li>• Check your spam folder if you don't see the email</li>
            <li>• The code is valid for 5 minutes</li>
            <li>• You can request a new code after expiration</li>
          </ul>
        </motion.div>
      </motion.div>
    </div>
  )
}

export default OTPVerificationPage
