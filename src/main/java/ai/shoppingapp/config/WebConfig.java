package ai.shoppingapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/images/categories/**")
                .addResourceLocations(
                        "file:///D:/Shopping/shoppingapp/src/main/resources/template/images/product/"
                );
        registry.addResourceHandler("/images/user/**")
        .addResourceLocations(
                "file:///D:/Shopping/shoppingapp/src/main/resources/template/images/user/"
        );
    }
}
