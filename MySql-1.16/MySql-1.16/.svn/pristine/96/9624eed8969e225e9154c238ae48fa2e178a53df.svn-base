/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.other.PrintFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author Sudhir Kr Bisht
 */
public class BillPrintManager extends BaseBean {

    private String message;
    private String billType;
    private String fieldName;
    private String fieldOrder;
    private String y;
    private String x;
    private String width;
    private String labelValue;
    private boolean disableSave;
    
    private boolean disableUpdate;
    private boolean disableDelete;
    private boolean disablebillType;
    private boolean disableFieldOrder;
    
    private List<SelectItem> billTypeList;
    private List<SelectItem> fieldNameList;
    private List<SelectItem> labelValueList;
    
    List<PrintParameterTable> printGridList = new ArrayList<PrintParameterTable>();
    PrintParameterTable currentprintGrid = new PrintParameterTable();
    private String flag = "false";
    private final String jndiHomeName = "PrintManagementFacade";
    private PrintFacadeRemote beanRemote = null;

    public String getLabelValue() {
        return labelValue;
    }

    public void setLabelValue(String labelValue) {
        this.labelValue = labelValue;
    }

    public List<SelectItem> getLabelValueList() {
        return labelValueList;
    }

    public void setLabelValueList(List<SelectItem> labelValueList) {
        this.labelValueList = labelValueList;
    }

    public List<SelectItem> getFieldNameList() {
        return fieldNameList;
    }

    public void setFieldNameList(List<SelectItem> fieldNameList) {
        this.fieldNameList = fieldNameList;
    }

    public List<SelectItem> getBillTypeList() {
        return billTypeList;
    }

