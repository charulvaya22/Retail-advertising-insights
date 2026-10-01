package com.dunnhumby.retailstreaming.model;

import java.io.Serializable;
import java.time.Instant;

public class CampaignAggregate implements Serializable {
	public String tenantId, campaignId;
	public long impressions, clicks;
	public Instant windowStart, windowEnd;

	public CampaignAggregate() {
	}
}