# Scalability Strategy

The platform is designed to scale horizontally across every major layer.

---

# Ingestion Layer

The Event Collector Service is stateless.

Scaling Strategy:

```text
Horizontal Pod Autoscaler
```

Additional pods can be added without impacting functionality.

---

# Kafka Scaling

Kafka scales through partitions.

Example:

```text
64 Partitions
```

Events are distributed using:

```text
tenantId#sessionId
```

This allows consumers to process events in parallel.

---

# Flink Scaling

Flink operators execute in parallel.

Scaling options:

- Increase parallelism
- Add TaskManagers
- Increase available CPU and memory

State remains distributed across the cluster.

---

# DynamoDB Scaling

DynamoDB uses:

```text
On-Demand Capacity
```

Benefits:

- Automatic scaling
- No capacity planning for normal workloads

---

# API Scaling

Insights API instances remain stateless.

Scale through:

- Additional API pods
- Load balancing
- Auto scaling

---

# Storage Scaling

Amazon S3 provides virtually unlimited capacity.

No manual storage scaling is required.

---

# Peak Event Handling

Traffic spikes are absorbed by Kafka.

Example:

```text
Normal Traffic
10K Events/sec

Peak Traffic
100K Events/sec
```

Kafka acts as an event buffer while downstream systems catch up.