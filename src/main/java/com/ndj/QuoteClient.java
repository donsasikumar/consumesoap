package com.ndj;


import net.webservicex.GetGeoIP;
import net.webservicex.GetGeoIPResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

/**
 * Created by don on 28/08/2017.
 */
public class QuoteClient extends WebServiceGatewaySupport {
    public GetGeoIPResponse getQuote(String ticker){
        GetGeoIP request = new GetGeoIP();
        request.setIPAddress("127.0.0.1");
        System.out.println("Requesting IP..");
        GetGeoIPResponse response = (GetGeoIPResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://www.webservicex.net/geoipservice.asmx",
                                        request,
                                        new SoapActionCallback("http://www.webservicex.net/GetGeoIP"));
        return response;
    }
}
