package com.ndj;


import com.towerswatson.rto.dpo.services._2010._01.ObjectFactory;
import com.towerswatson.rto.dpo.services._2010._01.PofRequest;
import com.towerswatson.rto.dpo.services._2010._01.PofResponse;
import com.towerswatson.rto.smf.types._2010._01.PofrInformationCollectionDataContract;
import com.towerswatson.rto.smf.types._2010._01.PofrInformationDataContract;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;

/**
 * Created by don on 28/08/2017.
 */
public class RadarClient extends WebServiceGatewaySupport {
    public PofResponse getQuote() throws SOAPException
    {
        PofRequest pofRequest = new PofRequest();
        System.out.println("Requesting Price..");

        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        MessageFactory msgFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
        SaajSoapMessageFactory newSoapMessageFactory = new SaajSoapMessageFactory(msgFactory);
        webServiceTemplate.setMessageFactory(newSoapMessageFactory);
        Jaxb2Marshaller marshaller = new QuoteConfiguration().marshaller();
        webServiceTemplate.setMarshaller(marshaller);
        webServiceTemplate.setUnmarshaller(marshaller);

        ObjectFactory objFactory = new ObjectFactory();
        PofrInformationCollectionDataContract value = new PofrInformationCollectionDataContract();
        PofrInformationDataContract pofrInfoDataContact = new PofrInformationDataContract();
        String xmlString = getRootSnippet();
        pofrInfoDataContact.setPofr(xmlString);
        value.getPofrInformationDataContract().add(pofrInfoDataContact);
        JAXBElement<PofrInformationCollectionDataContract> marshalledRequest = objFactory.createPofRequestPofrCollection(value);
        pofRequest.setPofrCollection(marshalledRequest);

        SoapActionCallback requestCallback = new SoapActionCallback("http://towerswatson.com/rto/dpo/services/2010/01/DpoServiceContract/GetPof") {
            public void doWithMessage(WebServiceMessage message) {
                SaajSoapMessage soapMessage = (SaajSoapMessage) message;
                SoapHeader soapHeader = soapMessage.getSoapHeader();
                QName wsaToQName = new QName("http://www.w3.org/2005/08/addressing", "To", "wsa");
                SoapHeaderElement wsaTo =  soapHeader.addHeaderElement(wsaToQName);
                wsaTo.setText("http://esu-rl-smf1.northeurope.cloudapp.azure.com:3760/DpoService.svc");

                QName wsaActionQName = new QName("http://www.w3.org/2005/08/addressing", "Action", "wsa");
                SoapHeaderElement wsaAction =  soapHeader.addHeaderElement(wsaActionQName);
                wsaAction.setText("http://towerswatson.com/rto/dpo/services/2010/01/DpoServiceContract/GetPof");
            }
        };


        PofResponse pofResponse = (PofResponse) webServiceTemplate
                .marshalSendAndReceive("http://esu-rl-smf1.northeurope.cloudapp.azure.com:3760/DpoService.svc",
                        pofRequest,
                        requestCallback);
        return pofResponse;
    }

