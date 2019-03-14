package com.example.txmessage.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.example")
//扫描dao
@EnableJpaRepositories(basePackages = "com.example")
public class JpaConfig {
}
