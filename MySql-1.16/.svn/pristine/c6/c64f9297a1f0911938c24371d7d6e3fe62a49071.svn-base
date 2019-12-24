/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.sms;

import com.cbs.dto.sms.BulkSmsTo;
import com.cbs.dto.sms.MbSmsSenderBankDetailTO;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.sms.MessageDetailBeanRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@ViewScoped
@ManagedBean(name = "instantMessaging")
public class InstantMessaging extends BaseBean {

    private Integer count;
    private String msg;
    private String branch;
    private String msgType;
    private String mobileNo;
    private String acno, acNoLen, newAccountNo;
    private String message;
    private boolean fieldDisable = true;
    private List<SelectItem> branchList;
    private List<SelectItem> msgTypeList;
    private Validator validator;
    private String displayOnCreate = "none";
    private String displayOnIndividual;
    private String displayOnProductWise;
    private String natureType;
    private List<SelectItem> natureTypeList;
    private String accountType;
    private List<SelectItem> accountTypeList;
    private String filterType;
    private List<SelectItem> filterTypeList;
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    private SmsManagementFacadeRemote smsMgmtFacade = null;
    private CommonReportMethodsRemote commonReport = null;
    private MessageDetailBeanRemote messageDetailBeanRemote = null;
    private Properties props = null;

    public InstantMessaging() {
        msg = "";
        try {
            props = new Properties();
            props.load(new FileReader("/opt/conf/sms.properties"));

            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            smsMgmtFacade = (SmsManagementFacadeRemote) ServiceLocator.getInstance().lookup("SmsManagementFacade");
            commonReport = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            messageDetailBeanRemote = (MessageDetailBeanRemote) ServiceLocator.getInstance().lookup("MessageDetailBean");
            this.setAcNoLen(getAcNoLength());

            validator = new Validator();
            branchList = new ArrayList<>();
            branchList.add(new SelectItem("0", "--Select--"));
            String alphaCode = commonReport.getAlphacodeByBrncode(getOrgnBrCode());
            if (alphaCode.equalsIgnoreCase("HO")) {
                branchList.add(new SelectItem("A", "ALL"));
            }
            List list = commonReport.getAlphacodeAllAndBranch(getOrgnBrCode());
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(i);
                    branchList.add(new SelectItem(ele.get(1).toString().length()
                            < 2 ? "0" + ele.get(1).toString() : ele.get(1).toString(), ele.get(0).toString()));
                }

            }

