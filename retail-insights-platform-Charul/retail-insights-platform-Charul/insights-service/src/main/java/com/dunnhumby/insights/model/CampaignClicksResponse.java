package com.dunnhumby.insights.model;

import java.time.Instant;

public record CampaignClicksResponse(
        String campaignId,
        long clicks,
        long uniqueCustomers,
        Instant from,
        Instant to,
        Instant lastUpdatedAt,
        String dataStatus
) {}
