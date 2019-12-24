/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.other.NpciMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.AadharMovedOutUpdationPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public class AadharMovedOutUpdation extends BaseBean {

    private String message;
    private String function;
    private List<SelectItem> functionList;
    private String aadharNo;
    boolean postButtonDisable;
    int currentRow;
    private NpciMgmtFacadeRemote npciFacade = null;
    private CommonReportMethodsRemote reportRemote = null;
    private AadharMovedOutUpdationPojo currentItem = new AadharMovedOutUpdationPojo();
    private List<AadharMovedOutUpdationPojo> gridDetail;

    public AadharMovedOutUpdation() {
        try {
            npciFacade = (NpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciMgmtFacade");
            reportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            //Do't change the functionList values.
            functionList = new ArrayList<>();
            functionList.add(new SelectItem("S", "--Select--"));
            functionList.add(new SelectItem("mo", "Moved Out"));
            functionList.add(new SelectItem("ur", "Update Closed A/c Responce"));
            functionList.add(new SelectItem("mr", "Make Registered"));
            functionList.add(new SelectItem("mi", "Make InActive At NPCI"));
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public boolean validate() {
        try {
            if (function.equalsIgnoreCase("S")) {
                setMessage("Please select function.");
                return false;
            }
            Pattern p = Pattern.compile("[0-9]*");
            if (aadharNo.equalsIgnoreCase("") || aadharNo == null) {
                setMessage("Please fill aadhar no.");
                return false;
            }
            Matcher matcher = p.matcher(aadharNo);
            if (!matcher.matches()) {
                setMessage("Aadhar no should be in numeric digits");
                return false;
            }
            if (aadharNo.length() != 12) {
                setMessage("Aadhar no should be of 12 digits");
                return false;
            }
            List list = reportRemote.getCustIdCheck(aadharNo);
            if (list.isEmpty()) {
                setMessage("There is no such aadhar no.");
                return false;
            }
            if (list.size() > 1) {
                setMessage("This aadhar no is mapped with multiple Customer Id.");
                return false;
            }
            Vector ele = (Vector) list.get(0);
            String aadharBrCode = ele.get(0).toString().trim();

            String alphaCode = reportRemote.getAlphacodeByBrncode(getOrgnBrCode());
            if (!(alphaCode.equalsIgnoreCase("HO") || alphaCode.equalsIgnoreCase("CELL"))) {
                if (!aadharBrCode.equalsIgnoreCase(getOrgnBrCode())) {
                    setMessage("This aadhar no is not mapped with customer id belong to your branch.");
                    return false;
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
            return false;
        }
        return true;
    }

    public void gridLoad() {
        setMessage("");
        try {
            if (validate()) {
                List custList = reportRemote.getCustId(aadharNo);
                Vector idv = (Vector) custList.get(0);
                String custId = idv.get(0).toString();

                gridDetail = new ArrayList<>();
                List dataList = reportRemote.getCustIdData(function, custId, aadharNo);
                if (!dataList.isEmpty()) {
                    for (int i = 0; i < dataList.size(); i++) {
                        Vector vtr = (Vector) dataList.get(i);
                        AadharMovedOutUpdationPojo pojo = new AadharMovedOutUpdationPojo();
                        pojo.setCustId(vtr.get(0).toString());
                        pojo.setAadharNo(vtr.get(1).toString());
                        pojo.setAcNo(reportRemote.getAAdharAcno(custId));
                        if (vtr.get(2).toString().equalsIgnoreCase("R")) {
                            pojo.setStatus("Sent To NPCI");
                        } else if (vtr.get(2).toString().equalsIgnoreCase("E")) {
                            pojo.setStatus("Deactivated at CBS Level");
                        } else if (vtr.get(2).toString().equalsIgnoreCase("U")) {
                            pojo.setStatus("Registered a/c to this aadhar is closed.");
                        } else if (vtr.get(2).toString().equalsIgnoreCase("D")) {
                            pojo.setStatus("Moved Out Aadhar.");
                        } else {
                            pojo.setStatus("");
                        }
                        if (function.equalsIgnoreCase("mo")) {
                            pojo.setMappedStatus("M");
                        } else if (function.equalsIgnoreCase("mi")) {
                            pojo.setMappedStatus("I");
                        } else {
                            pojo.setMappedStatus("");
                        }
                        gridDetail.add(pojo);
                    }
                } else {
                    setMessage("There is no such detail to update.");
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void postDetail() {
        setMessage("");
        try {
            if (validate()) {
                if (gridDetail.isEmpty()) {
                    throw new ApplicationException("There is no data to update the details.");
                }
                if (gridDetail.get(0).getCustId() != null) {
                    String result = npciFacade.aadharDeactivation(gridDetail.get(0).getCustId(), gridDetail.get(0).getAadharNo(), function, gridDetail.get(0).getMappedStatus(), "AD", getUserName());
                    if (result.substring(0, 4).equalsIgnoreCase("true")) {
                        if (function.equalsIgnoreCase("mo")) {
                            this.setMessage("Moved out has been done successfully.");
                        } else if (function.equalsIgnoreCase("ur")) {
                            this.setMessage("Update closed a/c responce has been done successfully.");
                        } else if (function.equalsIgnoreCase("mr")) {
                            this.setMessage("Aadhar has been marked registered successfully.");
                        } else if (function.equalsIgnoreCase("mi")) {
                            this.setMessage("Aadhar has been marked inactive successfully.");
                        }
                    }
                    gridDetail = new ArrayList<>();
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void resetForm() {
        setMessage("");
        setFunction("S");
        setAadharNo("");
        gridDetail = new ArrayList<>();
        this.setPostButtonDisable(true);
    }

    public String exitForm() {
        resetForm();
        return "case1";
    }

    public boolean isPostButtonDisable() {
        return postButtonDisable;
    }

    public void setPostButtonDisable(boolean postButtonDisable) {
        this.postButtonDisable = postButtonDisable;
    }

    public AadharMovedOutUpdationPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(AadharMovedOutUpdationPojo currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<AadharMovedOutUpdationPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<AadharMovedOutUpdationPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
