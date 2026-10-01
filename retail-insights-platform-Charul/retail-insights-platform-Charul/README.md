# Real-Time Retail Advertising & Customer Insights Platform

### Author
**Charul Vaya**  
Engineering Manager - I

---

# Overview

This repository demonstrates the architecture, low-level design, and sample implementation of a production-grade **Real-Time Retail Advertising & Customer Insights Platform**.

The platform enables retailers and marketers to capture, process, and analyze customer interaction events in near real-time. It supports campaign performance measurement, click-to-cart attribution, and low-latency insight retrieval through REST APIs.

Typical event types include:

- Ad Impression
- Ad Click
- Product View
- Add To Cart
- Purchase

The platform is designed to support:

- High-volume event ingestion
- Near real-time analytics
- Stateful attribution processing
- Multi-tenant architecture
- Historical reporting
- Fault tolerance
- Horizontal scalability

---

# Business Problem

Retailers display advertisements across web and mobile channels.

Every customer interaction generates an event.

Example customer journey:

```text
Ad Impression
      ↓
Ad Click
      ↓
Product View
      ↓
Add To Cart
      ↓
Purchase
```

The business requires real-time answers to questions such as:

- How many impressions did a campaign receive?
- How many users clicked an advertisement?
- What is the click-through rate (CTR)?
- How many users added a product to the cart after clicking an ad?
- Which campaigns are generating the highest conversions?
- How much advertising spend is being consumed?

---

# Solution Overview

The platform uses an event-driven architecture.

```text
Retailer Web/Mobile
        |
        v
API Gateway
        |
        v
Event Ingestion Service
        |
        v
Kafka / MSK
        |
   +----+----+
   |         |
   v         v
 Flink      S3
   |
   v
DynamoDB
   |
   v
Insights API
   |
   v
Dashboard

S3
 |
 v
Snowflake
 |
 v
BI / Analytics
```

---

# Key Design Principles

### Event-Driven Architecture

Customer events are captured as immutable events and published into Kafka.

### Streaming-First Design

Real-time analytics are continuously computed using Apache Flink.

### Pre-Aggregated Metrics

APIs never scan raw event data.

Metrics are continuously maintained and stored in DynamoDB.

### Immutable Source of Truth

All raw events are stored in S3 for:

- Replay
- Auditing
- Reconciliation
- Historical analytics

### Multi-Tenant by Design

Tenant isolation is enforced across:

- Authentication
- Kafka
- Stream Processing
- Storage
- APIs

---

# High Level Architecture

## Components

| Component | Responsibility |
|------------|---------------|
| API Gateway | Security, authentication, rate limiting |
| Event Ingestion Service | Validate and publish events |
| Kafka / MSK | Durable event backbone |
| Apache Flink | Stateful stream processing |
| DynamoDB | Low-latency serving layer |
| Redis | Optional cache layer |
| S3 | Immutable event storage |
| Snowflake | Historical analytics |
| Insights API | Campaign analytics APIs |
| Grafana / Prometheus | Monitoring & Observability |

---

# Technology Choices

| Layer | Technology | Reason |
|---------|------------|---------|
| Backend | Java 21 + Spring Boot | Enterprise ecosystem and maturity |
| Event Streaming | Kafka / Amazon MSK | Replayable, durable event streams |
| Stream Processing | Apache Flink | Stateful event-time processing |
| Serving Database | DynamoDB | Low-latency, scalable reads |
| Cache | Redis | Frequently accessed metrics |
| Data Lake | Amazon S3 | Durable low-cost storage |
| Analytics | Snowflake | OLAP analytics and reporting |
| Deployment | Kubernetes / EKS | Horizontal scalability |
| Observability | OpenTelemetry + Grafana | Monitoring and tracing |
| Infrastructure | Terraform | Repeatable infrastructure provisioning |

---

# Core APIs

## Get Campaign Clicks

```http
GET /ad/{campaignId}/clicks
```

Returns the number of ad clicks.

---

## Get Campaign Impressions

```http
GET /ad/{campaignId}/impressions
```

Returns total ad impressions.

---

## Get Click To Basket

```http
GET /ad/{campaignId}/clickToBasket
```

Returns attributed add-to-cart conversions.
