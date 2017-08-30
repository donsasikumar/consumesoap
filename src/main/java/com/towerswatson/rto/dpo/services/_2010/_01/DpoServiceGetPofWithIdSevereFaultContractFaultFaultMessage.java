
package com.towerswatson.rto.dpo.services._2010._01;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.1.12
 * 2017-08-30T11:57:45.821+01:00
 * Generated source version: 3.1.12
 */

@WebFault(name = "SevereFaultContract", targetNamespace = "http://towerswatson.com/rto/dpo/types/2010/01")
public class DpoServiceGetPofWithIdSevereFaultContractFaultFaultMessage extends Exception {
    
    private com.towerswatson.rto.dpo.types._2010._01.SevereFaultContract severeFaultContract;

    public DpoServiceGetPofWithIdSevereFaultContractFaultFaultMessage() {
        super();
    }
    
    public DpoServiceGetPofWithIdSevereFaultContractFaultFaultMessage(String message) {
        super(message);
    }
    
    public DpoServiceGetPofWithIdSevereFaultContractFaultFaultMessage(String message, Throwable cause) {
        super(message, cause);
    }

    public DpoServiceGetPofWithIdSevereFaultContractFaultFaultMessage(String message, com.towerswatson.rto.dpo.types._2010._01.SevereFaultContract severeFaultContract) {
        super(message);
        this.severeFaultContract = severeFaultContract;
    }

    public DpoServiceGetPofWithIdSevereFaultContractFaultFaultMessage(String message, com.towerswatson.rto.dpo.types._2010._01.SevereFaultContract severeFaultContract, Throwable cause) {
        super(message, cause);
        this.severeFaultContract = severeFaultContract;
    }

    public com.towerswatson.rto.dpo.types._2010._01.SevereFaultContract getFaultInfo() {
        return this.severeFaultContract;
    }
}