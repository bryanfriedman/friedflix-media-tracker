package friedflix.mediatracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class FriedflixMediaTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FriedflixMediaTrackerApplication.class, args);
	}
	
	
}