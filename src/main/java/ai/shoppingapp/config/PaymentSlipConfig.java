package ai.shoppingapp.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PaymentSlipConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        
        Path uploadDir = Paths.get("./uploads/payment-proofs");
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/payment-proofs/**")
                .addResourceLocations("file:/" + uploadPath + "/");
    }
}