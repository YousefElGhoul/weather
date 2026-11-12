package com.ghoul.weather.config;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloudflareIpRangesTest {

    private final CloudflareIpRanges ranges = new CloudflareIpRanges(
            List.of("173.245.48.0/20", "104.16.0.0/13", "2a06:98c0::/29"));

    @Test
    void matchesIpv4AddressesInsideRanges() {
        assertTrue(ranges.contains("173.245.48.1"));
        assertTrue(ranges.contains("173.245.63.255"));
        assertTrue(ranges.contains("104.23.255.255"));
    }

    @Test
    void rejectsIpv4AddressesOutsideRanges() {
        assertFalse(ranges.contains("173.245.64.0"));
        assertFalse(ranges.contains("104.24.0.0"));
        assertFalse(ranges.contains("156.223.38.59"));
    }

    @Test
    void matchesIpv6AddressesInsideRangesOnly() {
        assertTrue(ranges.contains("2a06:98c7:ffff::1"));
        assertFalse(ranges.contains("2a06:98c8::1"));
    }

    @Test
    void rejectsNonLiteralsWithoutDnsLookup() {
        assertFalse(ranges.contains(null));
        assertFalse(ranges.contains(""));
        assertFalse(ranges.contains("unknown"));
        assertFalse(ranges.contains("cafe.de"));
    }

    @Test
    void emptyConfigurationMatchesNothing() {
        assertFalse(new CloudflareIpRanges(List.of()).contains("173.245.48.1"));
    }

    @Test
    void rejectsMalformedRanges() {
        assertThrows(IllegalArgumentException.class, () -> new CloudflareIpRanges(List.of("173.245.48.0")));
        assertThrows(IllegalArgumentException.class, () -> new CloudflareIpRanges(List.of("173.245.48.0/33")));
    }
}
