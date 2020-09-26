package com.api.enrollee.swagger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  public static final Contact DEFAULT_CONTACT = new Contact(
      "Sai Krishna Sundeep", "http://www.test.com", "saireddy.psk@gmail.com");
  
  private ApiInfo apiInfo() {
      return new ApiInfoBuilder().title("Enroll API").version("1.0")
              .description("Enroll webservice implementation").build();
  }

  private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = 
      new HashSet<String>(Arrays.asList("application/json"));

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select().apis(RequestHandlerSelectors.basePackage("com.api.enroll.controller")).build()
        .produces(DEFAULT_PRODUCES_AND_CONSUMES)
        .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
  }
  
  @Bean
  public UiConfiguration uiConfig() {
      return UiConfigurationBuilder
              .builder()
              .operationsSorter(OperationsSorter.METHOD)
              .build();
  }
}
