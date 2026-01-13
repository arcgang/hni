# AurumOne Deployment Guide

## Prerequisites

### Required Software
- Docker 24.0+
- Docker Compose 2.20+
- Kubernetes 1.28+ (for production)
- kubectl CLI
- Java 17+ (for local development)
- Node.js 18+ (for frontend development)
- Maven 3.8+ (for backend builds)

### Infrastructure Requirements
- **Development**: 8GB RAM, 4 CPU cores
- **Production**: Kubernetes cluster with 16+ GB RAM per node

---

## Local Development Setup

### 1. Clone Repository
```bash
git clone https://github.com/aurumone/hni-wealth-management.git
cd hni-wealth-management
```

### 2. Start Infrastructure Services
```bash
docker-compose up -d
```

This starts:
- PostgreSQL (port 5432)
- Kafka + Zookeeper (port 9092)
- ElasticSearch (port 9200)
- Redis (port 6379)

### 3. Verify Infrastructure
```bash
# Check PostgreSQL
docker exec -it aurumone-postgres psql -U aurumone -d aurumone -c '\dt'

# Check Kafka
docker exec -it aurumone-kafka kafka-topics --list --bootstrap-server localhost:9092

# Check ElasticSearch
curl http://localhost:9200/_cluster/health
```

### 4. Build Shared Domain Library
```bash
cd backend/shared/domain
mvn clean install
```

### 5. Run Backend Services

#### Client Management Service
```bash
cd backend/services/client-management
mvn spring-boot:run
```
Service runs on: http://localhost:8081
Swagger UI: http://localhost:8081/swagger-ui.html

#### Portfolio Management Service
```bash
cd backend/services/portfolio-management
mvn spring-boot:run
```
Service runs on: http://localhost:8082

#### Advisory Engine Service
```bash
cd backend/services/advisory-engine
mvn spring-boot:run
```
Service runs on: http://localhost:8083

### 6. Run Frontend
```bash
cd frontend
npm install
npm run dev
```
Frontend runs on: http://localhost:3000

---

## Testing the Application

### 1. Create a Client
```bash
curl -X POST http://localhost:8081/api/v1/clients \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Smith",
    "email": "john.smith@example.com",
    "phone": "+1-555-0123",
    "segment": "HNI",
    "riskProfile": "MODERATE",
    "netWorthValue": 5000000,
    "currency": "USD",
    "familyOffice": false,
    "domicileCountry": "USA"
  }'
```

### 2. Get Client Details
```bash
curl http://localhost:8081/api/v1/clients/{clientId}
```

### 3. Generate AI Recommendation
```bash
curl -X POST http://localhost:8083/api/v1/advisory/recommendations \
  -H "Content-Type: application/json" \
  -d '{
    "clientId": "{clientId}",
    "portfolioId": "{portfolioId}",
    "recommendationType": "REBALANCE"
  }'
```

---

## Docker Deployment

### 1. Build Docker Images

```bash
# Build shared domain
cd backend/shared/domain
mvn clean install

# Build Client Management Service
cd backend/services/client-management
docker build -t aurumone/client-management:latest .

# Build Portfolio Management Service
cd backend/services/portfolio-management
docker build -t aurumone/portfolio-management:latest .

# Build Advisory Engine
cd backend/services/advisory-engine
docker build -t aurumone/advisory-engine:latest .

# Build Frontend
docker build -t aurumone/frontend:latest -f frontend/Dockerfile .
```

### 2. Run with Docker Compose
```bash
docker-compose -f docker-compose.yml -f docker-compose.services.yml up -d
```

---

## Kubernetes Deployment

### 1. Prerequisites
```bash
# Verify cluster connection
kubectl cluster-info

# Create namespace
kubectl create namespace aurumone
kubectl config set-context --current --namespace=aurumone
```

### 2. Deploy Infrastructure
```bash
# Deploy PostgreSQL
kubectl apply -f infrastructure/kubernetes/postgres-statefulset.yaml

# Wait for PostgreSQL to be ready
kubectl wait --for=condition=ready pod -l app=postgres --timeout=300s

# Deploy Kafka (if not using managed service)
# kubectl apply -f infrastructure/kubernetes/kafka-deployment.yaml
```

### 3. Deploy Application Services
```bash
# Deploy Client Management Service
kubectl apply -f infrastructure/kubernetes/client-management-deployment.yaml

# Deploy Portfolio Management Service
kubectl apply -f infrastructure/kubernetes/portfolio-management-deployment.yaml

# Deploy Advisory Engine Service
kubectl apply -f infrastructure/kubernetes/advisory-engine-deployment.yaml

# Deploy Ingress
kubectl apply -f infrastructure/kubernetes/ingress.yaml
```

