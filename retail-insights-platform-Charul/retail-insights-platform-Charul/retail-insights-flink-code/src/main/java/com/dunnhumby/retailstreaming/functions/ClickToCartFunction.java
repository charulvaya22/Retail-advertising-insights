package com.dunnhumby.retailstreaming.functions;

import com.dunnhumby.retailstreaming.model.*;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

public class ClickToCartFunction extends KeyedProcessFunction<String, RetailEvent, AttributedClick> {
	private static final long WINDOW = 30 * 60 * 1000L;
	private transient MapState<String, RetailEvent> clicks;

	public void open(Configuration c) {
		clicks = getRuntimeContext().getMapState(new MapStateDescriptor<>("clicks", String.class, RetailEvent.class));
	}

	public void processElement(RetailEvent e, Context ctx, Collector<AttributedClick> out) throws Exception {
		String p = e.productId == null ? "UNKNOWN" : e.productId;
		if ("AD_CLICK".equals(e.eventType)) {
			clicks.put(p, e);
			ctx.timerService().registerEventTimeTimer(e.eventTime.toEpochMilli() + WINDOW);
		} else if ("ADD_TO_CART".equals(e.eventType)) {
			RetailEvent click = clicks.get(p);
			if (click != null && !e.eventTime.isBefore(click.eventTime)
					&& e.eventTime.toEpochMilli() - click.eventTime.toEpochMilli() <= WINDOW) {
				out.collect(
						new AttributedClick(e.tenantId, click.campaignId, e.sessionId, e.productId, click.eventTime));
				clicks.remove(p);
			}
		}
	}

	public void onTimer(long ts, OnTimerContext c, Collector<AttributedClick> out) throws Exception {
		for (String p : clicks.keys()) {
			RetailEvent e = clicks.get(p);
			if (e != null && e.eventTime.toEpochMilli() + WINDOW <= ts)
				clicks.remove(p);
		}
	}
}