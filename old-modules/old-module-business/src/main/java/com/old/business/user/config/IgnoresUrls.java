package com.old.business.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

@Data
@Component
@ConfigurationProperties("old")
// public record IgnoresUrls(Set<String> ignoreUrls) {
public class IgnoresUrls {
    private Set<String> ignoreUrls;
}