            msgTypeList = new ArrayList<>();
            msgTypeList.add(new SelectItem("0", "--Select--"));
            msgTypeList.add(new SelectItem("AL", "ALL"));   //Registered+Non-Registered
            msgTypeList.add(new SelectItem("AR", "REGISTERED"));    //All Registered
            msgTypeList.add(new SelectItem("ST", "STAFF"));
            msgTypeList.add(new SelectItem("SI", "INDIVIDUAL"));
            msgTypeList.add(new SelectItem("PW", "PRODUCT WISE"));
            this.displayOnIndividual = "none";
            filterTypeList = new ArrayList<>();
            filterTypeList.add(new SelectItem("ALL"));
            filterTypeList.add(new SelectItem("AR", "REGISTERED"));
            filterTypeList.add(new SelectItem("NR", "NON REGISTERED"));
            this.displayOnProductWise = "none";
            getacNature();
            refreshPage();
            this.setMsg("Please select branch.");
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }

    }

    public void getacNature() {
        try {
            natureTypeList = new ArrayList<>();
            natureTypeList.add(new SelectItem("0", "--Select--"));
            List natureType = commonReport.getAllAcNature();
            if (!natureType.isEmpty()) {
                for (int i = 0; i < natureType.size(); i++) {
                    Vector vec = (Vector) natureType.get(i);
                    natureTypeList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public void getAccountDetails() {
        try {
            accountTypeList = new ArrayList<>();
            accountTypeList.add(new SelectItem("ALL", "ALL"));
            List acTypeList = commonReport.getAccType(natureType);
            if (!acTypeList.isEmpty()) {
                for (int i = 0; i < acTypeList.size(); i++) {
                    Vector vec = (Vector) acTypeList.get(i);
                    accountTypeList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public void accountAction() {
        try {
            this.newAccountNo = "";
            if (acno == null || acno.equalsIgnoreCase("")) {
                this.setMsg("Please fill Account Number.");
                return;
            }
            if (!(this.acno.length() == 12 || (this.acno.length() == Integer.parseInt(getAcNoLen())))) {
                this.setMsg("Please fill proper Account Number.");
                return;
            }
            this.newAccountNo = ftsRemote.getNewAccountNumber(this.acno);
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }

    }

    public void countingAction() {
        count = message.length();
    }

    public void msgTypeAction() {
        if (this.msgType.equalsIgnoreCase("SI")) {

            this.displayOnIndividual = "";
        } else {
            this.displayOnIndividual = "none";
        }
        if (this.msgType.equalsIgnoreCase("PW")) {
            this.displayOnProductWise = "";
        } else {
            this.displayOnProductWise = "none";
        }
    }

    public void sendMessage() {
        try {
            String result = validation();
            if (!result.equalsIgnoreCase("ok")) {
                this.setMsg(result);
                return;
            }
            List<MbSmsSenderBankDetailTO> bankTo = smsMgmtFacade.getBankAndSenderDetail();
            String templateBankName = bankTo.get(0).getTemplateBankName().trim();
            String vendor = props.getProperty("sms.vendor");
            String promoMsg = "";
            if (this.msgType.equalsIgnoreCase("SI")) { //Individual case
                promoMsg = message.trim() + " Thanks, " + bankTo.get(0).getTemplateBankName().trim();
                try {
                    smsMgmtFacade.sendPromotionalSms(newAccountNo, promoMsg, "+91" + mobileNo, branch, getUserName());
                    refreshPage();
                    msg = "Message has been sent successfully !";
                } catch (Exception ex) {
                    System.out.println("Individual SMS Sending-->" + ex.getMessage());
                    this.setMsg("Error In SMS Sending.");
                }
            } else { //Bulk Case    yyyy-MM-dd HH:mm:ss
                Integer bulkSmsCount = ftsRemote.getCodeForReportName("BULK-SMS-COUNT");
                Integer bulkSmsPerFile = ftsRemote.getCodeForReportName("BULK-SMS-PER-FILE");
                SimpleDateFormat dhmsss = new SimpleDateFormat("ddHHmmssSSS");
                List list;
                if (this.msgType.equalsIgnoreCase("PW")) { //Product Wise
                    list = messageDetailBeanRemote.getProductWiseDetail(this.branch, this.filterType,
                            this.natureType, this.accountType);
                } else {//Other Bulk Case
                    list = messageDetailBeanRemote.getMobileDetail(this.branch, this.msgType);
                }
                if (list == null || list.isEmpty()) {
                    this.setMsg("There is no detail to send SMS.");
                    return;
                }
                System.out.println("Actual List Size-->" + list.size());
                List<BulkSmsTo> bulkSmsList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    BulkSmsTo to = new BulkSmsTo();
                    Vector ele = (Vector) list.get(i);
                    to.setAccountNo(ele.get(1).toString().trim());
                    to.setPhone(ele.get(2).toString().trim()); //With +91
                    to.setMessage(this.message.trim());
                    to.setMessageType("TRANSACTIONAL");
                    to.setUserName(getUserName());
                    to.setModuleName("INSTANT");
                    to.setMasks("Custom");
                    to.setUniqueMessageId(dhmsss.format(new Date()) + i);
                    bulkSmsList.add(to);
                }
                System.out.println("After Conversion List Size-->" + bulkSmsList.size());
                if (bulkSmsList.size() >= bulkSmsCount) { //Bulk
                    List<List<BulkSmsTo>> fileSmsList = new ArrayList<>();
                    for (int i = 0; i < bulkSmsList.size(); i = i + bulkSmsPerFile) {
                        List<BulkSmsTo> subList;
                        if (i + bulkSmsPerFile < bulkSmsList.size()) {
                            subList = bulkSmsList.subList(i, i + bulkSmsPerFile);
                        } else {
                            subList = bulkSmsList.subList(i, bulkSmsList.size());
                        }
                        fileSmsList.add(subList);
                    }

                    if (vendor.equalsIgnoreCase("digialaya")) {
                        List<String> xmlString = createXmlStringFiles(fileSmsList);
                        result = smsMgmtFacade.sendBulkSms(vendor, null, bulkSmsList, xmlString, "Internal", null,
                                getUserName(), getOrgnBrCode(), getTodayDate());
                        refreshPage();
                        msg = result;
                    } else if (vendor.equalsIgnoreCase("gupshup")) {
                        List<File> files = createGupshupXlsFiles(fileSmsList);
                        result = smsMgmtFacade.sendBulkSms(vendor, files, bulkSmsList, null, "Internal", null,
                                getUserName(), getOrgnBrCode(), getTodayDate());
                        refreshPage();
                        msg = result;
                    }
                } else { //Individual
                    result = bulkSmsExceptFileMode(bulkSmsList, templateBankName);
                    if (!result.equalsIgnoreCase("true")) {
                        this.setMsg(result);
                        return;
                    }
                    refreshPage();
                    msg = "Message has been sent successfully !";
                }
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public String bulkSmsExceptFileMode(List<BulkSmsTo> bulkSmsList, String templateBankName) throws Exception {
        try {
            for (BulkSmsTo to : bulkSmsList) {
                try {
                    smsMgmtFacade.sendPromotionalSms(to.getAccountNo(), to.getMessage() + " Thanks, "
                            + templateBankName, to.getPhone(), templateBankName, getUserName());
                } catch (Exception ex) {
                    System.out.println("Error In Bulk SMS Sending. Mobile-->");
                }
            }
        } catch (Exception ex) {
            throw new Exception((ex.getMessage()));
        }
        return "true";
    }

    public List<File> createGupshupXlsFiles(List<List<BulkSmsTo>> fileSmsList) throws Exception {
        SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        List<File> files = new ArrayList<>();
        try {
            for (int x = 0; x < fileSmsList.size(); x++) {
                List<BulkSmsTo> bulkList = fileSmsList.get(x);

                HSSFWorkbook workbook = new HSSFWorkbook();
                HSSFSheet sheet = workbook.createSheet("sheet-1");
                //Header Line
                Row row = sheet.createRow(0);
                Cell cell = row.createCell(0);
                cell.setCellValue("PHONE");

                cell = row.createCell(1);
                cell.setCellValue("MESSAGE");

                cell = row.createCell(2);
                cell.setCellValue("MASKS");

                cell = row.createCell(3);
                cell.setCellValue("TIMESTAMPS");

                HSSFCellStyle cellStyle = workbook.createCellStyle();
                CreationHelper createHelper = workbook.getCreationHelper();
                cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
                //Data Line
                int rownum = 1;
                for (int i = 0; i < bulkList.size(); i++) {
                    BulkSmsTo to = bulkList.get(i);
                    row = sheet.createRow(rownum++);
                    cell = row.createCell(0);
                    cell.setCellValue(to.getPhone().trim().substring(3));

                    cell = row.createCell(1);
                    cell.setCellValue(to.getMessage().trim());

                    cell = row.createCell(2);
                    cell.setCellValue(to.getMasks().trim());

                    cell = row.createCell(3);
                    cell.setCellValue(new Date());
                    cell.setCellStyle(cellStyle);
                }

                ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                String dirLocation = context.getInitParameter("cbsFiles") + "/BULK-SMS/";
                File fileLocation = new File(dirLocation);
                if (!fileLocation.exists()) {
                    fileLocation.mkdirs();
                }

                //Write the workbook in file system
                File file = new File(dirLocation + yyyymmdd.format(new Date()) + ".xls");
                FileOutputStream out = new FileOutputStream(file);
                workbook.write(out);
                out.close();
                files.add(file);
                System.out.println("File has been generated successfully.");
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return files;
    }

//    public List<String> createXmlStringFiles(List<List<BulkSmsTo>> fileSmsList) throws Exception {
//        List<String> allXmlString = new ArrayList<>();
//        try {
//            for (int x = 0; x < fileSmsList.size(); x++) {
//                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//                //Root elements
//                Document doc = docBuilder.newDocument();
//                Element rootElement = doc.createElement("MESSAGE");
//                doc.appendChild(rootElement);
//                //Set attribute to root element
//                Attr rootEleAttr = doc.createAttribute("VER");
//                rootEleAttr.setValue("1.2");
//                rootElement.setAttributeNode(rootEleAttr);
//
//                //User element
//                Element user = doc.createElement("USER");
//                rootElement.appendChild(user);
//                //Set attribute to user element
//                Attr username = doc.createAttribute("USERNAME");
//                username.setValue(props.getProperty("sms.param.UserId"));
//                user.setAttributeNode(username);
//
//                Attr password = doc.createAttribute("PASSWORD");
//                password.setValue(props.getProperty("sms.param.pwd"));
//                user.setAttributeNode(password);
//
//                Attr dlr = doc.createAttribute("DLR");
//                dlr.setValue("0");
//                user.setAttributeNode(dlr);
//
//                StringWriter writer = null;
//                List<BulkSmsTo> smsList = fileSmsList.get(x);
//                for (int z = 0; z < smsList.size(); z++) {
//                    BulkSmsTo to = smsList.get(z);
//                    //Sms element
//                    Element sms = doc.createElement("SMS");
//                    rootElement.appendChild(sms);
//                    //Set attribute to sms element
//                    Attr id = doc.createAttribute("ID");
//                    id.setValue(to.getUniqueMessageId().trim());
//                    sms.setAttributeNode(id);
//
//                    Attr senderId = doc.createAttribute("SENDERID");
//                    senderId.setValue(props.getProperty("sms.param.SenderId"));
//                    sms.setAttributeNode(senderId);
//
//                    Attr text = doc.createAttribute("TEXT");
//                    text.setValue(to.getMessage());
//                    sms.setAttributeNode(text);
//                    //Dest element
//                    Element dest = doc.createElement("DEST");
//                    sms.appendChild(dest);
//                    //Set attribute to sms element
//                    Attr toPhone = doc.createAttribute("TO");
//                    toPhone.setValue(to.getPhone().substring(1));
//                    dest.setAttributeNode(toPhone);
//
//                    Attr seq = doc.createAttribute("SEQ");
//                    seq.setValue(String.valueOf(z));
//                    dest.setAttributeNode(seq);
//                    //Write the content into xml file
//                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
//                    Transformer transformer = transformerFactory.newTransformer();
//                    DOMSource source = new DOMSource(doc);
////                    StreamResult result = new StreamResult(new File("/root/Desktop/str.xml")); //In File
////                    StreamResult result = new StreamResult(System.out); //On Console
//                    writer = new StringWriter();
//                    StreamResult result = new StreamResult(writer);
//                    transformer.transform(source, result);
//                }
//                allXmlString.add(writer.toString());
//            }
//        } catch (Exception ex) {
//            throw new Exception(ex.getMessage());
//        }
//        return allXmlString;
//    }
    public List<String> createXmlStringFiles(List<List<BulkSmsTo>> fileSmsList) throws Exception {
        List<String> allXmlString = new ArrayList<>();
        try {
            for (int x = 0; x < fileSmsList.size(); x++) {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                //Root elements
                Document doc = docBuilder.newDocument();
                Element rootElement = doc.createElement("MESSAGE");
                doc.appendChild(rootElement);
                //Set attribute to root element
                Attr rootEleAttr = doc.createAttribute("VER");
                rootEleAttr.setValue(props.getProperty("sms.param.bulk.message.version"));
                rootElement.setAttributeNode(rootEleAttr);

                //Messagetype Element
                Element messageType = doc.createElement("MESSAGETYPE");
                rootElement.appendChild(messageType);
                //Set attribute to Messagetype element
                Attr mType = doc.createAttribute("MType");
                mType.setValue(props.getProperty("sms.param.MessageType"));
                messageType.setAttributeNode(mType);

                //User element
                Element user = doc.createElement("USER");
                rootElement.appendChild(user);
                //Set attribute to user element
                Attr username = doc.createAttribute("USERNAME");
                username.setValue(props.getProperty("sms.param.UserId"));
                user.setAttributeNode(username);

                Attr password = doc.createAttribute("PASSWORD");
                password.setValue(props.getProperty("sms.param.pwd"));
                user.setAttributeNode(password);

                Attr dlr = doc.createAttribute("DLR");
                dlr.setValue("0");
                user.setAttributeNode(dlr);
                //User parent element
                Element userParent = doc.createElement("USERPARENT");
                rootElement.appendChild(userParent);
                //Set attribute to user element
                Attr parentUserName = doc.createAttribute("USERNAME");
                parentUserName.setValue(props.getProperty("sms.param.ParentId"));
                userParent.setAttributeNode(parentUserName);

                Attr parentPassword = doc.createAttribute("PASSWORD");
                parentPassword.setValue(props.getProperty("sms.param.ParentPwd"));
                userParent.setAttributeNode(parentPassword);

                StringWriter writer = null;
                List<BulkSmsTo> smsList = fileSmsList.get(x);
                for (int z = 0; z < smsList.size(); z++) {
                    BulkSmsTo to = smsList.get(z);
                    //Sms element
                    Element sms = doc.createElement("SMS");
                    rootElement.appendChild(sms);
                    //Set attribute to sms element
                    Attr id = doc.createAttribute("ID");
                    id.setValue(to.getUniqueMessageId().trim());
                    sms.setAttributeNode(id);

                    Attr senderId = doc.createAttribute("SENDERID");
                    senderId.setValue(props.getProperty("sms.param.SenderId"));
                    sms.setAttributeNode(senderId);

                    Attr text = doc.createAttribute("TEXT");
                    text.setValue(to.getMessage());
                    sms.setAttributeNode(text);
                    //Dest element
                    Element dest = doc.createElement("DEST");
                    sms.appendChild(dest);
                    //Set attribute to sms element
                    Attr toPhone = doc.createAttribute("TO");
                    toPhone.setValue(to.getPhone().substring(1));
                    dest.setAttributeNode(toPhone);

                    Attr seq = doc.createAttribute("SEQ");
                    seq.setValue(String.valueOf(z));
                    dest.setAttributeNode(seq);
                    //Write the content into xml file
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(doc);

//                    StreamResult result = new StreamResult(new File("/root/Desktop/str.xml")); //In File
//                    StreamResult result = new StreamResult(System.out); //On Console

                    writer = new StringWriter();
                    StreamResult result = new StreamResult(writer);
                    transformer.transform(source, result);
                }
                allXmlString.add(writer.toString());
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return allXmlString;
    }

    public String validation() throws Exception {
        try {
            if (this.branch == null || this.branch.equals("0")) {
                return "Please select branch.";
            }
            if (this.msgType == null || this.msgType.equals("0")) {
                return "Please select message type.";
            }
            if (this.msgType.equalsIgnoreCase("SI")) {  //In case of individual
                if (acno == null || acno.equalsIgnoreCase("")) {
                    return "Please fill Account Number.";
                }
                if (!(this.acno.length() == 12 || (this.acno.length() == Integer.parseInt(getAcNoLen())))) {
                    return "Please fill proper Account Number.";
                }
                this.newAccountNo = ftsRemote.getNewAccountNumber(this.acno);
                String result = ftsRemote.ftsAcnoValidate(newAccountNo, 1, "");
                if (!result.equalsIgnoreCase("true")) {
                    return result;
                }
                if (!(ftsRemote.getCurrentBrnCode(this.newAccountNo).equalsIgnoreCase(this.branch))) {
                    return "Please fill selected branch A/c number !";
                }
                smsMgmtFacade.isActiveAccountForSms(newAccountNo);
                if (mobileNo == null || mobileNo.equalsIgnoreCase("")) {
                    return "Please specify Mobile No.";
                }
                if (mobileNo.length() < 10) {
                    return "Please fill proper Mobile Number.";
                }
                if (!validator.validateMobile(mobileNo)) {
                    return "Mobile No. Is Not Valid !";
                }
            }
            if (this.msgType.equalsIgnoreCase("PW")) {  //In case of individual
                if (filterType == null || filterType.equals("")) {
                    return "Please select Filter Type !";
                }
                if (natureType == null || natureType.equals("0") || natureType.equals("")) {
                    return "Please select Nature !";
                }
                if (accountType == null || accountType.equals("")) {
                    return "Please select Account Type !";
                }
            }
            if (message.equalsIgnoreCase("")) {
                return "Please enter some text.";
            }
            if (message.length() > 160) {
                return "Message length should be maximun of 160 digit";
            }
        } catch (Exception ex) {
            throw new Exception((ex.getMessage()));
        }
        return "ok";
    }

    public String onExitFromInstantMessaging() {
        refreshPage();
        return "/index.jsp?faces-redirect=true";
    }

    public void refreshPage() {
        this.setMsg("");
        this.setMessage("");
        this.setBranch("0");
        msgType = "0";
        displayOnIndividual = "none";
        displayOnProductWise = "none";
        natureType = "0";
        accountTypeList = new ArrayList<SelectItem>();
        filterType = "ALL";
        mobileNo = "";
        acno = "";
        this.setNewAccountNo("");
        this.setCount(0);
        this.setFieldDisable(true);

    }

    public String exitForm() {
        refreshPage();
        return "case1";
    }

    //Getter And Setter
    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public List<SelectItem> getMsgTypeList() {
        return msgTypeList;
    }

    public void setMsgTypeList(List<SelectItem> msgTypeList) {
        this.msgTypeList = msgTypeList;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public boolean isFieldDisable() {
        return fieldDisable;
    }

    public void setFieldDisable(boolean fieldDisable) {
        this.fieldDisable = fieldDisable;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getDisplayOnCreate() {
        return displayOnCreate;
    }

    public void setDisplayOnCreate(String displayOnCreate) {
        this.displayOnCreate = displayOnCreate;
    }

    public String getDisplayOnIndividual() {
        return displayOnIndividual;
    }

    public void setDisplayOnIndividual(String displayOnIndividual) {
        this.displayOnIndividual = displayOnIndividual;
    }

    public String getDisplayOnProductWise() {
        return displayOnProductWise;
    }

    public void setDisplayOnProductWise(String displayOnProductWise) {
        this.displayOnProductWise = displayOnProductWise;
    }

    public String getNatureType() {
        return natureType;
    }

    public void setNatureType(String natureType) {
        this.natureType = natureType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public List<SelectItem> getNatureTypeList() {
        return natureTypeList;
    }

    public void setNatureTypeList(List<SelectItem> natureTypeList) {
        this.natureTypeList = natureTypeList;
    }

    public List<SelectItem> getAccountTypeList() {
        return accountTypeList;
    }

    public void setAccountTypeList(List<SelectItem> accountTypeList) {
        this.accountTypeList = accountTypeList;
    }

    public List<SelectItem> getFilterTypeList() {
        return filterTypeList;
    }

    public void setFilterTypeList(List<SelectItem> filterTypeList) {
        this.filterTypeList = filterTypeList;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public String getNewAccountNo() {
        return newAccountNo;
    }

    public void setNewAccountNo(String newAccountNo) {
        this.newAccountNo = newAccountNo;
    }
}
