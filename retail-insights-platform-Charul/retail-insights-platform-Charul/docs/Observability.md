# Observability Strategy

Observability is critical for operating distributed systems at scale.

The platform uses OpenTelemetry, Prometheus, and Grafana to provide end-to-end visibility.

---

# Objectives

Observability enables:

- Performance monitoring
- Capacity planning
- Failure detection
- Root cause analysis
- SLA compliance

---

# Metrics

## Kafka

Key Metrics:

- Consumer Lag
- Throughput
- Partition Distribution
- Broker Availability

---

## Flink

Key Metrics:

- Processing Latency
- Checkpoint Duration
- Checkpoint Failures
- Backpressure
- Event Throughput

---

## APIs

Key Metrics:

- Request Rate
- Error Rate
- p95 Latency
- p99 Latency

---

## DynamoDB

Key Metrics:

- Read Latency
- Write Latency
- Throttled Requests
- Consumed Capacity

---

# Business Metrics

The platform must monitor business outcomes, not only infrastructure.

Metrics include:

- Impressions
- Clicks
- CTR
- Click