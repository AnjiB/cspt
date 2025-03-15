package com.anji.selenium.performance.service.impl;

import com.anji.selenium.performance.dao.MetricRepository;
import com.anji.selenium.performance.dto.MetricDto;
import com.anji.selenium.performance.entity.Metric;
import com.anji.selenium.performance.mapper.MetricMapper;
import com.anji.selenium.performance.service.MetricService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MetricServiceImpl implements MetricService {

  private final MetricRepository metricRepository;
  private final MetricMapper metricMapper;

  @Override
  @Transactional
  public void saveMetricData(List<MetricDto> dto) {
    List<Metric> metricEntityList = metricMapper.toEntityList(dto);
    metricRepository.saveAll(metricEntityList);
  }

  @Override
  @Transactional(readOnly = true)
  public List<MetricDto> getAllMetrics() {
    List<Metric> metrics = metricRepository.findAll();
    return metricMapper.toDtoList(metrics);
  }
}
