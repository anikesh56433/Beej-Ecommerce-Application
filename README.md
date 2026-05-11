# Beej E-Commerce Platform

A modern, full-stack e-commerce platform built with Spring Boot (backend) and React (frontend).

## 🚀 Tech Stack

### Backend
- **Java 17** with Spring Boot 3.2.0
- **Spring Security** with JWT authentication
- **Spring Data JPA** for data persistence
- **MySQL 8.0** database
- **SpringDoc OpenAPI** for API documentation
- **Maven** for dependency management

### Frontend
- **React 18** with modern hooks
- **Vite** for fast development and building
- **Redux Toolkit** for state management
- **React Router** for navigation
- **Tailwind CSS** for styling
- **Axios** for API calls
- **React Hook Form** for form management

### DevOps
- **Docker** & **Docker Compose** for containerization
- **Jenkins** for CI/CD pipeline

## 📁 Project Structure

```
beej-ecommerce/
├── backend/                 # Spring Boot application
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/beej/
│   │   │   │   ├── auth/           # Authentication module
│   │   │   │   ├── user/           # User management
│   │   │   │   ├── product/        # Product catalog
│   │   │   │   ├── cart/           # Shopping cart
│   │   │   │   ├── order/          # Order management
│   │   │   │   ├── payment/        # Payment processing
│   │   │   │   ├── wishlist/       # Wishlist functionality
│   │   │   │   ├── review/         # Product reviews
│   │   │   │   ├── admin/          # Admin dashboard
│   │   │   │   ├── notification/   # Notifications
│   │   │   │   ├── security/       # Security configuration
│   │   │   │   ├── common/         # Common utilities
│   │   │   │   └── exception/      # Exception handling
│   │   │   └── resources/
│   │   └── test/
│   ├── pom.xml
│   └── Dockerfile
├── frontend/                # React application
│   ├── src/
│   │   ├── api/             # API configuration
│   │   ├── components/      # Reusable components
│   │   ├── pages/           # Page components
│   │   ├── services/        # Business logic services
│   │   ├── store/           # Redux store
│   │   ├── routes/          # Route configuration
│   │   ├── hooks/           # Custom hooks
│   │   ├── utils/           # Utility functions
│   │   └── styles/          # Global styles
│   ├── package.json
│   ├── vite.config.js
│   └── Dockerfile
├── docs/                    # Documentation
├── docker-compose.yml
├── .gitignore
└── README.md
```

## 🛠️ Features

### Core E-Commerce Features
- ✅ User authentication & authorization (JWT)
- ✅ Product catalog with categories
- ✅ Shopping cart management
- ✅ Order processing
- ✅ Payment integration
- ✅ Wishlist functionality
- ✅ Product reviews & ratings
- ✅ Admin dashboard
- ✅ Search & filtering
- ✅ Responsive design

### Advanced Features (Planned)
- 🔄 AI-powered recommendations
- 🔄 Multi-vendor support
- 🔄 Mobile app (React Native)
- 🔄 Advanced analytics dashboard
- 🔄 Email notifications
- 🔄 Coupon & discount system
- 🔄 Inventory management

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Node.js 18+
- MySQL 8.0+
- Docker & Docker Compose (optional)

### Using Docker Compose (Recommended)

1. Clone the repository:
```bash
git clone <repository-url>
cd beej-ecommerce
```

2. Start all services:
```bash
docker-compose up -d
```

3. Access the applications:
- Frontend: http://localhost:3000
- Backend API: http://localhost:8080
- API Documentation: http://localhost:8080/swagger-ui.html

### Manual Setup

#### Backend
1. Navigate to backend directory:
```bash
cd backend
```

2. Update database configuration in `application.yml`

3. Run the application:
```bash
mvn spring-boot:run
```

#### Frontend
1. Navigate to frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start development server:
```bash
npm run dev
```

## 📚 API Documentation

Once the backend is running, you can access the interactive API documentation at:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## 🧪 Testing

### Backend Tests
```bash
cd backend
mvn test
```

### Frontend Tests
```bash
cd frontend
npm test
```

## 🔧 Configuration

### Environment Variables

#### Backend (.env)
```
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/beej_ecommerce
SPRING_DATASOURCE_USERNAME=your_username
SPRING_DATASOURCE_PASSWORD=your_password
JWT_SECRET=your_jwt_secret_key
JWT_EXPIRATION=86400000
```

#### Frontend (.env)
```
VITE_API_BASE_URL=http://localhost:8080/api
VITE_APP_NAME=Beej E-Commerce
```

## 📦 Deployment

### Production Deployment with Docker
```bash
docker-compose -f docker-compose.prod.yml up -d
```

### Build & Deploy Backend
```bash
cd backend
mvn clean package
java -jar target/beej-ecommerce-backend-1.0.0.jar
```

### Build & Deploy Frontend
```bash
cd frontend
npm run build
# Deploy the 'dist' folder to your web server
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

For support, please contact the development team or create an issue in the repository.

---

**Beej E-Commerce Platform** - Building the future of online shopping! 🛒✨
