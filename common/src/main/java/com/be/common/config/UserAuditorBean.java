package com.be.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class UserAuditorBean implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {

        return Optional.empty();
    }
}
