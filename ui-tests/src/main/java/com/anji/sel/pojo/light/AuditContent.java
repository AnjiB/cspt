package com.anji.sel.pojo.light;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuditContent {

  private String id;

  private String title;

  private String description;

  private double score;

  private double numericValue;

  private String displayValue;
}
