package com.anji.sel.light;

import com.anji.sel.pojo.light.LighthouseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JacksonTest {

  public static void main(String[] args) throws IOException {

    String report = new String(Files.readAllBytes(Paths.get("target/lighthouse-report.json")));

    LighthouseResponse lr = new ObjectMapper().readValue(report, LighthouseResponse.class);

    log.info(lr.getAudits().getCumulativeLayoutShift().getDisplayValue());
  }
}
