package com.anji.selenium.performance.mapper;

import com.anji.selenium.performance.dto.NetworkDataDto;
import com.anji.selenium.performance.entity.NetworkData;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NetworkDataMapper {

  NetworkData toEntity(NetworkDataDto dto);

  NetworkDataDto toDto(NetworkData entity);

  List<NetworkData> toEntityList(List<NetworkDataDto> dtoList);

  List<NetworkDataDto> toDtoList(List<NetworkData> entityList);
}
