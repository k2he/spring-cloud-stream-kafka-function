package com.cloudstreamkafkafunctional.programming.consumerstreamprocessor.config;

import java.time.Duration;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "process")
@Data
@NoArgsConstructor
public class ProcessProperties {

  private Duration joinWindowDuration;
  private Duration graceWindowDuration;
}
