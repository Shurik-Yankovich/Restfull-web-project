import config.BookstoreConfig;
import config.SecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@Configuration
//@EnableAutoConfiguration
//@EnableGlobalMethodSecurity(
//        prePostEnabled = true,
//        securedEnabled = true,
//        jsr250Enabled = true)
@Import({BookstoreConfig.class, SecurityConfiguration.class})
//@ComponentScan({"restcontroller",
////        "repository.security",
//        "service.security"})
@EnableJpaRepositories ("repository.security")
@EntityScan("entity.security")
//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@SpringBootApplication(scanBasePackages = {"restcontroller", "service.security"})
public class BookstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }
}
