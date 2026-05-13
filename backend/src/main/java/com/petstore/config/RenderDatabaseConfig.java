package com.petstore.config;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configuration to handle Render PostgreSQL database URL format conversion.
 * 
 * Render provides DATABASE_URL in postgresql://user:password@host:port/database format.
 * This configuration parses and extracts credentials to pass to the JDBC driver.
 */
@Configuration
@Profile("prod")
public class RenderDatabaseConfig {

  /**
   * Parse Render's DATABASE_URL and create a properly configured DataSource
   * 
   * Render URL format: postgresql://user:password@host:port/database
   * We need to extract: username, password, host, port, database name
   */
  @Bean
  public DataSource dataSource() {
    String databaseUrl = System.getenv("DATABASE_URL");
    
    if (databaseUrl == null || databaseUrl.isBlank()) {
      // Fallback to defaults
      return DataSourceBuilder.create()
        .driverClassName("org.postgresql.Driver")
        .url("jdbc:postgresql://localhost:5432/petstore_db")
        .username("petstore_user")
        .password("petstore_password")
        .build();
    }

    // Parse the PostgreSQL URL
    DatabaseConfig config = parseDatabaseUrl(databaseUrl);
    
    return DataSourceBuilder.create()
      .driverClassName("org.postgresql.Driver")
      .url(config.jdbcUrl)
      .username(config.username)
      .password(config.password)
      .build();
  }

  /**
   * Parse PostgreSQL URL and extract connection details
   * Format: postgresql://user:password@host:port/database
   */
  private DatabaseConfig parseDatabaseUrl(String dbUrl) {
    try {
      // Remove scheme
      if (dbUrl.startsWith("postgresql://")) {
        dbUrl = dbUrl.substring("postgresql://".length());
      } else if (dbUrl.startsWith("postgres://")) {
        dbUrl = dbUrl.substring("postgres://".length());
      }

      // Extract credentials and host info
      String credentials = "";
      String hostInfo = dbUrl;
      
      if (dbUrl.contains("@")) {
        String[] parts = dbUrl.split("@");
        credentials = parts[0];
        hostInfo = parts[1];
      }

      // Parse credentials
      String username = "";
      String password = "";
      if (!credentials.isEmpty()) {
        if (credentials.contains(":")) {
          String[] userPass = credentials.split(":", 2);
          username = URLDecoder.decode(userPass[0], StandardCharsets.UTF_8);
          password = URLDecoder.decode(userPass[1], StandardCharsets.UTF_8);
        } else {
          username = URLDecoder.decode(credentials, StandardCharsets.UTF_8);
        }
      }

      // Parse host info
      String host = "";
      int port = 5432; // Default PostgreSQL port
      String database = "petstore_db";

      if (hostInfo.contains("/")) {
        String[] parts = hostInfo.split("/", 2);
        String hostPort = parts[0];
        database = parts[1];

        if (hostPort.contains(":")) {
          String[] hp = hostPort.split(":");
          host = hp[0];
          try {
            port = Integer.parseInt(hp[1]);
          } catch (NumberFormatException e) {
            port = 5432;
          }
        } else {
          host = hostPort;
        }
      } else {
        if (hostInfo.contains(":")) {
          String[] hp = hostInfo.split(":");
          host = hp[0];
          try {
            port = Integer.parseInt(hp[1]);
          } catch (NumberFormatException e) {
            port = 5432;
          }
        } else {
          host = hostInfo;
        }
      }

      // Build JDBC URL
      String jdbcUrl = "jdbc:postgresql://" + host + ":" + port + "/" + database;

      return new DatabaseConfig(jdbcUrl, username, password);
    } catch (Exception e) {
      // Fallback to defaults if parsing fails
      return new DatabaseConfig(
        "jdbc:postgresql://localhost:5432/petstore_db",
        "petstore_user",
        "petstore_password"
      );
    }
  }

  /**
   * Helper class to hold parsed database configuration
   */
  private static class DatabaseConfig {
    final String jdbcUrl;
    final String username;
    final String password;

    DatabaseConfig(String jdbcUrl, String username, String password) {
      this.jdbcUrl = jdbcUrl;
      this.username = username;
      this.password = password;
    }
  }
}
