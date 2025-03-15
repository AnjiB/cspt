package com.anji.selenium.performance.rest;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.anji.selenium.performance.dto.MetricDto;
import com.anji.selenium.performance.dto.NetworkDataDto;
import com.anji.selenium.performance.service.MetricService;
import com.anji.selenium.performance.service.NetworkDataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ApiController.class)
class ApiControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private NetworkDataService networkDataService;

  @MockBean private MetricService metricService;

  @Test
  void saveNetworkData_shouldReturnCreatedStatus() throws Exception {
    // Arrange
    List<NetworkDataDto> networkDataList =
        Arrays.asList(
            NetworkDataDto.builder()
                .url("https://example.com/1")
                .status(200)
                .responseTime(150.5f)
                .build(),
            NetworkDataDto.builder()
                .url("https://example.com/2")
                .status(404)
                .responseTime(50.3f)
                .build());

    doNothing().when(networkDataService).saveNetworkData(networkDataList);

    // Act & Assert
    mockMvc
        .perform(
            post("/api/network-data")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(networkDataList)))
        .andExpect(status().isCreated());

    verify(networkDataService, times(1)).saveNetworkData(networkDataList);
  }

  @Test
  void saveNetworkData_withEmptyList_shouldReturnBadRequest() throws Exception {
    // Arrange
    List<NetworkDataDto> emptyList = Collections.emptyList();

    // Act & Assert
    mockMvc
        .perform(
            post("/api/network-data")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emptyList)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getAllNetworkData_shouldReturnAllData() throws Exception {
    // Arrange
    List<NetworkDataDto> networkDataList =
        Arrays.asList(
            NetworkDataDto.builder()
                .id(1L)
                .url("https://example.com/1")
                .status(200)
                .responseTime(150.5f)
                .build(),
            NetworkDataDto.builder()
                .id(2L)
                .url("https://example.com/2")
                .status(404)
                .responseTime(50.3f)
                .build());

    when(networkDataService.getAllNetworkData()).thenReturn(networkDataList);

    // Act & Assert
    mockMvc
        .perform(get("/api/network-data"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].url").value("https://example.com/1"))
        .andExpect(jsonPath("$[0].status").value(200))
        .andExpect(jsonPath("$[1].url").value("https://example.com/2"))
        .andExpect(jsonPath("$[1].status").value(404));

    verify(networkDataService, times(1)).getAllNetworkData();
  }

  @Test
  void savePerformanceMetrics_shouldReturnCreatedStatus() throws Exception {
    // Arrange
    List<MetricDto> metricList =
        Arrays.asList(
            MetricDto.builder().document("doc1").name("metric1").value(123.45).build(),
            MetricDto.builder().document("doc2").name("metric2").value(678.90).build());

    doNothing().when(metricService).saveMetricData(metricList);

    // Act & Assert
    mockMvc
        .perform(
            post("/api/performance-metrics")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(metricList)))
        .andExpect(status().isCreated());

    verify(metricService, times(1)).saveMetricData(metricList);
  }

  @Test
  void savePerformanceMetrics_withEmptyList_shouldReturnBadRequest() throws Exception {
    // Arrange
    List<MetricDto> emptyList = Collections.emptyList();

    // Act & Assert
    mockMvc
        .perform(
            post("/api/performance-metrics")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emptyList)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getAllMetrics_shouldReturnAllMetrics() throws Exception {
    // Arrange
    List<MetricDto> metricList =
        Arrays.asList(
            MetricDto.builder().id(1L).document("doc1").name("metric1").value(123.45).build(),
            MetricDto.builder().id(2L).document("doc2").name("metric2").value(678.90).build());

    when(metricService.getAllMetrics()).thenReturn(metricList);

    // Act & Assert
    mockMvc
        .perform(get("/api/performance-metrics"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].document").value("doc1"))
        .andExpect(jsonPath("$[0].name").value("metric1"))
        .andExpect(jsonPath("$[1].document").value("doc2"))
        .andExpect(jsonPath("$[1].name").value("metric2"));

    verify(metricService, times(1)).getAllMetrics();
  }
}
