package com.dunnhumby.retailstreaming.model;

import java.io.Serializable;
import java.time.Instant;

public class RetailEvent implements Serializable {
	public String eventId, eventType, tenantId, campaignId, adId, userId, sessionId, productId;
	public Instant eventTime;
	public long schemaVersion;

	public RetailEvent() {
	}
}