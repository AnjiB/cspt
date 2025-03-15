package com.anji.selenium.performance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetricDto {

  private Long id;

  @NotBlank(message = "Document cannot be blank")
  private String document;

  @NotBlank(message = "Metric name cannot be blank")
  private String name;

  @NotNull(message = "Metric value cannot be null")
  private Double value;

  private LocalDateTime timestamp;
}
