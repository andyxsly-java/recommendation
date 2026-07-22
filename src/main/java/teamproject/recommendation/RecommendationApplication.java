package teamproject.recommendation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecommendationApplication {

	public static void main(String[] args) {

		System.out.println("Working directory = " + System.getProperty("user.dir"));

		SpringApplication.run(RecommendationApplication.class, args);

	}
}
