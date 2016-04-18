package saleksovski.scrum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

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

    @Resource
    private Environment env;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String allowedOrigins = env.getProperty("app.frontendUrl");
        if (allowedOrigins == null) {
            allowedOrigins = "http://localhost:8000";
        }
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("PUT", "POST", "GET", "OPTIONS", "DELETE")
                .allowedHeaders("*");
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/static/");
        viewResolver.setSuffix(".html");

        return viewResolver;
    }

    private static String FRONTEND_URL;

    public static String getFrontendUrl() {
        return FRONTEND_URL;
    }

    @PostConstruct
    private void init() {
        FRONTEND_URL = env.getProperty("app.frontendUrl");
        if (FRONTEND_URL == null) {
            FRONTEND_URL = "http://localhost:8000";
        }
    }

}
