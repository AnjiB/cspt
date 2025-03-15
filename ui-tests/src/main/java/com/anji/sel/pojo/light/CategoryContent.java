package com.anji.sel.pojo.light;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CategoryContent {

  private String title;

  private String id;

  private double score;
}
