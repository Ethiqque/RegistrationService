package ethiqque.registration;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@SpringBootApplication
@EnableScheduling
@EnableFeignClients("ethiqque.registration")
public class RegistrationServiceApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(RegistrationServiceApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }
}