# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Repository Overview

Multi-project Daily Tech media platform repository by Thomas Maestas containing five distinct applications:

1. **dailytech-media** - Node.js/Express radio streaming platform with PostgreSQL (currently in development)
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

# Note: This project is currently in development - main server file may be missing
# Development commands will be available once implementation is complete
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
./mvnw spring-boot:run                    # Start application
./mvnw clean compile                      # Compile
./mvnw test                               # Run tests  
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
- **Pattern**: Express.js server with PostgreSQL integration (in development)
- **Authentication**: Session-based auth with guest fingerprinting
- **Streaming**: HLS audio streaming with real-time metadata
- **Database**: PostgreSQL with connection pooling
- **Structure**: Project structure being established

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
- **dailytech-rest**: 8080 (Spring Boot default)

### API Integration Points
- React/Angular frontends consume dailytech-rest APIs
- dailytech-media provides standalone radio streaming
- Shared authentication patterns across projects

## Testing Strategies

### Node.js (dailytech-media)
- **Framework**: Jest with Supertest
- **Coverage**: Built-in Jest coverage reporting
- **Database**: Test database isolation

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
- **Framework**: JUnit 5 + Mockito
- **Integration**: Spring Boot Test slices
- **Profiles**: Separate test configurations

## Deployment Considerations

### Containerization
- **dailytech-rest**: Full Docker + docker-compose setup
- **dailytech-media**: Dockerization planned
- **Frontend apps**: Standard build outputs for static hosting

### Environment Management
- Each project maintains separate environment configurations
- Database connection strings isolated per project
- API endpoints configured per deployment environment

## Development Workflow

1. **Backend First**: Start dailytech-rest for API development
2. **Database Setup**: Initialize project-specific databases  
3. **Frontend Integration**: Connect React/Angular to REST APIs
4. **Media Platform**: Independent dailytech-media for streaming features
5. **Testing**: Run project-specific test suites
6. **Integration**: Cross-project API testing