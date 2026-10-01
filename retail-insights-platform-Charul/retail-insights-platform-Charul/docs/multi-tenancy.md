# Multi-Tenancy Design

The platform supports multiple retailers within a shared infrastructure while maintaining strict data isolation.

---

# Tenant Identification

Tenant identity is derived from the authenticated principal.

Example:

```text
JWT Token
      |
Tenant Claim
      |
Tenant Context
```

The platform never trusts tenant identifiers provided by end users.

---

# Kafka Isolation

Partition Keys:

```text
tenantId#sessionId
```

Benefits:

- Event grouping
- Session consistency
- Scalable consumption

---

# Storage Isolation

DynamoDB primary keys are tenant aware.

Example:

```text
TENANT#walmart#CAMPAIGN#summer-sale
```

This ensures data separation across tenants.

---

# API Isolation

Every API request is evaluated against:

- Tenant context
- Authorization rules
- Resource ownership

Cross-tenant access is prohibited.

---

# Tenant Configuration

Tenant-specific configuration may include:

- Attribution windows
- Rate limits
- Feature flags
- Retention policies

---

# Noisy Tenant Protection

Controls include:

- API rate limits
- Processing quotas
- Traffic monitoring

For strategic customers, dedicated processing resources may