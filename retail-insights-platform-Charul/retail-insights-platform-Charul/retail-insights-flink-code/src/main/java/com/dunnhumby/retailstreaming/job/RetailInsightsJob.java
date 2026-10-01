package com.dunnhumby.retailstreaming.job;

import com.dunnhumby.retailstreaming.model.*;
import com.dunnhumby.retailstreaming.functions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import java.time.Duration;

public class RetailInsightsJob {
	static final ObjectMapper M = new ObjectMapper();

	public static void main(String[] a) throws Exception {
		var env = StreamExecutionEnvironment.getExecutionEnvironment();
		env.enableCheckpointing(30000);
		String bs = System.getenv().getOrDefault("KAFKA_BOOTSTRAP_SERVERS", "localhost:9092");
		String topic = System.getenv().getOrDefault("KAFKA_TOPIC", "retail-events");
		KafkaSource<String> source = KafkaSource.<String>builder().setBootstrapServers(bs).setTopics(topic)
				.setGroupId("retail-insights-flink").setStartingOffsets(OffsetsInitializer.latest())
				.setValueOnlyDeserializer(new SimpleStringSchema()).build();
		DataStream<RetailEvent> events = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka")
				.map(RetailInsightsJob::parse).filter(e -> e != null).assignTimestampsAndWatermarks(
						WatermarkStrategy.<RetailEvent>forBoundedOutOfOrderness(Duration.ofSeconds(10))
								.withTimestampAssigner((e, t) -> e.eventTime.toEpochMilli()));
		DataStream<RetailEvent> clean = events.keyBy(e -> e.tenantId + "|" + e.eventId)
				.process(new EventDeduplicationFunction());
		clean.filter(e -> "AD_IMPRESSION".equals(e.eventType) || "AD_CLICK".equals(e.eventType))
				.keyBy(e -> e.tenantId + "|" + e.campaignId).window(TumblingEventTimeWindows.of(Time.minutes(5)))
				.reduce((a, b) -> {
					a.impressions += "AD_IMPRESSION".equals(b.eventType) ? 1 : 0;
					a.clicks += "AD_CLICK".equals(b.eventType) ? 1 : 0;
					return a;
				}).print("campaign-metrics");
		clean.filter(e -> "AD_CLICK".equals(e.eventType) || "ADD_TO_CART".equals(e.eventType))
				.keyBy(e -> e.tenantId + "|" + e.sessionId).process(new ClickToCartFunction()).print("click-to-cart");
		env.execute("Retail Real-Time Insights");
	}

	static RetailEvent parse(String s) {
		try {
			return M.readValue(s, RetailEvent.class);
		} catch (Exception e) {
			return null;
		}
	}
}