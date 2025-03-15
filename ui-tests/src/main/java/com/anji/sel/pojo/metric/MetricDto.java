package com.anji.sel.pojo.metric;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MetricDto {

  private String document;

  private String name;

  private Double value;
}
