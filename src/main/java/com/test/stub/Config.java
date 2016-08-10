package com.test.stub;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="items")
public class Config {
  private String config_dir;

  public void setConfigDir(String config_dir) {
    this.config_dir = config_dir;
  }

  public String getConfigDir() {
    return config_dir;
  }
}
