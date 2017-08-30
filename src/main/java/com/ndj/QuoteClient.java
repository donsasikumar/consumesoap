package com.ndj;


import com.towerswatson.rto.dpo.services._2010._01.PofRequest;
import com.towerswatson.rto.dpo.services._2010._01.PofResponse;
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
public class QuoteClient extends WebServiceGatewaySupport {

    public PofResponse getQuote(String ticker) throws DatatypeConfigurationException
    {
        PofRequest request = new PofRequest();
        List<PofrInformationDataContract> listofPofrDataContracts = new ArrayList<>();
        PofrInformationCollectionDataContract collectionDataContract = new PofrInformationCollectionDataContract();
        JAXBElement<PofrInformationCollectionDataContract> element = null;
        request.setPofrCollection(element);
        XMLGregorianCalendar xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(new DateTime().toGregorianCalendar());
        request.setTime(xgc);
        System.out.println("Requesting Price..");
        PofResponse response = (PofResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://esu-rl-smf1.northeurope.cloudapp.azure.com:3760/DpoService.svc",
                                        request,
                                        new SoapActionCallback("http://esu-rl-smf1.northeurope.cloudapp.azure.com:3760/GetPof"));
        return response;
    }
}
