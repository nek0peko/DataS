package pers.nek0peko.datas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Knife4jConfiguration
 *
 * @author nek0peko
 * @date 2022/11/17
 */
@Configuration
@EnableOpenApi
public class Knife4jConfiguration {

    @Value("${version}")
    private String version;

    @Bean(value = "datasourceApi")
    public Docket datasourceApi() {
        return defaultApi("数据源", "pers.nek0peko.datas.controller.datasource");
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("DataS")
                .description("This is description.")
                .termsOfServiceUrl("")
                .contact(new Contact("nek0peko", "nek0peko.com", "sakura-migao@qq.com"))
                .version(version)
                .build();
    }

    private Docket defaultApi(String groupName, String packageName) {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage(packageName))
                .paths(PathSelectors.any())
                .build();
    }

}

