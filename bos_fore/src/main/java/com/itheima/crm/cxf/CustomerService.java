
package com.itheima.crm.cxf;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 *
 */
@WebService(name = "CustomerService", targetNamespace = "http://service.crm.itheima.com/")
@XmlSeeAlso({
})
public interface CustomerService {


    /**
     *
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "assignCustomers2FixedArea", targetNamespace = "http://service.crm.itheima.com/", className = "com.itheima.crm.cxf.AssignCustomers2FixedArea")
    @ResponseWrapper(localName = "assignCustomers2FixedAreaResponse", targetNamespace = "http://service.crm.itheima.com/", className = "com.itheima.crm.cxf.AssignCustomers2FixedAreaResponse")
    public void assignCustomers2FixedArea(
            @WebParam(name = "arg0", targetNamespace = "")
                    String arg0,
            @WebParam(name = "arg1", targetNamespace = "")
                    List<Integer> arg1);

    /**
     *
     * @return
     *     returns java.util.List<com.itheima.crm.cxf.Customer>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findCustomersNotAssociation", targetNamespace = "http://service.crm.itheima.com/", className = "com.itheima.crm.cxf.FindCustomersNotAssociation")
    @ResponseWrapper(localName = "findCustomersNotAssociationResponse", targetNamespace = "http://service.crm.itheima.com/", className = "com.itheima.crm.cxf.FindCustomersNotAssociationResponse")
    public List<Customer> findCustomersNotAssociation();

    /**
     *
     * @param arg0
     * @return
     *     returns java.util.List<com.itheima.crm.cxf.Customer>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findCustomersHasAssociation", targetNamespace = "http://service.crm.itheima.com/", className = "com.itheima.crm.cxf.FindCustomersHasAssociation")
    @ResponseWrapper(localName = "findCustomersHasAssociationResponse", targetNamespace = "http://service.crm.itheima.com/", className = "com.itheima.crm.cxf.FindCustomersHasAssociationResponse")
    public List<Customer> findCustomersHasAssociation(
            @WebParam(name = "arg0", targetNamespace = "")
                    String arg0);

    /**
     *
     * @return
     *     returns java.util.List<com.itheima.crm.cxf.Customer>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findAll", targetNamespace = "http://service.crm.itheima.com/", className = "com.itheima.crm.cxf.FindAll")
    @ResponseWrapper(localName = "findAllResponse", targetNamespace = "http://service.crm.itheima.com/", className = "com.itheima.crm.cxf.FindAllResponse")
    public List<Customer> findAll();

    public Customer findByTelephone(String telephone);

    public void regist(Customer customer);

    public void activeMail(String telephone);

    public Customer login(String telephone, String password);
}
