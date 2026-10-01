package com.dunnhumby.insights.service;

import com.dunnhumby.insights.exception.InvalidTimeRangeException;
import com.dunnhumby.insights.model.CampaignClicksResponse;
import com.dunnhumby.insights.model.CampaignMetrics;
import com.dunnhumby.insights.repository.CampaignMetricsRepository;
import com.dunnhumby.insights.security.TenantContext;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class CampaignInsightsService {

    private static final Duration MAX_QUERY_RANGE = Duration.ofDays(31);

    private final CampaignMetricsRepository repository;

    public CampaignInsightsService(CampaignMetricsRepository repository) {
        this.repository = repository;
    }

    public CampaignClicksResponse getClicks(
            String campaignId,
            Instant from,
            Instant to) {

        validate(campaignId, from, to);

        String tenantId = TenantContext.getTenantId();

        CampaignMetrics metrics =
                repository.findClicks(tenantId, campaignId, from, to);

        return new CampaignClicksResponse(
                metrics.campaignId(),
                metrics.clicks(),
                metrics.uniqueCustomers(),
                from,
                to,
                Instant.now(),
                "REAL_TIME"
        );
    }

    private void validate(
            String campaignId,
            Instant from,
            Instant to) {

        if (campaignId == null || campaignId.isBlank()) {
            throw new IllegalArgumentException("campaignId is required");
        }

        if (from == null || to == null) {
            throw new IllegalArgumentException("from and to are required");
        }

        if (from.isAfter(to)) {
            throw new InvalidTimeRangeException("from must be before to");
        }

        if (Duration.between(from, to).compareTo(MAX_QUERY_RANGE) > 0) {
            throw new InvalidTimeRangeException(
                    "Real-time API supports a maximum 31-day range"
            );
        }
    }
}
