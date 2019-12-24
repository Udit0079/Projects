/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.HoExtractEntryRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.pojo.ho.GridReconsilation;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Sudhir kr Bisht
 */
public class HoExtractEntry {
    
    private String orgBrnCode;
    private HttpServletRequest request;
    private String todayDate;
    private String userName;
    private List<SelectItem> transactionTypelist;
    private String transactionType;
    private String entryType;
    private List<SelectItem> entryTypeList;
    private String originBranchText;
    private String respondingBranchText;
    private String inputText3;
    private String inputText4;
    private String inputText5;
    private String amount;
    private String enterByText;
    private String detailsText;
    private String authByText;
    private String lastUpdateByText;
    private String lastUpdateDtText;
    private String instNoText;
    private ArrayList<GridReconsilation> griddata;
    private GridReconsilation recon;
    private GridReconsilation authorized;
    private int currentRow;
    private Iterator<GridReconsilation> Iterator1;
    private String msg;
    private String entrytype1;
    private Integer ty;
    private Date dt;
    private Date origindt;
    private Date responddt;
    private String dt1;
    private String origindt1;
    private String responddt1;
    private String image;
    private boolean inputtextby;
    private boolean inputtextdt;
    private String issaved;
    private boolean save;
    private int k = 0;
    private HoExtractEntryRemote beanRemote = null;
    
    public String getTransactionType() {
        return transactionType;
    }
    
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    
    public List<SelectItem> getTransactionTypelist() {
        return transactionTypelist;
    }
    
    public void setTransactionTypelist(List<SelectItem> transactionTypelist) {
        this.transactionTypelist = transactionTypelist;
    }
    
