import React from 'react'
import { cn } from '../../utils/cn'

const Button = React.forwardRef(({ 
  className, 
  variant = 'primary', 
  size = 'md', 
  children, 
  disabled = false,
  loading = false,
  ...props 
}, ref) => {
  const baseClasses = 'btn inline-flex items-center justify-center rounded-md text-sm font-medium transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:opacity-50 disabled:pointer-events-none ring-offset-background'
  
  const variants = {
    primary: 'bg-beej-green text-white hover:bg-beej-green-dark shadow-md hover:shadow-lg transition-all duration-200',
    secondary: 'bg-beej-beige text-beej-green hover:bg-beej-green/10 border border-beej-green/20',
    outline: 'border-2 border-beej-green text-beej-green hover:bg-beej-green hover:text-white transition-all duration-200',
    destructive: 'bg-red-500 text-white hover:bg-red-600 shadow-md hover:shadow-lg transition-all duration-200',
    ghost: 'hover:bg-beej-green/10 text-beej-green hover:text-beej-green-dark transition-all duration-200',
    link: 'text-beej-green underline-offset-4 hover:underline hover:text-beej-green-dark transition-all duration-200',
    'whatsapp': 'bg-green-500 text-white hover:bg-green-600 shadow-md hover:shadow-lg transition-all duration-200',
    'premium': 'bg-gradient-to-r from-beej-green to-beej-green-light text-white hover:from-beej-green-dark hover:to-beej-green shadow-lg hover:shadow-xl transition-all duration-300',
  }
  
  const sizes = {
    sm: 'h-9 px-3 rounded-md text-xs',
    md: 'h-10 py-2 px-4',
    lg: 'h-11 px-8 rounded-md',
    icon: 'h-10 w-10',
  }

  return (
    <button
      className={cn(
        baseClasses,
        variants[variant],
        sizes[size],
        className
      )}
      ref={ref}
      disabled={disabled || loading}
      {...props}
    >
      {loading && (
        <svg 
          className="animate-spin -ml-1 mr-2 h-4 w-4" 
          xmlns="http://www.w3.org/2000/svg" 
          fill="none" 
          viewBox="0 0 24 24"
        >
          <circle 
            className="opacity-25" 
            cx="12" 
            cy="12" 
            r="10" 
            stroke="currentColor" 
            strokeWidth="4"
          />
          <path 
            className="opacity-75" 
            fill="currentColor" 
            d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
          />
        </svg>
      )}
      {children}
    </button>
  )
})

Button.displayName = 'Button'

export default Button
