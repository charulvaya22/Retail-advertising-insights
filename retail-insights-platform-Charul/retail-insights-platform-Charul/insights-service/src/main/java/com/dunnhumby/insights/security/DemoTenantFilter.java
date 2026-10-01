package com.dunnhumby.insights.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Demo-only tenant filter.
 *
 * Production:
 * - validate JWT/OIDC token
 * - derive tenantId from trusted claims
 * - verify user permissions for the requested campaign
 * - never trust a tenantId supplied by an arbitrary client
 */
@Component
public class DemoTenantFilter extends OncePerRequestFilter {

    private static final String DEFAULT_TENANT = "retailer-demo";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        try {
            String tenantId = request.getHeader("X-Tenant-Id");
            TenantContext.setTenantId(
                    tenantId == null || tenantId.isBlank()
                            ? DEFAULT_TENANT
                            : tenantId
            );
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}
