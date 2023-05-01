package com.example.restdocsexample.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class CustomWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/api-spec/**")
      .addResourceLocations("classpath:/build/api-spec/");
  }

}
