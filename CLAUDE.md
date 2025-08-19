# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Repository Overview

Multi-project Daily Tech media platform repository by Thomas Maestas containing five distinct applications:

1. **dailytech-media** - Node.js/Express radio streaming platform with PostgreSQL (fully implemented)
2. **dailytech-react** - React.js frontend application  
3. **dailytech-angular** - Angular frontend application with Material Design
4. **dailytech-rest** - Spring Boot REST API backend
5. **dailytech-events** - Kafka-based event processing system with OpenSearch integration

## Architecture

```
┌─────────────────────────────────────────────────────────────────────┐
│                        CLAUDE_CODE_ALL                             │
├─────────────────┬─────────────────┬─────────────────────────────────┤
│  dailytech-media│  dailytech-react│      dailytech-angular          │
│   (Node.js)     │   (React 18)    │      (Angular 14)               │
│   Express +     │   Redux Toolkit │      NgRx + Material            │
│   PostgreSQL    │   Bootstrap 5   │      Firebase + Crypto          │
├─────────────────┴─────────────────┴─────────────────────────────────┤
│                    dailytech-rest                                   │
│               (Spring Boot 3.3.9 + Java 17)                        │
│          Spring Cloud + Security + MySQL/H2                         │
├─────────────────────────────────────────────────────────────────────┤
│                    dailytech-events                                 │
│               (Gradle + Spring Boot 3.3.11 + Java 17)              │
│          Kafka Producer/Consumer + OpenSearch + Wikimedia           │
└─────────────────────────────────────────────────────────────────────┘
```

## Common Development Commands

### Daily Tech Media (Node.js Radio Platform)
```bash
cd dailytech-media

# Development
npm start                       # Start radio server (port 3000)
npm run dev                     # Alternative dev start
npm test                        # Run test suite (29 passing tests)  
npm run test:coverage           # Generate coverage reports
npm run db:init                 # Initialize PostgreSQL database

# Database operations
npm run db:migrate              # Run database migrations
npm run db:seed                 # Seed test data
```

### Daily Tech React (Frontend)  
```bash
cd dailytech-react

# Development
npm start                      # Start on port 3000
npm run start3001              # Start on port 3001  
npm run build                  # Production build
npm test                       # Run tests (54 passing tests)
npm test -- --watchAll=false   # Single test run
```

### Daily Tech Angular (Frontend)
```bash
cd dailytech-angular

# Development  
ng serve                       # Start dev server
npm run start                  # Alternative start
ng build                       # Build for production
ng test                        # Run unit tests
ng lint                        # Lint code
ng e2e                         # End-to-end tests

# PWA Build
npm run pwa                    # Build with service worker
```

### Daily Tech REST (Spring Boot API)
```bash
cd dailytech-rest

# Development
./mvnw spring-boot:run                    # Start application (port 8082)
./mvnw clean compile                      # Compile
./mvnw test                               # Run test suite (89 test methods)
./mvnw package                            # Create JAR

# Docker
docker build -t dailytech-rest .          # Build image
docker-compose up                         # Start with dependencies
```

### Daily Tech Events (Kafka Event Processing)
```bash
cd dailytech-events

# Development
./gradlew build                           # Build all modules
./gradlew bootRun                         # Run event processing system
./gradlew test                            # Run tests

# Producer (Wikimedia changes)
cd kafka-producer-wikimedia
./gradlew bootRun                         # Start Wikimedia producer

# Consumer (OpenSearch indexing)  
cd kafka-consumer-opensearch
./gradlew bootRun                         # Start OpenSearch consumer

# Infrastructure
docker-compose up -d                      # Start Kafka + OpenSearch
# Access OpenSearch at http://localhost:9200
# Access Conduktor admin at http://localhost:8080 (admin@conduktor.io / adminP4ss!)
```

## Key Architecture Patterns

### Daily Tech Media (Node.js)
- **Pattern**: Express.js server with PostgreSQL integration
- **Authentication**: Session-based auth with guest fingerprinting  
- **Streaming**: HLS audio streaming with AWS CloudFront integration
- **Database**: PostgreSQL (radio_db) with users, ratings, track_metadata tables
- **Features**: Real-time track ratings, duplicate prevention, metadata display
- **Testing**: Jest + Supertest (29 passing tests)

### Daily Tech React  
- **Pattern**: Create React App with Redux Toolkit
- **State Management**: Redux with RTK Query for API calls
- **Styling**: Bootstrap 5 + Reactstrap components
- **Routing**: React Router DOM v6
- **Structure**: `src/components/`, `src/state/`, `src/services/`
- **Testing**: Jest + React Testing Library (54 passing tests)
- **Features**: Posts with weblink citations, comments, news categories

