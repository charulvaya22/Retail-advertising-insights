package com.dunnhumby.retailstreaming.functions;

import com.dunnhumby.retailstreaming.model.RetailEvent;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

public class EventDeduplicationFunction extends KeyedProcessFunction<String, RetailEvent, RetailEvent> {
	private transient ValueState<Boolean> seen;

	public void open(Configuration c) {
		seen = getRuntimeContext().getState(new ValueStateDescriptor<>("seen", Boolean.class));
	}

	public void processElement(RetailEvent e, Context c, Collector<RetailEvent> out) throws Exception {
		if (seen.value() == null) {
			seen.update(true);
			out.collect(e);
			c.timerService().registerEventTimeTimer(e.eventTime.toEpochMilli() + 86400000L);
		}
	}

	public void onTimer(long t, OnTimerContext c, Collector<RetailEvent> out) throws Exception {
		seen.clear();
	}
}