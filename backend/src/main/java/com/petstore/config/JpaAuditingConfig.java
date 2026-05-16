package com.petstore.config;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuration for JPA auditing.
 * 
 * Separated from main application class to avoid initialization errors
 * during unit tests that use @WebMvcTest without loading JPA entities.
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {

  /**
   * Provides the current time for JPA auditing timestamps.
   *
   * @return DateTimeProvider that returns the current LocalDateTime
   */
  @Bean
  public DateTimeProvider auditingDateTimeProvider() {
    return () -> Optional.of(LocalDateTime.now());
  }
}
