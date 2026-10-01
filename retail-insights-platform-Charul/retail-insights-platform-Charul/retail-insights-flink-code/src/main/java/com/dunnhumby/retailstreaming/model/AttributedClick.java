package com.dunnhumby.retailstreaming.model;

import java.io.Serializable;
import java.time.Instant;

public class AttributedClick implements Serializable {
	public String tenantId, campaignId, sessionId, productId;
	public Instant clickTime;

	public AttributedClick() {
	}

	public AttributedClick(String t, String c, String s, String p, Instant i) {
		tenantId = t;
		campaignId = c;
		sessionId = s;
		productId = p;
		clickTime = i;
	}
}