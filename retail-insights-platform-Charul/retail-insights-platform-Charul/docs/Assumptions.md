# Assumptions

This document captures the assumptions made while designing the Real-Time Retail Advertising & Customer Insights Platform.

---

# Business Assumptions

The platform serves multiple retailers through a shared multi-tenant environment.

Example retailers:

- Walmart Connect
- Target Roundel
- Amazon Ads
- Flipkart Ads

The platform processes customer interaction and advertising events generated from retailer websites and mobile applications.

---

# Event Volume Assumptions

Average Daily Active Users:

```text
1,000,000+
```

Average Events Per User:

```text
20
```

Estimated Daily Event Volume:

```text
20M+ events/day
```

Peak Event Volume:

```text
100,000+ events/sec
```

The architecture should support traffic spikes during major sales and promotional campaigns.

---

# Availability Assumptions

Target Platform Availability:

```text
99.9%
```

No single component should become a single point of failure.

---

# Data Consistency Assumptions

Real-time dashboards are eventually consistent.

Small delays between event creation and dashboard visibility are acceptable.

Historical reports are expected to be fully accurate after reconciliation.

---

# Attribution Assumptions

Default attribution window:

```text
30 minutes
```

A click is attributed to an Add-To-Cart event only if:

- The customer remains within the attribution window.
- Product matching rules are satisfied.

The attribution window should be configurable.

---

# Data Retention Assumptions

Kafka:

```text
24 hours
```

Retry Topics:

```text
72 hours
```

DLQ:

```text
14 days
```

Raw Events:

```text
3+ years
```

Aggregate Metrics:

```text
12 months
```

---

# Security Assumptions

Tenant identity is derived from authenticated credentials.

Tenant information supplied within request payloads is never trusted.

All communication occurs over HTTPS.

---

# Cloud Assumptions

The solution is deployed on AWS.

Primary services:

- Amazon MSK
- Amazon DynamoDB
- Amazon S3
- Amazon EKS
- AWS WAF
- Snowflake