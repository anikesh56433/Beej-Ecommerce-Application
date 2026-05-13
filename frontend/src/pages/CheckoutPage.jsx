import React, { useState } from 'react'
import { motion } from 'framer-motion'
import { useNavigate } from 'react-router-dom'
import { 
  User, 
  MapPin, 
  Phone, 
  Mail, 
  CreditCard, 
  Truck, 
  Shield, 
  Check,
  ArrowLeft,
  Plus,
  Minus
} from 'lucide-react'
import { useSelector, useDispatch } from 'react-redux'
import { createOrder } from '../store/slices/orderSlice'
import Button from '../components/ui/Button'
import Input from '../components/ui/Input'
import Badge from '../components/ui/Badge'

const CheckoutPage = () => {
  const navigate = useNavigate()
  const dispatch = useDispatch()
  const { items, totalAmount } = useSelector((state) => state.cart)
  
  const [shippingAddress, setShippingAddress] = useState({
    firstName: '',
    lastName: '',
    email: '',
    phone: '',
    address: '',
    city: '',
    state: '',
    pincode: '',
    country: 'India'
  })
  
  const [paymentMethod, setPaymentMethod] = useState('cod')
  const [orderNotes, setOrderNotes] = useState('')
  const [loading, setLoading] = useState(false)
  const [orderPlaced, setOrderPlaced] = useState(false)

  const handleInputChange = (e) => {
    setShippingAddress({
      ...shippingAddress,
      [e.target.name]: e.target.value
    })
  }

  const handlePlaceOrder = async () => {
    setLoading(true)
    
    try {
      const orderData = {
        items,
        shippingAddress,
        paymentMethod,
        totalAmount,
        notes: orderNotes
      }
      
      const result = await dispatch(createOrder(orderData)).unwrap()
      setOrderPlaced(true)
      
      // Redirect to WhatsApp for order confirmation
      setTimeout(() => {
        const message = `Hello%20I%20have%20placed%20order%20%23${result.id}%20for%20₹${totalAmount}`
        window.open(`https://wa.me/918319143937?text=${message}`, '_blank')
        navigate('/orders')
      }, 3000)
      
    } catch (error) {
      console.error('Order placement failed:', error)
    } finally {
      setLoading(false)
    }
  }

  if (orderPlaced) {
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
              Order Placed Successfully!
            </h2>
            
            <p className="text-beej-brown/70 mb-6">
              Thank you for your order. We'll redirect you to WhatsApp for order confirmation.
            </p>
            
            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-beej-green mx-auto"></div>
          </div>
        </motion.div>
      </div>
    )
  }

  if (items.length === 0) {
    return (
      <div className="min-h-screen bg-beej-beige flex items-center justify-center">
        <div className="text-center">
          <h2 className="text-2xl font-bold text-beej-brown mb-4">Your cart is empty</h2>
          <Button variant="primary" onClick={() => navigate('/products')}>
            Continue Shopping
          </Button>
        </div>
      </div>
    )
  }

  return (
    <div className="min-h-screen bg-beej-beige">
      {/* Header */}
      <div className="bg-white border-b border-beej-green/20">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
          <div className="flex items-center justify-between">
            <button
              onClick={() => navigate('/cart')}
              className="flex items-center text-beej-brown/60 hover:text-beej-green transition-colors"
            >
              <ArrowLeft className="h-4 w-4 mr-2" />
              Back to Cart
            </button>
            <h1 className="text-2xl font-bold text-beej-green">Checkout</h1>
            <div className="w-20"></div>
          </div>
        </div>
      </div>

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="grid lg:grid-cols-3 gap-8">
          {/* Checkout Form */}
          <div className="lg:col-span-2 space-y-8">
            {/* Shipping Address */}
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.6 }}
              className="bg-white rounded-2xl shadow-lg p-8"
            >
              <h2 className="text-xl font-semibold text-beej-brown mb-6 flex items-center">
                <MapPin className="h-5 w-5 mr-2" />
                Shipping Address
              </h2>
              
              <div className="grid md:grid-cols-2 gap-6">
                <Input
                  name="firstName"
                  label="First Name"
                  value={shippingAddress.firstName}
                  onChange={handleInputChange}
                  required
                />
                <Input
                  name="lastName"
                  label="Last Name"
                  value={shippingAddress.lastName}
                  onChange={handleInputChange}
                  required
                />
              </div>
              
              <div className="grid md:grid-cols-2 gap-6">
                <Input
                  name="email"
                  type="email"
                  label="Email Address"
                  value={shippingAddress.email}
                  onChange={handleInputChange}
                  required
                />
                <Input
                  name="phone"
                  type="tel"
                  label="Phone Number"
                  value={shippingAddress.phone}
                  onChange={handleInputChange}
                  required
                />
              </div>
              
              <Input
                name="address"
                label="Street Address"
                value={shippingAddress.address}
                onChange={handleInputChange}
                required
              />
              
              <div className="grid md:grid-cols-3 gap-6">
                <Input
                  name="city"
                  label="City"
                  value={shippingAddress.city}
                  onChange={handleInputChange}
                  required
                />
                <Input
                  name="state"
                  label="State"
                  value={shippingAddress.state}
                  onChange={handleInputChange}
                  required
                />
                <Input
                  name="pincode"
                  label="PIN Code"
                  value={shippingAddress.pincode}
                  onChange={handleInputChange}
                  required
                />
              </div>
            </motion.div>

            {/* Payment Method */}
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.6, delay: 0.2 }}
              className="bg-white rounded-2xl shadow-lg p-8"
            >
              <h2 className="text-xl font-semibold text-beej-brown mb-6 flex items-center">
                <CreditCard className="h-5 w-5 mr-2" />
                Payment Method
              </h2>
              
              <div className="space-y-4">
                <label className="flex items-center p-4 border border-beej-green/30 rounded-lg cursor-pointer hover:bg-beej-green/5 transition-colors">
                  <input
                    type="radio"
                    name="payment"
                    value="cod"
                    checked={paymentMethod === 'cod'}
                    onChange={(e) => setPaymentMethod(e.target.value)}
                    className="mr-3"
                  />
                  <div className="flex-1">
                    <div className="font-medium text-beej-brown">Cash on Delivery</div>
                    <div className="text-sm text-beej-brown/60">Pay when you receive your order</div>
                  </div>
                </label>
                
                <label className="flex items-center p-4 border border-beej-green/30 rounded-lg cursor-pointer hover:bg-beej-green/5 transition-colors">
                  <input
                    type="radio"
                    name="payment"
                    value="online"
                    checked={paymentMethod === 'online'}
                    onChange={(e) => setPaymentMethod(e.target.value)}
                    className="mr-3"
                  />
                  <div className="flex-1">
                    <div className="font-medium text-beej-brown">Online Payment</div>
                    <div className="text-sm text-beej-brown/60">Pay via UPI, Card, or Net Banking</div>
                  </div>
                </label>
              </div>
            </motion.div>

            {/* Order Notes */}
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.6, delay: 0.4 }}
              className="bg-white rounded-2xl shadow-lg p-8"
            >
              <h2 className="text-xl font-semibold text-beej-brown mb-6">Order Notes (Optional)</h2>
              <textarea
                value={orderNotes}
                onChange={(e) => setOrderNotes(e.target.value)}
                rows={4}
                placeholder="Any special instructions for delivery..."
                className="w-full px-4 py-3 border border-beej-green/30 rounded-lg focus:outline-none focus:ring-2 focus:ring-beej-green focus:border-beej-green transition-colors"
              />
            </motion.div>
          </div>

          {/* Order Summary */}
          <div className="lg:col-span-1">
            <motion.div
              initial={{ opacity: 0, x: 20 }}
              animate={{ opacity: 1, x: 0 }}
              transition={{ duration: 0.6, delay: 0.3 }}
              className="bg-white rounded-2xl shadow-lg p-8 sticky top-8"
            >
              <h2 className="text-xl font-semibold text-beej-brown mb-6">Order Summary</h2>
              
              {/* Items */}
              <div className="space-y-4 mb-6">
                {items.map((item, index) => (
                  <div key={index} className="flex items-center justify-between">
                    <div className="flex-1">
                      <h4 className="text-sm font-medium text-beej-brown">{item.name}</h4>
                      <p className="text-xs text-beej-brown/60">Qty: {item.quantity}</p>
                    </div>
                    <div className="text-sm font-medium text-beej-brown">
                      ₹{item.price * item.quantity}
                    </div>
                  </div>
                ))}
              </div>
              
              {/* Summary */}
              <div className="border-t border-beej-green/20 pt-4 space-y-2">
                <div className="flex justify-between text-sm">
                  <span className="text-beej-brown/60">Subtotal</span>
                  <span className="text-beej-brown">₹{totalAmount}</span>
                </div>
                <div className="flex justify-between text-sm">
                  <span className="text-beej-brown/60">Shipping</span>
                  <span className="text-beej-green">FREE</span>
                </div>
                <div className="flex justify-between text-sm">
                  <span className="text-beej-brown/60">Tax</span>
                  <span className="text-beej-brown">₹0</span>
                </div>
                <div className="border-t border-beej-green/20 pt-2">
                  <div className="flex justify-between">
                    <span className="font-semibold text-beej-brown">Total</span>
                    <span className="text-xl font-bold text-beej-green">₹{totalAmount}</span>
                  </div>
                </div>
              </div>
              
              {/* Trust Badges */}
              <div className="mt-6 space-y-3">
                <div className="flex items-center text-sm text-beej-brown/60">
                  <Truck className="h-4 w-4 mr-2 text-beej-green" />
                  Free shipping on orders above ₹499
                </div>
                <div className="flex items-center text-sm text-beej-brown/60">
                  <Shield className="h-4 w-4 mr-2 text-beej-green" />
                  Secure payment processing
                </div>
              </div>
              
              {/* Place Order Button */}
              <Button
                variant="primary"
                size="lg"
                onClick={handlePlaceOrder}
                loading={loading}
                className="w-full mt-6"
              >
                Place Order
              </Button>
              
              <p className="text-xs text-beej-brown/50 text-center mt-4">
                By placing this order, you agree to our Terms & Conditions
              </p>
            </motion.div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default CheckoutPage