    String getRootSnippet(){
        return "<root>\n" + "<Policy>\n" + "    <!-- This is intended as the Primary Key -->\n"
                + "    <PolicyReference value=\"I1589205361\"/>\n" + "    <!-- Optional -->\n"
                + "    <IbisQuoteReference value=\"1589205361\"/>\n" + "    <!-- Optional -->\n"
                + "    <TiaAgreementLineSeqNumber value=\"928340162874\"/>\n" + "    <!-- Optional -->\n"
                + "    <TiaPolicyNumber value=\"1000267\"/>\n"
                + "    <!-- Not sure rateSetId will be requied by Radar. For NB/Renewals this will 0 -->\n"
                + "    <RateSetID value=\"10235\"/>\n"
                + "    <!-- This will be included in the outer XML message, not sure it will be required by RADAR as it will be known -->\n"
                + "    <RadarMasterSetId value=\"06636ea7-b12c-4afa-b64e-7e6fd263f783\"/>\n"
                + "    <LineId value=\"ESHOM\"/>\n" + "    <UwCode value=\"ES\"/>\n"
                + "    <ChannelRatingFactor value=\"Y\"/>\n" + "    <ChannelCode value=\"W\"/>\n"
                + "    <!-- Optional -->\n" + "    <SchemeCode value=\"D007\"/>\n"
                + "    <TransactionCode value=\"10\"/>\n" + "    <PolicyYear value=\"1\"/>\n"
                + "    <StartDate value=\"2017-06-30\"/>\n" + "    <EffectiveDate value=\"2017-06-30\"/>\n"
                + "    <!-- Optional -->\n" + "    <AggregatorId value=\"MSM\"/>\n" + "    <ClaimScore value=\"0\"/>\n"
                + "    <PreferredPayMethod value=\"99\"/>\n" + "    <CurrentPriceOptimisationFactor value=\"0.99\"/>\n"
                + "    <!-- Optional -->\n" + "    <PreviousPriceOptimisationFactor value=\"1.01\"/>\n"
                + "    <PriceTestLevel value=\"50\"/>\n" + "    <RadarPriceTestLevel value=\"50\"/>\n"
                + "    <YearsWithPreviousInsurer value=\"1\"/>\n" + "    <NumberOfAdults value=\"1\"/>\n"
                + "    <NumberOfChildren value=\"3\"/>\n" + "    <ProtectNcd value=\"false\"/>\n"
                + "    <AddonYearTvl value=\"1\"/>\n" + "    <AddonYearWin value=\"1\"/>\n"
                + "    <!-- First Start Date and First Quote Date are used to calculate the Quote to Inception attributes. -->\n"
                + "    <!-- Need to confirm if we need to provide all information below -->\n"
                + "    <FirstQuoteDate value=\"2017-06-30\"/>\n" + "    <FirstStartDate value=\"2017-06-30\"/>\n"
                + "    <QuoteToInceptionHours value=\"1\"/>\n" + "    <QuoteToInceptionCode value=\"001H\"/>\n"
                + "    <!-- Indicator List -->\n" + "    <BuildingsIndicator value=\"false\"/>\n"
                + "    <ContentsIndicator value=\"false\"/>\n" + "    <PersonalPossessionsIndicator value=\"false\"/>\n"
                + "    <SpecifiedItemsIndicator value=\"false\"/>\n"
                + "    <FamilyLegalProtectionIndicator value=\"false\"/>\n"
                + "    <HomeAssistIndicator value=\"false\"/>\n" + "    <PestIndicator value=\"false\"/>\n"
                + "    <TravelIndicator value=\"false\"/>\n" + "    <WinterSportsIndicator value=\"false\"/>\n"
                + "    <!-- Previous Prices List -->\n" + "    <BuildingsLastInceptedPrice value=\"123.45\"/>\n"
                + "    <BuildingsLatestPrice value=\"123.45\"/>\n"
                + "    <ContentsLastInceptedPrice value=\"65.43\"/>\n" + "    <ContentsLatestPrice value=\"98.76\"/>\n"
                + "    <PersonalPossessionsLastInceptedPrice value=\"34.56\"/>\n"
                + "    <PersonalPossessionsLatestPrice value=\"45.67\"/>\n"
                + "    <SpecifiedItemsLastInceptedPrice value=\"0.00\"/>\n"
                + "    <SpecifiedItemsLatestPrice value=\"0.00\"/>\n"
                + "    <FamilyLegalProtectionLastInceptedPrice value=\"29.99\"/>\n"
                + "    <FamilyLegalProtectionLatestPrice value=\"29.99\"/>\n"
                + "    <HomeEmergencyLastInceptedPrice value=\"19.99\"/>\n"
                + "    <HomeEmergencyLatestPrice value=\"19.99\"/>\n" + "    <PestLastInceptedPrice value=\"24.99\"/>\n"
                + "    <PestLatestPrice value=\"24.99\"/>\n" + "    <TravelLastInceptedPrice value=\"66.99\"/>\n"
                + "    <TravelLatestPrice value=\"75.45\"/>\n"
                + "    <WinterSportsLastInceptedPrice value=\"123.45\"/>\n"
                + "    <WinterSportsLatestPrice value=\"123.45\"/>\n"
                + "    <LastInceptedPriceTotal value=\"123.45\"/>\n" + "    <LatestPriceTotal value=\"123.45\"/>\n"
                + "    <!-- Optional -->\n" + "    <BuildingCover>\n" + "        <AccidentalDamage value=\"false\"/>\n"
                + "        <SumsInsured value=\"124600\"/>\n" + "        <UwDiscount value=\"1.0\"/>\n"
                + "        <VoluntaryExcessCode value=\"100\"/>\n" + "    </BuildingCover>\n"
                + "    <!-- Optional -->\n" + "    <ContentCover>\n" + "        <AccidentalDamage value=\"false\"/>\n"
                + "        <HouseholdGoodsSumsInsured value=\"14500\"/>\n"
                + "        <HighRiskItemsSumsInsured value=\"7300\"/>\n"
                + "        <PersonalPossessionsSumsInsured value=\"0\"/>\n" + "        <UwDiscount value=\"1.0\"/>\n"
                + "        <VoluntaryExcessCode value=\"50\"/>\n" + "        <!-- Optional -->\n"
                + "        <SpecifiedItems>\n" + "            <SpecifiedItem>\n"
                + "                <TypeCode value=\"ANT\"/>\n" + "                <SumInsuredAmount value=\"1500\"/>\n"
                + "            </SpecifiedItem>\n" + "            <SpecifiedItem>\n"
                + "                <TypeCode value=\"BNK\"/>\n" + "                <SumInsuredAmount value=\"1500\"/>\n"
                + "            </SpecifiedItem>\n" + "            <SpecifiedItem>\n"
                + "                <TypeCode value=\"CTL\"/>\n" + "                <SumInsuredAmount value=\"1501\"/>\n"
                + "            </SpecifiedItem>\n" + "            <SpecifiedItem>\n"
                + "                <TypeCode value=\"CYC\"/>\n" + "                <SumInsuredAmount value=\"1502\"/>\n"
                + "            </SpecifiedItem>\n" + "            <SpecifiedItem>\n"
                + "                <TypeCode value=\"FUR\"/>\n" + "                <SumInsuredAmount value=\"1503\"/>\n"
                + "            </SpecifiedItem>\n" + "            <SpecifiedItem>\n"
                + "                <TypeCode value=\"JWL\"/>\n" + "                <SumInsuredAmount value=\"1504\"/>\n"
                + "            </SpecifiedItem>\n" + "            <SpecifiedItem>\n"
                + "                <TypeCode value=\"PNT\"/>\n" + "                <SumInsuredAmount value=\"1505\"/>\n"
                + "            </SpecifiedItem>\n" + "            <SpecifiedItem>\n"
                + "                <TypeCode value=\"OTH\"/>\n" + "                <SumInsuredAmount value=\"1506\"/>\n"
                + "            </SpecifiedItem>\n" + "        </SpecifiedItems>\n" + "    </ContentCover>\n"
                + "    <Insured>\n" + "        <Age value=\"37\"/>\n" + "        <Gender value=\"F\"/>\n"
                + "        <MaritalStatusCode value=\"1\"/>\n" + "        <NcdYears value=\"7\"/>\n"
                + "        <OccupationCode value=\"*10\"/>\n" + "        <OccupationSegmentCode value=\"15\"/>\n"
                + "        <DelphiBlock>\n" + "            <BespokeArray value=\"[]\"/>\n"
                + "            <E1A01 value=\"0\"/>\n" + "            <E1A02 value=\"0\"/>\n"
                + "            <E1A03 value=\"0\"/>\n" + "            <E1A04 value=\"0\"/>\n"
                + "            <E1A05 value=\"0\"/>\n" + "            <E1A06 value=\"0\"/>\n"
                + "            <E1A07 value=\"0\"/>\n" + "            <E1A08 value=\"0\"/>\n"
                + "            <E1A09 value=\"0\"/>\n" + "            <E1A10 value=\"0\"/>\n"
                + "            <E1A11 value=\"0\"/>\n" + "            <E1B01 value=\"0\"/>\n"
                + "            <E1B02 value=\"0\"/>\n" + "            <E1B03 value=\"N\"/>\n"
                + "            <E1B04 value=\"0\"/>\n" + "            <E1B05 value=\"N\"/>\n"
                + "            <E1B06 value=\"0\"/>\n" + "            <E1B07 value=\"N\"/>\n"
                + "            <E1B08 value=\"N\"/>\n" + "            <E1B09 value=\"4\"/>\n"
                + "            <E1B10 value=\"0\"/>\n" + "            <E1B11 value=\"0\"/>\n"
                + "            <E1B12 value=\"0\"/>\n" + "            <E1B13 value=\"0\"/>\n"
                + "            <E1C01 value=\"N\"/>\n" + "            <E1C02 value=\"0\"/>\n"
                + "            <E1C03 value=\"0\"/>\n" + "            <E1C04 value=\"0\"/>\n"
                + "            <E1C05 value=\"0\"/>\n" + "            <E1C06 value=\"0\"/>\n"
                + "            <E1D01 value=\"0\"/>\n" + "            <E1D02 value=\"0\"/>\n"
                + "            <E1D03 value=\"0\"/>\n" + "            <E1D04 value=\"0\"/>\n"
                + "            <E1E01 value=\"0\"/>\n" + "            <E1E02 value=\"0\"/>\n"
                + "            <E2G01 value=\"0\"/>\n" + "            <E2G02 value=\"0\"/>\n"
                + "            <E2G03 value=\"0\"/>\n" + "            <E2G04 value=\"0\"/>\n"
                + "            <E2G05 value=\"0\"/>\n" + "            <E2G06 value=\"0\"/>\n"
                + "            <E2G07 value=\"0\"/>\n" + "            <E2G08 value=\"0\"/>\n"
                + "            <E2G09 value=\"0\"/>\n" + "            <E2G10 value=\"0\"/>\n"
                + "            <E2G11 value=\"0\"/>\n" + "            <E2H01 value=\"0\"/>\n"
                + "            <E2H02 value=\"0\"/>\n" + "            <E2H03 value=\"N\"/>\n"
                + "            <E2H04 value=\"0\"/>\n" + "            <E2H05 value=\"N\"/>\n"
                + "            <E2H06 value=\"0\"/>\n" + "            <E2H07 value=\"N\"/>\n"
                + "            <E2H08 value=\"N\"/>\n" + "            <E2H09 value=\"0\"/>\n"
                + "            <E2H10 value=\"0\"/>\n" + "            <E2H11 value=\"0\"/>\n"
                + "            <E2H12 value=\"0\"/>\n" + "            <E2H13 value=\"0\"/>\n"
                + "            <E2I01 value=\"N\"/>\n" + "            <E2I02 value=\"0\"/>\n"
                + "            <E2I03 value=\"0\"/>\n" + "            <E2I04 value=\"0\"/>\n"
                + "            <E2I05 value=\"0\"/>\n" + "            <E2I06 value=\"0\"/>\n"
                + "            <E2J01 value=\"0\"/>\n" + "            <E2J02 value=\"0\"/>\n"
                + "            <E2J03 value=\"0\"/>\n" + "            <E2J04 value=\"0\"/>\n"
                + "            <E2K01 value=\"0\"/>\n" + "            <E2K02 value=\"0\"/>\n"
                + "            <E4Q01 value=\"1\"/>\n" + "            <E4Q02 value=\"1\"/>\n"
                + "            <E4Q03 value=\"1\"/>\n" + "            <E4Q04 value=\"1\"/>\n"
                + "            <E4Q05 value=\"5\"/>\n" + "            <E4Q06 value=\"5\"/>\n"
                + "            <E4Q07 value=\"0\"/>\n" + "            <E4Q08 value=\"0\"/>\n"
                + "            <E4Q09 value=\"5\"/>\n" + "            <E4Q10 value=\"5\"/>\n"
                + "            <E4Q11 value=\"0\"/>\n" + "            <E4Q12 value=\"0\"/>\n"
                + "            <E4Q13 value=\"5\"/>\n" + "            <E4Q14 value=\"5\"/>\n"
                + "            <E4Q15 value=\"0\"/>\n" + "            <E4Q16 value=\"0\"/>\n"
                + "            <E4Q17 value=\"2\"/>\n" + "            <E4Q18 value=\"1\"/>\n"
                + "            <E4R01 value=\"1\"/>\n" + "            <E4R02 value=\"BR13TG\"/>\n"
                + "            <E4R03 value=\"1\"/>\n" + "            <E5S01 value=\"1\"/>\n"
                + "            <E5S02 value=\"1\"/>\n" + "            <E5S041 value=\"90\"/>\n"
                + "            <E5S042 value=\"0\"/>\n" + "            <E5S043 value=\"0\"/>\n"
                + "            <E5S051 value=\"951\"/>\n" + "            <E5S052 value=\"792\"/>\n"
                + "            <E5S053 value=\"954\"/>\n" + "            <EA1A01 value=\"N\"/>\n"
                + "            <EA1B01 value=\"0\"/>\n" + "            <EA1B02 value=\"0\"/>\n"
                + "            <EA1C01 value=\"N\"/>\n" + "            <EA1C02 value=\"N\"/>\n"
                + "            <EA1D01 value=\"1\"/>\n" + "            <EA1D02 value=\"0\"/>\n"
                + "            <EA1D03 value=\"0\"/>\n" + "            <EA1E01 value=\"0\"/>\n"
                + "            <EA1E02 value=\"0\"/>\n" + "            <EA1E03 value=\"0\"/>\n"
                + "            <EA1E04 value=\"0\"/>\n" + "            <EA1F01 value=\"N\"/>\n"
                + "            <EA1F02 value=\"N\"/>\n" + "            <EA1F03 value=\"N\"/>\n"
                + "            <EA1F04 value=\"1\"/>\n" + "            <EA1G01 value=\"N\"/>\n"
                + "            <EA1G02 value=\"N\"/>\n" + "            <EA2G01 value=\"N\"/>\n"
                + "            <EA2H01 value=\"0\"/>\n" + "            <EA2H02 value=\"0\"/>\n"
                + "            <EA2I01 value=\"N\"/>\n" + "            <EA2I02 value=\"N\"/>\n"
                + "            <EA2I04 value=\"1\"/>\n" + "            <EA2J01 value=\"0\"/>\n"
                + "            <EA2J02 value=\"0\"/>\n" + "            <EA2J03 value=\"0\"/>\n"
                + "            <EA2K01 value=\"0\"/>\n" + "            <EA2K02 value=\"0\"/>\n"
                + "            <EA2K03 value=\"0\"/>\n" + "            <EA2K04 value=\"0\"/>\n"
                + "            <EA2L01 value=\"1\"/>\n" + "            <EA2L02 value=\"N\"/>\n"
                + "            <EA2L03 value=\"N\"/>\n" + "            <EA2M01 value=\"N\"/>\n"
                + "            <EA2M02 value=\"1\"/>\n" + "            <EA2Q01 value=\"3\"/>\n"
                + "            <EA2Q02 value=\"0\"/>\n" + "            <EA4M01 value=\"9047\"/>\n"
                + "            <EA4M02 value=\"476\"/>\n" + "            <EA4M03 value=\"0\"/>\n"
                + "            <EA4M04 value=\"100\"/>\n" + "            <EA4M05 value=\"2075\"/>\n"
                + "            <EA4M06 value=\"669\"/>\n" + "            <EA4M07 value=\"31\"/>\n"
                + "            <EA4M08 value=\"0\"/>\n" + "            <EA4M09 value=\"0\"/>\n"
                + "            <EA4M10 value=\"0\"/>\n" + "            <EA4M11 value=\"0\"/>\n"
                + "            <EA4M12 value=\"0\"/>\n" + "            <EA4N01 value=\"3408\"/>\n"
                + "            <EA4N02 value=\"1875\"/>\n" + "            <EA4N03 value=\"1623\"/>\n"
                + "            <EA4N04 value=\"-255\"/>\n" + "            <EA4N05 value=\"3923\"/>\n"
                + "            <EA4P01 value=\"N\"/>\n" + "            <EA4Q01 value=\"N\"/>\n"
                + "            <EA4Q02 value=\"N\"/>\n" + "            <EA4Q03 value=\"N\"/>\n"
                + "            <EA4Q04 value=\"N\"/>\n" + "            <EA4Q05 value=\"N\"/>\n"
                + "            <EA4Q06 value=\"N\"/>\n" + "            <EA4Q07 value=\"N\"/>\n"
                + "            <EA4Q08 value=\"N\"/>\n" + "            <EA4Q09 value=\"N\"/>\n"
                + "            <EA4Q10 value=\"N\"/>\n" + "            <EA4Q11 value=\"N\"/>\n"
                + "            <EA4R01CJ value=\"0\"/>\n" + "            <EA4R01PJ value=\"0\"/>\n"
                + "            <EA4R01PM value=\"0\"/>\n" + "            <EA4S01 value=\"0\"/>\n"
                + "            <EA4S02 value=\"1\"/>\n" + "            <EA4S03 value=\"1\"/>\n"
                + "            <EA4S04 value=\"05/12/88\"/>\n" + "            <EA4S05 value=\"0\"/>\n"
                + "            <EA4S06 value=\"1\"/>\n" + "            <EA4S07 value=\"1\"/>\n"
                + "            <EA4S08 value=\"05/12/88\"/>\n" + "            <EA4T01 value=\"3\"/>\n"
                + "            <EA5S01 value=\"40\"/>\n" + "            <EA5T01 value=\"0\"/>\n"
                + "            <EA5T02 value=\"12\"/>\n" + "            <EA5U01 value=\"2\"/>\n"
                + "            <EA5U02 value=\"0\"/>\n" + "            <HHOScore value=\"1\"/>\n"
                + "            <NDDIRSP value=\"N\"/>\n" + "            <NDDIRSPA value=\"N\"/>\n"
                + "            <NDDOB value=\"17/12/72\"/>\n" + "            <NDECC01 value=\"0\"/>\n"
                + "            <NDECC02 value=\"0\"/>\n" + "            <NDECC03 value=\"0\"/>\n"
                + "            <NDECC04 value=\"0\"/>\n" + "            <NDECC05 value=\"0\"/>\n"
                + "            <NDECC06 value=\"0\"/>\n" + "            <NDECC07 value=\"0\"/>\n"
                + "            <NDECC08 value=\"0\"/>\n" + "            <NDECC09 value=\"0\"/>\n"
                + "            <NDECC10 value=\"0\"/>\n" + "            <NDERL01 value=\"0\"/>\n"
                + "            <NDERL02 value=\"0\"/>\n" + "            <NDERLJACA value=\"1\"/>\n"
                + "            <NDERLJAPA value=\"1\"/>\n" + "            <NDERLMACA value=\"C\"/>\n"
                + "            <NDERLMAPA value=\"1\"/>\n" + "            <NDFCS01 value=\"1\"/>\n"
                + "            <NDFCS02 value=\"1\"/>\n" + "            <NDG01 value=\"5\"/>\n"
                + "            <NDG02 value=\"808\"/>\n" + "            <NDG03 value=\"19\"/>\n"
                + "            <NDG04 value=\"476\"/>\n" + "            <NDG05 value=\"0\"/>\n"
                + "            <NDG06 value=\"0\"/>\n" + "            <NDG07 value=\"0\"/>\n"
                + "            <NDG08 value=\"0\"/>\n" + "            <NDG09 value=\"0\"/>\n"
                + "            <NDG10 value=\"0\"/>\n" + "            <NDG11 value=\"0\"/>\n"
                + "            <NDG12 value=\"0\"/>\n" + "            <NDHAC01 value=\"0\"/>\n"
                + "            <NDHAC02 value=\"0\"/>\n" + "            <NDHAC03 value=\"0\"/>\n"
                + "            <NDHAC04 value=\"0\"/>\n" + "            <NDHAC05 value=\"0\"/>\n"
                + "            <NDHAC06 value=\"0\"/>\n" + "            <NDHAC07 value=\"0\"/>\n"
                + "            <NDHAC08 value=\"0\"/>\n" + "            <NDHAC09 value=\"0\"/>\n"
                + "            <NDHAC10 value=\"0\"/>\n" + "            <NDHHO value=\"N\"/>\n"
                + "            <NDHHOSCORE value=\"-999\"/>\n" + "            <NDINC01 value=\"1\"/>\n"
                + "            <NDINC02 value=\"0\"/>\n" + "            <NDINC03 value=\"0\"/>\n"
                + "            <NDLNK01 value=\"2\"/>\n" + "            <NDOPTOUT value=\"N\"/>\n"
                + "            <NDOPTOUTVALID value=\"1\"/>\n" + "            <NDPA value=\"1\"/>\n"
                + "            <NDPSD01 value=\"0\"/>\n" + "            <NDPSD02 value=\"0\"/>\n"
                + "            <NDPSD03 value=\"0\"/>\n" + "            <NDPSD04 value=\"0\"/>\n"
                + "            <NDPSD05 value=\"0\"/>\n" + "            <NDPSD06 value=\"0\"/>\n"
                + "            <NDPSD07 value=\"0\"/>\n" + "            <NDPSD08 value=\"0\"/>\n"
                + "            <NDPSD09 value=\"0\"/>\n" + "            <NDPSD10 value=\"0\"/>\n"
                + "            <NDPSD11 value=\"0\"/>\n" + "            <NDSI21 value=\"90CON\"/>\n"
                + "            <NDSI22 value=\"90CON\"/>\n" + "            <NDSI23 value=\"1\"/>\n"
                + "            <NDSPACII value=\"-2\"/>\n" + "            <NDSPCII value=\"-9\"/>\n"
                + "            <NDVALSCORE value=\"0\"/>\n" + "            <SPA01 value=\"N\"/>\n"
                + "            <SPA02 value=\"N\"/>\n" + "            <SPA03 value=\"N\"/>\n"
                + "            <SPA04 value=\"N\"/>\n" + "            <SPA05 value=\"N\"/>\n"
                + "            <SPA06 value=\"N\"/>\n" + "            <SPA07 value=\"N\"/>\n"
                + "            <SPA08 value=\"N\"/>\n" + "            <SPA09 value=\"N\"/>\n"
                + "            <SPA10 value=\"N\"/>\n" + "            <SPAA01 value=\"N\"/>\n"
                + "            <SPAA02 value=\"N\"/>\n" + "            <SPAA03 value=\"N\"/>\n"
                + "            <SPAA04 value=\"N\"/>\n" + "            <SPAA05 value=\"N\"/>\n"
                + "            <SPAA06 value=\"N\"/>\n" + "            <SPAA07 value=\"N\"/>\n"
                + "            <SPAA08 value=\"N\"/>\n" + "            <SPAA09 value=\"N\"/>\n"
                + "            <SPAA10 value=\"N\"/>\n" + "            <SPAB111 value=\"N\"/>\n"
                + "            <SPAB112 value=\"N\"/>\n" + "            <SPAB113 value=\"N\"/>\n"
                + "            <SPAB114 value=\"N\"/>\n" + "            <SPAB115 value=\"N\"/>\n"
                + "            <SPAB116 value=\"N\"/>\n" + "            <SPAB117 value=\"N\"/>\n"
                + "            <SPAB218 value=\"N\"/>\n" + "            <SPAB219 value=\"N\"/>\n"
                + "            <SPAB220 value=\"N\"/>\n" + "            <SPAB221 value=\"N\"/>\n"
                + "            <SPAB322 value=\"N\"/>\n" + "            <SPAB323 value=\"N\"/>\n"
                + "            <SPABRPRESENT value=\"N\"/>\n" + "            <SPAC24 value=\"N\"/>\n"
                + "            <SPACIICHECKDIGIT value=\"N\"/>\n" + "            <SPAD25 value=\"N\"/>\n"
                + "            <SPAE126 value=\"N\"/>\n" + "            <SPAE127 value=\"N\"/>\n"
                + "            <SPAE128 value=\"N\"/>\n" + "            <SPAF129 value=\"N\"/>\n"
                + "            <SPAF130 value=\"N\"/>\n" + "            <SPAF131 value=\"N\"/>\n"
                + "            <SPAF232 value=\"N\"/>\n" + "            <SPAF233 value=\"N\"/>\n"
                + "            <SPAF334 value=\"N\"/>\n" + "            <SPAF335 value=\"N\"/>\n"
                + "            <SPAF336 value=\"N\"/>\n" + "            <SPAG37 value=\"N\"/>\n"
                + "            <SPAG38 value=\"N\"/>\n" + "            <SPAH39 value=\"N\"/>\n"
                + "            <SPAH40 value=\"N\"/>\n" + "            <SPAH41 value=\"N\"/>\n"
                + "            <SPB111 value=\"N\"/>\n" + "            <SPB112 value=\"N\"/>\n"
                + "            <SPB113 value=\"N\"/>\n" + "            <SPB114 value=\"N\"/>\n"
                + "            <SPB115 value=\"N\"/>\n" + "            <SPB116 value=\"N\"/>\n"
                + "            <SPB117 value=\"N\"/>\n" + "            <SPB218 value=\"N\"/>\n"
                + "            <SPB219 value=\"N\"/>\n" + "            <SPB220 value=\"N\"/>\n"
                + "            <SPB221 value=\"N\"/>\n" + "            <SPB322 value=\"N\"/>\n"
                + "            <SPB323 value=\"N\"/>\n" + "            <SPBRPRESENT value=\"N\"/>\n"
                + "            <SPC24 value=\"N\"/>\n" + "            <SPCIICHECKDIGIT value=\"N\"/>\n"
                + "            <SPD25 value=\"N\"/>\n" + "            <SPE126 value=\"N\"/>\n"
                + "            <SPE127 value=\"N\"/>\n" + "            <SPE128 value=\"N\"/>\n"
                + "            <SPF129 value=\"N\"/>\n" + "            <SPF130 value=\"N\"/>\n"
                + "            <SPF131 value=\"N\"/>\n" + "            <SPF232 value=\"N\"/>\n"
                + "            <SPF233 value=\"N\"/>\n" + "            <SPF334 value=\"N\"/>\n"
                + "            <SPF335 value=\"N\"/>\n" + "            <SPF336 value=\"N\"/>\n"
                + "            <SPG37 value=\"73\"/>\n" + "            <SPG38 value=\"54\"/>\n"
                + "            <SPH39 value=\"N\"/>\n" + "            <SPH40 value=\"N\"/>\n"
                + "            <SPH41 value=\"N\"/>\n" + "            <UKMOSAIC value=\"1\"/>\n"
                + "            <DelphiStatus value=\"OK\"/>\n" + "        </DelphiBlock>\n" + "    </Insured>\n"
                + "    <!-- Optional -->\n" + "    <JointProposer>\n" + "        <Age value=\"37\"/>\n"
                + "        <Gender value=\"F\"/>\n" + "        <MaritalStatusCode value=\"1\"/>\n"
                + "        <NcdYears value=\"7\"/>\n" + "        <OccupationCode value=\"*10\"/>\n"
                + "        <OccupationSegmentCode value=\"15\"/>\n" + "    </JointProposer>\n" + "    <Properties>\n"
                + "        <Property>\n" + "            <PropertyId value=\"1\"/>\n"
                + "            <AlarmBurgler value=\"false\"/>\n" + "            <NumberOfBedrooms value=\"3\"/>\n"
                + "            <OwnershipTypeCode value=\"MNL\"/>\n" + "            <PropertyTypeCode value=\"THE\"/>\n"
                + "            <RiskPostCode value=\"BR13TG\"/>\n"
                + "            <RoofConstructionCode value=\"STA\"/>\n"
                + "            <WallConstructionCode value=\"STA\"/>\n"
                + "            <WindowDoorLocks value=\"true\"/>\n"
                + "            <YearOfConstruction value=\"1980\"/>\n" + "            <GeoAttributes>\n"
                + "                <!-- Special Term 1 -->\n" + "                <TheftAreaRating value=\"19\"/>\n"
                + "                <!-- Special Term 2 -->\n" + "                <SubsidenceAreaRating value=\"78\"/>\n"
                + "                <!-- Special Term 3 -->\n" + "                <WealthAreaRating value=\"44\"/>\n"
                + "                <!-- Special Term 4 -->\n" + "                <FloodAreaRating value=\"16\"/>\n"
                + "                <!-- Special Term 5 -->\n" + "                <TvRegion value=\"CAR\"/>\n"
                + "                <!-- Special Term 6 -->\n" + "                <NumberOfBedrooms value=\"3\"/>\n"
                + "                <!-- Special Term 7 -->\n" + "                <YearOfConstruction value=\"1979\"/>\n"
                + "                <!-- Special Term 8 -->\n" + "                <PropertyTypeCode value=\"THE\"/>\n"
                + "                <!-- Special Term 9 -->\n" + "                <ReferFloodRisk value=\"N\"/>\n"
                + "                <!-- Special Term 10 -->\n" + "                <ReferSubsidenceRisk value=\"N\"/>\n"
                + "                <!-- Special Term 11 -->\n" + "                <MinimumSecurityRequirement/>\n"
                + "                <!-- Special Term 12 -->\n"
                + "                <BasicBuildingsSumInsured value=\"110200\"/>\n"
                + "                <!-- Special Term 13 -->\n"
                + "                <BasicContentsSumInsured value=\"23200\"/>\n"
                + "                <!-- Special Term 14 -->\n"
                + "                <AccidentalDamageAreaRating value=\"52\"/>\n"
                + "                <!-- Special Term 15 -->\n"
                + "                <EscapeOfWaterAreaRating value=\"16\"/>\n" + "            </GeoAttributes>\n"
                + "        </Property>\n" + "    </Properties>\n" + "</Policy>\n" + "</root>";
    }


}
