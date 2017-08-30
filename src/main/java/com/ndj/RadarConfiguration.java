package com.ndj;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * Created by don on 28/08/2017.
 */
@Configuration
public class RadarConfiguration
{

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("com.towerswatson.rto.dpo.services._2010._01");
        return marshaller;
    }

    @Bean
    public RadarClient quoteClient(Jaxb2Marshaller marshaller) {
        RadarClient client = new RadarClient();
        client.setDefaultUri("http://esu-rl-smf1.northeurope.cloudapp.azure.com:3760/DpoService.svc");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
