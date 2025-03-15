package com.anji.selenium.performance.rest;

import com.anji.selenium.performance.dto.MetricDto;
import com.anji.selenium.performance.dto.NetworkDataDto;
import com.anji.selenium.performance.service.MetricService;
import com.anji.selenium.performance.service.NetworkDataService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class ApiController {

  private final NetworkDataService networkDataService;
  private final MetricService metricService;

  @PostMapping(value = "/network-data", consumes = "application/json")
  public ResponseEntity<Void> saveNetworkData(
      @Valid @RequestBody @NotEmpty List<NetworkDataDto> networkDto) {
    networkDataService.saveNetworkData(networkDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/network-data")
  public ResponseEntity<List<NetworkDataDto>> getAllNetworkData() {
    return ResponseEntity.ok(networkDataService.getAllNetworkData());
  }

  @PostMapping(value = "/performance-metrics", consumes = "application/json")
  public ResponseEntity<Void> savePerformanceMetrics(
      @Valid @RequestBody @NotEmpty List<MetricDto> metricDto) {
    metricService.saveMetricData(metricDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/performance-metrics")
  public ResponseEntity<List<MetricDto>> getAllMetrics() {
    return ResponseEntity.ok(metricService.getAllMetrics());
  }
}
