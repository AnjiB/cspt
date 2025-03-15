package com.anji.selenium.performance.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.anji.selenium.performance.PerformanceMetricsApplication;
import com.anji.selenium.performance.TestcontainersConfiguration;
import com.anji.selenium.performance.dao.MetricRepository;
import com.anji.selenium.performance.dao.NetworkDataRepository;
import com.anji.selenium.performance.dto.MetricDto;
import com.anji.selenium.performance.dto.NetworkDataDto;
import com.anji.selenium.performance.entity.Metric;
import com.anji.selenium.performance.entity.NetworkData;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = PerformanceMetricsApplication.class)
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
@Testcontainers
@ActiveProfiles("test")
@Tag("integration")
class ApiIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private MetricRepository metricRepository;

  @Autowired private NetworkDataRepository networkDataRepository;

  @BeforeEach
  void setUp() {
    metricRepository.deleteAll();
    networkDataRepository.deleteAll();
  }

  @Test
  void saveAndGetNetworkData_shouldWorkEndToEnd() throws Exception {
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

    // Act & Assert - Save
    mockMvc
        .perform(
            post("/api/network-data")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(networkDataList)))
        .andExpect(status().isCreated());

    // Verify data was saved
    List<NetworkData> savedData = networkDataRepository.findAll();
    assertThat(savedData).hasSize(2);

    // Act & Assert - Get
    mockMvc
        .perform(get("/api/network-data"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].url").value("https://example.com/1"))
        .andExpect(jsonPath("$[0].status").value(200))
        .andExpect(jsonPath("$[1].url").value("https://example.com/2"))
        .andExpect(jsonPath("$[1].status").value(404));
  }

  @Test
  void saveAndGetMetrics_shouldWorkEndToEnd() throws Exception {
    // Arrange
    List<MetricDto> metricList =
        Arrays.asList(
            MetricDto.builder().document("doc1").name("metric1").value(123.45).build(),
            MetricDto.builder().document("doc2").name("metric2").value(678.90).build());

    // Act & Assert - Save
    mockMvc
        .perform(
            post("/api/performance-metrics")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(metricList)))
        .andExpect(status().isCreated());

    // Verify data was saved
    List<Metric> savedMetrics = metricRepository.findAll();
    assertThat(savedMetrics).hasSize(2);

    // Act & Assert - Get
    mockMvc
        .perform(get("/api/performance-metrics"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].document").value("doc1"))
        .andExpect(jsonPath("$[0].name").value("metric1"))
        .andExpect(jsonPath("$[1].document").value("doc2"))
        .andExpect(jsonPath("$[1].name").value("metric2"));
  }

  @Test
  void saveNetworkData_withInvalidData_shouldReturnBadRequest() throws Exception {
    // Arrange - Missing required fields
    List<NetworkDataDto> invalidList =
        Arrays.asList(NetworkDataDto.builder().url("").status(200).responseTime(150.5f).build());

    // Act & Assert
    mockMvc
        .perform(
            post("/api/network-data")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidList)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void saveMetrics_withInvalidData_shouldReturnBadRequest() throws Exception {
    // Arrange - Missing required fields
    List<MetricDto> invalidList =
        Arrays.asList(MetricDto.builder().document("").name("metric1").value(123.45).build());

    // Act & Assert
    mockMvc
        .perform(
            post("/api/performance-metrics")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidList)))
        .andExpect(status().isBadRequest());
  }
}
