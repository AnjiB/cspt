package com.anji.selenium.performance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "performance_metrics")
public class Metric {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String document;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Double value;

  private LocalDateTime timestamp;

  @PrePersist
  public void prePersist() {
    if (timestamp == null) {
      timestamp = LocalDateTime.now();
    }
  }
}
