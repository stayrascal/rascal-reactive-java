package com.stayrascal.api.util.swagger;

import com.google.common.base.Optional;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 10)
public class SpringOperationNotesReader implements OperationBuilderPlugin {

    @Autowired
    private Environment environment;

    @Override
    public void apply(OperationContext context) {
        Optional<ApiOperation> apiOperation = context.findAnnotation(ApiOperation.class);
        if (apiOperation.isPresent() && StringUtils.hasText(apiOperation.get().notes())) {
            context.operationBuilder().notes(environment.resolvePlaceholders(apiOperation.get().notes()));
        }
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return false;
    }
}
