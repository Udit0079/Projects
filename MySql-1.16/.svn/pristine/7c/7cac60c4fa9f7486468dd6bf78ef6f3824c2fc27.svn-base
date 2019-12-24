package com.cbs.web.controller.master;

import com.cbs.exception.ApplicationException;
import com.cbs.web.pojo.master.LedgerDescTable;
import com.cbs.facade.master.LedgerMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GlDescription extends BaseBean {

    private boolean saveDisable;
    private boolean updateDisable;
    private String message;
    LedgerMasterFacadeRemote ledCodeMReg;
    private List<LedgerDescTable> glDescTab;
    private String glType;
    private String rangeFrom;
    private String rangeTo;
    private LedgerDescTable currentItem = new LedgerDescTable();
    private int mCode;

    public boolean isSaveDisable() {
        return saveDisable;
    }

    public void setSaveDisable(boolean saveDisable) {
        this.saveDisable = saveDisable;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<LedgerDescTable> getGlDescTab() {
        return glDescTab;
    }

    public void setGlDescTab(List<LedgerDescTable> glDescTab) {
        this.glDescTab = glDescTab;
    }

    public String getGlType() {
        return glType;
    }

    public void setGlType(String glType) {
        this.glType = glType;
    }

    public String getRangeFrom() {
        return rangeFrom;
    }

    public void setRangeFrom(String rangeFrom) {
        this.rangeFrom = rangeFrom;
    }

    public String getRangeTo() {
        return rangeTo;
    }

    public void setRangeTo(String rangeTo) {
        this.rangeTo = rangeTo;
    }

    public boolean isUpdateDisable() {
        return updateDisable;
    }

    public void setUpdateDisable(boolean updateDisable) {
        this.updateDisable = updateDisable;
    }

    public int getmCode() {
        return mCode;
    }

    public void setmCode(int mCode) {
        this.mCode = mCode;
    }

    public LedgerDescTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(LedgerDescTable currentItem) {
        this.currentItem = currentItem;
    }

    public GlDescription() {
        try {
            ledCodeMReg = (LedgerMasterFacadeRemote) ServiceLocator.getInstance().lookup("LedgerMasterFacade");
            setSaveDisable(false);
            setUpdateDisable(true);
            getTableValues();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getTableValues() {
        try {
            glDescTab = new ArrayList<LedgerDescTable>();
            List resultList = new ArrayList();
            resultList = ledCodeMReg.getGlDescData();
            if (resultList.size() <= 0) {
                return;
            }
            for (int i = 0; i < resultList.size(); i++) {
                Vector tableVector = (Vector) resultList.get(i);
                LedgerDescTable mt = new LedgerDescTable();
                mt.setGlCode(Integer.parseInt(tableVector.get(0).toString()));
                mt.setGlDes(tableVector.get(1).toString());
                mt.setGlRangeFr(Integer.parseInt(tableVector.get(2).toString()));
                mt.setGlRangeTo(Integer.parseInt(tableVector.get(3).toString()));
                glDescTab.add(mt);
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String validation() {
        try {
            String msg = "";
            if (this.getGlType() == null || this.glType.equals("")) {
                msg = "Please Fill G.L. Description";
                setMessage(msg);
                return "False";
            }
            if (!chkGlDescription().equalsIgnoreCase("true")) {
                return "False";
            }
            if (this.getRangeFrom() == null || this.getRangeFrom().equals("")) {
                msg = "Please Fill G.L. Range From";
                setMessage(msg);
                return "False";
            }
            if (!chkRange(this.getRangeFrom()).equalsIgnoreCase("true")) {
                return "False";
            }
            if (this.getRangeTo() == null || this.getRangeTo().equals("")) {
                msg = "Please Fill G.L. Range To";
                setMessage(msg);
                return "False";
            }
            if (!chkRange(this.getRangeTo()).equalsIgnoreCase("true")) {
                return "False";
            }
            if ((Integer.parseInt(this.getRangeFrom()) == 0) || (Integer.parseInt(this.getRangeTo())) == 0) {
                msg = "From Range Or to Range can not be equal to 0";
                setMessage(msg);
                return "False";
            }
            if ((Integer.parseInt(this.getRangeFrom())) > (Integer.parseInt(this.getRangeTo()))) {
                msg = "From Range can not be greater than to Range";
                setMessage(msg);
                return "False";
            }
            if ((Integer.parseInt(this.getRangeFrom())) == (Integer.parseInt(this.getRangeTo()))) {
                msg = "From Range can not be equal than to Range";
                setMessage(msg);
                return "False";
            }
            return "true";
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            return "false";
        }
    }

    public void saveAction() {
        try {
            setMessage("");
            if (validation().equalsIgnoreCase("False")) {
                return;
            }
            String msg = "";
            msg = ledCodeMReg.SaveData(this.getGlType(), Integer.parseInt(this.getRangeFrom()), Integer.parseInt(this.getRangeTo()), this.getUserName());
            setMessage(msg);
            getTableValues();
            setGlType("");
            setRangeFrom("");
            setRangeTo("");
            setSaveDisable(false);
            setUpdateDisable(true);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void updateAction() {
        try {
            setMessage("");
            if (validation().equalsIgnoreCase("False")) {
                return;
            }
            String msg = "";
            msg = ledCodeMReg.updateData(this.getmCode(), this.getGlType(), Integer.parseInt(this.getRangeFrom()), Integer.parseInt(this.getRangeTo()), this.getUserName());
            setMessage(msg);
            getTableValues();
            setGlType("");
            setRangeFrom("");
            setRangeTo("");
            setSaveDisable(false);
            setUpdateDisable(true);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void select() {
        try {
            this.setSaveDisable(true);
            this.setUpdateDisable(false);
            this.setmCode(currentItem.getGlCode());
            this.setGlType(currentItem.getGlDes());
            this.setRangeFrom(currentItem.getGlRangeFr().toString());
            this.setRangeTo(currentItem.getGlRangeTo().toString());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void refreshAction() {
        try {
            setMessage("");
            setGlType("");
            setRangeFrom("");
            setRangeTo("");
            setSaveDisable(false);
            setUpdateDisable(true);
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String btnExit() {
        refreshAction();
        return "case1";
    }

    public String chkGlDescription() {
        try {
            Pattern p = Pattern.compile("[A-Za-z0-9-()]+([ '-][A-Za-z0-9-()]+)*");
            Matcher custfullNameCheck = p.matcher(this.getGlType());
            if (!custfullNameCheck.matches()) {
                this.setMessage("Please Enter Characters in G.L Description Field");
                return "Please Enter Characters in G.L Description Field.";
            } else {
                this.setMessage("");
                return "true";
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            return "false";
        }
    }

    public String chkRange(String chk) {
        try {
            if (!chk.equalsIgnoreCase("")) {
                Pattern p = Pattern.compile("((|\\+)?[0-9]+(\\[0-9])?)+");
                Matcher mobileNoCheck = p.matcher(chk);
                if (!mobileNoCheck.matches()) {
                    this.setMessage("Please Enter Numeric Value " + chk + " is not correct");
                    return "Please Enter Numeric Value " + chk + " is not correct";
                } else {
                    this.setMessage("");
                }
            }
            return "true";
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            return "false";
        }
    }
}
