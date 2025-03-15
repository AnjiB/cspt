package com.anji.selenium.performance.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.anji.selenium.performance.dao.MetricRepository;
import com.anji.selenium.performance.dto.MetricDto;
import com.anji.selenium.performance.entity.Metric;
import com.anji.selenium.performance.mapper.MetricMapper;
import com.anji.selenium.performance.service.impl.MetricServiceImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MetricServiceTest {

  @Mock private MetricRepository metricRepository;

  @Mock private MetricMapper metricMapper;

  private MetricService metricService;

  @BeforeEach
  void setUp() {
    metricService = new MetricServiceImpl(metricRepository, metricMapper);
  }

  @Test
  void saveMetricData_shouldSaveAllMetrics() {
    // Arrange
    List<MetricDto> dtoList =
        Arrays.asList(
            MetricDto.builder().document("doc1").name("metric1").value(123.45).build(),
            MetricDto.builder().document("doc2").name("metric2").value(678.90).build());

    List<Metric> entityList =
        Arrays.asList(
            Metric.builder().document("doc1").name("metric1").value(123.45).build(),
            Metric.builder().document("doc2").name("metric2").value(678.90).build());

    when(metricMapper.toEntityList(dtoList)).thenReturn(entityList);

    // Act
    metricService.saveMetricData(dtoList);

    // Assert
    verify(metricMapper, times(1)).toEntityList(dtoList);
    verify(metricRepository, times(1)).saveAll(entityList);
  }

  @Test
  void getAllMetrics_shouldReturnAllMetrics() {
    // Arrange
    List<Metric> entityList =
        Arrays.asList(
            Metric.builder().id(1L).document("doc1").name("metric1").value(123.45).build(),
            Metric.builder().id(2L).document("doc2").name("metric2").value(678.90).build());

    List<MetricDto> dtoList =
        Arrays.asList(
            MetricDto.builder().id(1L).document("doc1").name("metric1").value(123.45).build(),
            MetricDto.builder().id(2L).document("doc2").name("metric2").value(678.90).build());

    when(metricRepository.findAll()).thenReturn(entityList);
    when(metricMapper.toDtoList(entityList)).thenReturn(dtoList);

    // Act
    List<MetricDto> result = metricService.getAllMetrics();

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.size()).isEqualTo(2);
    assertThat(result).containsExactlyElementsOf(dtoList);

    verify(metricRepository, times(1)).findAll();
    verify(metricMapper, times(1)).toDtoList(entityList);
  }
}
