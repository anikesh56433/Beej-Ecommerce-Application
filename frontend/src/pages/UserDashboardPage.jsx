import React, { useState, useEffect } from 'react'
import { motion } from 'framer-motion'
import { Link, useNavigate } from 'react-router-dom'
import { 
  User, 
  MapPin, 
  Phone, 
  Mail, 
  Calendar,
  Package,
  Heart,
  Settings,
  LogOut,
  Eye,
  Edit,
  Download,
  TrendingUp,
  Award,
  Clock
} from 'lucide-react'
import { useSelector, useDispatch } from 'react-redux'
import { logout, updateProfile } from '../store/slices/authSlice'
import { fetchOrders } from '../store/slices/orderSlice'
import { fetchWishlist } from '../store/slices/wishlistSlice'
import Button from '../components/ui/Button'
import Input from '../components/ui/Input'
import Badge from '../components/ui/Badge'
import LoadingSpinner from '../components/ui/LoadingSpinner'

const UserDashboardPage = () => {
  const dispatch = useDispatch()
  const navigate = useNavigate()
  const { user, loading } = useSelector((state) => state.auth)
  const { orders } = useSelector((state) => state.order)
  const { items: wishlistItems } = useSelector((state) => state.wishlist)
  
  const [activeTab, setActiveTab] = useState('overview')
  const [editMode, setEditMode] = useState(false)
  const [profileData, setProfileData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    phone: '',
    address: '',
    city: '',
    state: '',
    pincode: ''
  })

  useEffect(() => {
    dispatch(fetchOrders())
    dispatch(fetchWishlist())
    
    if (user) {
      setProfileData({
        firstName: user.firstName || '',
        lastName: user.lastName || '',
        email: user.email || '',
        phone: user.phone || '',
        address: user.address || '',
        city: user.city || '',
        state: user.state || '',
        pincode: user.pincode || ''
      })
    }
  }, [dispatch, user])

  const handleProfileUpdate = async (e) => {
    e.preventDefault()
    try {
      await dispatch(updateProfile(profileData)).unwrap()
      setEditMode(false)
    } catch (error) {
      console.error('Profile update failed:', error)
    }
  }

  const handleLogout = () => {
    dispatch(logout())
    navigate('/')
  }

  const tabs = [
    { id: 'overview', label: 'Overview', icon: User },
    { id: 'orders', label: 'Orders', icon: Package },
    { id: 'wishlist', label: 'Wishlist', icon: Heart },
    { id: 'settings', label: 'Settings', icon: Settings }
  ]

  // Mock data for demonstration
  const stats = {
    totalOrders: orders.length || 12,
    totalSpent: 8999,
    memberSince: user?.createdAt ? new Date(user.createdAt).toLocaleDateString() : 'Jan 2024',
    loyaltyPoints: 450
  }

  const recentOrders = orders.slice(0, 3) || [
    {
      id: 'BEEJ-2024-001',
      date: '2024-01-15',
      status: 'delivered',
      totalAmount: 1299,
      items: 3
    },
    {
      id: 'BEEJ-2024-002',
      date: '2024-01-10',
      status: 'shipped',
      totalAmount: 899,
      items: 2
    },
    {
      id: 'BEEJ-2024-003',
      date: '2024-01-05',
      status: 'pending',
      totalAmount: 599,
      items: 1
    }
  ]

  const renderOverview = () => (
    <div className="space-y-8">
      {/* Stats Cards */}
      <div className="grid md:grid-cols-4 gap-6">
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.6 }}
          className="bg-white rounded-xl p-6 shadow-lg"
        >
          <div className="flex items-center justify-between mb-4">
            <Package className="h-8 w-8 text-beej-green" />
            <Badge variant="secondary">{stats.totalOrders}</Badge>
          </div>
          <h3 className="text-2xl font-bold text-beej-brown">{stats.totalOrders}</h3>
          <p className="text-beej-brown/60 text-sm">Total Orders</p>
        </motion.div>

        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.6, delay: 0.1 }}
          className="bg-white rounded-xl p-6 shadow-lg"
        >
          <div className="flex items-center justify-between mb-4">
            <TrendingUp className="h-8 w-8 text-beej-green" />
            <Badge variant="secondary">₹</Badge>
          </div>
          <h3 className="text-2xl font-bold text-beej-brown">₹{stats.totalSpent}</h3>
          <p className="text-beej-brown/60 text-sm">Total Spent</p>
        </motion.div>

        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.6, delay: 0.2 }}
          className="bg-white rounded-xl p-6 shadow-lg"
        >
          <div className="flex items-center justify-between mb-4">
            <Calendar className="h-8 w-8 text-beej-green" />
            <Badge variant="secondary">📅</Badge>
          </div>
          <h3 className="text-2xl font-bold text-beej-brown">{stats.memberSince}</h3>
          <p className="text-beej-brown/60 text-sm">Member Since</p>
        </motion.div>

        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.6, delay: 0.3 }}
          className="bg-white rounded-xl p-6 shadow-lg"
        >
          <div className="flex items-center justify-between mb-4">
            <Award className="h-8 w-8 text-beej-green" />
            <Badge variant="secondary">⭐</Badge>
          </div>
          <h3 className="text-2xl font-bold text-beej-brown">{stats.loyaltyPoints}</h3>
          <p className="text-beej-brown/60 text-sm">Loyalty Points</p>
        </motion.div>
      </div>

      {/* Recent Orders */}
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6, delay: 0.4 }}
        className="bg-white rounded-2xl shadow-lg p-8"
      >
        <div className="flex items-center justify-between mb-6">
          <h2 className="text-xl font-semibold text-beej-brown">Recent Orders</h2>
          <Link to="/orders">
            <Button variant="outline" size="sm">View All</Button>
          </Link>
        </div>
        
        <div className="space-y-4">
          {recentOrders.map((order, index) => (
            <div key={order.id} className="flex items-center justify-between p-4 border border-beej-green/20 rounded-lg">
              <div className="flex-1">
                <div className="flex items-center space-x-4">
                  <div>
                    <h4 className="font-medium text-beej-brown">{order.id}</h4>
                    <p className="text-sm text-beej-brown/60">{new Date(order.date).toLocaleDateString()}</p>
                  </div>
                  <div className="text-center">
                    <p className="text-sm text-beej-brown/60">{order.items} items</p>
                    <p className="font-medium text-beej-green">₹{order.totalAmount}</p>
                  </div>
                  <div>
                    <Badge className={
                      order.status === 'delivered' ? 'bg-green-100 text-green-800' :
                      order.status === 'shipped' ? 'bg-purple-100 text-purple-800' :
                      'bg-yellow-100 text-yellow-800'
                    }>
                      {order.status}
                    </Badge>
                  </div>
                </div>
              </div>
              <Button variant="outline" size="sm">
                <Eye className="h-4 w-4" />
              </Button>
            </div>
          ))}
        </div>
      </motion.div>

      {/* Quick Actions */}
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6, delay: 0.5 }}
        className="grid md:grid-cols-3 gap-6"
      >
        <Link to="/products" className="block">
          <div className="bg-white rounded-xl p-6 shadow-lg hover:shadow-xl transition-shadow text-center">
            <Package className="h-12 w-12 text-beej-green mx-auto mb-4" />
            <h3 className="font-semibold text-beej-brown mb-2">Continue Shopping</h3>
            <p className="text-beej-brown/60 text-sm">Browse our products</p>
          </div>
        </Link>
        
        <Link to="/wishlist" className="block">
          <div className="bg-white rounded-xl p-6 shadow-lg hover:shadow-xl transition-shadow text-center">
            <Heart className="h-12 w-12 text-beej-green mx-auto mb-4" />
            <h3 className="font-semibold text-beej-brown mb-2">View Wishlist</h3>
            <p className="text-beej-brown/60 text-sm">{wishlistItems.length} items saved</p>
          </div>
        </Link>
        
        <button
          onClick={() => window.open('https://wa.me/918319143937?text=Hello%20I%20want%20to%20order%20BEEJ%20products', '_blank')}
          className="block w-full"
        >
          <div className="bg-white rounded-xl p-6 shadow-lg hover:shadow-xl transition-shadow text-center">
            <div className="text-3xl mb-4">📱</div>
            <h3 className="font-semibold text-beej-brown mb-2">WhatsApp Support</h3>
            <p className="text-beej-brown/60 text-sm">Get instant help</p>
          </div>
        </button>
      </motion.div>
    </div>
  )

  const renderOrders = () => (
    <div className="bg-white rounded-2xl shadow-lg p-8">
      <div className="flex items-center justify-between mb-6">
        <h2 className="text-xl font-semibold text-beej-brown">Order History</h2>
        <Link to="/orders">
          <Button variant="primary">View All Orders</Button>
        </Link>
      </div>
      
      <div className="space-y-4">
        {recentOrders.map((order) => (
          <div key={order.id} className="border border-beej-green/20 rounded-lg p-6">
            <div className="flex items-center justify-between">
              <div>
                <h3 className="font-semibold text-beej-brown">{order.id}</h3>
                <p className="text-sm text-beej-brown/60">{new Date(order.date).toLocaleDateString()}</p>
              </div>
              <div className="text-right">
                <p className="font-medium text-beej-green">₹{order.totalAmount}</p>
                <Badge className={
                  order.status === 'delivered' ? 'bg-green-100 text-green-800' :
                  order.status === 'shipped' ? 'bg-purple-100 text-purple-800' :
                  'bg-yellow-100 text-yellow-800'
                }>
                  {order.status}
                </Badge>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  )

  const renderWishlist = () => (
    <div className="bg-white rounded-2xl shadow-lg p-8">
      <div className="flex items-center justify-between mb-6">
        <h2 className="text-xl font-semibold text-beej-brown">My Wishlist</h2>
        <Link to="/products">
          <Button variant="primary">Browse Products</Button>
        </Link>
      </div>
      
      {wishlistItems.length > 0 ? (
        <div className="grid md:grid-cols-3 gap-6">
          {wishlistItems.slice(0, 6).map((item, index) => (
            <div key={index} className="border border-beej-green/20 rounded-lg p-4">
              <div className="aspect-square bg-beej-green/10 rounded-lg mb-4 flex items-center justify-center">
                <div className="text-4xl">🌱</div>
              </div>
              <h3 className="font-medium text-beej-brown mb-2">{item.name || 'Product Name'}</h3>
              <p className="text-beej-green font-bold mb-4">₹{item.price || 299}</p>
              <Button variant="primary" size="sm" className="w-full">
                Add to Cart
              </Button>
            </div>
          ))}
        </div>
      ) : (
        <div className="text-center py-12">
          <Heart className="h-16 w-16 text-beej-brown/30 mx-auto mb-4" />
          <h3 className="text-xl font-semibold text-beej-brown mb-2">Your wishlist is empty</h3>
          <p className="text-beej-brown/60 mb-6">Save your favorite products for later</p>
          <Link to="/products">
            <Button variant="primary">Start Shopping</Button>
          </Link>
        </div>
      )}
    </div>
  )

  const renderSettings = () => (
    <div className="space-y-8">
      {/* Profile Information */}
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6 }}
        className="bg-white rounded-2xl shadow-lg p-8"
      >
        <div className="flex items-center justify-between mb-6">
          <h2 className="text-xl font-semibold text-beej-brown">Profile Information</h2>
          <Button
            variant="outline"
            size="sm"
            onClick={() => setEditMode(!editMode)}
          >
            <Edit className="h-4 w-4 mr-1" />
            {editMode ? 'Cancel' : 'Edit'}
          </Button>
        </div>

        {editMode ? (
          <form onSubmit={handleProfileUpdate} className="space-y-6">
            <div className="grid md:grid-cols-2 gap-6">
              <Input
                name="firstName"
                label="First Name"
                value={profileData.firstName}
                onChange={(e) => setProfileData({...profileData, firstName: e.target.value})}
              />
              <Input
                name="lastName"
                label="Last Name"
                value={profileData.lastName}
                onChange={(e) => setProfileData({...profileData, lastName: e.target.value})}
              />
            </div>
            
            <Input
              name="email"
              type="email"
              label="Email Address"
              value={profileData.email}
              onChange={(e) => setProfileData({...profileData, email: e.target.value})}
              disabled
            />
            
            <Input
              name="phone"
              type="tel"
              label="Phone Number"
              value={profileData.phone}
              onChange={(e) => setProfileData({...profileData, phone: e.target.value})}
            />
            
            <Input
              name="address"
              label="Address"
              value={profileData.address}
              onChange={(e) => setProfileData({...profileData, address: e.target.value})}
            />
            
            <div className="grid md:grid-cols-3 gap-6">
              <Input
                name="city"
                label="City"
                value={profileData.city}
                onChange={(e) => setProfileData({...profileData, city: e.target.value})}
              />
              <Input
                name="state"
                label="State"
                value={profileData.state}
                onChange={(e) => setProfileData({...profileData, state: e.target.value})}
              />
              <Input
                name="pincode"
                label="PIN Code"
                value={profileData.pincode}
                onChange={(e) => setProfileData({...profileData, pincode: e.target.value})}
              />
            </div>
            
            <div className="flex space-x-4">
              <Button type="submit" variant="primary" loading={loading}>
                Save Changes
              </Button>
              <Button type="button" variant="outline" onClick={() => setEditMode(false)}>
                Cancel
              </Button>
            </div>
          </form>
        ) : (
          <div className="space-y-4">
            <div className="grid md:grid-cols-2 gap-6">
              <div>
                <p className="text-sm text-beej-brown/60">Full Name</p>
                <p className="font-medium text-beej-brown">{user?.firstName} {user?.lastName}</p>
              </div>
              <div>
                <p className="text-sm text-beej-brown/60">Email</p>
                <p className="font-medium text-beej-brown">{user?.email}</p>
              </div>
              <div>
                <p className="text-sm text-beej-brown/60">Phone</p>
                <p className="font-medium text-beej-brown">{user?.phone || 'Not provided'}</p>
              </div>
              <div>
                <p className="text-sm text-beej-brown/60">Member Since</p>
                <p className="font-medium text-beej-brown">{stats.memberSince}</p>
              </div>
            </div>
            
            {(profileData.address || profileData.city) && (
              <div className="pt-4 border-t">
                <p className="text-sm text-beej-brown/60 mb-2">Address</p>
                <p className="font-medium text-beej-brown">
                  {profileData.address && `${profileData.address}, `}
                  {profileData.city && `${profileData.city}, `}
                  {profileData.state && `${profileData.state} `}
                  {profileData.pincode && profileData.pincode}
                </p>
              </div>
            )}
          </div>
        )}
      </motion.div>

      {/* Account Actions */}
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6, delay: 0.2 }}
        className="bg-white rounded-2xl shadow-lg p-8"
      >
        <h2 className="text-xl font-semibold text-beej-brown mb-6">Account Actions</h2>
        
        <div className="space-y-4">
          <div className="flex items-center justify-between p-4 border border-beej-green/20 rounded-lg">
            <div>
              <h3 className="font-medium text-beej-brown">Download Order History</h3>
              <p className="text-sm text-beej-brown/60">Get a PDF of all your orders</p>
            </div>
            <Button variant="outline" size="sm">
              <Download className="h-4 w-4 mr-1" />
              Download
            </Button>
          </div>
          
          <div className="flex items-center justify-between p-4 border border-red-200 rounded-lg">
            <div>
              <h3 className="font-medium text-red-600">Delete Account</h3>
              <p className="text-sm text-beej-brown/60">Permanently delete your account</p>
            </div>
            <Button variant="destructive" size="sm">
              Delete
            </Button>
          </div>
        </div>
      </motion.div>
    </div>
  )

  if (loading) {
    return (
      <div className="min-h-screen bg-beej-beige flex items-center justify-center">
        <LoadingSpinner size="xl" />
      </div>
    )
  }

  return (
    <div className="min-h-screen bg-beej-beige">
      {/* Header */}
      <div className="bg-white border-b border-beej-green/20">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
          <div className="flex items-center justify-between">
            <h1 className="text-2xl font-bold text-beej-green">My Dashboard</h1>
            <Button variant="outline" onClick={handleLogout}>
              <LogOut className="h-4 w-4 mr-2" />
              Logout
            </Button>
          </div>
        </div>
      </div>

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="grid lg:grid-cols-4 gap-8">
          {/* Sidebar */}
          <div className="lg:col-span-1">
            <div className="bg-white rounded-2xl shadow-lg p-6">
              <div className="text-center mb-6">
                <div className="w-20 h-20 bg-beej-green rounded-full flex items-center justify-center mx-auto mb-4">
                  <User className="h-10 w-10 text-white" />
                </div>
                <h3 className="font-semibold text-beej-brown">
                  {user?.firstName} {user?.lastName}
                </h3>
                <p className="text-sm text-beej-brown/60">{user?.email}</p>
              </div>
              
              <nav className="space-y-2">
                {tabs.map((tab) => (
                  <button
                    key={tab.id}
                    onClick={() => setActiveTab(tab.id)}
                    className={`w-full flex items-center space-x-3 px-4 py-3 rounded-lg transition-colors ${
                      activeTab === tab.id
                        ? 'bg-beej-green text-white'
                        : 'text-beej-brown hover:bg-beej-green/10'
                    }`}
                  >
                    <tab.icon className="h-5 w-5" />
                    <span>{tab.label}</span>
                  </button>
                ))}
              </nav>
            </div>
          </div>

          {/* Content */}
          <div className="lg:col-span-3">
            {activeTab === 'overview' && renderOverview()}
            {activeTab === 'orders' && renderOrders()}
            {activeTab === 'wishlist' && renderWishlist()}
            {activeTab === 'settings' && renderSettings()}
          </div>
        </div>
      </div>
    </div>
  )
}

export default UserDashboardPage
