package waterfall.flowfall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FlowfallApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowfallApplication.class, args);
	}

}
