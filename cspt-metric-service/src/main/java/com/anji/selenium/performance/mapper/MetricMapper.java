package com.anji.selenium.performance.mapper;

import com.anji.selenium.performance.dto.MetricDto;
import com.anji.selenium.performance.entity.Metric;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MetricMapper {

  Metric toEntity(MetricDto dto);

  MetricDto toDto(Metric entity);

  List<Metric> toEntityList(List<MetricDto> dtoList);

  List<MetricDto> toDtoList(List<Metric> entityList);
}
