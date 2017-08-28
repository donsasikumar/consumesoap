package com.ndj;


import hello.wsdl.GetGeoIPResponse;
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
            String ticker = "UK";

            if (args.length > 0) {
                ticker = args[0];
            }
            GetGeoIPResponse response = quoteClient.getQuote(ticker);
            System.err.println(response.getGetGeoIPResult().getCountryName());
            System.err.println(response.getGetGeoIPResult().getReturnCode());
            System.err.println(response.getGetGeoIPResult().getReturnCodeDetails());
        };
    }
}
