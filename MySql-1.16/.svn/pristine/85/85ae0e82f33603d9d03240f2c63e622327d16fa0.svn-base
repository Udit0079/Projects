/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;


import com.cbs.dto.complex.GlSubHeadSchemeTO;
import com.cbs.dto.master.GltableTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import com.cbs.web.pojo.master.GlSubHeadTable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class GSHSD {

    private static final Logger logger = Logger.getLogger(CAD.class);
    private SchemeMaster schemeMaster;

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }
    //Variables for gshsd.jsp file
    private String glSubHeadCode;
    private String glSubHead;
    private String defaultFlag;
    private String deleteFlag;
    private boolean disableFlagGl;
    private int currentRow;
    private int count1 = 0;
    private int count2 = 0;
    private int selectCount = 0;
    private List<SelectItem> deleteGlList;
    private List<GlSubHeadTable> glHead;
    //private List<GlSubHeadTable> var1;
    private List<GlSubHeadTable> glHeadTmp;
    private GlSubHeadTable currentItem = new GlSubHeadTable();

    //Getter-Setter for gshsd.jsp file
    public int getCount1() {
        return count1;
    }

    public void setCount1(int count1) {
        this.count1 = count1;
    }

    public int getCount2() {
        return count2;
    }

    public void setCount2(int count2) {
        this.count2 = count2;
    }

    public GlSubHeadTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(GlSubHeadTable currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public List<SelectItem> getDeleteGlList() {
        return deleteGlList;
    }

    public void setDeleteGlList(List<SelectItem> deleteGlList) {
        this.deleteGlList = deleteGlList;
    }

    public boolean isDisableFlagGl() {
        return disableFlagGl;
    }

    public void setDisableFlagGl(boolean disableFlagGl) {
        this.disableFlagGl = disableFlagGl;
    }

    public List<GlSubHeadTable> getGlHead() {
        return glHead;
    }

    public void setGlHead(List<GlSubHeadTable> glHead) {
        this.glHead = glHead;
    }

    public List<GlSubHeadTable> getGlHeadTmp() {
        return glHeadTmp;
    }

    public void setGlHeadTmp(List<GlSubHeadTable> glHeadTmp) {
        this.glHeadTmp = glHeadTmp;
    }

    public String getGlSubHead() {
        return glSubHead;
    }

    public void setGlSubHead(String glSubHead) {
        this.glSubHead = glSubHead;
    }

    public String getGlSubHeadCode() {
        return glSubHeadCode;
    }

    public void setGlSubHeadCode(String glSubHeadCode) {
        this.glSubHeadCode = glSubHeadCode;
    }

    public int getSelectCount() {
        return selectCount;
    }

    public void setSelectCount(int selectCount) {
        this.selectCount = selectCount;
    }

    /** Creates a new instance of GSHSD */
    public GSHSD() {
        
        try {
            glHead = new ArrayList<GlSubHeadTable>();
            glHeadTmp = new ArrayList<GlSubHeadTable>();

            deleteGlList = new ArrayList<SelectItem>();
            deleteGlList.add(new SelectItem("0", ""));
            deleteGlList.add(new SelectItem("Y", "Yes"));
            deleteGlList.add(new SelectItem("N", "No"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Functionality for gshsd.jsp file
    public void selectGSHSDDetails() {
         this.getSchemeMaster().setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            List<GlSubHeadSchemeTO> listTOs = schemeMasterManagementDelegate.getGSHSDDetails(schemeMaster.getSchemeCode());
            if (listTOs.size() > 0) {
                for (GlSubHeadSchemeTO singleTO : listTOs) {
                    GlSubHeadTable tableObj = new GlSubHeadTable();
                    if (singleTO.getGlSubHeadCode() == null || singleTO.getGlSubHeadCode().equalsIgnoreCase("")) {
                        tableObj.setGlSubHeadCode("");
                    } else {
                        tableObj.setGlSubHeadCode(singleTO.getGlSubHeadCode());
                    }
                    if (singleTO.getAcName() == null || singleTO.getAcName().equalsIgnoreCase("")) {
                        tableObj.setGlSubHead("");
                    } else {
                        tableObj.setGlSubHead(singleTO.getAcName());
                    }
                    if (singleTO.getDefaultFlag() == null || singleTO.getDefaultFlag().equalsIgnoreCase("")) {
                        tableObj.setDefaultFlag("");
                    } else {
                        tableObj.setDefaultFlag(singleTO.getDefaultFlag());
                    }
                    if (singleTO.getDelFlag() == null || singleTO.getDelFlag().equalsIgnoreCase("")) {
                        tableObj.setDeleteFlag("0");
                    } else {
                        tableObj.setDeleteFlag(singleTO.getDelFlag());
                    }
                    tableObj.setCounterSaveUpdate("G");
                    glHead.add(tableObj);
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getGlSubHeadFun() {
        List<GlSubHeadTable> tmpGlTable = glHead;
        try {
            if (schemeMaster.functionFlag.equalsIgnoreCase("M") || schemeMaster.functionFlag.equalsIgnoreCase("A")) {
                for (int i = 0; i < tmpGlTable.size(); i++) {
                    String tmpGlSubHeadCode = tmpGlTable.get(i).getGlSubHeadCode();
                    if (tmpGlSubHeadCode.equalsIgnoreCase(glSubHeadCode)) {
                        schemeMaster.setMessage(glSubHeadCode + "already Exist In table enter New Gl sub Head");
                        return;
                    }
                }
            }
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            GltableTO gltableTO = schemeMasterManagementDelegate.getGLTable(this.getGlSubHeadCode());
            if (gltableTO != null) {
                this.setGlSubHead(gltableTO.getAcName());
            } else {
                return;
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void setDataInGlTable() {
        List<GlSubHeadTable> tmpGlTable = glHead;
        if (schemeMaster.functionFlag.equalsIgnoreCase("M") || schemeMaster.functionFlag.equalsIgnoreCase("A")) {
            for (int i = 0; i < tmpGlTable.size(); i++) {
                String tmpDefaultFlag = tmpGlTable.get(i).getDefaultFlag();
                //String tmpDeleteFlag = tmpGlTable.get(i).getDeleteFlag();
                if (tmpDefaultFlag.equalsIgnoreCase("Y")) {
                    if (defaultFlag.equalsIgnoreCase("Y")) {
                        schemeMaster.setMessage("Only one gl sub head can be 'Y' mark aginst on scheme code");
                        return;
                    }
                }
                if (deleteFlag.equalsIgnoreCase("Y")) {
                    if (defaultFlag.equalsIgnoreCase("Y")) {
                        schemeMaster.setMessage("Deleted Glsub Head can not be 'Y' mark");
                        return;
                    }
                }
            }
        }
        if (validationGL().equalsIgnoreCase("false")) {
            return;
        }
        GlSubHeadTable glsHead = new GlSubHeadTable();
        glsHead.setGlSubHeadCode(getGlSubHeadCode());
        glsHead.setGlSubHead(getGlSubHead());
        glsHead.setDefaultFlag(getDefaultFlag());
        glsHead.setDeleteFlag(getDeleteFlag());
        if (schemeMaster.functionFlag.equalsIgnoreCase("A")) {
            glsHead.setCounterSaveUpdate("S");
        }
        glHead.add(glsHead);
        if (schemeMaster.functionFlag.equalsIgnoreCase("M")) {
            if (selectCount == 0) {
                for (int i = 0; i < tmpGlTable.size(); i++) {
                    String tmpGlSubHeadCode = tmpGlTable.get(i).getGlSubHeadCode();
                    if (!tmpGlSubHeadCode.equalsIgnoreCase(glSubHeadCode)) {
                        glsHead.setCounterSaveUpdate("S");
                        //validation();
                        glHeadTmp.add(glsHead);
                        refreshGSHSDForm();
                        return;
                    }
                }
                selectCount = 0;
            }
            if (currentItem.getGlSubHeadCode() == null || currentItem.getGlSubHeadCode().equalsIgnoreCase("")) {
                glsHead.setCounterSaveUpdate("S");
                glHeadTmp.add(glsHead);
                refreshGSHSDForm();
                return;
            } else if (currentItem.getGlSubHeadCode().equalsIgnoreCase(glSubHeadCode)) {
                if (!currentItem.getDefaultFlag().equalsIgnoreCase(defaultFlag) || !currentItem.getDeleteFlag().equalsIgnoreCase(deleteFlag)) {
                    glsHead.setCounterSaveUpdate("U");
                    refreshGSHSDForm();
                    glHeadTmp.add(glsHead);
                }
                selectCount = 0;
            }
        }
    }

    public String validationGL() {
        String msg = "";

        if (deleteFlag.equalsIgnoreCase("0")) {
            msg = "Please Select The Deleted Flag";
        }
        if (defaultFlag == null || defaultFlag.equalsIgnoreCase("")) {
            msg = msg + "Please Enter the DefaultFlag";
        } else {
            if (!(defaultFlag.equalsIgnoreCase("Y") || defaultFlag.equalsIgnoreCase("N"))) {
                msg = msg + "Please Enter the correct Default Flag (Y or N)";
            }
        }
        if (glSubHeadCode.equalsIgnoreCase("") || glSubHeadCode == null) {
            msg = msg + "Please Enter the Gl Sub Head Code";
        }
        if (glSubHead.equalsIgnoreCase("") || glSubHead == null) {
            msg = msg + "Please Enter the Gl Sub Head";
        }
        if (msg.equalsIgnoreCase("")) {
            return "true";

        } else {
            schemeMaster.setMessage(msg);
            return "false";
        }
    }

    public void selectGl() {
        selectCount = 1;
        if ((glSubHeadCode != null && currentItem.getGlSubHeadCode() != null)) {
            if (!glSubHeadCode.equalsIgnoreCase("")) {
                if (!glSubHeadCode.equalsIgnoreCase(currentItem.getGlSubHeadCode())) {
                    count2 = count1;
                    count1 = count1 + 1;
                    if (count2 != count1) {
                        setDataInGlTable();
                    }
                } else {
                    count1 = 0;
                }
            }
        }
        setGlSubHead(currentItem.getGlSubHead());
        setGlSubHeadCode(currentItem.getGlSubHeadCode());
        setDefaultFlag(currentItem.getDefaultFlag());
        setDeleteFlag(currentItem.getDeleteFlag());
        glHead.remove(currentRow);
    }

    public void refreshGSHSDForm() {
        this.setGlSubHeadCode("");
        this.setGlSubHead("");
        this.setDefaultFlag("");
        this.setDeleteFlag("0");
    }

    public void enableGSHSDForm() {
        this.disableFlagGl = false;
    }

    public void disableGSHSDForm() {
        this.disableFlagGl = true;
    }
}
