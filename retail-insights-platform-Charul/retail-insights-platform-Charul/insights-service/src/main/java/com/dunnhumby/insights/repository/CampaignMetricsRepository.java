package com.dunnhumby.insights.repository;

import org.springframework.stereotype.Repository;

import com.dunnhumby.insights.model.CampaignMetrics;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CampaignMetricsRepository {

    private final Map<String, CampaignMetrics> store = new ConcurrentHashMap<>();

    public CampaignMetricsRepository() {
        store.put(key("retailer-demo", "campaign-123"),
                new CampaignMetrics("retailer-demo", "campaign-123", 15000, 12750));
    }

    public CampaignMetrics findClicks(
            String tenantId,
            String campaignId,
            Instant from,
            Instant to) {

        return store.getOrDefault(
                key(tenantId, campaignId),
                new CampaignMetrics(tenantId, campaignId, 0, 0)
        );
    }

    private String key(String tenantId, String campaignId) {
        return tenantId + "|" + campaignId;
    }
}
