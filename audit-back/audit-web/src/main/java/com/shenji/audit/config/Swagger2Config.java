package com.shenji.audit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2配置类
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/15 8:17
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket getUserDocket(){
        ApiInfo apiInfo=new ApiInfoBuilder()
                .title("web后端接口")
                .description("审计处物联化管理系统")
                .version("1.1")
                .contact(new Contact(
                        "审计管理系统开发小组",
                        "https://github.com/misxress/shenji",
                        "misxress@outlok.com"))
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.shenji.audit.controller"))
                .paths(PathSelectors.any())
                .build();
    }


}
