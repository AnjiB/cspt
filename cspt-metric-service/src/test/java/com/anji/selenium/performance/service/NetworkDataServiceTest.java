package com.anji.selenium.performance.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.anji.selenium.performance.dao.NetworkDataRepository;
import com.anji.selenium.performance.dto.NetworkDataDto;
import com.anji.selenium.performance.entity.NetworkData;
import com.anji.selenium.performance.mapper.NetworkDataMapper;
import com.anji.selenium.performance.service.impl.NetworkDataServiceImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NetworkDataServiceTest {

  @Mock private NetworkDataRepository networkDataRepository;

  @Mock private NetworkDataMapper networkDataMapper;

  private NetworkDataService networkDataService;

  @BeforeEach
  void setUp() {
    networkDataService = new NetworkDataServiceImpl(networkDataRepository, networkDataMapper);
  }

  @Test
  void saveNetworkData_shouldSaveAllNetworkData() {
    // Arrange
    List<NetworkDataDto> dtoList =
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

    List<NetworkData> entityList =
        Arrays.asList(
            NetworkData.builder()
                .url("https://example.com/1")
                .status(200)
                .responseTime(150.5f)
                .build(),
            NetworkData.builder()
                .url("https://example.com/2")
                .status(404)
                .responseTime(50.3f)
                .build());

    when(networkDataMapper.toEntityList(dtoList)).thenReturn(entityList);

    // Act
    networkDataService.saveNetworkData(dtoList);

    // Assert
    verify(networkDataMapper, times(1)).toEntityList(dtoList);
    verify(networkDataRepository, times(1)).saveAll(entityList);
  }

  @Test
  void getAllNetworkData_shouldReturnAllNetworkData() {
    // Arrange
    List<NetworkData> entityList =
        Arrays.asList(
            NetworkData.builder()
                .id(1L)
                .url("https://example.com/1")
                .status(200)
                .responseTime(150.5f)
                .build(),
            NetworkData.builder()
                .id(2L)
                .url("https://example.com/2")
                .status(404)
                .responseTime(50.3f)
                .build());

    List<NetworkDataDto> dtoList =
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

    when(networkDataRepository.findAll()).thenReturn(entityList);
    when(networkDataMapper.toDtoList(entityList)).thenReturn(dtoList);

    // Act
    List<NetworkDataDto> result = networkDataService.getAllNetworkData();

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.size()).isEqualTo(2);
    assertThat(result).containsExactlyElementsOf(dtoList);

    verify(networkDataRepository, times(1)).findAll();
    verify(networkDataMapper, times(1)).toDtoList(entityList);
  }
}
