package ai.shoppingapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ai.shoppingapp.interceptor.AdminInterceptor;
import ai.shoppingapp.interceptor.LoginInterceptor;

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

		registry.addResourceHandler("/images/categories/**")
				.addResourceLocations("file:///D:/Shopping/shoppingapp/src/main/resources/template/images/product/");
		registry.addResourceHandler("/images/user/**")
				.addResourceLocations("file:///D:/Shopping/shoppingapp/src/main/resources/template/images/user/");
	}
}