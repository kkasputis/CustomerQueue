package cq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CustomerQueueApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerQueueApplication.class, args);
	}

}
