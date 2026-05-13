import React from 'react'
import { cn } from '../../utils/cn'

const Badge = React.forwardRef(({ className, variant = 'default', children, ...props }, ref) => {
  const variants = {
    default: 'bg-beej-green text-white hover:bg-beej-green-dark',
    secondary: 'bg-beej-beige text-beej-green hover:bg-beej-green/10',
    destructive: 'bg-red-500 text-white hover:bg-red-600',
    outline: 'border border-beej-green text-beej-green hover:bg-beej-green hover:text-white',
    success: 'bg-green-500 text-white hover:bg-green-600',
    warning: 'bg-yellow-500 text-white hover:bg-yellow-600',
    discount: 'bg-red-100 text-red-700 font-bold',
    new: 'bg-blue-100 text-blue-700 font-semibold',
    trending: 'bg-orange-100 text-orange-700 font-semibold',
  }

  return (
    <div
      ref={ref}
      className={cn(
        'inline-flex items-center rounded-full px-2.5 py-0.5 text-xs font-semibold transition-colors focus:outline-none focus:ring-2 focus:ring-beej-green focus:ring-offset-2',
        variants[variant],
        className
      )}
      {...props}
    >
      {children}
    </div>
  )
})

Badge.displayName = 'Badge'

export default Badge
