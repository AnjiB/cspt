package com.anji.selenium.performance.dao;

import com.anji.selenium.performance.entity.Metric;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricRepository extends JpaRepository<Metric, Long> {}
