# Hook4Startup 🚀

A social media platform designed to connect entrepreneurs, startup founders, and potential partners to build the next big thing together.

## 🌟 Overview

Hook4Startup is a comprehensive platform that helps entrepreneurs find co-founders, team members, and partners for their startup ventures. Whether you're looking for a technical co-founder, a business partner, or want to join an exciting startup, Hook4Startup connects like-minded individuals in the startup ecosystem.

## ✨ Features

### 🎯 Core Functionality

- 🔐 **User Authentication & Authorization** - Secure login/signup with session management
- 👤 **User Profiles** - Detailed profiles showcasing skills, experience, and startup interests
- 📱 **Social Feed** - Share updates, ideas, and connect with the community
- ✍️ **Post Creation & Interaction** - Create posts, like, and comment on content
- 🔔 **Real-time Notifications** - Stay updated with meetups and platform activities
- 🔍 **Search & Discovery** - Find potential partners based on skills and interests
- 🤝 **Meetup System** - Organize and participate in startup events and networking sessions
- 📸 **Image Upload** - Profile pictures and post images via Cloudinary integration

### ⚡ Technical Features

- 💬 **Real-time Messaging** - RabbitMQ integration for instant notifications
- ☁️ **Cloud Storage** - Cloudinary for image management
- 📱 **Responsive Design** - Mobile-first approach with modern UI
- 📲 **Progressive Web App** - PWA capabilities for mobile experience

## 🛠️ Tech Stack

### 🖥️ Backend

- ☕ **Java 17** - Core programming language
- 🍃 **Spring Boot 3.2.5** - Application framework
- 🔒 **Spring Security** - Authentication and authorization
- 🍃 **Spring Data MongoDB** - Database integration
- 🐰 **RabbitMQ** - Message queuing for real-time features
- ☁️ **Cloudinary** - Image upload and management
- 📦 **Maven** - Dependency management

### 🎨 Frontend

- ⚛️ **React 18** - UI framework
- ⚡ **Vite** - Build tool and development server
- 🛣️ **React Router DOM** - Client-side routing
- 🌐 **Axios** - HTTP client for API calls
- 🎨 **Tailwind CSS** - Utility-first CSS framework
- 📝 **React Hook Form** - Form handling
- 🎭 **React Icons** - Icon library
- 📱 **PWA Support** - Progressive Web App features

### 🗄️ Database

- 🍃 **MongoDB** - NoSQL database for flexible data storage

## 🚀 Getting Started

### 📋 Prerequisites

- ☕ Java 17 or higher
- 🟢 Node.js 16+ and npm/pnpm
- 🍃 MongoDB instance
- 🐰 RabbitMQ server
- ☁️ Cloudinary account (for image uploads)

### 🖥️ Backend Setup

1. **📥 Clone the repository**

   ```bash
   git clone <repository-url>
   cd Hook4Startup-backend
   ```

2. **⚙️ Configure application properties**
   Update `src/main/resources/application.yml` with your database and service configurations:

   ```yaml
   # Add your MongoDB, RabbitMQ, and Cloudinary configurations
   ```

3. **▶️ Run the application**

   ```bash
   # Using Maven wrapper
   ./mvnw spring-boot:run

   # Or using Maven directly
   mvn spring-boot:run
   ```

   The backend will start on `http://localhost:8080` 🌐

### 🎨 Frontend Setup

1. **📂 Navigate to frontend directory**

   ```bash
   cd hook-4-startup-client
   ```

2. **📦 Install dependencies**

   ```bash
   # Using pnpm (recommended)
   pnpm install

   # Or using npm
   npm install
   ```

3. **🚀 Start development server**

   ```bash
   # Development server with host binding
   pnpm dev

   # Or standard development server
   pnpm chacha
   ```

   The frontend will start on `http://localhost:5173` 🌐

## 📁 Project Structure

### 🖥️ Backend Structure

```
Hook4Startup-backend/
├── src/main/java/com/hook4startup/
│   ├── Component/          # Message receivers and components
│   ├── Dto/               # Data Transfer Objects
│   ├── Filter/            # Security filters
│   ├── config/            # Configuration classes
│   ├── controller/        # REST API controllers
│   ├── models/            # Entity models
│   ├── repository/        # Data access layer
│   └── services/          # Business logic layer
├── src/main/resources/
│   ├── application.yml    # Application configuration
│   └── static/           # Static web resources
└── pom.xml               # Maven dependencies
```

### 🎨 Frontend Structure

```
hook-4-startup-client/
├── src/
│   ├── components/        # Reusable UI components
│   ├── pages/            # Page components
│   ├── context/          # React context providers
│   └── assets/           # Static assets
├── public/               # Public assets
└── package.json          # Node.js dependencies
```

## 🔧 API Endpoints

### 🔐 Authentication

- `POST /auth/login` - 🚪 User login
- `POST /auth/signup` - 📝 User registration

### 👤 User Management

- `GET /user/profile` - 👁️ Get user profile
- `POST /user/profile/create` - ➕ Create user profile
- `PUT /user/profile/edit` - ✏️ Update user profile

### 📱 Posts & Social Features

- `GET /posts` - 📋 Get all posts
- `POST /posts/create` - ✍️ Create new post
- `POST /posts/{id}/like` - ❤️ Like/unlike post
- `POST /posts/{id}/comment` - 💬 Add comment

### 🤝 Meetups & Events

- `GET /meetups` - 📅 Get meetup events
- `POST /meetups/create` - 🎉 Create meetup event

## 🌐 Deployment

The project includes deployment configuration:

- 🐳 `Dockerfile` for containerization
- 🚀 `render.yaml` for Render.com deployment
- 📦 Built static files served from Spring Boot

## 🤝 Contributing

1. 🍴 Fork the repository
2. 🌿 Create a feature branch (`git checkout -b feature/amazing-feature`)
3. 💾 Commit your changes (`git commit -m 'Add amazing feature'`)
4. 📤 Push to the branch (`git push origin feature/amazing-feature`)
5. 🔄 Open a Pull Request

## 📝 License

This project is open source and available under the [MIT License](LICENSE).

## 🎯 Future Enhancements

- [ ] 🧠 Advanced matching algorithm for partner recommendations
- [ ] 📹 Video call integration for virtual meetups
- [ ] 🛠️ Startup project collaboration tools
- [ ] 💰 Investment tracking and funding connections
- [ ] 🎯 Skills-based filtering and recommendations
- [ ] 📱 Mobile app development

## 📞 Support

For support and questions, please open an issue in the repository or contact the development team.

## 🏆 Project Stats

![GitHub stars](https://img.shields.io/github/stars/yourusername/Hook4Startup?style=social)
![GitHub forks](https://img.shields.io/github/forks/yourusername/Hook4Startup?style=social)
![GitHub issues](https://img.shields.io/github/issues/yourusername/Hook4Startup)
![GitHub license](https://img.shields.io/github/license/yourusername/Hook4Startup)

---

**Built with ❤️ for the startup community**
