package com.anji.selenium.performance.service;

import com.anji.selenium.performance.dto.MetricDto;
import java.util.List;

public interface MetricService {
  void saveMetricData(List<MetricDto> dto);

  List<MetricDto> getAllMetrics();
}
