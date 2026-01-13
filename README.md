# 🏦 AurumOne – HNI Wealth & Relationship Management Platform

## Overview
AurumOne is an **AI-augmented, agentic wealth management platform** designed for HNI/UHNI client management with personalized advisory, automated workflows, and regulatory compliance.

## Architecture
- **Style**: Microservices
- **Frontend**: React + Tailwind CSS
- **Backend**: Spring Boot (Java 17+)
- **Databases**: PostgreSQL, ElasticSearch
- **Event Bus**: Apache Kafka
- **Container Orchestration**: Kubernetes
- **Cloud**: AWS/Azure compatible

## Key Features
- 360° HNI Client View
- AI-Powered Advisory Engine
- Automated RM Workflows
- Real-time Portfolio Management
- Regulatory Compliance Engine
- Event-Driven Architecture

## Project Structure
```
├── backend/
│   ├── services/              # Microservices
│   │   ├── client-management/
│   │   ├── relationship-management/
│   │   ├── portfolio-management/
│   │   ├── advisory-engine/
│   │   ├── product-catalog/
│   │   ├── risk-suitability/
│   │   └── compliance/
│   └── shared/                # Shared libraries
│       ├── domain/
│       ├── security/
│       └── events/
├── frontend/                  # React application
├── infrastructure/            # K8s, Docker, Terraform
└── docs/                      # Documentation
```

## Getting Started

### Prerequisites
- Java 17+
- Node.js 18+
- Docker & Docker Compose
- Maven 3.8+
- PostgreSQL 14+

### Local Development

#### Backend Services
```bash
cd backend/services/client-management
mvn spring-boot:run
```

#### Frontend
```bash
cd frontend
npm install
npm start
```

### Docker Compose
```bash
docker-compose up -d
```

## Core Bounded Contexts
1. **ClientManagement** - Client profiles, KYC, net worth tracking
2. **RelationshipManagement** - RM dashboard, client interactions
3. **PortfolioManagement** - Asset allocation, performance tracking
4. **AdvisoryEngine** - AI-powered recommendations
5. **ProductCatalog** - Investment products
6. **RiskAndSuitability** - Risk profiling, suitability checks
7. **ComplianceAndRegulatory** - AML, KYC, audit trails

## Security
- **Authentication**: OAuth2 / OIDC
- **Authorization**: RBAC + ABAC
- **Encryption**: AES-256 (at rest), TLS 1.3 (in transit)
- **Zero Trust Architecture**

## API Documentation
- OpenAPI specs available at: `/docs/api/`
- Swagger UI: `http://localhost:8080/swagger-ui.html`

## License
Proprietary - AurumOne Platform
