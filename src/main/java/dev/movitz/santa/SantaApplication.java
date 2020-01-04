package dev.movitz.santa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
	@ConditionalOnProperty("sentry.dsn")
	public HandlerExceptionResolver sentryExceptionResolver(@Value("${sentry.dsn}") String dsn) {
		Sentry.init(dsn);
	    return new SentryExceptionResolver();
	}

}
