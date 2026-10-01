# Design Trade-Offs

Every architectural decision introduces benefits and trade-offs. This document explains the major design decisions and the reasoning behind them.

---

# Kafka vs RabbitMQ

## Kafka

Advantages:

- Replay capability
- High throughput
- Consumer independence
- Partition-based horizontal scaling

Disadvantages:

- Higher operational complexity
- More infrastructure management

---

## RabbitMQ

Advantages:

- Simpler operational model
- Good for request/workflow processing

Disadvantages:

- Limited event replay capability
- Not optimized for large-scale analytics streams

---

## Decision

Kafka was selected because replayability and stream processing are critical business requirements.

---

# Flink vs Spark Streaming

## Flink

Advantages:

- Stateful stream processing
- Event-time handling
- Watermarks
- Exactly-once processing

Disadvantages:

- Learning curve
- Operational complexity

---

## Spark Streaming

Advantages:

- Mature ecosystem
- Well known by engineering teams

Disadvantages:

- Micro-batch processing model
- Higher latency

---

## Decision

Flink was selected because the platform requires low-latency attribution and stateful processing.

---

# DynamoDB vs Cassandra

## DynamoDB

Advantages:

- Fully managed
- Auto scaling
- Low operational effort

Disadvantages:

- AWS dependency
- Cost at very large scale

---

## Cassandra

Advantages:

- Open source
- Extremely scalable

Disadvantages:

- Operational overhead
- Capacity management

---

## Decision

DynamoDB provides lower operational complexity while delivering required performance.

---

# Real-Time Accuracy vs Availability

Perfect real-time accuracy increases latency.

Immediate responsiveness may introduce temporary inaccuracies.

Decision:

- Real-time dashboards prioritize responsiveness.
- Historical reporting prioritizes accuracy.

---

# Redis Cache Trade-Off

Benefits:

- Low latency
- Reduced DynamoDB reads

Risks:

- Stale data
- Additional infrastructure

Decision:

Redis remains optional and may be introduced as traffic grows.