    public String getEntryType() {
        return entryType;
    }
    
    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }
    
    public List<SelectItem> getEntryTypeList() {
        return entryTypeList;
    }
    
    public void setEntryTypeList(List<SelectItem> entryTypeList) {
        this.entryTypeList = entryTypeList;
    }
    
    public String getInstNoText() {
        return instNoText;
    }
    
    public void setInstNoText(String instNoText) {
        this.instNoText = instNoText;
    }
    
    public String getLastUpdateDtText() {
        return lastUpdateDtText;
    }
    
    public void setLastUpdateDtText(String lastUpdateDtText) {
        this.lastUpdateDtText = lastUpdateDtText;
    }
    
    public String getLastUpdateByText() {
        return lastUpdateByText;
    }
    
    public void setLastUpdateByText(String lastUpdateByText) {
        this.lastUpdateByText = lastUpdateByText;
    }
    
    public String getAuthByText() {
        return authByText;
    }
    
    public void setAuthByText(String authByText) {
        this.authByText = authByText;
    }
    
    public String getDetailsText() {
        return detailsText;
    }
    
    public void setDetailsText(String detailsText) {
        this.detailsText = detailsText;
    }
    
    public String getEnterByText() {
        return enterByText;
    }
    
    public void setEnterByText(String enterByText) {
        this.enterByText = enterByText;
    }
    
    public String getAmount() {
        return amount;
    }
    
    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    public String getOriginBranchText() {
        return originBranchText;
    }
    
    public void setOriginBranchText(String originBranchText) {
        this.originBranchText = originBranchText;
    }
    
    public String getIssaved() {
        return issaved;
    }
    
    public void setIssaved(String issaved) {
        this.issaved = issaved;
    }
    
    public Integer getTy() {
        return ty;
    }
    
    public void setTy(Integer ty) {
        this.ty = ty;
    }
    
    public String getEntrytype1() {
        return entrytype1;
    }
    
    public void setEntrytype1(String entrytype1) {
        this.entrytype1 = entrytype1;
    }
    
    public boolean isInputtextby() {
        return inputtextby;
    }
    
    public void setInputtextby(boolean inputtextby) {
        this.inputtextby = inputtextby;
    }
    
    public boolean isInputtextdt() {
        return inputtextdt;
    }
    
    public void setInputtextdt(boolean inputtextdt) {
        this.inputtextdt = inputtextdt;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public Date getDt() {
        return dt;
    }
    
    public void setDt(Date dt) {
        this.dt = dt;
    }
    
    public Date getOrigindt() {
        return origindt;
    }
    
    public void setOrigindt(Date origindt) {
        this.origindt = origindt;
    }
    
    public Date getResponddt() {
        return responddt;
    }
    
    public void setResponddt(Date responddt) {
        this.responddt = responddt;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public Iterator<GridReconsilation> getIterator1() {
        return Iterator1;
    }
    
    public void setIterator1(Iterator<GridReconsilation> Iterator1) {
        this.Iterator1 = Iterator1;
    }
    
    public GridReconsilation getAuthorized() {
        return authorized;
    }
    
    public void setAuthorized(GridReconsilation authorized) {
        this.authorized = authorized;
    }
    
    public int getCurrentRow() {
        return currentRow;
    }
    
    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }
    
    public GridReconsilation getRecon() {
        return recon;
    }
    
    public void setRecon(GridReconsilation recon) {
        this.recon = recon;
    }
    
    public ArrayList<GridReconsilation> getGriddata() {
        return griddata;
    }
    
    public void setGriddata(ArrayList<GridReconsilation> griddata) {
        this.griddata = griddata;
    }
    
    public String getRespondingBranchText() {
        return respondingBranchText;
    }
    
    public void setRespondingBranchText(String respondingBranchText) {
        this.respondingBranchText = respondingBranchText;
    }
    
    public String getInputText3() {
        return inputText3;
    }
    
    public void setInputText3(String inputText3) {
        this.inputText3 = inputText3;
    }
    
    public String getInputText4() {
        return inputText4;
    }
    
    public void setInputText4(String inputText4) {
        this.inputText4 = inputText4;
    }
    
    public String getInputText5() {
        return inputText5;
    }
    
    public void setInputText5(String inputText5) {
        this.inputText5 = inputText5;
    }
    
    public String getOrgBrnCode() {
        return orgBrnCode;
    }
    
    public void setOrgBrnCode(String orgBrnCode) {
        this.orgBrnCode = orgBrnCode;
    }
    
    public String getTodayDate() {
        return todayDate;
    }
    
    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public boolean isSave() {
        return save;
    }
    
    public void setSave(boolean save) {
        this.save = save;
    }

    /** Creates a new instance of HoExtractEntry */
    public HoExtractEntry() {
        try {
            beanRemote = (HoExtractEntryRemote) ServiceLocator.getInstance().lookup("HoExtractEntryBean");
            request = getRequest();
            String orgnBrIp = WebUtil.getClientIP(request);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgBrnCode = Init.IP2BR.getBranch(localhost);
            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(dt));
            setUserName(request.getRemoteUser());
            onload();
            griddata = new ArrayList<GridReconsilation>();
            inputtextby = true;
            inputtextdt = true;
            save = true;
        } catch (Exception e) {
            this.setMsg(e.getLocalizedMessage());
        }
    }
    
    public HttpServletRequest getRequest() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return req;
    }
    
    public void onload() {
        transactionTypelist = new ArrayList<SelectItem>();
        transactionTypelist.add(new SelectItem("0", "Credit"));
        transactionTypelist.add(new SelectItem("1", "Debit"));
        entryTypeList = new ArrayList<SelectItem>();
        entryTypeList.add(new SelectItem("O", "Originating"));
        entryTypeList.add(new SelectItem("R", "Responding"));
    }
    
    public void addButton() {
        try {
            if (this.getOriginBranchText().equals("")) {
                this.setMsg("Error!!! Enter the value for origin branch");
                return;
            }
            if (this.getRespondingBranchText().equals("")) {
                this.setMsg("Error!!! Enter the value for responding branch");
                return;
            }
            if (dt == null) {
                this.setMsg("Error!!! Enter the date or input it in dd/mm/yyyy");
                return;
            }
            if (origindt == null) {
                this.setMsg("Error!!! Enter the date or input it in dd/mm/yyyy");
                return;
            }
            if (responddt == null) {
                this.setMsg("Error!!! Enter the date or input it in dd/mm/yyyy");
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            if (this.getAmount().equals("")) {
                this.setMsg("Error!!! Enter the amount..");
                return;
            }
            if (getEnterByText().equals("")) {
                this.setMsg("Error!!! Enter enter by value in field..");
                return;
            }
            if (getDetailsText().equals("")) {
                this.setMsg("Error!!! Enter details in the field given..");
                return;
            }
            if (getAuthByText().equals("")) {
                this.setMsg("Error!!! Enter the auth by field in text");
                return;
            }
            if (getInstNoText().equals("")) {
                this.setMsg("Error!!! Enter the Inst no");
                return;
            }
            if (getOriginBranchText().equalsIgnoreCase("")) {
                setMsg("Error!! Origin branch is empty");
                return;
            }
            if (getRespondingBranchText().equalsIgnoreCase("")) {
                setMsg("Error!! Responding branch is empty");
                return;
            }
            if (getDt() == null) {
                this.setMsg("Error!!! Date not selected");
                return;
            }
            if (getOrigindt() == null) {
                this.setMsg("Error!! Origin date not selcted");
                return;
            }
            if (getResponddt() == null) {
                this.setMsg("Error!! Respondign date not selected");
                return;
            }
            
            String valResult = onblurAmount();
            if (!valResult.equalsIgnoreCase("true")) {
                setMsg(valResult);
                return;
            }
            valResult = onBlurEnterBy();
            if (!valResult.equalsIgnoreCase("true")) {
                setMsg(valResult);
                return;
            }
            valResult = onBlurdetails();
            if (!valResult.equalsIgnoreCase("true")) {
                setMsg(valResult);
                return;
            }
            valResult = onBlurAuthBy();
            if (!valResult.equalsIgnoreCase("true")) {
                setMsg(valResult);
                return;
            }
            valResult = InstNo();
            if (!valResult.equalsIgnoreCase("true")) {
                setMsg(valResult);
                return;
            }
            recon = new GridReconsilation();
            recon.setAmount(Float.parseFloat(amount));
            recon.setAuthBy(authByText);
            recon.setDetails(detailsText);
            recon.setDt(sdf.format(dt));
            recon.setEnterBY(enterByText);
            recon.setInstNo(instNoText);
            recon.setLastUpdateBy(lastUpdateByText);
            recon.setLastUpdateDt("");
            recon.setOriginBranch(originBranchText);
            recon.setOriginDt(sdf.format(origindt));
            recon.setRespondDt(sdf.format(responddt));
            recon.setRespondingBranch(respondingBranchText);
            recon.setTy(Integer.parseInt(transactionType));
            recon.setEntrytype(entryType);
            
            griddata.add(recon);
            Iterator1 = griddata.iterator();
            save = false;
            this.setMsg("");
            setOriginBranchText("");
            setLastUpdateByText("");
            setLastUpdateDtText("");
            setInstNoText("");
            setRespondingBranchText("");
            setAmount("");
            setEnterByText("");
            setDetailsText("");
            setAuthByText("");
        } catch (Exception e) {
            this.setMsg(e.getLocalizedMessage());
        }
    }
    
    public void deleteRowValues() {
        try {
            griddata.remove(authorized);
        } catch (Exception e) {
            this.setMsg(e.getLocalizedMessage());
        }
    }
    
    public void save() {
        try {
            String resultMessage = null;
            if (griddata.isEmpty()) {
                this.setMsg("Error!!! No data in grid to save");
                return;
            }
            this.setMsg("");
            GridReconsilation element = null;
            this.setMsg("");
            while (Iterator1.hasNext()) {
                element = Iterator1.next();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String date = element.getDt().substring(6, 10) + element.getDt().substring(3, 5) + element.getDt().substring(0, 2);
                String origindate = element.getOriginDt().substring(6, 10) + element.getOriginDt().substring(3, 5) + element.getOriginDt().substring(0, 2);
                String respondate = element.getRespondDt().substring(6, 10) + element.getRespondDt().substring(3, 5) + element.getRespondDt().substring(0, 2);
                resultMessage = beanRemote.save(element.getOriginBranch(), element.getRespondingBranch(), date, origindate, respondate, element.getInstNo(),
                        element.getAmount(), element.getTy(), element.getEnterBY(), element.getAuthBy(), element.getDetails(), element.getEntrytype());
                if (resultMessage.equalsIgnoreCase("false")) {
                    break;
                } else {
                    griddata.remove(element);
                }
            }
            if (!resultMessage.equals("false")) {
                this.setMsg("Data saved successfully");
                save = true;
                griddata.clear();
                setOriginBranchText("");
                setLastUpdateByText("");
                setLastUpdateDtText("");
                setInstNoText("");
                setRespondingBranchText("");
                setAmount("");
                setEnterByText("");
                setDetailsText("");
                setAuthByText("");
            } else {
                this.setMsg("Error!! Entries remaining in grid could not saved..");
            }
        } catch (ApplicationException e) {
            this.setMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsg(e.getLocalizedMessage());
        }
    }
    
    public String onblurAmount() {
        try {
            setMsg("");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(this.getAmount());
            if (!billNoCheck.matches()) {
                return "Error!!! Amount not valid";
            }
            if (getAmount().equals("") || (Double.parseDouble(getAmount()) < 0)) {
                return "Error!!! Amount cannot be negative.. And should not be blank";
            }
        } catch (Exception e) {
            return e.getLocalizedMessage();
            
        }
        return "true";
    }
    
    public String onBlurEnterBy() {
        try {
            setMsg("");
            if (getEnterByText().equals("") || getEnterByText().length() > 40) {
                return "Error!!! EnterBy field cannot be blank and should not be exceed its Length..";
            }
            Pattern p = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");
            Matcher billNoCheck = p.matcher(getEnterByText());
            if (!billNoCheck.matches()) {
                return "Error!!! Enter valid value for enterby";
            }
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
        return "true";
    }
    
    public String onBlurdetails() {
        try {
            setMsg("");
            if (getDetailsText().equals("") || getDetailsText().length() > 100) {
                return "Error!!! Details field cannot be blank and should not be exceed its length..";
            }
            Pattern p = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");
            Matcher billNoCheck = p.matcher(getDetailsText());
            if (!billNoCheck.matches()) {
                return "Error!!! Please enter valid value for details";
            }
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
        return "true";
    }
    
    public String onBlurAuthBy() {
        try {
            setMsg("");
            if (getAuthByText().equals("") || getAuthByText().length() > 40) {
                return "Error!!! Auth by field cannot be blank and should not exceed its length..";
            }
            Pattern p = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");
            Matcher billNoCheck = p.matcher(this.getAuthByText());
            if (!billNoCheck.matches()) {
                return "Error!!! Enter valid value for auth by";
            }
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
        return "true";
    }
    
    public String InstNo() {
        try {
            setMsg("");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(this.getInstNoText());
            if (!billNoCheck.matches()) {
                return "Error!!! Enter valid value for Instrument no";
            }
            if (getInstNoText().equals("") || (Integer.parseInt(getInstNoText()) < 0)) {
                return "Error!!! Entered InstNo cannot be negative.. And should not be blank";
            }
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
        return "true";
    }
    
    public void refresh() {
        try {
            this.setMsg("");
            setOriginBranchText(originBranchText);
            setLastUpdateByText("");
            setLastUpdateDtText("");
            setInstNoText("");
            setRespondingBranchText("");
            setAmount("");
            setEnterByText("");
            setDetailsText("");
            setAuthByText("");
        } catch (Exception e) {
            this.setMsg(e.getLocalizedMessage());
        }
    }
    
    public String exitBtnAction() {
        try {
            refresh();
        } catch (Exception e) {
            this.setMsg(e.getLocalizedMessage());
        }
        return "case1";
    }
}
