package com.br.project.config.swagger;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.br.project.model.Usuario;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.service.Parameter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

	@Bean
	public Docket forumApi(){
		
		ParameterBuilder tokenPar = new ParameterBuilder();
	    List<Parameter> parameters = new ArrayList<>();
	    tokenPar.name("Authorization")
	            .description("Header para token JWT")
	            .modelRef(new ModelRef("string"))
	            .parameterType("header")
	            .required(false)
	            .build();
	    
	    parameters.add(tokenPar.build());
		
		Docket docket = new Docket(DocumentationType.SWAGGER_2)				
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.br.project"))
				.paths(PathSelectors.ant("/**"))
				.build()
				.ignoredParameterTypes(Usuario.class)
				.globalOperationParameters(parameters);
		
		return docket;
		
	}
}
