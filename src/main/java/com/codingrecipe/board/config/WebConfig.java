package com.codingrecipe.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// WebConfig는 일반적으로 Spring MVC에서 애플리케이션의 웹 관련 설정을 정의하는 클래스입니다.
// @Configuration과 @EnableWebMvc를 사용해 설정 클래스를 선언하며, XML 기반 설정을 대신할 수 있습니다.

// 동작 방식 ( 뷰리졸버는 웹에 관련된 설정을 하는
// 클라이언트가 /upload/image.jpg 같은 URL로 요청을 보냅니다.
// Spring MVC는 addResourceHandlers 설정에 따라 /upload/** 요청을 처리할 핸들러를 찾습니다.
// 요청 경로 /upload/image.jpg는 실제 경로 C:/Users/tfo/Desktop/spring_upload_files/image.jpg로 매핑됩니다.
// 해당 경로에 파일이 존재하면 Spring은 파일을 클라이언트에게 반환합니다.
// 사용 사례
//        이 설정은 주로 파일 업로드와 다운로드 기능을 구현할 때 사용됩니다. 예를 들어:

//        사용자가 파일 업로드:
//        업로드된 파일은 savePath 경로에 저장됩니다.
//        업로드된 파일 접근:
//        저장된 파일은 클라이언트가 /upload/ 경로를 통해 직접 접근할 수 있습니다.
//        예: /upload/myfile.png

// 각종
// addResourceHandlers를 통해 요청-저장 경로 매핑을 처리합니다.

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private String resourcePath = "/upload/**"; // view에서 사용할 경로
    //private String savePath = "file:///Users/codingrecipe/development/intellij_community/spring_upload_files/"; // 실제 파일 저장 경로
    private String savePath = "file:///C:/Users/tfo/Desktop/spring_upload_files/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcePath)
                .addResourceLocations(savePath);
    }
}