package com.cbs.web.controller.master;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.master.GeneralMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.master.Trip;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

public final class ParameterInfoReport extends BaseBean {

    private GeneralMasterFacadeRemote generalFacadeRemote;
    private String codePage;
    private String reportNamePage;
    private String message;
    private List<Trip> tripFile;
    private int currentRow;
    private Trip currentItem = new Trip();
    private boolean flag;
    private List<SelectItem> ddReportName;

    public String getCodePage() {
        return codePage;
    }

    public void setCodePage(String codePage) {
        this.codePage = codePage;
    }

    public String getReportNamePage() {
        return reportNamePage;
    }

    public void setReportNamePage(String reportNamePage) {
        this.reportNamePage = reportNamePage;
    }

    public Trip getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(Trip currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<SelectItem> getDdReportName() {
        return ddReportName;
    }

    public void setDdReportName(List<SelectItem> ddReportName) {
        this.ddReportName = ddReportName;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Trip> getTripFile() {
        return tripFile;
    }

    public void setTripFile(List<Trip> tripFile) {
        this.tripFile = tripFile;
    }

    public ParameterInfoReport() {
        this.setMessage("");
        try {
            generalFacadeRemote = (GeneralMasterFacadeRemote) ServiceLocator.getInstance().lookup("GeneralMasterFacade");
            getDropDownList();
            getTableDetails();
            setFlag(true);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

//    public void getDropDownList() {
//        this.setMessage("");
//        try {
//            ddReportName = new ArrayList<SelectItem>();
//            ddReportName.add(new SelectItem("--Select--"));
//            List resultList = generalFacadeRemote.getRefCodeAndDescByNo("004");
//            if (resultList.isEmpty()) {
//                this.setMessage("Please fill data in CBS REF REC TYPE for 004.");
//                return;
//            } else {
//                for (int i = 0; i < resultList.size(); i++) {
//                    Vector ele = (Vector) resultList.get(i);
//                    for (Object ee : ele) {
//                        ddReportName.add(new SelectItem(ee.toString()));
//                    }
//                }
//                getTableDetails();
//            }
//        } catch (ApplicationException e) {
//            setMessage(e.getLocalizedMessage());
//        } catch (Exception e) {
//            setMessage(e.getLocalizedMessage());
//        }
//    }
    
    public void getDropDownList() {
        try {
            List resultList = new ArrayList();
            resultList = generalFacadeRemote.branchCodeDropDownParameterInfoReport();
            ddReportName = new ArrayList<SelectItem>();
            ddReportName.add(new SelectItem("--Select--"));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    ddReportName.add(new SelectItem(ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getTableDetails() {
        try {
            tripFile = new ArrayList<Trip>();
            List resultLt = new ArrayList();
            resultLt = generalFacadeRemote.tableDataParameterInfoReport();
            if (!resultLt.isEmpty()) {
                for (int i = 0; i < resultLt.size(); i++) {
                    Vector ele = (Vector) resultLt.get(i);
                    Trip rd = new Trip();
                    rd.setDesc((ele.get(0).toString()));
                    rd.setCode((ele.get(1).toString()));
                    tripFile.add(rd);
                }
            } else {
                this.setMessage("Records does not exist in Parameterinfo Report. So you can add different parameters.");
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        try {
            String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ReportName"));
            currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
            for (Trip item : tripFile) {
                if (item.getDesc().equals(accNo)) {
                    currentItem = item;
                    break;
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void select() {
        try {
            flag = false;
            setCodePage(currentItem.getCode());
            setReportNamePage(currentItem.getDesc());
            this.setMessage("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void saveBtnAction() {
        try {
            this.setMessage("");
            if (this.reportNamePage.equals("--Select--")) {
                this.setMessage("Please select the appropriate option from Report Name");
                return;
            }
            onblurDuration();
            flag = true;
            String result = generalFacadeRemote.parameterSaveUpdation("save", reportNamePage, Integer.parseInt(codePage));
            this.setMessage(result);
            getTableDetails();
            if (result.equalsIgnoreCase("Same Code Already Exists")) {
                setCodePage(codePage);
            } else {
                setCodePage("");
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void updateBtnAction() {
        try {
            this.setMessage("");
            if (this.reportNamePage.equals("--Select--")) {
                this.setMessage("Please select the appropriate option from Report Name");
                return;
            }
            onblurDuration();
            String result = generalFacadeRemote.parameterSaveUpdation("update", reportNamePage, Integer.parseInt(codePage));
            this.setMessage(result);
            getTableDetails();
            if (result.equalsIgnoreCase("Same Code Already Exists")) {
                setCodePage(codePage);
            } else {
                setCodePage("");
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void valueChange() {
        try {
            this.setMessage("");
            this.setCodePage("");
            if (codePage.equals("")) {
                flag = true;
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void onblurDuration() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(this.getCodePage());
            if (!billNoCheck.matches()) {
                this.setMessage("Please enter numeric value in the Code ");
                this.setCodePage("");
                return;
            }
            if (this.getCodePage().equals("")) {
                this.setMessage("Please enter the value for Code");
            } else if (this.getCodePage().length() < 0) {
                this.setMessage("Please enter positive value for Code");
                this.setCodePage("");
                return;
            } else {
                this.setMessage("");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        this.setMessage("");
        this.setCodePage("");
        this.setReportNamePage("--Select--");
        return "case1";
    }

    public void refreshAction() {
        flag = true;
        this.setMessage("");
        this.setCodePage("");
        this.setReportNamePage("--Select--");
    }
}