### Daily Tech Angular
- **Pattern**: Angular 14 with NgRx state management  
- **UI Framework**: Angular Material + Flex Layout
- **State**: NgRx store with effects and entity management
- **Services**: Firebase integration, crypto features, NASA/Marvel APIs
- **Structure**: `src/app/components/`, `src/app/service/`, `src/app/reducers/`

### Daily Tech REST (Spring Boot)
- **Pattern**: Microservices-ready Spring Boot application
- **Architecture**: Spring Cloud (Config, Eureka, OpenFeign, Circuit Breaker)
- **Security**: Spring Security with JWT authentication
- **Database**: JPA with MySQL (prod) and H2 (test) profiles
- **Testing**: JUnit 5 + Mockito (89 test methods across 16 test files)
- **Features**: CRUD operations, weblink citations, role-based access
- **Documentation**: OpenAPI/Swagger integration
- **Structure**: `src/main/java/net/ourdailytech/`

### Daily Tech Events (Kafka + OpenSearch)
- **Pattern**: Event-driven architecture with Kafka streaming
- **Producer**: Wikimedia changes stream processing
- **Consumer**: OpenSearch document indexing and search
- **Build System**: Gradle with Spring Boot 3.3.11
- **Infrastructure**: Kafka, OpenSearch, Conduktor platform
- **Structure**: Multi-module Gradle project (`kafka-producer-wikimedia/`, `kafka-consumer-opensearch/`)

## Inter-Project Dependencies

### Database Systems
- **dailytech-media**: PostgreSQL (radio_db)
- **dailytech-rest**: MySQL (production), H2 (testing)
- **dailytech-react/angular**: API consumers

### Port Assignments
- **dailytech-media**: 3000 (default)
- **dailytech-react**: 3000 (default), 3001 (alternative)  
- **dailytech-angular**: 4200 (ng serve default)
- **dailytech-rest**: 8082 (configured port)
- **dailytech-events**: OpenSearch (9200), Dashboards (5601)

### API Integration Points
- React/Angular frontends consume dailytech-rest APIs
- **Base API URL**: `http://52.3.58.191:8082/api` (AWS production)
- **Local API URL**: `http://localhost:8082/api` (development)
- **JWT Authentication**: Token-based auth across frontend apps
- dailytech-media provides standalone radio streaming
- Shared authentication patterns across projects

## Testing Strategies

### Node.js (dailytech-media)
- **Framework**: Jest with Supertest (29 passing tests)
- **Coverage**: Built-in Jest coverage reporting
- **Database**: PostgreSQL test database isolation
- **Tests**: Authentication, ratings, streaming endpoints

### React (dailytech-react)  
- **Framework**: React Testing Library + Jest (54 passing tests)
- **Components**: Form components, validation, user interactions
- **Services**: API integration, CRUD operations, weblinks functionality
- **Redux**: State management, actions, reducers testing
- **Integration**: End-to-end workflows with mocked services

### Angular (dailytech-angular)
- **Framework**: Jasmine + Karma
- **E2E**: Protractor configuration
- **Services**: HTTP interceptor testing

### Spring Boot (dailytech-rest)
- **Framework**: JUnit 5 + Mockito (89 test methods)
- **Coverage**: Controllers, services, repositories, security
- **Integration**: Spring Boot Test slices with H2 database
- **Profiles**: Separate test configurations for dev/prod

## Deployment Considerations

### Containerization
- **dailytech-rest**: Full Docker + docker-compose setup with MySQL
- **dailytech-events**: Docker compose with Kafka + OpenSearch
- **dailytech-media**: PostgreSQL integration ready
- **Frontend apps**: Standard build outputs for static hosting

### Environment Management
- Each project maintains separate environment configurations
- Database connection strings isolated per project
- API endpoints configured per deployment environment

## Current Implementation Status

All projects are **fully implemented and production-ready**:

| Project | Implementation | Tests | Database | Status |
|---------|---------------|-------|----------|--------|
| dailytech-media | ✅ Complete | 29 passing | PostgreSQL ready | Production ready |
| dailytech-react | ✅ Complete | 54 passing | API integration | Production ready |
| dailytech-angular | ✅ Complete | Configured | Firebase + APIs | Production ready |
| dailytech-rest | ✅ Complete | 89 test methods | MySQL/H2 | Production ready |
| dailytech-events | ✅ Complete | Configured | Kafka + OpenSearch | Production ready |

## Development Workflow

1. **Backend First**: Start dailytech-rest for API development (port 8082)
2. **Database Setup**: All databases configured and initialized
3. **Frontend Integration**: Both React/Angular consume REST APIs  
4. **Media Platform**: Independent dailytech-media for streaming (port 3000)
5. **Event Processing**: Kafka streaming with OpenSearch indexing
6. **Testing**: Comprehensive test suites across all projects
7. **AWS Integration**: Production deployment endpoints configured