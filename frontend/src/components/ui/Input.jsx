import React from 'react'
import { cn } from '../../utils/cn'

const Input = React.forwardRef(({ 
  className, 
  type = 'text', 
  label, 
  error, 
  helperText, 
  ...props 
}, ref) => {
  return (
    <div className="space-y-2">
      {label && (
        <label 
          htmlFor={props.id}
          className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
        >
          {label}
        </label>
      )}
      <input
        type={type}
        className={cn(
          'flex h-10 w-full rounded-md border border-beej-green/30 bg-white px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-beej-brown/50 focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-beej-green focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50 transition-all duration-200',
          error && 'border-red-500 focus-visible:ring-red-500',
          className
        )}
        ref={ref}
        {...props}
      />
      {error && (
        <p className="text-sm text-destructive">{error}</p>
      )}
      {helperText && !error && (
        <p className="text-sm text-muted-foreground">{helperText}</p>
      )}
    </div>
  )
})

Input.displayName = 'Input'

export default Input
