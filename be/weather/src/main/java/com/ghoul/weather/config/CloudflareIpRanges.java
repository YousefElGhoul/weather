package com.ghoul.weather.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Matches addresses against Cloudflare's published edge ranges
 * (https://www.cloudflare.com/ips-v4 and /ips-v6), so the CF-Connecting-IP
 * header is only trusted on requests that actually arrived through Cloudflare.
 */
@Component
public class CloudflareIpRanges {

    private static final Pattern IPV4_LITERAL = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3}");
    private static final Pattern IPV6_LITERAL = Pattern.compile("[0-9a-fA-F:.]*:[0-9a-fA-F:.]*");

    private final List<Cidr> ranges;

    public CloudflareIpRanges(@Value("${cloudflare.ip-ranges:}") List<String> cidrs) {
        this.ranges = cidrs.stream().map(String::trim).filter(s -> !s.isEmpty()).map(Cidr::parse).toList();
    }

    public boolean contains(String ip) {
        InetAddress address = parseLiteral(ip);
        return address != null && ranges.stream().anyMatch(range -> range.contains(address));
    }

    /** Parses an IP literal without ever falling back to a DNS lookup; returns null if it isn't one. */
    public static InetAddress parseLiteral(String ip) {
        if (ip == null) {
            return null;
        }
        String candidate = ip.trim();
        if (!IPV4_LITERAL.matcher(candidate).matches() && !IPV6_LITERAL.matcher(candidate).matches()) {
            return null;
        }
        try {
            return InetAddress.getByName(candidate);
        } catch (UnknownHostException ex) {
            return null;
        }
    }

    private record Cidr(byte[] network, int prefixLength) {

        static Cidr parse(String cidr) {
            String[] parts = cidr.split("/", 2);
            InetAddress address = parseLiteral(parts[0]);
            if (address == null || parts.length != 2) {
                throw new IllegalArgumentException("Invalid CIDR range: " + cidr);
            }
            int prefix = Integer.parseInt(parts[1]);
            if (prefix < 0 || prefix > address.getAddress().length * 8) {
                throw new IllegalArgumentException("Invalid CIDR prefix length: " + cidr);
            }
            return new Cidr(address.getAddress(), prefix);
        }

        boolean contains(InetAddress address) {
            byte[] candidate = address.getAddress();
            if (candidate.length != network.length) {
                return false;
            }
            int fullBytes = prefixLength / 8;
            for (int i = 0; i < fullBytes; i++) {
                if (candidate[i] != network[i]) {
                    return false;
                }
            }
            int remainingBits = prefixLength % 8;
            if (remainingBits == 0) {
                return true;
            }
            int mask = (0xFF << (8 - remainingBits)) & 0xFF;
            return (candidate[fullBytes] & mask) == (network[fullBytes] & mask);
        }
    }
}
