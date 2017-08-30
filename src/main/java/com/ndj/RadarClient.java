package com.ndj;


import com.towerswatson.rto.dpo.services._2010._01.PofRequest;
import com.towerswatson.rto.dpo.services._2010._01.PofResponse;
import com.towerswatson.rto.smf.types._2010._01.ObjectFactory;
import com.towerswatson.rto.smf.types._2010._01.PofrInformationCollectionDataContract;
import com.towerswatson.rto.smf.types._2010._01.PofrInformationDataContract;
import org.joda.time.DateTime;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by don on 28/08/2017.
 */
public class RadarClient extends WebServiceGatewaySupport {

    public PofResponse getQuote() throws DatatypeConfigurationException
    {
        PofRequest request = new PofRequest();
        List<PofrInformationDataContract> listofPofrDataContracts = new ArrayList<>();

        PofrInformationCollectionDataContract collectionDataContract = new PofrInformationCollectionDataContract();
        JAXBElement<PofrInformationCollectionDataContract> element = null;
        XMLGregorianCalendar xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(new DateTime().toGregorianCalendar());
        request.setPofrCollection(element);
        request.setTime(xgc);
        System.out.println("Requesting Price..");
        PofResponse response = (PofResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://esu-rl-smf1.northeurope.cloudapp.azure.com:3760/DpoService.svc",
                                        request,
                                        new SoapActionCallback("http://esu-rl-smf1.northeurope.cloudapp.azure.com:3760/GetPof"));
        return response;
    }

    public PofResponse getPrice() throws DatatypeConfigurationException
    {
        long startTime = System.currentTimeMillis();
        ObjectFactory objFactory = new ObjectFactory();
        PofrInformationCollectionDataContract value = new PofrInformationCollectionDataContract();
        PofrInformationDataContract pofrInfoDataContact = new PofrInformationDataContract();
        String xmlString = "<root><Quote><HaveBoughtCar>true</HaveBoughtCar></Quote></root>";
        pofrInfoDataContact.setPofr(xmlString);
        value.getPofrInformationDataContract().add(pofrInfoDataContact);
        XMLGregorianCalendar xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(new DateTime().toGregorianCalendar());
        JAXBElement<PofrInformationCollectionDataContract> element = new JAXBElement<PofrInformationCollectionDataContract>(){}
        PofRequest request = new PofRequest();
        request.setPofrCollection(element);
        request.setTime(xgc);
        //JAXBElement<PofrInformationCollectionDataContract> marshalledRequest = objFactory.createPofRequestPofrCollection(value);
        //getWebServiceTemplate().marshalSendAndReceive(derivedArgumentsApiConfig.getDerivedArgumentsServiceUrl(), marshalledRequest);
        //LOGGER.info("Persist Derived Arguments took " + (System.currentTimeMillis() - startTime) + " ms");
    }
}
