package com.ysy.swagger.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration // java 소스가 설정 파일로 쓰인다는 의미
public class SwaggerConfig {
	
	/**
	 * 접속 URL -> http://localhost:8000/swagger-ui/index.html
	 * */
	@Bean // Docket : Swagger 설정의 핵심이 되는 Bean
	public Docket swaggerApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.consumes(getConsumeContentTypes())
				.produces(getProduceContentTypes())
				.apiInfo(swaggerInfo()) // Swagger UI로 노출 할 swagger 정보 등록
				.securityContexts(Arrays.asList(securityContext()))
				.securitySchemes(Arrays.asList(apiKey()))
				.select()
				.apis(RequestHandlerSelectors.any()) // api 스펙이 작성되어있는 패키지 (Controller) 지정
				.paths(PathSelectors.any()) // apis에 있는 API중 특정 path 선택 (현재 : 모든 Controller)
				.build()
				.useDefaultResponseMessages(false) // Swagger 기본 제공 응답코드 (200,401,403,404...) 노출 할지 말지 여부
//				.globalResponseMessage(RequestMethod.POST, getPostMessage()) // 커스텀 응답코드 메세지
				;
	}
	
	// Swagger UI를 보여주기 위한 설정 값 swaggerApi 메소드에서 사용됨
	private Set<String> getConsumeContentTypes() {
		Set<String> consumes = new HashSet<>();
		consumes.add("application/json;charset=UTF-8");
		consumes.add("application/x-www-form-urlencoded");
		return consumes;
	}
	
	private Set<String> getProduceContentTypes() {
		Set<String> produces = new HashSet<>();
		produces.add("application/json;charset=UTF-8");
		return produces;
	}
	
	// Swagger 정보
	private ApiInfo swaggerInfo() {
		return new ApiInfoBuilder()
				.title("S-POS Swagger")
				.version("1.0")
				.description("API description")
				.build();
	}
	
	// 인증 방식 설정
	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.build();
	}
	
	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
	}
	
	// Authorize 버튼 클릭 시 입력 값 설정
    private ApiKey apiKey(){
        return new ApiKey("Authorization", "Bearer", "header");
    }
	
//	// 커스텀 응답코드 메세지
//	 private List<ResponseMessage> getPostMessage() {
//		 List<ResponseMessage> lists = new ArrayList<ResponseMessage>();
//       lists.add(new ResponseMessageBuilder().code(500).message("500 에러 !!").build());
//       lists.add(new ResponseMessageBuilder().code(403).message("403 에러 !!").build());
//       lists.add(new ResponseMessageBuilder().code(401).message("401 에러 !!").build());
//       return lists;
//   }
}
