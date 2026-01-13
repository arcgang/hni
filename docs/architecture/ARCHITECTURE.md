# AurumOne - System Architecture

## 1. Architecture Overview

AurumOne follows a **microservices architecture** with event-driven communication, designed for scalability, resilience, and AI-augmented capabilities.

```
┌─────────────────────────────────────────────────────────────────┐
│                        Frontend Layer                            │
│         React + Tailwind CSS (RM Dashboard & Client Portal)     │
└────────────────────────┬────────────────────────────────────────┘
                         │
                    ┌────▼────┐
                    │ Ingress │
                    └────┬────┘
                         │
        ┌────────────────┼────────────────┐
        │                │                │
   ┌────▼────┐    ┌─────▼─────┐   ┌─────▼─────┐
   │ Client  │    │ Portfolio │   │ Advisory  │
   │  Mgmt   │    │   Mgmt    │   │  Engine   │
   └────┬────┘    └─────┬─────┘   └─────┬─────┘
        │                │                │
        └────────────────┼────────────────┘
                         │
                    ┌────▼────┐
                    │  Kafka  │
                    │ (Events)│
                    └────┬────┘
                         │
        ┌────────────────┼────────────────┐
        │                │                │
   ┌────▼────┐    ┌─────▼─────┐   ┌─────▼─────┐
   │Postgres │    │ElasticS...│   │   Redis   │
   └─────────┘    └───────────┘   └───────────┘
```

## 2. Architecture Patterns

### 2.1 Domain-Driven Design (DDD)

**Bounded Contexts**:
1. **ClientManagement** - Client profiles, KYC, net worth
2. **RelationshipManagement** - RM workflows, interactions
3. **PortfolioManagement** - Assets, holdings, performance
4. **AdvisoryEngine** - AI recommendations, suitability
5. **ProductCatalog** - Investment products
6. **RiskAndSuitability** - Risk profiling, compliance
7. **ComplianceAndRegulatory** - AML, KYC, audit

### 2.2 Event-Driven Architecture

**Event Flow**:
```
ClientCreatedEvent → PortfolioService (create default portfolio)
                   → ComplianceService (initiate KYC)
                   → RMService (assign relationship manager)

PortfolioUpdatedEvent → AdvisoryEngine (analyze for recommendations)
                      → RiskService (check risk limits)
                      → ReportingService (update analytics)

RecommendationGeneratedEvent → ComplianceService (suitability check)
                             → RMService (notify RM)
                             → AuditService (log recommendation)
```

### 2.3 CQRS (Command Query Responsibility Segregation)

- **Commands**: Write operations (Create, Update, Delete)
- **Queries**: Read operations (Get, Search, Analytics)
- **ElasticSearch**: Optimized for complex queries and analytics

## 3. Technology Stack

### 3.1 Backend
- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Build**: Maven 3.8+
- **API**: REST (OpenAPI/Swagger)

### 3.2 Frontend
- **Framework**: React 18
- **Styling**: Tailwind CSS
- **Build**: Vite
- **State**: React Hooks

### 3.3 Data Layer
- **OLTP**: PostgreSQL 15
- **Search/Analytics**: ElasticSearch 8.11
- **Cache**: Redis 7
- **Object Storage**: S3-compatible

### 3.4 Messaging
- **Event Bus**: Apache Kafka 3.5
- **Protocol**: CloudEvents

### 3.5 Infrastructure
- **Containers**: Docker
- **Orchestration**: Kubernetes
- **Cloud**: AWS/Azure compatible
- **CI/CD**: GitHub Actions

## 4. Security Architecture

### 4.1 Zero Trust Security
```
User → OAuth2/OIDC → API Gateway → RBAC → Service
                                  → ABAC
                                  → Audit Log
```

### 4.2 Security Measures
- **Authentication**: OAuth2 / OIDC
- **Authorization**: RBAC (Role-Based) + ABAC (Attribute-Based)
- **Encryption at Rest**: AES-256
- **Encryption in Transit**: TLS 1.3
- **Secrets Management**: Kubernetes Secrets / AWS Secrets Manager
- **Network Policies**: Zero Trust with service mesh

## 5. AI/ML Architecture

### 5.1 AI Advisory Agent
```
Client Profile ──┐
Portfolio Data ──┼─→ RAG System ──→ LLM ──→ Recommendation
Market Data ────┤                            + Rationale
Risk Rules ─────┘                            + Risk Disclosure
```

### 5.2 AI Components
- **RAG (Retrieval-Augmented Generation)**: Context retrieval
- **LLM**: GPT-4 / Claude for reasoning
- **Vector DB**: Pinecone / Weaviate for embeddings
- **Explainability**: Mandatory rationale for all recommendations
- **Suitability Engine**: Rules-based validation

### 5.3 Human-in-the-Loop
- All AI recommendations require RM review
- Audit trail for every AI decision
- Override capability with justification

## 6. Data Model

### 6.1 Core Entities

**Client**:
- clientId (UUID, PK)
- segment (HNI/UHNI)
- riskProfile (Conservative/Moderate/Aggressive)
- kycStatus (Pending/Verified/Expired)
- netWorth (Money value object)

**Portfolio**:
- portfolioId (UUID, PK)
- clientId (UUID, FK)
- holdings (List of PortfolioHolding)
- totalValue (Money)

**Product**:
- productId (UUID, PK)
- productType (Equity/Bond/Fund/PMS/AIF)
- riskRating (1-10)
- minRiskProfile

## 7. Observability

### 7.1 Monitoring Stack
- **Metrics**: Prometheus + Grafana
- **Logging**: ELK Stack (ElasticSearch, Logstash, Kibana)
- **Tracing**: Jaeger / Zipkin
- **APM**: Spring Boot Actuator

### 7.2 Key Metrics
- API latency (P50, P95, P99)
- Error rates
- Event processing lag
- AI recommendation latency
- Database connection pool

### 7.3 Audit & Compliance
- Every API call logged
- All AI decisions traced
- User actions auditable
- Compliance events flagged

## 8. Scalability & Performance

### 8.1 Horizontal Scaling
- Stateless services (scale pods)
- Database read replicas
- Kafka partitioning
- Redis clustering

### 8.2 Performance Targets
- **Availability**: 99.99% (4 nines)
- **API Latency (P95)**: < 200ms
- **Event Processing**: < 1s
- **Concurrent Users**: 10,000+

### 8.3 Caching Strategy
- **L1 Cache**: In-memory (Caffeine)
- **L2 Cache**: Redis (distributed)
- **CDN**: Static assets
- **Query Cache**: ElasticSearch

## 9. Disaster Recovery

### 9.1 Backup Strategy
- **Database**: Daily automated backups
- **Retention**: 30 days
- **Point-in-Time Recovery**: Last 7 days
- **Cross-region Replication**: Enabled

### 9.2 RTO/RPO
- **RTO (Recovery Time Objective)**: 1 hour
- **RPO (Recovery Point Objective)**: 15 minutes

## 10. Future Enhancements

### 10.1 Planned Features
- Tax Optimization Agent
- Estate Planning Agent
- Cross-Border Advisory Agent
- Life Event Prediction Agent
- Sentiment Analysis from client interactions
- Predictive churn detection

### 10.2 Technology Evolution
- GraphQL API layer
- gRPC for inter-service communication
- Service Mesh (Istio/Linkerd)
- Edge computing for mobile apps
