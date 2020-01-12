package com.kooixiuhong.astparser.api.configs.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
                .apiInfo(apiEndPointsInfo())
                .tags(new Tag("parser", "API to parse expression to Abstract Syntax Tree"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kooixiuhong.astparser.api.parser"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("AST Parser REST API Documentation")
                .description("Abstract Syntax Tree Parser REST API")
                .contact(new Contact("Kooixh", "www.kooixiuhong.com", "kooix2@gmail.com"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("0.0.1")
                .build();
    }
}
