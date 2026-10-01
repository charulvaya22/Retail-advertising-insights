package com.dunnhumby.insights.model;

public record CampaignMetrics(
        String tenantId,
        String campaignId,
        long clicks,
        long uniqueCustomers
) {}
