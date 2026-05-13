import React, { useState, useEffect } from 'react'
import { motion } from 'framer-motion'
import { Link } from 'react-router-dom'
import { 
  Package, 
  Truck, 
  CheckCircle, 
  Clock, 
  XCircle,
  Eye,
  Download,
  RefreshCw,
  Search,
  Filter
} from 'lucide-react'
import { useSelector, useDispatch } from 'react-redux'
import { fetchOrders, trackOrder, cancelOrder } from '../store/slices/orderSlice'
import Button from '../components/ui/Button'
import Input from '../components/ui/Input'
import Badge from '../components/ui/Badge'
import LoadingSpinner from '../components/ui/LoadingSpinner'

const OrdersPage = () => {
  const dispatch = useDispatch()
  const { orders, loading } = useSelector((state) => state.order)
  
  const [selectedStatus, setSelectedStatus] = useState('all')
  const [searchTerm, setSearchTerm] = useState('')
  const [expandedOrder, setExpandedOrder] = useState(null)

  useEffect(() => {
    dispatch(fetchOrders())
  }, [dispatch])

  const handleTrackOrder = (orderId) => {
    dispatch(trackOrder(orderId))
  }

  const handleCancelOrder = async (orderId) => {
    if (window.confirm('Are you sure you want to cancel this order?')) {
      try {
        await dispatch(cancelOrder(orderId)).unwrap()
        dispatch(fetchOrders())
      } catch (error) {
        console.error('Failed to cancel order:', error)
      }
    }
  }

  const getStatusIcon = (status) => {
    switch (status) {
      case 'pending':
        return <Clock className="h-4 w-4 text-yellow-500" />
      case 'confirmed':
        return <CheckCircle className="h-4 w-4 text-blue-500" />
      case 'shipped':
        return <Truck className="h-4 w-4 text-purple-500" />
      case 'delivered':
        return <CheckCircle className="h-4 w-4 text-green-500" />
      case 'cancelled':
        return <XCircle className="h-4 w-4 text-red-500" />
      default:
        return <Package className="h-4 w-4 text-gray-500" />
    }
  }

  const getStatusColor = (status) => {
    switch (status) {
      case 'pending':
        return 'bg-yellow-100 text-yellow-800'
      case 'confirmed':
        return 'bg-blue-100 text-blue-800'
      case 'shipped':
        return 'bg-purple-100 text-purple-800'
      case 'delivered':
        return 'bg-green-100 text-green-800'
      case 'cancelled':
        return 'bg-red-100 text-red-800'
      default:
        return 'bg-gray-100 text-gray-800'
    }
  }

  // Mock orders data for demonstration
  const mockOrders = [
    {
      id: 'BEEJ-2024-001',
      date: '2024-01-15',
      status: 'delivered',
      totalAmount: 1299,
      items: [
        { name: 'Premium Chia Seeds', quantity: 2, price: 499 },
        { name: 'Organic Flax Seeds', quantity: 1, price: 301 }
      ],
      shippingAddress: {
        name: 'John Doe',
        address: '123 Main St, Mumbai, Maharashtra 400001',
        phone: '+91 98765 43210'
      },
      trackingNumber: 'TRK123456789',
      estimatedDelivery: '2024-01-18'
    },
    {
      id: 'BEEJ-2024-002',
      date: '2024-01-18',
      status: 'shipped',
      totalAmount: 899,
      items: [
        { name: 'Pumpkin Seeds', quantity: 1, price: 399 },
        { name: 'Sunflower Seeds', quantity: 1, price: 500 }
      ],
      shippingAddress: {
        name: 'John Doe',
        address: '123 Main St, Mumbai, Maharashtra 400001',
        phone: '+91 98765 43210'
      },
      trackingNumber: 'TRK987654321',
      estimatedDelivery: '2024-01-22'
    },
    {
      id: 'BEEJ-2024-003',
      date: '2024-01-20',
      status: 'pending',
      totalAmount: 599,
      items: [
        { name: 'Sabja Seeds', quantity: 2, price: 299 }
      ],
      shippingAddress: {
        name: 'John Doe',
        address: '123 Main St, Mumbai, Maharashtra 400001',
        phone: '+91 98765 43210'
      },
      trackingNumber: null,
      estimatedDelivery: '2024-01-25'
    }
  ]

  const ordersData = orders.length > 0 ? orders : mockOrders

  const filteredOrders = ordersData.filter(order => {
    const matchesStatus = selectedStatus === 'all' || order.status === selectedStatus
    const matchesSearch = order.id.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         order.items.some(item => item.name.toLowerCase().includes(searchTerm.toLowerCase()))
    return matchesStatus && matchesSearch
  })

  const OrderCard = ({ order, index }) => (
    <motion.div
      key={order.id}
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.6, delay: index * 0.1 }}
      className="bg-white rounded-2xl shadow-lg hover:shadow-xl transition-shadow duration-300"
    >
      <div className="p-6">
        {/* Order Header */}
        <div className="flex items-center justify-between mb-4">
          <div>
            <h3 className="text-lg font-semibold text-beej-brown">{order.id}</h3>
            <p className="text-sm text-beej-brown/60">{new Date(order.date).toLocaleDateString()}</p>
          </div>
          <div className="flex items-center space-x-2">
            <Badge className={getStatusColor(order.status)}>
              <div className="flex items-center">
                {getStatusIcon(order.status)}
                <span className="ml-1 capitalize">{order.status}</span>
              </div>
            </Badge>
          </div>
        </div>

        {/* Order Items */}
        <div className="mb-4">
          <div className="flex items-center justify-between mb-2">
            <span className="text-sm text-beej-brown/60">Items ({order.items.length})</span>
            <span className="text-lg font-bold text-beej-green">₹{order.totalAmount}</span>
          </div>
          <div className="space-y-2">
            {order.items.map((item, idx) => (
              <div key={idx} className="flex items-center justify-between text-sm">
                <span className="text-beej-brown/70">{item.name} x {item.quantity}</span>
                <span className="text-beej-brown/60">₹{item.price * item.quantity}</span>
              </div>
            ))}
          </div>
        </div>

        {/* Shipping Info */}
        <div className="mb-4 p-3 bg-beej-beige/30 rounded-lg">
          <div className="text-sm text-beej-brown/60 mb-1">Shipping Address</div>
          <div className="text-sm text-beej-brown">{order.shippingAddress.name}</div>
          <div className="text-sm text-beej-brown/60">{order.shippingAddress.address}</div>
        </div>

        {/* Tracking Info */}
        {order.trackingNumber && (
          <div className="mb-4 p-3 bg-blue-50 rounded-lg">
            <div className="flex items-center justify-between">
              <div>
                <div className="text-sm text-beej-brown/60">Tracking Number</div>
                <div className="text-sm font-medium text-beej-brown">{order.trackingNumber}</div>
              </div>
              <Button
                variant="outline"
                size="sm"
                onClick={() => handleTrackOrder(order.id)}
              >
                <RefreshCw className="h-4 w-4 mr-1" />
                Track
              </Button>
            </div>
            {order.estimatedDelivery && (
              <div className="text-sm text-beej-brown/60 mt-2">
                Est. Delivery: {new Date(order.estimatedDelivery).toLocaleDateString()}
              </div>
            )}
          </div>
        )}

        {/* Action Buttons */}
        <div className="flex space-x-3">
          <Button
            variant="outline"
            size="sm"
            onClick={() => setExpandedOrder(expandedOrder === order.id ? null : order.id)}
            className="flex-1"
          >
            <Eye className="h-4 w-4 mr-1" />
            {expandedOrder === order.id ? 'Hide' : 'View'} Details
          </Button>
          
          {order.status === 'delivered' && (
            <Button variant="outline" size="sm">
              <Download className="h-4 w-4 mr-1" />
              Invoice
            </Button>
          )}
          
          {(order.status === 'pending' || order.status === 'confirmed') && (
            <Button
              variant="destructive"
              size="sm"
              onClick={() => handleCancelOrder(order.id)}
            >
              Cancel
            </Button>
          )}
        </div>

        {/* Expanded Details */}
        {expandedOrder === order.id && (
          <motion.div
            initial={{ opacity: 0, height: 0 }}
            animate={{ opacity: 1, height: 'auto' }}
            exit={{ opacity: 0, height: 0 }}
            className="mt-6 pt-6 border-t border-beej-green/20"
          >
            <div className="grid md:grid-cols-2 gap-6">
              <div>
                <h4 className="font-medium text-beej-brown mb-3">Order Timeline</h4>
                <div className="space-y-3">
                  <div className="flex items-center">
                    <div className="w-3 h-3 bg-green-500 rounded-full mr-3"></div>
                    <div>
                      <div className="text-sm font-medium text-beej-brown">Order Placed</div>
                      <div className="text-xs text-beej-brown/60">{new Date(order.date).toLocaleDateString()}</div>
                    </div>
                  </div>
                  {order.status !== 'pending' && (
                    <div className="flex items-center">
                      <div className="w-3 h-3 bg-blue-500 rounded-full mr-3"></div>
                      <div>
                        <div className="text-sm font-medium text-beej-brown">Order Confirmed</div>
                        <div className="text-xs text-beej-brown/60">{new Date(order.date).toLocaleDateString()}</div>
                      </div>
                    </div>
                  )}
                  {order.status === 'shipped' || order.status === 'delivered' ? (
                    <div className="flex items-center">
                      <div className="w-3 h-3 bg-purple-500 rounded-full mr-3"></div>
                      <div>
                        <div className="text-sm font-medium text-beej-brown">Order Shipped</div>
                        <div className="text-xs text-beej-brown/60">{new Date(order.date).toLocaleDateString()}</div>
                      </div>
                    </div>
                  ) : null}
                  {order.status === 'delivered' && (
                    <div className="flex items-center">
                      <div className="w-3 h-3 bg-green-500 rounded-full mr-3"></div>
                      <div>
                        <div className="text-sm font-medium text-beej-brown">Order Delivered</div>
                        <div className="text-xs text-beej-brown/60">{new Date(order.date).toLocaleDateString()}</div>
                      </div>
                    </div>
                  )}
                </div>
              </div>
              
              <div>
                <h4 className="font-medium text-beej-brown mb-3">Payment Summary</h4>
                <div className="space-y-2">
                  <div className="flex justify-between text-sm">
                    <span className="text-beej-brown/60">Subtotal</span>
                    <span className="text-beej-brown">₹{order.totalAmount}</span>
                  </div>
                  <div className="flex justify-between text-sm">
                    <span className="text-beej-brown/60">Shipping</span>
                    <span className="text-beej-green">FREE</span>
                  </div>
                  <div className="flex justify-between text-sm font-medium pt-2 border-t">
                    <span className="text-beej-brown">Total</span>
                    <span className="text-beej-green">₹{order.totalAmount}</span>
                  </div>
                </div>
              </div>
            </div>
          </motion.div>
        )}
      </div>
    </motion.div>
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
            <h1 className="text-2xl font-bold text-beej-green">My Orders</h1>
            <Button variant="outline" onClick={() => window.open('https://wa.me/918319143937?text=Hello%20I%20want%20to%20order%20BEEJ%20products', '_blank')}>
              <span className="text-green-500">📱</span> New Order
            </Button>
          </div>
        </div>
      </div>

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        {/* Filters */}
        <div className="bg-white rounded-2xl shadow-lg p-6 mb-8">
          <div className="flex flex-col md:flex-row gap-4">
            <div className="flex-1">
              <div className="relative">
                <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-beej-brown/50" />
                <Input
                  placeholder="Search orders..."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  className="pl-10"
                />
              </div>
            </div>
            
            <div className="flex space-x-2">
              <select
                value={selectedStatus}
                onChange={(e) => setSelectedStatus(e.target.value)}
                className="px-4 py-2 border border-beej-green/30 rounded-lg focus:outline-none focus:ring-2 focus:ring-beej-green"
              >
                <option value="all">All Orders</option>
                <option value="pending">Pending</option>
                <option value="confirmed">Confirmed</option>
                <option value="shipped">Shipped</option>
                <option value="delivered">Delivered</option>
                <option value="cancelled">Cancelled</option>
              </select>
            </div>
          </div>
        </div>

        {/* Orders List */}
        {filteredOrders.length > 0 ? (
          <div className="space-y-6">
            {filteredOrders.map((order, index) => (
              <OrderCard key={order.id} order={order} index={index} />
            ))}
          </div>
        ) : (
          <div className="text-center py-12">
            <Package className="h-16 w-16 text-beej-brown/30 mx-auto mb-4" />
            <h3 className="text-xl font-semibold text-beej-brown mb-2">
              No orders found
            </h3>
            <p className="text-beej-brown/60 mb-6">
              {searchTerm || selectedStatus !== 'all' 
                ? 'Try adjusting your filters or search terms'
                : 'You haven\'t placed any orders yet'
              }
            </p>
            <Link to="/products">
              <Button variant="primary">
                Start Shopping
              </Button>
            </Link>
          </div>
        )}
      </div>
    </div>
  )
}

export default OrdersPage
