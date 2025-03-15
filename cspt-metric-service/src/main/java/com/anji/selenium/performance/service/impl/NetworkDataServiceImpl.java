package com.anji.selenium.performance.service.impl;

import com.anji.selenium.performance.dao.NetworkDataRepository;
import com.anji.selenium.performance.dto.NetworkDataDto;
import com.anji.selenium.performance.entity.NetworkData;
import com.anji.selenium.performance.mapper.NetworkDataMapper;
import com.anji.selenium.performance.service.NetworkDataService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NetworkDataServiceImpl implements NetworkDataService {

  private final NetworkDataRepository networkDataRepository;
  private final NetworkDataMapper networkDataMapper;

  @Override
  @Transactional
  public void saveNetworkData(List<NetworkDataDto> dto) {
    List<NetworkData> networkDataEntityList = networkDataMapper.toEntityList(dto);
    networkDataRepository.saveAll(networkDataEntityList);
  }

  @Override
  @Transactional(readOnly = true)
  public List<NetworkDataDto> getAllNetworkData() {
    List<NetworkData> networkData = networkDataRepository.findAll();
    return networkDataMapper.toDtoList(networkData);
  }
}
