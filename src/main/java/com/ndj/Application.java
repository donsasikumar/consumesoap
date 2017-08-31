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
    CommandLineRunner lookup(RadarClient radarClient) {
        return args -> {

            PofResponse response = radarClient.getQuote();
            System.err.println("PofResponse - getErrorCode().getName()               -> "+response.getErrorCode().getName());
            System.err.println("PofResponse - response.getErrorCode().getValue()     ->"+response.getErrorCode().getValue());
            System.err.println("PofResponse - response.getErrorMessage().getValue()  ->"+response.getErrorMessage().getValue());
            System.err.println("PofResponse - response.getErrorMessage().getValue()  ->"+response.getErrorMessage().getValue());
            System.err.println("PofResponse - response.getPofCollection().getValue() ->"+response.getPofCollection().getValue());
            System.err.println("PofResponse - response.getPofCollection().getName()  ->"+response.getPofCollection().getName());
        };
    }
}
