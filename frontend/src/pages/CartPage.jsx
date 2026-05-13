import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { ShoppingCart, Plus, Minus, X, Trash2, ArrowLeft } from 'lucide-react';

const CartPage = () => {
  const [cartItems, setCartItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [couponCode, setCouponCode] = useState('');
  const [discount, setDiscount] = useState(0);

  useEffect(() => {
    const fetchCartItems = async () => {
      try {
        const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/cart/items`);
        const data = await response.json();
        setCartItems(data.data || []);
      } catch (error) {
        console.error('Error fetching cart items:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchCartItems();
  }, []);

  const updateQuantity = async (itemId, newQuantity) => {
    if (newQuantity < 1) return;
    
    try {
      const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/cart/items/${itemId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ quantity: newQuantity }),
      });
      
      if (response.ok) {
        const updatedItem = await response.json();
        setCartItems(prev => 
          prev.map(item => 
            item.id === itemId ? { ...item, ...updatedItem } : item
          )
        );
      }
    } catch (error) {
      console.error('Error updating quantity:', error);
    }
  };

  const removeFromCart = async (itemId) => {
    try {
      const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/cart/items/${itemId}`, {
        method: 'DELETE',
      });
      
      if (response.ok) {
        setCartItems(prev => prev.filter(item => item.id !== itemId));
      }
    } catch (error) {
      console.error('Error removing from cart:', error);
    }
  };

  const applyCoupon = async () => {
    if (!couponCode.trim()) return;
    
    try {
      const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/cart/apply-coupon`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ couponCode }),
      });
      
      if (response.ok) {
        const data = await response.json();
        setDiscount(data.discount || 0);
      }
    } catch (error) {
      console.error('Error applying coupon:', error);
    }
  };

  const subtotal = cartItems.reduce((sum, item) => sum + (item.price * item.quantity), 0);
  const total = Math.max(0, subtotal - discount);

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <div className="bg-white shadow-sm">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between h-16">
            <div className="flex items-center space-x-4">
              <Link to="/" className="text-2xl font-bold text-green-600 hover:text-green-700">
                Beej Seeds
              </Link>
            </div>
            
            <Link to="/products" className="relative p-2 text-gray-600 hover:text-green-600">
              <ShoppingCart className="h-6 w-6" />
              {cartItems.length > 0 && (
                <span className="absolute -top-1 -right-1 bg-red-500 text-white text-xs rounded-full h-5 w-5 flex items-center justify-center">
                  {cartItems.length}
                </span>
              )}
            </Link>
          </div>
        </div>
      </div>

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <h1 className="text-3xl font-bold text-gray-900 mb-8">
          Shopping Cart
        </h1>

        {loading ? (
          <div className="space-y-4">
            {[...Array(3)].map((_, index) => (
              <div key={index} className="bg-white rounded-lg shadow-md p-6">
                <div className="bg-gray-200 h-32 rounded-md mb-4 animate-pulse"></div>
                <div className="h-4 bg-gray-200 rounded mb-2"></div>
                <div className="h-4 bg-gray-200 rounded mb-2"></div>
              </div>
            ))}
          </div>
        ) : cartItems.length === 0 ? (
          <div className="text-center py-16">
            <ShoppingCart className="h-16 w-16 text-gray-400 mx-auto mb-4" />
            <h2 className="text-2xl font-semibold text-gray-900 mb-4">
              Your cart is empty
            </h2>
            <p className="text-gray-600 mb-8">
              Looks like you haven't added any products yet
            </p>
            <Link
              to="/products"
              className="bg-green-600 text-white px-8 py-3 rounded-lg font-semibold hover:bg-green-700 inline-flex items-center"
            >
              <ArrowLeft className="mr-2 h-5 w-5" />
              Continue Shopping
            </Link>
          </div>
        ) : (
          <div className="grid lg:grid-cols-3 gap-8">
            {/* Cart Items */}
            <div className="lg:col-span-2">
              <div className="bg-white rounded-lg shadow-md p-6">
                <h2 className="text-xl font-semibold text-gray-900 mb-6">
                  Cart Items ({cartItems.length})
                </h2>
                
                <div className="space-y-4">
                  {cartItems.map((item) => (
                    <div key={item.id} className="flex items-center space-x-4 p-4 border rounded-lg">
                      <div className="w-20 h-20 bg-gray-200 rounded-md flex-shrink-0">
                        <img 
                          src={item.image || '/api/placeholder/200x200'} 
                          alt={item.name}
                          className="w-full h-full object-cover rounded-md"
                        />
                      </div>
                      
                      <div className="flex-1">
                        <div className="flex justify-between items-start">
                          <div>
                            <h3 className="text-lg font-semibold text-gray-900">
                              {item.name}
                            </h3>
                            <p className="text-sm text-gray-600 mt-1">
                              {item.shortDescription}
                            </p>
                            <p className="text-xs text-gray-500 mt-1">
                              SKU: {item.sku}
                            </p>
                          </div>
                          
                          <div className="text-right">
                            <button
                              onClick={() => removeFromCart(item.id)}
                              className="text-red-600 hover:text-red-800 p-2"
                            >
                              <X className="h-4 w-4" />
                            </button>
                          </div>
                        </div>
                        
                        <div className="flex items-center justify-between mt-4">
                          <div className="flex items-center space-x-2">
                            <button
                              onClick={() => updateQuantity(item.id, Math.max(1, item.quantity - 1))}
                              className="bg-gray-100 text-gray-600 hover:bg-gray-200 p-1 rounded"
                            >
                              <Minus className="h-4 w-4" />
                            </button>
                            
                            <span className="px-4 py-1 bg-gray-50 rounded-md text-center font-semibold">
                              {item.quantity}
                            </span>
                            
                            <button
                              onClick={() => updateQuantity(item.id, item.quantity + 1)}
                              className="bg-gray-100 text-gray-600 hover:bg-gray-200 p-1 rounded"
                            >
                              <Plus className="h-4 w-4" />
                            </button>
                          </div>
                          
                          <span className="text-lg font-semibold text-gray-900">
                            ${item.price}
                          </span>
                        </div>
                        
                        <div className="text-sm text-gray-600">
                          ${item.price} each = ${(item.price * item.quantity).toFixed(2)}
                        </div>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            </div>

            {/* Cart Summary */}
            <div className="lg:col-span-1">
              <div className="bg-white rounded-lg shadow-md p-6">
                <h2 className="text-xl font-semibold text-gray-900 mb-6">
                  Cart Summary
                </h2>
                
                {/* Coupon Code */}
                <div className="mb-6">
                  <h3 className="text-sm font-medium text-gray-700 mb-3">
                    Coupon Code
                  </h3>
                  <div className="flex space-x-2">
                    <input
                      type="text"
                      placeholder="Enter coupon code"
                      value={couponCode}
                      onChange={(e) => setCouponCode(e.target.value)}
                      className="flex-1 px-3 py-2 border border-gray-300 rounded-md text-sm"
                    />
                    <button
                      onClick={applyCoupon}
                      className="bg-green-600 text-white px-4 py-2 rounded-md text-sm font-semibold hover:bg-green-700"
                    >
                      Apply
                    </button>
                  </div>
                  {discount > 0 && (
                    <div className="mt-2 text-sm text-green-600">
                      Coupon applied! You saved ${discount.toFixed(2)}
                    </div>
                  )}
                </div>

                {/* Order Summary */}
                <div className="space-y-3 border-t pt-4">
                  <div className="flex justify-between text-sm">
                    <span className="text-gray-600">Subtotal:</span>
                    <span className="font-semibold">${subtotal.toFixed(2)}</span>
                  </div>
                  
                  {discount > 0 && (
                    <div className="flex justify-between text-sm">
                      <span className="text-gray-600">Discount:</span>
                      <span className="font-semibold text-green-600">-${discount.toFixed(2)}</span>
                    </div>
                  )}
                  
                  <div className="flex justify-between text-sm">
                    <span className="text-gray-600">Shipping:</span>
                    <span className="font-semibold">Calculated at checkout</span>
                  </div>
                  
                  <div className="flex justify-between text-lg font-bold border-t pt-3">
                    <span>Total:</span>
                    <span className="text-green-600">${total.toFixed(2)}</span>
                  </div>
                </div>

                {/* Actions */}
                <div className="space-y-3 mt-6">
                  <Link
                    to="/products"
                    className="w-full bg-gray-100 text-gray-700 px-4 py-3 rounded-lg font-semibold hover:bg-gray-200 text-center"
                  >
                    <ArrowLeft className="inline h-4 w-4 mr-2" />
                    Continue Shopping
                  </Link>
                  
                  <button className="w-full bg-green-600 text-white px-4 py-3 rounded-lg font-semibold hover:bg-green-700">
                    Proceed to Checkout
                  </button>
                </div>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default CartPage;
