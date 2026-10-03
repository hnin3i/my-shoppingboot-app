package ai.shoppingapp.config;

//import ai.shoppingapp.interceptor.AdminInterceptor;
//import ai.shoppingapp.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


<<<<<<< HEAD
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

                    //Errors
                    "/not-authorized",
                    "/error/**",
                    "/errors",
                    "/errors/**",

                    // Public Storefront Pages
                    "/",
                    "/home",
                    "/products/**",
                    "/categories/**",

                    // Public Auth Pages
                    "/user/**",
                    "/login",
                    "/register",
                    "/verify-otp",
                    "/forgot-password",
                    "/reset-password",
                    
                    //Order History
                    "/order-history",                  
                    "/api/orders/place",
                    "/cart",
                    "/checkout",
                    "/order-success",
                    "/index",

                    "/products/**",
                    "/details"

                    
                );
        		

        // 2. Admin Interceptor - protects admin routes
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**");
    }
=======
//   private final LoginInterceptor loginInterceptor;
//   private final AdminInterceptor adminInterceptor;
//
//   public WebConfig(LoginInterceptor loginInterceptor, AdminInterceptor adminInterceptor) {
//   this.loginInterceptor = loginInterceptor;
//   this.adminInterceptor = adminInterceptor;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 1. Login Interceptor - protects private user areas, allows public pages & assets
//        registry.addInterceptor(loginInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns(
//                    // Static Assets
//                    "/css/**",
//                    "/js/**",
//                    "/images/**",
//
//                    //Errors
//                    "/not-authorized",
//                    "/error/**",
//                    "/errors",
//                    "/errors/**",
//
//                    // Public Storefront Pages
//                    "/",
//                    "/home",
//                    "/products/**",
//                    "/categories/**",
//
//                    // Public Auth Pages
//                    "/login",
//                    "/register",
//                    "/verify-otp",
//                    "/forgot-password",
//                    "/reset-password",
//                    
//                    //Order History
//                    "/order-history",
//                    "/products/**",
//                    "/details"
//                    
//                );
//        		
//
//        // 2. Admin Interceptor - protects admin routes
//        registry.addInterceptor(adminInterceptor)
//                .addPathPatterns("/admin/**");
//    }
>>>>>>> 96580e3a09d201d07e9f097d97daa8a08263c13c

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map standard static resources inside src/main/resources/static/
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");

<<<<<<< HEAD
		registry.addResourceHandler("static/images/**")
				.addResourceLocations("file://C://Users/Admin/git/my-shoppingboot-app/src/main/resources/static/images/");
		
	}
}
=======
        registry.addResourceHandler("/images/product/")
        .addResourceLocations("file:///D:/MyShop/my-shoppingboot-app/src/main/resources/static/images/product/");
    registry.addResourceHandler("/images/user/")
        .addResourceLocations("file:///D:/Myshop/my-shoppingboot-app/src/main/resources/static/images/user/");
  }
}

>>>>>>> 96580e3a09d201d07e9f097d97daa8a08263c13c
