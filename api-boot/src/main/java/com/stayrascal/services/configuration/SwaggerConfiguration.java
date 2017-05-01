package com.stayrascal.services.configuration;

import com.stayrascal.api.util.swagger.SwaggerDocketFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value(value = "${info.app.name}")
    private String name;

    @Value(value = "${info.version}")
    private String version;

    @Value(value = "${swagger.app.description}")
    private String description;

    @Value(value = "${info.app.base.url:https://}")
    private String protocol;

    @Value(value = "${info.contact.email}")
    private String email;


    @Bean
    public Docket createDocket() {
        return SwaggerDocketFactory
                .createDocket("com.stayrascal.services.rest", "v1", apiInfo(description))
                .securitySchemes(singletonList(new BasicAuth("basic")))
                .globalOperationParameters(singletonList(correlationIdParam()))
                .protocols(extractProtocol())
                .globalResponseMessage(RequestMethod.GET, asList(
                        createResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Code: 999\n Internal Server Error"),
                        createResponseMessage(HttpStatus.NOT_FOUND.value(), "Code: 003\n Not Found"),
                        createResponseMessage(HttpStatus.FORBIDDEN.value(), "Forbidden"),
                        createResponseMessage(HttpStatus.UNAUTHORIZED.value(), "Unauthorized"),
                        createResponseMessage(HttpStatus.BAD_REQUEST.value(), "Code: 008 \n Bad Request")
                ));
    }

    private Parameter correlationIdParam() {
        return new ParameterBuilder().name("Correlation-ID")
                .description("The correlation id of request")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build();
    }

    private ApiInfo apiInfo(String description) {
        return new ApiInfo(name, description, version, "", new Contact("", "", email), "", "");
    }

    private Set<String> extractProtocol() {
        return newHashSet(protocol.split(":")[0]);
    }

    private ResponseMessage createResponseMessage(int statusCode, String message) {
        return new ResponseMessageBuilder()
                .code(statusCode)
                .message(message)
                .build();
    }
}
