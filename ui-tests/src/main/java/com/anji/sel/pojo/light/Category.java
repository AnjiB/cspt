package com.anji.sel.pojo.light;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Category {

  private CategoryContent accessibility;

  private CategoryContent performance;
}
