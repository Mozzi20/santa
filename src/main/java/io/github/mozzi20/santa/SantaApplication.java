package io.github.mozzi20.santa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerExceptionResolver;

import io.sentry.Sentry;
import io.sentry.spring.SentryExceptionResolver;

@SpringBootApplication
public class SantaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SantaApplication.class, args);
	}
	
	@Bean
	public HandlerExceptionResolver sentryExceptionResolver() {
		Sentry.init("https://91a9ca21c5e740b29fff2653cf31d5b6@sentry.io/1805760");
	    return new SentryExceptionResolver();
	}

}