### 4. Verify Deployments
```bash
# Check all pods
kubectl get pods

# Check services
kubectl get services

# Check ingress
kubectl get ingress

# View logs
kubectl logs -l app=client-management --tail=50
```

### 5. Access Application
```bash
# Port-forward for local access
kubectl port-forward svc/client-management-service 8081:8081

# Or configure /etc/hosts for ingress
echo "127.0.0.1 aurumone.local" | sudo tee -a /etc/hosts
```

---

## Production Deployment Checklist

### Security
- [ ] Enable OAuth2/OIDC authentication
- [ ] Configure RBAC policies
- [ ] Set up TLS certificates
- [ ] Enable network policies
- [ ] Configure secrets management (AWS Secrets Manager / HashiCorp Vault)
- [ ] Enable pod security policies
- [ ] Set up WAF (Web Application Firewall)

### Monitoring & Observability
- [ ] Deploy Prometheus + Grafana
- [ ] Configure ELK stack for logging
- [ ] Set up distributed tracing (Jaeger)
- [ ] Configure alerts (PagerDuty / Opsgenie)
- [ ] Enable APM monitoring
- [ ] Set up dashboards

### High Availability
- [ ] Configure pod autoscaling (HPA)
- [ ] Set up node autoscaling
- [ ] Configure pod disruption budgets
- [ ] Enable multi-zone deployment
- [ ] Set up load balancing
- [ ] Configure health checks

### Data & Backup
- [ ] Enable database backups
- [ ] Configure point-in-time recovery
- [ ] Set up cross-region replication
- [ ] Test disaster recovery procedures
- [ ] Configure data retention policies

### Performance
- [ ] Enable Redis caching
- [ ] Configure connection pooling
- [ ] Set resource limits/requests
- [ ] Enable CDN for static assets
- [ ] Optimize database indexes
- [ ] Configure rate limiting

---

## Environment Variables

### Client Management Service
```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/aurumone
SPRING_DATASOURCE_USERNAME=aurumone
SPRING_DATASOURCE_PASSWORD=<secret>
SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092
```

### Portfolio Management Service
```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/aurumone
SPRING_DATASOURCE_USERNAME=aurumone
SPRING_DATASOURCE_PASSWORD=<secret>
SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092
```

### Advisory Engine Service
```bash
SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092
AI_MODEL_ENDPOINT=https://api.openai.com/v1
AI_MODEL_API_KEY=<secret>
```

---

## Troubleshooting

### Service Won't Start
```bash
# Check logs
kubectl logs <pod-name>

# Check events
kubectl describe pod <pod-name>

# Check resource usage
kubectl top pods
```

### Database Connection Issues
```bash
# Test PostgreSQL connection
kubectl exec -it <postgres-pod> -- psql -U aurumone -d aurumone

# Check database secrets
kubectl get secret postgres-secret -o yaml
```

### Kafka Issues
```bash
# List topics
kubectl exec -it <kafka-pod> -- kafka-topics --list --bootstrap-server localhost:9092

# Check consumer groups
kubectl exec -it <kafka-pod> -- kafka-consumer-groups --list --bootstrap-server localhost:9092
```

### Performance Issues
```bash
# Check resource usage
kubectl top nodes
kubectl top pods

# Check HPA status
kubectl get hpa

# Check pod resource limits
kubectl describe pod <pod-name> | grep -A 5 "Limits:"
```

---

## Scaling

### Manual Scaling
```bash
# Scale deployment
kubectl scale deployment client-management-service --replicas=5

# Scale statefulset
kubectl scale statefulset postgres --replicas=3
```

### Auto-scaling
```bash
# Create HPA
kubectl autoscale deployment client-management-service \
  --cpu-percent=70 \
  --min=2 \
  --max=10
```

---

## Maintenance

### Rolling Updates
```bash
# Update image
kubectl set image deployment/client-management-service \
  client-management=aurumone/client-management:v2.0.0

# Check rollout status
kubectl rollout status deployment/client-management-service

# Rollback if needed
kubectl rollout undo deployment/client-management-service
```

### Database Migrations
```bash
# Run Flyway migrations
kubectl exec -it <client-management-pod> -- \
  java -jar flyway.jar migrate
```

---

## Support

For issues and support:
- **Documentation**: https://docs.aurumone.com
- **GitHub Issues**: https://github.com/aurumone/hni/issues
- **Email**: support@aurumone.com
