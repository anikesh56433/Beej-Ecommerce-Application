import React from 'react'
import { cn } from '../../utils/cn'

const SkeletonLoader = ({ className, variant = 'default', ...props }) => {
  const variants = {
    default: 'h-4 w-full',
    text: 'h-4 w-full',
    title: 'h-8 w-3/4',
    avatar: 'h-10 w-10 rounded-full',
    button: 'h-10 w-20',
    card: 'h-32 w-full',
    image: 'h-48 w-full',
    product: 'h-64 w-full',
    line: 'h-2 w-full',
    circle: 'h-12 w-12 rounded-full',
  }

  return (
    <div
      className={cn(
        'animate-pulse rounded-md bg-beej-beige/50',
        variants[variant],
        className
      )}
      {...props}
    />
  )
}

const ProductCardSkeleton = () => (
  <div className="bg-white rounded-lg border border-beej-green/20 overflow-hidden">
    <SkeletonLoader variant="product" className="rounded-t-lg" />
    <div className="p-4 space-y-3">
      <SkeletonLoader variant="title" />
      <SkeletonLoader variant="text" />
      <SkeletonLoader variant="text" className="w-2/3" />
      <div className="flex items-center justify-between">
        <SkeletonLoader variant="button" />
        <SkeletonLoader variant="button" className="w-16" />
      </div>
    </div>
  </div>
)

const CardSkeleton = () => (
  <div className="bg-white rounded-lg border border-beej-green/20 p-6 space-y-4">
    <SkeletonLoader variant="circle" />
    <SkeletonLoader variant="title" />
    <SkeletonLoader variant="text" />
    <SkeletonLoader variant="text" className="w-4/5" />
    <SkeletonLoader variant="button" className="w-full" />
  </div>
)

const ListSkeleton = ({ items = 3 }) => (
  <div className="space-y-4">
    {Array.from({ length: items }).map((_, i) => (
      <div key={i} className="flex items-center space-x-4">
        <SkeletonLoader variant="avatar" />
        <div className="flex-1 space-y-2">
          <SkeletonLoader variant="text" className="w-1/3" />
          <SkeletonLoader variant="text" className="w-2/3" />
        </div>
        <SkeletonLoader variant="button" />
      </div>
    ))}
  </div>
)

export { SkeletonLoader, ProductCardSkeleton, CardSkeleton, ListSkeleton }
