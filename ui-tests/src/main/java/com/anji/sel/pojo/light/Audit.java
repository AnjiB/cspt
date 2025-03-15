package com.anji.sel.pojo.light;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Audit {

  @JsonProperty(value = "first-contentful-paint")
  private AuditContent firstContentfulPaint;

  @JsonProperty(value = "largest-contentful-paint")
  private AuditContent largestContentfulPaint;

  @JsonProperty(value = "first-meaningful-paint")
  private AuditContent firstMeaningfulPaint;

  @JsonProperty(value = "speed-index")
  private AuditContent speedIndex;

  @JsonProperty(value = "total-blocking-time")
  private AuditContent totalBlockingTime;

  @JsonProperty(value = "cumulative-layout-shift")
  private AuditContent cumulativeLayoutShift;

  @JsonProperty(value = "server-response-time")
  private AuditContent serverResponseTime;

  @JsonProperty(value = "uses-long-cache-ttl")
  private AuditContent usesLongCacheTtl;

  @JsonProperty(value = "total-byte-weight")
  private AuditContent totalByteWeight;
}
