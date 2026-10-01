package com.dunnhumby.insights.controller;

import com.dunnhumby.insights.model.CampaignClicksResponse;
import com.dunnhumby.insights.service.CampaignInsightsService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/v1/campaigns")
public class CampaignInsightsController {

    private final CampaignInsightsService service;

    public CampaignInsightsController(CampaignInsightsService service) {
        this.service = service;
    }

    @GetMapping("/{campaignId}/clicks")
    public ResponseEntity<CampaignClicksResponse> getClicks(
            @PathVariable String campaignId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            Instant from,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            Instant to) {

        return ResponseEntity.ok(
                service.getClicks(campaignId, from, to)
        );
    }
}
