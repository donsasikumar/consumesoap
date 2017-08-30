package com.ndj;


import com.towerswatson.rto.dpo.services._2010._01.PofResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by don on 28/08/2017.
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    CommandLineRunner lookup(QuoteClient quoteClient) {
        return args -> {
            PofResponse response = new PofResponse();
            System.err.println(response.getPofCollection());
            System.err.println(response.getErrorMessage());
            System.err.println(response.getErrorCode());
        };
    }
}
