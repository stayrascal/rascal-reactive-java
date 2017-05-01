package com.stayrascal.api.util.swagger;

import com.fasterxml.classmate.TypeResolver;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.lang.reflect.WildcardType;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

public class SwaggerDocketFactory {

    public static Docket createDocket(String basePackage, String groupName, ApiInfo apiInfo, Class... ignoredParameters) {
        TypeResolver typeResolver = new TypeResolver();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo)
                .useDefaultResponseMessages(false)
                .ignoredParameterTypes(addPrincipalToIgnoredParameters(ignoredParameters))
                .directModelSubstitute(Collection.class, List.class)
                .globalResponseMessage(RequestMethod.GET,
                        newArrayList(new ResponseMessageBuilder()
                                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message("Code: 999 <br /> Internal Sever Error")
                                .responseModel(new ModelRef("ResponseError"))
                                .build()))
                .alternateTypeRules(newRule(
                        typeResolver.resolve(Collection.class, WildcardType.class),
                        typeResolver.resolve(List.class, WildcardType.class)));
        addJodaTimeIfAvailable(docket);
        return docket;
    }

    private static void addJodaTimeIfAvailable(Docket docket) {
        if (ClassUtils.isPresent("org.joda.time.DateTime", null)) {
            docket.directModelSubstitute(DateTime.class, String.class)
                    .directModelSubstitute(LocalDate.class, String.class);
        }
    }

    private static Class[] addPrincipalToIgnoredParameters(Class[] ignoredParameters) {
        List<Class> ignoredParametersList = new ArrayList<>();
        ignoredParametersList.add(Principal.class);
        ignoredParametersList.addAll(Arrays.asList(ignoredParameters));
        return ignoredParametersList.toArray(new Class[ignoredParametersList.size()]);

    }
}
