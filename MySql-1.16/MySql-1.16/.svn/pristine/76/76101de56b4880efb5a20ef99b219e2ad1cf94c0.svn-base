package com.cbs.web.controller.master;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.master.GeneralMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public final class HolidayMarkingRegister extends BaseBean {
    private GeneralMasterFacadeRemote testEJB;
    private String message;
    private List<HolidayRegisterPojo> register;
    HolidayRegisterPojo currentItem;
    Date holidayDates;
    private String holidayDescriptions;
    private String whatDoWant;
    private String flag;
    boolean flag1;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    
    public boolean isFlag1() {
        return flag1;
    }
    
    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }
    
    public String getFlag() {
        return flag;
    }
    
    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    public String getWhatDoWant() {
        return whatDoWant;
    }
    
    public void setWhatDoWant(String whatDoWant) {
        this.whatDoWant = whatDoWant;
    }
    
    public Date getHolidayDates() {
        return holidayDates;
    }
    
    public void setHolidayDates(Date holidayDates) {
        this.holidayDates = holidayDates;
    }
    
    public String getHolidayDescriptions() {
        return holidayDescriptions;
    }
    
    public void setHolidayDescriptions(String holidayDescriptions) {
        this.holidayDescriptions = holidayDescriptions;
    }
    
    public HolidayRegisterPojo getCurrentItem() {
        return currentItem;
    }
    
    public void setCurrentItem(HolidayRegisterPojo currentItem) {
        this.currentItem = currentItem;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public List<HolidayRegisterPojo> getRegister() {
        return register;
    }
    
    public void setRegister(List<HolidayRegisterPojo> register) {
        this.register = register;
    }
    
    public HolidayMarkingRegister() {
        try {
            testEJB = (GeneralMasterFacadeRemote) ServiceLocator.getInstance().lookup("GeneralMasterFacade");
            flag1 = true;
            getTableDetails();
            this.setMessage("");
            resetAllValue();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public void getTableDetails() {
        try {
            this.setMessage("");
            register = new ArrayList<HolidayRegisterPojo>();
            List tableList = new ArrayList();
            tableList = testEJB.tableDataHolidayMarkingRegister(getOrgnBrCode());
            if (!tableList.isEmpty()) {
                for (int i = 0; i < tableList.size(); i++) {
                    Vector ele = (Vector) tableList.get(i);
                    HolidayRegisterPojo res = new HolidayRegisterPojo();
                    res.setHolidayDate(ele.get(0).toString());
                    res.setHolidayDescription(ele.get(1).toString());
                    register.add(res);
                }
            } else {
                this.setMessage("Records does not Exist. ");
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void setRowValues() {
        try {
            setHolidayDescriptions(currentItem.getHolidayDescription());
            setHolidayDates(new SimpleDateFormat("dd/MM/yyyy").parse(currentItem.getHolidayDate()));
            setWhatDoWant("HOLIDAY UNMARK");
            if (whatDoWant.equalsIgnoreCase("HOLIDAY UNMARK")) {
                flag1 = false;
            } else {
                flag1 = true;
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public void whatdou() {
        try {
            if (whatDoWant.equalsIgnoreCase("HOLIDAY UNMARK")) {
                flag1 = false;
            } else {
                flag1 = true;
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public void SaveData() {
        try {
            this.setMessage("");
            if (holidayDescriptions.equals("")) {
                this.setMessage("Please fill the Holiday description");
                return;
            }
            if (holidayDates == null) {
                this.setMessage("Please fill the Date");
                return;
            }
            String dts = sdf.format(this.holidayDates);
            String save = testEJB.saveHolidayMarkingData(dts, holidayDescriptions, getOrgnBrCode());
            getTableDetails();
            setHolidayDescriptions("");
            setHolidayDates(null);
            this.setMessage(save);
            resetAllValue1();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public void delete() {
        try {
            if (holidayDescriptions.equals("")) {
                this.setMessage("Please fill the Holiday Description");
                return;
            }
            if (holidayDates == null) {
                this.setMessage("Please fill the Date");
                return;
            }
            String dts = sdf.format(this.holidayDates);
            this.setMessage("");
            flag = "d";
            if (holidayDescriptions.equals("")) {
                this.setMessage("Please fill the Holiday Description");
                return;
            }
            Date d1 = new Date();
            if (this.holidayDates.before(d1)) {
                this.setMessage("Cannot select the Date before than Login date...");
                return;
            }
            String deleteEntry = testEJB.deleteHolidayMarkingData(dts, holidayDescriptions, flag, getOrgnBrCode());
            getTableDetails();
            setHolidayDescriptions("");
            setHolidayDates(null);
            this.setMessage(deleteEntry);
            resetAllValue1();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public void upDate() {
        try {
            if (holidayDescriptions.equals("")) {
                this.setMessage("Please fill the Holiday Description");
                return;
            }
            if (holidayDates == null) {
                this.setMessage("Please fill the Date");
                return;
            }
            String dts = sdf.format(this.holidayDates);
            this.setMessage("");
            flag = "u";
            if (holidayDescriptions.equals("")) {
                this.setMessage("Please fill the Holiday Description");
                return;
            }
            Date d1 = new Date();
            if (this.holidayDates.before(d1)) {
                this.setMessage("Cannot select the Date before than Login Date...");
                return;
            }
            String update = testEJB.deleteHolidayMarkingData(dts, holidayDescriptions, flag, getOrgnBrCode());
            getTableDetails();
            setHolidayDescriptions("");
            setHolidayDates(null);
            this.setMessage(update);
            resetAllValue1();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public void resetAllValue() {
        try {
            setHolidayDescriptions("");
            this.setMessage("");
            setHolidayDates(null);
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public void resetAllValue1() {
        try {
            setHolidayDescriptions("");
            setHolidayDates(null);
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public String exitFrm() {
        try {
            resetAllValue();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
