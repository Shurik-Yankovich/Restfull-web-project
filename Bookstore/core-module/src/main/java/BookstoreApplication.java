import config.BookstoreConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableAutoConfiguration
//@PropertySource("application.properties")
@Import({BookstoreConfig.class })
@ComponentScan("restcontroller")
@SpringBootApplication
//@Import({BookstoreConfig.class })
//@ComponentScan({"org.bookstore"})
public class BookstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }
}
