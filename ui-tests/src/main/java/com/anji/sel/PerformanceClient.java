package com.anji.sel;

import com.anji.sel.pojo.metric.MetricDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.util.List;

public class PerformanceClient {

  private static final String BASE_PATH = "/api/performance-metrics";

  private static final String CLIENT_BASE_URI = "http://localhost:8080";

  private RequestSpecification spec;

  public PerformanceClient() {
    spec = RestAssured.given();
  }

  public void sendMetrics(List<MetricDto> request) {

    spec.baseUri(CLIENT_BASE_URI)
        .basePath(BASE_PATH)
        .contentType(ContentType.JSON)
        .body(request)
        .post()
        .then()
        .statusCode(201);
  }
}
