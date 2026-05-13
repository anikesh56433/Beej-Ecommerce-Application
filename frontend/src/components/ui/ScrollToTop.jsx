import React, { useState, useEffect } from 'react'
import { motion } from 'framer-motion'
import { ChevronUp } from 'lucide-react'
import { useSelector, useDispatch } from 'react-redux'
import { selectScrollToTopVisible, setScrollToTopVisible } from '../../store/slices/uiSlice'

const ScrollToTop = () => {
  const dispatch = useDispatch()
  const isVisible = useSelector(selectScrollToTopVisible)
  const [lastScrollY, setLastScrollY] = useState(0)

  useEffect(() => {
    const handleScroll = () => {
      const currentScrollY = window.scrollY
      const shouldShow = currentScrollY > 300

      if (shouldShow !== isVisible) {
        dispatch(setScrollToTopVisible(shouldShow))
      }
      
      setLastScrollY(currentScrollY)
    }

    window.addEventListener('scroll', handleScroll, { passive: true })
    return () => window.removeEventListener('scroll', handleScroll)
  }, [isVisible, dispatch])

  const scrollToTop = () => {
    window.scrollTo({
      top: 0,
      behavior: 'smooth'
    })
  }

  return (
    <motion.div
      className="fixed bottom-8 right-8 z-40"
      initial={{ opacity: 0, scale: 0 }}
      animate={{ 
        opacity: isVisible ? 1 : 0, 
        scale: isVisible ? 1 : 0,
        transition: { duration: 0.3 }
      }}
      whileHover={{ scale: 1.1 }}
      whileTap={{ scale: 0.9 }}
    >
      <button
        onClick={scrollToTop}
        className="bg-beej-green text-white p-3 rounded-full shadow-lg hover:bg-beej-green-dark transition-colors duration-200 hover:shadow-xl"
        aria-label="Scroll to top"
      >
        <ChevronUp className="h-6 w-6" />
      </button>
    </motion.div>
  )
}

export default ScrollToTop
