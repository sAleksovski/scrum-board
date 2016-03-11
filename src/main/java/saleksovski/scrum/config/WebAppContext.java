package saleksovski.scrum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Created by stefan on 1/14/16.
 */
@Configuration
@ComponentScan(basePackages = {
        "saleksovski.auth",
        "saleksovski.scrum"
})
@EnableWebMvc
public class WebAppContext extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

//    @Bean
//    public SimpleMappingExceptionResolver exceptionResolver() {
//        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
//
//        Properties exceptionMappings = new Properties();
//
//        exceptionMappings.put("java.lang.Exception", "error/error");
//        exceptionMappings.put("java.lang.RuntimeException", "error/error");
//
//        exceptionResolver.setExceptionMappings(exceptionMappings);
//
//        Properties statusCodes = new Properties();
//
//        statusCodes.put("error/404", "404");
//        statusCodes.put("error/error", "500");
//
//        exceptionResolver.setStatusCodes(statusCodes);
//
//        return exceptionResolver;
//    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/static/");
        viewResolver.setSuffix(".html");

        return viewResolver;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8000")
                .allowedMethods("PUT", "POST", "GET", "OPTIONS", "DELETE")
                .allowedHeaders("*");

    }

}
