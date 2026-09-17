package ai.shoppingapp.config;

import ai.shoppingapp.interceptor.AdminInterceptor;
import ai.shoppingapp.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;
    private final AdminInterceptor adminInterceptor;

    public WebConfig(LoginInterceptor loginInterceptor, AdminInterceptor adminInterceptor) {
        this.loginInterceptor = loginInterceptor;
        this.adminInterceptor = adminInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 1. Login Interceptor - protects private user areas, allows public pages & assets
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                    // Static Assets
                    "/css/**",
                    "/js/**",
                    "/images/**",
//                    "/favicon.ico",
                    "/error",

                    // Public Storefront Pages
                    "/",
                    "/home",
                    "/products/**",
                    "/categories/**",

                    // Public Auth Pages
                    "/login",
                    "/register",
                    "/verify-otp",
                    "/forgot-password",
                    "/reset-password"
                );

        // 2. Admin Interceptor - protects admin routes
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map standard static resources inside src/main/resources/static/
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
//        registry.addResourceHandler("/images/**")
//                .addResourceLocations("classpath:/static/images/");

        // External category image upload path if needed
        registry.addResourceHandler("/images/categories/**")
                .addResourceLocations("file:///D:/Shopping/shoppingapp/src/main/resources/static/images/categories/");
    }
}