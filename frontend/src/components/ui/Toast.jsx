import React from 'react'
import { cn } from '../../utils/cn'
import { X, CheckCircle, AlertCircle, Info, AlertTriangle } from 'lucide-react'

const Toast = ({ type = 'info', message, onClose, className }) => {
  const icons = {
    success: <CheckCircle className="h-5 w-5 text-green-500" />,
    error: <AlertCircle className="h-5 w-5 text-red-500" />,
    warning: <AlertTriangle className="h-5 w-5 text-yellow-500" />,
    info: <Info className="h-5 w-5 text-blue-500" />,
  }

  const backgrounds = {
    success: 'bg-green-50 border-green-200',
    error: 'bg-red-50 border-red-200',
    warning: 'bg-yellow-50 border-yellow-200',
    info: 'bg-blue-50 border-blue-200',
  }

  return (
    <div
      className={cn(
        'fixed top-4 right-4 z-50 flex items-center gap-3 rounded-lg border p-4 shadow-lg transition-all duration-300 animate-in slide-in-from-right',
        backgrounds[type],
        className
      )}
    >
      {icons[type]}
      <p className="text-sm font-medium text-gray-900">{message}</p>
      <button
        onClick={onClose}
        className="ml-auto h-4 w-4 rounded-sm opacity-70 ring-offset-background transition-opacity hover:opacity-100 focus:outline-none focus:ring-2 focus:ring-beej-green focus:ring-offset-2"
      >
        <X className="h-4 w-4" />
        <span className="sr-only">Close</span>
      </button>
    </div>
  )
}

export default Toast
