# Retail Real-Time Insights - Apache Flink

Production-oriented Flink sample for the Architect case study.

## Flow
Retailer -> Kafka/MSK -> Flink -> DynamoDB/S3/Snowflake -> Insights API

## Demonstrates
- Kafka ingestion
- Event-time processing
- 10-second bounded out-of-orderness
- Event deduplication by tenant + eventId
- Stateful click-to-cart attribution
- 30-minute attribution window
- 5-minute campaign aggregation
- Flink checkpointing
- Multi-tenant keys

## Build
mvn clean package

## Run
KAFKA_BOOTSTRAP_SERVERS=localhost:9092 KAFKA_TOPIC=retail-events java -jar target/retail-streaming-flink-1.0.0.jar

## Example event
{"eventId":"evt-1","eventType":"AD_CLICK","eventTime":"2026-09-27T10:00:01Z","tenantId":"retailer-a","campaignId":"campaign-123","adId":"ad-1","userId":"hashed-user","sessionId":"session-1","productId":"product-1","schemaVersion":1}