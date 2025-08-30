# Radio Thomas1 🎵

> French-style online radio station with HLS lossless streaming and integrated rating system

![Live Stream](https://img.shields.io/badge/Stream-Live-brightgreen)
![Quality](https://img.shields.io/badge/Audio-HLS%20Lossless-blue)
![Platform](https://img.shields.io/badge/Platform-Full%20Stack-orange)

## 🎯 Overview

Radio Thomas1 is a modern web-based radio streaming platform inspired by "Radio NrG", featuring high-quality HLS streaming, real-time track metadata, and an interactive rating system. Built with Node.js, PostgreSQL, and vanilla JavaScript.

## ✨ Features

### 🎧 Streaming
- **HLS Lossless Streaming** - High-quality audio via AWS CloudFront CDN
- **Real-time Metadata** - Live track information with artist, album, year
- **Stream History** - Track previous songs played
- **Quality Display** - Show source quality (16-bit 44.1kHz) and stream quality (48kHz FLAC)

### 👥 User System
- **Flexible Authentication** - Username-only or email/password registration
- **Guest Mode** - Anonymous users via browser fingerprinting
- **Session Management** - Persistent login state

### 📊 Rating System
- **Track Ratings** - Thumbs up/down for individual tracks
- **Duplicate Prevention** - One vote per user per track
- **Real-time Counts** - Live rating statistics display
- **Guest Support** - Anonymous rating via fingerprinting

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   AWS CloudFront │────│   HLS Stream    │────│   Nginx Server │
│       CDN        │    │   + Metadata    │    │                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │
         ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│  Web Browser    │────│  Express.js API │────│   PostgreSQL    │
│  (HLS.js Player)│    │   (Node.js)     │    │    Database     │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## 🚀 Quick Start

### Prerequisites
- Node.js (v14+)
- PostgreSQL (v12+)
- Git

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd radio_thomas1
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Set up PostgreSQL database**
   ```bash
   # Create database and user
   sudo -u postgres createuser -P radio_user  # Enter password: radio_pass
   sudo -u postgres createdb -O radio_user radio_db
   
   # Initialize schema
   npm run db:init
   ```

4. **Create environment file**
   ```bash
   # Create .env file with:
   PORT=3009
   DB_HOST=localhost
   DB_PORT=5432
   DB_NAME=radio_db
   DB_USER=radio_user
   DB_PASSWORD=radio_pass
   SESSION_SECRET=your-secret-key-here
   ```

5. **Start the application**
   ```bash
   npm run dev
   ```

6. **Open in browser**
   ```
   http://localhost:3009
   ```

## 🛠️ Development

### Available Commands

```bash
# Start development server
npm run dev

# Initialize/reset database
npm run db:init

# Test database connection
curl http://localhost:3009/health

# Connect to database directly
PGPASSWORD=radio_pass psql -h localhost -U radio_user -d radio_db
```

### Project Structure

```
radio_thomas1/
├── server.js              # Express.js server with API endpoints
├── public/
│   ├── index.html         # Frontend with HLS player
│   └── images/            # Static assets
├── database/
│   └── init.sql          # Database schema and sample data
├── package.json          # Dependencies and scripts
└── .env                  # Environment configuration
```

### API Endpoints

#### Authentication
- `POST /api/register` - User registration
- `POST /api/login` - User login
- `POST /api/logout` - User logout
- `GET /api/user` - Get current user info

#### Ratings
- `POST /api/rate-track` - Submit track rating
- `GET /api/track-ratings` - Get ratings for track

#### Health
- `GET /health` - Database connection status

## 🎵 Stream Configuration

- **Stream URL**: `https://d3d4yli4hf5bmh.cloudfront.net/hls/live.m3u8`
- **Metadata URL**: `https://d3d4yli4hf5bmh.cloudfront.net/metadatav2.json`
- **Format**: HLS (HTTP Live Streaming)
- **Quality**: 48kHz FLAC/Lossless

## 🗄️ Database Schema

### Core Tables
- **`users`** - Registered user accounts
- **`guest_users`** - Anonymous user fingerprints
- **`track_ratings`** - User ratings for tracks
- **`track_metadata`** - Track information storage

## 🔮 Future Enhancements

- [ ] **Google Play Integration** - Personal playlist preferences
- [ ] **Docker Containerization** - Easy deployment
- [ ] **Unit Testing** - Comprehensive test coverage
- [ ] **GitHub Actions** - CI/CD with automated AI code review
- [ ] **Mobile App** - Native iOS/Android applications
- [ ] **Playlist System** - Custom user playlists

## 👨‍💻 Author

**Thomas Maestas**  
*Full-stack Developer*

---

*Built with ❤️ using Node.js, PostgreSQL, and modern web technologies*
