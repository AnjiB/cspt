package com.anji.sel.pojo.light;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LighthouseResponse {
  private String lighthouseVersion;

  private String requestedUrl;

  private String mainDocumentUrl;

  private String finalDisplayedUrl;

  private String finalUrl;

  private ArrayList<String> runWarnings;

  private Audit audits;

  private Category categories;
}
