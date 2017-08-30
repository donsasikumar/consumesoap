package com.ndj;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.model.support.PropertiesMap;
import com.eviware.soapui.tools.SoapUIMockServiceRunner;
import com.eviware.soapui.tools.SoapUITestCaseRunner;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.junit.Test;

public class SOAPTest
{
    @Test
    public void testDPOService() throws Exception {
        SoapUITestCaseRunner testCaseRunner = new SoapUITestCaseRunner();
        SoapUIMockServiceRunner mockServiceRunner = new SoapUIMockServiceRunner();

        testCaseRunner.setProjectFile("src/test/resources/DpoService-soapui-project.xml");
        mockServiceRunner.setProjectFile("src/test/resources/DpoService-soapui-project.xml");
        mockServiceRunner.run();
        testCaseRunner.run();
    }


}
