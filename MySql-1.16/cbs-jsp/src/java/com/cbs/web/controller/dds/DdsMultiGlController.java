/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.dds;

import com.cbs.dto.master.MultiAcCodeTo;
import com.cbs.facade.dds.DDSManagementFacadeRemote;
import com.cbs.utils.ParseFileUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import static com.cbs.web.controller.dds.DDSOutwardFile.PORT;
import com.hrms.web.utils.WebUtil;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public class DdsMultiGlController extends BaseBean {

    private String errMessage;
    private String ddsAcCode;
    private List<SelectItem> ddsAcCodeList;
    private String agentPanelEnable = "none";
    private String accountCode;
    private List<SelectItem> accountCodeList;
    private String agentCode;
    private String agentName;
    private List<MultiAcCodeTo> selectedList = new CopyOnWriteArrayList<MultiAcCodeTo>();
    private List<MultiAcCodeTo> gridDetail;
    private MultiAcCodeTo currentItem;
    private String days;
    private Socket socketTCP;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private DDSManagementFacadeRemote ddsRemote = null;

    public DdsMultiGlController() {
        this.setErrMessage("");
        try {
            ddsRemote = (DDSManagementFacadeRemote) ServiceLocator.getInstance().lookup("DDSManagementFacade");
            refreshForm();
            initData();
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void initData() {
        accountCodeList = new ArrayList<SelectItem>();
        ddsAcCodeList = new ArrayList<SelectItem>();
        try {
            ddsAcCodeList.add(new SelectItem("0", "--Select--"));
            ddsAcCodeList.add(new SelectItem("1", "With DDS"));
            ddsAcCodeList.add(new SelectItem("2", "Without DDS"));

            accountCodeList.add(new SelectItem("0", "--Select--"));
            List acTypeList = ddsRemote.getAcctType();
            if (!acTypeList.isEmpty()) {
                for (int i = 0; i < acTypeList.size(); i++) {
                    Vector element = (Vector) acTypeList.get(i);
                    accountCodeList.add(new SelectItem(element.get(0).toString()));
                }
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void processAcType() {
        this.setErrMessage("");
        this.agentPanelEnable = "none";
        gridDetail = new ArrayList<MultiAcCodeTo>();
        try {
            if (this.ddsAcCode == null || this.ddsAcCode.equals("0")) {
                this.setErrMessage("Please select A/c Type.");
                return;
            }
            if (this.ddsAcCode.equals("1")) {
                this.agentPanelEnable = "";
            }
            List list = ddsRemote.getMultiGlDdsAcCode();
            if (list.isEmpty()) {
                this.setErrMessage("There is no data in accounttypemaster.");
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                MultiAcCodeTo to = new MultiAcCodeTo();
                to.setNature(ele.get(0).toString());
                to.setAcCode(ele.get(1).toString());
                to.setAcCodeDesc(ele.get(2).toString());
                to.setCheckBox(false);

                gridDetail.add(to);
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void processAgentCode() {
        this.setErrMessage("");
        try {
            if (this.agentCode == null || this.agentCode.equals("") || this.agentCode.length() < 2) {
                this.setErrMessage("Please fill agent code in 2 digit.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher agentCheck = p.matcher(this.agentCode);
            if (!agentCheck.matches()) {
                this.setErrMessage("Invalid Agent Code Entry.");
                return;
            }
            String name = ddsRemote.getAgentName(this.agentCode, getOrgnBrCode());
            if (name.equals("")) {
                this.setErrMessage("Agent does not exist.");
                return;
            }
            this.setAgentName(name);
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public boolean validateField() {
        if (this.ddsAcCode == null || this.ddsAcCode.equals("0")) {
            this.setErrMessage("Please select A/c Type.");
            return false;
        }
        if (this.ddsAcCode.equals("1")) {
            if (this.accountCode == null || this.accountCode.equals("0")) {
                this.setErrMessage("Please select Account Code.");
                return false;
            }
            if (this.agentCode == null || this.agentCode.equals("") || this.agentCode.length() < 2) {
                this.setErrMessage("Please fill agent code in 2 digit.");
                return false;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher agentCheck = p.matcher(this.agentCode);
            if (!agentCheck.matches()) {
                this.setErrMessage("Invalid Agent Code Entry.");
                return false;
            }
            if (this.agentName == null || this.agentName.equals("")) {
                this.setErrMessage("There should be agent name.");
                return false;
            }
        }
        if (this.days == null || this.days.equals("")) {
            this.setErrMessage("Please fill Password Expiry Days. Values can be 1-5");
            return false;
        }
        if (Integer.parseInt(this.days) < 1 || Integer.parseInt(this.days) > 5) {
            this.setErrMessage("Please fill Password Expiry Days. Values can be 1-5");
            return false;
        }
        return true;
    }

    public void addRemoveAcCode() {
        try {
            if (currentItem != null) {
                if (currentItem.isCheckBox()) {
                    selectedList.add(currentItem);
                } else if (!currentItem.isCheckBox()) {
                    selectedList.remove(currentItem);
                }
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void generateFile() {
        this.setErrMessage("");
        try {
            if (validateField()) {
                List<MultiAcCodeTo> glList = selectedList;
                System.out.println("Actual List Size is-->" + glList.size());
                for (int i = 0; i < glList.size(); i++) {
                    System.out.println("Added A/c Code is-->" + glList.get(i).getAcCode());
                }
                if (this.ddsAcCode.equals("2") && glList.isEmpty()) {
                    this.setErrMessage("Please select row from table.");
                    return;
                }
                if (this.ddsAcCode.equals("2") && glList.size() > 10) {
                    this.setErrMessage("You can not select more than 10 a/c type in case of without dds.");
                    return;
                }
                if (this.ddsAcCode.equals("1") && glList.size() > 9) {
                    this.setErrMessage("You can not select more than 9 a/c type in case of with dds.");
                    return;
                }
                Integer password = ParseFileUtil.getNDigitRandomNumber(8);
                List<String> dataList = ddsRemote.generateMultiGlDDSOwFile(glList, this.ddsAcCode, this.accountCode,
                        this.agentCode, this.agentName, this.days, password, getOrgnBrCode(), getTodayDate(), getUserName());
                if (dataList.isEmpty()) {
                    this.setErrMessage("There is no data to generate the file.");
                    return;
                }
                //Test List
//                for (int i = 0; i < dataList.size(); i++) {
//                    String outStr = dataList.get(i);
//                    System.out.println(outStr);
//                }
                //End Here.
               // String remoteHost = getHttpRequest().getRemoteHost();
                String remoteHost = WebUtil.getClientIP(getHttpRequest());
                socketTCP = new Socket(remoteHost, PORT);
                objectOutputStream = new ObjectOutputStream(socketTCP.getOutputStream());
                objectInputStream = new ObjectInputStream(socketTCP.getInputStream());
                writeObject(dataList);
                Vector result = (Vector) readObject();
                System.out.println("result = " + result);
                if (result.elementAt(0).equals(true)) {
                    this.setErrMessage("DDS outward file is successfully generated. Now you can send data "
                            + "to Machine. And Password is :: " + password);
                } else {
                    this.setErrMessage("System error occurred");
                }
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public boolean writeObject(List<String> dataList) {
        try {
            Vector writeVector = new Vector();
            writeVector.add("DDS-MULTI-GL");
            writeVector.add(dataList);
            objectOutputStream.writeObject(writeVector);
            objectOutputStream.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Object readObject() {
        try {
            Object readObject = objectInputStream.readObject();
            return readObject;
        } catch (Exception e) {
            e.printStackTrace();
            if (!this.socketTCP.isClosed()) {
                System.out.println("SOCKET CLOSED FROM READ OBJECT");
            }
            return null;
        }
    }

    public void refreshForm() {
        this.setErrMessage("");
        this.setDdsAcCode("0");
        this.setAccountCode("0");
        this.setAgentCode("");
        this.setAgentName("");
        this.agentPanelEnable = "none";
        gridDetail = new ArrayList<MultiAcCodeTo>();
        this.days = "";
        selectedList = new CopyOnWriteArrayList<MultiAcCodeTo>();
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }

    //Getter And Setter
    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public List<SelectItem> getAccountCodeList() {
        return accountCodeList;
    }

    public void setAccountCodeList(List<SelectItem> accountCodeList) {
        this.accountCodeList = accountCodeList;
    }

    public String getAgentPanelEnable() {
        return agentPanelEnable;
    }

    public void setAgentPanelEnable(String agentPanelEnable) {
        this.agentPanelEnable = agentPanelEnable;
    }

    public String getDdsAcCode() {
        return ddsAcCode;
    }

    public void setDdsAcCode(String ddsAcCode) {
        this.ddsAcCode = ddsAcCode;
    }

    public List<SelectItem> getDdsAcCodeList() {
        return ddsAcCodeList;
    }

    public void setDdsAcCodeList(List<SelectItem> ddsAcCodeList) {
        this.ddsAcCodeList = ddsAcCodeList;
    }

    public List<MultiAcCodeTo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<MultiAcCodeTo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public MultiAcCodeTo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(MultiAcCodeTo currentItem) {
        this.currentItem = currentItem;
    }
}
