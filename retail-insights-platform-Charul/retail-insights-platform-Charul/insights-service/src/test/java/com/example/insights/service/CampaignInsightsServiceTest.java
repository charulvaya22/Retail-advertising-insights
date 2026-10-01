package com.example.insights.service;

import com.dunnhumby.insights.model.CampaignClicksResponse;
import com.dunnhumby.insights.repository.CampaignMetricsRepository;
import com.dunnhumby.insights.security.TenantContext;
import com.dunnhumby.insights.service.CampaignInsightsService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class CampaignInsightsServiceTest {

    private final CampaignInsightsService service =
            new CampaignInsightsService(new CampaignMetricsRepository());

    @AfterEach
    void cleanup() {
        TenantContext.clear();
    }

    @Test
    void returnsCampaignClickMetrics() {
        TenantContext.setTenantId("retailer-demo");

        CampaignClicksResponse response = service.getClicks(
                "campaign-123",
                Instant.parse("2026-09-27T00:00:00Z"),
                Instant.parse("2026-09-27T23:59:59Z")
        );

        assertEquals("campaign-123", response.campaignId());
        assertEquals(15000, response.clicks());
        assertEquals(12750, response.uniqueCustomers());
    }

    @Test
    void rejectsInvalidTimeRange() {
        TenantContext.setTenantId("retailer-demo");

        assertThrows(
                RuntimeException.class,
                () -> service.getClicks(
                        "campaign-123",
                        Instant.parse("2026-09-28T00:00:00Z"),
                        Instant.parse("2026-09-27T00:00:00Z")
                )
        );
    }
}
