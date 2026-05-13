package com.petstore.config;

import java.net.URI;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configuration to handle Render PostgreSQL database URL format conversion.
 * 
 * Render provides DATABASE_URL in postgresql:// format, but we need JDBC format.
 * This configuration automatically converts between formats.
 */
@Configuration
@Profile("prod")
public class RenderDatabaseConfig {

  /**
   * Parse Render's DATABASE_URL and convert to JDBC DataSource
   */
  @Bean
  public DataSource dataSource() {
    String databaseUrl = System.getenv("JDBC_DATABASE_URL");
    String dbUser = System.getenv("DB_USER");
    String dbPassword = System.getenv("DB_PASSWORD");

    // If we have all separate components, use them
    if (databaseUrl != null && dbUser != null && dbPassword != null) {
      // Ensure URL starts with jdbc: prefix
      if (!databaseUrl.startsWith("jdbc:")) {
        databaseUrl = convertPostgresUrlToJdbc(databaseUrl);
      }
      
      return DataSourceBuilder.create()
        .driverClassName("org.postgresql.Driver")
        .url(databaseUrl)
        .username(dbUser)
        .password(dbPassword)
        .build();
    }

    // Fallback to DATABASE_URL if separate components aren't available
    String fallbackUrl = System.getenv("DATABASE_URL");
    if (fallbackUrl != null) {
      if (!fallbackUrl.startsWith("jdbc:")) {
        fallbackUrl = convertPostgresUrlToJdbc(fallbackUrl);
      }
      return DataSourceBuilder.create()
        .driverClassName("org.postgresql.Driver")
        .url(fallbackUrl)
        .build();
    }

    // Default fallback
    return DataSourceBuilder.create()
      .driverClassName("org.postgresql.Driver")
      .url("jdbc:postgresql://localhost:5432/petstore_db")
      .username("petstore_user")
      .password("petstore_password")
      .build();
  }

  /**
   * Convert PostgreSQL URL format (postgresql://user:pass@host/db)
   * to JDBC format (jdbc:postgresql://host/db)
   */
  private String convertPostgresUrlToJdbc(String postgresUrl) {
    try {
      // Remove postgresql:// scheme
      if (postgresUrl.startsWith("postgresql://")) {
        postgresUrl = postgresUrl.substring("postgresql://".length());
      }

      // Parse the URL
      URI uri = new URI("http://" + postgresUrl); // Use http as a parsing helper
      String host = uri.getHost();
      int port = uri.getPort();
      String path = uri.getPath();

      // Reconstruct as JDBC URL
      String jdbcUrl = "jdbc:postgresql://" + host;
      if (port > 0) {
        jdbcUrl += ":" + port;
      } else {
        jdbcUrl += ":5432"; // Default PostgreSQL port
      }
      if (path != null && !path.isEmpty()) {
        jdbcUrl += path;
      } else {
        jdbcUrl += "/petstore_db";
      }

      return jdbcUrl;
    } catch (Exception e) {
      // If parsing fails, return URL with jdbc: prefix
      if (!postgresUrl.startsWith("jdbc:")) {
        return "jdbc:" + postgresUrl;
      }
      return postgresUrl;
    }
  }
}
