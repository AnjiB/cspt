package com.anji.selenium.performance.service;

import com.anji.selenium.performance.dto.NetworkDataDto;
import java.util.List;

public interface NetworkDataService {
  void saveNetworkData(List<NetworkDataDto> dto);

  List<NetworkDataDto> getAllNetworkData();
}
