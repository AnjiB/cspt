package com.anji.selenium.performance.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class NetworkDataDto {

  private Long id;

  @NotBlank(message = "URL cannot be blank")
  private String url;

  @NotNull(message = "Response status cannot be null")
  @Min(value = 100, message = "Response status must be at least 100")
  @Max(value = 599, message = "Response status must be at most 599")
  private Integer status;

  @NotNull(message = "Response time cannot be null")
  private Float responseTime;

  private Float predictedResponseTime;

  private LocalDateTime timestamp;
}
