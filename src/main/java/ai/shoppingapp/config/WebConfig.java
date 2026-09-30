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

		registry.addInterceptor(loginInterceptor).addPathPatterns("route");

		registry.addInterceptor(adminInterceptor).addPathPatterns("route");

	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("/images/product/**")
				.addResourceLocations("file:///D:/Shopping/my-shoppingboot-app/src/main/resources/static/images/product/");
		registry.addResourceHandler("/images/user/**")
				.addResourceLocations("file:///D:/Shopping/my-shoppingboot-app/src/main/resources/static/images/user/");
	


}
}
