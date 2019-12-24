/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.neftrtgs;

import com.cbs.entity.neftrtgs.CbsAutoNeftDetails;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.neftrtgs.NeftRtgsMgmtFacadeRemote;
import com.cbs.ibl.pojo.CustomerBalEnq;
import com.cbs.ibl.pojo.CustomerBalEnqResp;
import com.cbs.ibl.pojo.CustomerStmtEnq;
import com.cbs.ibl.pojo.CustomerStmtEnqResp;
import com.cbs.ibl.pojo.ReturnEnquiry;
import com.cbs.ibl.util.IblUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author root
 */
public class IblEnquiry extends BaseBean {

    public IblEnquiry() {
        try {
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            neftRemote = (NeftRtgsMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NeftRtgsMgmtFacade");
            functionList = new ArrayList<SelectItem>();

            functionList.add(new SelectItem("", "Select Option"));
            functionList.add(new SelectItem("AB", "Account Balance"));
            functionList.add(new SelectItem("AS", "Account Statement"));
            functionList.add(new SelectItem("RE", "Return Enquiry"));

            setFunction("");
            setCustomerId(ftsRemote.getCodeFromCbsParameterInfo("IBL_CLIENT_ID"));
            setAcctNo(neftRemote.getSponsorAcctNo());
            setDisable(false);
            setFocusId("ddFunction");
            setBalDisplay("none");
            setStmtDisplay("none");
            setRetDisplay("none");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void changeFunction() {
        try {
            setBalDisplay("none");
            setStmtDisplay("none");
            setRetDisplay("none");
            
            if (function.equals("")) {
                throw new ApplicationException("Please select the function");
            }
            if (function.equals("AB")) {
                setDisable(true);
                setFocusId("btn");
            } else if (function.equals("AS")) {
                setDisable(false);
                setFocusId("txtFromDt");
            } else if (function.equals("RE")) {
                setDisable(true);
                setFocusId("btn");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getDetails() {
        try {
            if (function.equals("")) {
                throw new ApplicationException("Please select the function");
            }

            if (getCustomerId().equals("")) {
                throw new ApplicationException("Customer Id must not be blank");
            }
            String enqReq = "";
            CbsAutoNeftDetails obj = neftRemote.getIblWebServiceObject();
            String wsUrl = IblUtil.createIblWsUrl(obj.getHostName(), obj.getUserName(), obj.getPassword());
            if (function.equals("RE")) {
                ReturnEnquiry retEnq = new ReturnEnquiry();
                retEnq.setCustomerID(getCustomerId());
                //enqReq = IblUtil.create(IblUtil.getXmlStringWithoutPrifix(retEnq));
            }

            if (function.equals("AB")) {
                if (getCustomerId().equals("")) {
                    throw new ApplicationException("Sponsor Account No must not be blank");
                }
                setBalDisplay("block");
                balList = new ArrayList<CustomerBalEnqResp.Accounts.AccDtls>();
                CustomerBalEnq balEnq = new CustomerBalEnq();
                balEnq.setCustomerID(getCustomerId());

                balEnq.setAccountNumber(getAcctNo());
                balEnq.setUserID(ftsRemote.getCodeFromCbsParameterInfo("IBL_MAKER_ID"));
                enqReq = IblUtil.createBalEnqReq(IblUtil.getXmlStringWithoutPrifix(balEnq));

                String soapInput = IblUtil.createSOAPRequest(enqReq);

                Document responseDoc = IblUtil.executeWSOperation(wsUrl, "GetAccBalance", soapInput);

                NodeList nodeList = responseDoc.getElementsByTagName("CustomerBalEnqResp");
                JAXBContext jaxbContext = JAXBContext.newInstance(CustomerBalEnqResp.class);

                Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
                CustomerBalEnqResp res = (CustomerBalEnqResp) unMarshaller.unmarshal(nodeList.item(0));
                
                System.out.println(IblUtil.getXmlStringWithoutPrifix(res));
                if (!res.getRespCode().equals("R000")) throw new ApplicationException(res.getRespDesc());
                balList.add(res.getAccounts().getAccDtls());

            }

            if (function.equals("AS")) {
                if (fromDt.equals("")) {
                    throw new ApplicationException("From Date must not be blank");
                }

                if (toDt.equals("")) {
                    throw new ApplicationException("To Date must not be blank");
                }

                if (dmy.parse(fromDt).after(dmy.parse(toDt))) {
                    throw new ApplicationException("From Date must be less than To Date");
                }
                setStmtDisplay("block");
                stmtList = new ArrayList<CustomerStmtEnqResp.TrxnHistory.Transaction>();
                CustomerStmtEnq stmtEnq = new CustomerStmtEnq();
                stmtEnq.setCustomerID(getCustomerId());
                stmtEnq.setAccountNumber(getAcctNo());

                stmtEnq.setUserID(ftsRemote.getCodeFromCbsParameterInfo("IBL_MAKER_ID"));
                stmtEnq.setFromDate(dmyhifen.format(dmy.parse(fromDt)));

                stmtEnq.setToDate(dmyhifen.format(dmy.parse(toDt)));

                String soapInput = IblUtil.createSOAPRequest(IblUtil.createAcctStmtReq(IblUtil.getXmlStringWithoutPrifix(stmtEnq)));

                Document responseDoc = IblUtil.executeWSOperation(wsUrl, "GetStatment", soapInput);

                NodeList nodeList = responseDoc.getElementsByTagName("CustomerStmtEnqResp");
                JAXBContext jaxbContext = JAXBContext.newInstance(CustomerStmtEnqResp.class);

                Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
                CustomerStmtEnqResp res = (CustomerStmtEnqResp) unMarshaller.unmarshal(nodeList.item(0));
                System.out.println(IblUtil.getXmlStringWithoutPrifix(res));
                
                if(!res.getRespCode().equals("R000"))  throw new ApplicationException(res.getRespDesc()); 
                for (CustomerStmtEnqResp.TrxnHistory.Transaction txnObj : res.getTrxnHistory().getTransaction()) {
                    stmtList.add(txnObj);
                }
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refreshForm() {
        setFunction("");
        setFocusId("ddFunction");
        setDisable(false);
        
        setFromDt("");
        setToDt("");
        
        setBalDisplay("none");
        setStmtDisplay("none");
        setRetDisplay("none");
        
        balList = new ArrayList<CustomerBalEnqResp.Accounts.AccDtls>();
        stmtList = new ArrayList<CustomerStmtEnqResp.TrxnHistory.Transaction>();
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }

    private String function;
    private String customerId;
    private String acctNo;
    private String ibRefNo;
    private String user;
    private String fromDt;
    private String toDt;
    private String message;
    private boolean disable;
    private String focusId;
    private String balDisplay;
    private String stmtDisplay;
    private String retDisplay;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dmyhifen = new SimpleDateFormat("dd-MM-yyyy");

    private List<SelectItem> functionList;
    private List<CustomerBalEnqResp.Accounts.AccDtls> balList = new ArrayList<CustomerBalEnqResp.Accounts.AccDtls>();
    private List<CustomerStmtEnqResp.TrxnHistory.Transaction> stmtList = new ArrayList<CustomerStmtEnqResp.TrxnHistory.Transaction>();

    private FtsPostingMgmtFacadeRemote ftsRemote;
    private NeftRtgsMgmtFacadeRemote neftRemote;

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getIbRefNo() {
        return ibRefNo;
    }

    public void setIbRefNo(String ibRefNo) {
        this.ibRefNo = ibRefNo;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFromDt() {
        return fromDt;
    }

    public void setFromDt(String fromDt) {
        this.fromDt = fromDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public String getBalDisplay() {
        return balDisplay;
    }

    public void setBalDisplay(String balDisplay) {
        this.balDisplay = balDisplay;
    }

    public String getStmtDisplay() {
        return stmtDisplay;
    }

    public void setStmtDisplay(String stmtDisplay) {
        this.stmtDisplay = stmtDisplay;
    }

    public String getRetDisplay() {
        return retDisplay;
    }

    public void setRetDisplay(String retDisplay) {
        this.retDisplay = retDisplay;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public List<CustomerBalEnqResp.Accounts.AccDtls> getBalList() {
        return balList;
    }

    public void setBalList(List<CustomerBalEnqResp.Accounts.AccDtls> balList) {
        this.balList = balList;
    }

    public List<CustomerStmtEnqResp.TrxnHistory.Transaction> getStmtList() {
        return stmtList;
    }

    public void setStmtList(List<CustomerStmtEnqResp.TrxnHistory.Transaction> stmtList) {
        this.stmtList = stmtList;
    }
}