    public void setBillTypeList(List<SelectItem> billTypeList) {
        this.billTypeList = billTypeList;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public boolean isDisableFieldOrder() {
        return disableFieldOrder;
    }

    public void setDisableFieldOrder(boolean disableFieldOrder) {
        this.disableFieldOrder = disableFieldOrder;
    }

    public boolean isDisablebillType() {
        return disablebillType;
    }

    public void setDisablebillType(boolean disablebillType) {
        this.disablebillType = disablebillType;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public PrintParameterTable getCurrentprintGrid() {
        return currentprintGrid;
    }

    public void setCurrentprintGrid(PrintParameterTable currentprintGrid) {
        this.currentprintGrid = currentprintGrid;
    }

    public List<PrintParameterTable> getPrintGridList() {
        return printGridList;
    }

    public void setPrintGridList(List<PrintParameterTable> printGridList) {
        this.printGridList = printGridList;
    }

    public boolean isDisableDelete() {
        return disableDelete;
    }

    public void setDisableDelete(boolean disableDelete) {
        this.disableDelete = disableDelete;
    }

    public boolean isDisableSave() {
        return disableSave;
    }

    public void setDisableSave(boolean disableSave) {
        this.disableSave = disableSave;
    }

    public boolean isDisableUpdate() {
        return disableUpdate;
    }

    public void setDisableUpdate(boolean disableUpdate) {
        this.disableUpdate = disableUpdate;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldOrder() {
        return fieldOrder;
    }

    public void setFieldOrder(String fieldOrder) {
        this.fieldOrder = fieldOrder;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Creates a new instance of BillPrintManager
     */
    public BillPrintManager() {
        try {
            beanRemote = (PrintFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            this.setMessage("");
            disableDelete = true;
            disableSave = false;
            disableUpdate = true;
            billTypeList = new ArrayList<SelectItem>();
            billTypeList.add(new SelectItem("0", "--Select--"));
            fieldNameList = new ArrayList<SelectItem>();
            labelValueList = new ArrayList<SelectItem>();
            getOnLoadData();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void getOnLoadData() {
        try {
            List dataList = beanRemote.getOnloadData();
            if (dataList.size() > 0) {
                for (int i = 0; i < dataList.size(); i++) {
                    Vector element = (Vector) dataList.get(i);
                    billTypeList.add(new SelectItem(element.get(0).toString(), element.get(0).toString()));
                }
            }
            billTypeList.add(new SelectItem("FD", "FD"));
            billTypeList.add(new SelectItem("PB", "PB"));
            
            labelValueList.add(new SelectItem("L", "Label"));
            labelValueList.add(new SelectItem("V", "Value"));
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void onBlurBillTypeEvent() {
        try {
            List billList = beanRemote.getData(billType, labelValue);
            if (!billList.isEmpty()) {
                printGridList.clear();
                flag = "true";
                for (int i = 0; i < billList.size(); i++) {
                    PrintParameterTable table = new PrintParameterTable();
                    Vector v = (Vector) billList.get(i);
                    table.setBillType(v.get(0).toString());
                    table.setFieldName(v.get(1).toString());
                    table.setFieldOrder(v.get(2).toString());
                    table.setX(v.get(3).toString());
                    table.setY(v.get(4).toString());
                    table.setWidth(v.get(5).toString());
                    if(v.get(6).toString().equals("L")){
                        table.setFieldType("Label");
                    }else{
                        table.setFieldType("Value");
                    }
                    printGridList.add(table);
                }
            } else {
                flag = "false";
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void getFieldList() {
        try {
            fieldNameList = new ArrayList<SelectItem>();
            List billList = beanRemote.getFieldNameList(billType, labelValue);
            if (!billList.isEmpty()) {
                for (int i = 0; i < billList.size(); i++) {
                    Vector element = (Vector) billList.get(i);
                    fieldNameList.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

//    public void saveButton() {
//        try {
//            String valresult = valiations();
//            if (!valresult.equalsIgnoreCase("true")) {
//                setMessage(valresult);
//                return;
//            }
//            String result = beanRemote.save(billType, fieldName, Integer.parseInt(fieldOrder), Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(width));
//            refresh();
//            setMessage(result);
//        } catch (ApplicationException e) {
//            this.setMessage(e.getMessage());
//        } catch (Exception e) {
//            this.setMessage(e.getLocalizedMessage());
//        }
//    }

    public void updateButton() {
        try {
            String valresult = valiations();
            if (!valresult.equalsIgnoreCase("true")) {
                setMessage(valresult);
                return;
            }
            String result = beanRemote.update(billType, labelValue, Integer.parseInt(fieldName), Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(width));
            refresh();
            setMessage(result);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

//    public void deleteButton() {
//        try {
//            String valresult = valiations();
//            if (!valresult.equalsIgnoreCase("true")) {
//                setMessage(valresult);
//                return;
//            }
//            String result = beanRemote.delete(billType, Integer.parseInt(fieldOrder));
//            refresh();
//            setMessage(result);
//        } catch (ApplicationException e) {
//            this.setMessage(e.getMessage());
//        } catch (Exception e) {
//            this.setMessage(e.getLocalizedMessage());
//        }
//    }

    public void refresh() {
        try {
            setMessage("");
            setFieldName("");
            setFieldOrder("");
            setX("");
            setY("");
            setWidth("");
            setBillType("");
            disableDelete = true;
            disableSave = false;
            disableUpdate = true;
            disableFieldOrder = false;
            disablebillType = false;
            fieldNameList = new ArrayList<SelectItem>();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void setGridData() {
        try {
            setBillType(currentprintGrid.getBillType());
            setFieldName(currentprintGrid.getFieldName());
            setFieldOrder(currentprintGrid.getFieldOrder());
            setX(currentprintGrid.getX());
            setY(currentprintGrid.getY());
            setWidth(currentprintGrid.getWidth());
            setDisableSave(true);
            setDisableDelete(false);
            setDisableUpdate(false);
            disableFieldOrder = true;
            disablebillType = true;
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String valiations() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (billType.equalsIgnoreCase("")) {
                return "Bill type cannot be blank!!!";
            }
            if (fieldName.equalsIgnoreCase("")) {
                return "Field name cannot be blank!!!";
            }
            if (fieldOrder.equalsIgnoreCase("")) {
                return "Field order no cannot be blank!!!";
            }
            Matcher fieldOrderCheck = p.matcher(fieldOrder);
            if (!fieldOrderCheck.matches()) {
                return "Enter the integral values for field order no (0-9)!!!";
            }
            if (Integer.parseInt(fieldOrder) <= 0) {
                return "Field order cannot be negative or zero and should be integral value!!!";
            }
            if (y.equalsIgnoreCase("")) {
                return "Y Co-Ordinate cannot be blank!!!";
            }
            Matcher topSpaceCheck = p.matcher(y);
            if (!topSpaceCheck.matches()) {
                return "Enter valid numeric value for Y Co-Ordinate!!!";
            }
            if (Integer.parseInt(y) < 0) {
                return "Y Co-Ordinate field cannot be negative or zero(0)!!!";
            }
            if (x.equalsIgnoreCase("")) {
                return "X Co-Ordinate cannot be blank!!!";
            }
            Matcher leftSpaceCheck = p.matcher(x);
            if (!leftSpaceCheck.matches()) {
                return "Enter valid numeric value for X Co-Ordinate!!!";
            }
            if (Integer.parseInt(x) < 0) {
                return "X Co-Ordinate field cannot be negative or zero(0)!!!";
            }
            if (width.equalsIgnoreCase("")) {
                return "Column width cannot be blank!!!";
            }
            Matcher colWidthCheck = p.matcher(x);
            if (!colWidthCheck.matches()) {
                return "Enter valid numeric value for Column width!!!";
            }
            if (Integer.parseInt(width) < 0) {
                return "Column Width field cannot be negative or zero(0)!!!";
            }
            return "true";
        } catch (NumberFormatException e) {
            return "Error in validating numeric value!!!";
        } catch (Exception e) {
            return "Error occurred in Validating!!!";
        }
    }

    public String exit() {
        return "case1";
    }
}
