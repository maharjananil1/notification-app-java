package com.bitskraft.notification.common.configuration;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/** created on: 10/9/23 created by: Anil Maharjan */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
  private final ServletContext servletContext;
  @Value("${user-defined.swagger.base-url}")
  private String baseUrl;

  public SwaggerConfig(ServletContext servletContext) {
    this.servletContext = servletContext;
  }

  @Bean
  public Docket notificationApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .protocols(Stream.of("http", "https").collect(Collectors.toSet()))
        .apiInfo(apiInfo())
        //        .securityContexts(Collections.singletonList(securityContext()))
        //        .securitySchemes(Collections.singletonList(apiKey()))
        .pathProvider(
            new RelativePathProvider(servletContext) {
              @Override
              public String getApplicationBasePath() {
                return baseUrl;
              }
            })
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.bitskraft.notification"))
        .build();
  }

  private ApiKey apiKey() {
    return new ApiKey("JWT", "Authorization", "header");
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder().securityReferences(defaultAuth()).build();
  }

  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
  }

  private ApiInfo apiInfo() {
    return new ApiInfo(
        "Notification Service API",
        "Provides all the admin API",
        "1.0",
        "http://digiconnect.com.np",
        new Contact("DigiConnect Teams", "http://digiconnect.com.np", "info@digiconnect.com.np"),
        "",
        "",
        Collections.emptyList());
  }
}
