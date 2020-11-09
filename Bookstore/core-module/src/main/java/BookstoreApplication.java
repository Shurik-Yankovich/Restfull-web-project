import config.BookstoreConfig;
import config.SecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Import({BookstoreConfig.class, SecurityConfiguration.class})
@EnableJpaRepositories ("repository.security")
@EntityScan("entity.security")
@SpringBootApplication(scanBasePackages = {"restcontroller", "service.security", "util.security", "filter"})
public class BookstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }
}
ь