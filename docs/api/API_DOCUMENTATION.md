# AurumOne API Documentation

## Overview
This document provides comprehensive API documentation for the AurumOne HNI Wealth Management Platform.

## Base URLs
- **Client Management**: `http://localhost:8081`
- **Portfolio Management**: `http://localhost:8082`
- **Advisory Engine**: `http://localhost:8083`

## Authentication
All APIs use OAuth2/OIDC authentication (to be implemented).

---

## Client Management API

### Create Client
Creates a new HNI/UHNI client profile.

**Endpoint**: `POST /api/v1/clients`

**Request Body**:
```json
{
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
}
```

**Response**: `201 Created`
```json
{
  "clientId": "550e8400-e29b-41d4-a716-446655440000",
  "firstName": "John",
  "lastName": "Smith",
  "email": "john.smith@example.com",
  "segment": "HNI",
  "riskProfile": "MODERATE",
  "kycStatus": "PENDING",
  "netWorthValue": 5000000,
  "currency": "USD",
  "createdAt": "2024-01-13T10:30:00"
}
```

### Get Client
Retrieves client details by ID.

**Endpoint**: `GET /api/v1/clients/{clientId}`

**Response**: `200 OK`
```json
{
  "clientId": "550e8400-e29b-41d4-a716-446655440000",
  "firstName": "John",
  "lastName": "Smith",
  "email": "john.smith@example.com",
  "phone": "+1-555-0123",
  "segment": "HNI",
  "riskProfile": "MODERATE",
  "kycStatus": "VERIFIED",
  "netWorthValue": 5000000,
  "currency": "USD",
  "familyOffice": false,
  "domicileCountry": "USA",
  "createdAt": "2024-01-13T10:30:00",
  "updatedAt": "2024-01-13T11:00:00"
}
```

### Update KYC Status
Updates KYC verification status for a client.

**Endpoint**: `PUT /api/v1/clients/{clientId}/kyc`

**Request Body**:
```json
{
  "kycStatus": "VERIFIED",
  "kycExpiryDate": "2025-01-13T00:00:00"
}
```

**Response**: `200 OK`

### Get Client Net Worth
Retrieves client's net worth information.

**Endpoint**: `GET /api/v1/clients/{clientId}/net-worth`

**Response**: `200 OK`

---

## Portfolio Management API

### Get Client Portfolios
Retrieves all portfolios for a specific client.

**Endpoint**: `GET /api/v1/portfolios/client/{clientId}`

**Response**: `200 OK`
```json
[
  {
    "portfolioId": "660e8400-e29b-41d4-a716-446655440000",
    "clientId": "550e8400-e29b-41d4-a716-446655440000",
    "portfolioName": "Primary Portfolio",
    "totalValue": 2400000,
    "currency": "USD"
  }
]
```

### Get Portfolio Performance
Retrieves portfolio performance metrics.

**Endpoint**: `GET /api/v1/portfolios/{portfolioId}/performance`

**Response**: `200 OK`
```json
{
  "portfolioId": "660e8400-e29b-41d4-a716-446655440000",
  "ytdReturn": 12.5,
  "benchmarkReturn": 10.2,
  "sharpeRatio": 1.8,
  "volatility": 8.5
}
```

---

## Advisory Engine API

### Generate Recommendation
Generates AI-powered investment recommendations.

**Endpoint**: `POST /api/v1/advisory/recommendations`

**Request Body**:
```json
{
  "clientId": "550e8400-e29b-41d4-a716-446655440000",
  "portfolioId": "660e8400-e29b-41d4-a716-446655440000",
  "recommendationType": "REBALANCE",
  "context": {
    "marketCondition": "VOLATILE",
    "timeHorizon": "LONG_TERM"
  }
}
```

**Response**: `200 OK`
```json
{
  "recommendationId": "770e8400-e29b-41d4-a716-446655440000",
  "clientId": "550e8400-e29b-41d4-a716-446655440000",
  "recommendationType": "REBALANCE",
  "recommendations": [
    {
      "productId": "880e8400-e29b-41d4-a716-446655440000",
      "productName": "Diversified Equity Fund",
      "action": "BUY",
      "reasoning": "Portfolio shows low equity exposure. Client risk profile supports moderate equity allocation."
    }
  ],
  "rationale": "Based on portfolio analysis and client risk profile, diversification into equity funds is recommended.",
  "riskDisclosure": "Market risks apply. Past performance does not guarantee future results.",
  "confidenceScore": 0.85,
  "suitabilityCheckPassed": true
}
```

---

## Event-Driven Architecture

### Events Published

#### ClientCreatedEvent
```json
{
  "eventId": "990e8400-e29b-41d4-a716-446655440000",
  "eventType": "ClientCreated",
  "occurredAt": "2024-01-13T10:30:00",
  "clientId": "550e8400-e29b-41d4-a716-446655440000",
  "segment": "HNI",
  "riskProfile": "MODERATE"
}
```

#### PortfolioUpdatedEvent
```json
{
  "eventId": "aa0e8400-e29b-41d4-a716-446655440000",
  "eventType": "PortfolioUpdated",
  "occurredAt": "2024-01-13T11:00:00",
  "portfolioId": "660e8400-e29b-41d4-a716-446655440000",
  "clientId": "550e8400-e29b-41d4-a716-446655440000",
  "totalValue": {
    "value": 2400000,
    "currency": "USD"
  }
}
```

#### RecommendationGeneratedEvent
```json
{
  "eventId": "bb0e8400-e29b-41d4-a716-446655440000",
  "eventType": "RecommendationGenerated",
  "occurredAt": "2024-01-13T12:00:00",
  "recommendationId": "770e8400-e29b-41d4-a716-446655440000",
  "clientId": "550e8400-e29b-41d4-a716-446655440000",
  "recommendationType": "REBALANCE",
  "confidenceScore": 0.85
}
```

---

## Error Responses

All APIs return standard error responses:

**400 Bad Request**:
```json
{
  "timestamp": "2024-01-13T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/v1/clients"
}
```

**404 Not Found**:
```json
{
  "timestamp": "2024-01-13T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Client not found",
  "path": "/api/v1/clients/550e8400-e29b-41d4-a716-446655440000"
}
```

**500 Internal Server Error**:
```json
{
  "timestamp": "2024-01-13T10:30:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred"
}
```

---

## OpenAPI/Swagger

Interactive API documentation is available at:
- Client Management: `http://localhost:8081/swagger-ui.html`
- Portfolio Management: `http://localhost:8082/swagger-ui.html`
- Advisory Engine: `http://localhost:8083/swagger-ui.html`
